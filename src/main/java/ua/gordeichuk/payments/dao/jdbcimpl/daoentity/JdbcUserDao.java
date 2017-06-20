package ua.gordeichuk.payments.dao.jdbcimpl.daoentity;

import ua.gordeichuk.payments.dao.daoentity.UserDao;
import ua.gordeichuk.payments.dao.jdbcimpl.JdbcCommonEntityDao;
import ua.gordeichuk.payments.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcUserDao extends JdbcCommonEntityDao<User> implements UserDao {
    private static final String ENTITY_NAME = "user";

    private static final String ID = "user_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    public static final int ID_INDEX = 3;

    public JdbcUserDao(Connection connection) {
        super(connection, ENTITY_NAME);

    }


    public User extractEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .setId(resultSet.getLong(ID))
                .setFirstName(resultSet.getString(FIRST_NAME))
                .setLastName(resultSet.getString(LAST_NAME))
                .build();
    }

    @Override
    protected void setEntityToPreparedStatement(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, user.getId());
        }
    }
}
