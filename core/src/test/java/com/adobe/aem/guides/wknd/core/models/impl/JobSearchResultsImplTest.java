package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.JobSearchResults;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class JobSearchResultsImplTest {

    private JobSearchResults jobSearchResults;

    @BeforeEach
    void setup(AemContext context) {
        context.create().page("/content/danone");
        Resource resource = context.create().resource("/content/danone/jcr:content/job-search-results",
            "sling:resourceType", JobSearchResultsImpl.RESOURCE_TYPE,
            "apiEndpoint", "/api/jobs",
            "noResultsMessage", "Sorry, no positions available.",
            "jobAlertUrl", "/content/danone/job-alert",
            "itemsPerPage", 5);

        context.currentResource(resource);
        jobSearchResults = context.request().adaptTo(JobSearchResults.class);
    }

    @Test
    void testGetApiEndpoint() {
        assertEquals("/api/jobs", jobSearchResults.getApiEndpoint());
    }

    @Test
    void testGetNoResultsMessage() {
        assertEquals("Sorry, no positions available.", jobSearchResults.getNoResultsMessage());
    }

    @Test
    void testGetJobAlertUrl() {
        assertEquals("/content/danone/job-alert", jobSearchResults.getJobAlertUrl());
    }

    @Test
    void testGetItemsPerPage() {
        assertEquals(5, jobSearchResults.getItemsPerPage());
    }

    @Test
    void testIsNotEmpty() {
        assertFalse(jobSearchResults.isEmpty());
    }

    @Test
    void testDefaultValues(AemContext context) {
        Resource resource = context.create().resource("/content/danone/jcr:content/job-results-defaults",
            "sling:resourceType", JobSearchResultsImpl.RESOURCE_TYPE);
        context.currentResource(resource);
        JobSearchResults defaults = context.request().adaptTo(JobSearchResults.class);

        assertNotNull(defaults);
        assertEquals("OH BUMMER! NO JOBS FOUND!", defaults.getNoResultsMessage());
        assertEquals("#", defaults.getJobAlertUrl());
        assertEquals(10, defaults.getItemsPerPage());
        assertTrue(defaults.isEmpty());
    }
}
