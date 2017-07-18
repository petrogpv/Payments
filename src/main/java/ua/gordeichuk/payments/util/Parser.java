package ua.gordeichuk.payments.util;

import ua.gordeichuk.payments.service.localization.LocaleContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Parser {
    public static final Locale ENGLISH_LOCALE = new Locale("en", "US");
    public static final Locale UKRAINIAN_LOCALE = new Locale("uk", "UA");
    private static final String DATE_PATTERN_EN = "MM.dd.yyyy";
    private static final String DATE_PATTERN_UA = "dd.MM.yyyy";


    public static Date parseStringToDate(String dateString) throws ParseException {
        String datePattern = getDatePattern();
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        return formatter.parse(dateString);
    }

    private static String getDatePattern() {
        String datePattern = null;
        Locale locale = LocaleContext.getLocale();
        if (locale == null || locale.equals(ENGLISH_LOCALE)) {
            datePattern = DATE_PATTERN_EN;
        } else if (locale.equals(UKRAINIAN_LOCALE)) {
            datePattern = DATE_PATTERN_UA;
        }
        return datePattern;
    }
}
