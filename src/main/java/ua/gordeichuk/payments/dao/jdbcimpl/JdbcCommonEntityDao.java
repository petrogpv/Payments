package ua.gordeichuk.payments.dao.jdbcimpl;

import ua.gordeichuk.payments.dao.GenericDao;
import ua.gordeichuk.payments.entity.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Валерий on 18.06.2017.
 */
public abstract class JdbcCommonEntityDao<T extends Entity> implements GenericDao<T> {

    protected Connection connection;
    public static final String FIND_ALL = "findAll";
    public static final String FIND= "find";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String SPACE = " ";
    public static final String DOT = ".";

    protected static final ResourceBundle SQL_BUNDLE = ResourceBundle.getBundle("sql");

    protected String entityName;

    public JdbcCommonEntityDao(Connection connection, String entityName ) {
        this.connection = connection;
        this.entityName = entityName;
    }

    @Override
    public Optional<T> find(Long id) {
        String sql = getSqlString(FIND);
        Optional<T> result = Optional.empty();
        try(PreparedStatement statement =
                    connection.prepareStatement(sql)){
            statement.setLong( 1 , id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = Optional.of(extractEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<T> findAll() {
        String sql = getSqlString(FIND_ALL);
        List<T> result = new ArrayList<>();
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){

            while (resultSet.next()) {
                result.add(extractEntityFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void create(T entity) {
        String sql = getSqlString(CREATE);
        try( PreparedStatement statement =
                     connection.prepareStatement(sql
                             , Statement.RETURN_GENERATED_KEYS ) ){

            setEntityToPreparedStatement(entity , statement);

            statement.executeUpdate();
            ResultSet keys =  statement.getGeneratedKeys();
            if( keys.next()){
                entity.setId(keys.getLong(1) );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T entity) {
        String sql = getSqlString(UPDATE);
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            setEntityToPreparedStatement(entity, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = getSqlString(DELETE);
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getSqlString(String queryName) {
        return SQL_BUNDLE.getString(entityName + DOT + queryName);
    }
    protected abstract T extractEntityFromResultSet(ResultSet resultSet) throws SQLException;
    protected abstract void setEntityToPreparedStatement(T entity, PreparedStatement statement)
            throws SQLException;

}
