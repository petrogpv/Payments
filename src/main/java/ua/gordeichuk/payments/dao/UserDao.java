package ua.gordeichuk.payments.dao;

import ua.gordeichuk.payments.entity.User;

import java.util.Optional;

/**
 * DAO interface for CRUD operations with entity User
 */
public interface UserDao extends Dao<User> {
    String ENTITY_NAME = "user";

    Optional<User> findByLogin(String login);
}
