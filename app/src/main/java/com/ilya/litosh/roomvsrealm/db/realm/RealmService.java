package com.ilya.litosh.roomvsrealm.db.realm;

import com.ilya.litosh.roomvsrealm.db.realm.migrations.DBMigration;
import com.ilya.litosh.roomvsrealm.db.realm.models.Car;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmService implements IRealmService{
    RealmConfiguration config = new RealmConfiguration.Builder()
            .schemaVersion(1)
            .migration(new DBMigration())
            .build();

    @Override
    public void addCars(int rows) {
        Realm realm = Realm.getInstance(config);
        List<Car> cars = new /*CopyOnWrite*/ArrayList<>();
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
        /*Car car = new Car();
        car.setColor("Green");
        car.setFuelCapacity(113);
        car.setPrice(666);
        car.setId(id + rows);
        cars.add(car);*/

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
        return Observable.just(Car.class)
                .flatMap((Function<Class<Car>, ObservableSource<List<Car>>>) carClass ->
                        Observable.just(realm.where(Car.class).findAll()))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Car> getCarById(long id) {
        Realm realm = Realm.getInstance(config);

        return Observable.just(Car.class)
                .flatMap(new Function<Class<Car>, ObservableSource<Car>>() {
                    @Override
                    public ObservableSource<Car> apply(Class<Car> carClass) throws Exception {
                        try{
                            return Observable.just(realm.where(Car.class)
                                    .equalTo("id", id)
                                    .findFirst());
                        }catch (Exception e){
                            e.printStackTrace();
                            return null;
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
