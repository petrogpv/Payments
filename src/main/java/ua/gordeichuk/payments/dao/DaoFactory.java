package ua.gordeichuk.payments.dao;

import ua.gordeichuk.payments.dao.daoentity.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by Валерий on 18.06.2017.
 */
public abstract class DaoFactory {
    public abstract DaoConnection getConnection();

//    public abstract AccountDao createAccountDao( );
//    public abstract CardDao createCardDao();
//    public abstract TransactionDao createTransactionDao();
//    public abstract UserDao createUserDao();
//    public abstract UserAuthDao createUserAuthDao();

    public abstract AccountDao createAccountDao(Connection connection );
    public abstract CardDao createCardDao(Connection connection);
    public abstract TransactionDao createTransactionDao(Connection connection);
    public abstract UserDao createUserDao(Connection connection);
    public abstract UserAuthDao createUserAuthDao(Connection connection);

    public static final String DB_FILE = "/db.properties";
    private static final String DB_FACTORY_CLASS = "factory.class";
    private static  DaoFactory instance;

    public static DaoFactory getInstance(){
        if( instance == null) {
            try {
                InputStream inputStream =
                        DaoFactory.class.getResourceAsStream(DB_FILE);
                Properties dbProps = new Properties();
                dbProps.load(inputStream);
                String factoryClass = dbProps.getProperty(DB_FACTORY_CLASS);
                instance = (DaoFactory) Class.forName(factoryClass).newInstance();

            } catch (IOException | IllegalAccessException|
                    InstantiationException |ClassNotFoundException e ) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
}

