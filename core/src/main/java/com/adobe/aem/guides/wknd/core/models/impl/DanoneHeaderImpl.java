package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.DanoneHeader;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(
    adaptables = {SlingHttpServletRequest.class},
    adapters   = {DanoneHeader.class},
    resourceType = {DanoneHeaderImpl.RESOURCE_TYPE},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class DanoneHeaderImpl implements DanoneHeader {

    static final String RESOURCE_TYPE = "wknd/components/danone-header";

    @ValueMapValue
    private String logoReference;

    @ValueMapValue
    private String logoAlt;

    @ValueMapValue
    private String jobsUrl;

    @ValueMapValue
    private String jobsLabel;

    @ValueMapValue
    private String navigationRoot;

    private List<NavItem> navItems;

    private SlingHttpServletRequest request;

    @javax.inject.Inject
    public DanoneHeaderImpl(SlingHttpServletRequest request) {
        this.request = request;
    }

    @PostConstruct
    protected void init() {
        navItems = new ArrayList<>();
        if (StringUtils.isNotBlank(navigationRoot)) {
            PageManager pageManager = request.getResourceResolver().adaptTo(PageManager.class);
            if (pageManager != null) {
                Page rootPage = pageManager.getPage(navigationRoot);
                if (rootPage != null) {
                    String currentPath = request.getPathInfo();
                    Iterator<Page> children = rootPage.listChildren();
                    while (children.hasNext()) {
                        Page child = children.next();
                        if (!child.isHideInNav()) {
                            String title = StringUtils.defaultIfBlank(
                                child.getNavigationTitle(), child.getTitle());
                            String path = child.getPath() + ".html";
                            boolean active = StringUtils.isNotBlank(currentPath)
                                && currentPath.startsWith(child.getPath());
                            navItems.add(new NavItemImpl(title, path, active));
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getLogoReference() {
        return logoReference;
    }

    @Override
    public String getLogoAlt() {
        return StringUtils.defaultIfBlank(logoAlt, "Danone");
    }

    @Override
    public String getJobsUrl() {
        return StringUtils.defaultIfBlank(jobsUrl, "#");
    }

    @Override
    public String getJobsLabel() {
        return StringUtils.defaultIfBlank(jobsLabel, "JOBS");
    }

    @Override
    public String getNavigationRoot() {
        return navigationRoot;
    }

    @Override
    public List<NavItem> getNavItems() {
        return navItems;
    }

    static class NavItemImpl implements NavItem {
        private final String title;
        private final String path;
        private final boolean active;

        NavItemImpl(String title, String path, boolean active) {
            this.title = title;
            this.path = path;
            this.active = active;
        }

        @Override
        public String getTitle() { return title; }

        @Override
        public String getPath() { return path; }

        @Override
        public boolean isActive() { return active; }
    }
}
