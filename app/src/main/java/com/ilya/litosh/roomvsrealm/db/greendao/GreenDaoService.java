package com.ilya.litosh.roomvsrealm.db.greendao;

import android.util.Log;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.greendao.models.Fruit;
import com.ilya.litosh.roomvsrealm.db.greendao.models.FruitDao;
import com.ilya.litosh.roomvsrealm.models.DbBaseModel;
import com.ilya.litosh.roomvsrealm.models.IAutoIncrement;
import com.ilya.litosh.roomvsrealm.models.IEntityGenerator;
import com.ilya.litosh.roomvsrealm.models.ResultString;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class GreenDaoService implements DbBaseModel, IEntityGenerator<Fruit>, IAutoIncrement {

    private static final String TAG = "GreenDaoService" ;

    @Override
    public String insertingRes(int rows) {
        long start = System.currentTimeMillis();
        // TODO: check changes
        long id = getId();
        for(long i = id; i < rows + id; i++){
            App.getDaoWritingSession()
                    .getFruitDao()
                    .insert(generateEntity(i));
        }
        return ResultString.getResult(start, System.currentTimeMillis());
    }

    @Override
    public Observable<String> reactiveInsertingRes(int rows) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            long id = getId();
            for(long i = id; i < rows + id; i++){
                App.getDaoWritingSession()
                        .getFruitDao()
                        .insert(generateEntity(i));
            }
            return ResultString.getResult(start, System.currentTimeMillis());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingAllRes() {
        long start = System.currentTimeMillis();
        List<Fruit> fruits = App.getDaoReadingSession()
                .getFruitDao()
                .loadAll();
        long end = System.currentTimeMillis();
        Log.i(TAG, "Найдено: " + fruits.size());
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingAllRes() {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            List<Fruit> fruits = App.getDaoReadingSession()
                            .getFruitDao()
                            .loadAll();
            long end = System.currentTimeMillis();
            Log.i(TAG, "Найдено: " + fruits.size());
            return ResultString.getResult(start, end);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingByIdRes(int id) {
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
        Log.i(TAG, fruit.getId() + " "
                + fruit.getName() + " "
                + fruit.getColor() + " "
                + fruit.getWeight());
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingByIdRes(int id) {
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
            Log.i(TAG, fruit.getId() + " "
                    + fruit.getName() + " "
                    + fruit.getColor() + " "
                    + fruit.getWeight());
            return ResultString.getResult(start, end);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Fruit generateEntity(long id) {
        Fruit apple = new Fruit();
        apple.setId(id);
        apple.setName("Яблоко");
        apple.setColor("Красный");
        apple.setWeight(100);

        return apple;
    }

    @Override
    public long getId() {
        try{
            return App.getDaoReadingSession()
                    .getFruitDao()
                    .queryBuilder()
                    .offset((int)App.getDaoReadingSession()
                            .getFruitDao().count() - 1)
                    .limit(1)
                    .list()
                    .get(0)
                    .getId() + 1;
        }catch (IndexOutOfBoundsException e){
            return 0L;
        }
    }

}
