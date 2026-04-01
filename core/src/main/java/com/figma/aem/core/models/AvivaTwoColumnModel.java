package com.figma.aem.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class AvivaTwoColumnModel {

    @SlingObject
    private Resource resource;

    private List<Card> cards;

    @PostConstruct
    protected void init() {
        if (resource == null) {
            cards = Collections.emptyList();
            return;
        }

        Resource cardsResource = resource.getChild("cards");
        if (cardsResource != null) {
            cards = StreamSupport.stream(cardsResource.getChildren().spliterator(), false)
                    .map(Card::new)
                    .collect(Collectors.toList());
        } else {
            cards = Collections.emptyList();
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public static class Card {

        private final String title;
        private final String description;
        private final String image;
        private final String altText;
        private final List<CTA> ctas;

        public Card(Resource resource) {
            ValueMap vm = resource.getValueMap();

            this.title = vm.get("title", String.class);
            this.description = vm.get("description", String.class);
            this.image = vm.get("image", String.class);
            this.altText = vm.get("altText", String.class);

            this.ctas = Optional.ofNullable(resource.getChild("ctas"))
                    .map(Resource::getChildren)
                    .map(children -> StreamSupport.stream(children.spliterator(), false)
                            .limit(3)
                            .map(CTA::new)
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
        }

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
            return ctas;
        }
    }

    public static class CTA {

        private final String label;
        private final String link;
        private final boolean openInNewTab;

        public CTA(Resource resource) {
            ValueMap vm = resource.getValueMap();

            this.label = vm.get("label", String.class);

            String rawLink = vm.get("link", String.class);
            if (rawLink != null && rawLink.startsWith("/content/") && !rawLink.contains(".")) {
                this.link = rawLink + ".html";
            } else {
                this.link = rawLink;
            }

            this.openInNewTab = vm.get("openInNewTab", false);
        }

        public String getLabel() {
            return label;
        }

        public String getLink() {
            return link;
        }

        public boolean isOpenInNewTab() {
            return openInNewTab;
        }
    }
}