package ua.gordeichuk.payments.daojdbc.jdbcimpl.entityimpl;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.daoentity.CardDao;
import ua.gordeichuk.payments.daojdbc.jdbcimpl.JdbcEntityDao;
import ua.gordeichuk.payments.daojdbc.jdbcimpl.entityimpl.enums.CardRelation;
import ua.gordeichuk.payments.daojdbc.jdbcimpl.entityimpl.psdtobuilder.CardPsDtoBuilder;
import ua.gordeichuk.payments.dto.entityparam.CardParamDto;
import ua.gordeichuk.payments.dto.entityparam.PreparedStatementDto;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.enums.CardStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcCardDao extends JdbcEntityDao<Card> implements CardDao {
    private static final String FIND_BY_USER_CONDITION = "find.ByUser";
    private static final String FIND_BY_ACCOUNT_CONDITION = "find.ByAccount";
    private static final String FIND_AND_BY_CARD_STATUS = "find.AndByCardStatus";
    private static final String ID = "card_id";
    private static final String STATUS = "card_status";
    private static final String REL_ID = "rel_card_id";
    private static final String REL_STATUS = "rel_card_status";
    public static final int ID_INDEX = 4;

    public JdbcCardDao(Connection connection) {
        super(connection, ENTITY_NAME);
    }


    @Override
    protected Card extractEntityFromResultSet(ResultSet resultSet) throws SQLException {
            User user = JdbcUserDao.extractUserFromResultSet(resultSet);
            Account account = JdbcAccountDao.extractAccountFromResultSet(resultSet);
            CardStatus cardStatus = CardStatus.valueOf(resultSet.getString(STATUS));
        return  new Card.Builder()
                .setId(resultSet.getLong(ID))
                .setUser(user)
                .setAccount(account)
                .setCardStatus(cardStatus)
                .build();
    }
    protected static Card extractCardFromResultSetLazy(ResultSet resultSet,
                                                       CardRelation cardRelation) throws SQLException {
        String status;
        String id;
        if(cardRelation.equals(CardRelation.HOST)){
            id = ID;
            status = STATUS;
        } else {
            id = REL_ID;
            status = REL_STATUS;
        }
        CardStatus cardStatus = CardStatus.valueOf(resultSet.getString(status));
        Card card = new Card();
        card.setId(resultSet.getLong(id));
        card.setStatus(cardStatus);
        return  card;

    }

    @Override
    protected void setEntityToPreparedStatement(Card card, PreparedStatement statement)
            throws SQLException {
            statement.setLong(1, card.getUser().getId());
            statement.setLong(2, card.getAccount().getId());
            statement.setString(3, card.getStatus().name());
            if (statement.getParameterMetaData().getParameterCount() == ID_INDEX) {
                statement.setLong(ID_INDEX, card.getId());
            }
    }

    @Override
    public List<Card> findManyByDto(CardParamDto cardParamDto) {
        CardPsDtoBuilder cardPsDtoBuilder = new CardPsDtoBuilder();
        PreparedStatementDto preparedStatementDto = cardPsDtoBuilder
                .buildPreparedStatementDto(cardParamDto);
        return findManyByCondition(preparedStatementDto.getSql(),
                preparedStatementDto.getParameters().toArray());
    }
//    @Override
//    public List<Card> findCardsByUser(Long userId) {
//        String sql = getSqlString(FIND_ALL_QUERY) + getSqlString(FIND_BY_USER_CONDITION);
//        return findManyByCondition(sql, userId);
//    }
//
//    @Override
//    public List<Card> findManyByUserAndCardStatus(Long userId, String cardStatus) {
//        String sql = getSqlString(FIND_ALL_QUERY)
//                + getSqlString(FIND_BY_USER_CONDITION)
//                + getSqlString(FIND_AND_BY_CARD_STATUS);
//        return findManyByCondition(sql, userId, cardStatus);
//    }
//
//    @Override
//    public List<Card> findManyByAccount(Long accountId) {
//        String sql = getSqlString(FIND_ALL_QUERY)
//                + getSqlString(FIND_BY_ACCOUNT_CONDITION);
//        return findManyByCondition(sql, accountId);
//    }
//
//    @Override
//    public List<Card> findManyByAccountAndCardStatus(Long accountId, String cardStatus) {
//        String sql = getSqlString(FIND_ALL_QUERY)
//                + getSqlString(FIND_BY_ACCOUNT_CONDITION)
//                + getSqlString(FIND_AND_BY_CARD_STATUS);
//        return findManyByCondition(sql, accountId, cardStatus);
//    }


}
