package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.Dao;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.daoentity.UserAuthDao;
import ua.gordeichuk.payments.dao.daoentity.UserDao;
import ua.gordeichuk.payments.entity.User;

import java.util.Optional;

/**
 * Created by Валерий on 26.06.2017.
 */
public class UserService extends Service<User> {
    private static final Logger LOGGER = Logger.getLogger(User.class);

    private static class Holder{
        static final UserService INSTANCE = new UserService();
    }

    private UserService () {
        super(UserDao.ENTITY_NAME);
    }

    public static UserService getInstance(){
        return UserService.Holder.INSTANCE;
    }

    public Optional<User> findByLogin(String login) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            UserDao userDao = (UserDao)daoFactory.createDao(entityName, connection);
            Optional<User> user = userDao.findByLogin(login);
            return user;
        }
    }
}
