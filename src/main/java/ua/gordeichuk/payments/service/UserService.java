package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.Hasher;
import ua.gordeichuk.payments.daojdbc.DaoConnection;
import ua.gordeichuk.payments.daojdbc.DaoFactory;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.util.Message;
import ua.gordeichuk.payments.daoentity.UserAuthDao;
import ua.gordeichuk.payments.daoentity.UserDao;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.UserAuth;
import ua.gordeichuk.payments.util.MessageDto;

import java.util.Optional;

/**
 * Created by Валерий on 26.06.2017.
 */
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private UserService() {
    }

    private static class Holder {
        static final UserService INSTANCE = new UserService();
    }

    public static UserService getInstance() {
        return UserService.Holder.INSTANCE;
    }

    public User signInUser(UserAuth userAuth) throws ServiceException {
        String login = userAuth.getLogin();
        String password = userAuth.getPassword();

        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            UserDao userDao = daoFactory.createUserDao(connection);
            Optional<User> optionalUser = userDao.findByLogin(login);
            checkUserIsNotNull(login, optionalUser);
            User user = optionalUser.get();
            checkUserIsSignedUp(login, user);
            authenticateUser(password, user);

            connection.commit();
            return user;
        }
    }

    public User signUpUser(String login, String password, String passwordConfirm) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            UserDao userDao = daoFactory.createUserDao(connection);

            Optional<User> optionalUser = userDao.findByLogin(login);
            checkUserIsNotNull(login, optionalUser);
            User user = optionalUser.get();
            checkUserIsNotSignedUp(login, user);
            UserAuth userAuth = user.getUserAuth();

            String sole = Hasher.createSoleString();
            String passwordHash = Hasher.getHash(password, sole);

            userAuth.setSole(sole);
            userAuth.setPassword(passwordHash);
            UserAuthDao userAuthDao = daoFactory.createUserAuthDao(connection);
            userAuthDao.update(userAuth);
            connection.commit();
            return user;
        }

    }

    private void checkUserIsNotNull(String login, Optional<User> optionalUser) throws ServiceException {
        if (!optionalUser.isPresent()) {
            MessageDto messageDto = new MessageDto.Builder()
                    .addMessage(Message.WRONG_LOGIN)
                    .build();
            LOGGER.warn(messageDto.getMessage());
            throw new ServiceException(messageDto.getLogMessage());
        }
    }

    private void authenticateUser(String password, User user) throws ServiceException {
        String sole = user.getUserAuth().getSole();
        String hash = Hasher.getHash(password, sole);
        if (!user.getUserAuth().getPassword().equals(hash)) {
            MessageDto messageDto = new MessageDto.Builder()
                    .addMessage(Message.WRONG_PASSWORD)
                    .build();
            LOGGER.warn(messageDto.getMessage());
            throw new ServiceException(messageDto.getLogMessage());
        }
    }

    private void checkUserIsSignedUp(String login, User user) throws ServiceException {
        if (user.getUserAuth().getPassword() == null) {
            MessageDto messageDto = new MessageDto.Builder()
                    .addMessage(Message.USER_IS_NOT_SIGNED_UP)
                    .addMessage(login)
                    .build();
            LOGGER.warn(messageDto.getMessage());
            throw new ServiceException(messageDto.getLogMessage());
        }
    }
    private void checkUserIsNotSignedUp(String login, User user) throws ServiceException {
        if (user.getUserAuth().getPassword() != null) {
            MessageDto messageDto = new MessageDto.Builder()
                    .addMessage(Message.USER_IS_ALREADY_SIGNED_UP)
                    .addMessage(login)
                    .build();
            LOGGER.warn(messageDto.getMessage());
            throw new ServiceException(messageDto.getLogMessage());
        }
    }




    public Optional<User> findByLogin(String login) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            UserDao userDao = daoFactory.createUserDao(connection);
            Optional<User> user = userDao.findByLogin(login);
            return user;
        }
    }
}
