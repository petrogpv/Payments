package ua.gordeichuk.payments.daoentity;

import ua.gordeichuk.payments.dto.entityparam.CardParamDto;
import ua.gordeichuk.payments.entity.Card;

import java.util.List;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface CardDao extends Dao<Card> {
    String ENTITY_NAME = "card";
    List<Card> findManyByDto(CardParamDto cardParamDto);
}
