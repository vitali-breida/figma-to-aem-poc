package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.JobsEmptyState;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
    adaptables = {SlingHttpServletRequest.class},
    adapters   = {JobsEmptyState.class},
    resourceType = {JobsEmptyStateImpl.RESOURCE_TYPE},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class JobsEmptyStateImpl implements JobsEmptyState {

    static final String RESOURCE_TYPE = "wknd/components/jobs-empty-state";

    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String linkText;

    @ValueMapValue
    private String linkHref;

    @Override
    public String getHeading() {
        return StringUtils.defaultIfBlank(heading, "OH BUMMER! NO JOBS FOUND!");
    }

    @Override
    public String getLinkText() {
        return StringUtils.defaultIfBlank(linkText, "SIGN UP FOR OUR JOB ALERT");
    }

    @Override
    public String getLinkHref() {
        return StringUtils.defaultIfBlank(linkHref, "#");
    }

    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(heading) && StringUtils.isBlank(linkText);
    }
}
