package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.entity.Card;

/**
 * Created by Валерий on 26.06.2017.
 */
public class CardService extends Service<Card> {
    private static final String ENTITY_NAME = "card";
    private static final Logger LOGGER = Logger.getLogger(CardService.class);

    protected CardService(String entityName) {
        super(ENTITY_NAME);
    }
}
