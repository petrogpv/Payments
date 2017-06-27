package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.entity.Account;

/**
 * Created by Валерий on 26.06.2017.
 */
public class AccountService extends Service<Account> {
    private static final String ENTITY_NAME = "account";
    private static final Logger LOGGER = Logger.getLogger(AccountService.class);

    protected AccountService() {
        super(ENTITY_NAME);
    }
}
