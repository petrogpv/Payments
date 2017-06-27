package ua.gordeichuk.payments.dao.jdbcimpl.entityimpl;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.daoentity.TransactionDao;
import ua.gordeichuk.payments.dao.jdbcimpl.JdbcEntityDao;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.Transaction;
import ua.gordeichuk.payments.entity.enums.TransactionType;
import ua.gordeichuk.payments.util.LogMessages;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcTransactionDao extends JdbcEntityDao<Transaction> implements TransactionDao {

    private static final Logger LOGGER = Logger.getLogger(JdbcTransactionDao.class);
    private static final String ENTITY_NAME = "transaction";
    private static final String FIND_BY_CARD_CONDITION = "findByCard";
    private static final String FIND_BY_ACCOUNT_CONDITION = "findByAccount";
    private static final String FIND_BY_USER_CONDITION = "findByUser";
    private static final String FIND_AND_BY_DATE = "findAndByDate";
    private static final String FIND_AND_BY_DATE_BETWEEN = "findAndByDateBetween";
    private static final String ID = "transaction_id";
    private static final String TYPE = "type";
    private static final String BALANCE_BEFORE = "balance_before";
    private static final String BALANCE_AFTER = "balance_after";
    private static final String VALUE = "value";
    private static final String DATE = "date";
    public static final int ID_INDEX = 7;

    public JdbcTransactionDao(Connection connection) {
        super(connection, ENTITY_NAME);
    }

    @Override
    protected Transaction extractEntityFromResultSet(ResultSet resultSet) {
        Transaction transaction = null;
        Card card = null;
        try {
                card = JdbcCardDao.extractCardFromResultSet(resultSet);
                transaction = new Transaction.Builder()
                        .setId(resultSet.getLong(ID))
                        .setCard(card)
                        .setType(TransactionType.valueOf(resultSet.getString(TYPE)))
                        .setBalanceBefore(resultSet.getLong(BALANCE_BEFORE))
                        .setBalanceAfter(resultSet.getLong(BALANCE_AFTER))
                        .setValue(resultSet.getLong(VALUE))
                        .setDate(resultSet.getTimestamp(DATE))
                        .build();
        } catch (SQLException e) {
            LOGGER.error(LogMessages.EXCEPTION, e);
            throw new RuntimeException(e);
        }
        return  transaction;

    }

    @Override
    protected void setEntityToPreparedStatement(Transaction transaction, PreparedStatement statement) {
        try {
            statement.setString(1, transaction.getType().name());
            statement.setLong(2, transaction.getCard().getId());
            statement.setLong(3, transaction.getTransaction().getId());
            statement.setLong(4, transaction.getBalanceBefore());
            statement.setLong(5, transaction.getBalanceAfter());
            statement.setTimestamp(6, (Timestamp) transaction.getDate());
            if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
                statement.setLong(ID_INDEX, transaction.getId());
            }
        } catch (SQLException e) {
            LOGGER.error(LogMessages.EXCEPTION, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> findManyByCard(Long cardId) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_CARD_CONDITION);
        return findManyById(sql, cardId);
    }

    @Override
    public List<Transaction> findManyByAccount(Long accountId) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_ACCOUNT_CONDITION);
        return findManyById(sql, accountId);
    }

    @Override
    public List<Transaction> findManyByAccountAndByDate(Long accountId, Date date) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_ACCOUNT_CONDITION) +
                getSqlString(FIND_AND_BY_DATE);
        return findManyById(sql, accountId, date);
    }

    @Override
    public List<Transaction> findManyByAccountAndByDateBetween(Long accountId, Date dateFrom, Date dateTo) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_ACCOUNT_CONDITION) +
                getSqlString(FIND_AND_BY_DATE_BETWEEN);
        return findManyById(sql, accountId, dateFrom, dateTo);
    }

    @Override
    public List<Transaction> findManyByUser(Long userId) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_USER_CONDITION);
        return findManyById(sql, userId);
    }

    @Override
    public List<Transaction> findManyByUserAndByDate(Long userId, Date date) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_USER_CONDITION)+
                getSqlString(FIND_AND_BY_DATE);
        return findManyById(sql, userId, date);
    }

    @Override
    public List<Transaction> findManyByUserAndByDateBetwee(Long userId, Date dateFrom, Date dateTo) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_USER_CONDITION) +
                getSqlString(FIND_AND_BY_DATE_BETWEEN);
        return findManyById(sql, userId, dateFrom, dateTo);
    }
}
