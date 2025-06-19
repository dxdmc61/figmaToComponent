package com.figma.aem.core.services.imp;

// This class holds static mock responses for testing purposes.
public class MockGeminiResponses {

    /**
     * A mock response mimicking the actual Gemini API structure for component generation.
     * The actual generated JSON content is nested within `candidates[0].content.parts[0].text`.
     */
    public static final String MOCK_AEM_CARD_GENERATION_SUCCESS = "{\\n" + //
                "\" + //\n" + //
                "                        \"  \\\"ProductCarousel.html\\\": \\\"<div data-sly-use.model=\\\\\\\"com.figma.aem.core.models.ProductCarousel\\\\\\\" class=\\\\\\\"cmp-product-carousel\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"    <div class=\\\\\\\"cmp-product-carousel__container\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"        <sly data-sly-list.item=\\\\\\\"${model.productItems}\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"            <div class=\\\\\\\"cmp-product-carousel__item\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                <div class=\\\\\\\"cmp-product-card\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                    <sly data-sly-test.hasImage=\\\\\\\"${item.imageSrc}\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                        <div class=\\\\\\\"cmp-product-card__image-container\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                            <img src=\\\\\\\"${item.imageSrc}\\\\\\\" alt=\\\\\\\"${item.imageAlt}\\\\\\\" class=\\\\\\\"cmp-product-card__image\\\\\\\"/>\\\\n" + //
                "\" + //\n" + //
                "                        \"                        </div>\\\\n" + //
                "\" + //\n" + //
                "                        \"                    </sly>\\\\n" + //
                "\" + //\n" + //
                "                        \"                    <div class=\\\\\\\"cmp-product-card__content\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                        <h3 class=\\\\\\\"cmp-product-card__title\\\\\\\">${item.title}</h3>\\\\n" + //
                "\" + //\n" + //
                "                        \"                        <p class=\\\\\\\"cmp-product-card__price\\\\\\\">${item.price}</p>\\\\n" + //
                "\" + //\n" + //
                "                        \"                        <p class=\\\\\\\"cmp-product-card__description\\\\\\\">${item.description}</p>\\\\n" + //
                "\" + //\n" + //
                "                        \"                    </div>\\\\n" + //
                "\" + //\n" + //
                "                        \"                </div>\\\\n" + //
                "\" + //\n" + //
                "                        \"            </div>\\\\n" + //
                "\" + //\n" + //
                "                        \"        </sly>\\\\n" + //
                "\" + //\n" + //
                "                        \"    </div>\\\\n" + //
                "\" + //\n" + //
                "                        \"</div>\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\",\\n" + //
                "\" + //\n" + //
                "                        \"  \\\"ProductCarousel.java\\\": \\\"package com.figma.aem.core.models;\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"import org.apache.sling.api.resource.Resource;\\\\n" + //
                "\" + //\n" + //
                "                        \"import org.apache.sling.models.annotations.DefaultInjectionStrategy;\\\\n" + //
                "\" + //\n" + //
                "                        \"import org.apache.sling.models.annotations.Model;\\\\n" + //
                "\" + //\n" + //
                "                        \"import org.apache.sling.models.annotations.injectorspecific.ChildResource;\\\\n" + //
                "\" + //\n" + //
                "                        \"import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"import javax.annotation.PostConstruct;\\\\n" + //
                "\" + //\n" + //
                "                        \"import java.util.Collections;\\\\n" + //
                "\" + //\n" + //
                "                        \"import java.util.List;\\\\n" + //
                "\" + //\n" + //
                "                        \"import java.util.Objects;\\\\n" + //
                "\" + //\n" + //
                "                        \"import java.util.Optional;\\\\n" + //
                "\" + //\n" + //
                "                        \"import java.util.stream.Collectors;\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"@Model(\\\\n" + //
                "\" + //\n" + //
                "                        \"    adaptables = Resource.class,\\\\n" + //
                "\" + //\n" + //
                "                        \"    resourceType = ProductCarousel.RESOURCE_TYPE,\\\\n" + //
                "\" + //\n" + //
                "                        \"    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL\\\\n" + //
                "\" + //\n" + //
                "                        \")\\\\n" + //
                "\" + //\n" + //
                "                        \"public class ProductCarousel {\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"    protected static final String RESOURCE_TYPE = \\\\\\\"figma/components/productcarousel\\\\\\\";\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"    @ChildResource\\\\n" + //
                "\" + //\n" + //
                "                        \"    private List<Resource> productItems;\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"    private List<ProductItem> items;\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"    @PostConstruct\\\\n" + //
                "\" + //\n" + //
                "                        \"    protected void init() {\\\\n" + //
                "\" + //\n" + //
                "                        \"        if (productItems != null) {\\\\n" + //
                "\" + //\n" + //
                "                        \"            items = productItems.stream()\\\\n" + //
                "\" + //\n" + //
                "                        \"                .map(resource -> resource.adaptTo(ProductItem.class))\\\\n" + //
                "\" + //\n" + //
                "                        \"                .filter(Objects::nonNull)\\\\n" + //
                "\" + //\n" + //
                "                        \"                .collect(Collectors.toList());\\\\n" + //
                "\" + //\n" + //
                "                        \"        } else {\\\\n" + //
                "\" + //\n" + //
                "                        \"            items = Collections.emptyList();\\\\n" + //
                "\" + //\n" + //
                "                        \"        }\\\\n" + //
                "\" + //\n" + //
                "                        \"    }\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"    public List<ProductItem> getProductItems() {\\\\n" + //
                "\" + //\n" + //
                "                        \"        return Collections.unmodifiableList(items);\\\\n" + //
                "\" + //\n" + //
                "                        \"    }\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)\\\\n" + //
                "\" + //\n" + //
                "                        \"    public static class ProductItem {\\\\n" + //
                "\" + //\n" + //
                "                        \"        @ValueMapValue\\\\n" + //
                "\" + //\n" + //
                "                        \"        private String imageSrc;\\\\n" + //
                "\" + //\n" + //
                "                        \"        @ValueMapValue\\\\n" + //
                "\" + //\n" + //
                "                        \"        private String imageAlt;\\\\n" + //
                "\" + //\n" + //
                "                        \"        @ValueMapValue\\\\n" + //
                "\" + //\n" + //
                "                        \"        private String title;\\\\n" + //
                "\" + //\n" + //
                "                        \"        @ValueMapValue\\\\n" + //
                "\" + //\n" + //
                "                        \"        private String price;\\\\n" + //
                "\" + //\n" + //
                "                        \"        @ValueMapValue\\\\n" + //
                "\" + //\n" + //
                "                        \"        private String description;\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"        public String getImageSrc() {\\\\n" + //
                "\" + //\n" + //
                "                        \"            return imageSrc;\\\\n" + //
                "\" + //\n" + //
                "                        \"        }\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"        public String getImageAlt() {\\\\n" + //
                "\" + //\n" + //
                "                        \"            return Optional.ofNullable(imageAlt).orElse(\\\\\\\"Product image\\\\\\\");\\\\n" + //
                "\" + //\n" + //
                "                        \"        }\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"        public String getTitle() {\\\\n" + //
                "\" + //\n" + //
                "                        \"            return title;\\\\n" + //
                "\" + //\n" + //
                "                        \"        }\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"        public String getPrice() {\\\\n" + //
                "\" + //\n" + //
                "                        \"            return price;\\\\n" + //
                "\" + //\n" + //
                "                        \"        }\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"        public String getDescription() {\\\\n" + //
                "\" + //\n" + //
                "                        \"            return description;\\\\n" + //
                "\" + //\n" + //
                "                        \"        }\\\\n" + //
                "\" + //\n" + //
                "                        \"    }\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\",\\n" + //
                "\" + //\n" + //
                "                        \"  \\\"_cq_dialog/.content.xml\\\": \\\"<?xml version=\\\\\\\"1.0\\\\\\\" encoding=\\\\\\\"UTF-8\\\\\\\"?>\\\\n" + //
                "\" + //\n" + //
                "                        \"<jcr:root xmlns:sling=\\\\\\\"http://sling.apache.org/jcr/sling/1.0\\\\\\\" xmlns:cq=\\\\\\\"http://www.day.com/jcr/cq/1.0\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    xmlns:jcr=\\\\\\\"http://www.jcp.org/jcr/1.0\\\\\\\" xmlns:nt=\\\\\\\"http://www.jcp.org/jcr/nt/1.0\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    jcr:title=\\\\\\\"Product Carousel Properties\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    sling:resourceType=\\\\\\\"cq/gui/components/authoring/dialog\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"    <content\\\\n" + //
                "\" + //\n" + //
                "                        \"        jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"        sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/container\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"        <items jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"            <tabs\\\\n" + //
                "\" + //\n" + //
                "                        \"                jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/tabs\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                maximized=\\\\\\\"{Boolean}true\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                <items jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                    <properties\\\\n" + //
                "\" + //\n" + //
                "                        \"                        jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                        jcr:title=\\\\\\\"Carousel Items\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                        sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/container\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                        <items jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                            <column\\\\n" + //
                "\" + //\n" + //
                "                        \"                                jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/container\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                                <items jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                                    <productItems\\\\n" + //
                "\" + //\n" + //
                "                        \"                                        jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                        sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/form/multifield\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                        fieldDescription=\\\\\\\"Add Product Cards to the carousel.\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                        fieldLabel=\\\\\\\"Product Cards\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                                        <field\\\\n" + //
                "\" + //\n" + //
                "                        \"                                            jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                            sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/container\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                            name=\\\\\\\"./productItems\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                                            <items jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                <column\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                    jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                    sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/container\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                    <items jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                        <imageSrc\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            sling:resourceType=\\\\\\\"cq/gui/components/authoring/dialog/fileupload\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            autoStart=\\\\\\\"{Boolean}false\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            class=\\\\\\\"cq-droptarget\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            fieldLabel=\\\\\\\"Product Image\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            fileNameParameter=\\\\\\\"./imageFileName\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            fileReferenceParameter=\\\\\\\"./imageSrc\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            mimeTypes=\\\\\\\"[image]\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            name=\\\\\\\"./image\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            uploadUrl=\\\\\\\"${suffix.path}\\\\\\\"/>\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                        <imageAlt\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/form/textfield\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            fieldLabel=\\\\\\\"Image Alt Text\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            name=\\\\\\\"./imageAlt\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            emptyText=\\\\\\\"Product image\\\\\\\"/>\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                        <title\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/form/textfield\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            fieldLabel=\\\\\\\"Title\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            name=\\\\\\\"./title\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            required=\\\\\\\"{Boolean}true\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            emptyText=\\\\\\\"Enter product title\\\\\\\"/>\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                        <price\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/form/textfield\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            fieldLabel=\\\\\\\"Price\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            name=\\\\\\\"./price\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            emptyText=\\\\\\\"$0.00 / lb\\\\\\\"/>\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                        <description\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            sling:resourceType=\\\\\\\"granite/ui/components/coral/foundation/form/textfield\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            fieldLabel=\\\\\\\"Description\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            name=\\\\\\\"./description\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                            emptyText=\\\\\\\"Grown in...\\\\\\\"/>\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                    </items>\\\\n" + //
                "\" + //\n" + //
                "                        \"                                                </column>\\\\n" + //
                "\" + //\n" + //
                "                        \"                                            </items>\\\\n" + //
                "\" + //\n" + //
                "                        \"                                        </field>\\\\n" + //
                "\" + //\n" + //
                "                        \"                                    </productItems>\\\\n" + //
                "\" + //\n" + //
                "                        \"                                </items>\\\\n" + //
                "\" + //\n" + //
                "                        \"                            </column>\\\\n" + //
                "\" + //\n" + //
                "                        \"                        </items>\\\\n" + //
                "\" + //\n" + //
                "                        \"                    </properties>\\\\n" + //
                "\" + //\n" + //
                "                        \"                </items>\\\\n" + //
                "\" + //\n" + //
                "                        \"            </tabs>\\\\n" + //
                "\" + //\n" + //
                "                        \"        </items>\\\\n" + //
                "\" + //\n" + //
                "                        \"    </content>\\\\n" + //
                "\" + //\n" + //
                "                        \"</jcr:root>\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\",\\n" + //
                "\" + //\n" + //
                "                        \"  \\\"_cq_editConfig.xml\\\": \\\"<?xml version=\\\\\\\"1.0\\\\\\\" encoding=\\\\\\\"UTF-8\\\\\\\"?>\\\\n" + //
                "\" + //\n" + //
                "                        \"<jcr:root xmlns:cq=\\\\\\\"http://www.day.com/jcr/cq/1.0\\\\\\\" xmlns:jcr=\\\\\\\"http://www.jcp.org/jcr/1.0\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    jcr:primaryType=\\\\\\\"cq:EditConfig\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"    <cq:listeners\\\\n" + //
                "\" + //\n" + //
                "                        \"        jcr:primaryType=\\\\\\\"cq:Listeners\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"        afterdelete=\\\\\\\"REFRESH_PAGE\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"        afteredit=\\\\\\\"REFRESH_PAGE\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"        afterinsert=\\\\\\\"REFRESH_PAGE\\\\\\\"/>\\\\n" + //
                "\" + //\n" + //
                "                        \"</jcr:root>\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\",\\n" + //
                "\" + //\n" + //
                "                        \"  \\\".content.xml\\\": \\\"<?xml version=\\\\\\\"1.0\\\\\\\" encoding=\\\\\\\"UTF-8\\\\\\\"?>\\\\n" + //
                "\" + //\n" + //
                "                        \"<jcr:root xmlns:sling=\\\\\\\"http://sling.apache.org/jcr/sling/1.0\\\\\\\" xmlns:cq=\\\\\\\"http://www.day.com/jcr/cq/1.0\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    xmlns:jcr=\\\\\\\"http://www.jcp.org/jcr/1.0\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    xmlns:nt=\\\\\\\"http://www.jcp.org/jcr/nt/1.0\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    jcr:primaryType=\\\\\\\"cq:Component\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    jcr:title=\\\\\\\"Product Carousel\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    componentGroup=\\\\\\\"Figma - Content\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"    <cq:responsive jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\">\\\\n" + //
                "\" + //\n" + //
                "                        \"        <default\\\\n" + //
                "\" + //\n" + //
                "                        \"            jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"            width=\\\\\\\"12\\\\\\\"/>\\\\n" + //
                "\" + //\n" + //
                "                        \"        <tablet\\\\n" + //
                "\" + //\n" + //
                "                        \"            jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"            width=\\\\\\\"8\\\\\\\"/>\\\\n" + //
                "\" + //\n" + //
                "                        \"        <mobile\\\\n" + //
                "\" + //\n" + //
                "                        \"            jcr:primaryType=\\\\\\\"nt:unstructured\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"            width=\\\\\\\"12\\\\\\\"/>\\\\n" + //
                "\" + //\n" + //
                "                        \"    </cq:responsive>\\\\n" + //
                "\" + //\n" + //
                "                        \"</jcr:root>\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\",\\n" + //
                "\" + //\n" + //
                "                        \"  \\\"clientlibs/product-carousel/css/style.css\\\": \\\".cmp-product-carousel {\\\\n" + //
                "\" + //\n" + //
                "                        \"    padding: 20px;\\\\n" + //
                "\" + //\n" + //
                "                        \"    background-color: #f8f8f8;\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \".cmp-product-carousel__container {\\\\n" + //
                "\" + //\n" + //
                "                        \"    display: flex;\\\\n" + //
                "\" + //\n" + //
                "                        \"    flex-wrap: wrap; /* Allows items to wrap on smaller screens */\\\\n" + //
                "\" + //\n" + //
                "                        \"    gap: 20px; /* Space between cards */\\\\n" + //
                "\" + //\n" + //
                "                        \"    justify-content: center; /* Center cards if not filling row */\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \".cmp-product-carousel__item {\\\\n" + //
                "\" + //\n" + //
                "                        \"    flex: 0 0 calc(33.33% - 20px); /* 3 items per row on large screens */\\\\n" + //
                "\" + //\n" + //
                "                        \"    max-width: calc(33.33% - 20px);\\\\n" + //
                "\" + //\n" + //
                "                        \"    box-sizing: border-box; /* Include padding and border in the element's total width and height */\\\\n" + //
                "\" + //\n" + //
                "                        \"    margin-bottom: 20px; /* Space below items for wrapping */\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \".cmp-product-card {\\\\n" + //
                "\" + //\n" + //
                "                        \"    background-color: #ffffff;\\\\n" + //
                "\" + //\n" + //
                "                        \"    border-radius: 8px;\\\\n" + //
                "\" + //\n" + //
                "                        \"    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\\\\n" + //
                "\" + //\n" + //
                "                        \"    overflow: hidden; /* Ensures image corners are rounded with container */\\\\n" + //
                "\" + //\n" + //
                "                        \"    display: flex;\\\\n" + //
                "\" + //\n" + //
                "                        \"    flex-direction: column;\\\\n" + //
                "\" + //\n" + //
                "                        \"    height: 100%; /* Ensure cards are same height in a row */\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \".cmp-product-card__image-container {\\\\n" + //
                "\" + //\n" + //
                "                        \"    width: 100%;\\\\n" + //
                "\" + //\n" + //
                "                        \"    height: 180px; /* Fixed height for images */\\\\n" + //
                "\" + //\n" + //
                "                        \"    overflow: hidden;\\\\n" + //
                "\" + //\n" + //
                "                        \"    display: flex;\\\\n" + //
                "\" + //\n" + //
                "                        \"    align-items: center;\\\\n" + //
                "\" + //\n" + //
                "                        \"    justify-content: center;\\\\n" + //
                "\" + //\n" + //
                "                        \"    background-color: #f0f0f0; /* Placeholder background */\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \".cmp-product-card__image {\\\\n" + //
                "\" + //\n" + //
                "                        \"    width: 100%;\\\\n" + //
                "\" + //\n" + //
                "                        \"    height: 100%;\\\\n" + //
                "\" + //\n" + //
                "                        \"    object-fit: cover; /* Cover the container, cropping if necessary */\\\\n" + //
                "\" + //\n" + //
                "                        \"    display: block;\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \".cmp-product-card__content {\\\\n" + //
                "\" + //\n" + //
                "                        \"    padding: 15px;\\\\n" + //
                "\" + //\n" + //
                "                        \"    display: flex;\\\\n" + //
                "\" + //\n" + //
                "                        \"    flex-direction: column;\\\\n" + //
                "\" + //\n" + //
                "                        \"    flex-grow: 1; /* Allow content to grow to fill space */\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \".cmp-product-card__title {\\\\n" + //
                "\" + //\n" + //
                "                        \"    font-size: 1.2em;\\\\n" + //
                "\" + //\n" + //
                "                        \"    font-weight: bold;\\\\n" + //
                "\" + //\n" + //
                "                        \"    margin-top: 0;\\\\n" + //
                "\" + //\n" + //
                "                        \"    margin-bottom: 8px;\\\\n" + //
                "\" + //\n" + //
                "                        \"    color: #333;\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \".cmp-product-card__price {\\\\n" + //
                "\" + //\n" + //
                "                        \"    font-size: 1.1em;\\\\n" + //
                "\" + //\n" + //
                "                        \"    color: #007bff; /* A distinct color for price */\\\\n" + //
                "\" + //\n" + //
                "                        \"    font-weight: 600;\\\\n" + //
                "\" + //\n" + //
                "                        \"    margin-bottom: 8px;\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \".cmp-product-card__description {\\\\n" + //
                "\" + //\n" + //
                "                        \"    font-size: 0.9em;\\\\n" + //
                "\" + //\n" + //
                "                        \"    color: #666;\\\\n" + //
                "\" + //\n" + //
                "                        \"    margin-bottom: 0;\\\\n" + //
                "\" + //\n" + //
                "                        \"    flex-grow: 1; /* Allow description to take available space */\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"/* Responsive adjustments */\\\\n" + //
                "\" + //\n" + //
                "                        \"@media (max-width: 992px) {\\\\n" + //
                "\" + //\n" + //
                "                        \"    .cmp-product-carousel__item {\\\\n" + //
                "\" + //\n" + //
                "                        \"        flex: 0 0 calc(50% - 20px); /* 2 items per row on medium screens */\\\\n" + //
                "\" + //\n" + //
                "                        \"        max-width: calc(50% - 20px);\\\\n" + //
                "\" + //\n" + //
                "                        \"    }\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"@media (max-width: 768px) {\\\\n" + //
                "\" + //\n" + //
                "                        \"    .cmp-product-carousel__item {\\\\n" + //
                "\" + //\n" + //
                "                        \"        flex: 0 0 calc(100% - 20px); /* 1 item per row on small screens */\\\\n" + //
                "\" + //\n" + //
                "                        \"        max-width: calc(100% - 20px);\\\\n" + //
                "\" + //\n" + //
                "                        \"    }\\\\n" + //
                "\" + //\n" + //
                "                        \"}\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\",\\n" + //
                "\" + //\n" + //
                "                        \"  \\\"clientlibs/product-carousel/js/script.js\\\": \\\"// clientlibs/ProductCarousel/js/script.js\\\\n" + //
                "\" + //\n" + //
                "                        \"(function() {\\\\n" + //
                "\" + //\n" + //
                "                        \"    \\\\\\\"use strict\\\\\\\";\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"    // This script can be used to initialize any carousel specific JS library,\\\\n" + //
                "\" + //\n" + //
                "                        \"    // or for dynamic interactions if needed.\\\\n" + //
                "\" + //\n" + //
                "                        \"    // For a simple static display, it might not be strictly necessary.\\\\n" + //
                "\" + //\n" + //
                "                        \"    // If using a library like Slick Carousel or Swiper.js, you'd initialize it here.\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"    function initProductCarousel() {\\\\n" + //
                "\" + //\n" + //
                "                        \"        var carousels = document.querySelectorAll(\\\\\\\".cmp-product-carousel__container\\\\\\\");\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"        carousels.forEach(function(carousel) {\\\\n" + //
                "\" + //\n" + //
                "                        \"            // Example: Add a class once JS is loaded/initialized\\\\n" + //
                "\" + //\n" + //
                "                        \"            carousel.classList.add(\\\\\\\"cmp-product-carousel__container--initialized\\\\\\\");\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"            // If it were a dynamic carousel (e.g., using a library):\\\\n" + //
                "\" + //\n" + //
                "                        \"            // $(carousel).slick({\\\\n" + //
                "\" + //\n" + //
                "                        \"            //     slidesToShow: 3,\\\\n" + //
                "\" + //\n" + //
                "                        \"            //     slidesToScroll: 1,\\\\n" + //
                "\" + //\n" + //
                "                        \"            //     autoplay: true,\\\\n" + //
                "\" + //\n" + //
                "                        \"            //     autoplaySpeed: 2000,\\\\n" + //
                "\" + //\n" + //
                "                        \"            //     responsive: [\\\\n" + //
                "\" + //\n" + //
                "                        \"            //         {\\\\n" + //
                "\" + //\n" + //
                "                        \"            //             breakpoint: 992,\\\\n" + //
                "\" + //\n" + //
                "                        \"            //             settings: {\\\\n" + //
                "\" + //\n" + //
                "                        \"            //                 slidesToShow: 2\\\\n" + //
                "\" + //\n" + //
                "                        \"            //             }\\\\n" + //
                "\" + //\n" + //
                "                        \"            //         },\\\\n" + //
                "\" + //\n" + //
                "                        \"            //         {\\\\n" + //
                "\" + //\n" + //
                "                        \"            //             breakpoint: 768,\\\\n" + //
                "\" + //\n" + //
                "                        \"            //             settings: {\\\\n" + //
                "\" + //\n" + //
                "                        \"            //                 slidesToShow: 1\\\\n" + //
                "\" + //\n" + //
                "                        \"            //             }\\\\n" + //
                "\" + //\n" + //
                "                        \"            //         }\\\\n" + //
                "\" + //\n" + //
                "                        \"            //     ]\\\\n" + //
                "\" + //\n" + //
                "                        \"            // });\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"            // console.log(\\\\\\\"Product Carousel initialized:\\\\\\\", carousel);\\\\n" + //
                "\" + //\n" + //
                "                        \"        });\\\\n" + //
                "\" + //\n" + //
                "                        \"    }\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\\n" + //
                "\" + //\n" + //
                "                        \"    // Initialize when the DOM is ready\\\\n" + //
                "\" + //\n" + //
                "                        \"    if (document.readyState === \\\\\\\"loading\\\\\\\") {\\\\n" + //
                "\" + //\n" + //
                "                        \"        document.addEventListener(\\\\\\\"DOMContentLoaded\\\\\\\", initProductCarousel);\\\\n" + //
                "\" + //\n" + //
                "                        \"    } else {\\\\n" + //
                "\" + //\n" + //
                "                        \"        initProductCarousel();\\\\n" + //
                "\" + //\n" + //
                "                        \"    }\\\\n" + //
                "\" + //\n" + //
                "                        \"})();\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\",\\n" + //
                "\" + //\n" + //
                "                        \"  \\\"clientlibs/product-carousel/css.txt\\\": \\\"#base=css\\\\n" + //
                "\" + //\n" + //
                "                        \"style.css\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\",\\n" + //
                "\" + //\n" + //
                "                        \"  \\\"clientlibs/product-carousel/js.txt\\\": \\\"#base=js\\\\n" + //
                "\" + //\n" + //
                "                        \"script.js\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\",\\n" + //
                "\" + //\n" + //
                "                        \"  \\\"clientlibs/product-carousel/.content.xml\\\": \\\"<?xml version=\\\\\\\"1.0\\\\\\\" encoding=\\\\\\\"UTF-8\\\\\\\"?>\\\\n" + //
                "\" + //\n" + //
                "                        \"<jcr:root xmlns:cq=\\\\\\\"http://www.day.com/jcr/cq/1.0\\\\\\\" xmlns:jcr=\\\\\\\"http://www.jcp.org/jcr/1.0\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    jcr:primaryType=\\\\\\\"cq:ClientLibraryFolder\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    categories=\\\\\\\"[figma.product-carousel]\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    cssProcessor=\\\\\\\"[default:none, minify:true]\\\\\\\"\\\\n" + //
                "\" + //\n" + //
                "                        \"    jsProcessor=\\\\\\\"[default:none, minify:true]\\\\\\\"/>\\\\n" + //
                "\" + //\n" + //
                "                        \"\\\"\\n" + //
                "\" + //\n" + //
                "                        \"}" ;

}