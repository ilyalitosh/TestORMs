package com.ilya.litosh.roomvsrealm.db.realm;

import com.ilya.litosh.roomvsrealm.db.realm.migrations.DBMigration;
import com.ilya.litosh.roomvsrealm.db.realm.models.Car;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
}
