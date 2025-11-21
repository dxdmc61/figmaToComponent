package com.figma.aem.core.services.imp;

import com.figma.aem.core.services.AIGenerator;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray; // Import JSONArray
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
    // Note: The model name 'gemini-2.5-pro-exp-03-25' might be an experimental
    // or internal model. For stable use, 'gemini-pro' is generally recommended.
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-pro-exp-03-25:generateContent";

    public GeminiGenerator(String apiKey) {
        // IMPORTANT: For production, UNCOMMENT 'this.apiKey = apiKey;'
        // and remove the hardcoded placeholder.
        this.apiKey = "AIzaSyBCFfDYjzUKs3ZkCd1uT_35_CFYtjK2k9k"; // This is a placeholder key
        // this.apiKey = apiKey; // Use this line in production with a valid key
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
        // The requestJson construction block is still here, but effectively unused
        // because the 'response' string is hardcoded.
        JSONObject requestJson = new JSONObject();
        List<JSONObject> contents = new ArrayList<>();
        JSONObject partsObject = new JSONObject();
        partsObject.put("text", userPrompt);
        JSONObject contentObject = new JSONObject();
        JSONArray partsArray = new JSONArray();
        partsArray.put(partsObject);
        contentObject.put("parts", partsArray);
        contents.add(contentObject);
        requestJson.put("contents", contents);

        JSONObject generationConfig = new JSONObject();
        generationConfig.put("temperature", 0.3);
        requestJson.put("generationConfig", generationConfig);

        // --- START OF HARDCODED RESPONSE ---
        // The following section comments out the actual HTTP call and hardcodes the
        // response.
        // This is useful for testing the parsing logic or mocking the API.
        // REMEMBER TO UNCOMMENT THE HTTP CALLS AND REMOVE THIS HARDCODED STRING FOR
        // PRODUCTION!

        // int status = HttpURLConnection.HTTP_OK; // Assume OK status for hardcoded
        // response
        // InputStream responseStream = null; // No actual stream
        String response = "{\n" + //
                "  \".content.xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n" + //
                "<jcr:root xmlns:sling=\\\"http://sling.apache.org/jcr/sling/1.0\\\" xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\" xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\" xmlns:nt=\\\"http://www.jcp.org/jcr/nt/1.0\\\" jcr:primaryType=\\\"cq:Component\\\" jcr:title=\\\"Humana Hero\\\" componentGroup=\\\"Figma - Content\\\" sling:resourceType=\\\"figma/components/heroHumana\\\"/>\",\n"
                + //
                "  \"humana-hero.html\": \"<div data-sly-use.model=\\\"com.figma.aem.core.models.HumanaHeroModel\\\" class=\\\"humana-hero\\\"><div class=\\\"humana-hero__container\\\"><div class=\\\"humana-hero__text\\\"><p class=\\\"humana-hero__greeting\\\" data-sly-test=\\\"${model.greeting}\\\">${model.greeting}</p><h1 class=\\\"humana-hero__heading\\\" data-sly-test=\\\"${model.heading}\\\">${model.heading}</h1><div class=\\\"humana-hero__body\\\" data-sly-test=\\\"${model.bodyText}\\\">${model.bodyText @ context='html'}</div><div class=\\\"humana-hero__ctas\\\"><a data-sly-test=\\\"${model.primaryCtaLink}\\\" href=\\\"${model.primaryCtaLink @ extension='html'}\\\" class=\\\"humana-hero__cta humana-hero__cta--primary\\\"><span class=\\\"humana-hero__cta-text\\\">${model.primaryCtaText @ context='text'}</span><span class=\\\"humana-hero__cta-icon humana-hero__cta-icon--play\\\" aria-hidden=\\\"true\\\"></span></a><a data-sly-test=\\\"${model.secondaryCtaLink}\\\" href=\\\"${model.secondaryCtaLink @ extension='html'}\\\" class=\\\"humana-hero__cta humana-hero__cta--secondary\\\"><span class=\\\"humana-hero__cta-text\\\">${model.secondaryCtaText @ context='text'}</span><span class=\\\"humana-hero__cta-icon humana-hero__cta-icon--arrow\\\" aria-hidden=\\\"true\\\"></span></a></div></div><div class=\\\"humana-hero__visual\\\" data-sly-test=\\\"${model.deviceImage}\\\"><img class=\\\"humana-hero__image\\\" src=\\\"${model.deviceImage}\\\" alt=\\\"${model.imageAlt @ context='text'}\\\"/></div></div></div>\",\n"
                + //
                "  \"_cq_dialog/.content.xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n" + //
                "<jcr:root xmlns:sling=\\\"http://sling.apache.org/jcr/sling/1.0\\\" xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\" xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\" xmlns:nt=\\\"http://www.jcp.org/jcr/nt/1.0\\\" xmlns:granite=\\\"http://www.adobe.com/jcr/granite/1.0\\\" jcr:primaryType=\\\"nt:unstructured\\\" jcr:title=\\\"Humana Hero Configuration\\\" sling:resourceType=\\\"cq/gui/components/authoring/dialog\\\"><content jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\"><items jcr:primaryType=\\\"nt:unstructured\\\"><tabs jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/tabs\\\" maximized=\\\"true\\\"><items jcr:primaryType=\\\"nt:unstructured\\\"><contentTab jcr:primaryType=\\\"nt:unstructured\\\" jcr:title=\\\"Content\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\"><items jcr:primaryType=\\\"nt:unstructured\\\"><greeting jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\" fieldLabel=\\\"Greeting\\\" name=\\\"./greeting\\\"/><heading jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\" fieldLabel=\\\"Heading\\\" name=\\\"./heading\\\" required=\\\"true\\\"/><bodyText jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"cq/gui/components/authoring/dialog/richtext\\\" fieldLabel=\\\"Body Text\\\" name=\\\"./bodyText\\\" useFixedInlineToolbar=\\\"true\\\"/></items></contentTab><ctaTab jcr:primaryType=\\\"nt:unstructured\\\" jcr:title=\\\"CTA\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\"><items jcr:primaryType=\\\"nt:unstructured\\\"><primaryFieldset jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/form/fieldset\\\" fieldLabel=\\\"Primary CTA\\\"><items jcr:primaryType=\\\"nt:unstructured\\\"><primaryCtaText jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\" fieldLabel=\\\"Text\\\" name=\\\"./primaryCtaText\\\" value=\\\"Play Video\\\"/><primaryCtaLink jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/form/pathfield\\\" fieldLabel=\\\"Link\\\" name=\\\"./primaryCtaLink\\\" rootPath=\\\"/content\\\"/></items></primaryFieldset><secondaryFieldset jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/form/fieldset\\\" fieldLabel=\\\"Secondary CTA\\\"><items jcr:primaryType=\\\"nt:unstructured\\\"><secondaryCtaText jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\" fieldLabel=\\\"Text\\\" name=\\\"./secondaryCtaText\\\" value=\\\"Get Started\\\"/><secondaryCtaLink jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/form/pathfield\\\" fieldLabel=\\\"Link\\\" name=\\\"./secondaryCtaLink\\\" rootPath=\\\"/content\\\"/></items></secondaryFieldset></items></ctaTab><mediaTab jcr:primaryType=\\\"nt:unstructured\\\" jcr:title=\\\"Media\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\"><items jcr:primaryType=\\\"nt:unstructured\\\"><deviceImage jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/form/pathfield\\\" fieldLabel=\\\"Device Image\\\" name=\\\"./deviceImage\\\" rootPath=\\\"/content/dam\\\" selectionService=\\\"granite.omniadmin.content\\\"/><imageAlt jcr:primaryType=\\\"nt:unstructured\\\" sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\" fieldLabel=\\\"Image Alt Text\\\" name=\\\"./imageAlt\\\"/></items></mediaTab></items></tabs></items></content></jcr:root>\",\n"
                + //
                "  \"HumanaHeroModel.java\": \"package com.figma.aem.core.models; import org.apache.sling.api.SlingHttpServletRequest; import org.apache.sling.api.resource.Resource; import org.apache.sling.models.annotations.DefaultInjectionStrategy; import org.apache.sling.models.annotations.Model; import org.apache.sling.models.annotations.injectorspecific.ChildResource; import org.apache.sling.models.annotations.injectorspecific.Self; import org.apache.sling.models.annotations.injectorspecific.ValueMapValue; import javax.annotation.PostConstruct; @Model(adaptables={SlingHttpServletRequest.class, Resource.class}, resourceType=HumanaHeroModel.RESOURCE_TYPE, defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL) public class HumanaHeroModel { public static final String RESOURCE_TYPE = \\\"figma/components/heroHumana\\\"; @Self private SlingHttpServletRequest request; @ValueMapValue private String greeting; @ValueMapValue private String heading; @ValueMapValue private String bodyText; @ValueMapValue private String primaryCtaText; @ValueMapValue private String primaryCtaLink; @ValueMapValue private String secondaryCtaText; @ValueMapValue private String secondaryCtaLink; @ValueMapValue private String deviceImage; @ValueMapValue private String imageAlt; private boolean hasContent; @PostConstruct protected void init() { this.hasContent = (heading != null && !heading.isEmpty()) || (bodyText != null && !bodyText.isEmpty()) || (deviceImage != null); } public String getGreeting(){return greeting;} public String getHeading(){return heading;} public String getBodyText(){return bodyText;} public String getPrimaryCtaText(){return primaryCtaText != null ? primaryCtaText : \\\"Play Video\\\";} public String getPrimaryCtaLink(){return primaryCtaLink;} public String getSecondaryCtaText(){return secondaryCtaText != null ? secondaryCtaText : \\\"Get Started\\\";} public String getSecondaryCtaLink(){return secondaryCtaLink;} public String getDeviceImage(){ return deviceImage ; } public String getImageAlt(){return imageAlt != null ? imageAlt : \\\"Hero visual\\\";} public boolean getHasContent(){return hasContent;} }\",\n"
                + //
                "  \"clientlibs/humana-hero/.content.xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><jcr:root xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\" xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\" jcr:primaryType=\\\"cq:ClientLibraryFolder\\\" allowProxy=\\\"true\\\" categories=\\\"[figma.humana-hero]\\\" cssProcessor=\\\"[default:none, minify:true]\\\" jsProcessor=\\\"[default:none, minify:true]\\\"/>\",\n"
                + //
                "  \"css.txt\": \"humana-hero.css\",\n" + //
                "  \"humana-hero.css\": \".humana-hero { background-color:#fff; padding:2rem 1rem; } .humana-hero__container { max-width:1200px; margin:0 auto; display:flex; flex-wrap:wrap; align-items:center; gap:1.5rem; } .humana-hero__text { flex:1 1 50%; } .humana-hero__greeting { color:#2f9e1f; font-weight:700; margin-bottom:.5rem; } .humana-hero__heading { font-size:2.25rem; margin-bottom:1rem; color:#0b4f1b; } .humana-hero__body { color:#374047; margin-bottom:1.25rem; } .humana-hero__ctas { display:flex; gap:1rem; flex-wrap:wrap; } .humana-hero__cta { display:inline-flex; align-items:center; padding:.75rem 1.25rem; border-radius:6px; text-decoration:none; font-weight:600; } .humana-hero__cta--primary { background-color:#2f9e1f; color:#fff; border:2px solid #2f9e1f; } .humana-hero__cta--secondary { background-color:transparent; color:#2f9e1f; border:2px solid #2f9e1f; } .humana-hero__cta-icon { margin-left:.5rem; } .humana-hero__visual { flex:1 1 40%; display:flex; justify-content:center; } .humana-hero__image { max-width:100%; height:auto; } @media(max-width:768px){ .humana-hero__container{flex-direction:column;} .humana-hero__heading{font-size:1.75rem;} }\",\n"
                + //
                "  \"clientlibs/humana-hero/js.txt\": \"humana-hero.js\",\n" + //
                "  \"humana-hero.js\": \"(function(){'use strict';document.addEventListener('DOMContentLoaded',function(){document.querySelectorAll('.humana-hero__image').forEach(function(img){if(img.complete){img.classList.add('humana-hero__image--loaded');}else{img.addEventListener('load',function(){img.classList.add('humana-hero__image--loaded');});}});});})();\"\n"
                + //
                "}\n";
        // Original HTTP connection code - commented out
        /*
         * URL url = new URL(GEMINI_API_URL + "?key=" + apiKey);
         * HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         * connection.setRequestMethod("POST");
         * connection.setRequestProperty("Content-Type", "application/json");
         * connection.setDoOutput(true);
         * 
         * try (OutputStream os = connection.getOutputStream()) {
         * byte[] input = requestJson.toString().getBytes(StandardCharsets.UTF_8);
         * os.write(input, 0, input.length);
         * }
         * 
         * int status = connection.getResponseCode();
         * InputStream responseStream = (status == HttpURLConnection.HTTP_OK)
         * ? connection.getInputStream()
         * : connection.getErrorStream();
         * 
         * String response = IOUtils.toString(responseStream, StandardCharsets.UTF_8);
         */

        // Mimic connection disconnection for completeness if it were active
        // connection.disconnect(); // This line should be here if the connection was
        // active

        // For hardcoded response, assume success (HTTP_OK)
        int status = HttpURLConnection.HTTP_OK;

        if (status != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Gemini API error: " + status + " - " + response);
        }

        // Parse the raw text content from Gemini
        JSONObject responseJson = new JSONObject(new JSONTokener(response)); // Parse the hardcoded string

        // Navigate through the JSON structure to get the generated text
        // Note: The hardcoded 'response' string is already the final JSON object,
        // not a Gemini API response wrapper with 'candidates' and 'parts'.
        // So, the parsing logic below needs to be adjusted or you can
        // directly return the 'response' as it's already the expected format.
        // For now, I'll keep the existing parsing logic and wrap the hardcoded response
        // to match the expected Gemini API format, so that the existing parsing
        // logic (candidates -> content -> parts -> text) works.

        // Re-wrapping the hardcoded response to fit the expected Gemini API structure
        // for parsing.
        // In a real scenario, you would just parse the actual Gemini response here.
        JSONObject mockGeminiResponse = new JSONObject();
        JSONArray mockCandidates = new JSONArray();
        JSONObject mockCandidate = new JSONObject();
        JSONObject mockContent = new JSONObject();
        JSONArray mockParts = new JSONArray();
        JSONObject mockPart = new JSONObject();

        // The hardcoded 'response' string is the direct JSON output, so put it here.
        // This is a common pattern: the AI generates code (JSON) inside a 'text' field.
        mockPart.put("text", response);
        mockParts.put(mockPart);
        mockContent.put("parts", mockParts);
        mockCandidate.put("content", mockContent);
        mockCandidates.put(mockCandidate);
        mockGeminiResponse.put("candidates", mockCandidates);

        // Now parse the mock Gemini response
        responseJson = mockGeminiResponse;

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
        throw new RuntimeException("Failed to extract content from Gemini API response: " + response);
    }

    private Map<String, String> parseGeneratedCode(String responseText) {
        String cleaned = responseText;

        // Remove code block formatting if present
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        } else if (cleaned.startsWith("```json\n")) { // Handle newline after ```json
            cleaned = cleaned.substring(8);
        }
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }

        try {
            JSONObject json = new JSONObject(new JSONTokener(cleaned));
            Map<String, String> files = new HashMap<>();
            for (String key : json.keySet()) {
                files.put(key, json.getString(key));
            }
            return files;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse AI JSON output. Response:\n" + responseText + "\n\nError: " + e.getMessage());
        }
    }
}