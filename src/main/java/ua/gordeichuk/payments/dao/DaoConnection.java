package ua.gordeichuk.payments.dao;

/**
 * Wrapper interface for Connection object
 */
public interface DaoConnection extends AutoCloseable {
    void begin();

    void commit();

    void rollback();

    void close();
}
