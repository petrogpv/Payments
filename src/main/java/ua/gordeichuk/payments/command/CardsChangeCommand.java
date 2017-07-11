package ua.gordeichuk.payments.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.Validator;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 11.07.2017.
 */
public class CardsChangeCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CardsChangeCommand.class);
    private static final String UNLOCK = "Unlock";
    private static final String DEACTIVATE = "Deactivate";
    private static final String LOCK = "Lock";
    private CardService cardService;
    private Validator validator = Validator.getInstance();
    public CardsChangeCommand(CardService cardService) {
        this.cardService = cardService;
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String cardIdString = request.getParameter(Attribute.CARD);
        String action = request.getParameter(Attribute.ACTION);
        Long cardId = validator.validateAndParseCardNumber(cardIdString);
        changeCardStatus(cardId, action);
        writeMessageAndLog(request);
        return Path.MANAGE_CARDS;
    }

    private void changeCardStatus (Long cardId, String action) throws ServiceException {
        if(action.equals(UNLOCK)){
            cardService.unlockCard(cardId);
        }else if(action.equals(DEACTIVATE)){
            cardService.deactivateCard(cardId);
        }else if(action.equals(LOCK)){
            cardService.lockCard(cardId);
        }else {
            String errorMessage = LogMessage.CARDS_ACTION_ATTRIBUTE_ERROR + action;
            LOGGER.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    private void writeMessageAndLog(HttpServletRequest request){
        List<String> messageKeys = new ArrayList<>();
        messageKeys.add(Message.CARD_STATUS_CHANGED);
        messageKeys.add(request.getParameter(Attribute.CARD));
        MessageDto messageDto = Message.getMessageDto(messageKeys);
        request.setAttribute(Attribute.MESSAGE, messageDto.getMessage());
        LOGGER.info(messageDto.getLogMessage());

    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.CHANGE_CARD_STATUS_FAILED
                + request.getParameter(Attribute.CARD) +
                LogMessage.TO
                + request.getParameter(Attribute.ACTION));
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.MANAGE_CARDS;
    }
}
