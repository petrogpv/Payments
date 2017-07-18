package ua.gordeichuk.payments.controller.command.admin;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Path;
import ua.gordeichuk.payments.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCardCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DeleteCardCommand.class);
    private Validator validator = Validator.getInstance();
    private CardService cardService;

    public DeleteCardCommand(CardService accountService) {
        this.cardService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String cardIdString = request.getParameter(Attribute.CARD);
        Long cardId = validator.validateAndParseCardNumber(cardIdString);
        cardService.deleteCard(cardId);
        writeMessageAndLog(request, cardId);
        return Path.MANAGE_USERS;
    }

    private void writeMessageAndLog(HttpServletRequest request, Long cardId) {
        MessageDto messageDto = new MessageDtoBuilder()
                .addMessage(Message.CARD_SUCCESSFULLY_DELETED)
                .addMessage(String.valueOf(cardId))
                .build();
        LOGGER.info(messageDto.getLogMessage());
        request.setAttribute(Attribute.MESSAGE, messageDto.getMessage());
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.CARD_DELETION_FAILED);
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.MANAGE_USERS;
    }
}