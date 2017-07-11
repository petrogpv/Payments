package ua.gordeichuk.payments.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Администратор on 05.07.2017.
 */
public class Parser {
    private static final String LOCALE_EN = "en_US";
    private static final String LOCALE_UA = "uk_UA";
    private static final String DATE_PATTERN_EN = "MM.dd.yyyy";
    private static final String DATE_PATTERN_UA = "dd.MM.yyyy";


    public static Date parseStringToDate(String dateString, String locale) throws ParseException {
        String datePattern = getDataPattern(locale);
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        return formatter.parse(dateString);
    }
    public static String parseDateToString(Date date, String locale){
        String datePattern = getDataPattern(locale);
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        return formatter.format(date);
    }


    private static String getDataPattern(String locale) {
        String datePattern = null;
        if (locale == null || locale.equals(LOCALE_EN)) {
            datePattern = DATE_PATTERN_EN;
        } else if (locale.equals(LOCALE_UA)) {
            datePattern = DATE_PATTERN_UA;
        }
        return datePattern;
    }
}
