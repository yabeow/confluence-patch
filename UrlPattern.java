package com.atlassian.confluence.impl.webapp;

/* loaded from: UrlPattern.class */
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
    JSP("*.jsp"),
    ERROR_500_JSP("/500page.jsp"),
    ADMIN_APP_TRUST_CERTIFICATE("/admin/appTrustCertificate"),
    SETUP("/setup/*"),
    BOOTSTRAP("/bootstrap/*"),
    JOHNSON("/johnson/*"),
    JOHNSON_ANALYTICS("/johnson/analytics/*"),
    JOHNSON_DATA("/johnson/data"),
    JOHNSON_DISMISS_EVENTS("/johnson/events/dismiss"),
    ERRORS_JSP("/errors.jsp"),
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
    JCAPTCHA("/jcaptcha/*");
    
    private final String pattern;

    UrlPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.pattern;
    }
}