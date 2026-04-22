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
    private String title;

    @ValueMapValue
    private String searchPlaceholder;

    @ValueMapValue
    private String countriesLabel;

    @ValueMapValue
    private String jobFunctionLabel;

    @ValueMapValue
    private String experienceLevelLabel;

    @ValueMapValue
    private String buttonText;

    @Override
    public String getTitle() {
        return StringUtils.defaultIfBlank(title, "Find Your Danone Journey Today");
    }

    @Override
    public String getSearchPlaceholder() {
        return StringUtils.defaultIfBlank(searchPlaceholder, "Try Procurement, Operation, Quality...");
    }

    @Override
    public String getCountriesLabel() {
        return StringUtils.defaultIfBlank(countriesLabel, "Countries");
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
    public String getButtonText() {
        return StringUtils.defaultIfBlank(buttonText, "FIND JOBS");
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
