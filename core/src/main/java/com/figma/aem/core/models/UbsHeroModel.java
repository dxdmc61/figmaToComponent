package com.figma.aem.core.models;


import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Getter
@Model(adaptables = Resource.class,
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class UbsHeroModel {

    @ValueMapValue
    private String backgroundImage;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String overlayPosition;

    @ValueMapValue
    private String theme;

    @ChildResource(name = "ctaLinks")
    private List<CtaItem> ctaList;

    @PostConstruct
    protected void init() {
        if (StringUtils.isBlank(overlayPosition)) {
            overlayPosition = "left";
        }
        if (StringUtils.isBlank(theme)) {
            theme = "light";
        }
    }

    @Getter
    @Model(adaptables = Resource.class)
    public static class CtaItem {

        @ValueMapValue
        private String linkText;

        @ValueMapValue
        private String linkUrl;
    }
}