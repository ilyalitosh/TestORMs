package com.ilya.litosh.roomvsrealm.db.objectbox;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.models.DBBaseModel;
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

public class OBoxService implements DBBaseModel, IEntityGenerator<Figure> {

    @Override
    public String insertingRes(int rows) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        long start = System.currentTimeMillis();
        for(int i = 0; i < rows; i++){
            figureBox.put(generateEntity(0));
        }

        return ResultString.getResult(start, System.currentTimeMillis());
    }

    @Override
    public Observable<String> reactiveInsertingRes(int rows) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);

        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            for(int i = 0; i < rows; i++){
                figureBox.put(generateEntity(0));
            }
            return ResultString.getResult(start, System.currentTimeMillis());
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingAllRes() {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        long start = System.currentTimeMillis();
        List<Figure> figures = figureBox.getAll();
        long end = System.currentTimeMillis();
        //System.out.println("Найдено: " + figures.size());

        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingAllRes() {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            List<Figure> figures = figureBox.getAll();
            long end = System.currentTimeMillis();
            System.out.println("Найдено: " + figures.size());
            return ResultString.getResult(start, end);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingByIdRes(int id) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        long start = System.currentTimeMillis();
        Figure figure = figureBox.get(id);
        long end = System.currentTimeMillis();
        System.out.println(figure.getId() + " "
                + figure.getName() + " "
                + figure.getArea());
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingByIdRes(int id) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            Figure figure = figureBox.get(id);
            long end = System.currentTimeMillis();
            System.out.println(figure.getId() + " "
                    + figure.getName() + " "
                    + figure.getArea());
            return ResultString.getResult(start, end);
        })
                .subscribeOn(Schedulers.io())
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
