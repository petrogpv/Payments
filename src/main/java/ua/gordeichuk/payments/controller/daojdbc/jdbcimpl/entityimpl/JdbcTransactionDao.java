package ua.gordeichuk.payments.controller.daojdbc.jdbcimpl.entityimpl;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.daoentity.TransactionDao;
import ua.gordeichuk.payments.controller.daojdbc.jdbcimpl.JdbcEntityDao;
import ua.gordeichuk.payments.model.entity.Card;
import ua.gordeichuk.payments.model.entity.Transaction;
import ua.gordeichuk.payments.model.entity.enums.TransactionType;

import java.sql.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcTransactionDao extends JdbcEntityDao<Transaction> implements TransactionDao {

    private static final Logger LOGGER = Logger.getLogger(JdbcTransactionDao.class);
    private static final String FIND_BY_CARD_CONDITION = "findByCard";
    private static final String FIND_BY_ACCOUNT_CONDITION = "findByAccount";
    private static final String FIND_BY_USER_CONDITION = "findByUser";
    private static final String FIND_AND_BY_DATE = "findAndByDate";
    private static final String FIND_AND_BY_DATE_BETWEEN = "findAndByDateBetween";
    private static final String FIND_AND_BY_DATE_BEFORE = "findAndByDateBefore";
    private static final String FIND_AND_BY_DATE_AFTER = "findAndByDateAfter";
    private static final String FIND_AND_BY_TYPE = "findAndByType";
    private static final String ID = "transaction_id";
    private static final String RELATIVE_ID = "t_id";
    private static final String TYPE = "type";
    private static final String BALANCE_AFTER = "balance_after";
    private static final String VALUE = "value";
    private static final String DATE = "date";
    public static final int ID_INDEX = 7;

    public JdbcTransactionDao(Connection connection) {
        super(connection, ENTITY_NAME);
    }

    @Override
    protected Transaction extractEntityFromResultSet(ResultSet resultSet) throws SQLException {
        Card card = JdbcCardDao.extractCardFromResultSetLazy(resultSet);
        Transaction relativeTransaction = new Transaction.Builder()
                .setId(resultSet.getLong(RELATIVE_ID))
                .build();
        return new Transaction.Builder()
                .setId(resultSet.getLong(ID))
                .setTransaction(relativeTransaction)
                .setCard(card)
                .setType(TransactionType.valueOf(resultSet.getString(TYPE)))
                .setBalanceAfter(resultSet.getLong(BALANCE_AFTER))
                .setValue(resultSet.getLong(VALUE))
                .setDate(resultSet.getTimestamp(DATE))
                .build();
    }

    @Override
    protected void setEntityToPreparedStatement(Transaction transaction, PreparedStatement statement)
            throws SQLException {
        Transaction relativeTransaction = transaction.getRelativeTransaction();
        statement.setString(1, transaction.getType().name());
        statement.setLong(2, transaction.getCard().getId());
        if (relativeTransaction == null
                || relativeTransaction.getId() == null
                || relativeTransaction.getId() == 0) {
            statement.setNull(3, Types.BIGINT);
        } else {
            statement.setLong(3, relativeTransaction.getId());
        }
        statement.setLong(4, transaction.getBalanceAfter());
        statement.setLong(5, transaction.getValue());
        statement.setTimestamp(6, new Timestamp(transaction.getDate().getTime()));
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, transaction.getId());
        }
    }

    @Override
    public List<Transaction> findManyByCard(Long cardId) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_CARD_CONDITION);
        return findManyByCondition(sql, cardId);
    }

    @Override
    public List<Transaction> findManyByCardAndDateBetween(Long cardId, Date dateFrom, Date dateTo) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_CARD_CONDITION)
                + getSqlString(FIND_AND_BY_DATE_BETWEEN);
        return findManyByCondition(sql, cardId, dateFrom, dateTo);
    }

    @Override
    public List<Transaction> findManyByCardAndDateBefore(Long cardId, Date date) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_CARD_CONDITION)
                + getSqlString(FIND_AND_BY_DATE_BEFORE);
        return findManyByCondition(sql, cardId, date);
    }

    @Override
    public List<Transaction> findManyByCardAndDateAfter(Long cardId, Date date) {

        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_CARD_CONDITION)
                + getSqlString(FIND_AND_BY_DATE_AFTER);
        return findManyByCondition(sql, cardId, date);
    }
    @Override
    public List<Transaction> findManyByCardAndType(Long cardId, String type) {

        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_CARD_CONDITION)
                + getSqlString(FIND_AND_BY_TYPE);
        return findManyByCondition(sql, cardId, type);
    }

    @Override
    public List<Transaction> findManyByCardAndDateBetweenAndType(Long cardId,
                                                                 Date dateFrom, Date dateTo, String type) {

        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_CARD_CONDITION)
                + getSqlString(FIND_AND_BY_DATE_BETWEEN) + getSqlString(FIND_AND_BY_TYPE);
        return findManyByCondition(sql, cardId, dateFrom, dateTo, type);
    }

    @Override
    public List<Transaction> findManyByCardAndDateBeforeAndType(Long cardId, Date date, String type) {

        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_CARD_CONDITION)
                + getSqlString(FIND_AND_BY_DATE_BEFORE) + getSqlString(FIND_AND_BY_TYPE);
        return findManyByCondition(sql, cardId, date, type);
    }

    @Override
    public List<Transaction> findManyByCardAndDateAfterAndType(Long cardId, Date date, String type) {

        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_CARD_CONDITION)
                + getSqlString(FIND_AND_BY_DATE_AFTER)+ getSqlString(FIND_AND_BY_TYPE);
        return findManyByCondition(sql, cardId, date, type);
    }



//    @Override
//    public List<Transaction> findManyByCardAndOrderType(Long cardId) {
//        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_CARD_CONDITION);
//        return findManyByCondition(sql, cardId);
//    }
//
//    @Override
//    public List<Transaction> findManyByAccount(Long accountId) {
//        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_ACCOUNT_CONDITION);
//        return findManyByCondition(sql, accountId);
//    }
//
//    @Override
//    public List<Transaction> findManyByAccountAndByDate(Long accountId, Date date) {
//        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_ACCOUNT_CONDITION) +
//                getSqlString(FIND_AND_BY_DATE);
//        return findManyByCondition(sql, accountId, date);
//    }
//
//    @Override
//    public List<Transaction> findManyByAccountAndByDateBetween(Long accountId, Date dateFrom, Date dateTo) {
//        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_ACCOUNT_CONDITION) +
//                getSqlString(FIND_AND_BY_DATE_BETWEEN);
//        return findManyByCondition(sql, accountId, dateFrom, dateTo);
//    }
//
//    @Override
//    public List<Transaction> findManyByUser(Long userId) {
//        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_USER_CONDITION);
//        return findManyByCondition(sql, userId);
//    }
//
//    @Override
//    public List<Transaction> findManyByUserAndByDate(Long userId, Date date) {
//        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_USER_CONDITION)+
//                getSqlString(FIND_AND_BY_DATE);
//        return findManyByCondition(sql, userId, date);
//    }
//
//    @Override
//    public List<Transaction> findManyByUserAndByDateBetween(Long userId, Date dateFrom, Date dateTo) {
//        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_USER_CONDITION) +
//                getSqlString(FIND_AND_BY_DATE_BETWEEN);
//        return findManyByCondition(sql, userId, dateFrom, dateTo);
//    }

}
