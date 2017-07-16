package ua.gordeichuk.payments.service;

import org.junit.Test;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.DaoFactory;
import ua.gordeichuk.payments.dao.UserAuthDao;
import ua.gordeichuk.payments.dao.UserDao;
import ua.gordeichuk.payments.entity.User;
import ua.gordeichuk.payments.entity.UserAuth;
import ua.gordeichuk.payments.exception.ServiceException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    UserService userService;
    DaoFactory daoFactory;
    DaoConnection daoConnection;
    UserDao userDao;
    UserAuthDao userAuthDao;
    User user;
    UserAuth userAuth;

    private void doInitialization() {
        userAuth = mock(UserAuth.class);
        userDao = mock(UserDao.class);
        userAuthDao = mock(UserAuthDao.class);
        daoConnection = mock(DaoConnection.class);
        daoFactory = mock(DaoFactory.class);
        userService = new UserService(daoFactory);
        user = mock(User.class);

    }
    private void stubDaoFactory(){
        when(daoFactory.getConnection()).thenReturn(daoConnection);
        when(daoFactory.createUserDao(daoConnection)).thenReturn(userDao);
        when(daoFactory.createUserAuthDao(daoConnection)).thenReturn(userAuthDao);
    }

    @Test
    public void testAddUser() throws ServiceException {
        doInitialization();
        stubDaoFactory();

        when(user.getUserAuth()).thenReturn(userAuth);
        when(userAuth.getLogin()).thenReturn("");
        when(userDao.findByLogin(anyString())).thenReturn(Optional.empty());

        boolean result = userService.addUser(user);
        assertTrue(result);

        verify(userAuth).getLogin();
        verify(userDao).findByLogin("");

        verify(daoFactory).getConnection();
        verify(daoConnection).begin();
        verify(daoConnection).close();
        verify(userAuthDao).create(userAuth);

    }

}
