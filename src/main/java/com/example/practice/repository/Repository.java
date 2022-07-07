package com.example.practice.repository;

import java.util.List;

public interface Repository<T, U> {
    List<T> getAll();
    T getById(U id);
    T save(T object);
    void remove(U id);
}
