package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.DaoFactory;
import ua.gordeichuk.payments.dao.daoentity.AccountDao;
import ua.gordeichuk.payments.entity.Account;

import java.util.Optional;

/**
 * Created by Валерий on 26.06.2017.
 */
public class AccountService extends Service<Account> {
    private static final Logger LOGGER = Logger.getLogger(AccountService.class);

    private static class Holder{
        static final AccountService INSTANCE = new AccountService();
    }

    private AccountService () {
        super(AccountDao.ENTITY_NAME);
    }

    public static AccountService getInstance(){
        return Holder.INSTANCE;
    }

        public Optional<Account> getAccountByCard(Long cardId) {
            try (DaoConnection connection = daoFactory.getConnection()) {
                connection.begin();
                AccountDao accountDao = (AccountDao)daoFactory.createDao(entityName, connection);
                Optional<Account> entity = accountDao.findByCard(cardId);
                connection.commit();
                return entity;
            }
    }
}
