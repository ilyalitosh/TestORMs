package com.ilya.litosh.roomvsrealm.db.objectbox;

import com.ilya.litosh.roomvsrealm.db.objectbox.models.Figure;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ilya_ on 22.04.2018.
 */

public interface IOBoxService {

    void addFigure(Figure figure);

    void addFigures(List<Figure> figures);

    List<Figure> getAllFigures();

    //RxAndroid
    Observable<List<Figure>> getAllFiguresRx();

    Observable<Figure> addFigureRx(Figure figure);

    Observable<List<Figure>> addFiguresRx(List<Figure> figures);

}
