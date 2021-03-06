package ua.gordeichuk.payments.dao.impl.jdbc;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.Dao;
import ua.gordeichuk.payments.entity.Entity;
import ua.gordeichuk.payments.util.LogMessage;
import ua.gordeichuk.payments.util.SqlBundle;

import java.sql.*;
import java.util.*;
import java.util.Date;

public abstract class JdbcEntityDao<T extends Entity> implements Dao<T> {

    protected Connection connection;
    public static final String FIND_ALL_QUERY = "find.All";
    public static final String FIND_BY_ID_CONDITION = "find.ById";
    public static final String CREATE_QUERY = "create";
    public static final String UPDATE_QUERY = "update";
    public static final String DELETE_QUERY = "delete";
    public static final String DOT = ".";
    private ResourceBundle sqlBundle;
    protected String entityName;
    private static final Logger LOGGER = Logger.getLogger(JdbcEntityDao.class);

    protected JdbcEntityDao(Connection connection, String entityName) {
        this.connection = connection;
        this.entityName = entityName;
        sqlBundle = SqlBundle.getInstance().getSqlBundle();
    }

    @Override
    public Optional<T> find(Long id) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_ID_CONDITION);
        return findBy(sql, id);
    }

    protected Optional<T> findBy(String sql, Object... param) {
        List<T> foundList = findManyByCondition(sql, param);
        return Optional.ofNullable(foundList.isEmpty() ? null : foundList.get(0));
    }

    @Override
    public List<T> findAll() {
        String sql = getSqlString(FIND_ALL_QUERY);
        return findManyByCondition(sql, null);
    }

    protected List<T> findManyByCondition(String sql, Object... param) {
        List<T> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setConditionParametersToStatement(statement, param);
            LOGGER.info(statement.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(extractEntity(resultSet));

            }
            LOGGER.info(LogMessage.ROWS_FOUND + list.size() + LogMessage.IN_TABLE + entityName);

        } catch (SQLException e) {
            String errorMessage = LogMessage.DB_ERROR_FIND + entityName;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        return list;
    }

    protected int findCountByCondition(String sql, Object... param) {
        int count = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setConditionParametersToStatement(statement, param);
            LOGGER.info(statement.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            LOGGER.info(LogMessage.ROWS_FOUND + count + LogMessage.IN_TABLE + entityName);
        } catch (SQLException e) {
            String errorMessage = LogMessage.DB_ERROR_FIND + entityName;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        return count;
    }


    private void setConditionParametersToStatement(PreparedStatement statement,
                                                   Object... param) throws SQLException {
        if (param != null) {
            for (int i = 0; i < param.length; i++) {
                if (param[i] instanceof Long) {
                    statement.setLong(i + 1, (Long) param[i]);
                } else if (param[i] instanceof String) {
                    statement.setString(i + 1, (String) param[i]);
                } else if (param[i] instanceof Date) {
                    statement.setTimestamp(i + 1, new Timestamp(((Date) param[i]).getTime()));
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

            setEntity(entity, statement);
            LOGGER.info(LogMessage.PREPARED_STATEMENT + statement.toString());
            statement.executeUpdate();
            int rows = statement.getUpdateCount();
            updated = rows > 0;
            LOGGER.info(LogMessage.ROWS_SAVED + rows + LogMessage.IN_TABLE + entityName);
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                entity.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            String errorMessage = LogMessage.DB_ERROR_CREATE + entityName;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        return updated;
    }

    @Override
    public boolean update(T entity) {
        boolean updated;
        String sql = getSqlString(UPDATE_QUERY);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setEntity(entity, statement);
            LOGGER.info(LogMessage.PREPARED_STATEMENT + statement.toString());
            statement.executeUpdate();
            int rows = statement.getUpdateCount();
            updated = rows > 0;
            LOGGER.info(LogMessage.ROWS_UPDATED + rows + LogMessage.IN_TABLE + entityName);
        } catch (SQLException e) {
            String errorMessage = LogMessage.DB_ERROR_UPDATE + entityName;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        return updated;
    }

    @Override
    public boolean delete(Long id) {
        boolean updated;
        String sql = getSqlString(DELETE_QUERY);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            LOGGER.info(LogMessage.PREPARED_STATEMENT + statement.toString());
            int rows = statement.executeUpdate();
            updated = rows > 0;
            LOGGER.info(LogMessage.ROWS_DELETED + rows);
        } catch (SQLException e) {
            String errorMessage = LogMessage.DB_ERROR_DELETE + entityName;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        return updated;
    }

    protected String getSqlString(String queryName) {
        return sqlBundle.getString(entityName + DOT + queryName);
    }


    protected abstract T extractEntityFromResultSet(ResultSet resultSet) throws SQLException;

    protected abstract void setEntityToPreparedStatement(T entity, PreparedStatement statement)
            throws SQLException;

    private T extractEntity(ResultSet resultSet) {
        T entity = null;
        try {
            entity = extractEntityFromResultSet(resultSet);
        } catch (NullPointerException | SQLException e) {
            String errorMessage = LogMessage.EXTRACT_ENTITY_ERROR + entityName;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        return entity;
    }

    private void setEntity(T entity, PreparedStatement statement) {
        try {
            setEntityToPreparedStatement(entity, statement);
        } catch (NullPointerException | SQLException e) {
            String errorMessage = LogMessage.SET_ENTITY_TO_PS_ERROR
                    + entityName;
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }


}
