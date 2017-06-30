package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.daoentity.TransactionDao;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.Transaction;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.exception.ServiceException;

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

    private static class Holder{
        static final TransactionService INSTANCE = new TransactionService();
    }

    private TransactionService () {
        super(TransactionDao.ENTITY_NAME);
    }

    public static TransactionService getInstance(){
        return TransactionService.Holder.INSTANCE;
    }


    @Override
    public void delete(Transaction entity) throws ServiceException {
        super.delete(entity);
    }

    public List<Transaction> findManyByCard(Card card) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            List<Transaction> list = transactionDao.findManyByCard(card.getId());
            connection.commit();
            return list;
        }
    }

    public List<Transaction> findManyByAccount(Account account) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            List<Transaction> list = transactionDao.findManyByAccount(account.getId());
            connection.commit();
            return list;
        }
    }

    public List<Transaction> findManyByAccountAndByDate(Account account, Date date) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            List<Transaction> list = transactionDao.findManyByAccountAndByDate(account.getId(), date);
            connection.commit();
            return list;
        }
    }

    public List<Transaction> findManyByAccountAndByDateBetween(Account account, Date dateFrom, Date dateTo) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            List<Transaction> list = transactionDao.findManyByAccountAndByDateBetween(account.getId(), dateFrom, dateTo);
            connection.commit();
            return list;
        }
    }

    public List<Transaction> findManyByUser(User user) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            List<Transaction> list = transactionDao.findManyByUser(user.getId());
            connection.commit();
            return list;
        }
    }

    public List<Transaction> findManyByUserAndByDate(User user, Date date) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            List<Transaction> list = transactionDao.findManyByUserAndByDate(user.getId(), date);
            connection.commit();
            return list;
        }
    }

    public List<Transaction> findManyByUserAndByDateBetween(User user, Date dateFrom, Date dateTo) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = (TransactionDao) daoFactory.createDao(entityName, connection);
            List<Transaction> list = transactionDao.findManyByUserAndByDateBetween(user.getId(), dateFrom, dateTo);
            connection.commit();
            return list;
        }
    }





}
