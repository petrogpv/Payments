package ua.gordeichuk.payments.service;

import com.sun.corba.se.spi.legacy.connection.Connection;
import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.daoentity.AccountDao;
import ua.gordeichuk.payments.dao.daoentity.TransactionDao;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.Transaction;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.CardStatus;
import ua.gordeichuk.payments.entity.enums.TransactionType;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.util.LogMessages;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Валерий on 26.06.2017.
 */
public class TransactionService extends Service<Transaction> {
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class);
    private static final int TRANSACTION_FAILD_CARD_IS_NOT_ACTIVE = -1;
    private static final int TRANSACTION_FAILD_NOT_ENOUGH_MONEY = 0;
    private static final int TRANSACTION_APPLIED = 1;

    protected TransactionService(String entityName) {
        super(TransactionDao.ENTITY_NAME);
    }

    @Override
    public void delete(Transaction entity) throws ServiceException {
        super.delete(entity);
    }

    public List<Transaction> findManyByCard(Card card) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            return transactionDao.findManyByCard(card.getId());
        }
    }

    public List<Transaction> findManyByAccount(Account account) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            return transactionDao.findManyByAccount(account.getId());
        }
    }

    public List<Transaction> findManyByAccountAndByDate(Account account, Date date) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            return transactionDao.findManyByAccountAndByDate(account.getId(), date);
        }
    }

    public List<Transaction> findManyByAccountAndByDateBetween(Account account, Date dateFrom, Date dateTo) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            return transactionDao.findManyByAccountAndByDateBetween(account.getId(), dateFrom, dateTo);
        }
    }

    public List<Transaction> findManyByUser(User user) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            return transactionDao.findManyByUser(user.getId());
        }
    }

    public List<Transaction> findManyByUserAndByDate(User user, Date date) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            return transactionDao.findManyByUserAndByDate(user.getId(), date);
        }
    }

    public List<Transaction> findManyByUserAndByDateBetween(User user, Date dateFrom, Date dateTo) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            return transactionDao.findManyByUserAndByDateBetween(user.getId(), dateFrom, dateTo);
        }
    }





}
