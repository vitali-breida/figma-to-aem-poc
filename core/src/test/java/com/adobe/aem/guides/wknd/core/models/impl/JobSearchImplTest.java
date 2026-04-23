package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.JobSearch;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class JobSearchImplTest {

    private JobSearch jobSearch;

    @BeforeEach
    void setup(AemContext context) {
        context.create().page("/content/danone");
        Resource resource = context.create().resource("/content/danone/jcr:content/job-search",
            "sling:resourceType", JobSearchImpl.RESOURCE_TYPE,
            "apiEndpoint", "/api/jobs",
            "heading", "Find Your Danone Journey Today",
            "titlePlaceholder", "Enter job title",
            "countryLabel", "Select Country",
            "jobFunctionLabel", "Select Function",
            "experienceLevelLabel", "Select Level",
            "submitLabel", "FIND JOBS");

        context.currentResource(resource);
        jobSearch = context.request().adaptTo(JobSearch.class);
    }

    @Test
    void testGetApiEndpoint() {
        assertEquals("/api/jobs", jobSearch.getApiEndpoint());
    }

    @Test
    void testGetHeading() {
        assertEquals("Find Your Danone Journey Today", jobSearch.getHeading());
    }

    @Test
    void testGetTitlePlaceholder() {
        assertEquals("Enter job title", jobSearch.getTitlePlaceholder());
    }

    @Test
    void testGetCountryLabel() {
        assertEquals("Select Country", jobSearch.getCountryLabel());
    }

    @Test
    void testGetJobFunctionLabel() {
        assertEquals("Select Function", jobSearch.getJobFunctionLabel());
    }

    @Test
    void testGetExperienceLevelLabel() {
        assertEquals("Select Level", jobSearch.getExperienceLevelLabel());
    }

    @Test
    void testGetSubmitLabel() {
        assertEquals("FIND JOBS", jobSearch.getSubmitLabel());
    }

    @Test
    void testIsNotEmpty() {
        assertFalse(jobSearch.isEmpty());
    }

    @Test
    void testDefaultValues(AemContext context) {
        Resource resource = context.create().resource("/content/danone/jcr:content/job-search-defaults",
            "sling:resourceType", JobSearchImpl.RESOURCE_TYPE);
        context.currentResource(resource);
        JobSearch defaults = context.request().adaptTo(JobSearch.class);

        assertNotNull(defaults);
        assertEquals("Find Your Danone Journey Today", defaults.getHeading());
        assertEquals("Try Procurement, Operation, Quality...", defaults.getTitlePlaceholder());
        assertEquals("Countries", defaults.getCountryLabel());
        assertEquals("Job Function", defaults.getJobFunctionLabel());
        assertEquals("Experience Level", defaults.getExperienceLevelLabel());
        assertEquals("FIND JOBS", defaults.getSubmitLabel());
        assertTrue(defaults.isEmpty());
    }
}
