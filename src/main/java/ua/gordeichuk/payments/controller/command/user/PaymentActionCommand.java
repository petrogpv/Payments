package ua.gordeichuk.payments.controller.command.user;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.util.Validator;
import ua.gordeichuk.payments.controller.parameters.dto.PaymentParamDto;
import ua.gordeichuk.payments.controller.parameters.dto.TransferParamDto;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PaymentActionCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(PaymentActionCommand.class);
    private CardService cardService;
    private Validator validator = Validator.getInstance();

    public PaymentActionCommand(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException {
        PaymentParamDto paramDto = extractPaymentParamDto(request);
        TransferParamDto transferParamDto = validateAndGetParams(paramDto);
        cardService.transfer(transferParamDto);
        request.setAttribute(Attribute.MESSAGE, new MessageDtoBuilder()
                .getMessage(Message.PAYMENT_SUCCESS));
        writeLog(paramDto);
        return Path.PAYMENT;
    }

    private TransferParamDto validateAndGetParams(PaymentParamDto paymentParamDto)
            throws ServiceException {
        Long cardIdFrom = validator
                .validateAndParseCardNumber(paymentParamDto.getCardIdFromString());
        Long cardIdTo = validator
                .validateAndParseCardNumber(paymentParamDto.getCardIdToString());
        validator.validateCardsNotEquals(cardIdFrom, cardIdTo);
        Long value = validator
                .validateAndParseMoneyValue(paymentParamDto.getValueString());
        return new TransferParamDto.Builder()
                .setCarIdFrom(cardIdFrom)
                .setCardIdTo(cardIdTo)
                .setValue(value)
                .build();
    }

    private void writeLog(PaymentParamDto paramDto) {
        StringBuilder sbLog = new StringBuilder();
        sbLog.append(LogMessage.PAYMENT_OK)
                .append(LogMessage.FROM)
                .append(paramDto.getCardIdFromString())
                .append(LogMessage.TO)
                .append(paramDto.getCardIdToString())
                .append(LogMessage.VALUE)
                .append(paramDto.getValueString());
        LOGGER.info(sbLog.toString());
    }

    private PaymentParamDto extractPaymentParamDto(HttpServletRequest request) {
        return new PaymentParamDto.Builder()
                .setCardIdFromString(request.getParameter(Attribute.CARD_FROM))
                .setCardIdToString(request.getParameter(Attribute.CARD_TO))
                .setValueString(request.getParameter(Attribute.VALUE))
                .build();
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.PAYMENT_FAILED
                + LogMessage.FROM
                + request.getParameter(Attribute.CARD_FROM) +
                LogMessage.TO
                + request.getParameter(Attribute.CARD_TO));
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.PAYMENT;
    }
}
