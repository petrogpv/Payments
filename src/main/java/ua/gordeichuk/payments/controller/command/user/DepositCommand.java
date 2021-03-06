package ua.gordeichuk.payments.controller.command.user;

import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.CardService;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DepositCommand implements Command {
    private CardService cardService;

    public DepositCommand(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        User user = (User) request.getSession().getAttribute(Attribute.USER);
        List<Card> cards = cardService.findCardsByUser(user);
        request.setAttribute(Attribute.CARDS, cards);
        return Page.DEPOSIT;

    }
}
