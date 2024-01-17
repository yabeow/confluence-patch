package com.atlassian.confluence.impl.webapp;

import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: ServletRegistrar.class */
final class ServletRegistrar implements ServletContextRegistrar {
    private static final Logger log = LoggerFactory.getLogger(ServletRegistrar.class);
    private final Class<? extends Servlet> servletClass;
    private final String servletName;
    private final Integer loadOnStartup;
    private final Map<String, String> initParams;
    private final Collection<ServletMapping> mappings;

    private ServletRegistrar(Class<? extends Servlet> servletClass, String servletName, @Nullable Integer loadOnStartup, Map<String, String> initParams, Collection<ServletMapping> mappings) {
        this.servletClass = servletClass;
        this.servletName = servletName;
        this.loadOnStartup = loadOnStartup;
        this.initParams = initParams;
        this.mappings = mappings;
    }

    public void register(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic registration = servletContext.addServlet(this.servletName, this.servletClass);
        if (registration == null) {
            throw new ServletException("ServletContext already contains a complete registration for " + this.servletName);
        }
        log.debug("Registering '{}' with init-params {}", this.servletName, this.initParams);
        registration.setInitParameters(this.initParams);
        if (this.loadOnStartup != null) {
            registration.setLoadOnStartup(this.loadOnStartup.intValue());
        }
        for (ServletMapping mapping : this.mappings) {
            mapping.addTo(registration, this.servletName);
        }
    }

    static Builder servlet(String servletName, Class<? extends Servlet> servletClass) {
        return new Builder().servletName(servletName).servletClass(servletClass);
    }
}