package com.ilya.litosh.roomvsrealm.db.greendao;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.greendao.models.Fruit;
import com.ilya.litosh.roomvsrealm.db.greendao.models.FruitDao;
import com.ilya.litosh.roomvsrealm.models.DBBaseModel;
import com.ilya.litosh.roomvsrealm.models.IEntityGenerator;
import com.ilya.litosh.roomvsrealm.models.ResultString;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GreenDAOService implements DBBaseModel, IEntityGenerator<Fruit> {

    @Override
    public String insertingRes(int rows) {
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
                App.getDaoWritingSession()
                        .getFruitDao()
                        .insert(generateEntity(i));
            }
            return ResultString.getResult(start, System.currentTimeMillis());
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingAllRes() {
        long start = System.currentTimeMillis();
        List<Fruit> fruits = App.getDaoReadingSession()
                .getFruitDao()
                .loadAll();
        long end = System.currentTimeMillis();
        System.out.println("Найдено: " + fruits.size());
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
            System.out.println("Найдено: " + fruits.size());
            return ResultString.getResult(start, end);
        })
                .subscribeOn(Schedulers.io())
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
        System.out.println(fruit.getId() + " "
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
            /*System.out.println(fruit.getId() + " "
                    + fruit.getName() + " "
                    + fruit.getColor() + " "
                    + fruit.getWeight());*/
            return ResultString.getResult(start, end);
        })
                .subscribeOn(Schedulers.io())
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
}
