package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.entity.Transaction;

/**
 * Created by Валерий on 26.06.2017.
 */
public class TransactionService extends Service<Transaction> {
    private static final String ENTITY_NAME = "transaction";
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class);

    protected TransactionService(String entityName) {
        super(ENTITY_NAME);
    }
}
