package ua.gordeichuk.payments.dao;

import ua.gordeichuk.payments.dao.parameters.dto.TransactionParamDto;
import ua.gordeichuk.payments.entity.Transaction;

import java.util.List;

/**
 * DAO interface for CRUD operations with entity Transaction
 */
public interface TransactionDao extends Dao<Transaction> {
    String ENTITY_NAME = "transaction";

    List<Transaction> findManyByParamDTO(TransactionParamDto transactionParamDto);

    Integer findCountByLogin(String login);

    Integer findCountByAccount(Long accountId);

    Integer findCountByCard(Long cardId);
}
