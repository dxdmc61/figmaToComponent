package com.figma.aem.core.services.imp;

// This class holds static mock responses for testing purposes.
public class MockGeminiResponses {

    /**
     * A mock response mimicking the actual Gemini API structure for component generation.
     * The actual generated JSON content is nested within `candidates[0].content.parts[0].text`.
     */
    public static final String MOCK_AEM_CARD_GENERATION_SUCCESS = "{\n" +
            "  \"candidates\": [\n" +
            "    {\n" +
            "      \"content\": {\n" +
            "        \"parts\": [\n" +
            "          {\n" +
            "            \"text\": \"{\\n\" +\n" +
            "            \"  \\\"aiCard.html\\\": \\\"<div data-sly-use.model=\\\\\\\"com.figma.aem.core.models.AiCard\\\\\\\" class=\\\\\\\"cmp-aicard\\\\\\\" data-sly-unwrap>\\\\n\" +\n" +
            "            \"    <sly data-sly-test.hasImage=\\\\\\\"${model.imageSrc}\\\\\\\">\\\\n\" +\n" +
            "            \"        <div class=\\\\\\\"cmp-aicard__image-container\\\\\\\">\\\\n\" +\n" +
            "            \"            <img src=\\\\\\\"${model.imageSrc}\\\\\\\" alt=\\\\\\\"${model.imageAltText}\\\\\\\" class=\\\\\\\"cmp-aicard__image\\\\\\\" />\\\\n\" +\n" +
            "            \"        </div>\\\\n\" +\n" +
            "            \"    </sly>\\\\n\" +\n" +
            "            \"    <div class=\\\\\\\"cmp-aicard__content\\\\\\\">\\\\n\" +\n" +
            "            \"        <h2 class=\\\\\\\"cmp-aicard__title\\\\\\\" data-sly-test=\\\\\\\"${model.title}\\\\\\\">${model.title}</h2>\\\\n\" +\n" +
            "            \"        <div class=\\\\\\\"cmp-aicard__description\\\\\\\" data-sly-test=\\\\\\\"${model.description}\\\\\\\">${model.description @ context='html'}</div>\\\\n\" +\n" +
            "            \"        <sly data-sly-test.hasCta=\\\\\\\"${model.ctaLink && model.ctaText}\\\\\\\">\\\\n\" +\n" +
            "            \"            <a href=\\\\\\\"${model.ctaLink}\\\\\\\" class=\\\\\\\"cmp-aicard__cta cmp-aicard__cta--primary\\\\\\\">${model.ctaText}</a>\\\\n\" +\n" +
            "            \"        </sly>\\\\n\" +\n" +
            "            \"    </div>\\\\n\" +\n" +
            "            \"</div>\\\\n\" +\n" +
            "            \"\\\",\\n\" +\n" +
            "            \"  \\\"AiCard.java\\\": \\\"package com.figma.aem.core.models;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"import com.adobe.cq.wcm.core.components.models.Image;\\\\n\" +\n" +
            "            \"import com.adobe.cq.wcm.core.components.models.Title;\\\\n\" +\n" +
            "            \"import com.adobe.cq.wcm.core.components.models.Teaser;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"import com.day.cq.wcm.api.Page;\\\\n\" +\n" +
            "            \"import com.day.cq.wcm.api.PageManager;\\\\n\" +\n" +
            "            \"import org.apache.sling.api.resource.Resource;\\\\n\" +\n" +
            "            \"import org.apache.sling.api.resource.ResourceResolver;\\\\n\" +\n" +
            "            \"import org.apache.sling.models.annotations.DefaultInjectionStrategy;\\\\n\" +\n" +
            "            \"import org.apache.sling.models.annotations.Model;\\\\n\" +\n" +
            "            \"import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;\\\\n\" +\n" +
            "            \"import org.apache.sling.models.annotations.injectorspecific.SlingObject;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"import javax.annotation.PostConstruct;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"import java.util.Optional;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"@Model(adaptables = Resource.class,\\\\n\" +\n" +
            "            \"       resourceType = AiCard.RESOURCE_TYPE,\\\\n\" +\n" +
            "            \"       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)\\\\n\" +\n" +
            "            \"public class AiCard {\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    protected static final String RESOURCE_TYPE = \\\\\\\"honda/components/aicard\\\\\\\";\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    @SlingObject\\\\n\" +\n" +
            "            \"    private Resource currentResource;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    @SlingObject\\\\n\" +\n" +
            "            \"    private ResourceResolver resourceResolver;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    @ValueMapValue\\\\n\" +\n" +
            "            \"    private String imageSrc;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    @ValueMapValue\\\\n\" +\n" +
            "            \"    private String imageAltText;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    @ValueMapValue\\\\n\" +\n" +
            "            \"    private String title;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    @ValueMapValue\\\\n\" +\n" +
            "            \"    private String description;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    @ValueMapValue\\\\n\" +\n" +
            "            \"    private String ctaText;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    @ValueMapValue\\\\n\" +\n" +
            "            \"    private String ctaLink;\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    @PostConstruct\\\\n\" +\n" +
            "            \"    protected void init() {\\\\n\" +\n" +
            "            \"        // You can add any post-construction logic here if needed.\\\\n\" +\n" +
            "            \"        // For instance, if 'ctaLink' needed to be resolved to a friendly URL\\\\n\" +\n" +
            "            \"        // based on a path, you would do it here.\\\\n\" +\n" +
            "            \"        // Example: If ctaLink is a path to an AEM page, resolve it.\\\\n\" +\n" +
            "            \"        // PageManager pageManager = resourceResolver.adaptTo(PageManager.class);\\\\n\" +\n" +
            "            \"        // if (pageManager != null && ctaLink != null && ctaLink.startsWith(\\\\\\\"/\\\\\\\")) {\\\\n\" +\n" +
            "            \"        //     Page linkedPage = pageManager.getPage(ctaLink);\\\\n\" +\n" +
            "            \"        //     if (linkedPage != null) {\\\\n\" +\n" +
            "            \"        //         this.ctaLink = linkedPage.getPath() + \\\\\\\".html\\\\\\\"; // Or use LinkRewriter service\\\\n\" +\n" +
            "            \"        //     }\\\\n\" +\n" +
            "            \"        // }\\\\n\" +\n" +
            "            \"    }\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    public String getImageSrc() {\\\\n\" +\n" +
            "            \"        return imageSrc;\\\\n\" +\n" +
            "            \"    }\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    public String getImageAltText() {\\\\n\" +\n" +
            "            \"        return Optional.ofNullable(imageAltText).orElse(\\\\\\\"Card image\\\\\\\"); // Default alt text\\\\n\" +\n" +
            "            \"    }\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    public String getTitle() {\\\\n\" +\n" +
            "            \"        return title;\\\\n\" +\n" +
            "            \"    }\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    public String getDescription() {\\\\n\" +\n" +
            "            \"        return description;\\\\n\" +\n" +
            "            \"    }\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    public String getCtaText() {\\\\n\" +\n" +
            "            \"        return ctaText;\\\\n\" +\n" +
            "            \"    }\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    public String getCtaLink() {\\\\n\" +\n" +
            "            \"        return ctaLink;\\\\n\" +\n" +
            "            \"    }\\\\n\" +\n" +
            "            \"}\\\\n\" +\n" +
            "            \"\\\",\\n\" +\n" +
            "            \"  \\\"dialog.xml\\\": \\\"<?xml version=\\\\\\\"1.0\\\\\\\" encoding=\\\\\\\"UTF-8\\\\\\\"?>\\\\n\" +\n" +
            "            \"<jcr:root xmlns:sling=\\\\\\\"http://sling.apache.org/jcr/sling/1.0\\\\\\\"\\\\n\" +\n" +
            "            \"    xmlns:cq=\\\\\\\"http://www.day.com/jcr/cq/1.0\\\\\\\"\\\\n\" +\n" +
            "            \"    xmlns:jcr=\\\\\\\"http://www.jcp.org/jcr/1.0\\\\\\\"\\\\n\" +\n" +
            "            \"    xmlns:nt=\\\\\\\"http://www.jcp.org/jcr/nt/1.0\\\\\\\"\\\\n\" +\n" +
            "            \"    jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"    jcr:title=\\\\\\\"AI Card Component Dialog\\\\\\\"\\\\n\" +\n" +
            "            \"    sling:resourceType=\\\\\\\"cq/gui/components/authoring/dialog\\\\\\\">\\\\n\" +\n" +
            "            \"    <content\\\\n\" +\n" +
            "            \"        jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"        sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/container\\\\\\\">\\\\n\" +\n" +
            "            \"        <items jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\">\\\\n\" +\n" +
            "            \"            <tabs\\\\n\" +\n" +
            "            \"                jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"                sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/tabs\\\\\\\"\\\\n\" +\n" +
            "            \"                maximized=\\\\\\\"true\\\\\\\">\\\\n\" +\n" +
            "            \"                <items jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\">\\\\n\" +\n" +
            "            \"                    <properties\\\\n\" +\n" +
            "            \"                        jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"                        jcr:title=\\\\\\\"Card Properties\\\\\\\"\\\\n\" +\n" +
            "            \"                        sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/container\\\\\\\">\\\\n\" +\n" +
            "            \"                        <items jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\">\\\\n\" +\n" +
            "            \"                            <column\\\\n\" +\n" +
            "            \"                                jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"                                sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/container\\\\\\\">\\\\n\" +\n" +
            "            \"                                <items jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\">\\\\n\" +\n" +
            "            \"                                    <image\\\\n\" +\n" +
            "            \"                                        jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"                                        sling:resourceType=\\\\\\\"cq/gui/components/authoring/dialog/fileupload\\\\\\\"\\\\n\" +\n" +
            "            \"                                        autoStart=\\\\\\\"false\\\\\\\"\\\\n\" +\n" +
            "            \"                                        class=\\\\\\\"cq-droptarget\\\\\\\"\\\\n\" +\n" +
            "            \"                                        fileNameParameter=\\\\\\\"./fileName\\\\\\\"\\\\n\" +\n" +
            "            \"                                        fileReferenceParameter=\\\\\\\"./imageSrc\\\\\\\"\\\\n\" +\n" +
            "            \"                                        mimeTypes=\\\\\\\"[image]\\\\\\\"\\\\n\" +\n" +
            "            \"                                        name=\\\\\\\"./image\\\\\\\"\\\\n\" +\n" +
            "            \"                                        fieldLabel=\\\\\\\"Card Image\\\\\\\"\\\\n\" +\n" +
            "            \"                                        uploadUrl=\\\\\\\"${suffix.path}\\\\\\\"/>\\\\n\" +\n" +
            "            \"                                    <imageAltText\\\\n\" +\n" +
            "            \"                                        jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"                                        sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/form/textfield\\\\\\\"\\\\n\" +\n" +
            "            \"                                        fieldLabel=\\\\\\\"Image Alt Text\\\\\\\"\\\\n\" +\n" +
            "            \"                                        name=\\\\\\\"./imageAltText\\\\\\\"\\\\n\" +\n" +
            "            \"                                        emptyText=\\\\\\\"Card image\\\\\\\"/>\\\\n\" +\n" +
            "            \"                                    <title\\\\n\" +\n" +
            "            \"                                        jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"                                        sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/form/textfield\\\\\\\"\\\\n\" +\n" +
            "            \"                                        fieldLabel=\\\\\\\"Title\\\\\\\"\\\\n\" +\n" +
            "            \"                                        name=\\\\\\\"./title\\\\\\\"\\\\n\" +\n" +
            "            \"                                        maxLength=\\\\\\\"60\\\\\\\"\\\\n\" +\n" +
            "            \"                                        required=\\\\\\\"true\\\\\\\"\\\\n\" +\n" +
            "            \"                                        emptyText=\\\\\\\"Enter card title\\\\\\\"/>\\\\n\" +\n" +
            "            \"                                    <description\\\\n\" +\n" +
            "            \"                                        jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"                                        sling:resourceType=\\\\\\\"cq/gui/components/authoring/dialog/richtext\\\\\\\"\\\\n\" +\n" +
            "            \"                                        fieldLabel=\\\\\\\"Description\\\\\\\"\\\\n\" +\n" +
            "            \"                                        name=\\\\\\\"./description\\\\\\\"\\\\n\" +\n" +
            "            \"                                        use           =\\\\\\\"${'core/wcm/components/form/richtext/v2/richtext' @ extension='html'}\\\\\\\"\\\\n\" +\n" +
            "            \"                                        rtePlugins    =\\\\\\\"${'core/wcm/components/form/richtext/v2/richtext' @ extension='json'}\\\\\\\"/>\\\\n\" +\n" +
            "            \"                                    <ctaText\\\\n\" +\n" +
            "            \"                                        jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"                                        sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/form/textfield\\\\\\\"\\\\n\" +\n" +
            "            \"                                        fieldLabel=\\\\\\\"CTA Text\\\\\\\"\\\\n\" +\n" +
            "            \"                                        name=\\\\\\\"./ctaText\\\\\\\"\\\\n\" +\n" +
            "            \"                                        emptyText=\\\\\\\"Learn More\\\\\\\"/>\\\\n\" +\n" +
            "            \"                                    <ctaLink\\\\n\" +\n" +
            "            \"                                        jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"                                        sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/form/textfield\\\\\\\"\\\\n\" +\n" +
            "            \"                                        fieldLabel=\\\\\\\"CTA Link\\\\\\\"\\\\n\" +\n" +
            "            \"                                        name=\\\\\\\"./ctaLink\\\\\\\"\\\\n\" +\n" +
            "            \"                                        emptyText=\\\\\\\"#\\\\\\\"\\\\n\" +\n" +
            "            \"                                        suggestion=\\\\\\\"true\\\\\\\"/>\\\\n\" +\n" +
            "            \"                                </items>\\\\n\" +\n" +
            "            \"                            </column>\\\\n\" +\n" +
            "            \"                        </items>\\\\n\" +\n" +
            "            \"                    </properties\\\\n\" +\n" +
            "            \"                </items>\\\\n\" +\n" +
            "            \"            </tabs\\\\n\" +\n" +
            "            \"        </items>\\\\n\" +\n" +
            "            \"    </content\\\\n\" +\n" +
            "            \"</jcr:root>\\\\n\" +\n" +
            "            \"\\\",\\n\" +\n" +
            "            \"  \\\"clientlib.css\\\": \\\".cmp-aicard {\\\\n\" +\n" +
            "            \"    background-color: #F5F7FA;\\\\n\" +\n" +
            "            \"    border-radius: 8px;\\\\n\" +\n" +
            "            \"    box-shadow: 0 4px 8px rgba(0,0,0,0.1);\\\\n\" +\n" +
            "            \"    padding: 16px;\\\\n\" +\n" +
            "            \"    display: flex;\\\\n\" +\n" +
            "            \"    flex-direction: column;\\\\n\" +\n" +
            "            \"    gap: 12px;\\\\n\" +\n" +
            "            \"    font-family: 'Roboto', sans-serif;\\\\n\" +\n" +
            "            \"}\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \".cmp-aicard__image-container {\\\\n\" +\n" +
            "            \"    width: 100%;\\\\n\" +\n" +
            "            \"    height: 160px;\\\\n\" +\n" +
            "            \"    overflow: hidden;\\\\n\" +\n" +
            "            \"    border-radius: 8px 8px 0 0; /* Match card border radius for top corners */\\\\n\" +\n" +
            "            \"}\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \".cmp-aicard__image {\\\\n\" +\n" +
            "            \"    width: 100%;\\\\n\" +\n" +
            "            \"    height: 100%;\\\\n\" +\n" +
            "            \"    object-fit: cover;\\\\n\" +\n" +
            "            \"}\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \".cmp-aicard__content {\\\\n\" +\n" +
            "            \"    display: flex;\\\\n\" +\n" +
            "            \"    flex-direction: column;\\\\n\" +\n" +
            "            \"    gap: 12px; /* Consistent spacing */\\\\n\" +\n" +
            "            \"}\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \".cmp-aicard__title {\\\\n\" +\n" +
            "            \"    font-size: 20px;\\\\n\" +\n" +
            "            \"    font-weight: 700;\\\\n\" +\n" +
            "            \"    color: #333333;\\\\n\" +\n" +
            "            \"    margin: 0; /* Reset browser default margin */\\\\n\" +\n" +
            "            \"}\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \".cmp-aicard__description {\\\\n\" +\n" +
            "            \"    font-size: 16px;\\\\n\" +\n" +
            "            \"    font-weight: 400;\\\\n\" +\n" +
            "            \"    color: #333333;\\\\n\" +\n" +
            "            \"    line-height: 1.5;\\\\n\" +\n" +
            "            \"}\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \".cmp-aicard__cta {\\\\n\" +\n" +
            "            \"    display: inline-block;\\\\n\" +\n" +
            "            \"    padding: 10px 16px;\\\\n\" +\n" +
            "            \"    border-radius: 4px;\\\\n\" +\n" +
            "            \"    text-decoration: none;\\\\n\" +\n" +
            "            \"    font-weight: 700;\\\\n\" +\n" +
            "            \"    text-align: center;\\\\n\" +\n" +
            "            \"    cursor: pointer;\\\\n\" +\n" +
            "            \"    transition: background-color 0.3s ease;\\\\n\" +\n" +
            "            \"}\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \".cmp-aicard__cta--primary {\\\\n\" +\n" +
            "            \"    background-color: #2E86DE;\\\\n\" +\n" +
            "            \"    color: #FFFFFF;\\\\n\" +\n" +
            "            \"    border: 1px solid #2E86DE;\\\\n\" +\n" +
            "            \"}\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \".cmp-aicard__cta--primary:hover,\\\\n\" +\n" +
            "            \".cmp-aicard__cta--primary:focus {\\\\n\" +\n" +
            "            \"    background-color: #256bbd; /* A darker shade for hover */\\\\n\" +\n" +
            "            \"    border-color: #256bbd;\\\\n\" +\n" +
            "            \"}\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"/* Responsive Styles */\\\\n\" +\n" +
            "            \"/* Mobile (default: 100% width) */\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"@media (min-width: 768px) {\\\\n\" +\n" +
            "            \"    .cmp-aicard {\\\\n\" +\n" +
            "            \"        width: calc(50% - 16px); /* 50% width with gap consideration if in a grid */\\\\n\" +\n" +
            "            \"    }\\\\n\" +\n" +
            "            \"}\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"@media (min-width: 1200px) {\\\\n\" +\n" +
            "            \"    .cmp-aicard {\\\\n\" +\n" +
            "            \"        width: calc(33.33% - 16px); /* 33.33% width with gap consideration if in a grid */\\\\n\" +\n" +
            "            \"    }\\\\n\" +\n" +
            "            \"}\\\\n\" +\n" +
            "            \"\\\",\\n\" +\n" +
            "            \"  \\\"clientlib.js\\\": \\\"// This file can be used for any client-side logic for the aiCard component.\\\\n\" +\n" +
            "            \"// For example, dynamic interactions, analytics, or form submissions.\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"(function() {\\\\n\" +\n" +
            "            \"    'use strict';\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    // Example: Simple initialization for cards\\\\n\" +\n" +
            "            \"    document.addEventListener('DOMContentLoaded', function() {\\\\n\" +\n" +
            "            \"        var cards = document.querySelectorAll('.cmp-aicard');\\\\n\" +\n" +
            "            \"        cards.forEach(function(card) {\\\\n\" +\n" +
            "            \"            // Add any JavaScript logic here, e.g., click handlers, animations\\\\n\" +\n" +
            "            \"            // console.log('AI Card initialized:', card);\\\\n\" +\n" +
            "            \"        });\\\\n\" +\n" +
            "            \"    });\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"})();\\\\n\" +\n" +
            "            \"\\\",\\n\" +\n" +
            "            \"  \\\"_cq_dialog/.content.xml\\\": \\\"<?xml version=\\\\\\\"1.0\\\\\\\" encoding=\\\\\\\"UTF-8\\\\\\\"?>\\\\n\" +\n" +
            "            \"<jcr:root xmlns:sling=\\\\\\\"http://sling.apache.org/jcr/sling/1.0\\\\\\\"\\\\n\" +\n" +
            "            \"    xmlns:cq=\\\\\\\"http://www.day.com/jcr/cq/1.0\\\\\\\"\\\\n\" +\n" +
            "            \"    xmlns:jcr=\\\\\\\"http://www.jcp.org/jcr/1.0\\\\\\\"\\\\n\" +\n" +
            "            \"    jcr:primaryType=\\\\\\\"cq:Component\\\\\\\"\\\\n\" +\n" +
            "            \"    jcr:title=\\\\\\\"AI Card\\\\\\\"\\\\n\" +\n" +
            "            \"    componentGroup=\\\\\\\"Honda - Content\\\\\\\"\\\\n\" +\n" +
            "            \"    sling:resourceSuperType=\\\\\\\"core/wcm/components/container/v1/container\\\\\\\"\\\\n\" +\n" +
            "            \"    imageDelegate=\\\\\\\"core/wcm/components/image/v3/image\\\\\\\"\\\\n\" +\n" +
            "            \"    textDelegate=\\\\\\\"core/wcm/components/text/v2/text\\\\\\\"\\\\n\" +\n" +
            "            \"    linkDelegate=\\\\\\\"core/wcm/components/button/v1/button\\\\\\\">\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"    <cq:responsive jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\">\\\\n\" +\n" +
            "            \"        <default\\\\n\" +\n" +
            "            \"            jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"            width=\\\\\\\"3\\\\\\\"/>\\\\n\" +\n" +
            "            \"        <tablet\\\\n\" +\n" +
            "            \"            jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"            width=\\\\\\\"6\\\\\\\"/>\\\\n\" +\n" +
            "            \"        <mobile\\\\n\" +\n" +
            "            \"            jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n\" +\n" +
            "            \"            width=\\\\\\\"12\\\\\\\"/>\\\\n\" +\n" +
            "            \"    </cq:responsive>\\\\n\" +\n" +
            "            \"\\\\n\" +\n" +
            "            \"</jcr:root>\\\\n\" +\n" +
            "            \"\\\"\\n" +
            "}\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";
}