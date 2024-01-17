// Source code is decompiled from a .class file using FernFlower decompiler.
package com.atlassian.confluence.impl.webapp;

enum UrlPattern {
   ALL_URLS("/*"),
   START_HEARTBEAT_ACTIVITY("/json/startheartbeatactivity.action"),
   REST_TINYMCE("/rest/tinymce/*"),
   REST_QUICKRELOAD("/rest/quickreload/*"),
   REST_ANALYTICS("/rest/analytics/*"),
   REST_SYNCHRONY_INTEROP("/rest/synchrony-interop/*"),
   REST_MYWORK("/rest/mywork/*"),
   VELOCITY("*.vm"),
   DISPLAY("/display/*"),
   ACTIONS("*.action"),
   DOWNLOAD("/download/*"),
   PLUGIN_SERVLET("/plugins/servlet/*"),
   LABELS("/label/*"),
   STATIC("/s/*"),
   TINY_URL("/x/*"),
   ADMIN_APP_TRUST_CERTIFICATE("/admin/appTrustCertificate"),
   SETUP("/setup/*"),
   BOOTSTRAP("/bootstrap/*"),
   JOHNSON("/johnson/*"),
   JOHNSON_ANALYTICS("/johnson/analytics/*"),
   JOHNSON_DATA("/johnson/data"),
   JOHNSON_DISMISS_EVENTS("/johnson/events/dismiss"),
   AXIS_SOAP("/plugins/servlet/soap-axis1/*"),
   RPC("/rpc/*"),
   XML_RPC("/rpc/xmlrpc"),
   REST_PROTOTYPE("/rest/prototype/*"),
   REST("/rest/*"),
   QUESTIONS("/questions/*"),
   EXPORTWORD("/exportword"),
   PLUGINS("/plugins/*"),
   IMAGES_ICONS("/images/icons/*"),
   JS("*.js"),
   CSS("*.css"),
   STATUS("/status"),
   JCAPTCHA("/jcaptcha/*"),
   JSP("*.jsp"),
   JSPX("*.jspx"),
   JSP_NOOP("/noop.jsp"),
   JSP_ERRORS("/errors.jsp"),
   JSP_ERRORS_NOTFOUND("/errors/notfound.jsp"),
   JSP_SYSTEM_ERROR("/500page.jsp"),
   JSP_ADMIN_DEFAULT("/admin/default.jsp"),
   JSP_ADMIN_CLUSTER("/admin/cluster/hashclustername.jsp");

   private final String pattern;

   private UrlPattern(String pattern) {
      this.pattern = pattern;
   }

   public String toString() {
      return this.pattern;
   }
}