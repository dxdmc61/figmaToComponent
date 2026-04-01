package com.figma.aem.core.services.imp;

import com.figma.aem.core.services.AIGenerator;

import freemarker.template.Configuration;
import freemarker.template.Template;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.http.Part;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GeminiGenerator implements AIGenerator {

    private final String apiKey;
    private final Configuration cfg;

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-pro:generateContent";

    public GeminiGenerator(String apiKey) {
        this.apiKey = apiKey;

        cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDefaultEncoding("UTF-8");

        // Loads templates from: core/src/main/resources/templates/
        cfg.setClassLoaderForTemplateLoading(
                getClass().getClassLoader(),
                "templates"
        );
    }

    @Override
    public Map<String, String> generateComponent(Part figmaFile, String componentName, String prompt) throws Exception {
        String figmaJson = extractFigmaJson(figmaFile);
        String userPrompt = buildUserPrompt(componentName, prompt, figmaJson);
        String responseContent = callGemini(userPrompt);
        return parseGeneratedCode(responseContent);
    }

    /** --------------------------------------------------------------------
     *  Load uploaded Figma JSON file
     * -------------------------------------------------------------------- */
    private String extractFigmaJson(Part figmaFile) throws Exception {
        try (InputStream inputStream = figmaFile.getInputStream()) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
    }

    /** --------------------------------------------------------------------
     *  Build the AI prompt using FreeMarker template
     * -------------------------------------------------------------------- */
    private String buildUserPrompt(String componentTitle, String description, String figmaJson)
            throws Exception {

        Template template = cfg.getTemplate("gemini_prompt.ftl");

        // Generate className for Sling Model (CamelCase + Model)
        String sanitized = componentTitle.replaceAll("[^A-Za-z0-9]", "");
        String className = sanitized.substring(0, 1).toUpperCase() + sanitized.substring(1) + "Model";

        String componentPath = "/apps/figma/components/content/" +
                componentTitle.toLowerCase().replace(" ", "");

        String javaModelPath =
                "src/main/java/com/figma/aem/core/models/" + className + ".java";

        // NEW: ClientLib category
        String clientLibCategory =
                "figma." + componentTitle.toLowerCase().replace(" ", "");

        String clientLibPath =
                componentPath + "/clientlibs/" + clientLibCategory;

        Map<String, Object> model = new HashMap<>();
        model.put("componentTitle", componentTitle);
        model.put("description", description);
        model.put("figmaJson", figmaJson);
        model.put("componentPath", componentPath);
        model.put("className", className);
        model.put("javaModelPath", javaModelPath);

        // NEW: Add clientLib bindings
        model.put("clientLibCategory", clientLibCategory);
        model.put("clientLibPath", clientLibPath);

        // Placeholder (you may extract real fields in future)
        model.put("fields", "");

        StringWriter out = new StringWriter();
        template.process(model, out);
        return out.toString();
    }

    /** --------------------------------------------------------------------
     *  Send request to Gemini API
     * -------------------------------------------------------------------- */
    private String callGemini(String userPrompt) throws Exception {

        JSONObject requestJson = new JSONObject();

        JSONArray partsArray = new JSONArray()
                .put(new JSONObject().put("text", userPrompt));

        JSONArray contents = new JSONArray()
                .put(new JSONObject().put("parts", partsArray));

        requestJson.put("contents", contents);

        JSONObject generationConfig = new JSONObject();
        generationConfig.put("temperature", 0.3);
        requestJson.put("generationConfig", generationConfig);

        URL url = new URL(GEMINI_API_URL + "?key=" + apiKey);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(600000);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestJson.toString().getBytes(StandardCharsets.UTF_8));
        }

        int status = connection.getResponseCode();
        String response;

        try (InputStream responseStream =
                     (status == HttpURLConnection.HTTP_OK)
                             ? connection.getInputStream()
                             : connection.getErrorStream()) {

            response = IOUtils.toString(responseStream, StandardCharsets.UTF_8);
        } finally {
            connection.disconnect();
        }

        if (status != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException(
                    "Gemini API error: HTTP " + status + ": " + response
            );
        }

        JSONObject responseJson = new JSONObject(new JSONTokener(response));

        if (responseJson.has("candidates")) {
            JSONObject candidate = responseJson.getJSONArray("candidates")
                    .getJSONObject(0);

            JSONArray parts = candidate.getJSONObject("content")
                    .optJSONArray("parts");

            if (parts != null && parts.length() > 0) {
                JSONObject part = parts.getJSONObject(0);
                if (part.has("text")) {
                    return part.getString("text").trim();
                }
            }
        }

        throw new RuntimeException("Failed to extract content from Gemini response: " + response);
    }

    /** --------------------------------------------------------------------
     *  Parse JSON returned by AI into Map<String,String>
     * -------------------------------------------------------------------- */
    private Map<String, String> parseGeneratedCode(String responseText) {
        String cleaned = responseText.trim();

        // Handle cases where AI wraps JSON in ```json blocks
        if (cleaned.startsWith("```")) {
            int startIndex = cleaned.indexOf('{');
            int endIndex = cleaned.lastIndexOf('}');
            if (startIndex != -1 && endIndex != -1) {
                cleaned = cleaned.substring(startIndex, endIndex + 1);
            }
        }

        try {
            JSONObject json = new JSONObject(cleaned);
            Map<String, String> files = new HashMap<>();

            for (String key : json.keySet()) {
                files.put(key, json.getString(key));
            }

            return files;

        } catch (Exception e) {
            System.err.println("Failed AI output:\n" + responseText);
            throw new RuntimeException("Invalid AI JSON output. " + e.getMessage());
        }
    }
}
