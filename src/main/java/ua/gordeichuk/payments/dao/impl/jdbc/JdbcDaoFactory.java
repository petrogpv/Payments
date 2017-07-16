package ua.gordeichuk.payments.dao.impl.jdbc;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.DaoFactory;
import ua.gordeichuk.payments.dao.*;
import ua.gordeichuk.payments.util.LogMessage;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC implementation of DaoFactory with Data Source
 * connection pool from JNDI
 */
public class JdbcDaoFactory extends DaoFactory {

    private static final Logger LOGGER = Logger.getLogger(JdbcDaoFactory.class);

    private DataSource dataSource;

    public JdbcDaoFactory() {
        try{
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/payments");
        }catch(Exception e){
            String errorMessage = LogMessage.RETRIEVE_DATASOURCE_ERROR;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    @Override
    public DaoConnection getConnection() {
        try {
            Connection connection =  dataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            return new JdbcDaoConnection( connection );
        } catch (SQLException e) {
            String errorMessage = LogMessage.GET_CONNECTION_ERROR;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    @Override
    public AccountDao createAccountDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection = (JdbcDaoConnection)connection;
        Connection sqlConnection = jdbcConnection.getConnection();
        return new JdbcAccountDao(sqlConnection);
    }

    @Override
    public CardDao createCardDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection = (JdbcDaoConnection)connection;
        Connection sqlConnection = jdbcConnection.getConnection();
        return new JdbcCardDao(sqlConnection);
    }

    @Override
    public TransactionDao createTransactionDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection = (JdbcDaoConnection)connection;
        Connection sqlConnection = jdbcConnection.getConnection();
        return new JdbcTransactionDao(sqlConnection);
    }

    @Override
    public UserDao createUserDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection = (JdbcDaoConnection)connection;
        Connection sqlConnection = jdbcConnection.getConnection();
        return new JdbcUserDao(sqlConnection);
    }

    @Override
    public UserAuthDao createUserAuthDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection = (JdbcDaoConnection)connection;
        Connection sqlConnection = jdbcConnection.getConnection();
        return new JdbcUserAuthDao(sqlConnection);
    }



}
