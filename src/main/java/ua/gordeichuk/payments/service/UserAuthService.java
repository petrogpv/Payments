package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.entity.UserAuth;

/**
 * Created by Валерий on 26.06.2017.
 */
public class UserAuthService extends Service<UserAuth> {
    private static final String ENTITY_NAME = "userAuth";
    private static final Logger LOGGER = Logger.getLogger(UserAuthService.class);

    protected UserAuthService(String entityName) {
        super(ENTITY_NAME);
    }
}
