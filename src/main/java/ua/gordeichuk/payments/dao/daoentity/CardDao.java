package ua.gordeichuk.payments.dao.daoentity;

import ua.gordeichuk.payments.dao.GenericDao;
import ua.gordeichuk.payments.entity.Account;
import ua.gordeichuk.payments.entity.Card;

import java.util.List;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface CardDao extends GenericDao<Card> {
    List<Card> findManyByAccount(Account account);
}
