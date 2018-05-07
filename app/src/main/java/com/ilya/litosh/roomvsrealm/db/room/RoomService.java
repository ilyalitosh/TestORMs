package com.ilya.litosh.roomvsrealm.db.room;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.room.models.Phone;
import com.ilya.litosh.roomvsrealm.models.DBBaseModel;
import com.ilya.litosh.roomvsrealm.models.IEntityGenerator;
import com.ilya.litosh.roomvsrealm.models.ResultString;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomService implements DBBaseModel, IEntityGenerator<Phone> {

    @Override
    public String insertingRes(int rows) {
        long start = System.currentTimeMillis();
        for(int i = 0; i < rows; i++){
            //phones.add(samsung);
            App.getRoomDBSession().getPhoneDAO().addPhone(generateEntity(0));
        }

        return ResultString.getResult(start, System.currentTimeMillis());
    }

    @Override
    public Observable<String> reactiveInsertingRes(int rows) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            for(int i = 0; i < rows; i++){
                //phones.add(samsung);
                App.getRoomDBSession().getPhoneDAO().addPhone(generateEntity(0));
            }

            return ResultString.getResult(start, System.currentTimeMillis());
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingAllRes() {
        long start = System.currentTimeMillis();
        List<Phone> phones = App.getRoomDBSession().getPhoneDAO().getAllPhones();
        long end = System.currentTimeMillis();
        System.out.println("Надено: " + phones.size());
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingAllRes() {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            List<Phone> phones = App.getRoomDBSession().getPhoneDAO().getAllPhones();
            long end = System.currentTimeMillis();

            System.out.println("Надено: " + phones.size());
            return ResultString.getResult(start, end);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingByIdRes(int id) {
        long start = System.currentTimeMillis();
        Phone phone = App.getRoomDBSession().getPhoneDAO().getPhoneById(id);
        long end = System.currentTimeMillis();

        System.out.println(phone.getId() + " "
                + phone.getName() + " "
                + phone.getModel());

        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingByIdRes(int id) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            Phone phone = App.getRoomDBSession().getPhoneDAO().getPhoneById(id);
            long end = System.currentTimeMillis();

            System.out.println(phone.getId() + " "
                    + phone.getName() + " "
                    + phone.getModel());

            return ResultString.getResult(start, end);
        })
                .subscribeOn(Schedulers.io())
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