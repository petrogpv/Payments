package ua.gordeichuk.payments.dao.jdbcimpl.daoentity;

import ua.gordeichuk.payments.dao.daoentity.TransactionDao;
import ua.gordeichuk.payments.dao.jdbcimpl.JdbcCommonEntityDao;
import ua.gordeichuk.payments.entity.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcTransactionDao extends JdbcCommonEntityDao<Transaction> implements TransactionDao {


    private static final String ENTITY_NAME = "transaction";

    private static final String ID = "transaction_id";
    private static final String CARD_ID = "c_id";
    private static final String TRANSACTION_ID = "t_id";
    private static final String BALANCE_BEFORE = "balance_before";
    private static final String BALANCE_AFTER = "balance_after";
    private static final String VALUE = "value";
    private static final String DATE = "date";

    public static final int ID_INDEX = 7;

    private Connection connection;

    public JdbcTransactionDao(Connection connection) {
        super(connection, ENTITY_NAME);
    }

    @Override
    protected Transaction extractEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return null;

    }

    @Override
    protected void setEntityToPreparedStatement(Transaction entity, PreparedStatement statement) throws SQLException {

    }
}
