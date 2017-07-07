package ua.gordeichuk.payments.controller.daoentity;

import ua.gordeichuk.payments.model.entity.UserAuth;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface UserAuthDao extends Dao<UserAuth> {
    String ENTITY_NAME = "userAuth";

}
