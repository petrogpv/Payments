package ua.gordeichuk.payments.util;

import org.apache.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class SqlBundle {
    private static final Logger LOGGER = Logger.getLogger(SqlBundle.class);
    private static final String SQL_PROPERTIES_FILE = "sql";
    private ResourceBundle sqlBundle;

    private SqlBundle() {
        try {
            sqlBundle = ResourceBundle.getBundle(SQL_PROPERTIES_FILE);
            LOGGER.info(LogMessage.RB_READ_SUCCESSFUL + SQL_PROPERTIES_FILE);
        } catch (MissingResourceException e) {
            String errorMessage = LogMessage.RB_READ_ERROR + SQL_PROPERTIES_FILE;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    private static class Holder {
        static final SqlBundle INSTANCE = new SqlBundle();
    }

    public static SqlBundle getInstance() {
        return Holder.INSTANCE;
    }

    public ResourceBundle getSqlBundle() {
        return sqlBundle;
    }
}
