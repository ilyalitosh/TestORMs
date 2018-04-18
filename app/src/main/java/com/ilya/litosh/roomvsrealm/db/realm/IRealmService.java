package com.ilya.litosh.roomvsrealm.db.realm;

import com.ilya.litosh.roomvsrealm.db.realm.models.Car;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.realm.RealmObject;

public interface IRealmService {

    void addCar(Car car);

    List<Car> getAllCars();

    //RxAndroid

    Observable<List<Car>> getAllCarsRx();

}
