package com.ilya.litosh.roomvsrealm.db;

import java.util.List;

public interface DBBaseModel<T> {

    void insert(T t);

    List<T> getAll();

    T getById(int id);

}
