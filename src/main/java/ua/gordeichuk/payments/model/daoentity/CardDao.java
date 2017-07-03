package ua.gordeichuk.payments.model.daoentity;

import ua.gordeichuk.payments.model.entity.Card;

import java.util.List;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface CardDao extends Dao<Card> {
    String ENTITY_NAME = "card";
    List<Card> findManyByUser(Long userId);
    List<Card> findManyByUserAndCardStatus(Long userId, String cardStatus);
    List<Card> findManyByAccount(Long accountId);
    List<Card> findManyByAccountAndCardStatus(Long accountId, String cardStatus);
}
