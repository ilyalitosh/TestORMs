package com.ilya.litosh.roomvsrealm.models;

import io.reactivex.Observable;

public interface DBBaseModel {

    /**
     * Возвращает строку с результатом вставки
     * @param rows кол-во вставляемых строк
     */
    String insertingRes(int rows);

    /**
     * возвращает Observable c результатом вставки
     * @param rows кол-во вставляемых строк
     */
    Observable<String> reactiveInsertingRes(int rows);

    /**
     * возвращает результат чтения всех записей
     */
    String readingAllRes();

    /**
     * возвращает Observable с результатом чтения всех записей
     */
    Observable<String> reactiveReadingAllRes();

    /**
     * возвращает результат поиска по id
     * @param id id для поиска
     */
    String readingByIdRes(int id);

    /**
     * возвращает Observable с результатом поиска по id
     * @param id id для поиска
     */
    Observable<String> reactiveReadingByIdRes(int id);

}
