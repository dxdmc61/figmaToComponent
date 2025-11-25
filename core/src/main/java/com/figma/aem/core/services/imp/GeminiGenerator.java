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
import java.util.stream.Collectors; // Added for stream operations
import java.util.Locale;           // Added for case operations

public class GeminiGenerator implements AIGenerator {

    private final String apiKey;
    // IMPORTANT: Use a stable model name like 'gemini-2.5-flash' or 'gemini-2.5-pro'
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

    public GeminiGenerator(String apiKey) {
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

    /**
     * Constructs a highly explicit and robust prompt for the Gemini model,
     * ensuring all required AEM component files, including the often-missed
     * .content.xml and _cq_dialog/.content.xml, are requested with accurate paths.
     *
     * @param componentName The name of the component (e.g., "Apple Hero").
     * @param prompt Additional user instructions.
     * @param figmaJson The design specification JSON.
     * @return The complete, formatted prompt string.
     */
    private String buildUserPrompt(String componentName, String prompt, String figmaJson) {
        // Sanitize component name for use in HTL file paths and ClientLib names (e.g., "Apple Hero" -> "apple-hero")
        String sanitizedName = componentName.toLowerCase(Locale.ROOT).replaceAll("\\s+", "-");
        
        // Base AEM path (e.g., "/apps/my-site/components/content/apple-hero")
        String componentPath = "/apps/my-site/components/content/" + sanitizedName;
        
        // Generate Model Class Name (e.g., "Apple Hero" -> "AppleHeroModel")
        String modelName = Arrays.stream(componentName.split("\\s+"))
            .filter(s -> !s.isEmpty())
            .map(s -> s.substring(0, 1).toUpperCase(Locale.ROOT) + s.substring(1))
            .collect(Collectors.joining()) + "Model";

        // The prompt uses placeholders (%1$s, %2$s, etc.) for dynamic injection of the variables
        return String.format(
                "You are a senior AEM full-stack developer. Your task is to generate a complete, production-ready AEM component based on the provided specifications.\n\n" +
                "Component Name: `%1$s`\n" +
                "Figma JSON Design Spec: \n%2$s\n\n" +
                "Additional User Prompt: \n%3$s\n\n" +
                "--- Component Generation Requirements ---\n" +
                "The generation MUST produce **all necessary AEM component files** in the standard structure, dynamically using the Component Name (`%1$s`) for filenames and paths.\n\n" +
                "**MANDATORY FILES LIST (Use these exact paths as JSON keys):**\n" +
                "1. Component Definition File (`.content.xml`):\n" +
                "   - KEY: `%4$s/.content.xml`\n" +
                "   - Define component Title (`%1$s`). Set `componentGroup` and `sling:resourceSuperType`.\n\n" +
                "2. Dialog Configuration (`_cq_dialog/.content.xml`):\n" +
                "   - KEY: `%4$s/_cq_dialog/.content.xml`\n" +
                "   - Use Granite UI tabs. Make **all** screenshot elements authorable.\n\n" +
                "3. HTL Template (`%5$s.html`):\n" +
                "   - KEY: `%4$s/%5$s.html`\n" +
                "   - Use clean, semantic HTL. All content must be dynamic (pulled from Sling Model).\n\n" +
                "4. Java Sling Model (`%6$s.java`):\n" +
                "   - KEY: `%6$s.java` (The resulting file should contain the full Java class definition for the model)\n" +
                "   - Map all dialog fields. Use `@Model` and `@Inject` (OSGi R6).\n\n" +
                "5. Client-Side Libraries (`clientlibs/.content.xml` and basic files):\n" +
                "   - KEY: `%4$s/clientlibs/.content.xml`\n" +
                "   - KEY: `%4$s/clientlibs/css/style.css`\n" +
                "   - KEY: `%4$s/clientlibs/css.txt`\n" +
                "   - Use BEM CSS. The ClientLib category MUST be unique and based on the component name.\n\n" +
                "--- Output Format Constraint ---\n" +
                "Your response MUST be a **JSON object**. The keys MUST be the **full, accurate file paths** (like the examples above). The values MUST be the full code content.\n\n" +
                "Respond with only the JSON object. Do not include explanation or Markdown formatting.",
                
                // 1. %1$s: componentName (used 3 times)
                componentName,
                // 2. %2$s: figmaJson
                figmaJson,
                // 3. %3$s: prompt
                prompt,
                // 4. %4$s: componentPath (used 5 times for XML/ClientLib paths)
                componentPath,
                // 5. %5$s: sanitizedName (used 2 times for HTL file name)
                sanitizedName,
                // 6. %6$s: modelName (used 2 times for Java file name)
                modelName
        );
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