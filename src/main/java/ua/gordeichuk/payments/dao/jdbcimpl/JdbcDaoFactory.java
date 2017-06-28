package ua.gordeichuk.payments.dao.jdbcimpl;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.Dao;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.DaoFactory;
import ua.gordeichuk.payments.dao.jdbcimpl.entityimpl.*;
import ua.gordeichuk.payments.util.LogMessages;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcDaoFactory extends DaoFactory {

    // private Connection connection;
//    private static final String DB_URL = "url";

    // @Resource(name="java:comp/env/jdbcimpl/footbal")
    private static final Logger LOGGER = Logger.getLogger(JdbcDaoFactory.class);
    private static final String ACCOUNT = "account";
    private static final String CARD = "card";
    private static final String TRANSACTION = "transaction";
    private static final String USER = "user";
    private static final String USER_AUTH = "userAuth";
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
            dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/payments");
            //connection = dataSource.getConnection();


        }catch(Exception e){
            LOGGER.error(LogMessages.RETRIEVE_DATASOURCE_ERROR, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public DaoConnection getConnection() {
        try {
            Connection connection =  dataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            return new JdbcDaoConnection( connection );
        } catch (SQLException e) {
            LOGGER.error(LogMessages.GET_CONNECTION_ERROR, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dao createDao(String entityName, DaoConnection connection) {
        Dao dao;
        JdbcDaoConnection jdbcConnection = (JdbcDaoConnection)connection;
        Connection sqlConnection = jdbcConnection.getConnection();
        switch(entityName) {
            case ACCOUNT:
                dao = new JdbcAccountDao(sqlConnection);
                break;
            case CARD:
                dao = new JdbcCardDao(sqlConnection);
                break;
            case TRANSACTION:
                dao = new JdbcTransactionDao(sqlConnection);
                break;
            case USER:
                dao = new JdbcUserDao(sqlConnection);
                break;
            case USER_AUTH:
                dao = new JdbcUserAuthDao(sqlConnection);
                break;
            default:
                String message = LogMessages.CREATE_DAO_ERROR  + entityName;
                LOGGER.error(message);
                throw new RuntimeException(message);
        }
        return dao;
    }



}
