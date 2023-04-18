package ru.clevertec.cheque.service;

import java.util.List;

public interface EntityService<T> {
    List<T> findAll(Integer page, Integer size);

    T findById(int id);

    T save(T t);

    void deleteById(int id);

    T update(T t);

}