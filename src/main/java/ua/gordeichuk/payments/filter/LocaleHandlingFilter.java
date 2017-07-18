package ua.gordeichuk.payments.filter;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.service.localization.LocaleContext;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class LocaleHandlingFilter implements Filter {
    private static final String EN_LOCALE_STRING = "en_US";
    private static final String UA_LOCALE_STRING = "uk_UA";
    public static final Locale ENGLISH_LOCALE = new Locale("en", "US");
    public static final Locale UKRAINIAN_LOCALE = new Locale("uk", "UA");
    private static final Logger LOGGER = Logger.getLogger(LocaleHandlingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String localeString = request.getParameter(Attribute.LOCALE);
        System.out.println(localeString);
        manageLocaleContext(localeString, request);
        chain.doFilter(req, res);
    }

    private void manageLocaleContext(String localeString, HttpServletRequest request) {
        setLocaleSessionAttribute(localeString, request);
        Locale locale = getCurrentLocale(request);
        LocaleContext.setLocale(locale);
    }

    private Locale getCurrentLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String localeString = (String) session.getAttribute(Attribute.LOCALE);
        Locale locale = null;
        if(localeString == null || localeString.equals(EN_LOCALE_STRING)){
            locale = ENGLISH_LOCALE;
        } else if(localeString.equals(UA_LOCALE_STRING)){
            locale = UKRAINIAN_LOCALE;
        }
        return locale;
    }

    private void setLocaleSessionAttribute(String localeString, HttpServletRequest request){
        if(localeString != null){
            if (localeString.equals(EN_LOCALE_STRING)) {
                request.getSession().setAttribute(Attribute.LOCALE, EN_LOCALE_STRING);
                LOGGER.info(LogMessage.SET_LOCALE + localeString);
            } else if (localeString.equals(UA_LOCALE_STRING)) {
                request.getSession().setAttribute(Attribute.LOCALE, UA_LOCALE_STRING);
                LOGGER.info(LogMessage.SET_LOCALE + localeString);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
