package ua.gordeichuk.payments.dao.jdbcimpl.entityimpl;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.daoentity.AccountDao;
import ua.gordeichuk.payments.dao.jdbcimpl.JdbcEntityDao;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.util.LogMessages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JdbcAccountDao extends JdbcEntityDao<Account> implements AccountDao {

    private static final Logger LOGGER = Logger.getLogger(JdbcAccountDao.class);
    private static final String ENTITY_NAME = "transaction";
    private static final String ID = "account_id";
    private static final String BALANCE = "balance";
    public static final int ID_INDEX = 2;

    public JdbcAccountDao(Connection connection) {
       super(connection, ENTITY_NAME);
    }

    @Override
    protected Account extractEntityFromResultSet(ResultSet resultSet) {
        return extractAccountFromResultSet(resultSet);
    }

    protected static Account extractAccountFromResultSet(ResultSet resultSet) {
        Account account = null;
        try {
            account = new Account.Builder()
                    .setId(resultSet.getLong(ID))
                    .setBalance(resultSet.getLong(BALANCE))
                    .build();
        } catch (SQLException e) {
            LOGGER.error(LogMessages.EXCEPTION, e);
            throw new RuntimeException(e);
        }
        return account;
    }

    @Override
    protected void setEntityToPreparedStatement(Account account, PreparedStatement statement) {
        try {
            statement.setLong(1, account.getBalance());
            if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
                statement.setLong(ID_INDEX, account.getId());
            }
        } catch (SQLException e) {
            LOGGER.error(LogMessages.EXCEPTION, e);
            throw new RuntimeException(e);
        }
    }
}
