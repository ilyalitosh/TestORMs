package com.ilya.litosh.roomvsrealm.db.greendao;

import com.ilya.litosh.roomvsrealm.db.greendao.models.Fruit;

import java.util.List;

import io.reactivex.Observable;

public interface IGreenDAOService {

    void addFruit(Fruit fruit);

    List<Fruit> getFruits();

    //RxAndroid
    Observable<List<Fruit>> getFruitsRx();

}
