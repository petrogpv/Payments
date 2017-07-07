package ua.gordeichuk.payments.controller.daoentity;

import ua.gordeichuk.payments.model.entity.User;

import java.util.Optional;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface UserDao extends Dao<User> {
    String ENTITY_NAME = "user";
    Optional<User> findByLogin(String login);
}
