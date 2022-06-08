package ru.javawebinar.topjava.dao;

import java.util.List;

public interface InterfaceDao<E> {
    void add(E entity);

    void save(E entity);

    void delete(E entity);

    List<E> get();
}
