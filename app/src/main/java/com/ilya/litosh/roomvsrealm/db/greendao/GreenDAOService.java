package com.ilya.litosh.roomvsrealm.db.greendao;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.greendao.models.Fruit;
import com.ilya.litosh.roomvsrealm.db.greendao.models.FruitDao;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GreenDAOService implements IGreenDAOService {
    @Override
    public void addFruit(Fruit fruit) {
        long id;
        try{
            id = App.getDaoReadingSession()
                    .getFruitDao()
                    .queryBuilder()
                    .offset((int)App.getDaoReadingSession()
                            .getFruitDao().count() - 1)
                    .limit(1)
                    .list()
                    .get(0)
                    .getId() + 1;
        }catch (IndexOutOfBoundsException e){
            id = 0L;
        }
        fruit.setId(id);
        App.getDaoWritingSession()
                .getFruitDao()
                .insert(fruit);
    }

    @Override
    public List<Fruit> getFruits() {
        return App.getDaoReadingSession()
                .getFruitDao()
                .loadAll();
    }

    @Override
    public Observable<List<Fruit>> getFruitsRx() {
        FruitDao fruitDao = App.getDaoReadingSession()
                .getFruitDao();
        return Observable.just(fruitDao.loadAll())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
