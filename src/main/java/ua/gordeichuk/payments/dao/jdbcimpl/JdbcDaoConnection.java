package ua.gordeichuk.payments.dao.jdbcimpl;

/**
 * Created by Валерий on 18.06.2017.
 */

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.util.LogMessage;

import java.sql.Connection;
import java.sql.SQLException;


public class JdbcDaoConnection implements DaoConnection {
    private static final Logger LOGGER = Logger.getLogger(JdbcDaoConnection.class);
    private Connection connection;
    private boolean inTransaction = false;



    JdbcDaoConnection(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public void close() {
        if(inTransaction) {
            rollback();
            LOGGER.warn(LogMessage.ROLLBACK);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(LogMessage.CLOSE_WHILE_ROLLBACK_ERROR, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void begin() {
        try {
            connection.setAutoCommit(false);
            inTransaction = true;
        } catch (SQLException e) {
            LOGGER.error(LogMessage.SET_AUTOCOMMIT_ERROR, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
            inTransaction = false;
        } catch (SQLException e) {
            LOGGER.error(LogMessage.COMMIT_ERROR, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
            inTransaction = false;
        } catch (SQLException e) {
            LOGGER.error(LogMessage.ROLLBACK_ERROR, e);
            throw new RuntimeException(e);
        }

    }

    protected Connection getConnection() {
        return connection;
    }


}
