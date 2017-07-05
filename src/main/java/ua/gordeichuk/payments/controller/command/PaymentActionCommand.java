package ua.gordeichuk.payments.controller.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.controller.Validator;
import ua.gordeichuk.payments.controller.exception.ServiceException;
import ua.gordeichuk.payments.controller.service.CardService;
import ua.gordeichuk.payments.controller.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 05.07.2017.
 */
public class PaymentActionCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(PaymentActionCommand.class);
    private CardService cardService;

    public PaymentActionCommand(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

            String cardIdFromString = request.getParameter(Attribute.CARD_FROM);
            String cardIdToString = request.getParameter(Attribute.CARD_TO);
            String valueString = request.getParameter(Attribute.VALUE);


            Long cardIdFrom = Validator.validateAndParseCardNumber(cardIdFromString);
            Long cardIdTo = Validator.validateAndParseCardNumber(cardIdToString);
            Validator.validateCardsNotEquals(cardIdFrom, cardIdTo );

            Long value = Validator.validateAndParseMoneyValue(valueString);

            cardService.transfer(cardIdFrom, cardIdTo, value);
            request.setAttribute(Attribute.MESSAGE, Message.getMessage(Message.PAYMENT_SUCCESS));
            LOGGER.info(LogMessage.PAYMENT_OK + LogMessage.FROM + cardIdFrom + LogMessage.TO + cardIdTo +
                    LogMessage.VALUE + value);
        return Path.PAYMENT;
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.PAYMENT_FAILED + LogMessage.FROM + request.getParameter(Attribute.CARD_FROM) +
                LogMessage.TO + request.getParameter(Attribute.CARD_TO));
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.PAYMENT;
    }
}
