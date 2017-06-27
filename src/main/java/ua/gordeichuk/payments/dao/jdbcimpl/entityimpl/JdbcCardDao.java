package ua.gordeichuk.payments.dao.jdbcimpl.entityimpl;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.daoentity.CardDao;
import ua.gordeichuk.payments.dao.jdbcimpl.JdbcEntityDao;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.CardStatus;
import ua.gordeichuk.payments.util.LogMessages;

import java.sql.*;
import java.util.List;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcCardDao extends JdbcEntityDao<Card> implements CardDao {

    private static final Logger LOGGER = Logger.getLogger(JdbcCardDao.class);
    private static final String ENTITY_NAME = "card";
    private static final String FIND_BY_USER_CONDITION = "findByUser";
    private static final String FIND_BY_ACCOUNT_CONDITION = "findByAccount";
    private static final String FIND_AND_BY_CARD_STATUS = "findAndByCardStatus";
    private static final String ID = "card_id";
    private static final String STATUS = "card_status";
    public static final int ID_INDEX = 4;

    public JdbcCardDao(Connection connection) {
        super(connection, ENTITY_NAME);
    }


    @Override
    protected Card extractEntityFromResultSet(ResultSet resultSet){
        return extractEntityFromResultSet(resultSet);
    }

    protected static Card extractCardFromResultSet(ResultSet resultSet){
        Card card = null;
        try {
//            Long id = resultSet.getLong(ID);
            User user = JdbcUserDao.extractUserFromResultSet(resultSet);
            Account account = JdbcAccountDao.extractAccountFromResultSet(resultSet);
            CardStatus cardStatus = CardStatus.valueOf(resultSet.getString(STATUS));
            card = new Card.Builder()
                    .setId(resultSet.getLong(ID))
                    .setUser(user)
                    .setAccount(account)
                    .setCardStatus(cardStatus)
                    .build();
        } catch (SQLException e) {
            LOGGER.error(LogMessages.EXCEPTION, e);
            throw new RuntimeException(e);
        }
        return  card;
    }

    @Override
    protected void setEntityToPreparedStatement(Card card, PreparedStatement statement) {
        try {
            statement.setLong(1, card.getUser().getId());
            statement.setLong(2, card.getAccount().getId());
            statement.setString(3, card.getStatus().name());
            if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
                statement.setLong(ID_INDEX, card.getId());
            }
        } catch (SQLException e) {
            LOGGER.error(LogMessages.EXCEPTION, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Card> findManyByUser(Long userId) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_USER_CONDITION);
        return findManyById(sql, userId);
    }

    @Override
    public List<Card> findManyByUserAndCardStatus(Long userId, String cardStatus) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_USER_CONDITION) +
                getSqlString(FIND_AND_BY_CARD_STATUS);
        return findManyById(sql, userId, cardStatus);
    }

    @Override
    public List<Card> findManyByAccount(Long accountId) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_ACCOUNT_CONDITION);
        return findManyById(sql, accountId);
    }

    @Override
    public List<Card> findManyByAccountAndCardStatus(Long accountId, String cardStatus) {
        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_ACCOUNT_CONDITION) +
                getSqlString(FIND_AND_BY_CARD_STATUS);
        return findManyById(sql, accountId, cardStatus);
    }
}
