package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.DanoneFooter;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
    adaptables = {SlingHttpServletRequest.class},
    adapters   = {DanoneFooter.class},
    resourceType = {DanoneFooterImpl.RESOURCE_TYPE},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class DanoneFooterImpl implements DanoneFooter {

    static final String RESOURCE_TYPE = "wknd/components/danone-footer";

    @ValueMapValue
    private String jobsAllUrl;

    @ValueMapValue
    private String jobsAllLabel;

    @ValueMapValue
    private String facebookUrl;

    @ValueMapValue
    private String instagramUrl;

    @ValueMapValue
    private String linkedinUrl;

    @ValueMapValue
    private String copyrightText;

    @Override
    public String getJobsAllUrl() {
        return StringUtils.defaultIfBlank(jobsAllUrl, "#");
    }

    @Override
    public String getJobsAllLabel() {
        return StringUtils.defaultIfBlank(jobsAllLabel, "ALL UK AND IRELAND JOBS");
    }

    @Override
    public String getFacebookUrl() {
        return StringUtils.defaultIfBlank(facebookUrl, "#");
    }

    @Override
    public String getInstagramUrl() {
        return StringUtils.defaultIfBlank(instagramUrl, "#");
    }

    @Override
    public String getLinkedinUrl() {
        return StringUtils.defaultIfBlank(linkedinUrl, "#");
    }

    @Override
    public String getCopyrightText() {
        return StringUtils.defaultIfBlank(copyrightText, "COOKIES | PRIVACY POLICY | DANONE.COM");
    }
}
