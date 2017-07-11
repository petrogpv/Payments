package ua.gordeichuk.payments.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.Command;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.Page;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Администратор on 05.07.2017.
 */
public class DepositCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DepositCommand.class);
    private CardService cardService;

    public DepositCommand(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
            User user = (User)request.getSession().getAttribute(Attribute.USER);
            List<Card> cards = cardService.findCardsByUser(user.getId());
            request.setAttribute(Attribute.CARDS, cards);
            return Page.DEPOSIT;

    }
}
