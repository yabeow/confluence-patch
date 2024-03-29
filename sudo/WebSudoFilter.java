// Source code is decompiled from a .class file using FernFlower decompiler.
package com.atlassian.confluence.impl.webapp.sudo;

import com.atlassian.annotations.nullability.ParametersAreNonnullByDefault;
import com.atlassian.annotations.nullability.ReturnValuesAreNonnullByDefault;
import com.atlassian.config.util.BootstrapUtils;
import com.atlassian.confluence.core.ConfluenceSystemProperties;
import com.atlassian.confluence.internal.spaces.SpaceManagerInternal;
import com.atlassian.confluence.security.PermissionManager;
import com.atlassian.confluence.security.websudo.WebSudoManager;
import com.atlassian.confluence.util.GeneralUtil;
import com.atlassian.spring.container.ContainerManager;
import com.atlassian.spring.container.LazyComponentReference;
import com.atlassian.util.concurrent.Supplier;
import java.io.IOException;
import java.net.URI;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.dispatcher.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
public final class WebSudoFilter extends HttpFilter {
   private static final Logger log = LoggerFactory.getLogger(WebSudoFilter.class);
   private final transient ParameterEnforcer parameterEnforcer = new ParameterEnforcer();
   private final transient Supplier<WebSudoManager> webSudoManagerRef = new LazyComponentReference("webSudoManager");
   private final transient Supplier<ClassLoader> classLoaderRef = new LazyComponentReference("uberClassLoader");
   private final transient Supplier<PermissionManager> permissionManagerRef = new LazyComponentReference("permissionManager");
   private final transient Supplier<SpaceManagerInternal> spaceManagerRef = new LazyComponentReference("spaceManager");
   private final transient java.util.function.Supplier<Boolean> isSetupComplete = new BooleanFuse(false, () -> {
      return !ConfluenceSystemProperties.isDevMode() && ContainerManager.isContainerSetup() && GeneralUtil.isSetupComplete();
   });

   public WebSudoFilter() {
   }

   public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
      if (this.parameterEnforcer.enforce(request)) {
         response.sendError(400);
      } else if (!(Boolean)this.isSetupComplete.get()) {
         log.debug("Skipping websudo check; dev mode or setup incomplete");
         chain.doFilter(request, response);
      } else {
         URI redirectUri = this.createEnforcer().enforceWebSudo(request, response);
         if (redirectUri != null) {
            response.sendRedirect(redirectUri.toString());
         } else {
            chain.doFilter(request, response);
         }
      }
   }

   WebSudoEnforcer createEnforcer() {
      return new WebSudoEnforcer((WebSudoManager)this.webSudoManagerRef.get(), (ClassLoader)this.classLoaderRef.get(), (PermissionManager)this.permissionManagerRef.get(), (Dispatcher)BootstrapUtils.getBootstrapContext().getBean("strutsDispatcher", Dispatcher.class), (SpaceManagerInternal)this.spaceManagerRef.get());
   }
}
