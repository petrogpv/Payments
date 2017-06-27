package ua.gordeichuk.payments.exception;

/**
 * Created by Валерий on 25.06.2017.
 */
public class ServiceException extends Exception {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
