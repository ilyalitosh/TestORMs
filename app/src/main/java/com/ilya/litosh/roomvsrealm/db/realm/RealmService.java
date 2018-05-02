package com.ilya.litosh.roomvsrealm.db.realm;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.objectbox.models.Figure;
import com.ilya.litosh.roomvsrealm.db.realm.migrations.DBMigration;
import com.ilya.litosh.roomvsrealm.db.realm.models.Car;
import com.ilya.litosh.roomvsrealm.models.DBBaseModel;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmService implements DBBaseModel{
    private RealmConfiguration config = new RealmConfiguration.Builder()
            .schemaVersion(1)
            .migration(new DBMigration())
            .build();

    @Override
    public String insert(int rows) {
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
            Car car = new Car();
            car.setColor("Black");
            car.setFuelCapacity(113);
            car.setPrice(666);
            car.setId(i);
            realm.insert(car);
        }
        realm.commitTransaction();
        realm.close();
        return String.valueOf((System.currentTimeMillis() + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> insertRx(int rows) {
        return Observable.fromCallable(() -> {
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
                Car car = new Car();
                car.setColor("Black");
                car.setFuelCapacity(113);
                car.setPrice(666);
                car.setId(i);
                realm.insert(car);
            }
            realm.commitTransaction();
            realm.close();
            return String.valueOf((System.currentTimeMillis() + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String getAll() {
        Realm realm = Realm.getInstance(config);
        long start = System.currentTimeMillis();
        List<Car> cars = realm.where(Car.class).findAll();
        long end = System.currentTimeMillis();
        System.out.println(cars.size());
        return String.valueOf((end + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> getAllRx() {
        return Observable.fromCallable(() -> {
            Realm realm = Realm.getInstance(config);
            long start = System.currentTimeMillis();
            realm.beginTransaction();
            List<Car> cars = realm.where(Car.class).findAll();
            realm.commitTransaction();
            long end = System.currentTimeMillis();
            System.out.println("Надено: " + cars.size());
            return String.valueOf((end + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String getById(int id) {
        Realm realm = Realm.getInstance(config);
        long start = System.currentTimeMillis();
        Car car = realm.where(Car.class)
                .equalTo("id", id)
                .findFirst();
        long end = System.currentTimeMillis();
        System.out.println(car.getId() + " "
                + car.getColor() + " "
                + car.getPrice());
        return String.valueOf((end + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> getByIdRx(int id) {
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
            return String.valueOf((end + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
