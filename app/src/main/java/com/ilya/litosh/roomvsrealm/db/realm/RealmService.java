package com.ilya.litosh.roomvsrealm.db.realm;

import com.ilya.litosh.roomvsrealm.db.realm.migrations.DBMigration;
import com.ilya.litosh.roomvsrealm.db.realm.models.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
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
    public void addCar(Car car) {
        Realm realm = Realm.getInstance(config);
        long id;
        try{
            id = realm.where(Car.class).max("id").intValue() + 1;
        }catch (Exception e){
            id = 0L;
        }
        car.setId(id);
        realm.beginTransaction();
        realm.copyToRealm(car);
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
