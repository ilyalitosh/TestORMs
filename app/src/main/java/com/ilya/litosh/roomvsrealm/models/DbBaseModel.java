package com.ilya.litosh.roomvsrealm.models;

import io.reactivex.Observable;

public interface DbBaseModel {

    /**
     * Returns String with insert result
     * @param rows insert string count
     */
    String insertingRes(int rows);

    /**
     * Returns Observable with insert result
     * @param rows insert string count
     */
    Observable<String> reactiveInsertingRes(int rows);

    /**
     * Returns reading result all rows
     */
    String readingAllRes();

    /**
     * Returns Observable with reading result all rows
     */
    Observable<String> reactiveReadingAllRes();

    /**
     * Returns searching result by id
     * @param id id for searching
     */
    String readingByIdRes(int id);

    /**
     * Returns Observable with searching result by id
     * @param id id for searching
     */
    Observable<String> reactiveReadingByIdRes(int id);

}
