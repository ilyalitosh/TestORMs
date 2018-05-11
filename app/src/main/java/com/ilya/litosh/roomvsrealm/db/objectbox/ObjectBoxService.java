package com.ilya.litosh.roomvsrealm.db.objectbox;

import android.util.Log;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.models.DbBaseModel;
import com.ilya.litosh.roomvsrealm.db.objectbox.models.Figure;
import com.ilya.litosh.roomvsrealm.models.IEntityGenerator;
import com.ilya.litosh.roomvsrealm.models.ResultString;

import java.util.List;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ilya_ on 22.04.2018.
 */

public class ObjectBoxService implements DbBaseModel, IEntityGenerator<Figure> {

    private static final String TAG = "ObjectBoxService";

    @Override
    public String insertingResult(int rows) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        long start = System.currentTimeMillis();
        for(int i = 0; i < rows; i++){
            figureBox.put(generateEntity(0));
        }
        return ResultString.getResult(start, System.currentTimeMillis());
    }

    @Override
    public Observable<String> reactiveInsertingResult(int rows) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            for(int i = 0; i < rows; i++){
                figureBox.put(generateEntity(0));
            }
            return ResultString.getResult(start, System.currentTimeMillis());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingAllResult() {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        long start = System.currentTimeMillis();
        List<Figure> figures = figureBox.getAll();
        long end = System.currentTimeMillis();
        Log.i(TAG, "Найдено: " + figures.size());

        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingAllResult() {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            List<Figure> figures = figureBox.getAll();
            long end = System.currentTimeMillis();
            Log.i(TAG, "Найдено: " + figures.size());
            return ResultString.getResult(start, end);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingByIdResult(int id) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        long start = System.currentTimeMillis();
        Figure figure = figureBox.get(id);
        long end = System.currentTimeMillis();
        Log.i(TAG, figure.getId() + " "
                + figure.getName() + " "
                + figure.getArea());
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingByIdResult(int id) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            Figure figure = figureBox.get(id);
            long end = System.currentTimeMillis();
            Log.i(TAG, figure.getId() + " "
                    + figure.getName() + " "
                    + figure.getArea());
            return ResultString.getResult(start, end);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Figure generateEntity(long id) {
        Figure square = new Figure();
        square.setName("Квадрат");
        square.setArea(100);
        square.setColor("Green");

        return square;
    }

}
