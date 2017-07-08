package ua.gordeichuk.payments.controller.daojdbc.jdbcimpl.entityimpl;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.model.daoentity.UserDao;
import ua.gordeichuk.payments.controller.daojdbc.jdbcimpl.JdbcEntityDao;
import ua.gordeichuk.payments.model.entity.User;
import ua.gordeichuk.payments.model.entity.UserAuth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcUserDao extends JdbcEntityDao<User> implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(JdbcEntityDao.class);
    private static final String FIND_BY_LOGIN_CONDITION = "findByLogin";
    private static final String ID = "user_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    public static final int ID_INDEX = 3;


    public JdbcUserDao(Connection connection) {
        super(connection, ENTITY_NAME);

    }

    protected User extractEntityFromResultSet(ResultSet resultSet)
            throws SQLException {
        return extractUserFromResultSet(resultSet);
    }

    protected static User extractUserFromResultSet(ResultSet resultSet)
            throws SQLException {
            UserAuth userAuth = JdbcUserAuthDao.extractUserAuthFromResultSet(resultSet);
            return new User.Builder()
                    .setId(resultSet.getLong(ID))
                    .setFirstName(resultSet.getString(FIRST_NAME))
                    .setLastName(resultSet.getString(LAST_NAME))
                    .setUserAuth(userAuth)
                    .build();
    }

    @Override
    protected void setEntityToPreparedStatement(User user, PreparedStatement statement)
            throws SQLException {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
                statement.setLong(ID_INDEX, user.getId());
            }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_LOGIN_CONDITION);
        return findBy(sql, login);
    }
}
