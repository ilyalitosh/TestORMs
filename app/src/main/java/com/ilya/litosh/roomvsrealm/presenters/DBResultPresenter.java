package com.ilya.litosh.roomvsrealm.presenters;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ilya.litosh.roomvsrealm.db.realm.RealmService;
import com.ilya.litosh.roomvsrealm.db.realm.models.Car;
import com.ilya.litosh.roomvsrealm.db.room.RoomService;
import com.ilya.litosh.roomvsrealm.db.room.models.Phone;
import com.ilya.litosh.roomvsrealm.models.CRUDType;
import com.ilya.litosh.roomvsrealm.views.DBResultView;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

@InjectViewState
public class DBResultPresenter extends MvpPresenter<DBResultView> implements IResultShow{

    public DBResultPresenter(){

    }

    @Override
    public void showRoomResult(Context context, int type) {
        final RoomService db = Room
                .databaseBuilder(
                        context,
                        RoomService.class,
                        "room-database")
                .build();
        switch (type){
            case CRUDType.READ:
                db.getPhoneDAO()
                        .getAllPhonesRx()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSubscriber<List<Phone>>() {
                            private long start, end;
                            private double result;
                            @Override
                            public void onNext(List<Phone> phones) {
                                end = System.currentTimeMillis();
                                result = (end + .0 - start)/1000;
                                StringBuilder s = new StringBuilder();
                                s.append(result).append(" сек.");
                                getViewState().showRoomResult(s.toString());
                            }

                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            protected void onStart() {
                                start = System.currentTimeMillis();
                                super.onStart();
                            }
                        });
                break;
            case CRUDType.CREATE:
                Phone samsung = new Phone();
                samsung.setName("Samsung");
                samsung.setModel("i9150");
                Observable.fromCallable(() -> {
                    db.getPhoneDAO().addPhone(samsung);
                    return new Object();
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
                break;
        }
    }

    @Override
    public void showRealmResult(Context context, int type) {
        RealmService realmService = new RealmService();
        switch (type){
            case CRUDType.READ:
                realmService.getAllCarsRx()
                        .subscribe(new Observer<List<Car>>() {
                            private long start, end;
                            private double result;
                            @Override
                            public void onSubscribe(Disposable d) {
                                start = System.currentTimeMillis();
                            }

                            @Override
                            public void onNext(List<Car> cars) {
                                end = System.currentTimeMillis();
                                result = (end + .0 - start)/1000;
                                StringBuilder s = new StringBuilder();
                                s.append(result).append(" сек.");
                                getViewState().showRealmResult(s.toString());
                                for(Car car : cars){
                                    System.out.println(car.getId() + " " + car.getColor() + " " + car.getPrice());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case CRUDType.CREATE:
                Car car = new Car();
                car.setColor("Black");
                car.setFuelCapacity(113);
                car.setPrice(666);
                AtomicLong start = new AtomicLong();
                AtomicLong end = new AtomicLong();
                AtomicReference<Double> result = new AtomicReference<>((double) 0);
                Observable.fromCallable(() -> {
                    realmService.addCar(car);
                    return new Object();
                }).doOnSubscribe(disposable -> {
                    start.set(System.currentTimeMillis());
                }).doOnComplete(() -> {
                    end.set(System.currentTimeMillis());
                    result.set((end.get() + .0 - start.get())/1000);
                    StringBuilder s = new StringBuilder();
                    s.append(result.get()).append(" сек.");
                    getViewState().showRealmResult(s.toString());
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();

                break;
        }
    }
}
