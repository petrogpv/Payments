package ua.gordeichuk.payments.controller.command;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.controller.Validator;
import ua.gordeichuk.payments.controller.exception.ServiceException;
import ua.gordeichuk.payments.controller.service.CardService;
import ua.gordeichuk.payments.controller.util.Attribute;
import ua.gordeichuk.payments.controller.util.LogMessage;
import ua.gordeichuk.payments.controller.util.Message;
import ua.gordeichuk.payments.controller.util.Page;
import ua.gordeichuk.payments.model.entity.Card;
import ua.gordeichuk.payments.model.entity.User;

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
            List<Card> cards = cardService.findManyByUser(user.getId());
            request.setAttribute(Attribute.CARDS, cards);
            return Page.DEPOSIT;

    }
}
