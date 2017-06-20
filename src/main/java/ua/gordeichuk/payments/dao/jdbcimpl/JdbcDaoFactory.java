package ua.gordeichuk.payments.dao.jdbcimpl;

import ua.gordeichuk.payments.dao.*;
import ua.gordeichuk.payments.dao.daoentity.*;
import ua.gordeichuk.payments.dao.jdbcimpl.daoentity.*;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcDaoFactory extends DaoFactory {

    // private Connection connection;
//    private static final String DB_URL = "url";

    // @Resource(name="java:comp/env/jdbcimpl/footbal")
    private DataSource dataSource;

    public JdbcDaoFactory() {
        try{


//            InputStream inputStream =
//                DaoFactory.class.getResourceAsStream(DB_FILE);
//            Properties dbProps = new Properties();
//            dbProps.load(inputStream);
//            String url = dbProps.getProperty(DB_URL);
            //new Driver();
            //connection = DriverManager.getConnection(url , dbProps);
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:comp/env/jdbcimpl/football");
            //connection = dataSource.getConnection();


        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public DaoConnection getConnection() {
        try {
            return new JdbcDaoConnection( dataSource.getConnection() );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AccountDao createAccountDao(Connection connection) {
        return new JdbcAccountDao(connection);
    }

    @Override
    public CardDao createCardDao(Connection connection) {
        return new JdbcCardDao(connection);
    }

    @Override
    public TransactionDao createTransactionDao(Connection connection) {
        return new JdbcTransactionDao(connection);
    }

    @Override
    public UserDao createUserDao(Connection connection) {
        return new JdbcUserDao(connection);
    }

    @Override
    public UserAuthDao createUserAuthDao(Connection connection) {
        return new JdbcUserAuthDao(connection);
    }
}
