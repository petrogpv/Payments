package ua.gordeichuk.payments.controller.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.dao.DaoConnection;
import ua.gordeichuk.payments.controller.dao.DaoFactory;
import ua.gordeichuk.payments.model.daoentity.AccountDao;
import ua.gordeichuk.payments.model.daoentity.CardDao;
import ua.gordeichuk.payments.model.daoentity.TransactionDao;
import ua.gordeichuk.payments.model.entity.Account;
import ua.gordeichuk.payments.model.entity.Card;
import ua.gordeichuk.payments.model.entity.Transaction;
import ua.gordeichuk.payments.model.entity.enums.CardStatus;
import ua.gordeichuk.payments.model.entity.enums.TransactionType;
import ua.gordeichuk.payments.controller.exception.ServiceException;
import ua.gordeichuk.payments.controller.util.ExceptionMessage;
import ua.gordeichuk.payments.controller.util.LogMessage;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Валерий on 26.06.2017.
 */
public class CardService {
    private static final Logger LOGGER = Logger.getLogger(CardService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private static class Holder {
        static final CardService INSTANCE = new CardService();
    }

    public static CardService getInstance() {
        return CardService.Holder.INSTANCE;
    }

    public List<Card> findManyByUser(Long userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            CardDao cardDao = daoFactory.createCardDao(connection);
            List<Card> list = cardDao.findManyByUser(userId);
            connection.commit();
            return list;
        }
    }

    public List<Card> findManyByUserAndCardStatus(Long userId, String cardStatus) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            CardDao cardDao = daoFactory.createCardDao(connection);
            List<Card> list = cardDao.findManyByUserAndCardStatus(userId, cardStatus);
            connection.commit();
            return list;
        }
    }

    public List<Card> findManyByAccount(Long accountId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            CardDao cardDao = daoFactory.createCardDao(connection);
            List<Card> list = cardDao.findManyByAccount(accountId);
            connection.commit();
            return list;
        }
    }

    public List<Card> findManyByAccountAndCardStatus(Long accountId, String cardStatus) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            CardDao cardDao = daoFactory.createCardDao(connection);
            List<Card> list = cardDao.findManyByAccountAndCardStatus(accountId, cardStatus);
            connection.commit();
            return list;
        }
    }

    public boolean transfer(Long cardIdFrom, Long cardIdTo, Long sum) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();

            Card cardFrom = findCard(cardIdFrom, connection);
            checkCardForActivity(cardFrom);
            Card cardTo = findCard(cardIdTo, connection);

            updateCardDeduct(cardFrom, sum, connection);
            updateCardAdd(cardTo, sum, connection);


            saveTransactions(cardFrom, cardTo, sum, connection);

            LOGGER.info(LogMessage.TRANSFER_SUCCESSFUL + cardFrom.getId()
                    + LogMessage.TO + cardTo.getId());
            return true;
        }
    }

    private void saveTransactions(Card cardFrom, Card cardTo, Long sum, DaoConnection connection) {
        Date date = Calendar.getInstance().getTime();

        Transaction transactionFrom = new Transaction.Builder()
                .setCard(cardFrom)
                .setBalanceAfter(cardFrom.getAccount().getBalance())
                .setValue(sum)
                .setType(TransactionType.OUTCOME)
                .setDate(date)
                .build();

        Transaction transactionTo = new Transaction.Builder()
                .setCard(cardTo)
                .setBalanceAfter(cardTo.getAccount().getBalance())
                .setValue(sum)
                .setType(TransactionType.INCOME)
                .setDate(date)
                .build();

        TransactionDao transactionDao = daoFactory.createTransactionDao(connection);

        transactionDao.create(transactionFrom);
        transactionDao.create(transactionTo);

        transactionFrom.setTransaction(transactionTo);
        transactionTo.setTransaction(transactionFrom);

        transactionDao.update(transactionFrom);
        transactionDao.update(transactionTo);
    }

    private void updateCardDeduct(Card card, Long sum, DaoConnection connection) throws ServiceException {
        Account accountFrom = card.getAccount();
        Long balance = getBalanceAndCheckSufficiency(sum, accountFrom);
        accountFrom.setBalance(balance - sum);
        AccountDao accountDao = daoFactory.createAccountDao(connection);
        accountDao.update(accountFrom);

    }
    private void updateCardAdd(Card card, Long sum, DaoConnection connection){
        Account accountTo = card.getAccount();
        Long balanceBeforeTo = accountTo.getBalance();
        accountTo.setBalance(balanceBeforeTo + sum);
        AccountDao accountDao = daoFactory.createAccountDao(connection);
        accountDao.update(accountTo);
    }

    private Long getBalanceAndCheckSufficiency(Long sum, Account accountFrom) throws ServiceException {
        Long balanceBeforeFrom = accountFrom.getBalance();
        if (balanceBeforeFrom < sum) {
            String logMessage = ExceptionMessage.getLogMessage(ExceptionMessage.NOT_ENOUGH_MONEY)
                    + accountFrom.getId();
            LOGGER.error(logMessage);
            String message = ExceptionMessage.getMessage(ExceptionMessage.NOT_ENOUGH_MONEY)
                    + accountFrom.getId();
            throw new ServiceException(message);
        }
        return balanceBeforeFrom;
    }

    private Card findCard(Long cardId, DaoConnection connection) throws ServiceException {
        CardDao cardDao = daoFactory.createCardDao(connection);
        Optional<Card> optional = cardDao.find(cardId);
        if (!optional.isPresent()) {
            String logMessage = ExceptionMessage.getLogMessage(ExceptionMessage.CARD_NOT_EXIST)
                    + cardId;
            LOGGER.error(logMessage);
            String message = ExceptionMessage.getMessage(ExceptionMessage.CARD_NOT_EXIST)
                    + cardId;
            throw new ServiceException(message);
        }
        return optional.get();
    }

    private void checkCardForActivity(Card cardFrom) throws ServiceException {
        if (!cardFrom.getStatus().equals(CardStatus.ACTIVE)) {
            String logMessage = ExceptionMessage.getLogMessage(ExceptionMessage.CARD_NOT_ACTIVE)
                    + cardFrom.getId();
            LOGGER.error(logMessage);
            String message = ExceptionMessage.getMessage(ExceptionMessage.CARD_NOT_ACTIVE)
                    + cardFrom.getId();
            throw new ServiceException(message);
        }
    }

    public boolean deposit(Card card, Long sum) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();

            Account account = card.getAccount();
            account.setBalance(account.getBalance() + sum);
            AccountDao accountDao = daoFactory.createAccountDao(connection);
            accountDao.update(account);
            connection.commit();
            LOGGER.info(LogMessage.DEPOSITING_SUCCESSFUL);
            return true;
        }
    }
}
