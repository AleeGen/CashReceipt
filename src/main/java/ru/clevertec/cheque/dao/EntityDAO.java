package ru.clevertec.cheque.dao;

import java.util.List;

public interface EntityDAO<T> {

    List<T> getAll();

    T getById(int id);

    void save(T t);

    void delete(int id);

    void update(T t);
}
