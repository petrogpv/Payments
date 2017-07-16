package ua.gordeichuk.payments.controller.command.admin;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.util.Validator;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ManageCardsActionCommand implements Command {
    private static final String CARD_BY_NUMBER = "cardByNumber";
    private static final String CARD_BY_LOGIN = "cardByLogin";
    private static final Logger LOGGER = Logger.getLogger(ManageCardsActionCommand.class);
    private CardService cardService;
    private Validator validator = Validator.getInstance();

    public ManageCardsActionCommand(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String searchType = request.getParameter(Attribute.SEARCH_TYPE);
        String searchParameter = request.getParameter(Attribute.SEARCH_PARAMETER);
        List<Card> cards = new ArrayList();
        if (searchType.equals(CARD_BY_NUMBER)) {
            Long cardId = validator.validateAndParseCardNumber(searchParameter);
            Card card = cardService.findCardById(cardId);
            cards.add(card);
        }
        if (searchType.equals(CARD_BY_LOGIN)) {
            validator.validateLogin(searchParameter);
            cards = cardService.findCardsByLogin(searchParameter);
        }
        request.setAttribute(Attribute.CARDS, cards);
        return Path.MANAGE_CARDS;
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.MANAGE_SEARCH_FAILED);
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.MANAGE_CARDS;
    }
}
