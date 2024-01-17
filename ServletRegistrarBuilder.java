// Source code is decompiled from a .class file using FernFlower decompiler.
package com.atlassian.confluence.impl.webapp;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
      } else {
         log.debug("Registering '{}' with init-params {}", this.servletName, this.initParams);
         registration.setInitParameters(this.initParams);
         if (this.loadOnStartup != null) {
            registration.setLoadOnStartup(this.loadOnStartup);
         }

         Iterator var3 = this.mappings.iterator();

         while(var3.hasNext()) {
            ServletMapping mapping = (ServletMapping)var3.next();
            mapping.addTo(registration, this.servletName);
         }

      }
   }

   static Builder servlet(String servletName, Class<? extends Servlet> servletClass) {
      return (new Builder()).servletName(servletName).servletClass(servletClass);
   }

   static Builder servlet(String servletName, String servletClassName) {
      try {
         return servlet(servletName, Class.forName(servletClassName));
      } catch (ClassNotFoundException var3) {
         throw new RuntimeException(var3);
      }
   }
}