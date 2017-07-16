package ua.gordeichuk.payments.controller.command.admin;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.Command;
import ua.gordeichuk.payments.util.Validator;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.CardStatus;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.AccountService;
import ua.gordeichuk.payments.service.TransactionService;
import ua.gordeichuk.payments.service.UserService;
import ua.gordeichuk.payments.util.Attribute;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ManageUsersActionCommand implements Command {
    private UserService userService;
    private TransactionService transactionService;
    private AccountService accountService;
    private Validator validator = Validator.getInstance();
    private static final Logger LOGGER = Logger.getLogger(ManageUsersActionCommand.class);

    public ManageUsersActionCommand(UserService userService,
                                    TransactionService transactionService,
                                    AccountService accountService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter(Attribute.LOGIN);
        validator.validateLogin(login);
        User user = userService.findUserByLogin(login);
        validator.validateNotAdmin(user);
        checkUserToDelete(request, login);
        List<Account> accounts = accountService.findAccountsByUser(user);

        List<Long> accountsIdToAddCard = getAccountsToAddCard(accounts);
        List<Long> accountsIdToDelete = transactionService.getAccountsToDelete(accounts);
        List<Long> cardsIdToDelete = transactionService.getCardsToDelete(accounts);

        request.setAttribute(Attribute.ACCOUNTS_TO_ADD_CARD, accountsIdToAddCard);
        request.setAttribute(Attribute.ACCOUNTS_TO_DELETE, accountsIdToDelete);
        request.setAttribute(Attribute.CARDS_TO_DELETE, cardsIdToDelete);
        request.setAttribute(Attribute.ACCOUNTS, accounts);
        request.setAttribute(Attribute.USER_MANAGE, user);
        return Path.MANAGE_USERS;
    }

    private List<Long> getAccountsToAddCard(List<Account> accounts) {
        List<Long> accountsIdToAddCard = new ArrayList<>();
        List<Card> cards;
        boolean flag = true;
        for (Account account : accounts) {
            cards = account.getCards();
            for (Card card : cards) {
                flag = true;
                if (card.getStatus().equals(CardStatus.ACTIVE)
                        || card.getStatus().equals(CardStatus.LOCKED)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                accountsIdToAddCard.add(account.getId());
            }
        }
        return accountsIdToAddCard;
    }


    private void checkUserToDelete(HttpServletRequest request, String login) {
        int transactionsCountByUser = transactionService.findCountByLogin(login);
        if (transactionsCountByUser == 0) {
            request.setAttribute(Attribute.DELETE_USER, true);
        }
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) {
        LOGGER.warn(LogMessage.MANAGE_SEARCH_FAILED);
        request.setAttribute(Attribute.MESSAGE_ERROR, e.getMessage());
        return Path.MANAGE_USERS;
    }

}