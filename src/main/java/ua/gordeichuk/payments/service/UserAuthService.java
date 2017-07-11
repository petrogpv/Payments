package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.daojdbc.DaoFactory;

/**
 * Created by Валерий on 26.06.2017.
 */
public class UserAuthService {
    private static final Logger LOGGER = Logger.getLogger(UserAuthService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private UserAuthService() {
    }

    private static class Holder{
        static final UserAuthService INSTANCE = new UserAuthService();
    }

    public static UserAuthService getInstance(){
        return UserAuthService.Holder.INSTANCE;
    }



}
