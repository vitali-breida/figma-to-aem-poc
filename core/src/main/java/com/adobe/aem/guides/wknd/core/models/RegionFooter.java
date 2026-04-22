package com.adobe.aem.guides.wknd.core.models;

import java.util.List;

public interface RegionFooter {

    String getCtaLabel();

    String getCtaHref();

    List<LinkItem> getPrimaryNavItems();

    List<LinkItem> getSecondaryNavItems();

    String getCopyrightText();

    boolean isEmpty();

    interface LinkItem {
        String getLabel();
        String getHref();
    }
}
