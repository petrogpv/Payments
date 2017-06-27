package ua.gordeichuk.payments.dao.jdbcimpl;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.Dao;
import ua.gordeichuk.payments.entity.Entity;
import ua.gordeichuk.payments.util.LogMessages;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Валерий on 18.06.2017.
 */
public abstract class JdbcEntityDao<T extends Entity> implements Dao<T> {

    protected Connection connection;
    public static final String FIND_ALL_QUERY = "findAll";
    public static final String FIND_BY_ID_CONDITION = "findById";
    public static final String CREATE_QUERY = "create";
    public static final String UPDATE_QUERY = "update";
    public static final String DELETE_QUERY = "delete";
    public static final String SPACE = " ";
    public static final String DOT = ".";
    public static final String SQL_PROPERTIES_FILE = "sql.properties";
    protected static ResourceBundle sqlBundle;
    protected String entityName;
    private static final Logger LOGGER = Logger.getLogger(JdbcEntityDao.class);

    protected JdbcEntityDao(Connection connection, String entityName) {
        this.connection = connection;
        this.entityName = entityName;
        try {
            sqlBundle = ResourceBundle.getBundle(SQL_PROPERTIES_FILE);
            LOGGER.info(LogMessages.RB_READ_SUCCESSFUL + SQL_PROPERTIES_FILE);
        } catch (MissingResourceException e) {
            LOGGER.error(LogMessages.RB_READ_ERROR + SQL_PROPERTIES_FILE);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getEntityName() {
        return entityName;
    }

    @Override
    public Optional<T> find(Long id) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_ID_CONDITION);
        return findBy(sql, id);
    }
    protected Optional<T> findBy(String sql, Object ... param) {
        List<T> foundList = findManyById(sql, param);
        return Optional.ofNullable(foundList.isEmpty() ? null : foundList.get(0));
    }

    @Override
    public List<T> findAll() {
        String sql = getSqlString(FIND_ALL_QUERY);
        return findManyById(sql, null);
    }
    protected List<T> findManyById(String sql, Object ... param){
        List<T> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setConditionParametersToStatement(statement, param);
            LOGGER.info(statement.toString());
            ResultSet resultSet = statement.executeQuery();
            int rows = statement.getUpdateCount();
            LOGGER.info(LogMessages.ROWS_FOUND + rows + LogMessages.IN_TABLE + entityName);
            while (resultSet.next()) {
                result.add(extractEntityFromResultSet(resultSet));

            }

        } catch (SQLException e) {
            LOGGER.error(LogMessages.DB_ERROR_FIND + entityName +
                    LogMessages.EXCEPTION_MESSAGE + e.getMessage());
        }
        return result;
    }


    private void setConditionParametersToStatement(PreparedStatement statement, Object ... param) throws SQLException {
        if(param!=null){
            for (int i = 0; i < param.length; i++) {
                if(param[i] instanceof Long) {
                    statement.setLong(i+1, (Long) param[i]);
                } else if(param[i] instanceof String) {
                    statement.setString(i+1, (String) param[i]);
                } else if(param[i] instanceof Date){
                    statement.setTimestamp(i+1, (Timestamp) param[i]);
                }
            }

        }
    }


    @Override
    public boolean create(T entity) {
        boolean updated;
        String sql = getSqlString(CREATE_QUERY);
        try (PreparedStatement statement =
                     connection.prepareStatement(sql
                             , Statement.RETURN_GENERATED_KEYS)) {

            setEntityToPreparedStatement(entity, statement);
            LOGGER.info(LogMessages.PREPARED_STATEMENT + statement.toString());
            statement.executeUpdate();
            int rows = statement.getUpdateCount();
            updated = rows > 0;
            LOGGER.info(LogMessages.ROWS_SAVED + rows + LogMessages.IN_TABLE + entityName);
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                entity.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error(LogMessages.DB_ERROR_CREATE + entityName +
                    LogMessages.EXCEPTION_MESSAGE + e.getMessage());
            throw new RuntimeException(e);
        }
        return updated;
    }

    @Override
    public boolean update(T entity) {
        boolean updated;
        String sql = getSqlString(UPDATE_QUERY);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setEntityToPreparedStatement(entity, statement);
            LOGGER.info(LogMessages.PREPARED_STATEMENT + statement.toString());
            statement.executeUpdate();
            int rows = statement.getUpdateCount();
            updated = rows > 0;
            LOGGER.info(LogMessages.ROWS_UPDATED + rows + LogMessages.IN_TABLE + entityName);
        } catch (SQLException e) {
            LOGGER.error(LogMessages.DB_ERROR_UPDATE + entityName +
                    LogMessages.EXCEPTION_MESSAGE + e.getMessage());
            throw new RuntimeException(e);
        }
        return updated;
    }

    @Override
    public boolean delete(Long id) {
        boolean updated;
        String sql = getSqlString(DELETE_QUERY);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            LOGGER.info(LogMessages.PREPARED_STATEMENT + statement.toString());
            int rows = statement.executeUpdate();
            updated = rows > 0;
            LOGGER.info(LogMessages.ROWS_DELETED + rows);
        } catch (SQLException e) {
            LOGGER.error(LogMessages.DB_ERROR_DELETE + entityName +
                    LogMessages.EXCEPTION_MESSAGE + e.getMessage());
            throw new RuntimeException(e);
        }
        return updated;
    }

    protected String getSqlString(String queryName) {
        return sqlBundle.getString(entityName + DOT + queryName);
    }

    protected abstract T extractEntityFromResultSet(ResultSet resultSet);

    protected abstract void setEntityToPreparedStatement(T entity, PreparedStatement statement);

}
