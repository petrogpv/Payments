package ua.gordeichuk.payments.controller.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Validator;
import ua.gordeichuk.payments.controller.exception.ServiceException;
import ua.gordeichuk.payments.controller.service.CardService;
import ua.gordeichuk.payments.controller.util.Attribute;
import ua.gordeichuk.payments.controller.util.Path;
import ua.gordeichuk.payments.model.entity.Card;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 07.07.2017.
 */
public class ManageActionCommand implements ua.gordeichuk.payments.controller.Command {
    private  static final String CARD_BY_NUMBER = "cardByNumber";
    private  static final String CARD_BY_LOGIN = "cardByLogin";
    private  static final String USER_BY_LOGIN = "userByLogin";
    private static final Logger LOGGER = Logger.getLogger(ManageActionCommand.class);
    private CardService cardService;

    public ManageActionCommand(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String searchType = request.getParameter(Attribute.SEARCH_TYPE);
        String searchParameter = request.getParameter(Attribute.SEARCH_PARAMETER);
        String searchWithoutTransactions = request.getParameter(Attribute.SEARCH_PARAMETER);

        if(searchType.equals(CARD_BY_NUMBER)){
           Long cardId =  Validator.validateAndParseCardNumber(searchParameter);
            Card card = cardService.findCardById(cardId);
            List cards = new ArrayList();
            cards.add(card);
            request.setAttribute(Attribute.CARDS, cards);
        }


        return Path.MANAGE;
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        return null;
    }
}
