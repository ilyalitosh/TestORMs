package com.ilya.litosh.roomvsrealm.db.snappydb;

import android.util.Log;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.snappydb.models.Book;
import com.ilya.litosh.roomvsrealm.models.DbBaseModel;
import com.ilya.litosh.roomvsrealm.models.IEntityGenerator;
import com.ilya.litosh.roomvsrealm.models.ResultString;
import com.snappydb.SnappydbException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilya_ on 22.04.2018.
 */

public class SnappyDbService implements DbBaseModel, IEntityGenerator<Book> {

    private static final String TAG = "SnappyDbService";

    @Override
    public String insertingResult(int rows) {
        long start = System.currentTimeMillis();
        for(int i = 0; i < rows; i++){
            try {
                App.getSnappyDBSession()
                        .put("android:" + String.valueOf(i), generateEntity(0));
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
        }
        return ResultString.getResult(start, System.currentTimeMillis());
    }

    @Override
    public Observable<String> reactiveInsertingResult(int rows) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            for(int i = 0; i < rows; i++){
                try {
                    App.getSnappyDBSession()
                            .put("android:" + String.valueOf(i), generateEntity(0));
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
            }
            return ResultString.getResult(start, System.currentTimeMillis());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingAllResult() {
        long start = System.currentTimeMillis();
        long end = 0;
        String[] keys;
        try {
            keys = App.getSnappyDBSession().findKeys("android:");
            List<Book> books = new ArrayList<>();
            for(int i = 0; i < keys.length; i++){
                books.add(App.getSnappyDBSession().getObject(keys[i], Book.class));
            }
            end = System.currentTimeMillis();
            Log.i(TAG, "Найдено: " + books.size());
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingAllResult() {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            long end = 0;
            String[] keys;
            try {
                keys = App.getSnappyDBSession().findKeys("android:");
                List<Book> books = new ArrayList<>();
                for(int i = 0; i < keys.length; i++){
                    books.add(App.getSnappyDBSession().getObject(keys[i], Book.class));
                }
                end = System.currentTimeMillis();
                Log.i(TAG, "Найдено: " + books.size());
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
            return ResultString.getResult(start, end);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingByIdResult(int id) {
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            Book book = App.getSnappyDBSession().getObject("android:" + id, Book.class);
            end = System.currentTimeMillis();
            Log.i(TAG, book.getName() + " "
                    + book.getAuthor() + " "
                    + book.getPagesCount());
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingByIdResult(int id) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            long end = 0;
            try {
                Book book = App.getSnappyDBSession().getObject("android:" + id, Book.class);
                end = System.currentTimeMillis();
                Log.i(TAG, book.getName() + " "
                        + book.getAuthor() + " "
                        + book.getPagesCount());
            } catch (SnappydbException e) {
                e.printStackTrace();
            }
            return ResultString.getResult(start, end);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Book generateEntity(long id) {
        Book book = new Book();
        book.setAuthor("Толстой");
        book.setDate(2005);
        book.setName("Пессель");
        book.setPagesCount(1000);

        return book;
    }

}
