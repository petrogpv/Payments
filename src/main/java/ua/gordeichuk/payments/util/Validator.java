package ua.gordeichuk.payments.util;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.TransactionType;
import ua.gordeichuk.payments.entity.enums.UserRole;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.enums.SortType;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Consists of validation methods for service layer
 */
public class Validator {
    private static final Logger LOGGER = Logger.getLogger(Validator.class);
    public static final String LOGIN_EMAIL_REGEX =
            "^(?=.{1,50}$)[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,20}$";
    public static final String FIRST_NAME_REGEX = "^(?=.{1,50}$)[A-Z]{1}[a-z]+$";
    public static final String LAST_NAME_REGEX = "^(?=.{1,50}$)[A-Z]{1}[a-z]+([-][A-Z]{1}[a-z]+)?$";
    public static final String ACCOUNT_NUMBER_REGEX = "^2605[\\d]{6}$";
    public static final String CARD_NUMBER_REGEX = "^5505[\\d]{8}$";
    public static final String MONEY_VALUE_REGEX = "^(?=.{1,10}$)\\d+([\\.|,]\\d\\d?)?$";
    private static final String SPLIT_VALUE_REGEX = "\\.|,";
    private static final String DOUBLE_ZERO = "00";
    private static final String ZERO = "0";
    private static final String ALL = "ALL";

    private Validator() {
    }

    private static class Holder {
        static final Validator INSTANCE = new Validator();
    }

    public static Validator getInstance() {
        return Holder.INSTANCE;
    }

    public Date validateAndParseDate(String dateString)
            throws ServiceException {
        Date date;
        if (dateString == null || dateString.length() == 0) {
            date = null;
        } else {
            try {
                date = Parser.parseStringToDate(dateString);
            } catch (ParseException e) {
                MessageDto messageDto = new MessageDtoBuilder()
                        .addMessage(Message.WRONG_DATE_FORMAT)
                        .addMessage(dateString)
                        .build();
                LOGGER.warn(messageDto.getLogMessage());
                throw new ServiceException(messageDto.getMessage());
            }
        }
        return date;
    }

    public void validateDates(Date dateFrom, Date dateTo) throws ServiceException {
        Date now = Calendar.getInstance().getTime();

        if (dateTo != null && dateFrom != null && dateFrom.after(dateTo)
                || dateFrom != null && dateFrom.after(now)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.WRONG_DATES_RELATIONS)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
    }

    public Long validateAndParseCardNumber(String cardNumber) throws ServiceException {
        if (cardNumber == null || !cardNumber.matches(CARD_NUMBER_REGEX)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.WRONG_CARD_FORMAT)
                    .addMessage(cardNumber)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
        return Long.parseLong(cardNumber);
    }

    public Long validateAndParseAccountNumber(String accountId) throws ServiceException {
        if (accountId == null || !accountId.matches(ACCOUNT_NUMBER_REGEX)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.WRONG_ACCOUNT_FORMAT)
                    .addMessage(accountId)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
        return Long.parseLong(accountId);
    }


    public Long validateAndParseMoneyValue(String value) throws ServiceException {
        if (value == null || !value.matches(MONEY_VALUE_REGEX)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.WRONG_MONEY_VALUE_FORMAT)
                    .addMessage(value)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        } else {
            return parseMoneyValue(value);
        }
    }

    private Long parseMoneyValue(String value) {
        String resultValue;
        String[] valueParts = value.split(SPLIT_VALUE_REGEX);
        if (valueParts.length == 1) {
            resultValue = value + DOUBLE_ZERO;
        } else if (valueParts[1].length() == 1) {
            resultValue = valueParts[0] + valueParts[1] + ZERO;
        } else {
            resultValue = valueParts[0] + valueParts[1];
        }
        return Long.parseLong(resultValue);
    }

    public void validateLogin(String login) throws ServiceException {
        if (login == null || !login.matches(LOGIN_EMAIL_REGEX)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.WRONG_LOGIN_FORMAT)
                    .addMessage(login)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
    }

    public void validateFirstName(String firstName) throws ServiceException {
        if (firstName == null || !firstName.matches(FIRST_NAME_REGEX)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.WRONG_FIRST_NAME_FORMAT)
                    .addMessage(firstName)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
    }

    public void validateLastName(String lastName) throws ServiceException {
        if (lastName == null || !lastName.matches(LAST_NAME_REGEX)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.WRONG_LAST_NAME_FORMAT)
                    .addMessage(lastName)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
    }

    public void validatePassword(String password) throws ServiceException {
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.WRONG_PASSWORD_FORMAT)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
    }

    public void validatePasswordsOnSignUp(String password, String passwordConfirm)
            throws ServiceException {
        validatePassword(password);
        validatePassword(passwordConfirm);
        if (!password.equals(passwordConfirm)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.PASSWORDS_NOT_IDENTICAL)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
    }

    public void validateCardsNotEquals(Long cardIdFrom, Long cardIdTo)
            throws ServiceException {
        if (cardIdFrom == null || cardIdFrom.equals(cardIdTo)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.CARDS_EQUALS)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
    }

    public SortType validateSortType(String sortType)
            throws ServiceException {
        try {
            return SortType.valueOf(sortType);
        } catch (IllegalArgumentException e) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.WRONG_SORT_TYPE)
                    .addMessage(sortType)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
    }

    public TransactionType validateTransactionType(String transactionTypeString)
            throws ServiceException {
        if (transactionTypeString.equals(ALL)) {
            return null;
        }
        try {
            return TransactionType.valueOf(transactionTypeString);
        } catch (IllegalArgumentException e) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.WRONG_TRANSACTION_TYPE)
                    .addMessage(transactionTypeString)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }

    }

    public void validateNotAdmin(User user) throws ServiceException {
        if (user.getUserAuth().getRole().equals(UserRole.ADMIN)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.WRONG_ADMIN_CANNOT_MANAGE_HIMSELF)
                    .build();
            LOGGER.warn(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
        }
    }

}
