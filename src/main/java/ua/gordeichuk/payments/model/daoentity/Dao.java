package ua.gordeichuk.payments.model.daoentity;

import ua.gordeichuk.payments.model.entity.Entity;

import java.util.List;
import java.util.Optional;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface Dao<E extends Entity> {
    Optional<E> find(Long id);
    List<E> findAll();
    boolean create(E e);
    boolean update(E e);
    boolean delete(Long id);
    String getEntityName();
}
