package ua.gordeichuk.payments.daoentity;

import ua.gordeichuk.payments.entity.User;

import java.util.Optional;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface UserDao extends Dao<User> {
    String ENTITY_NAME = "user";
    Optional<User> findByLogin(String login);
}
