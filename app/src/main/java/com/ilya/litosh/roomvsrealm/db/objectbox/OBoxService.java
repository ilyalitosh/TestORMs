package com.ilya.litosh.roomvsrealm.db.objectbox;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.models.DBBaseModel;
import com.ilya.litosh.roomvsrealm.db.objectbox.models.Figure;

import java.util.List;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ilya_ on 22.04.2018.
 */

public class OBoxService implements DBBaseModel {

    @Override
    public String insert(int rows) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        long start = System.currentTimeMillis();
        for(int i = 0; i < rows; i++){
            Figure square = new Figure();
            square.setName("Квадрат");
            square.setArea(100);
            square.setColor("Green");
            figureBox.put(square);
        }
        return String.valueOf((System.currentTimeMillis() + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> insertRx(int rows) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            for(int i = 0; i < rows; i++){
                Figure square = new Figure();
                square.setName("Квадрат");
                square.setArea(100);
                square.setColor("Green");
                figureBox.put(square);
            }
            return String.valueOf((System.currentTimeMillis() + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String getAll() {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        long start = System.currentTimeMillis();
        List<Figure> figures = figureBox.getAll();
        long end = System.currentTimeMillis();
        System.out.println("Найдено: " + figures.size());
        return String.valueOf((end + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> getAllRx() {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            List<Figure> figures = figureBox.getAll();
            long end = System.currentTimeMillis();
            System.out.println("Найдено: " + figures.size());
            return String.valueOf((end + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String getById(int id) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        long start = System.currentTimeMillis();
        Figure figure = figureBox.get(id);
        long end = System.currentTimeMillis();
        System.out.println(figure.getId() + " "
                + figure.getName() + " "
                + figure.getArea());
        return String.valueOf((end + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> getByIdRx(int id) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            Figure figure = figureBox.get(id);
            long end = System.currentTimeMillis();
            System.out.println(figure.getId() + " "
                    + figure.getName() + " "
                    + figure.getArea());
            return String.valueOf((end + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
