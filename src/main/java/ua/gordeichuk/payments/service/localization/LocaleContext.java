package ua.gordeichuk.payments.service.localization;

import java.util.Locale;

/**
 * Created by Валерий on 13.07.2017.
 */
public class LocaleContext {
    private static final ThreadLocal<Locale> threadLocal = new ThreadLocal<>();

    public static Locale getLocale() {
        return threadLocal.get();
    }
    public static void setLocale(Locale locale){
        threadLocal.set(locale);
    }
    public static void removeLocale(){
        threadLocal.remove();
    }
}
