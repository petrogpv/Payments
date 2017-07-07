package ua.gordeichuk.payments.controller.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.controller.Validator;
import ua.gordeichuk.payments.controller.exception.ServiceException;
import ua.gordeichuk.payments.controller.service.CardService;
import ua.gordeichuk.payments.controller.util.*;
import ua.gordeichuk.payments.model.entity.Card;
import ua.gordeichuk.payments.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Администратор on 04.07.2017.
 */
public class LockCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DepositCommand.class);
    private CardService cardService;

    public LockCommand(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String cardIdString = request.getParameter(Attribute.CARD);

        if( cardIdString == null){
            User user = (User)request.getSession().getAttribute(Attribute.USER);
            List<Card> cards = cardService.findManyByUser(user.getId());
            request.setAttribute(Attribute.CARDS, cards);
            return Page.LOCK;

        }else{
            Long cardId = Validator.validateAndParseCardNumber(cardIdString);
            cardService.lockCard(cardId);
            request.setAttribute(Attribute.MESSAGE, Message.getMessage(Message.CARD_LOCKED_SUCCESS));
            LOGGER.info(LogMessage.LOCKED_SUCCESSFUL + cardId);
            return Path.DEFAULT;
        }

    }
    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.LOCKING_FAILED + request.getParameter(Attribute.CARD));
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.DEFAULT;
    }
}
