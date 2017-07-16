package ua.gordeichuk.payments.filter;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.service.localization.LocaleContext;
import ua.gordeichuk.payments.service.localization.SessionLocale;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

public class LocaleHandlingFilter implements Filter {
    private static final String EN_LOCALE = "en_US";
    private static final String UA_LOCALE = "uk_UA";
    private static final Logger LOGGER = Logger.getLogger(LocaleHandlingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String localeString = request.getParameter(Attribute.LOCALE);
        manageLocaleContext(localeString, request);
        chain.doFilter(req, res);
    }

    private void manageLocaleContext(String localeString, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        if (localeString != null) {
            if (localeString.equals(EN_LOCALE)) {
                request.getSession().setAttribute(Attribute.LOCALE, EN_LOCALE);
                SessionLocale.getInstance().setLocale(sessionId, SessionLocale.ENGLISH_LOCALE);
                LOGGER.info(LogMessage.SET_LOCALE + localeString);
            } else if (localeString.equals(UA_LOCALE)) {
                request.getSession().setAttribute(Attribute.LOCALE, UA_LOCALE);
                SessionLocale.getInstance().setLocale(sessionId, SessionLocale.UKRAINIAN_LOCALE);
                LOGGER.info(LogMessage.SET_LOCALE + localeString);
            }
        }
        Locale locale = SessionLocale.getInstance().getLocale(sessionId);
        LocaleContext.setLocale(locale);
    }

    @Override
    public void destroy() {

    }
}
