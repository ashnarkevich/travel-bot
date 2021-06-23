package com.gmail.petrikov05.app.repository;

import java.util.List;

import com.gmail.petrikov05.app.repository.exception.RepositoryException;

public interface GenericRepository<I, T> {

    void add(T t) throws RepositoryException;

    void delete(T t);

    void update(T t);

    List<T> findAll();

}
