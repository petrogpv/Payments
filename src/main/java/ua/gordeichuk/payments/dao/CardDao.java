package ua.gordeichuk.payments.dao;

import ua.gordeichuk.payments.dao.parameters.dto.CardParamDto;
import ua.gordeichuk.payments.entity.Card;

import java.util.List;

/**
 * DAO interface for CRUD operations with entity Card
 */
public interface CardDao extends Dao<Card> {
    String ENTITY_NAME = "card";

    List<Card> findManyByDto(CardParamDto cardParamDto);
}
