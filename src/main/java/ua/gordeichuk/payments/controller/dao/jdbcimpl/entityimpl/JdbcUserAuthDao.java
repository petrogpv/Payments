package ua.gordeichuk.payments.controller.dao.jdbcimpl.entityimpl;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.model.daoentity.UserAuthDao;
import ua.gordeichuk.payments.controller.dao.jdbcimpl.JdbcEntityDao;
import ua.gordeichuk.payments.model.entity.UserAuth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcUserAuthDao extends JdbcEntityDao<UserAuth> implements UserAuthDao {

    private static final Logger LOGGER = Logger.getLogger(JdbcUserAuthDao.class);
    private static final String ID = "user_auth_id";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String SOLE = "sole";
    public static final int ID_INDEX = 4;

    public JdbcUserAuthDao(Connection connection) {
            super(connection, ENTITY_NAME);
    }

    @Override
    protected UserAuth extractEntityFromResultSet(ResultSet resultSet) throws SQLException {
       return extractUserAuthFromResultSet(resultSet);
    }

    protected static  UserAuth extractUserAuthFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserAuth.Builder()
                .setId(resultSet.getLong(ID))
                .setLogin(resultSet.getString(LOGIN))
                .setPassword(resultSet.getString(PASSWORD))
                .setSole(resultSet.getString(SOLE))
                .build();
    }

    @Override
    protected void setEntityToPreparedStatement(UserAuth userAuth, PreparedStatement statement) throws SQLException {
            statement.setString(1, userAuth.getLogin());
            statement.setString(2, userAuth.getPassword());
            statement.setString(3, userAuth.getSole());
            if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
                statement.setLong(ID_INDEX, userAuth.getId());
            }
    }
}
