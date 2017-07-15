package ua.gordeichuk.payments.daojdbc.jdbcimpl.entityimpl;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.daoentity.AccountDao;
import ua.gordeichuk.payments.daojdbc.jdbcimpl.JdbcEntityDao;
import ua.gordeichuk.payments.entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class JdbcAccountDao extends JdbcEntityDao<Account> implements AccountDao {

    private static final Logger LOGGER = Logger.getLogger(JdbcAccountDao.class);
    private static final String FIND_BY_USER_CONDITION = "find.ByUser";
    private static final String ID = "account_id";
    private static final String BALANCE = "balance";
    public static final int ID_INDEX = 2;
    private static final String FIND_BY_CARD = "find.ByCard";

    public JdbcAccountDao(Connection connection) {
       super(connection, ENTITY_NAME);
    }

    @Override
    protected Account extractEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return extractAccountFromResultSet(resultSet);
    }

    protected static Account extractAccountFromResultSet(ResultSet resultSet)
            throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong(ID));
        account.setBalance(resultSet.getLong(BALANCE));
        return account;
    }

    @Override
    protected void setEntityToPreparedStatement(Account account, PreparedStatement statement)
            throws SQLException {
            statement.setLong(1, account.getBalance());
            if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
                statement.setLong(ID_INDEX, account.getId());
            }
    }

    @Override
    public Optional<Account> findByCard(Long cardId) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_CARD);
        return findBy(sql, cardId);
    }

    @Override
    public List<Account> findManyByUser(Long userId) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_USER_CONDITION);
        return findManyByCondition(sql, userId);
    }

}
