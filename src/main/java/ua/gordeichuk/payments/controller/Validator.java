package ua.gordeichuk.payments.controller;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.exception.ServiceException;
import ua.gordeichuk.payments.controller.service.UserService;
import ua.gordeichuk.payments.controller.util.Message;

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
    private static final String MONEY_VALUE_REGEX = "^[^0]\\d+([\\.|,]\\d\\d?)?$";


    public static Long validateAndParseCardNumber(String cardNumber) throws ServiceException {
        if (cardNumber == null || !cardNumber.matches(CARD_NUMBER_REGEX)) {
            String logMessage = Message.getLogMessage(
                    Message.WRONG_CARD_FORMAT) + cardNumber;
            LOGGER.warn(logMessage);
            String message = Message.getMessage(
                    Message.WRONG_CARD_FORMAT) + cardNumber;
            throw new ServiceException(message);
        }
        return Long.parseLong(cardNumber);
    }
    public static Long validateAndParseMoneyValue(String value) throws ServiceException {
        if (value == null || !value.matches(MONEY_VALUE_REGEX)) {
            String logMessage = Message.getLogMessage(
                    Message.WRONG_MONEY_VALUE_FORMAT) + value;
            LOGGER.warn(logMessage);
            String message = Message.getMessage(
                    Message.WRONG_MONEY_VALUE_FORMAT) + value;
            throw new ServiceException(message);
        }
        return Long.parseLong(value);
    }


    public static void validateLogin(String login) throws ServiceException {
        if (login == null ||!login.matches(LOGIN_EMAIL_REGEX)) {
            String logMessage = Message.getLogMessage(
                    Message.LOGIN_WRONG_FORMAT)  + login;
            LOGGER.warn(logMessage);
            String message = Message.getMessage(
                    Message.LOGIN_WRONG_FORMAT)  + login;
            throw new ServiceException(message);
        }
    }
    public static void validatePassword(String password) throws ServiceException {
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            String logMessage = Message.getLogMessage(
                    Message.PASSWORD_WRONG_FORMAT);
            LOGGER.warn(logMessage);
            String message = Message.getMessage(
                    Message.PASSWORD_WRONG_FORMAT);
            throw new ServiceException(message);
        }
    }

    public static void validatePasswordsOnSignUp(String password, String passwordConfirm) throws ServiceException {
        validatePassword(password);
        validatePassword(passwordConfirm);
        if(!password.equals(passwordConfirm)){
            String logMessage = Message.getLogMessage(
                    Message.PASSWORDS_NOT_IDENTICAL);
            LOGGER.warn(logMessage);
            String message = Message.getMessage(
                    Message.PASSWORDS_NOT_IDENTICAL);
            throw new ServiceException(message);
        }
    }

    public static void validateCardsNotEquals(Long cardIdFrom, Long cardIdTo) throws ServiceException {
        if(cardIdFrom == null || cardIdFrom.equals(cardIdTo)){
            String logMessage = Message.getLogMessage(
                    Message.CARDS_EQUALS);
            LOGGER.warn(logMessage);
            String message = Message.getMessage(
                    Message.CARDS_EQUALS);
            throw new ServiceException(message);
        }
    }
}
