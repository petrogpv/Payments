package ua.gordeichuk.payments.dao.jdbcimpl.daoentity;

import ua.gordeichuk.payments.dao.DaoFactory;
import ua.gordeichuk.payments.dao.daoentity.AccountDao;
import ua.gordeichuk.payments.dao.daoentity.CardDao;
import ua.gordeichuk.payments.dao.jdbcimpl.JdbcCommonEntityDao;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;
import ua.gordeichuk.payments.entity.enums.AccountStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Валерий on 18.06.2017.
 */
public class JdbcAccountDao extends JdbcCommonEntityDao<Account> implements AccountDao {

    private static final String ENTITY_NAME = "transaction";

    private static final String ID = "account_id";
    private static final String BALANCE = "balance";
    private static final String STATUS = "account_status";

    public static final int ID_INDEX = 3;

    public JdbcAccountDao(Connection connection) {
       super(connection, ENTITY_NAME);
    }

    @Override
    public Optional<Account> find(Long id) {
        Optional<Account> result = super.find(id);
        List<Card> cardsList = new ArrayList<>();
        CardDao cardDao= DaoFactory.getInstance().createCardDao(connection);
        Account account = result.get();
        cardsList = cardDao.findManyByAccount(account);
        account.setCards(cardsList);
        result = Optional.of(account);
        return result;
    }
    @Override
    protected Account extractEntityFromResultSet(ResultSet resultSet) throws SQLException {

        return new Account.Builder()
                .setId(resultSet.getLong(ID))
                .setBalance(resultSet.getLong(BALANCE))
                .setAccountStatus(AccountStatus.valueOf(resultSet.getString(STATUS)))
                .build();
    }

    @Override
    protected void setEntityToPreparedStatement(Account entity, PreparedStatement statement) throws SQLException {

    }
}
