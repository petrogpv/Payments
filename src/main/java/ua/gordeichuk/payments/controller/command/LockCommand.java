package ua.gordeichuk.payments.controller.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.util.Validator;
import ua.gordeichuk.payments.controller.command.user.DepositCommand;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Page;
import ua.gordeichuk.payments.util.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LockCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DepositCommand.class);
    private CardService cardService;
    private Validator validator = Validator.getInstance();

    public LockCommand(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String cardIdString = request.getParameter(Attribute.CARD);

        if (cardIdString == null) {
            User user = (User) request.getSession().getAttribute(Attribute.USER);
            List<Card> cards = cardService.findCardsByUser(user);
            request.setAttribute(Attribute.CARDS, cards);
            return Page.LOCK;

        } else {
            Long cardId = validator.validateAndParseCardNumber(cardIdString);
            cardService.lockCard(cardId);
            request.setAttribute(Attribute.MESSAGE, new MessageDtoBuilder()
                    .getMessage(Message.CARD_LOCKED_SUCCESS));
            LOGGER.info(LogMessage.CARD_STATUS_CHANGED + cardId);
            return Path.DEFAULT;
        }

    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.LOCKING_FAILED + request.getParameter(Attribute.CARD));
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.LOCK;
    }
}
