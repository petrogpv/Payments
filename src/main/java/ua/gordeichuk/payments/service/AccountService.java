package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.daoentity.AccountDao;
import ua.gordeichuk.payments.daojdbc.DaoConnection;
import ua.gordeichuk.payments.daojdbc.DaoFactory;

import ua.gordeichuk.payments.entity.Account;

import java.util.Optional;

/**
 * Created by Валерий on 26.06.2017.
 */
public class AccountService{
    private static final Logger LOGGER = Logger.getLogger(AccountService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private AccountService() {
    }

    private static class Holder{
        static final AccountService INSTANCE = new AccountService();
    }


    public static AccountService getInstance(){
        return Holder.INSTANCE;
    }

        public Optional<Account> getAccountByCard(Long cardId) {
            try (DaoConnection connection = daoFactory.getConnection()) {
                connection.begin();
                AccountDao accountDao = daoFactory.createAccountDao(connection);
                Optional<Account> entity = accountDao.findByCard(cardId);
                connection.commit();
                return entity;
            }
    }
}
