package ua.gordeichuk.payments.daojdbc.jdbcimpl.entityimpl;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.daoentity.TransactionDao;
import ua.gordeichuk.payments.daojdbc.jdbcimpl.JdbcEntityDao;
import ua.gordeichuk.payments.daojdbc.jdbcimpl.entityimpl.enums.CardRelation;
import ua.gordeichuk.payments.daojdbc.jdbcimpl.entityimpl.psdtobuilder.TransactionPsDtoBuilder;
import ua.gordeichuk.payments.dto.entityparam.PreparedStatementDto;
import ua.gordeichuk.payments.dto.entityparam.TransactionParamDto;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.Transaction;
import ua.gordeichuk.payments.entity.enums.TransactionType;

import java.sql.*;
import java.util.List;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcTransactionDao extends JdbcEntityDao<Transaction> implements TransactionDao {

    private static final String ID = "transaction_id";
    private static final String TYPE = "type";
    private static final String BALANCE_AFTER = "balance_after";
    private static final String VALUE = "value";
    private static final String DATE = "date";
    private static final String REL_ID = "rel_transaction_id";
    private static final String REL_TYPE = "rel_type";
    private static final String REL_BALANCE_AFTER = "rel_balance_after";
    private static final String REL_VALUE = "rel_value";
    private static final String REL_DATE = "rel_date";
    public static final int ID_INDEX = 7;

    public JdbcTransactionDao(Connection connection) {
        super(connection, ENTITY_NAME);
    }

    @Override
    protected Transaction extractEntityFromResultSet(ResultSet resultSet) throws SQLException {
        Card card = JdbcCardDao.extractCardFromResultSetLazy(resultSet, CardRelation.HOST);
        Transaction relativeTransaction = extractRelativeTransactionFromResultSet(resultSet);
        Transaction transaction = new Transaction.Builder()
                .setId(resultSet.getLong(ID))
                .setRelativeTransaction(relativeTransaction)
                .setCard(card)
                .setType(TransactionType.valueOf(resultSet.getString(TYPE)))
                .setBalanceAfter(resultSet.getLong(BALANCE_AFTER))
                .setValue(resultSet.getLong(VALUE))
                .setDate(resultSet.getTimestamp(DATE))
                .build();

        return transaction;
    }

    protected Transaction extractRelativeTransactionFromResultSet(ResultSet resultSet) throws SQLException {
        if(resultSet.getLong(REL_ID) == 0){
            return null;
        }
        Card card = JdbcCardDao.extractCardFromResultSetLazy(resultSet, CardRelation.RELATIVE);
        return new Transaction.Builder()
                .setId(resultSet.getLong(REL_ID))
                .setCard(card)
                .setType(TransactionType.valueOf(resultSet.getString(REL_TYPE)))
                .setBalanceAfter(resultSet.getLong(REL_BALANCE_AFTER))
                .setValue(resultSet.getLong(REL_VALUE))
                .setDate(resultSet.getTimestamp(REL_DATE))
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
    public List<Transaction> findManyByParamDTO(TransactionParamDto transactionParamDto) {
        TransactionPsDtoBuilder transactionPsDtoBuilder = new TransactionPsDtoBuilder();
        PreparedStatementDto preparedStatementDto =
                transactionPsDtoBuilder.buildPreparedStatementDto(transactionParamDto);

        return findManyByCondition(preparedStatementDto.getSql(),
                preparedStatementDto.getParameters().toArray());
    }
}
