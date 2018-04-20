package com.ilya.litosh.roomvsrealm.presenters;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ilya.litosh.roomvsrealm.db.greendao.GreenDAOService;
import com.ilya.litosh.roomvsrealm.db.greendao.models.Fruit;
import com.ilya.litosh.roomvsrealm.db.realm.RealmService;
import com.ilya.litosh.roomvsrealm.db.realm.models.Car;
import com.ilya.litosh.roomvsrealm.db.room.RoomService;
import com.ilya.litosh.roomvsrealm.db.room.models.Phone;
import com.ilya.litosh.roomvsrealm.models.CRUDType;
import com.ilya.litosh.roomvsrealm.views.DBResultView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

@InjectViewState
public class DBResultPresenter extends MvpPresenter<DBResultView> implements IResultShow{

    public DBResultPresenter(){

    }

    @Override
    public void showRoomResult(Context context, int type, int rows) {
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
                                getViewState().showResult(s.toString());
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
                    long start, end;
                    double result;
                    start = System.currentTimeMillis();
                    for(int i = 0; i < rows; i++){
                        db.getPhoneDAO().addPhone(samsung);
                    }
                    end = System.currentTimeMillis();
                    result = (end + .0 - start) / 1000;
                    StringBuilder s = new StringBuilder();
                    s.append(result).append(" сек.");
                    getViewState().showResult(s.toString());
                    return new Object();
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
                break;
        }
    }

    @Override
    public void showRealmResult(Context context, int type, int rows) {
        RealmService realmService = new RealmService();
        switch (type){
            case CRUDType.READ:
                long start1, end1;
                double result1;
                start1 = System.currentTimeMillis();
                List<Car> cars = realmService.getAllCars();
                end1 = System.currentTimeMillis();
                result1 = (end1 + .0 - start1)/1000;
                Observable.just(cars)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Car>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<Car> cars) {
                                StringBuilder s1 = new StringBuilder();
                                s1.append(result1).append(" сек.");
                                getViewState().showResult(s1.toString());
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
                AtomicLong start = new AtomicLong();
                AtomicLong end = new AtomicLong();
                AtomicReference<Double> result = new AtomicReference<>((double) 0);
                Observable.fromCallable(() -> {
                    realmService.addCars(rows);
                    return new Object();
                }).doOnSubscribe(disposable -> {
                    start.set(System.currentTimeMillis());
                }).doOnComplete(() -> {
                    end.set(System.currentTimeMillis());
                    result.set((end.get() + .0 - start.get())/1000);
                    StringBuilder s = new StringBuilder();
                    s.append(result.get()).append(" сек.");
                    getViewState().showResult(s.toString());
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();

                break;
        }
    }

    @Override
    public void showGreenDAOResult(Context context, int type) {
        GreenDAOService dbService = new GreenDAOService();
        switch (type){
            case CRUDType.READ:
                dbService.getFruitsRx()
                        .subscribe(new Observer<List<Fruit>>() {
                            private long start, end;
                            private double result;
                            @Override
                            public void onSubscribe(Disposable d) {
                                start = System.currentTimeMillis();
                            }

                            @Override
                            public void onNext(List<Fruit> fruits) {
                                end = System.currentTimeMillis();
                                result = (end + .0 - start)/1000;
                                StringBuilder s = new StringBuilder();
                                s.append(result).append(" сек.");
                                getViewState().showResult(s.toString());
                                System.out.println(fruits.size() + " -----");
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
                AtomicLong start = new AtomicLong();
                AtomicLong end = new AtomicLong();
                AtomicReference<Double> result = new AtomicReference<>((double) 0);
                Observable.fromCallable(() -> {
                    for(int i = 0; i < 1000; i++){
                        Fruit apple = new Fruit();
                        apple.setName("Апельсин");
                        apple.setColor("Orange");
                        apple.setWeight(150);
                        dbService.addFruit(apple);
                    }
                    return new Object();
                }).doOnSubscribe(disposable -> {
                    start.set(System.currentTimeMillis());
                }).doOnComplete(() -> {
                    end.set(System.currentTimeMillis());
                    result.set((end.get() + .0 - start.get())/1000);
                    StringBuilder s = new StringBuilder();
                    s.append(result.get()).append(" сек.");
                    getViewState().showResult(s.toString());
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
                break;
        }
    }
}
