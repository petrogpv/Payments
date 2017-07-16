package ua.gordeichuk.payments.dao;

import ua.gordeichuk.payments.entity.Entity;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for common CRUD operations with entities
 */
public interface Dao<E extends Entity> {
    Optional<E> find(Long id);
    List<E> findAll();
    boolean create(E e);
    boolean update(E e);
    boolean delete(Long id);
}
