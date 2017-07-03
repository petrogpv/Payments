package ua.gordeichuk.payments.controller.dao;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.model.daoentity.*;
import ua.gordeichuk.payments.controller.util.LogMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Валерий on 18.06.2017.
 */
public abstract class DaoFactory {
    public abstract DaoConnection getConnection();

    private static DaoFactory instance;
    private static final Logger LOGGER = Logger.getLogger(DaoFactory.class);
    public static final String DB_FILE = "/db.properties";
    private static final String DB_FACTORY_CLASS = "factory.class";

    public abstract AccountDao createAccountDao(DaoConnection connection);
    public abstract CardDao createCardDao(DaoConnection connection);
    public abstract TransactionDao createTransactionDao(DaoConnection connection);
    public abstract UserDao createUserDao(DaoConnection connection);
    public abstract UserAuthDao createUserAuthDao(DaoConnection connection);

    public static DaoFactory getInstance() {
        if (instance == null) {
            try (InputStream inputStream =
                         DaoFactory.class.getResourceAsStream(DB_FILE)){
                Properties properties = new Properties();
                properties.load(inputStream);
                String factoryClass = properties.getProperty(DB_FACTORY_CLASS);
                instance = (DaoFactory) Class.forName(factoryClass).newInstance();
                LOGGER.info(LogMessage.RB_READ_SUCCESSFUL + LogMessage.DAO_FACTORY_CREATED +
                        DB_FACTORY_CLASS);

            } catch (IOException
                    | IllegalAccessException
                    | InstantiationException
                    | ClassNotFoundException e) {
                String errorMessage = LogMessage.DAO_FACTORY_CREATION_ERROR + DB_FACTORY_CLASS;
                LOGGER.error(errorMessage, e);
                throw new RuntimeException(errorMessage, e);
            }
        }
        return instance;
    }
}

