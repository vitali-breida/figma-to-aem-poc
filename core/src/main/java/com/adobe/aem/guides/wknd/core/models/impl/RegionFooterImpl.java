package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.RegionFooter;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(
    adaptables = {SlingHttpServletRequest.class},
    adapters   = {RegionFooter.class},
    resourceType = {RegionFooterImpl.RESOURCE_TYPE},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class RegionFooterImpl implements RegionFooter {

    static final String RESOURCE_TYPE = "wknd/components/region-footer";

    @ValueMapValue
    private String ctaLabel;

    @ValueMapValue
    private String ctaHref;

    @ValueMapValue
    private String copyrightText;

    @ValueMapValue
    private String[] primaryNavLabels;

    @ValueMapValue
    private String[] primaryNavHrefs;

    @ValueMapValue
    private String[] secondaryNavLabels;

    @ValueMapValue
    private String[] secondaryNavHrefs;

    private List<LinkItem> primaryNavItems;
    private List<LinkItem> secondaryNavItems;

    @PostConstruct
    private void init() {
        primaryNavItems = buildItems(primaryNavLabels, primaryNavHrefs,
            new String[]{"Teams", "Brands", "Our Promise", "Life at Danone", "Stories"},
            new String[]{"#", "#", "#", "#", "#"});

        secondaryNavItems = buildItems(secondaryNavLabels, secondaryNavHrefs,
            new String[]{"Countries", "Offices", "Factories", "Research & Innovation Centres", "Graduates & Trainees"},
            new String[]{"#", "#", "#", "#", "#"});
    }

    private List<LinkItem> buildItems(String[] labels, String[] hrefs,
                                       String[] defaultLabels, String[] defaultHrefs) {
        List<LinkItem> items = new ArrayList<>();
        String[] resolvedLabels = (labels != null && labels.length > 0) ? labels : defaultLabels;
        String[] resolvedHrefs  = (hrefs  != null && hrefs.length  > 0) ? hrefs  : defaultHrefs;
        for (int i = 0; i < resolvedLabels.length; i++) {
            final String label = resolvedLabels[i];
            final String href  = (i < resolvedHrefs.length) ? resolvedHrefs[i] : "#";
            items.add(new LinkItem() {
                @Override public String getLabel() { return label; }
                @Override public String getHref()  { return href;  }
            });
        }
        return items;
    }

    @Override public String getCtaLabel() { return StringUtils.defaultIfBlank(ctaLabel, "ALL UK AND IRELAND JOBS"); }
    @Override public String getCtaHref()  { return StringUtils.defaultIfBlank(ctaHref, "#"); }
    @Override public String getCopyrightText() { return StringUtils.defaultIfBlank(copyrightText, "COOKIES | PRIVACY POLICY | DANONE.COM"); }
    @Override public List<LinkItem> getPrimaryNavItems()   { return primaryNavItems;   }
    @Override public List<LinkItem> getSecondaryNavItems() { return secondaryNavItems; }
    @Override public boolean isEmpty() { return false; }
}
