package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.Hasher;
import ua.gordeichuk.payments.daoentity.AccountDao;
import ua.gordeichuk.payments.daoentity.UserAuthDao;
import ua.gordeichuk.payments.daoentity.UserDao;
import ua.gordeichuk.payments.daojdbc.DaoConnection;
import ua.gordeichuk.payments.daojdbc.DaoFactory;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.UserAuth;
import ua.gordeichuk.payments.entity.enums.UserRole;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.service.localization.Message;
import ua.gordeichuk.payments.service.localization.MessageDto;
import ua.gordeichuk.payments.service.localization.MessageDtoBuilder;

import java.util.List;
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

            User user = findUserByLogin(login, connection);
            checkUserIsSignedUp(login, user);
            authenticateUser(password, user);

            connection.commit();
            return user;
        }
    }


    public boolean addUser(User user) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            UserDao userDao = daoFactory.createUserDao(connection);
            Optional<User> userOptional = userDao
                    .findByLogin(user.getUserAuth().getLogin());
            checkUserIsNull(userOptional);
            saveUser(user, connection);
            connection.commit();
        }
        return true;
    }

    public void deleteUser(Long userId) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            User user = findUser(userId, connection);
            AccountDao accountDao = daoFactory.createAccountDao(connection);
            List<Account> accounts = accountDao.findManyByUser(user.getId());
            for (Account account : accounts) {
                accountDao.delete(account.getId());
            }
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.delete(userId);
            connection.commit();
        }
    }

    public User signUpUser(String login, String password) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            User user = findUserByLogin(login, connection);
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

    public User findUserByLogin(String login) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            User user = findUserByLogin(login, connection);
            connection.commit();
            return user;
        }
    }
    public User findUserByLogin(String login, DaoConnection connection)
            throws ServiceException {
            UserDao userDao = daoFactory.createUserDao(connection);
            Optional<User> userOptional = userDao.findByLogin(login);
            if (!userOptional.isPresent()) {
                MessageDto messageDto = new MessageDtoBuilder()
                        .addMessage(Message.USER_NOT_EXIST)
                        .addMessage(login)
                        .build();
                throw new ServiceException(messageDto.getMessage());
            }
            return userOptional.get();
    }
    public User findUser(Long userId, DaoConnection connection)
            throws ServiceException {
        UserDao userDao = daoFactory.createUserDao(connection);
        Optional<User> userOptional = userDao.find(userId);
        if (!userOptional.isPresent()) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.USER_NOT_EXIST)
                    .addMessage(String.valueOf(userId))
                    .build();
            throw new ServiceException(messageDto.getMessage());
        }
        return userOptional.get();
    }

    private void checkUserIsNull(Optional<User> userOptional)
            throws ServiceException {
        if (userOptional.isPresent()) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.USER_IS_ALREADY_REGISTERED)
                    .build();
            LOGGER.warn(messageDto.getMessage());
            throw new ServiceException(messageDto.getLogMessage());
        }
    }

    private void saveUser(User user, DaoConnection connection) {
        UserAuth userAuth = user.getUserAuth();
        UserDao userDao = daoFactory.createUserDao(connection);
        userDao.create(user);
        userAuth.setId(user.getId());
        userAuth.setRole(UserRole.USER);
        UserAuthDao userAuthDao = daoFactory.createUserAuthDao(connection);
        userAuthDao.create(userAuth);
    }

    private void authenticateUser(String password, User user)
            throws ServiceException {
        String sole = user.getUserAuth().getSole();
        String hash = Hasher.getHash(password, sole);
        if (!user.getUserAuth().getPassword().equals(hash)) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.WRONG_PASSWORD)
                    .build();
            LOGGER.warn(messageDto.getMessage());
            throw new ServiceException(messageDto.getLogMessage());
        }
    }

    private void checkUserIsSignedUp(String login, User user)
            throws ServiceException {
        if (user.getUserAuth().getPassword() == null) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.USER_IS_NOT_SIGNED_UP)
                    .addMessage(login)
                    .build();
            LOGGER.warn(messageDto.getMessage());
            throw new ServiceException(messageDto.getLogMessage());
        }
    }

    private void checkUserIsNotSignedUp(String login, User user)
            throws ServiceException {
        if (user.getUserAuth().getPassword() != null) {
            MessageDto messageDto = new MessageDtoBuilder()
                    .addMessage(Message.USER_IS_ALREADY_SIGNED_UP)
                    .addMessage(login)
                    .build();
            LOGGER.warn(messageDto.getMessage());
            throw new ServiceException(messageDto.getLogMessage());
        }
    }
}
