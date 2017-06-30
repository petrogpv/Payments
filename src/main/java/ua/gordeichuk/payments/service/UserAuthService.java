package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.daoentity.TransactionDao;
import ua.gordeichuk.payments.dao.daoentity.UserAuthDao;
import ua.gordeichuk.payments.entity.UserAuth;

/**
 * Created by Валерий on 26.06.2017.
 */
public class UserAuthService extends Service<UserAuth> {
    private static final Logger LOGGER = Logger.getLogger(UserAuthService.class);

    private static class Holder{
        static final UserAuthService INSTANCE = new UserAuthService();
    }

    private UserAuthService () {
        super(UserAuthDao.ENTITY_NAME);
    }

    public static UserAuthService getInstance(){
        return UserAuthService.Holder.INSTANCE;
    }
}
