package com.atlassian.confluence.impl.webapp;

import javax.servlet.ServletRegistration;

/* loaded from: ServletRegistrar$ServletMapping.class */
class ServletRegistrar$ServletMapping {
    final UrlPatterns urlPatterns;

    ServletRegistrar$ServletMapping(UrlPatterns urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    void addTo(ServletRegistration registration, String servletName) {
        ServletRegistrar.log.debug("Mapping servlet '{}' to url-patterns {}", servletName, this.urlPatterns);
        registration.addMapping(this.urlPatterns.getPatternsAsString());
    }
}