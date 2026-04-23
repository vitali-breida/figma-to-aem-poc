package com.adobe.aem.guides.wknd.core.models;

import java.util.List;

public interface DanoneHeader {

    String getLogoReference();

    String getLogoAlt();

    String getJobsUrl();

    String getJobsLabel();

    String getNavigationRoot();

    List<NavItem> getNavItems();

    interface NavItem {
        String getTitle();
        String getPath();
        boolean isActive();
    }
}
