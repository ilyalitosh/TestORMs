package com.ilya.litosh.roomvsrealm.db.room;

import android.util.Log;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.room.models.Phone;
import com.ilya.litosh.roomvsrealm.models.DbBaseModel;
import com.ilya.litosh.roomvsrealm.models.IEntityGenerator;
import com.ilya.litosh.roomvsrealm.models.ResultString;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class RoomService implements DbBaseModel, IEntityGenerator<Phone> {

    private static final String TAG = "RoomService";

    @Override
    public String insertingResult(int rows) {
        long start = System.currentTimeMillis();
        for(int i = 0; i < rows; i++){
            /* TODO: use for bulk insert
            phones.add(samsung);*/
            App.getRoomDBSession().getPhoneDAO().addPhone(generateEntity(0));
        }

        return ResultString.getResult(start, System.currentTimeMillis());
    }

    @Override
    public Observable<String> reactiveInsertingResult(int rows) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            for(int i = 0; i < rows; i++){
                /* TODO: use for bulk insert
                phones.add(samsung);*/
                App.getRoomDBSession().getPhoneDAO().addPhone(generateEntity(0));
            }
            return ResultString.getResult(start, System.currentTimeMillis());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingAllResult() {
        long start = System.currentTimeMillis();
        List<Phone> phones = App.getRoomDBSession().getPhoneDAO().getAllPhones();
        long end = System.currentTimeMillis();
        Log.i(TAG, "Надено: " + phones.size());
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingAllResult() {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            List<Phone> phones = App.getRoomDBSession().getPhoneDAO().getAllPhones();
            long end = System.currentTimeMillis();
            Log.i(TAG, "Надено: " + phones.size());
            return ResultString.getResult(start, end);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingByIdResult(int id) {
        long start = System.currentTimeMillis();
        Phone phone = App.getRoomDBSession().getPhoneDAO().getPhoneById(id);
        long end = System.currentTimeMillis();
        Log.i(TAG, phone.getId() + " "
                + phone.getName() + " "
                + phone.getModel());
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingByIdResult(int id) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            Phone phone = App.getRoomDBSession().getPhoneDAO().getPhoneById(id);
            long end = System.currentTimeMillis();
            Log.i(TAG, phone.getId() + " "
                    + phone.getName() + " "
                    + phone.getModel());
            return ResultString.getResult(start, end);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Phone generateEntity(long id) {
        Phone samsung = new Phone();
        samsung.setName("Samsung");
        samsung.setModel("i9150");
        samsung.setPrice(100);

        return samsung;
    }
}