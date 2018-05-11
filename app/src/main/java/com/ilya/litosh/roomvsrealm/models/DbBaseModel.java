package com.ilya.litosh.roomvsrealm.models;

import io.reactivex.Observable;

public interface DbBaseModel {

    /**
     * Returns String with insert result
     * @param rows insert string count
     */
    String insertingResult(int rows);

    /**
     * Returns Observable with insert result
     * @param rows insert string count
     */
    Observable<String> reactiveInsertingResult(int rows);

    /**
     * Returns reading result all rows
     */
    String readingAllResult();

    /**
     * Returns Observable with reading result all rows
     */
    Observable<String> reactiveReadingAllResult();

    /**
     * Returns searching result by id
     * @param id id for searching
     */
    String readingByIdResult(int id);

    /**
     * Returns Observable with searching result by id
     * @param id id for searching
     */
    Observable<String> reactiveReadingByIdResult(int id);

}
