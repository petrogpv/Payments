package ua.gordeichuk.payments.daoentity;

import ua.gordeichuk.payments.entity.UserAuth;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface UserAuthDao extends Dao<UserAuth> {
    String ENTITY_NAME = "userAuth";

}
