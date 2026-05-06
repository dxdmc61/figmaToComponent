package com.figma.aem.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
    adaptables = Resource.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class AzurecardModel {

    @Self
    private Resource resource;

    @ValueMapValue
    private String sectionLabel;

    @ValueMapValue
    private String sectionHeading;

    @ValueMapValue
    private String id;

    private List<Card> cards;

    @PostConstruct
    protected void init() {
        cards = new ArrayList<>();

        Resource cardsResource = resource.getChild("cards");
        if (cardsResource != null) {
            for (Resource item : cardsResource.getChildren()) {
                ValueMap vm = item.getValueMap();

                Card card = new Card(
                    vm.get("cardImage", String.class),
                    vm.get("cardTitle", String.class),
                    vm.get("cardDescription", String.class),
                    vm.get("cardLinkText", String.class),
                    vm.get("cardLinkUrl", String.class),
                    vm.get("openInNewTab", Boolean.class)
                );

                cards.add(card);
            }
        }

        if (cards.isEmpty()) {
            cards = Collections.emptyList();
        }
    }

    public String getSectionLabel() {
        return sectionLabel;
    }

    public String getSectionHeading() {
        return sectionHeading;
    }

    public String getId() {
        return id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public static class Card {

        private final String cardImage;
        private final String cardTitle;
        private final String cardDescription;
        private final String cardLinkText;
        private final String cardLinkUrl;
        private final Boolean openInNewTab;

        public Card(String cardImage, String cardTitle, String cardDescription,
                    String cardLinkText, String cardLinkUrl, Boolean openInNewTab) {
            this.cardImage = cardImage;
            this.cardTitle = cardTitle;
            this.cardDescription = cardDescription;
            this.cardLinkText = cardLinkText;
            this.cardLinkUrl = cardLinkUrl;
            this.openInNewTab = openInNewTab;
        }

        public String getCardImage() {
            return cardImage;
        }

        public String getCardTitle() {
            return cardTitle;
        }

        public String getCardDescription() {
            return cardDescription;
        }

        public String getCardLinkText() {
            return cardLinkText;
        }

        public String getCardLinkUrl() {
            return cardLinkUrl;
        }

        public boolean isOpenInNewTab() {
            return openInNewTab != null && openInNewTab;
        }
    }
}