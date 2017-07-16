package ua.gordeichuk.payments.dao;

import ua.gordeichuk.payments.entity.UserAuth;

/**
 * DAO interface for CRUD operations with entity UserAuth
 */
public interface UserAuthDao extends Dao<UserAuth> {
    String ENTITY_NAME = "userAuth";

}
