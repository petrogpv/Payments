package ua.gordeichuk.payments.util;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.daojdbc.jdbcimpl.entityimpl.psdtobuilder.TransactionPsDtoBuilder;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Администратор on 10.07.2017.
 */
public class SqlBundle {
    private static final Logger LOGGER = Logger.getLogger(SqlBundle.class);
    private static final String SQL_PROPERTIES_FILE = "sql";
    private static ResourceBundle sqlBundle;
    static {
        try {
            sqlBundle = ResourceBundle.getBundle(SQL_PROPERTIES_FILE);
            LOGGER.info(LogMessage.RB_READ_SUCCESSFUL + SQL_PROPERTIES_FILE);
        } catch (MissingResourceException e) {
            String errorMessage = LogMessage.RB_READ_ERROR + SQL_PROPERTIES_FILE;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    public static ResourceBundle getSqlBundle(){
        return sqlBundle;
    }
}
