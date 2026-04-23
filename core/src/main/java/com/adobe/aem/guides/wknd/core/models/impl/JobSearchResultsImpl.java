package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.JobSearchResults;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
    adaptables = {SlingHttpServletRequest.class},
    adapters   = {JobSearchResults.class},
    resourceType = {JobSearchResultsImpl.RESOURCE_TYPE},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class JobSearchResultsImpl implements JobSearchResults {

    static final String RESOURCE_TYPE = "wknd/components/job-search-results";

    @ValueMapValue
    private String apiEndpoint;

    @ValueMapValue
    private String noResultsMessage;

    @ValueMapValue
    private String jobAlertUrl;

    @ValueMapValue
    private Integer itemsPerPage;

    @Override
    public String getApiEndpoint() {
        return StringUtils.defaultIfBlank(apiEndpoint, "");
    }

    @Override
    public String getNoResultsMessage() {
        return StringUtils.defaultIfBlank(noResultsMessage, "OH BUMMER! NO JOBS FOUND!");
    }

    @Override
    public String getJobAlertUrl() {
        return StringUtils.defaultIfBlank(jobAlertUrl, "#");
    }

    @Override
    public int getItemsPerPage() {
        return itemsPerPage != null && itemsPerPage > 0 ? itemsPerPage : 10;
    }

    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(apiEndpoint);
    }
}
