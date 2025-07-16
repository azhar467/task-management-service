package com.azhar.taskmanagement.service.interfaces;

import java.util.List;

public interface EntityService<T, ID> {
    T save(T entity);
    List<T> findAll();
    T findById(ID id);
    T update(ID id, T entity);
    void deleteById(ID id);
}