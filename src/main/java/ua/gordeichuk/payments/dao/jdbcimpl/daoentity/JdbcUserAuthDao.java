package ua.gordeichuk.payments.dao.jdbcimpl.daoentity;

import ua.gordeichuk.payments.dao.daoentity.UserAuthDao;
import ua.gordeichuk.payments.dao.jdbcimpl.JdbcCommonEntityDao;
import ua.gordeichuk.payments.entity.UserAuth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcUserAuthDao extends JdbcCommonEntityDao<UserAuth> implements UserAuthDao {

    private static final String ENTITY_NAME = "userAuth";

    private static final String ID = "user_auth_id";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    public static final int ID_INDEX = 3;

    public JdbcUserAuthDao(Connection connection) {
        super(connection, ENTITY_NAME);;
    }

    @Override
    protected UserAuth extractEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserAuth.Builder()
                .setId(resultSet.getLong(ID))
                .setLogin(resultSet.getString(LOGIN))
                .setPassword(resultSet.getString(PASSWORD))
                .build();
    }

    @Override
    protected void setEntityToPreparedStatement(UserAuth userAuth, PreparedStatement statement) throws SQLException {
        statement.setString(1, userAuth.getLogin());
        statement.setString(2, userAuth.getPassword());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, userAuth.getId());
        }
    }
}
