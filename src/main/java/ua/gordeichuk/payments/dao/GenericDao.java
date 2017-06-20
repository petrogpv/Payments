package ua.gordeichuk.payments.dao;

import ua.gordeichuk.payments.entity.Entity;

import java.util.List;
import java.util.Optional;

/**
 * Created by Валерий on 18.06.2017.
 */
public interface GenericDao<E extends Entity> {
    Optional<E> find(Long id);
    List<E> findAll();
    void create(E e);
    void update(E e);
    void delete(Long id);
}
