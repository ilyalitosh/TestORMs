package com.ilya.litosh.roomvsrealm.models;

import io.reactivex.Observable;

public interface DBBaseModel {

    String insert(int rows);

    Observable<String> insertRx(int rows);

    String getAll();

    Observable<String> getAllRx();

    String getById(int id);

    Observable<String> getByIdRx(int id);

}
