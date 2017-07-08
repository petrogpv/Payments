package ua.gordeichuk.payments.controller.daojdbc.jdbcimpl.entityimpl;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.model.daoentity.UserAuthDao;
import ua.gordeichuk.payments.controller.daojdbc.jdbcimpl.JdbcEntityDao;
import ua.gordeichuk.payments.model.entity.UserAuth;
import ua.gordeichuk.payments.model.entity.enums.UserRole;

import java.sql.*;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcUserAuthDao extends JdbcEntityDao<UserAuth> implements UserAuthDao {

    private static final Logger LOGGER = Logger.getLogger(JdbcUserAuthDao.class);
    private static final String ID = "user_auth_id";
    private static final String ROLE = "role";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String SOLE = "sole";
    public static final int ID_INDEX = 5;

    public JdbcUserAuthDao(Connection connection) {
            super(connection, ENTITY_NAME);
    }

    @Override
    protected UserAuth extractEntityFromResultSet(ResultSet resultSet)
            throws SQLException {
       return extractUserAuthFromResultSet(resultSet);
    }

    protected static  UserAuth extractUserAuthFromResultSet(ResultSet resultSet)
            throws SQLException {
        UserRole userRole = UserRole.valueOf(resultSet.getString(ROLE));
        return new UserAuth.Builder()
                .setId(resultSet.getLong(ID))
                .setRole(userRole)
                .setLogin(resultSet.getString(LOGIN))
                .setPassword(resultSet.getString(PASSWORD))
                .setSole(resultSet.getString(SOLE))
                .build();
    }

    @Override
    protected void setEntityToPreparedStatement(
            UserAuth userAuth, PreparedStatement statement) throws SQLException {
        String password = userAuth.getPassword();
        String sole = userAuth.getSole();
            statement.setString(1, userAuth.getRole().name());
            statement.setString(2, userAuth.getLogin());
        if(password == null){
            statement.setNull(3, Types.VARCHAR);
        }else{
            statement.setString(3, userAuth.getPassword());

        }
        if(sole == null){
            statement.setNull(4, Types.VARCHAR);

        }else{
            statement.setString(4, userAuth.getSole());

        }

            if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
                statement.setLong(ID_INDEX, userAuth.getId());
            }
    }
}
