package ua.gordeichuk.payments.util;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Валерий on 25.06.2017.
 */
public final class Message {

    public static final String CARD_NOT_ACTIVE = "failed.card.notActive";
    public static final String NOT_ENOUGH_MONEY = "failed.notEnoughMoney";
    public static final String CARD_NOT_EXIST = "failed.card.notExist";
    public static final String LOGIN_WRONG_FORMAT = "signin.wrong.loginFormat";
    public static final String PASSWORD_WRONG_FORMAT = "signin.wrong.passwordFormat";
    public static final String WRONG_LOGIN = "signin.wrong.login";
    public static final String WRONG_PASSWORD =  "signin.wrong.password" ;
    public static final String USER_IS_NOT_SIGNED_UP = "signin.wrong.userNotSignedUp";
    public static final String USER_IS_ALREADY_SIGNED_UP = "signup.wrong.user";
    public static final String PASSWORDS_NOT_IDENTICAL = "signup.wrong.passwordsNotIdentical";
    public static final String SIGNED_IN_SUCCESS = "signin.success";
    public static final String CARD_IS_NOT_AVAILABLE = "failed.card.notAvailable";
    public static final String WRONG_CARD_FORMAT = "failed.wrong.card";
    public static final String WRONG_MONEY_VALUE_FORMAT = "failed.wrong.moneyValue";
    public static final String PAYMENT_SUCCESS = "success.payment";
    public static final String CARDS_EQUALS = "failed.cardsEquals";
    public static final String CARD_LOCKED_SUCCESS = "success.lock";
    public static final String WRONG_DATE_FORMAT = "failed.wrong.date";
    public static final String TRANSACTIONS_NOT_FOUND = "failed.transactions.notFound";
    public static final String TRANSACTION_NOT_FOUND = "failed.transaction.notFound";
    public static final String FOR_CARD = "forCard";
    public static final String DATE_FROM = "date.from";
    public static final String DATE_TO = "date.to";
    public static final String TRANSACTION_TYPE = "transaction.type";
    public static final String TRANSACTION_TYPE_PATH = "transaction.type.";
    public static final String WRONG_SORT_TYPE = "failed.wrong.sortType";
    public static final String WRONG_TRANCASTION_TYPE = "failed.wrong.transactionType";
    public static final String CARD_STATUS_CHANGED = "success.cardStatusChanged";


    public static final String WRONG_DATES_RELATIONS = "failed.wrong.datesRelations";
    public static final Locale ENGLISH_LOCALE = new Locale("en", "US");
    public static final Locale LOCALE_LOCALE = new Locale("uk", "UA");
    private static final Logger LOGGER = Logger.getLogger(Message.class);
    private static final String BUNDLE_NAME = "/localization/messages";


    private static ResourceBundle messagesBundle = ResourceBundle.getBundle(BUNDLE_NAME, ENGLISH_LOCALE);;
    private static ResourceBundle logBundle = ResourceBundle.getBundle(BUNDLE_NAME, ENGLISH_LOCALE);

    public static void setLocale(Locale locale) {

        try {
            messagesBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        } catch (Exception e) {
            LOGGER.error(LogMessage.RB_READ_ERROR + BUNDLE_NAME + locale);
        }

    }

    public static String getTransactionTypeMessage(String type) {
        String key = TRANSACTION_TYPE_PATH + type;
        return messagesBundle.getString(key);
    }
    public static String getMessage(String key) {
        return messagesBundle.getString(key);
    }
    public static String getLogMessage(String key) {
        return logBundle.getString(key);
    }
    public static String getMessageForDto(ResourceBundle bundle, String key){
        String message;
        try {
            message = bundle.getString(key);
        } catch (MissingResourceException e) {
            message = key;
        }
        return message;
    }

    public static MessageDto getMessageDto (List<String> messageKeys){
        MessageDto messageDto = new MessageDto();
        StringBuilder sbMessage = new StringBuilder();
        StringBuilder sbLog = new StringBuilder();
        for (String key: messageKeys) {
            sbMessage.append(getMessageForDto(messagesBundle, key));
            sbLog.append(getMessageForDto(logBundle, key));
        }
        messageDto.setMessage(sbMessage.toString());
        messageDto.setLogMessage(sbLog.toString());
        return messageDto;



    }


}
