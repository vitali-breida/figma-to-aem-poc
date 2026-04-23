package com.adobe.aem.guides.wknd.core.models;

public interface JobSearchResults {

    String getApiEndpoint();

    String getNoResultsMessage();

    String getJobAlertUrl();

    int getItemsPerPage();

    boolean isEmpty();
}
