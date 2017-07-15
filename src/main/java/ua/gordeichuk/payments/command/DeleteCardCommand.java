package ua.gordeichuk.payments.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.service.localization.SessionLocale;
import ua.gordeichuk.payments.Validator;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Валерий on 13.07.2017.
 */
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
        String sessionId = request.getSession().getId();
        Locale locale = SessionLocale.getInstance().getLocale(sessionId);
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