package com.ilya.litosh.roomvsrealm.db.realm;

import com.ilya.litosh.roomvsrealm.db.realm.migrations.DBMigration;
import com.ilya.litosh.roomvsrealm.db.realm.models.Car;
import com.ilya.litosh.roomvsrealm.models.DBBaseModel;
import com.ilya.litosh.roomvsrealm.models.IEntityGenerator;
import com.ilya.litosh.roomvsrealm.models.ResultString;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmService implements DBBaseModel, IEntityGenerator<Car>{
    private RealmConfiguration config = new RealmConfiguration.Builder()
            .schemaVersion(1)
            .migration(new DBMigration())
            .build();

    @Override
    public String insertingRes(int rows) {
        Realm realm = Realm.getInstance(config);
        long start = System.currentTimeMillis();
        long id;

        try{
            id = realm.where(Car.class).max("id").intValue() + 1;
        }catch (Exception e){
            id = 0L;
        }

        realm.beginTransaction();
        for(long i = id; i < id + rows; i++){
            realm.insert(generateEntity(i));
        }
        realm.commitTransaction();
        realm.close();

        return ResultString.getResult(start, System.currentTimeMillis());
    }


    @Override
    public Observable<String> reactiveInsertingRes(int rows) {
        return Observable.fromCallable(() -> {
            Realm realm = Realm.getInstance(config);
            long start = System.currentTimeMillis();
            long id;
            try {
                id = realm.where(Car.class).max("id").intValue() + 1;
            } catch (Exception e){
                id = 0L;
            }
            realm.beginTransaction();
            for(long i = id; i < id + rows; i++){
                realm.insert(generateEntity(i));
            }
            realm.commitTransaction();
            realm.close();
            return ResultString.getResult(start, System.currentTimeMillis());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    @Override
    public String readingAllRes() {
        Realm realm = Realm.getInstance(config);
        long start = System.currentTimeMillis();
        List<Car> cars = realm.where(Car.class).findAll();
        long end = System.currentTimeMillis();
        System.out.println(cars.size());
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingAllRes() {
        return Observable.fromCallable(() -> {
            Realm realm = Realm.getInstance(config);
            long start = System.currentTimeMillis();

            realm.beginTransaction();
            List<Car> cars = realm.where(Car.class).findAll();
            realm.commitTransaction();

            long end = System.currentTimeMillis();
            System.out.println("Надено: " + cars.size());
            return ResultString.getResult(start, end);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingByIdRes(int id) {
        Realm realm = Realm.getInstance(config);
        long start = System.currentTimeMillis();
        Car car = realm.where(Car.class)
                .equalTo("id", id)
                .findFirst();
        long end = System.currentTimeMillis();
        System.out.println(car.getId() + " "
                + car.getColor() + " "
                + car.getPrice());
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingByIdRes(int id) {
        return Observable.fromCallable(() -> {
            Realm realm = Realm.getInstance(config);
            long start = System.currentTimeMillis();
            Car car = realm.where(Car.class)
                    .equalTo("id", id)
                    .findFirst();
            long end = System.currentTimeMillis();
            System.out.println(car.getId() + " "
                    + car.getColor() + " "
                    + car.getPrice());
            return ResultString.getResult(start, end);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Car generateEntity(long id) {
        Car car = new Car();
        car.setColor("Black");
        car.setFuelCapacity(113);
        car.setPrice(666);
        car.setId(id);

        return car;
    }
}
