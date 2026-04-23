package com.adobe.aem.guides.wknd.core.models;

public interface JobSearch {

    String getApiEndpoint();

    String getHeading();

    String getTitlePlaceholder();

    String getCountryLabel();

    String getJobFunctionLabel();

    String getExperienceLevelLabel();

    String getSubmitLabel();

    boolean isEmpty();
}
