package ua.gordeichuk.payments.dao.jdbcimpl.daoentity;

import ua.gordeichuk.payments.dao.daoentity.CardDao;
import ua.gordeichuk.payments.dao.jdbcimpl.JdbcCommonEntityDao;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.CardStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcCardDao extends JdbcCommonEntityDao<Card> implements CardDao {
    private static final String ENTITY_NAME = "card";

    public static final String FIND_MANY_BY_ACOOUNT = "findManyByAccount";

    private static final String ID = "card_id";
    private static final String STATUS = "card_status";


    public static final int ID_INDEX = 4;

    private Connection connection;

    public JdbcCardDao(Connection connection) {
        super(connection, ENTITY_NAME);
    }


    @Override
    protected Card extractEntityFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        User user = new JdbcUserDao(null).extractEntityFromResultSet(resultSet);
        Account account = new JdbcAccountDao(null).extractEntityFromResultSet(resultSet);
        CardStatus cardStatus = CardStatus.valueOf(resultSet.getString(STATUS));

        return new Card.Builder()
                .setId(id)
                .setUser(user)
                .setAccount(account)
                .setCardStatus(cardStatus)
                .build();
    }

    @Override
    protected void setEntityToPreparedStatement(Card card, PreparedStatement statement) throws SQLException {
        statement.setLong(1, card.getUser().getId());
        statement.setLong(2, card.getAccount().getId());
        statement.setString(3, card.getStatus().name());
        if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
            statement.setLong(ID_INDEX, card.getId());
        }
    }

    @Override
    public List<Card> findManyByAccount(Account account) {
        List<Card> result = new ArrayList<>();
        String sql = getSqlString(FIND_MANY_BY_ACOOUNT);
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
}
