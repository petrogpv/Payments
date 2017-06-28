package ua.gordeichuk.payments.dao.daoentity;

import ua.gordeichuk.payments.dao.Dao;
import ua.gordeichuk.payments.entity.Account;

import java.util.Optional;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface AccountDao extends Dao<Account> {
    String ENTITY_NAME = "transaction";
    Optional<Account> findByCard(Long CardId);
}
