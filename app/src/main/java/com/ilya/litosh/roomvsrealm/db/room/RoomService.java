package com.ilya.litosh.roomvsrealm.db.room;

import android.arch.persistence.room.Room;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.room.models.Phone;
import com.ilya.litosh.roomvsrealm.models.DBBaseModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomService implements DBBaseModel {

    @Override
    public String insert(int rows) {
        long start = System.currentTimeMillis();
        for(int i = 0; i < rows; i++){
            Phone samsung = new Phone();
            samsung.setName("Samsung");
            samsung.setModel("i9150");
            //phones.add(samsung);
            App.getRoomDBSession().getPhoneDAO().addPhone(samsung);
        }
        return String.valueOf((System.currentTimeMillis() + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> insertRx(int rows) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            for(int i = 0; i < rows; i++){
                Phone samsung = new Phone();
                samsung.setName("Samsung");
                samsung.setModel("i9150");
                //phones.add(samsung);
                App.getRoomDBSession().getPhoneDAO().addPhone(samsung);
            }
            return String.valueOf((System.currentTimeMillis() + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String getAll() {
        long start = System.currentTimeMillis();
        List<Phone> phones = App.getRoomDBSession().getPhoneDAO().getAllPhones();
        long end = System.currentTimeMillis();
        System.out.println("Надено: " + phones.size());
        return String.valueOf((end + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> getAllRx() {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            List<Phone> phones = App.getRoomDBSession().getPhoneDAO().getAllPhones();
            long end = System.currentTimeMillis();
            System.out.println("Надено: " + phones.size());
            return String.valueOf((end + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String getById(int id) {
        long start = System.currentTimeMillis();
        Phone phone = App.getRoomDBSession().getPhoneDAO().getPhoneById(id);
        long end = System.currentTimeMillis();
        System.out.println(phone.getId() + " "
                + phone.getName() + " "
                + phone.getModel());
        return String.valueOf((end + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> getByIdRx(int id) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            Phone phone = App.getRoomDBSession().getPhoneDAO().getPhoneById(id);
            long end = System.currentTimeMillis();
            System.out.println(phone.getId() + " "
                    + phone.getName() + " "
                    + phone.getModel());
            return String.valueOf((end + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}