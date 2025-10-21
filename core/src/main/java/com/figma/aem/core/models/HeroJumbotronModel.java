package com.figma.aem.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.annotation.versioning.ProviderType;

import javax.annotation.PostConstruct;

@ProviderType
@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    resourceType = HeroJumbotronModel.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeroJumbotronModel {

    static final String RESOURCE_TYPE = "figma/components/hero-jumbotron";

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

    // Holds the path to the device image file reference
    private String deviceImage;

    private boolean hasContent;

    @PostConstruct
    protected void init() {
        // Get the image reference from the child resource "deviceImage" created by fileupload
        Resource imageResource = request.getResource().getChild("deviceImage");
        if (imageResource != null) {
            this.deviceImage = imageResource.getValueMap().get("fileReference", String.class);
        }

        // Determine if the component has enough content to be rendered
        this.hasContent = heading != null || bodyText != null || primaryCtaLink != null || deviceImage != null;
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
        // Fallback for button text if not configured
        return primaryCtaText != null ? primaryCtaText : "Play Video";
    }

    public String getPrimaryCtaLink() {
        return primaryCtaLink;
    }

    public String getSecondaryCtaText() {
        // Fallback for button text if not configured
        return secondaryCtaText != null ? secondaryCtaText : "Get Started";
    }

    public String getSecondaryCtaLink() {
        return secondaryCtaLink;
    }

    public String getDeviceImage() {
        return deviceImage;
    }

    public boolean getHasContent() {
        return hasContent;
    }
}
