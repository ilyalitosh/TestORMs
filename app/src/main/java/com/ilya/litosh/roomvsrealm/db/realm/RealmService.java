package com.ilya.litosh.roomvsrealm.db.realm;

import com.ilya.litosh.roomvsrealm.db.realm.migrations.DBMigration;
import com.ilya.litosh.roomvsrealm.db.realm.models.Car;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmService implements IRealmService{
    RealmConfiguration config = new RealmConfiguration.Builder()
            .schemaVersion(1)
            .migration(new DBMigration())
            .build();

    @Override
    public void addCars(int rows) {
        Realm realm = Realm.getInstance(config);
        List<Car> cars = new ArrayList<>();
        long id;
        try{
            id = realm.where(Car.class).max("id").intValue() + 1;
        }catch (Exception e){
            id = 0L;
        }
        //realm.beginTransaction();
        for(long i = id; i < id + rows; i++){
            Car car = new Car();
            car.setColor("Black");
            car.setFuelCapacity(113);
            car.setPrice(666);
            car.setId(i);
            cars.add(car);
            //realm.insert(car);
        }
        realm.beginTransaction();
        realm.insert(cars);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public List<Car> getAllCars() {
        Realm realm = Realm.getInstance(config);
        realm.beginTransaction();
        List<Car> carList = new ArrayList<>(realm.where(Car.class).findAll());
        realm.commitTransaction();
        realm.close();
        return carList;
    }

    @Override
    public Observable<List<Car>> getAllCarsRx() {
        Realm realm = Realm.getInstance(config);
        return Observable.just(realm.where(Car.class).findAll())
                .flatMap(new Function<RealmResults<Car>, ObservableSource<List<Car>>>() {
                    @Override
                    public ObservableSource<List<Car>> apply(RealmResults<Car> cars) throws Exception {
                        return Observable.just(cars);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
