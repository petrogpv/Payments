package ua.gordeichuk.payments.dao.daoentity;

import ua.gordeichuk.payments.dao.Dao;
import ua.gordeichuk.payments.entity.User;

import java.util.Optional;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface UserDao extends Dao<User> {
    Optional<User> findByLogin(String login);
}
