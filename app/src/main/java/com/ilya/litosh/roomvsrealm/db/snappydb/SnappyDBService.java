package com.ilya.litosh.roomvsrealm.db.snappydb;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.snappydb.models.Book;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ilya_ on 22.04.2018.
 */

public class SnappyDBService implements ISnappyDBService {
    @Override
    public Observable<Book> addBook(Book book, String key) {
        return Observable.just(book)
                .doOnNext(book1 -> {
                    App.getSnappyDBSession().put(key, book);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Book>> getBooks() {
        return Observable.just(Book.class)
                .flatMap(bookClass -> {
                    String[] keys = App.getSnappyDBSession().findKeys("android");
                    List<Book> books = new ArrayList<>();
                    for(int i = 0; i < keys.length; i++){
                        books.add(App.getSnappyDBSession().getObject(keys[i], Book.class));
                    }
                    return Observable.just(books);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Book> getBookByKey(int key) {
        return Observable.just(Book.class)
                .flatMap(bookClass -> {
                    Book book = App.getSnappyDBSession().getObject("android:" + key, Book.class);
                    return Observable.just(book);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
