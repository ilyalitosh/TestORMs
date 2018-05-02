package com.ilya.litosh.roomvsrealm.db.greendao;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.greendao.models.Fruit;
import com.ilya.litosh.roomvsrealm.db.greendao.models.FruitDao;
import com.ilya.litosh.roomvsrealm.db.objectbox.models.Figure;
import com.ilya.litosh.roomvsrealm.models.DBBaseModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GreenDAOService implements DBBaseModel {
    @Override
    public String insert(int rows) {
        long start = System.currentTimeMillis();
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
        for(long i = id; i < rows + id; i++){
            Fruit apple = new Fruit();
            apple.setId(i);
            apple.setName("Яблоко");
            apple.setColor("Красный");
            apple.setWeight(100);
            App.getDaoWritingSession()
                    .getFruitDao()
                    .insert(apple);
        }
        return String.valueOf((System.currentTimeMillis() + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> insertRx(int rows) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
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
            for(long i = id; i < rows + id; i++){
                Fruit apple = new Fruit();
                apple.setId(i);
                apple.setName("Яблоко");
                apple.setColor("Красный");
                apple.setWeight(100);
                App.getDaoWritingSession()
                        .getFruitDao()
                        .insert(apple);
            }
            return String.valueOf((System.currentTimeMillis() + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String getAll() {
        long start = System.currentTimeMillis();
        List<Fruit> fruits = App.getDaoReadingSession()
                .getFruitDao()
                .loadAll();
        long end = System.currentTimeMillis();
        System.out.println("Найдено: " + fruits.size());
        return String.valueOf((end + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> getAllRx() {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            List<Fruit> fruits = App.getDaoReadingSession()
                    .getFruitDao()
                    .loadAll();
            long end = System.currentTimeMillis();
            System.out.println("Найдено: " + fruits.size());
            return String.valueOf((end + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String getById(int id) {
        long start = System.currentTimeMillis();
        Fruit fruit = App.getDaoReadingSession()
                .getFruitDao()
                .queryBuilder()
                .where(FruitDao
                        .Properties
                        .Id
                        .eq(id))
                .list()
                .get(0);
        long end = System.currentTimeMillis();
        System.out.println(fruit.getId() + " "
                + fruit.getName() + " "
                + fruit.getColor() + " "
                + fruit.getWeight());
        return String.valueOf((end + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> getByIdRx(int id) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            Fruit fruit = App.getDaoReadingSession()
                    .getFruitDao()
                    .queryBuilder()
                    .where(FruitDao
                            .Properties
                            .Id
                            .eq(id))
                    .list()
                    .get(0);
            long end = System.currentTimeMillis();
            System.out.println(fruit.getId() + " "
                    + fruit.getName() + " "
                    + fruit.getColor() + " "
                    + fruit.getWeight());
            return String.valueOf((end + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
