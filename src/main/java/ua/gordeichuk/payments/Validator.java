package ua.gordeichuk.payments;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.enums.SortType;
import ua.gordeichuk.payments.util.Message;
import ua.gordeichuk.payments.util.Parser;
import ua.gordeichuk.payments.entity.enums.TransactionType;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Валерий on 30.06.2017.
 */
public class Validator {
    private static final Logger LOGGER = Logger.getLogger(Validator.class);
    private static final String LOGIN_EMAIL_REGEX =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,20}$";

    private static final String ACCOUNT_NUMBER_REGEX = "^2605[\\d]{6}$";
    private static final String CARD_NUMBER_REGEX = "^5505(\\d){8}$";
    private static final String MONEY_VALUE_REGEX = "^[^0]\\d*([\\.|,]\\d\\d?)?$";  // check with one digit!!!!
    private static final String SPLIT_VALUE_REGEX = "\\.|,";
    private static final String DOUBLE_ZERO = "00";
    private static final String ZERO = "0";
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String LOCALE_EN = "en_US";
    private static final String LOCALE_UA = "uk_UA";
    private static final String DATE_PATTERN_EN = "MM.dd.yyyy";
    private static final String DATE_PATTERN_UA = "dd.MM.yyyy";
    private static final String ALL = "ALL";

    private Validator() {
    }

    private static class Holder{
        static final Validator INSTANCE = new Validator();
    }

    public static Validator getInstance(){
        return Holder.INSTANCE;
    }

    public Date validateAndParseDate(String dateString, String locale)
            throws ServiceException {
        Date date;
        if (dateString == null || dateString.length() == 0) {
            date = null;
        } else {
            try{
                date = Parser.parseStringToDate(dateString, locale);
            } catch (ParseException e) {
                String logMessage = Message.getLogMessage(Message.WRONG_DATE_FORMAT) + dateString;
                LOGGER.warn(logMessage);
                String message = Message.getMessage(Message.WRONG_DATE_FORMAT) + dateString;
                throw new ServiceException(message);
            }
        }
        return date;
    }

    private String getDataPattern(String locale) {
        String datePattern = null;
        if (locale == null || locale.equals(LOCALE_EN)) {
            datePattern = DATE_PATTERN_EN;
        } else if (locale.equals(LOCALE_UA)) {
            datePattern = DATE_PATTERN_UA;
        }
        return datePattern;
    }

    public void validateDates(Date dateFrom, Date dateTo) throws ServiceException {
        Date now = Calendar.getInstance().getTime();

        if (dateTo != null && dateFrom != null && dateFrom.after(dateTo)
                || dateFrom != null && dateFrom.after(now)) {
            String logMessage = Message.getLogMessage(Message.WRONG_DATES_RELATIONS);
            LOGGER.warn(logMessage);
            String message = Message.getMessage(Message.WRONG_DATES_RELATIONS);
            throw new ServiceException(message);
        }
    }

    public Long validateAndParseCardNumber(String cardNumber) throws ServiceException {
        if (cardNumber == null || !cardNumber.matches(CARD_NUMBER_REGEX)) {
            String logMessage = Message.getLogMessage(Message.WRONG_CARD_FORMAT) + cardNumber;
            LOGGER.warn(logMessage);
            String message = Message.getMessage(Message.WRONG_CARD_FORMAT) + cardNumber;
            throw new ServiceException(message);
        }
        return Long.parseLong(cardNumber);
    }


    public Long validateAndParseMoneyValue(String value) throws ServiceException {
        if (value == null || !value.matches(MONEY_VALUE_REGEX)) {
            String logMessage = Message.getLogMessage(Message.WRONG_MONEY_VALUE_FORMAT) + value;
            LOGGER.warn(logMessage);
            String message = Message.getMessage(Message.WRONG_MONEY_VALUE_FORMAT) + value;
            throw new ServiceException(message);
        } else {
            return parseMoneyValue(value);
        }
    }
    private Long parseMoneyValue (String value){
        String resultValue;
        String [] valueParts = value.split(SPLIT_VALUE_REGEX);
        if(valueParts.length == 1){
            resultValue = value + DOUBLE_ZERO;
        }else if(valueParts[1].length() == 1){
            resultValue = valueParts[0] + valueParts[1] + ZERO;
        }else {
            resultValue = valueParts[0] + valueParts[1];
        }
        return Long.parseLong(resultValue);
    }

    public void validateLogin(String login) throws ServiceException {
        if (login == null || !login.matches(LOGIN_EMAIL_REGEX)) {
            String logMessage = Message.getLogMessage(Message.LOGIN_WRONG_FORMAT) + login;
            LOGGER.warn(logMessage);
            String message = Message.getMessage(Message.LOGIN_WRONG_FORMAT) + login;
            throw new ServiceException(message);
        }
    }

    public void validatePassword(String password) throws ServiceException {
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            String logMessage = Message.getLogMessage(Message.PASSWORD_WRONG_FORMAT);
            LOGGER.warn(logMessage);
            String message = Message.getMessage(Message.PASSWORD_WRONG_FORMAT);
            throw new ServiceException(message);
        }
    }

    public void validatePasswordsOnSignUp(String password, String passwordConfirm)
            throws ServiceException {
        validatePassword(password);
        validatePassword(passwordConfirm);
        if (!password.equals(passwordConfirm)) {
            String logMessage = Message.getLogMessage(Message.PASSWORDS_NOT_IDENTICAL);
            LOGGER.warn(logMessage);
            String message = Message.getMessage(Message.PASSWORDS_NOT_IDENTICAL);
            throw new ServiceException(message);
        }
    }

    public void validateCardsNotEquals(Long cardIdFrom, Long cardIdTo)
            throws ServiceException {
        if (cardIdFrom == null || cardIdFrom.equals(cardIdTo)) {
            String logMessage = Message.getLogMessage(Message.CARDS_EQUALS);
            LOGGER.warn(logMessage);
            String message = Message.getMessage(Message.CARDS_EQUALS);
            throw new ServiceException(message);
        }
    }

    public SortType validateSortType(String sortType) throws ServiceException {
        try {
            return SortType.valueOf(sortType);
        }catch (IllegalArgumentException e){
            String logMessage = Message.getLogMessage(Message.WRONG_SORT_TYPE) + sortType;
            LOGGER.warn(logMessage);
            String message = Message.getMessage(Message.WRONG_SORT_TYPE);
            throw new ServiceException(message);
        }
    }

    public TransactionType validateTransactionType(String transactionTypeString) throws ServiceException {
        if(transactionTypeString.equals(ALL)){
            return null;
        }
        try {
            return TransactionType.valueOf(transactionTypeString);
        } catch (IllegalArgumentException e) {
            String logMessage = Message.getLogMessage(Message.WRONG_TRANCASTION_TYPE) + transactionTypeString;
            LOGGER.warn(logMessage);
            String message = Message.getMessage(Message.WRONG_TRANCASTION_TYPE);
            throw new ServiceException(message);
        }

    }
}
