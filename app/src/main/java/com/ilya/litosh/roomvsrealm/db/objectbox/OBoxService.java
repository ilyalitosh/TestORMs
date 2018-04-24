package com.ilya.litosh.roomvsrealm.db.objectbox;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.objectbox.models.Figure;

import java.util.List;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ilya_ on 22.04.2018.
 */

public class OBoxService implements IOBoxService {

    @Override
    public void addFigure(Figure figure) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        figureBox.put(figure);
    }

    @Override
    public void addFigures(List<Figure> figures) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);
        figureBox.put(figures);
    }

    @Override
    public List<Figure> getAllFigures() {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);

        return figureBox.getAll();
    }

    @Override
    public Observable<List<Figure>> getAllFiguresRx() {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);

        return Observable.just(Figure.class)
                .flatMap(figureClass -> {
                    return Observable.just(figureClass)
                            .map(figureClass1 -> {
                                return figureBox.getAll();
                            });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Figure> addFigureRx(Figure figure) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);

        return Observable.just(figure)
                    .doOnNext(figure2 -> {
                        figureBox.put(figure);
                    });
    }

    @Override
    public Observable<List<Figure>> addFiguresRx(List<Figure> figures) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);

        return Observable.just(figures)
                .doOnNext(figure2 -> {
                    figureBox.put(figures);
                });
    }

    @Override
    public Observable<Figure> getFigureById(long id) {
        Box<Figure> figureBox = App.getOBoxSession().boxFor(Figure.class);

        return Observable.just(Figure.class)
                .flatMap(figureClass -> {
                    return Observable.just(figureClass)
                            .map(figureClass1 -> {
                                return figureBox.get(id);
                            });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
