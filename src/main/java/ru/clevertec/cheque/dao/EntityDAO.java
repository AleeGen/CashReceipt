package ru.clevertec.cheque.dao;

import java.util.List;

public interface EntityDAO<T> {

    List<T> getAll();

    T getById(Integer id);

    void save(T t);

    void delete(Integer id);

    void update(T t);
}
