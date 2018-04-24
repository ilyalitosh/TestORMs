package com.ilya.litosh.roomvsrealm.db.snappydb;

import com.ilya.litosh.roomvsrealm.db.snappydb.models.Book;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ilya_ on 22.04.2018.
 */

public interface ISnappyDBService {

    Observable<Book> addBook(Book book, String key);

    Observable<List<Book>> getBooks();

    Observable<Book> getBookByKey(int key);

}
