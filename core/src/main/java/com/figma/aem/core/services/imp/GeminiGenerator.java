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
                        "  \"productCarousel.html\": \"<div data-sly-use.model=\\\"com.figma.aem.core.models.ProductCarousel\\\" class=\\\"cmp-product-carousel\\\">\\n" + //
                        "    <div class=\\\"cmp-product-carousel__container\\\">\\n" + //
                        "        <sly data-sly-list.item=\\\"${model.productItems}\\\">\\n" + //
                        "            <div class=\\\"cmp-product-carousel__item\\\">\\n" + //
                        "                <div class=\\\"cmp-product-card\\\">\\n" + //
                        "                    <sly data-sly-test.hasImage=\\\"${item.image}\\\">\\n" + //
                        "                        <div class=\\\"cmp-product-card__image-container\\\">\\n" + //
                        "                            <img src=\\\"${item.image}\\\" alt=\\\"${item.imageAlt}\\\" class=\\\"cmp-product-card__image\\\"/>\\n" + //
                        "                        </div>\\n" + //
                        "                    </sly>\\n" + //
                        "                    <div class=\\\"cmp-product-card__content\\\">\\n" + //
                        "                        <h3 class=\\\"cmp-product-card__title\\\">${item.title}</h3>\\n" + //
                        "                        <p class=\\\"cmp-product-card__price\\\">${item.price}</p>\\n" + //
                        "                        <p class=\\\"cmp-product-card__description\\\">${item.description}</p>\\n" + //
                        "                    </div>\\n" + //
                        "                </div>\\n" + //
                        "            </div>\\n" + //
                        "        </sly>\\n" + //
                        "    </div>\\n" + //
                        "</div>\\n" + //
                        "\",\n" + //
                        "  \"ProductCarousel.java\": \"package com.figma.aem.core.models;\\n" + //
                        "\\n" + //
                        "import org.apache.sling.api.resource.Resource;\\n" + //
                        "import org.apache.sling.models.annotations.DefaultInjectionStrategy;\\n" + //
                        "import org.apache.sling.models.annotations.Model;\\n" + //
                        "import org.apache.sling.models.annotations.injectorspecific.ChildResource;\\n" + //
                        "import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;\\n" + //
                        "\\n" + //
                        "import javax.annotation.PostConstruct;\\n" + //
                        "import java.util.Collections;\\n" + //
                        "import java.util.List;\\n" + //
                        "import java.util.Objects;\\n" + //
                        "import java.util.Optional;\\n" + //
                        "import java.util.stream.Collectors;\\n" + //
                        "\\n" + //
                        "@Model(\\n" + //
                        "    adaptables = Resource.class,\\n" + //
                        "    resourceType = ProductCarousel.RESOURCE_TYPE,\\n" + //
                        "    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL\\n" + //
                        ")\\n" + //
                        "public class ProductCarousel {\\n" + //
                        "\\n" + //
                        "    protected static final String RESOURCE_TYPE = \\\"figma/components/productcarousel\\\";\\n" + //
                        "\\n" + //
                        "    @ChildResource\\n" + //
                        "    private List<Resource> productItems;\\n" + //
                        "\\n" + //
                        "    private List<ProductItem> items;\\n" + //
                        "\\n" + //
                        "    @PostConstruct\\n" + //
                        "    protected void init() {\\n" + //
                        "        if (productItems != null) {\\n" + //
                        "            items = productItems.stream()\\n" + //
                        "                .map(resource -> resource.adaptTo(ProductItem.class))\\n" + //
                        "                .filter(Objects::nonNull)\\n" + //
                        "                .collect(Collectors.toList());\\n" + //
                        "        } else {\\n" + //
                        "            items = Collections.emptyList();\\n" + //
                        "        }\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    public List<ProductItem> getProductItems() {\\n" + //
                        "        return Collections.unmodifiableList(items);\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)\\n" + //
                        "    public static class ProductItem {\\n" + //
                        "        @ValueMapValue\\n" + //
                        "        private String image;\\n" + //
                        "        @ValueMapValue\\n" + //
                        "        private String imageAlt;\\n" + //
                        "        @ValueMapValue\\n" + //
                        "        private String title;\\n" + //
                        "        @ValueMapValue\\n" + //
                        "        private String price;\\n" + //
                        "        @ValueMapValue\\n" + //
                        "        private String description;\\n" + //
                        "\\n" + //
                        "        public String getImage() {\\n" + //
                        "            return image;\\n" + //
                        "        }\\n" + //
                        "\\n" + //
                        "        public String getImageAlt() {\\n" + //
                        "            return Optional.ofNullable(imageAlt).orElse(\\\"Product image\\\");\\n" + //
                        "        }\\n" + //
                        "\\n" + //
                        "        public String getTitle() {\\n" + //
                        "            return title;\\n" + //
                        "        }\\n" + //
                        "\\n" + //
                        "        public String getPrice() {\\n" + //
                        "            return price;\\n" + //
                        "        }\\n" + //
                        "\\n" + //
                        "        public String getDescription() {\\n" + //
                        "            return description;\\n" + //
                        "        }\\n" + //
                        "    }\\n" + //
                        "}\\n" + //
                        "\",\n" + //
                        "  \"_cq_dialog/.content.xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n" + //
                        "<jcr:root xmlns:sling=\\\"http://sling.apache.org/jcr/sling/1.0\\\" xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\"\\n" + //
                        "    xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\" xmlns:nt=\\\"http://www.jcp.org/jcr/nt/1.0\\\"\\n" + //
                        "    jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "    jcr:title=\\\"Product Carousel Properties\\\"\\n" + //
                        "    sling:resourceType=\\\"cq/gui/components/authoring/dialog\\\">\\n" + //
                        "    <content\\n" + //
                        "        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "        sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\">\\n" + //
                        "        <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "            <tabs\\n" + //
                        "                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                sling:resourceType=\\\"granite/ui/components/coral/foundation/tabs\\\"\\n" + //
                        "                maximized=\\\"{Boolean}true\\\">\\n" + //
                        "                <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                    <properties\\n" + //
                        "                        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                        jcr:title=\\\"Carousel Items\\\"\\n" + //
                        "                        sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\">\\n" + //
                        "                        <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                            <column\\n" + //
                        "                                jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\">\\n" + //
                        "                                <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                                    <productItems\\n" + //
                        "                                        jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                        composite=\\\"{Boolean}true\\\"\\n" + //
                        "                                        sling:resourceType=\\\"granite/ui/components/coral/foundation/form/multifield\\\"\\n" + //
                        "                                        fieldDescription=\\\"Add Product Cards to the carousel.\\\"\\n" + //
                        "                                        fieldLabel=\\\"Product Cards\\\">\\n" + //
                        "                                        <field\\n" + //
                        "                                            jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                            sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\"\\n" + //
                        "                                            name=\\\"./productItems\\\">\\n" + //
                        "                                            <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                                                <column\\n" + //
                        "                                                    jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                    sling:resourceType=\\\"granite/ui/components/coral/foundation/container\\\">\\n" + //
                        "                                                    <items jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "                                                        <imageSrc\\n" + //
                        "                                                            jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                            sling:resourceType=\\\"granite/ui/components/coral/foundation/form/pathfield\\\"\\n" + //
                        "                                                            fieldLabel=\\\"Product Image\\\"\\n" + //
                        "                                                            name=\\\"./image\\\"\\n" + //
                        "                                                            emptyText=\\\"Enter product title\\\"/>\\n" + //
                        "                                                        <imageAlt\\n" + //
                        "                                                            jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                            sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                                            fieldLabel=\\\"Image Alt Text\\\"\\n" + //
                        "                                                            name=\\\"./imageAlt\\\"\\n" + //
                        "                                                            emptyText=\\\"Product image\\\"/>\\n" + //
                        "                                                        <title\\n" + //
                        "                                                            jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                            sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                                            fieldLabel=\\\"Title\\\"\\n" + //
                        "                                                            name=\\\"./title\\\"\\n" + //
                        "                                                            required=\\\"{Boolean}true\\\"\\n" + //
                        "                                                            emptyText=\\\"Enter product title\\\"/>\\n" + //
                        "                                                        <price\\n" + //
                        "                                                            jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                            sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                                            fieldLabel=\\\"Price\\\"\\n" + //
                        "                                                            name=\\\"./price\\\"\\n" + //
                        "                                                            emptyText=\\\"$0.00 / lb\\\"/>\\n" + //
                        "                                                        <description\\n" + //
                        "                                                            jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "                                                            sling:resourceType=\\\"granite/ui/components/coral/foundation/form/textfield\\\"\\n" + //
                        "                                                            fieldLabel=\\\"Description\\\"\\n" + //
                        "                                                            name=\\\"./description\\\"\\n" + //
                        "                                                            emptyText=\\\"Grown in...\\\"/>\\n" + //
                        "                                                    </items>\\n" + //
                        "                                                </column>\\n" + //
                        "                                            </items>\\n" + //
                        "                                        </field>\\n" + //
                        "                                    </productItems>\\n" + //
                        "                                </items>\\n" + //
                        "                            </column>\\n" + //
                        "                        </items>\\n" + //
                        "                    </properties>\\n" + //
                        "                </items>\\n" + //
                        "            </tabs>\\n" + //
                        "        </items>\\n" + //
                        "    </content>\\n" + //
                        "</jcr:root>\\n" + //
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
                        "  \".content.xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n" + //
                        "<jcr:root xmlns:sling=\\\"http://sling.apache.org/jcr/sling/1.0\\\" xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\"\\n" + //
                        "    xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\"\\n" + //
                        "    xmlns:nt=\\\"http://www.jcp.org/jcr/nt/1.0\\\"\\n" + //
                        "    jcr:primaryType=\\\"cq:Component\\\"\\n" + //
                        "    jcr:title=\\\"Product Carousel\\\"\\n" + //
                        "    componentGroup=\\\"Figma - Content\\\">\\n" + //
                        "    <cq:responsive jcr:primaryType=\\\"nt:unstructured\\\">\\n" + //
                        "        <default\\n" + //
                        "            jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "            width=\\\"12\\\"/>\\n" + //
                        "        <tablet\\n" + //
                        "            jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "            width=\\\"8\\\"/>\\n" + //
                        "        <mobile\\n" + //
                        "            jcr:primaryType=\\\"nt:unstructured\\\"\\n" + //
                        "            width=\\\"12\\\"/>\\n" + //
                        "    </cq:responsive>\\n" + //
                        "</jcr:root>\\n" + //
                        "\",\n" + //
                        "  \"clientlibs/product-carousel/css/style.css\": \".cmp-product-carousel {\\n" + //
                        "    padding: 20px;\\n" + //
                        "    background-color: #f8f8f8;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".cmp-product-carousel__container {\\n" + //
                        "    display: flex;\\n" + //
                        "    flex-wrap: wrap; /* Allows items to wrap on smaller screens */\\n" + //
                        "    gap: 20px; /* Space between cards */\\n" + //
                        "    justify-content: center; /* Center cards if not filling row */\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".cmp-product-carousel__item {\\n" + //
                        "    flex: 0 0 calc(33.33% - 20px); /* 3 items per row on large screens */\\n" + //
                        "    max-width: calc(33.33% - 20px);\\n" + //
                        "    box-sizing: border-box; /* Include padding and border in the element's total width and height */\\n" + //
                        "    margin-bottom: 20px; /* Space below items for wrapping */\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".cmp-product-card {\\n" + //
                        "    background-color: #ffffff;\\n" + //
                        "    border-radius: 8px;\\n" + //
                        "    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\\n" + //
                        "    overflow: hidden; /* Ensures image corners are rounded with container */\\n" + //
                        "    display: flex;\\n" + //
                        "    flex-direction: column;\\n" + //
                        "    height: 100%; /* Ensure cards are same height in a row */\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".cmp-product-card__image-container {\\n" + //
                        "    width: 100%;\\n" + //
                        "    height: 180px; /* Fixed height for images */\\n" + //
                        "    overflow: hidden;\\n" + //
                        "    display: flex;\\n" + //
                        "    align-items: center;\\n" + //
                        "    justify-content: center;\\n" + //
                        "    background-color: #f0f0f0; /* Placeholder background */\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".cmp-product-card__image {\\n" + //
                        "    width: 100%;\\n" + //
                        "    height: 100%;\\n" + //
                        "    object-fit: cover; /* Cover the container, cropping if necessary */\\n" + //
                        "    display: block;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".cmp-product-card__content {\\n" + //
                        "    padding: 15px;\\n" + //
                        "    display: flex;\\n" + //
                        "    flex-direction: column;\\n" + //
                        "    flex-grow: 1; /* Allow content to grow to fill space */\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".cmp-product-card__title {\\n" + //
                        "    font-size: 1.2em;\\n" + //
                        "    font-weight: bold;\\n" + //
                        "    margin-top: 0;\\n" + //
                        "    margin-bottom: 8px;\\n" + //
                        "    color: #333;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".cmp-product-card__price {\\n" + //
                        "    font-size: 1.1em;\\n" + //
                        "    color: #007bff; /* A distinct color for price */\\n" + //
                        "    font-weight: 600;\\n" + //
                        "    margin-bottom: 8px;\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        ".cmp-product-card__description {\\n" + //
                        "    font-size: 0.9em;\\n" + //
                        "    color: #666;\\n" + //
                        "    margin-bottom: 0;\\n" + //
                        "    flex-grow: 1; /* Allow description to take available space */\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        "/* Responsive adjustments */\\n" + //
                        "@media (max-width: 992px) {\\n" + //
                        "    .cmp-product-carousel__item {\\n" + //
                        "        flex: 0 0 calc(50% - 20px); /* 2 items per row on medium screens */\\n" + //
                        "        max-width: calc(50% - 20px);\\n" + //
                        "    }\\n" + //
                        "}\\n" + //
                        "\\n" + //
                        "@media (max-width: 768px) {\\n" + //
                        "    .cmp-product-carousel__item {\\n" + //
                        "        flex: 0 0 calc(100% - 20px); /* 1 item per row on small screens */\\n" + //
                        "        max-width: calc(100% - 20px);\\n" + //
                        "    }\\n" + //
                        "}\\n" + //
                        "\",\n" + //
                        "  \"clientlibs/product-carousel/js/script.js\": \"// clientlibs/ProductCarousel/js/script.js\\n" + //
                        "(function() {\\n" + //
                        "    \\\"use strict\\\";\\n" + //
                        "\\n" + //
                        "    // This script can be used to initialize any carousel specific JS library,\\n" + //
                        "    // or for dynamic interactions if needed.\\n" + //
                        "    // For a simple static display, it might not be strictly necessary.\\n" + //
                        "    // If using a library like Slick Carousel or Swiper.js, you'd initialize it here.\\n" + //
                        "\\n" + //
                        "    function initProductCarousel() {\\n" + //
                        "        var carousels = document.querySelectorAll(\\\".cmp-product-carousel__container\\\");\\n" + //
                        "\\n" + //
                        "        carousels.forEach(function(carousel) {\\n" + //
                        "            // Example: Add a class once JS is loaded/initialized\\n" + //
                        "            carousel.classList.add(\\\"cmp-product-carousel__container--initialized\\\");\\n" + //
                        "\\n" + //
                        "            // If it were a dynamic carousel (e.g., using a library):\\n" + //
                        "            // $(carousel).slick({\\n" + //
                        "            //     slidesToShow: 3,\\n" + //
                        "            //     slidesToScroll: 1,\\n" + //
                        "            //     autoplay: true,\\n" + //
                        "            //     autoplaySpeed: 2000,\\n" + //
                        "            //     responsive: [\\n" + //
                        "            //         {\\n" + //
                        "            //             breakpoint: 992,\\n" + //
                        "            //             settings: {\\n" + //
                        "            //                 slidesToShow: 2\\n" + //
                        "            //             }\\n" + //
                        "            //         },\\n" + //
                        "            //         {\\n" + //
                        "            //             breakpoint: 768,\\n" + //
                        "            //             settings: {\\n" + //
                        "            //                 slidesToShow: 1\\n" + //
                        "            //             }\\n" + //
                        "            //         }\\n" + //
                        "            //     ]\\n" + //
                        "            // });\\n" + //
                        "\\n" + //
                        "            // console.log(\\\"Product Carousel initialized:\\\", carousel);\\n" + //
                        "        });\\n" + //
                        "    }\\n" + //
                        "\\n" + //
                        "    // Initialize when the DOM is ready\\n" + //
                        "    if (document.readyState === \\\"loading\\\") {\\n" + //
                        "        document.addEventListener(\\\"DOMContentLoaded\\\", initProductCarousel);\\n" + //
                        "    } else {\\n" + //
                        "        initProductCarousel();\\n" + //
                        "    }\\n" + //
                        "})();\\n" + //
                        "\",\n" + //
                        "  \"clientlibs/product-carousel/css.txt\": \"#base=css\\n" + //
                        "style.css\\n" + //
                        "\",\n" + //
                        "  \"clientlibs/product-carousel/js.txt\": \"#base=js\\n" + //
                        "script.js\\n" + //
                        "\",\n" + //
                        "  \"clientlibs/product-carousel/.content.xml\": \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n" + //
                        "<jcr:root xmlns:cq=\\\"http://www.day.com/jcr/cq/1.0\\\" xmlns:jcr=\\\"http://www.jcp.org/jcr/1.0\\\"\\n" + //
                        "    jcr:primaryType=\\\"cq:ClientLibraryFolder\\\"\\n" + //
                        "    categories=\\\"[figma.product-carousel]\\\"\\n" + //
                        "    cssProcessor=\\\"[default:none, minify:true]\\\"\\n" + //
                        "    jsProcessor=\\\"[default:none, minify:true]\\\"/>\\n" + //
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