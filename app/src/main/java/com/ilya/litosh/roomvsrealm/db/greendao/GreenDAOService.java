package com.ilya.litosh.roomvsrealm.db.greendao;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.greendao.models.Fruit;
import com.ilya.litosh.roomvsrealm.db.greendao.models.FruitDao;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GreenDAOService implements IGreenDAOService {
    @Override
    public void addFruits(int rows) {
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
        List<Fruit> fruits = new ArrayList<>();
        for(long i = id; i < rows + id; i++){
            Fruit apple = new Fruit();
            apple.setName("Яблоко");
            apple.setColor("Orange");
            apple.setWeight(150);
            apple.setId(i);
            fruits.add(apple);
            /*App.getDaoWritingSession()
                    .getFruitDao()
                    .insert(apple);*/
        }
        App.getDaoWritingSession()
                .getFruitDao()
                .insertInTx(fruits);
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
        /*return Observable.just(fruitDao.loadAll())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());*/
        return Observable.just(Fruit.class)
                .flatMap(fruitClass -> {
                    return Observable.just(fruitClass)
                            .map(fruitClass1 -> {
                                return fruitDao.loadAll();
                            });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Fruit> getFruitByID(long id) {
        FruitDao fruitDao = App.getDaoReadingSession().getFruitDao();
        return Observable.just(Fruit.class)
                .flatMap(fruitClass -> {
                    return Observable.just(fruitClass)
                            .map(fruitClass1 -> {
                                return fruitDao
                                        .queryBuilder()
                                        .where(FruitDao
                                                .Properties
                                                .Id
                                                .eq(id))
                                        .list()
                                        .get(0);
                            });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
