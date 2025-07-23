package com.figma.aem.core.services.imp;

import com.figma.aem.core.services.AIGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;


import javax.servlet.http.Part;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ChatGPTGenerator implements AIGenerator {

    private final String apiKey;
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public ChatGPTGenerator(String apiKey) {
        // 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
        this.apiKey = "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";
    }

    @Override
    public Map<String, String> generateComponent(Part figmaFile, String componentName, String prompt) throws Exception {
        String figmaJson = extractFigmaJson(figmaFile);
        String userPrompt = buildUserPrompt(componentName, prompt, figmaJson);
        String responseContent = callOpenAI(userPrompt);
        return parseGeneratedCode(responseContent);
    }

    public String extractFigmaJson(Part figmaFile) throws Exception {
        try (InputStream inputStream = figmaFile.getInputStream()) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
    }

    public String buildUserPrompt(String componentName, String prompt, String figmaJson) {
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

    public String callOpenAI(String userPrompt) throws Exception {
        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("model", "gpt-3.5-turbo");
        requestJson.addProperty("temperature", 0.3);

        // Create messages array
        JsonArray messagesArray = new JsonArray();

        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", "You generate AEM component source files from design specs.");
        messagesArray.add(systemMessage);

        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", userPrompt);
        messagesArray.add(userMessage);

        // Add messages to the request JSON
        requestJson.add("messages", messagesArray);

        // Prepare connection
        HttpURLConnection connection = (HttpURLConnection) new URL(OPENAI_API_URL).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send request
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestJson.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int status = connection.getResponseCode();
        InputStream responseStream = (status == 200)
                ? connection.getInputStream()
                : connection.getErrorStream();

        String response = IOUtils.toString(responseStream, StandardCharsets.UTF_8);
        connection.disconnect();

        if (status != 200) {
            throw new RuntimeException("OpenAI API error: " + status + " - " + response);
        }

        // Parse the raw text content from OpenAI
        JsonObject responseJson = JsonParser.parseString(response).getAsJsonObject();

        JsonArray choices = responseJson.getAsJsonArray("choices");
        JsonObject message = choices
                .get(0)
                .getAsJsonObject()
                .getAsJsonObject("message");

        return message.get("content").getAsString().trim();
    }

    public Map<String, String> parseGeneratedCode(String responseText) {
        String cleaned = responseText;

        // Remove code block formatting if present
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        }
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }

        try {
            JsonObject json = JsonParser.parseString(cleaned).getAsJsonObject();

            Map<String, String> files = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                files.put(entry.getKey(), entry.getValue().getAsString());
            }
            return files;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse AI JSON output. Response:\n" + responseText + "\n\nError: " + e.getMessage());
        }
    }
}
