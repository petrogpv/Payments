package ua.gordeichuk.payments.controller.daoentity;

import ua.gordeichuk.payments.model.entity.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface TransactionDao extends Dao<Transaction> {
    String ENTITY_NAME = "transaction";

//    List<Transaction> findManyByAccount(Long accountId);
//    List<Transaction> findManyByAccountAndByDate(Long accountId, Date date);
//    List<Transaction> findManyByAccountAndByDateBetween(Long accountId, Date dateFrom, Date dateTo);
//    List<Transaction> findManyByUser(Long userId);
//    List<Transaction> findManyByUserAndByDate(Long userId, Date date);
//    List<Transaction> findManyByUserAndByDateBetween(Long userId, Date dateFrom, Date dateTo);

    List<Transaction> findManyByCardAndType(Long cardId, String type);
    List<Transaction> findManyByCardAndDateBetweenAndType(Long cardId, Date dateFrom, Date dateTo, String type);
    List<Transaction> findManyByCardAndDateBeforeAndType(Long cardId, Date date, String type);
    List<Transaction> findManyByCardAndDateAfterAndType(Long cardId, Date date, String type);

    List<Transaction> findManyByCard(Long cardId);
    List<Transaction> findManyByCardAndDateBetween(Long cardId, Date dateFrom, Date dateTo);
    List<Transaction> findManyByCardAndDateBefore(Long cardId, Date date);
    List<Transaction> findManyByCardAndDateAfter(Long cardId, Date date);
}
