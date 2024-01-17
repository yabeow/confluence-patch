package com.atlassian.confluence.impl.webapp;

import com.atlassian.confluence.impl.webapp.ServletRegistrar;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.servlet.Servlet;

/* loaded from: ServletRegistrar$Builder.class */
class ServletRegistrar$Builder {
    private String servletName;
    private Class<? extends Servlet> servletClass;
    private final Map<String, String> initParams = new LinkedHashMap();
    private final Collection<ServletRegistrar.ServletMapping> mappings = new LinkedList();
    private Integer loadOnStartup;

    ServletRegistrar$Builder() {
    }

    public ServletRegistrar$Builder mapping(UrlPattern... urlPatterns) {
        this.mappings.add(new ServletRegistrar.ServletMapping(new UrlPatterns(urlPatterns)));
        return this;
    }

    public ServletRegistrar build() {
        return new ServletRegistrar(this.servletClass, this.servletName, this.loadOnStartup, this.initParams, this.mappings);
    }

    public ServletRegistrar$Builder servletClass(Class<? extends Servlet> servletClass) {
        this.servletClass = servletClass;
        return this;
    }

    public ServletRegistrar$Builder initParam(String name, String value) {
        this.initParams.put(name, value);
        return this;
    }

    public ServletRegistrar$Builder servletName(String servletName) {
        this.servletName = servletName;
        return this;
    }

    public ServletRegistrar$Builder loadOnStartup(int loadOnStartup) {
        this.loadOnStartup = Integer.valueOf(loadOnStartup);
        return this;
    }
}