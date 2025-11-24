package com.figma.aem.core.services.imp;

import com.figma.aem.core.services.AIGenerator;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.http.Part;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GeminiGenerator implements AIGenerator {

    private final String apiKey;
    // IMPORTANT: Use a stable model name like 'gemini-2.5-flash' or 'gemini-2.5-pro'
    // for production.
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

    public GeminiGenerator(String apiKey) {
        // Use the passed API key directly.
        // NOTE: The placeholder key has been removed.
        this.apiKey = apiKey;
    }

    @Override
    public Map<String, String> generateComponent(Part figmaFile, String componentName, String prompt) throws Exception {
        String figmaJson = extractFigmaJson(figmaFile);
        String userPrompt = buildUserPrompt(componentName, prompt, figmaJson);
        String responseContent = callGemini(userPrompt);
        return parseGeneratedCode(responseContent);
    }

    private String extractFigmaJson(Part figmaFile) throws Exception {
        try (InputStream inputStream = figmaFile.getInputStream()) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
    }

    private String buildUserPrompt(String componentName, String prompt, String figmaJson) {
        return String.format(
                "You are an expert AEM developer.\n\n" +
                        "Your task is to generate a production-ready Adobe Experience Manager (AEM) component based on the provided information.\n\n"
                        +
                        "Component Name: %s\n\n" +
                        "Design Specification (Figma JSON):\n%s\n\n" +
                        "Additional User Prompt:\n%s\n\n" +
                        "Follow these AEM best practices:\n" +
                        "- Use HTL (HTML Template Language) with data-sly attributes\n" +
                        "- Use granite/ui components for dialogs\n" +
                        "- Use Sling Models (OSGi R6 annotations)\n" +
                        "- Follow BEM methodology for CSS\n" +
                        "- Ensure all fields are configurable via the dialog\n\n" +
                        "Return your response as a **JSON object** where:\n" +
                        "- Keys are filenames (e.g. `component.html`, `ComponentModel.java`, `dialog.xml`, `clientlib.css`, `clientlib.js`)\n"
                        +
                        "- Values are the full code content\n\n" +
                        "Respond with only the JSON object. Do not include explanation or Markdown formatting.",
                componentName, figmaJson, prompt);
    }

    private String callGemini(String userPrompt) throws Exception {
        // 1. Construct the API Request JSON
        JSONObject requestJson = new JSONObject();
        
        // Parts array for the prompt
        JSONObject partsObject = new JSONObject();
        partsObject.put("text", userPrompt);
        JSONArray partsArray = new JSONArray();
        partsArray.put(partsObject);
        
        // Content object
        JSONObject contentObject = new JSONObject();
        contentObject.put("parts", partsArray);
        
        // Contents array
        JSONArray contents = new JSONArray();
        contents.put(contentObject);
        requestJson.put("contents", contents);

        // Generation configuration
        JSONObject generationConfig = new JSONObject();
        generationConfig.put("temperature", 0.3);
        requestJson.put("generationConfig", generationConfig);

        // 2. Execute the HTTP Call
        URL url = new URL(GEMINI_API_URL + "?key=" + apiKey);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.setConnectTimeout(15000); // 15 seconds
        connection.setReadTimeout(600000); // 15 seconds

        // Send request payload
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestJson.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 3. Process the Response
        int status = connection.getResponseCode();
        String response;
        
        try (InputStream responseStream = (status == HttpURLConnection.HTTP_OK)
                ? connection.getInputStream()
                : connection.getErrorStream()) {
            
            response = IOUtils.toString(responseStream, StandardCharsets.UTF_8);
        } finally {
            connection.disconnect();
        }

        if (status != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Gemini API error: HTTP " + status + " - " + response);
        }

        // 4. Extract the generated text from the Gemini response structure
        JSONObject responseJson = new JSONObject(new JSONTokener(response));

        if (responseJson.has("candidates") && !responseJson.getJSONArray("candidates").isEmpty()) {
            JSONObject candidate = responseJson.getJSONArray("candidates").getJSONObject(0);
            if (candidate.has("content") && candidate.getJSONObject("content").has("parts") &&
                    !candidate.getJSONObject("content").getJSONArray("parts").isEmpty()) {

                JSONObject part = candidate.getJSONObject("content").getJSONArray("parts").getJSONObject(0);
                if (part.has("text")) {
                    return part.getString("text").trim();
                }
            }
        }
        
        // Throw if structure is unexpected, but status was OK
        throw new RuntimeException("Failed to extract content from Gemini API response: " + response);
    }

    private Map<String, String> parseGeneratedCode(String responseText) {
        String cleaned = responseText.trim();

        // Robustly remove JSON code block wrappers if they exist
        if (cleaned.startsWith("```")) {
            // Find the start of the actual JSON content after the opening ```[language]
            int startIndex = cleaned.indexOf('{');
            int endIndex = cleaned.lastIndexOf('}');
            
            if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
                // Extract only the content between the first { and the last }
                cleaned = cleaned.substring(startIndex, endIndex + 1);
            }
        }
        
        try {
            JSONObject json = new JSONObject(new JSONTokener(cleaned));
            Map<String, String> files = new HashMap<>();
            for (String key : json.keySet()) {
                files.put(key, json.getString(key));
            }
            return files;
        } catch (Exception e) {
            // Log the problematic text for debugging
            System.err.println("Failed to parse AI JSON output:\n" + responseText);
            throw new RuntimeException(
                    "Failed to parse AI JSON output. Please check the AI response format. Error: " + e.getMessage());
        }
    }
}