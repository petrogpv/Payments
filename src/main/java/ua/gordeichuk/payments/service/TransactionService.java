package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.daojdbc.DaoConnection;
import ua.gordeichuk.payments.daojdbc.DaoFactory;
import ua.gordeichuk.payments.dto.entityparam.TransactionParamDto;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.util.Message;
import ua.gordeichuk.payments.daoentity.TransactionDao;
import ua.gordeichuk.payments.entity.Transaction;
import ua.gordeichuk.payments.util.MessageDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Валерий on 26.06.2017.
 */
public class TransactionService {
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private TransactionService() {
    }

    private static class Holder {
        static final TransactionService INSTANCE = new TransactionService();
    }

    public static TransactionService getInstance() {
        return Holder.INSTANCE;
    }

        public List<Transaction> findTransactionsByParamDto(TransactionParamDto  parameterDto) throws ServiceException {
            try (DaoConnection connection = daoFactory.getConnection()) {
                connection.begin();
                TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
                List<Transaction> transactions  = transactionDao.findManyByParamDTO(parameterDto);
                connection.commit();
                return transactions;
            }
    }


    protected Transaction findTransaction(Long transactionId, DaoConnection connection) throws ServiceException {
        TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
        Optional<Transaction> optional = transactionDao.find(transactionId);
        if (!optional.isPresent()) {
            MessageDto messageDto = new MessageDto.Builder()
                    .addMessage(Message.TRANSACTION_NOT_FOUND)
                    .addMessage(String.valueOf(transactionId))
                    .build();
            LOGGER.error(messageDto.getLogMessage());
            throw new ServiceException(messageDto.getMessage());
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
//    public List<Transaction> findCardsByUser(User user) {
//        try (DaoConnection connection = daoFactory.getConnection()) {
//            connection.begin();
//            TransactionDao transactionDao = daoFactory.createTransactionDao(connection);
//            List<Transaction> list = transactionDao.findCardsByUser(user.getId());
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
