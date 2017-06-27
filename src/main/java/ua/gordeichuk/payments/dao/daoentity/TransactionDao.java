package ua.gordeichuk.payments.dao.daoentity;

import ua.gordeichuk.payments.dao.Dao;
import ua.gordeichuk.payments.entity.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface TransactionDao extends Dao<Transaction> {
    List<Transaction> findManyByCard(Long cardId);
    List<Transaction> findManyByAccount(Long accountId);
    List<Transaction> findManyByAccountAndByDate(Long accountId, Date date);
    List<Transaction> findManyByAccountAndByDateBetween(Long accountId, Date dateFrom, Date dateTo);
    List<Transaction> findManyByUser(Long userId);
    List<Transaction> findManyByUserAndByDate(Long userId, Date date);
    List<Transaction> findManyByUserAndByDateBetwee(Long userId, Date dateFrom, Date dateTo);
}
