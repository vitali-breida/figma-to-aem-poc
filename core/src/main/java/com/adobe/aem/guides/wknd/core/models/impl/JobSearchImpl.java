package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.JobSearch;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
    adaptables = {SlingHttpServletRequest.class},
    adapters   = {JobSearch.class},
    resourceType = {JobSearchImpl.RESOURCE_TYPE},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class JobSearchImpl implements JobSearch {

    static final String RESOURCE_TYPE = "wknd/components/job-search";

    @ValueMapValue
    private String apiEndpoint;

    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String titlePlaceholder;

    @ValueMapValue
    private String countryLabel;

    @ValueMapValue
    private String jobFunctionLabel;

    @ValueMapValue
    private String experienceLevelLabel;

    @ValueMapValue
    private String submitLabel;

    @Override
    public String getApiEndpoint() {
        return StringUtils.defaultIfBlank(apiEndpoint, "");
    }

    @Override
    public String getHeading() {
        return StringUtils.defaultIfBlank(heading, "Find Your Danone Journey Today");
    }

    @Override
    public String getTitlePlaceholder() {
        return StringUtils.defaultIfBlank(titlePlaceholder, "Try Procurement, Operation, Quality...");
    }

    @Override
    public String getCountryLabel() {
        return StringUtils.defaultIfBlank(countryLabel, "Countries");
    }

    @Override
    public String getJobFunctionLabel() {
        return StringUtils.defaultIfBlank(jobFunctionLabel, "Job Function");
    }

    @Override
    public String getExperienceLevelLabel() {
        return StringUtils.defaultIfBlank(experienceLevelLabel, "Experience Level");
    }

    @Override
    public String getSubmitLabel() {
        return StringUtils.defaultIfBlank(submitLabel, "FIND JOBS");
    }

    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(apiEndpoint);
    }
}
