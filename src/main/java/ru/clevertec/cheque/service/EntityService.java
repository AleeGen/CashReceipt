package ru.clevertec.cheque.service;

import java.util.List;

public interface EntityService<T> {
    List<T> getAll();

    T getById(int id);

    void save(T t);

    void delete(int id);

    void update(T t);
}
