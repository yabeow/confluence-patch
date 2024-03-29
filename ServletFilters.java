// Source code is decompiled from a .class file using FernFlower decompiler.
package com.atlassian.confluence.impl.webapp;

import com.atlassian.confluence.cache.TransactionalCacheFactoryCleanupFilter;
import com.atlassian.confluence.core.datetime.RequestTimeThreadLocalFilter;
import com.atlassian.confluence.impl.seraph.AuthenticatorMetricsFilter;
import com.atlassian.confluence.impl.servlet.HibernateGetConnectionFilter;
import com.atlassian.confluence.impl.struts.ConfluenceStrutsPrepareFilter;
import com.atlassian.confluence.impl.vcache.VCacheRequestContextFilter;
import com.atlassian.confluence.impl.webapp.sudo.WebSudoFilter;
import com.atlassian.confluence.internal.diagnostics.HttpRequestMonitoringFilter;
import com.atlassian.confluence.internal.diagnostics.ipd.http.IpdHttpMonitoringFilter;
import com.atlassian.confluence.internal.web.filter.spring.IgnoreWebAsyncManagerFilter;
import com.atlassian.confluence.jmx.JmxFilter;
import com.atlassian.confluence.security.trust.seraph.ConfluenceTrustedApplicationsFilter;
import com.atlassian.confluence.servlet.FourOhFourErrorLoggingFilter;
import com.atlassian.confluence.setup.ConfluenceEncodingFilter;
import com.atlassian.confluence.tenant.TenantGateFilter;
import com.atlassian.confluence.util.ClusterHeaderFilter;
import com.atlassian.confluence.util.ConfluenceErrorFilter;
import com.atlassian.confluence.util.ConfluenceVelocityFilter;
import com.atlassian.confluence.util.LoggingContextFilter;
import com.atlassian.confluence.util.MobileAppRequestFilter;
import com.atlassian.confluence.util.RequestCacheThreadLocalFilter;
import com.atlassian.confluence.util.UserLoggingContextFilter;
import com.atlassian.confluence.util.UserNameHeaderFilter;
import com.atlassian.confluence.util.UserThreadLocalFilter;
import com.atlassian.confluence.util.message.MessagesDecoratorFilter;
import com.atlassian.confluence.util.profiling.ConfluenceProfilingFilter;
import com.atlassian.confluence.util.profiling.ProfilingSiteMeshFilter;
import com.atlassian.confluence.web.ConfluenceJohnsonFilter;
import com.atlassian.confluence.web.filter.ConfluenceCachingFilter;
import com.atlassian.confluence.web.filter.ConfluenceOpenSessionInViewFilter;
import com.atlassian.confluence.web.filter.ConfluenceSecurityFilter;
import com.atlassian.confluence.web.filter.ConfluenceTimeoutFilter;
import com.atlassian.confluence.web.filter.DebugFilter;
import com.atlassian.confluence.web.filter.DropIfNotSetupFilter;
import com.atlassian.confluence.web.filter.HttpSessionRegistrarFilter;
import com.atlassian.confluence.web.filter.LanguageExtractionFilter;
import com.atlassian.confluence.web.filter.MauEventFilter;
import com.atlassian.confluence.web.filter.ResponseOutputStreamFilter;
import com.atlassian.confluence.web.filter.ThreadLocalCacheFilter;
import com.atlassian.confluence.web.filter.TranslationModeFilter;
import com.atlassian.confluence.web.filter.ZipkinTracingFilter;
import com.atlassian.confluence.web.filter.validateparam.RequestParamValidationFilter;
import com.atlassian.core.filters.ExpiresFilter;
import com.atlassian.core.filters.HeaderSanitisingFilter;
import com.atlassian.core.filters.ServletContextThreadLocalFilter;
import com.atlassian.seraph.filter.LoginFilter;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.struts2.dispatcher.filter.StrutsExecuteFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

