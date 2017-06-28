package ua.gordeichuk.payments.util;

/**
 * Created by Валерий on 25.06.2017.
 */
public final class LogMessages {

    public static final String EXCEPTION = "exception";
    public static final String PREPARED_STATEMENT = "Prepared statement: ";
    public static final String DB_ERROR_FIND = "DB error operation [find] in table: ";;
    public static final String DB_ERROR_CREATE = "DB error operation [create] in table: ";
    public static final String DB_ERROR_UPDATE = "DB error operation [update] table: ";
    public static final String DB_ERROR_DELETE = "DB error operation [delete] from table: ";
    public static final String EXCEPTION_MESSAGE = ", exception message: ";
    public static final String ROWS_FOUND = "Rows found: ";
    public static final String ROWS_SAVED = "Rows saved: ";
    public static final String ROWS_UPDATED = "Rows updated: ";
    public static final String ROWS_DELETED = "Rows deleted: ";
    public static final String IN_TABLE = " in table ";
    public static final String RB_READ_ERROR = "Can't read resource bundle file: ";
    public static final String RB_READ_SUCCESSFUL = "Resource bundle read successfully: ";
    public static final String DAO_FACTORY_CREATED = "Dao factory created  successfully: ";
    public static final String DAO_FACTORY_CREATION_ERROR = "Error while creating Dao factory: ";
    public static final String GET_CONNECTION_ERROR = "Can't get connection from datasource ";
    public static final String CREATE_DAO_ERROR = "Can't create DAO for entity: ";
    public static final String RETRIEVE_DATASOURCE_ERROR = "Can't retrieve datasource ";
    public static final String ROLLBACK = "Rollback invoked ";
    public static final String CLOSE_WHILE_ROLLBACK_ERROR = "Error while closing connection after rollback " ;
    public static final String SET_AUTOCOMMIT_ERROR = "Set autocommit \'false\' error ";
    public static final String COMMIT_ERROR = "Commit error ";
    public static final String ROLLBACK_ERROR = "Rollback error";
    public static final String SET_TRANSACTION_ISO_LEVEL_ERROR = "Set transaction isolation level error";
    public static final String TRANSFER_SUCCESSFUL = "Transfer successful from  ";
    public static final String TO = "to ";
    public static final String DEPOSITING_SUCCESSFUL = "Depositing successful to card: ";
    public static final String TRANSACTION_DELETE_ERROR = "Transaction deletion isn't supported";
}
