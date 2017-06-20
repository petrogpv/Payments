package ua.gordeichuk.payments.dao;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface DaoConnection extends AutoCloseable {
    void begin();
    void commit();
    void rollback();
    void close();
}
