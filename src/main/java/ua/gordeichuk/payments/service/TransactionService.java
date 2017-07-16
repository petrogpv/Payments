package ua.gordeichuk.payments.service;

import ua.gordeichuk.payments.dao.TransactionDao;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.DaoFactory;
import ua.gordeichuk.payments.dao.parameters.dto.TransactionParamDto;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.Transaction;
import ua.gordeichuk.payments.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private TransactionService() {
    }


    private static class Holder {
        static final TransactionService INSTANCE = new TransactionService();
    }

    public static TransactionService getInstance() {
        return Holder.INSTANCE;
    }

    public List<Transaction> findTransactionsByParamDto(TransactionParamDto parameterDto) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
            List<Transaction> transactions = transactionDao.findManyByParamDTO(parameterDto);
            connection.commit();
            return transactions;
        }
    }

    public int findCountByLogin(String login) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
            int count = transactionDao.findCountByLogin(login);
            connection.commit();
            return count;
        }
    }

    public List<Long> getAccountsToDelete(List<Account> accounts) {
        List<Long> accountsIdToDelete = new ArrayList<>();
        Long accountId;
        if (!accounts.isEmpty()) {
            try (DaoConnection connection = daoFactory.getConnection()) {
                connection.begin();
                TransactionDao transactionDao = daoFactory.createTransactionDao(connection);

                for (Account account : accounts) {
                    accountId = account.getId();
                    if (transactionDao.findCountByAccount(accountId) == 0) {
                        accountsIdToDelete.add(accountId);
                    }
                }
                connection.commit();
            }
        }
        return accountsIdToDelete;
    }

    public List<Long> getCardsToDelete(List<Account> accounts) {
        List<Long> cardsIdToDelete = new ArrayList<>();
        if (!accounts.isEmpty()) {
            try (DaoConnection connection = daoFactory.getConnection()) {
                connection.begin();
                TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
                List<Card> cards;
                Long cardId;
                for (Account account : accounts) {
                    cards = account.getCards();
                    if (!cards.isEmpty()) {
                        for (Card card : cards) {
                            cardId = card.getId();
                            if (transactionDao.findCountByCard(cardId) == 0) {
                                cardsIdToDelete.add(cardId);
                            }
                        }
                    }
                }
                connection.commit();
            }
        }
        return cardsIdToDelete;
    }
}
