package ua.gordeichuk.payments.daoentity;

import ua.gordeichuk.payments.entity.Account;

import java.util.Optional;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface AccountDao extends Dao<Account> {
    String ENTITY_NAME = "account";
    Optional<Account> findByCard(Long CardId);
}
