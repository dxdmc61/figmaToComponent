package com.figma.aem.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import javax.annotation.PostConstruct;

@Model(adaptables = { SlingHttpServletRequest.class,
        Resource.class }, resourceType = HumanaHeroModel.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HumanaHeroModel {
    public static final String RESOURCE_TYPE = "figma/components/heroHumana";
    @Self
    private SlingHttpServletRequest request;
    @ValueMapValue
    private String greeting;
    @ValueMapValue
    private String heading;
    @ValueMapValue
    private String bodyText;
    @ValueMapValue
    private String primaryCtaText;
    @ValueMapValue
    private String primaryCtaLink;
    @ValueMapValue
    private String secondaryCtaText;
    @ValueMapValue
    private String secondaryCtaLink;
    @ValueMapValue
    private String deviceImage;
    @ValueMapValue
    private String imageAlt;
    private boolean hasContent;

    @PostConstruct
    protected void init() {
        this.hasContent = (heading != null && !heading.isEmpty()) || (bodyText != null && !bodyText.isEmpty())
                || (deviceImage != null);
    }

    public String getGreeting() {
        return greeting;
    }

    public String getHeading() {
        return heading;
    }

    public String getBodyText() {
        return bodyText;
    }

    public String getPrimaryCtaText() {
        return primaryCtaText != null ? primaryCtaText : "Play Video";
    }

    public String getPrimaryCtaLink() {
        return primaryCtaLink;
    }

    public String getSecondaryCtaText() {
        return secondaryCtaText != null ? secondaryCtaText : "Get Started";
    }

    public String getSecondaryCtaLink() {
        return secondaryCtaLink;
    }

    public String getDeviceImage() {
        return deviceImage ;
    }

    public String getImageAlt() {
        return imageAlt != null ? imageAlt : "Hero visual";
    }

    public boolean getHasContent() {
        return hasContent;
    }
}