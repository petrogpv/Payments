package ua.gordeichuk.payments.dao;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.util.LogMessage;

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

    public abstract Dao createDao(String entityName, DaoConnection connection);

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
                LOGGER.error(LogMessage.DAO_FACTORY_CREATION_ERROR + DB_FACTORY_CLASS, e);
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
}

