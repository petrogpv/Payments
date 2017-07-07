package ua.gordeichuk.payments.controller.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.daojdbc.DaoConnection;
import ua.gordeichuk.payments.controller.daojdbc.DaoFactory;
import ua.gordeichuk.payments.controller.exception.ServiceException;
import ua.gordeichuk.payments.controller.util.Message;
import ua.gordeichuk.payments.controller.daoentity.TransactionDao;
import ua.gordeichuk.payments.model.entity.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Валерий on 26.06.2017.
 */
public class TransactionService {
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private static class Holder {
        static final TransactionService INSTANCE = new TransactionService();
    }

    public static TransactionService getInstance() {
        return Holder.INSTANCE;
    }

    public List<Transaction> findManyByCardAndByDatesAndByType(Long cardId,
                                                               Date dateFrom,
                                                               Date dateTo,
                                                               String type) throws ServiceException {

        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
            List<Transaction> transactions = new ArrayList<>();

            if (dateFrom == null && dateTo == null) {
                if(type == null){
                    transactions = transactionDao.findManyByCard(cardId);

                } else {
                    transactions = transactionDao.findManyByCardAndType(cardId, type);
                }
            } else if (dateFrom != null && dateTo == null) {
                if(type == null){
                    transactions = transactionDao.findManyByCardAndDateAfter(cardId, dateFrom);
                } else {
                    transactions = transactionDao.findManyByCardAndDateAfterAndType(cardId, dateFrom, type);
                }
            } else if (dateFrom == null && dateTo != null) {
                if(type == null){
                    transactions = transactionDao.findManyByCardAndDateBefore(cardId, dateTo);
                } else {
                    transactions = transactionDao.findManyByCardAndDateBeforeAndType(cardId, dateTo, type);
                }
            } else if (dateFrom != null && dateTo != null) {
                if(type == null){
                    transactions = transactionDao.findManyByCardAndDateBetween(cardId, dateFrom, dateTo);
                } else {
                    transactions = transactionDao.findManyByCardAndDateBetweenAndType(cardId, dateFrom, dateTo, type);
                }

            }

            setRelativeTransactions(transactions, connection);
            transactions.stream().forEach(System.out::println);

            connection.commit();
            return transactions;
        }
    }

    public List<Transaction> findManyByCardAndByDates(Long cardId,
                                                      Date dateFrom,
                                                      Date dateTo) throws ServiceException {
        return findManyByCardAndByDatesAndByType(cardId, dateFrom, dateTo, null);

    }

    private void setRelativeTransactions(List<Transaction> transactions, DaoConnection connection)
            throws ServiceException {

        for (Transaction transaction : transactions) {
            Long relativeId = transaction.getRelativeTransaction().getId();
            if (!(relativeId == null || relativeId == 0)) {
                Transaction relTransaction = findTransaction(relativeId, connection);
                transaction.setRelativeTransaction(relTransaction);
            }

        }
    }

    protected Transaction findTransaction(Long transactionId, DaoConnection connection) throws ServiceException {
        TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
        Optional<Transaction> optional = transactionDao.find(transactionId);
        if (!optional.isPresent()) {
            String logMessage = Message.getLogMessage(Message.TRANSACTION_NOT_FOUND)
                    + transactionId;
            LOGGER.error(logMessage);
            String message = Message.getMessage(Message.TRANSACTION_NOT_FOUND)
                    + transactionId;
            throw new ServiceException(message);
        }
        return optional.get();
    }
//    public List<Transaction> findManyByCardAndOrderType(Card card) {
//        try (DaoConnection connection = daoFactory.getConnection()) {
//            connection.begin();
//            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
//            List<Transaction> list = transactionDao.findManyByCardAndOrderType(card.getId());
//            connection.commit();
//            return list;
//        }
//    public List<Transaction> findManyByAccount(Account account) {
//        try (DaoConnection connection = daoFactory.getConnection()) {
//            connection.begin();
//            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
//            List<Transaction> list = transactionDao.findManyByAccount(account.getId());
//            connection.commit();
//            return list;
//        }
//    }
//
//    public List<Transaction> findManyByAccountAndByDate(Account account, Date date) {
//        try (DaoConnection connection = daoFactory.getConnection()) {
//            connection.begin();
//            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
//            List<Transaction> list = transactionDao.findManyByAccountAndByDate(account.getId(), date);
//            connection.commit();
//            return list;
//        }
//    }
//
//    public List<Transaction> findManyByAccountAndByDateBetween(Account account, Date dateFrom, Date dateTo) {
//        try (DaoConnection connection = daoFactory.getConnection()) {
//            connection.begin();
//            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
//            List<Transaction> list = transactionDao.findManyByAccountAndByDateBetween(account.getId(), dateFrom, dateTo);
//            connection.commit();
//            return list;
//        }
//    }
//
//    public List<Transaction> findManyByUser(User user) {
//        try (DaoConnection connection = daoFactory.getConnection()) {
//            connection.begin();
//            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
//            List<Transaction> list = transactionDao.findManyByUser(user.getId());
//            connection.commit();
//            return list;
//        }
//    }
//
//    public List<Transaction> findManyByUserAndByDate(User user, Date date) {
//        try (DaoConnection connection = daoFactory.getConnection()) {
//            connection.begin();
//            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
//            List<Transaction> list = transactionDao.findManyByUserAndByDate(user.getId(), date);
//            connection.commit();
//            return list;
//        }
//    }
//
//    public List<Transaction> findManyByUserAndByDateBetween(User user, Date dateFrom, Date dateTo) {
//        try (DaoConnection connection = daoFactory.getConnection()) {
//            connection.begin();
//            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
//            List<Transaction> list = transactionDao.findManyByUserAndByDateBetween(user.getId(), dateFrom, dateTo);
//            connection.commit();
//            return list;
//        }
//    }

}
