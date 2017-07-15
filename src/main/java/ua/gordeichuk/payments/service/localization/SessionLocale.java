package ua.gordeichuk.payments.service.localization;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Валерий on 13.07.2017.
 */
public class SessionLocale {
    public static final Locale ENGLISH_LOCALE = new Locale("en", "US");
    public static final Locale UKRAINIAN_LOCALE= new Locale("uk", "UA");
    private Map<String, Locale> locales = new HashMap<>();

    private SessionLocale() {
    }
    private static class Holder {
        static final SessionLocale INSTANCE = new SessionLocale();
    }

    public static SessionLocale getInstance(){
        return Holder.INSTANCE;
    }
    public void setLocale(String sessionId, Locale locale) {
        locales.put(sessionId, locale);
    }
    public Locale getLocale(String sessionId) {
        Locale locale = locales.get(sessionId);
        return locale==null?ENGLISH_LOCALE:locale;
    }
    public void removeLocale(String sessionId){
        locales.remove(sessionId);
    }

}
