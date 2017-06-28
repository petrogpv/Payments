package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.daoentity.AccountDao;
import ua.gordeichuk.payments.entity.Account;

import java.util.Optional;

/**
 * Created by Валерий on 26.06.2017.
 */
public class AccountService extends Service<Account> {
    private static final Logger LOGGER = Logger.getLogger(AccountService.class);

    protected AccountService() {
        super(AccountDao.ENTITY_NAME);
    }
        public Optional<Account> getAccountByCard(Long cardId) {
            try (DaoConnection connection = daoFactory.getConnection()) {
                connection.begin();
                AccountDao accountDao = (AccountDao)daoFactory.createDao(entityName, connection);
                return accountDao.findByCard(cardId);
            }
    }
}
