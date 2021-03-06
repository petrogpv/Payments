package ua.gordeichuk.payments.util;

public final class LogMessage {

    public static final String PREPARED_STATEMENT = "Prepared statement: ";
    public static final String DB_ERROR_FIND = "DB error operation [find] in table: ";;
    public static final String DB_ERROR_CREATE = "DB error operation [create] in table: ";
    public static final String DB_ERROR_UPDATE = "DB error operation [update] table: ";
    public static final String DB_ERROR_DELETE = "DB error operation [delete] from table: ";
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
    public static final String RETRIEVE_DATASOURCE_ERROR = "Can't retrieve datasource ";
    public static final String ROLLBACK = "Rollback invoked ";
    public static final String CLOSE_WHILE_ROLLBACK_ERROR = "Error while closing connection after rollback " ;
    public static final String SET_AUTOCOMMIT_ERROR = "Set autocommit \'false\' error ";
    public static final String COMMIT_ERROR = "Commit error ";
    public static final String ROLLBACK_ERROR = "Rollback error ";
    public static final String TO = " to ";
    public static final String FROM = " from ";
    public static final String VALUE = " value ";
    public static final String DEPOSITING_SUCCESSFUL = "Depositing successful to card: ";
    public static final String ATTEMPT_TO_VISIT_AUTHORIZED = "Attempt ro visit authorized area without signing in ";
    public static final String ATTEMPT_TO_VISIT_WITHOUT_PERMISSION = "Attempt ro visit without permission ";
    public static final String SET_ENTITY_TO_PS_ERROR = "Error while setting entity to prepared statement for" +
            " entity name: ";
    public static final String EXTRACT_ENTITY_ERROR = "Error while extracting entity from result set for" +
            " entity name: ";
    public static final String USER_SIGNED_IN = "Visitor successfully signed in: ";
    public static final String ERROR_NOT_IDENTIFIED = "Unpredictable error has occurred! " ;
    public static final String VISITOR_WAS_DENIED_ACCESS = "The visitor was denied access: ";
    public static final String USER_LOGOUT = "User logout: ";
    public static final String REGISTRATION_ERROR = "Registration error: ";
    public static final String SET_LOCALE = "Locale set: ";
    public static final String  ATTEMPT_TO_SIGN_UP_BY_SIGNED_IN_USER = "Attempt to visit get sign up while signing in.";
    public static final String PAYMENT_OK = "Payment successful";
    public static final String DEPOSIT_OK = "Deposit successful ";
    public static final String PAYMENT_FAILED = "Payment failed " ;
    public static final String DEPOSIT_FAILED = "Deposit failed ";
    public static final String CARD_STATUS_CHANGED = "Card status changed: ";
    public static final String LOCKING_FAILED = "Card locking failed: ";
    public static final String TRANSACTIONS_FOUND =  "Transactions found: ";
    public static final String SEARCHING_TRANSACTIONS_FAILED = "Transactions search failed!";
    public static final String MANAGE_SEARCH_FAILED = "Manage search failed!";
    public static final String CARDS_ACTION_ATTRIBUTE_ERROR = "Wrong attribute \"action\" for card status change: ";
    public static final String CHANGE_CARD_STATUS_FAILED = "Change card status failed for card: ";
    public static final String USER_REGISTRATION_ERROR = "User was not registered: ";
    public static final String ADD_CARD_FAILED = "Adding card to account failed";
    public static final String ACCOUNT_DELETE_FAILED = "Account deletion failed";
    public static final String CARD_DELETION_FAILED = "Card deletion failed";
    public static final String USER_DELETION_FAILED = "User deletion failed";;
}
