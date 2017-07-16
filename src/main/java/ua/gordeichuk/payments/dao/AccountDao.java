package ua.gordeichuk.payments.dao;

import ua.gordeichuk.payments.entity.Account;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for CRUD operations with entity Account
 */
public interface AccountDao extends Dao<Account> {
    String ENTITY_NAME = "account";

    Optional<Account> findByCard(Long cardId);

    List<Account> findManyByUser(Long userId);
}
