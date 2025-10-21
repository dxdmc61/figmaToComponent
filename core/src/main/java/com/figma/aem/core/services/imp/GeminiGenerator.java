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
                        "  \"_content.xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n" + //
                        "<jcr:root xmlns:sling=\\\"http://sling.apache.org/jcr/sling/1.0\\\" xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\" xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\"\\n" + //
                        "    jcr:primaryType=\\\"cq:Component\\\"\\n" + //
                        "    jcr:title=\\\"Hero Jumbotron\\\"\\n" + //
                        "    componentGroup=\\\"My Project - Content\\\"\\n" + //
                        "    sling:resourceSuperType=\\\"core/wcm/components/container/v1/container\\\"/>\\n" + //
                        "\",\n" + //
                        "  \"hero-jumbotron.html\": \"<div data-sly-use.component=\\\"com.myproject.core.models.HeroJumbotronModel\\\" data-sly-use.templates=\\\"/libs/wcm/foundation/components/experiencefragment/template.html\\\" data-sly-unwrap>\\n" + //
                        "    <div class=\\\"hero-jumbotron\\\" data-sly-test.hasContent=\\\"${component.hasContent}\\\">\\n" + //
                        "        <div class=\\\"hero-jumbotron__content-wrapper\\\">\\n" + //
                        "            <div class=\\\"hero-jumbotron__text-content\\\">\\n" + //
                        "                \\n" + //
                        "                <p class=\\\"hero-jumbotron__greeting\\\" data-sly-test=\\\"${component.greeting}\\\">${component.greeting}</p>\\n" + //
                        "\\n" + //
                        "                \\n" + //
                        "                <h1 class=\\\"hero-jumbotron__heading\\\" data-sly-test=\\\"${component.heading}\\\">${component.heading}</h1>\\n" + //
                        "\\n" + //
                        "                \\n" + //
                        "                <div class=\\\"hero-jumbotron__body-text\\\" data-sly-test=\\\"${component.bodyText}\\\">\\n" + //
                        "                    ${component.bodyText @ context='html'}\\n" + //
                        "                </div>\\n" + //
                        "\\n" + //
                        "                \\n" + //
                        "                <div class=\\\"hero-jumbotron__ctas\\\">\\n" + //
                        "                    \\n" + //
                        "                    <a data-sly-test=\\\"${component.primaryCtaLink}\\\"\\n" + //
                        "                       href=\\\"${component.primaryCtaLink @ extension='html'}\\\"\\n" + //
                        "                       class=\\\"hero-jumbotron__cta hero-jumbotron__cta--primary\\\">\\n" + //
                        "                        <span class=\\\"hero-jumbotron__cta-icon hero-jumbotron__cta-icon--play\\\"></span>\\n" + //
                        "                        <span>${component.primaryCtaText @ context='text'}</span>\\n" + //
                        "                    </a>\\n" + //
                        "\\n" + //
                        "                    \\n" + //
                        "                    <a data-sly-test=\\\"${component.secondaryCtaLink}\\\"\\n" + //
                        "                       href=\\\"${component.secondaryCtaLink @ extension='html'}\\\"\\n" + //
                        "                       class=\\\"hero-jumbotron__cta hero-jumbotron__cta--secondary\\\">\\n" + //
                        "                        <span>${component.secondaryCtaText @ context='text'}</span>\\n" + //
                        "                        <span class=\\\"hero-jumbotron__cta-icon hero-jumbotron__cta-icon--arrow\\\"></span>\\n" + //
                        "                    </a>\\n" + //
                        "                </div>\\n" + //
                        "            </div>\\n" + //
                        "\\n" + //
                        "            \\n" + //
                        "            <div class=\\\"hero-jumbotron__visual\\\" data-sly-test=\\\"${component.deviceImage}\\\">\\n" + //
                        "                <img src=\\\"${component.deviceImage}\\\" alt=\\\"Visual representation of the content on various devices\\\" class=\\\"hero-jumbotron__image\\\"/>\\n" + //
                        "            </div>\\n" + //
                        "        </div>\\n" + //
                        "    </div>\\n" + //
                        "    <div data-sly-call=\\\"${templates.placeholder @ isEmpty=!hasContent, classAppend='hero-jumbotron'}\\\" data-sly-unwrap></div>\\n" + //
                        "</div>\\n" + //
                        "\",\n" + //
                        "  \"dialog.xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n" + //
                        "<jcr:root xmlns:sling=\\\"http://sling.apache.org/jcr/sling/1.0\\\" xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\" xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\" xmlns:nt=\\\"http://www.jcp.org/jcr/nt/1.0\\\" xmlns:granite=\\\"http://www.adobe.com/jcr/granite/1.0\\\"\\n" + //
                        "    jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "    jcr:title=\\\"Hero Jumbotron Configuration\\\"\\n" + //
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
                        "                    <text\\n" + //
                        "                        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                        jcr:title=\\\"Text Content\\\"\\n" + //
                        "                        sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\">\\n" + //
                        "                        <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                            <greeting\\n" + //
                        "                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                fieldLabel=\\\"Greeting Text (e.g., Good afternoon)\\\"\\n" + //
                        "                                name=\\\"./greeting\\\"\\n" + //
                        "                                required=\\\"false\\\"/>\\n" + //
                        "                            <heading\\n" + //
                        "                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                fieldLabel=\\\"Main Heading (H1)\\\"\\n" + //
                        "                                name=\\\"./heading\\\"\\n" + //
                        "                                required=\\\"true\\\"/>\\n" + //
                        "                            <bodyText\\n" + //
                        "                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                sling:resourceType=\\\"cq/gui/components/authoring/dialog/richtext\\\"\\n" + //
                        "                                fieldLabel=\\\"Body Text/Purpose\\\"\\n" + //
                        "                                name=\\\"./bodyText\\\"\\n" + //
                        "                                use=\\\"content.xml\\\"\\n" + //
                        "                                required=\\\"true\\\"/>\\n" + //
                        "                        </items>\\n" + //
                        "                    </text>\\n" + //
                        "                    <cta\\n" + //
                        "                        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                        jcr:title=\\\"Call to Actions\\\"\\n" + //
                        "                        sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\">\\n" + //
                        "                        <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                            <primaryCtaFieldSet\\n" + //
                        "                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                sling:resourceType=\\\"granite/ui/components/coral/foundation/form/fieldset\\\"\\n" + //
                        "                                fieldLabel=\\\"Primary CTA (Video Link)\\\">\\n" + //
                        "                                <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                                    <primaryCtaText\\n" + //
                        "                                        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                        sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                        fieldLabel=\\\"Primary CTA Button Text\\\"\\n" + //
                        "                                        name=\\\"./primaryCtaText\\\"\\n" + //
                        "                                        value=\\\"Play Video\\\"\\n" + //
                        "                                        required=\\\"true\\\"/>\\n" + //
                        "                                    <primaryCtaLink\\n" + //
                        "                                        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                        sling:resourceType=\\\"granite/ui/components/coral/foundation/form/pathfield\\\"\\n" + //
                        "                                        fieldLabel=\\\"Primary CTA Link/Video Path\\\"\\n" + //
                        "                                        name=\\\"./primaryCtaLink\\\"\\n" + //
                        "                                        rootPath=\\\"/content\\\"\\n" + //
                        "                                        required=\\\"true\\\"/>\\n" + //
                        "                                </items>\\n" + //
                        "                            </primaryCtaFieldSet>\\n" + //
                        "                            <secondaryCtaFieldSet\\n" + //
                        "                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                sling:resourceType=\\\"granite/ui/components/coral/foundation/form/fieldset\\\"\\n" + //
                        "                                fieldLabel=\\\"Secondary CTA (Get Started Link)\\\">\\n" + //
                        "                                <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                                    <secondaryCtaText\\n" + //
                        "                                        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                        sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                        fieldLabel=\\\"Secondary CTA Button Text\\\"\\n" + //
                        "                                        name=\\\"./secondaryCtaText\\\"\\n" + //
                        "                                        value=\\\"Get Started\\\"\\n" + //
                        "                                        required=\\\"true\\\"/>\\n" + //
                        "                                    <secondaryCtaLink\\n" + //
                        "                                        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                        sling:resourceType=\\\"granite/ui/components/coral/foundation/form/pathfield\\\"\\n" + //
                        "                                        fieldLabel=\\\"Secondary CTA Link Path\\\"\\n" + //
                        "                                        name=\\\"./secondaryCtaLink\\\"\\n" + //
                        "                                        rootPath=\\\"/content\\\"\\n" + //
                        "                                        required=\\\"true\\\"/>\\n" + //
                        "                                </items>\\n" + //
                        "                            </secondaryCtaFieldSet>\\n" + //
                        "                        </items>\\n" + //
                        "                    </cta>\\n" + //
                        "                    <image\\n" + //
                        "                        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                        jcr:title=\\\"Visual\\\"\\n" + //
                        "                        sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\">\\n" + //
                        "                        <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                            <deviceImage\\n" + //
                        "                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                sling:resourceType=\\\"cq/gui/components/authoring/dialog/fileupload\\\"\\n" + //
                        "                                autoStart=\\\"false\\\"\\n" + //
                        "                                class=\\\"cq-droptarget\\\"\\n" + //
                        "                                fieldLabel=\\\"Device Visual Image\\\"\\n" + //
                        "                                fileNameParameter=\\\"./deviceImage/fileName\\\"\\n" + //
                        "                                fileReferenceParameter=\\\"./deviceImage/fileReference\\\"\\n" + //
                        "                                mimeTypes=\\\"[image/gif,image/jpeg,image/png,image/webp]\\\"\\n" + //
                        "                                name=\\\"./deviceImage/file\\\"\\n" + //
                        "                                uploadUrl=\\\"${sling:resourcePath}\\\"/>\\n" + //
                        "                        </items>\\n" + //
                        "                    </image>\\n" + //
                        "                </items>\\n" + //
                        "            </tabs>\\n" + //
                        "        </items>\\n" + //
                        "    </content>\\n" + //
                        "</jcr:root>\\n" + //
                        "\",\n" + //
                        "  \"HeroJumbotronModel.java\": \"package com.myproject.core.models;\\n" + //
                        "\\n" + //
                        "import org.apache.sling.api.SlingHttpServletRequest;\\n" + //
                        "import org.apache.sling.api.resource.Resource;\\n" + //
                        "import org.apache.sling.models.annotations.DefaultInjectionStrategy;\\n" + //
                        "import org.apache.sling.models.annotations.Model;\\n" + //
                        "import org.apache.sling.models.annotations.injectorspecific.Self;\\n" + //
                        "import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;\\n" + //
                        "import org.osgi.annotation.versioning.ProviderType;\\n" + //
                        "\\n" + //
                        "import javax.annotation.PostConstruct;\\n" + //
                        "\\n" + //
                        "@ProviderType\\n" + //
                        "@Model(\\n" + //
                        "    adaptables = {SlingHttpServletRequest.class, Resource.class},\\n" + //
                        "    resourceType = HeroJumbotronModel.RESOURCE_TYPE,\\n" + //
                        "    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL\\n" + //
                        ")\\n" + //
                        "public class HeroJumbotronModel {\\n" + //
                        "\\n" + //
                        "    static final String RESOURCE_TYPE = \\\"my-project/components/content/hero-jumbotron\\\";\\n" + //
                        "\\n" + //
                        "    @Self\\n" + //
                        "    private SlingHttpServletRequest request;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String greeting;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String heading;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String bodyText;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String primaryCtaText;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String primaryCtaLink;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String secondaryCtaText;\\n" + //
                        "\\n" + //
                        "    @ValueMapValue\\n" + //
                        "    private String secondaryCtaLink;\\n" + //
                        "\\n" + //
                        "    // Holds the path to the device image file reference\\n" + //
                        "    private String deviceImage;\\n" + //
                        "\\n" + //
                        "    private boolean hasContent;\\n" + //
                        "\\n" + //
                        "    @PostConstruct\\n" + //
                        "    protected void init() {\\n" + //
                        "        // Get the image reference from the child resource \\\"deviceImage\\\" created by fileupload\\n" + //
                        "        Resource imageResource = request.getResource().getChild(\\\"deviceImage\\\");\\n" + //
                        "        if (imageResource != null) {\\n" + //
                        "            this.deviceImage = imageResource.getValueMap().get(\\\"fileReference\\\", String.class);\\n" + //
                        "        }\\n" + //
                        "\\n" + //
                        "        // Determine if the component has enough content to be rendered\\n" + //
                        "        this.hasContent = heading != null || bodyText != null || primaryCtaLink != null || deviceImage != null;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getGreeting() {\\n" + //
                        "        return greeting;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getHeading() {\\n" + //
                        "        return heading;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getBodyText() {\\n" + //
                        "        return bodyText;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getPrimaryCtaText() {\\n" + //
                        "        // Fallback for button text if not configured\\n" + //
                        "        return primaryCtaText != null ? primaryCtaText : \\\"Play Video\\\";\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getPrimaryCtaLink() {\\n" + //
                        "        return primaryCtaLink;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getSecondaryCtaText() {\\n" + //
                        "        // Fallback for button text if not configured\\n" + //
                        "        return secondaryCtaText != null ? secondaryCtaText : \\\"Get Started\\\";\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getSecondaryCtaLink() {\\n" + //
                        "        return secondaryCtaLink;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public String getDeviceImage() {\\n" + //
                        "        return deviceImage;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public boolean getHasContent() {\\n" + //
                        "        return hasContent;\\n" + //
                        "    }\\n" + //
                        "}\\n" + //
                        "\",\n" + //
                        "  \"clientlib/css/hero-jumbotron.less\": \"@hero-jumbotron-bg: #f8f8f8;\\n" + //
                        "@color-green-primary: #459d1a;\\n" + //
                        "@color-text-dark: #333;\\n" + //
                        "@color-text-light: #fff;\\n" + //
                        "@breakpoint-tablet: 768px;\\n" + //
                        "\\n" + //
                        ".hero-jumbotron {\\n" + //
                        "    background-color: @hero-jumbotron-bg;\\n" + //
                        "    padding: 3rem 1rem;\\n" + //
                        "    width: 100%;\\n" + //
                        "\\n" + //
                        "    &__content-wrapper {\\n" + //
                        "        max-width: 1200px;\\n" + //
                        "        margin: 0 auto;\\n" + //
                        "        display: flex;\\n" + //
                        "        flex-direction: column; /* Stack on mobile */\\n" + //
                        "        align-items: center;\\n" + //
                        "        text-align: center;\\n" + //
                        "        box-sizing: border-box;\\n" + //
                        "\\n" + //
                        "        @media (min-width: @breakpoint-tablet) {\\n" + //
                        "            flex-direction: row;\\n" + //
                        "            text-align: left;\\n" + //
                        "            justify-content: space-between;\\n" + //
                        "        }\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    &__text-content {\\n" + //
                        "        flex: 1 1 50%;\\n" + //
                        "        padding: 0 1rem;\\n" + //
                        "        margin-bottom: 2rem;\\n" + //
                        "\\n" + //
                        "        @media (min-width: @breakpoint-tablet) {\\n" + //
                        "            margin-bottom: 0;\\n" + //
                        "            padding-right: 4rem;\\n" + //
                        "        }\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    &__greeting {\\n" + //
                        "        color: @color-green-primary;\\n" + //
                        "        font-size: 1.125rem;\\n" + //
                        "        font-weight: 600;\\n" + //
                        "        margin-bottom: 0.5rem;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    &__heading {\\n" + //
                        "        font-size: 2.5rem;\\n" + //
                        "        font-weight: 700;\\n" + //
                        "        color: @color-text-dark;\\n" + //
                        "        margin-top: 0;\\n" + //
                        "        margin-bottom: 1.5rem;\\n" + //
                        "\\n" + //
                        "        @media (min-width: @breakpoint-tablet) {\\n" + //
                        "            font-size: 3rem;\\n" + //
                        "        }\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    &__body-text {\\n" + //
                        "        color: @color-text-dark;\\n" + //
                        "        font-size: 1rem;\\n" + //
                        "        line-height: 1.6;\\n" + //
                        "        margin-bottom: 2rem;\\n" + //
                        "\\n" + //
                        "        p:last-child { margin-bottom: 0; }\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    &__visual {\\n" + //
                        "        flex: 1 1 50%;\\n" + //
                        "        max-width: 100%;\\n" + //
                        "        padding: 1rem;\\n" + //
                        "        \\n" + //
                        "        @media (min-width: @breakpoint-tablet) {\\n" + //
                        "            max-width: 50%;\\n" + //
                        "        }\\n" + //
                        "    }\\n" + //
                        "    \\n" + //
                        "    &__image {\\n" + //
                        "        width: 100%;\\n" + //
                        "        height: auto;\\n" + //
                        "        display: block;\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    &__ctas {\\n" + //
                        "        display: flex;\\n" + //
                        "        flex-wrap: wrap;\\n" + //
                        "        justify-content: center;\\n" + //
                        "        gap: 1rem;\\n" + //
                        "\\n" + //
                        "        @media (min-width: @breakpoint-tablet) {\\n" + //
                        "            justify-content: flex-start;\\n" + //
                        "        }\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    &__cta {\\n" + //
                        "        display: inline-flex;\\n" + //
                        "        align-items: center;\\n" + //
                        "        text-decoration: none;\\n" + //
                        "        padding: 0.75rem 1.5rem;\\n" + //
                        "        font-weight: 600;\\n" + //
                        "        border-radius: 4px;\\n" + //
                        "        transition: background-color 0.2s, border-color 0.2s;\\n" + //
                        "        white-space: nowrap;\\n" + //
                        "        \\n" + //
                        "        &--primary {\\n" + //
                        "            background-color: @color-green-primary;\\n" + //
                        "            color: @color-text-light;\\n" + //
                        "            border: 2px solid @color-green-primary;\\n" + //
                        "\\n" + //
                        "            &:hover {\\n" + //
                        "                background-color: darken(@color-green-primary, 10%);\\n" + //
                        "                border-color: darken(@color-green-primary, 10%);\\n" + //
                        "            }\\n" + //
                        "        }\\n" + //
                        "\\n" + //
                        "        &--secondary {\\n" + //
                        "            background-color: transparent;\\n" + //
                        "            color: @color-green-primary;\\n" + //
                        "            border: 2px solid @color-green-primary;\\n" + //
                        "\\n" + //
                        "            &:hover {\\n" + //
                        "                background-color: fadeout(@color-green-primary, 90%);\\n" + //
                        "            }\\n" + //
                        "        }\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    &__cta-icon {\\n" + //
                        "        display: inline-block;\\n" + //
                        "        line-height: 1;\\n" + //
                        "        \\n" + //
                        "        /* Simple, text-based icons for representation */\\n" + //
                        "        &--play::before {\\n" + //
                        "            content: '\\\\25B6'; /* Black right-pointing triangle (Play) */\\n" + //
                        "            margin-right: 0.5rem;\\n" + //
                        "        }\\n" + //
                        "        \\n" + //
                        "        &--arrow::after {\\n" + //
                        "            content: '\\\\2192'; /* Rightwards arrow */\\n" + //
                        "            margin-left: 0.5rem;\\n" + //
                        "        }\\n" + //
                        "    }\\n" + //
                        "}\\n" + //
                        "\",\n" + //
                        "  \"clientlib/css.txt\": \"hero-jumbotron.less\\n" + //
                        "\",\n" + //
                        "  \"clientlib/.content.xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n" + //
                        "<jcr:root xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\" xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\"\\n" + //
                        "    jcr:primaryType=\\\"cq:ClientLibraryFolder\\\"\\n" + //
                        "    allowProxy=\\\"true\\\"\\n" + //
                        "    categories=\\\"[myproject.hero-jumbotron]\\\"/>\\n" + //
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