package com.figma.aem.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Collections;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AvivaTwoColumnModel {

    @ChildResource(name = "cards")
    private List<Card> cards;

    public List<Card> getCards() {
        return cards != null ? Collections.unmodifiableList(cards) : Collections.emptyList();
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class Card {

        @ValueMapValue
        private String title;

        @ValueMapValue
        private String description;

        @ValueMapValue
        private String image;

        @ValueMapValue
        private String altText;

        @ChildResource(name = "ctas")
        private List<CTA> ctas;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }

        public String getAltText() {
            return altText;
        }

        public List<CTA> getCtas() {
            return ctas != null ? Collections.unmodifiableList(ctas) : Collections.emptyList();
        }
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class CTA {

        @ValueMapValue
        private String label;

        @ValueMapValue
        private String link;

        @ValueMapValue
        private boolean openInNewTab;

        public String getLabel() {
            return label;
        }

        public String getLink() {
            // Basic link processing for internal AEM pages
            if (link != null && link.startsWith("/content/") && !link.contains(".")) {
                return link + ".html";
            }
            return link;
        }

        public boolean isOpenInNewTab() {
            return openInNewTab;
        }
    }
}