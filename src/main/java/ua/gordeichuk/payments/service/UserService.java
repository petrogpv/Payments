package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.entity.User;

/**
 * Created by Валерий on 26.06.2017.
 */
public class UserService extends Service<User> {
    private static final String ENTITY_NAME = "user";
    private static final Logger LOGGER = Logger.getLogger(User.class);

    protected UserService(String entityName) {
        super(ENTITY_NAME);
    }
}
