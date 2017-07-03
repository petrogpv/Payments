package ua.gordeichuk.payments.controller.dao.jdbcimpl;

/**
 * Created by Валерий on 18.06.2017.
 */

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.controller.dao.DaoConnection;
import ua.gordeichuk.payments.controller.util.LogMessage;

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
        if (inTransaction) {
            rollback();
            LOGGER.warn(LogMessage.ROLLBACK);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            String errorMessage = LogMessage.CLOSE_WHILE_ROLLBACK_ERROR;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    @Override
    public void begin() {
        try {
            connection.setAutoCommit(false);
            inTransaction = true;
        } catch (SQLException e) {
            String errorMessage = LogMessage.SET_AUTOCOMMIT_ERROR;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
            inTransaction = false;
        } catch (SQLException e) {
            String errorMessage = LogMessage.COMMIT_ERROR;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
            inTransaction = false;
        } catch (SQLException e) {
            String errorMessage = LogMessage.ROLLBACK_ERROR;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }

    }

    protected Connection getConnection() {
        return connection;
    }


}