enum ServletFilters {
   DEBUG_BEFORE(ServletFilterRegistrar.filter("debug-before", new DebugFilter("before")).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR), new UrlPattern[]{UrlPattern.ALL_URLS})),
   HTTP_HEADER_SECURITY_FILTER(ServletFilterRegistrar.filter("httpHeaderSecurity", new ConfluenceHttpHeaderSecurityFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   DROP_IF_NOT_SETUP_FILTER(ServletFilterRegistrar.filter("dropIfNotSetupFilter", new DropIfNotSetupFilter()).mapping(new UrlPattern[]{UrlPattern.START_HEARTBEAT_ACTIVITY, UrlPattern.REST_TINYMCE, UrlPattern.REST_QUICKRELOAD, UrlPattern.REST_ANALYTICS, UrlPattern.REST_SYNCHRONY_INTEROP, UrlPattern.REST_MYWORK})),
   REQUEST_MONITORING_FILTER(ServletFilterRegistrar.filter("httpRequestMonitoringFilter", new HttpRequestMonitoringFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   IPD_REQUEST_MONITORING_FILTER(ServletFilterRegistrar.filter("ipd-http-request-monitoring-filter", new IpdHttpMonitoringFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   LOG_404S(ServletFilterRegistrar.filter("log404s", new FourOhFourErrorLoggingFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   HEADER_SANITISER(ServletFilterRegistrar.filter("header-sanitiser", new HeaderSanitisingFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   THREAD_LOCAL_CACHE(ServletFilterRegistrar.filter("threadLocalCache", new ThreadLocalCacheFilter()).mapping(new UrlPattern[]{UrlPattern.ACTIONS, UrlPattern.VELOCITY, UrlPattern.JSP, UrlPattern.DISPLAY, UrlPattern.LABELS, UrlPattern.EXPORTWORD, UrlPattern.RPC, UrlPattern.STATIC, UrlPattern.REST, UrlPattern.PLUGIN_SERVLET, UrlPattern.DOWNLOAD})),
   ENCODING(ServletFilterRegistrar.filter("encoding", new ConfluenceEncodingFilter()).mapping(new UrlPattern[]{UrlPattern.VELOCITY, UrlPattern.DISPLAY, UrlPattern.ACTIONS, UrlPattern.DOWNLOAD, UrlPattern.PLUGIN_SERVLET, UrlPattern.LABELS, UrlPattern.STATIC})),
   JOHNSON(ServletFilterRegistrar.filter("johnson", new ConfluenceJohnsonFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), new UrlPattern[]{UrlPattern.JSP, UrlPattern.VELOCITY, UrlPattern.DISPLAY, UrlPattern.DOWNLOAD, UrlPattern.LABELS, UrlPattern.ACTIONS, UrlPattern.PLUGIN_SERVLET})),
   RESPONSE_OUTPUT_STREAM_FILTER(ServletFilterRegistrar.filter("ResponseOutputStreamFilter", new ResponseOutputStreamFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   ZIPKIN_FILTER(ServletFilterRegistrar.filter("zipkinFilter", new ZipkinTracingFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR), new UrlPattern[]{UrlPattern.ALL_URLS})),
   REQUEST_CACHE(ServletFilterRegistrar.filter("requestcache", new RequestCacheThreadLocalFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR), new UrlPattern[]{UrlPattern.ALL_URLS})),
   LOGGING_CONTEXT_FILTER(ServletFilterRegistrar.filter("LoggingContextFilter", new LoggingContextFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   VCACHE_REQUEST_CONTEXT(ServletFilterRegistrar.filter("vcache-request-context", new VCacheRequestContextFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   LANGUAGE(ServletFilterRegistrar.filter("language", new LanguageExtractionFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   HIBERNATE_GET_CONNECTION_FILTER(ServletFilterRegistrar.filter("hibernate-get-connection", new HibernateGetConnectionFilter()).mapping(DispatcherType.ERROR, new UrlPattern[]{UrlPattern.JSP_SYSTEM_ERROR})),
   TRANSLATION_MODE(ServletFilterRegistrar.filter("translation-mode", new TranslationModeFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   REQUEST_PARAM_CLEANER(ServletFilterRegistrar.filter("request-param-cleaner", new RequestParamValidationFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   IGNORE_ASYNCWEBMANAGER(ServletFilterRegistrar.filter("ignore-webasyncmanager", new IgnoreWebAsyncManagerFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   MOBILE_APP_REQUEST_FILTER(ServletFilterRegistrar.filter("MobileAppRequestFilter", new MobileAppRequestFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   OPEN_TENANT_GATE_FILTER(ServletFilterRegistrar.filter("OpenTenantGateFilter", new TenantGateFilter()).initParam("permit", "true").mapping(new UrlPattern[]{UrlPattern.ADMIN_APP_TRUST_CERTIFICATE, UrlPattern.SETUP, UrlPattern.BOOTSTRAP, UrlPattern.JOHNSON, UrlPattern.JSP_ERRORS})),
   PLUGIN_FILTERS_AFTER_ENCODING(PluginFilterLocation.AFTER_ENCODING),
   CACHING(ServletFilterRegistrar.filter("caching", new ConfluenceCachingFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   LEGACY_REMOTE_API_EVENT_PUBLISHING_FILTER(ServletFilterRegistrar.filter("legacyRemoteApiEventPublishingFilter", new DelegatingFilterProxy()).mapping(new UrlPattern[]{UrlPattern.AXIS_SOAP, UrlPattern.RPC, UrlPattern.REST_PROTOTYPE})),
   CONFLUENCE_VELOCITY_FILTER(ServletFilterRegistrar.filter("confluenceVelocityFilter", new ConfluenceVelocityFilter()).mapping(new UrlPattern[]{UrlPattern.VELOCITY})),
   REQUEST_TIME_FILTER(ServletFilterRegistrar.filter("RequestTimeFilter", new RequestTimeThreadLocalFilter()).mapping(new UrlPattern[]{UrlPattern.ACTIONS, UrlPattern.VELOCITY, UrlPattern.DISPLAY, UrlPattern.PLUGIN_SERVLET, UrlPattern.DOWNLOAD, UrlPattern.LABELS, UrlPattern.STATIC, UrlPattern.QUESTIONS})),
   PROFILING(ServletFilterRegistrar.filter("profiling", new ConfluenceProfilingFilter()).initParam("activate.param", "profile").initParam("autostart", "false").mapping(new UrlPattern[]{UrlPattern.ACTIONS, UrlPattern.VELOCITY, UrlPattern.DISPLAY, UrlPattern.PLUGIN_SERVLET, UrlPattern.DOWNLOAD, UrlPattern.LABELS, UrlPattern.REST, UrlPattern.RPC, UrlPattern.STATIC})),
   THREAD_LOCAL_ERROR_COLLECTION(ServletFilterRegistrar.filter("thread-local-error-collection", new ConfluenceErrorFilter()).mapping(new UrlPattern[]{UrlPattern.ACTIONS, UrlPattern.VELOCITY, UrlPattern.DISPLAY, UrlPattern.PLUGIN_SERVLET, UrlPattern.DOWNLOAD, UrlPattern.LABELS, UrlPattern.STATIC})),
   SESSION_IN_VIEW(ServletFilterRegistrar.filter("sessioninview", new ConfluenceOpenSessionInViewFilter()).mapping(new UrlPattern[]{UrlPattern.ACTIONS, UrlPattern.VELOCITY, UrlPattern.DISPLAY, UrlPattern.PLUGIN_SERVLET, UrlPattern.LABELS, UrlPattern.STATIC, UrlPattern.REST})),
   CLUSTER_HEADER_FILTER(ServletFilterRegistrar.filter("ClusterHeaderFilter", new ClusterHeaderFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   PLUGIN_FILTERS_BEFORE_LOGIN(PluginFilterLocation.BEFORE_LOGIN),
   AUTHENTICATOR_METRICS(ServletFilterRegistrar.filter("authenticator-metrics", new AuthenticatorMetricsFilter()).mapping(DispatcherType.REQUEST, new UrlPattern[]{UrlPattern.DISPLAY})),
   LOGIN(ServletFilterRegistrar.filter("login", new LoginFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR), new UrlPattern[]{UrlPattern.ALL_URLS})),
   TRUSTED_APP(ServletFilterRegistrar.filter("trustedapp", new ConfluenceTrustedApplicationsFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   SECURITY(ServletFilterRegistrar.filter("security", new ConfluenceSecurityFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR), new UrlPattern[]{UrlPattern.ALL_URLS})),
   SESSION(ServletFilterRegistrar.filter("session", new HttpSessionRegistrarFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR), new UrlPattern[]{UrlPattern.ALL_URLS})),
   TIMEOUT(ServletFilterRegistrar.filter("timeout", new ConfluenceTimeoutFilter()).initParam("urlPatternsToExclude", "/rest/quickreload/**,/rest/mywork/latest/status/notification/count").mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   USER_THREAD_LOCAL(ServletFilterRegistrar.filter("userthreadlocal", new UserThreadLocalFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR), new UrlPattern[]{UrlPattern.ACTIONS, UrlPattern.VELOCITY, UrlPattern.DISPLAY, UrlPattern.LABELS, UrlPattern.EXPORTWORD, UrlPattern.STATIC})),
   MAU_EVENT_FILTER(ServletFilterRegistrar.filter("maueventfilter", new MauEventFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   USERNAME_HEADER(ServletFilterRegistrar.filter("usernameheader", new UserNameHeaderFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), new UrlPattern[]{UrlPattern.ALL_URLS})),
   USER_LOGGING_CONTEXT_FILTER(ServletFilterRegistrar.filter("UserLoggingContextFilter", new UserLoggingContextFilter()).mapping(new UrlPattern[]{UrlPattern.ALL_URLS})),
   SERVLET_CONTEXT_THREAD_LOCAL(ServletFilterRegistrar.filter("servletcontextthreadlocal", new ServletContextThreadLocalFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), new UrlPattern[]{UrlPattern.ACTIONS, UrlPattern.VELOCITY, UrlPattern.DISPLAY, UrlPattern.LABELS, UrlPattern.PLUGINS, UrlPattern.STATIC, UrlPattern.DOWNLOAD, UrlPattern.REST}).mapping(new UrlPattern[]{UrlPattern.RPC})),
   TRANSACTIONAL_CACHE_FACTORY_CLEANUP_FILTER(ServletFilterRegistrar.filter("transactionalCacheFactoryCleanupFilter", new TransactionalCacheFactoryCleanupFilter()).mapping(DispatcherType.REQUEST, new UrlPattern[]{UrlPattern.ACTIONS, UrlPattern.VELOCITY, UrlPattern.DISPLAY, UrlPattern.LABELS, UrlPattern.STATIC, UrlPattern.DOWNLOAD, UrlPattern.REST}).mapping(new UrlPattern[]{UrlPattern.RPC})),
   JMX(ServletFilterRegistrar.filter("jmx", new JmxFilter()).mapping(new UrlPattern[]{UrlPattern.ACTIONS, UrlPattern.DISPLAY})),
   URL_REWRITE_FILTER(ServletFilterRegistrar.filter("UrlRewriteFilter", new UrlRewriteFilter()).mapping(new UrlPattern[]{UrlPattern.STATIC, UrlPattern.IMAGES_ICONS})),
   STRUTS2_PREPARE(ServletFilterRegistrar.filter("struts2-prepare", new ConfluenceStrutsPrepareFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR), new UrlPattern[]{UrlPattern.ALL_URLS})),
   WEB_SUDO(ServletFilterRegistrar.filter("websudo-security", new WebSudoFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR), new UrlPattern[]{UrlPattern.ALL_URLS})),
   PLUGIN_FILTERS_BEFORE_DECORATION(PluginFilterLocation.BEFORE_DECORATION),
   SITEMESH(ServletFilterRegistrar.filter("sitemesh", new ProfilingSiteMeshFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR), new UrlPattern[]{UrlPattern.ALL_URLS})),
   MESSAGES_DECORATOR_FILTER(ServletFilterRegistrar.filter("messagesDecoratorFilter", new MessagesDecoratorFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), new UrlPattern[]{UrlPattern.ACTIONS, UrlPattern.VELOCITY, UrlPattern.DISPLAY, UrlPattern.PLUGIN_SERVLET, UrlPattern.LABELS})),
   EXPIRES_ONE_HOUR(ServletFilterRegistrar.filter("expires-one-hour", new ExpiresFilter()).initParam("expiryTimeInSeconds", "3600").mapping(new UrlPattern[]{UrlPattern.JS})),
   PLUGIN_FILTERS_BEFORE_DISPATCH(PluginFilterLocation.BEFORE_DISPATCH),
   STRUTS2_EXECUTE(ServletFilterRegistrar.filter("struts2-execute", new StrutsExecuteFilter()).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR), new UrlPattern[]{UrlPattern.ACTIONS})),
   DEBUG_AFTER(ServletFilterRegistrar.filter("debug-after", new DebugFilter("after")).mapping(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR), new UrlPattern[]{UrlPattern.ALL_URLS}));

   private final ServletContextRegistrar registrar;

   private ServletFilters(ServletContextRegistrar registrar) {
      this.registrar = registrar;
   }

   private ServletFilters(ServletFilterRegistrar.Builder builder) {
      this((ServletContextRegistrar)builder.build());
   }

   static void registerAll(ServletContext servletContext) throws ServletException {
      ServletFilters[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ServletFilters filter = var1[var3];
         filter.registrar.register(servletContext);
      }

   }
}