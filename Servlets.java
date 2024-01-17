// Source code is decompiled from a .class file using FernFlower decompiler.
package com.atlassian.confluence.impl.webapp;

import com.atlassian.confluence.servlet.ApplicationStatusServlet;
import com.atlassian.confluence.servlet.ConfluenceNoOpServlet;
import com.atlassian.confluence.servlet.ConfluenceVelocityServlet;
import com.atlassian.confluence.servlet.CssServlet;
import com.atlassian.confluence.servlet.FileServerServlet;
import com.atlassian.confluence.servlet.ImageCaptchaServlet;
import com.atlassian.confluence.servlet.JohnsonAnalyticsServlet;
import com.atlassian.confluence.servlet.JohnsonDataServlet;
import com.atlassian.confluence.servlet.JohnsonDismissEventsServlet;
import com.atlassian.confluence.servlet.LabelServlet;
import com.atlassian.confluence.servlet.ReadyToServeServlet;
import com.atlassian.confluence.servlet.ServletModuleContainerServlet;
import com.atlassian.confluence.servlet.SpringManagedServlet;
import com.atlassian.confluence.servlet.TinyUrlServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.struts2.views.JspSupportServlet;

enum Servlets {
   JSP_NOOP(ServletRegistrar.servlet("noop-jsp", "noop__002e__jsp").mapping(new UrlPattern[]{UrlPattern.JSP_NOOP})),
   JSP_ERRORS(ServletRegistrar.servlet("errors-jsp", "errors__002e__jsp").mapping(new UrlPattern[]{UrlPattern.JSP_ERRORS})),
   JSP_ERRORS_NOTFOUND(ServletRegistrar.servlet("errors-notfound-jsp", "errors.notfound__002e__jsp").mapping(new UrlPattern[]{UrlPattern.JSP_ERRORS_NOTFOUND})),
   JSP_SYSTEM_ERROR(ServletRegistrar.servlet("system-error", "__0035__00page__002e__jsp").mapping(new UrlPattern[]{UrlPattern.JSP_SYSTEM_ERROR})),
   JSP_ADMIN_DEFAULT(ServletRegistrar.servlet("admin-default", RedirectServlet.class).initParam("target", "/admin/console.action").mapping(new UrlPattern[]{UrlPattern.JSP_ADMIN_DEFAULT})),
   JSP_ADMIN_CLUSTER(ServletRegistrar.servlet("admin-cluster", "admin.cluster.hashclustername__002e__jsp").mapping(new UrlPattern[]{UrlPattern.JSP_ADMIN_CLUSTER})),
   DISABLE_JASPER(ServletRegistrar.servlet("jsp", FourOhFourServlet.class).mapping(new UrlPattern[]{UrlPattern.JSP, UrlPattern.JSPX})),
   CSS_SERVLET(ServletRegistrar.servlet("css", CssServlet.class).loadOnStartup(10).mapping(new UrlPattern[]{UrlPattern.CSS})),
   JSP_SUPPORT_SERVLET(ServletRegistrar.servlet("jsp-support-servlet", JspSupportServlet.class).loadOnStartup(1)),
   VELOCITY_SERVLET(ServletRegistrar.servlet("velocity", ConfluenceVelocityServlet.class).loadOnStartup(2).mapping(new UrlPattern[]{UrlPattern.VELOCITY})),
   SIMPLE_DISPLAY(ServletRegistrar.servlet("simple-display", SpringManagedServlet.class).initParam("springComponentName", "simpleDisplayServlet").loadOnStartup(3).mapping(new UrlPattern[]{UrlPattern.DISPLAY})),
   TINY_URL_SERVLET(ServletRegistrar.servlet("tiny-url", TinyUrlServlet.class).loadOnStartup(3).mapping(new UrlPattern[]{UrlPattern.TINY_URL})),
   FILE_SERVER(ServletRegistrar.servlet("file-server", FileServerServlet.class).loadOnStartup(4).mapping(new UrlPattern[]{UrlPattern.DOWNLOAD})),
   STATUS_SERVLET(ServletRegistrar.servlet("status-servlet", ApplicationStatusServlet.class).loadOnStartup(5).mapping(new UrlPattern[]{UrlPattern.STATUS})),
   XML_RPC_SERVLET(ServletRegistrar.servlet("xmlrpc", SpringManagedServlet.class).initParam("springComponentName", "xmlRpcServer").loadOnStartup(6).mapping(new UrlPattern[]{UrlPattern.XML_RPC})),
   PLUGIN_SERVLETS(ServletRegistrar.servlet("servlet-module-container-servlet", ServletModuleContainerServlet.class).loadOnStartup(9).mapping(new UrlPattern[]{UrlPattern.PLUGIN_SERVLET})),
   LABELS_SERVLET(ServletRegistrar.servlet("labels", LabelServlet.class).loadOnStartup(9).mapping(new UrlPattern[]{UrlPattern.LABELS})),
   JCAPTCHA_SERVLET(ServletRegistrar.servlet("jcaptcha", ImageCaptchaServlet.class).loadOnStartup(0).mapping(new UrlPattern[]{UrlPattern.JCAPTCHA})),
   EXPORTWORD_SERVLET(ServletRegistrar.servlet("exportword", SpringManagedServlet.class).initParam("springComponentName", "exportWordPageServer").mapping(new UrlPattern[]{UrlPattern.EXPORTWORD})),
   NOOP(ServletRegistrar.servlet("noop", ConfluenceNoOpServlet.class).loadOnStartup(0).mapping(new UrlPattern[]{UrlPattern.STATIC}).mapping(new UrlPattern[]{UrlPattern.ADMIN_APP_TRUST_CERTIFICATE}).mapping(new UrlPattern[]{UrlPattern.REST})),
   JOHNSON_ANALYTICS_SERVLET(ServletRegistrar.servlet("johnson-analytics-servlet", JohnsonAnalyticsServlet.class).mapping(new UrlPattern[]{UrlPattern.JOHNSON_ANALYTICS})),
   JOHNSON_DATA_SERVLET(ServletRegistrar.servlet("johnson-data-servlet", JohnsonDataServlet.class).mapping(new UrlPattern[]{UrlPattern.JOHNSON_DATA})),
   JOHNSON_DISMISS_EVENTS_SERVLET(ServletRegistrar.servlet("johnson-dismiss-events-servlet", JohnsonDismissEventsServlet.class).mapping(new UrlPattern[]{UrlPattern.JOHNSON_DISMISS_EVENTS})),
   FINAL(ServletRegistrar.servlet("final-servlet", ReadyToServeServlet.class).loadOnStartup(100));

   private final ServletContextRegistrar registrar;

   private Servlets(ServletContextRegistrar registrar) {
      this.registrar = registrar;
   }

   private Servlets(ServletRegistrar.Builder builder) {
      this((ServletContextRegistrar)builder.build());
   }

   static void registerAll(ServletContext servletContext) throws ServletException {
      Servlets[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Servlets servlet = var1[var3];
         servlet.registrar.register(servletContext);
      }

   }
}