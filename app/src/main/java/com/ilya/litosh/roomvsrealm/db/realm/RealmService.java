package com.ilya.litosh.roomvsrealm.db.realm;

import android.util.Log;

import com.ilya.litosh.roomvsrealm.db.realm.migrations.DbMigration;
import com.ilya.litosh.roomvsrealm.db.realm.models.Car;
import com.ilya.litosh.roomvsrealm.models.DbBaseModel;
import com.ilya.litosh.roomvsrealm.models.IAutoIncrement;
import com.ilya.litosh.roomvsrealm.models.IEntityGenerator;
import com.ilya.litosh.roomvsrealm.models.ResultString;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import java.util.List;

public class RealmService implements DbBaseModel, IEntityGenerator<Car>, IAutoIncrement{

    private RealmConfiguration config = new RealmConfiguration.Builder()
            .schemaVersion(1)
            .migration(new DbMigration())
            .build();
    private static final String TAG = "RealmService";

    @Override
    public String insertingRes(int rows) {
        Realm realm = Realm.getInstance(config);
        long start = System.currentTimeMillis();
        realm.beginTransaction();
        long id = getId();
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
            realm.beginTransaction();
            long start = System.currentTimeMillis();
            // TODO: check changes
            long id = getId();
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
        Log.i(TAG, "Надено: " + cars.size());
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
            Log.i(TAG, "Надено: " + cars.size());
            return ResultString.getResult(start, end);
        }).subscribeOn(Schedulers.io())
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
        Log.i(TAG, car.getId() + " "
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
            Log.i(TAG,car.getId() + " "
                    + car.getColor() + " "
                    + car.getPrice());
            return ResultString.getResult(start, end);
        }).subscribeOn(Schedulers.io())
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

    @Override
    public long getId() {
        try {
            Realm realm = Realm.getInstance(config);
            return realm.where(Car.class).max("id").intValue() + 1;
        } catch (Exception e){
            return 0L;
        }
    }
}
