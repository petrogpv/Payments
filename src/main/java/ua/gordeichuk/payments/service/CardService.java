package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.daoentity.AccountDao;
import ua.gordeichuk.payments.dao.daoentity.CardDao;
import ua.gordeichuk.payments.dao.daoentity.TransactionDao;
import ua.gordeichuk.payments.dao.daoentity.UserDao;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.Transaction;
import ua.gordeichuk.payments.entity.enums.TransactionType;
import ua.gordeichuk.payments.util.LogMessages;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Валерий on 26.06.2017.
 */
public class CardService extends Service<Card> {
    private static final Logger LOGGER = Logger.getLogger(CardService.class);

    protected CardService(String entityName) {
        super(CardDao.ENTITY_NAME);
    }

    public List<Card> findManyByUser(Long userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            CardDao cardDao = (CardDao) daoFactory.createDao(entityName, connection);
            return cardDao.findManyByUser(userId);
        }
    }

    public List<Card> findManyByUserAndCardStatus(Long userId, String cardStatus) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            CardDao cardDao = (CardDao) daoFactory.createDao(entityName, connection);
            return cardDao.findManyByUserAndCardStatus(userId, cardStatus);
        }
    }

    public List<Card> findManyByAccount(Long accountId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            CardDao cardDao = (CardDao) daoFactory.createDao(entityName, connection);
            return cardDao.findManyByAccount(accountId);
        }
    }

    public List<Card> findManyByAccountAndCardStatus(Long accountId, String cardStatus) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            CardDao cardDao = (CardDao) daoFactory.createDao(entityName, connection);
            return cardDao.findManyByAccountAndCardStatus(accountId, cardStatus);
        }
    }

    public boolean transfer(Card cardFrom, Card cardTo, Long sum, DaoConnection connection) {
//        try (DaoConnection connection = daoFactory.getConnection()) {
//            connection.begin();

        TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(TransactionDao.ENTITY_NAME, connection);
        AccountDao accountDao = (AccountDao) daoFactory.createDao(AccountDao.ENTITY_NAME, connection);

        Account accountFrom = cardFrom.getAccount();
        Account accountTo = cardTo.getAccount();

        Long balanceBeforeFrom = accountFrom.getBalance();
        Long balanceBeforeTo = accountTo.getBalance();

        accountFrom.setBalance(balanceBeforeFrom - sum);
        accountTo.setBalance(balanceBeforeTo + sum);

        accountDao.update(accountFrom);
        accountDao.update(accountTo);

        Date date = Calendar.getInstance().getTime();

        Transaction transactionFrom = new Transaction.Builder()
                .setCard(cardFrom)
                .setBalanceBefore(balanceBeforeFrom)
                .setBalanceAfter(accountFrom.getBalance())
                .setValue(sum)
                .setType(TransactionType.OUTCOME)
                .setDate(date)
                .build();

        Transaction transactionTo = new Transaction.Builder()
                .setCard(cardTo)
                .setBalanceBefore(balanceBeforeTo)
                .setBalanceAfter(accountTo.getBalance())
                .setValue(sum)
                .setType(TransactionType.INCOME)
                .setDate(date)
                .build();

        transactionDao.create(transactionFrom);
        transactionDao.create(transactionTo);

        transactionFrom.setTransaction(transactionTo);
        transactionTo.setTransaction(transactionFrom);

        transactionDao.update(transactionFrom);
        transactionDao.update(transactionTo);
        LOGGER.info(LogMessages.TRANSFER_SUCCESSFUL + cardFrom.getId()
                + LogMessages.TO + cardTo.getId());
        return true;
//        }
    }

    public boolean deposit (Card card, Long sum){
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();

            Account account = card.getAccount();
            account.setBalance(account.getBalance() + sum);
            AccountDao accountDao = (AccountDao) daoFactory.createDao(AccountDao.ENTITY_NAME, connection);
            accountDao.update(account);
            LOGGER.info(LogMessages.DEPOSITING_SUCCESSFUL);
            return true;
        }
    }
}
