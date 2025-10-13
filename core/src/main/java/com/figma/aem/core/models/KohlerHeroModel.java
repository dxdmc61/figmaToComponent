package com.figma.aem.core.models;

import com.adobe.cq.wcm.core.components.models.Image;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, resourceType = "my-site/components/kohlerhero", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class KohlerHeroModel {

    @ValueMapValue
    private String videoUrl;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String subtitle;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String cta1Label;

    @ValueMapValue
    private String cta1Url;

    @ValueMapValue
    private String cta2Label;

    @ValueMapValue
    private String cta2Url;

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public String getCta1Label() {
        return cta1Label;
    }

    public String getCta1Url() {
        return cta1Url;
    }

    public String getCta2Label() {
        return cta2Label;
    }

    public String getCta2Url() {
        return cta2Url;
    }
}
