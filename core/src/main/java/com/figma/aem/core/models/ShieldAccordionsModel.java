package com.figma.aem.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ShieldAccordionsModel {

    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String altText;

    @ChildResource
    private List<AccordionItem> accordions;

    public String getFileReference() {
        return fileReference;
    }

    public String getAltText() {
        return altText;
    }

    public List<AccordionItem> getAccordions() {
        return accordions != null ? accordions : Collections.emptyList();
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class AccordionItem {

        @ValueMapValue
        private String accordionTitle;

        @ValueMapValue
        private String accordionDescription;

        public String getAccordionTitle() {
            return accordionTitle;
        }

        public String getAccordionDescription() {
            return accordionDescription;
        }
    }
}
