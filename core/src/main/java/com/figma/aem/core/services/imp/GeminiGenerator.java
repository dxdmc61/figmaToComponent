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
                        "<jcr:root xmlns:sling=\\\"http://sling.apache.org/jcr/sling/1.0\\\" xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\"\\n" + //
                        "    xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\"\\n" + //
                        "    jcr:primaryType=\\\"cq:Component\\\"\\n" + //
                        "    jcr:title=\\\"kohler Hero\\\"\\n" + //
                        "    componentGroup=\\\"Figma - Content\\\"/>\\n" + //
                        "\",\n" + //
                        "  \"_cq_editConfig.xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n" + //
                        "<jcr:root xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\" xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\"\\n" + //
                        "    jcr:primaryType=\\\"cq:EditConfig\\\">\\n" + //
                        "    <cq:listeners\\n" + //
                        "        jcr:primaryType=\\\"cq:Listeners\\\"\\n" + //
                        "        afterdelete=\\\"REFRESH_PAGE\\\"\\n" + //
                        "        afteredit=\\\"REFRESH_PAGE\\\"\\n" + //
                        "        afterinsert=\\\"REFRESH_PAGE\\\"/>\\n" + //
                        "</jcr:root>\\n" + //
                        "\",\n" + //
                        "  \"_cq_dialog/.content.xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n" + //
                        "<jcr:root xmlns:sling=\\\"http://sling.apache.org/jcr/sling/1.0\\\" xmlns:granite=\\\"http://www.adobe.com/jcr/granite/1.0\\\" xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\" xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\" xmlns:nt=\\\"http://www.jcp.org/jcr/nt/1.0\\\"\\n" + //
                        "    jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "    jcr:title=\\\"kohler Hero\\\"\\n" + //
                        "    sling:resourceType=\\\"cq/gui/components/authoring/dialog\\\">\\n" + //
                        "    <content\\n" + //
                        "        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "        sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\">\\n" + //
                        "        <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "            <tabs\\n" + //
                        "                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                sling:resourceType=\\\"granite/ui/components/coral/foundation/tabs\\\"\\n" + //
                        "                maximized=\\\"true\\\">\\n" + //
                        "                <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                    <properties\\n" + //
                        "                        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                        jcr:title=\\\"Properties\\\"\\n" + //
                        "                        sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\"\\n" + //
                        "                        margin=\\\"true\\\">\\n" + //
                        "                        <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                            <columns\\n" + //
                        "                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                sling:resourceType=\\\"granite/ui/components/coral/foundation/fixedcolumns\\\"\\n" + //
                        "                                margin=\\\"true\\\">\\n" + //
                        "                                <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                                    <column\\n" + //
                        "                                        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                        sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\">\\n" + //
                        "                                        <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                                            <videoUrl\\n" + //
                        "                                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                                fieldLabel=\\\"Background Video URL\\\"\\n" + //
                        "                                                name=\\\"./videoUrl\\\"\\n" + //
                        "                                                emptyText=\\\"Enter video URL (e.g., /content/dam/figma/hero-video.mp4)\\\"/>\\n" + //
                        "                                            <subtitle\\n" + //
                        "                                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                                fieldLabel=\\\"Subtitle\\\"\\n" + //
                        "                                                name=\\\"./subtitle\\\"\\n" + //
                        "                                                emptyText=\\\"Enter subtitle\\\"/>\\n" + //
                        "                                            <title\\n" + //
                        "                                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                                fieldLabel=\\\"Title\\\"\\n" + //
                        "                                                name=\\\"./title\\\"\\n" + //
                        "                                                emptyText=\\\"Enter title\\\"/>\\n" + //
                        "                                            <description\\n" + //
                        "                                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                sling:resourceType=\\\"cq/gui/components/authoring/dialog/richtext\\\"\\n" + //
                        "                                                fieldLabel=\\\"Description\\\"\\n" + //
                        "                                                name=\\\"./description\\\"\\n" + //
                        "                                                use=\\\"true\\\"/>\\n" + //
                        "                                            <cta1Label\\n" + //
                        "                                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                                fieldLabel=\\\"CTA 1 Label\\\"\\n" + //
                        "                                                name=\\\"./cta1Label\\\"\\n" + //
                        "                                                emptyText=\\\"Explore Cast Iron\\\"/>\\n" + //
                        "                                            <cta1Url\\n" + //
                        "                                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                sling:resourceType=\\\"granite/ui/components/coral/foundation/form/pathfield\\\"\\n" + //
                        "                                                fieldLabel=\\\"CTA 1 URL\\\"\\n" + //
                        "                                                name=\\\"./cta1Url\\\"\\n" + //
                        "                                                rootPath=\\\"/content\\\"\\n" + //
                        "                                                emptyText=\\\"/content/mysite/en/cast-iron\\\"/>\\n" + //
                        "                                            <cta2Label\\n" + //
                        "                                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                                fieldLabel=\\\"CTA 2 Label\\\"\\n" + //
                        "                                                name=\\\"./cta2Label\\\"\\n" + //
                        "                                                emptyText=\\\"Shop All Cast Iron\\\"/>\\n" + //
                        "                                            <cta2Url\\n" + //
                        "                                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                sling:resourceType=\\\"granite/ui/components/coral/foundation/form/pathfield\\\"\\n" + //
                        "                                                fieldLabel=\\\"CTA 2 URL\\\"\\n" + //
                        "                                                name=\\\"./cta2Url\\\"\\n" + //
                        "                                                rootPath=\\\"/content\\\"\\n" + //
                        "                                                emptyText=\\\"/content/mysite/en/shop/cast-iron\\\"/>\\n" + //
                        "                                        </items>\\n" + //
                        "                                    </column>\\n" + //
                        "                                </items>\\n" + //
                        "                            </columns>\\n" + //
                        "                        </items>\\n" + //
                        "                    </properties>\\n" + //
                        "                </items>\\n" + //
                        "            </tabs>\\n" + //
                        "        </items>\\n" + //
                        "    </content>\\n" + //
                        "</jcr:root>\\n" + //
                        "\",\n" + //
                        "  \"KohlerHeroModel.java\": \"package com.figma.aem.core.models;\\n" + //
                        "\\n" + //
                        "import com.adobe.cq.wcm.core.components.models.Image;\\n" + //
                        "import org.apache.sling.api.SlingHttpServletRequest;\\n" + //
                        "import org.apache.sling.api.resource.Resource;\\n" + //
                        "import org.apache.sling.models.annotations.DefaultInjectionStrategy;\\n" + //
                        "import org.apache.sling.models.annotations.Model;\\n" + //
                        "import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;\\n" + //
                        "\\n" + //
                        "@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, resourceType = \\\"figma/components/kohlerhero\\\", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)\\n" + //
                        "public class KohlerHeroModel {\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String videoUrl;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String title;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String subtitle;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String description;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String cta1Label;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String cta1Url;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String cta2Label;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String cta2Url;\\n" + //
                        "\\n" + //
                        "    public String getVideoUrl() {\\n" + //
                        "        return videoUrl;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getTitle() {\\n" + //
                        "        return title;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getSubtitle() {\\n" + //
                        "        return subtitle;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getDescription() {\\n" + //
                        "        return description;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getCta1Label() {\\n" + //
                        "        return cta1Label;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getCta1Url() {\\n" + //
                        "        return cta1Url;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getCta2Label() {\\n" + //
                        "        return cta2Label;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getCta2Url() {\\n" + //
                        "        return cta2Url;\\n" + //
                        "    }\\n" + //
                        "}\\n" + //
                        "\",\n" + //
                        "  \"kohlerHero.html\": \"<div data-sly-use.model=\\\"com.figma.aem.core.models.KohlerHeroModel\\\" data-sly-test.hasContent=\\\"${model.title || model.subtitle || model.description || model.cta1Label}\\\" class=\\\"kohler-hero\\\">\\n" + //
                        "    <div class=\\\"kohler-hero__background-video\\\" data-sly-test=\\\"${model.videoUrl}\\\">\\n" + //
                        "        <video class=\\\"kohler-hero__video\\\" autoplay muted loop playsinline>\\n" + //
                        "            <source data-sly-attribute.src=\\\"${model.videoUrl}\\\" type=\\\"video/mp4\\\">\\n" + //
                        "            Your browser does not support the video tag.\\n" + //
                        "        </video>\\n" + //
                        "    </div>\\n" + //
                        "    <div class=\\\"kohler-hero__content-wrapper\\\">\\n" + //
                        "        <div class=\\\"kohler-hero__content\\\">\\n" + //
                        "            <h1 data-sly-test=\\\"${model.title}\\\" class=\\\"kohler-hero__title\\\">${model.title}</h1>\\n" + //
                        "            <p data-sly-test=\\\"${model.description}\\\" class=\\\"kohler-hero__description\\\">${model.description @ context='html'}</p>\\n" + //
                        "            <h2 data-sly-test=\\\"${model.subtitle}\\\" class=\\\"kohler-hero__subtitle\\\">${model.subtitle}</h2>\\n" + //
                        "            <div class=\\\"kohler-hero__cta-container\\\">\\n" + //
                        "                <a data-sly-test=\\\"${model.cta1Label && model.cta1Url}\\\" href=\\\"${model.cta1Url}\\\" class=\\\"kohler-hero__cta-link kohler-hero__cta-link--primary\\\">${model.cta1Label}</a>\\n" + //
                        "                <a data-sly-test=\\\"${model.cta2Label && model.cta2Url}\\\" href=\\\"${model.cta2Url}\\\" class=\\\"kohler-hero__cta-link kohler-hero__cta-link--secondary\\\">${model.cta2Label}</a>\\n" + //
                        "            </div>\\n" + //
                        "        </div>\\n" + //
                        "    </div>\\n" + //
                        "</div>\\n" + //
                        "\",\n" + //
                        "  \"clientlibs/kohlerhero/css/style.css\": \".kohler-hero {\\n" + //
                        "  position: relative;\\n" + //
                        "  width: 100%;\\n" + //
                        "  height: 100vh;\\n" + //
                        "  display: flex;\\n" + //
                        "  align-items: center;\\n" + //
                        "  justify-content: center;\\n" + //
                        "  color: #fff;\\n" + //
                        "  text-align: center;\\n" + //
                        "  overflow: hidden;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".kohler-hero__background-video {\\n" + //
                        "  position: absolute;\\n" + //
                        "  top: 0;\\n" + //
                        "  left: 0;\\n" + //
                        "  width: 100%;\\n" + //
                        "  height: 100%;\\n" + //
                        "  overflow: hidden;\\n" + //
                        "  z-index: -1;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".kohler-hero__video {\\n" + //
                        "  width: 100%;\\n" + //
                        "  height: 100%;\\n" + //
                        "  object-fit: cover;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".kohler-hero__content-wrapper {\\n" + //
                        "  position: relative;\\n" + //
                        "  z-index: 1;\\n" + //
                        "  max-width: 800px;\\n" + //
                        "  padding: 2rem;\\n" + //
                        "  background: rgba(0, 0, 0, 0.4);\\n" + //
                        "  border-radius: 8px;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".kohler-hero__content {\\n" + //
                        "  display: flex;\\n" + //
                        "  flex-direction: column;\\n" + //
                        "  align-items: center;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".kohler-hero__subtitle {\\n" + //
                        "  font-size: 1.5rem;\\n" + //
                        "  font-weight: 300;\\n" + //
                        "  margin-bottom: 0.5rem;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".kohler-hero__title {\\n" + //
                        "  font-size: 3.5rem;\\n" + //
                        "  font-weight: 700;\\n" + //
                        "  margin-bottom: 1rem;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".kohler-hero__description {\\n" + //
                        "  font-size: 1rem;\\n" + //
                        "  font-weight: 400;\\n" + //
                        "  line-height: 1.5;\\n" + //
                        "  margin-bottom: 2rem;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".kohler-hero__cta-container {\\n" + //
                        "  display: flex;\\n" + //
                        "  gap: 1rem;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".kohler-hero__cta-link {\\n" + //
                        "  padding: 0.75rem 1.5rem;\\n" + //
                        "  border: 1px solid #fff;\\n" + //
                        "  text-decoration: none;\\n" + //
                        "  color: #fff;\\n" + //
                        "  transition: background-color 0.3s, color 0.3s;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".kohler-hero__cta-link:hover {\\n" + //
                        "  background-color: #fff;\\n" + //
                        "  color: #000;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        "@media (max-width: 768px) {\\n" + //
                        "  .kohler-hero__title {\\n" + //
                        "    font-size: 2.5rem;\\n" + //
                        "  }\\n" + //
                        "  .kohler-hero__cta-container {\\n" + //
                        "    flex-direction: column;\\n" + //
                        "  }\\n" + //
                        "}\\n" + //
                        "\",\n" + //
                        "  \"clientlibs/kohlerhero/css.txt\": \"style.css\\n" + //
                        "\",\n" + //
                        "  \"clientlibs/kohlerhero/js.txt\": \"\",\n" + //
                        "  \"clientlibs/kohlerhero/.content.xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n" + //
                        "<jcr:root xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\" xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\"\\n" + //
                        "    jcr:primaryType=\\\"cq:ClientLibraryFolder\\\"\\n" + //
                        "    categories=\\\"[figma.kohlerhero]\\\"/>\\n" + //
                        "\"\n" + //
                        "}";

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