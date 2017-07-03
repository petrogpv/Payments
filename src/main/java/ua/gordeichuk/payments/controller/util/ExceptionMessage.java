package ua.gordeichuk.payments.controller.util;

import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Валерий on 25.06.2017.
 */
public final class ExceptionMessage {

    public static final String CARD_NOT_ACTIVE = "failed.cardNotActive";
    public static final String NOT_ENOUGH_MONEY = "failed.notEnoughMoney";
    public static final String CARD_NOT_EXIST = "failed.cardNotExist";
    public static final String LOGIN_WRONG_FORMAT = "signin.wrong.loginFormat";
    public static final String PASSWORD_WRONG_FORMAT = "signin.wrong.passwordFormat";
    public static final String WRONG_LOGIN = "signin.wrong.login";
    public static final String WRONG_PASSWORD =  "signin.wrong.password" ;
    public static final String USER_IS_NOT_SIGNED_UP = "signin.wrong.userNotSignedUp";
    public static final String USER_IS_ALREADY_SIGNED_UP = "signup.wrong.user";
    public static final String PASSWORDS_NOT_IDENTICAL = "signup.wrong.passwordsNotIdentical";

    public static final Locale ENGLISH_LOCALE = new Locale("en", "US");
    public static final Locale LOCALE_LOCALE = new Locale("uk", "UA");
    private static final Logger LOGGER = Logger.getLogger(ExceptionMessage.class);
    private static final String BUNDLE_NAME = "/localization/exceptions";
    private static ResourceBundle messagesBundle = ResourceBundle.getBundle(BUNDLE_NAME, ENGLISH_LOCALE);;
    private static ResourceBundle logBundle = ResourceBundle.getBundle(BUNDLE_NAME, ENGLISH_LOCALE);

    public static void setLocale(Locale locale) {

        try {
            messagesBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        } catch (Exception e) {
            LOGGER.error(LogMessage.RB_READ_ERROR + BUNDLE_NAME + locale);
        }

    }

    public static String getMessage(String key) {
        return messagesBundle.getString(key);
    }
    public static String getLogMessage(String key) {
        return logBundle.getString(key);
    }


}
