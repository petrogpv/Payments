package ua.gordeichuk.payments.controller.command.user;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.util.Validator;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DepositActionCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DepositCommand.class);
    private CardService cardService;
    private Validator validator = Validator.getInstance();

    public DepositActionCommand(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String cardIdString = request.getParameter(Attribute.CARD);
        String valueString = request.getParameter(Attribute.VALUE);
        Long cardId = validator.validateAndParseCardNumber(cardIdString);
        Long value = validator.validateAndParseMoneyValue(valueString);
        cardService.deposit(cardId, value);
        String message = new MessageDtoBuilder().getMessage(Message.PAYMENT_SUCCESS);
        request.setAttribute(Attribute.MESSAGE, message);
        LOGGER.info(LogMessage.DEPOSIT_OK + LogMessage.TO + cardId + LogMessage.VALUE + value);
        return Path.DEPOSIT;

    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.DEPOSIT_FAILED + LogMessage.TO + request.getParameter(Attribute.CARD));
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.DEPOSIT;
    }
}
