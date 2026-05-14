package com.figma.aem.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.Exporter;
import com.adobe.cq.export.json.ExporterConstants;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
       resourceType = "figma/components/content/ciscoaccordion")
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CiscoAccordionModel {

    @ValueMapValue
    private String componentTitle;

    @ValueMapValue
    private String buttonLabel;

    @ValueMapValue
    private String buttonLink;

    @ChildResource(name = "accordionItems")
    private List<AccordionItem> accordionItems;

    private String id;

    @PostConstruct
    protected void init() {
        id = "cisco-accordion-" + UUID.randomUUID().toString();
    }

    public String getComponentTitle() {
        return componentTitle;
    }

    public List<AccordionItem> getAccordionItems() {
        return accordionItems;
    }

    public String getId() {
        return id;
    }
    public String getButtonLabel() {
        return buttonLabel;
    }
    
    public String getButtonLink() {
        return buttonLink;
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class AccordionItem {

        @ValueMapValue
        private String itemTitle;

        @ValueMapValue
        private String itemDescription;

        @ValueMapValue
        private String itemImage;

        public String getItemTitle() {
            return itemTitle;
        }

        public String getItemDescription() {
            return itemDescription;
        }

        public String getItemImage() {
            return itemImage;
        }
    }
}
