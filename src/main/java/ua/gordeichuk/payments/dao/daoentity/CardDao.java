package ua.gordeichuk.payments.dao.daoentity;

import ua.gordeichuk.payments.dao.Dao;
import ua.gordeichuk.payments.entity.Card;

import java.util.List;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface CardDao extends Dao<Card> {
    List<Card> findManyByUser(Long userId);
    List<Card> findManyByUserAndCardStatus(Long userId, String cardStatus);
    List<Card> findManyByAccount(Long accountId);
    List<Card> findManyByAccountAndCardStatus(Long accountId, String cardStatus);
}
