package ua.gordeichuk.payments.daoentity;

import ua.gordeichuk.payments.dto.entityparam.TransactionParamDto;
import ua.gordeichuk.payments.entity.Transaction;

import java.util.List;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface TransactionDao extends Dao<Transaction> {
    String ENTITY_NAME = "transaction";
    List<Transaction> findManyByParamDTO(TransactionParamDto transactionParamDto);
}
