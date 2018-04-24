package com.ilya.litosh.roomvsrealm.presenters;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ilya.litosh.roomvsrealm.db.greendao.GreenDAOService;
import com.ilya.litosh.roomvsrealm.db.greendao.models.Fruit;
import com.ilya.litosh.roomvsrealm.db.objectbox.OBoxService;
import com.ilya.litosh.roomvsrealm.db.objectbox.models.Figure;
import com.ilya.litosh.roomvsrealm.db.realm.RealmService;
import com.ilya.litosh.roomvsrealm.db.realm.models.Car;
import com.ilya.litosh.roomvsrealm.db.room.RoomService;
import com.ilya.litosh.roomvsrealm.db.room.models.Phone;
import com.ilya.litosh.roomvsrealm.db.snappydb.SnappyDBService;
import com.ilya.litosh.roomvsrealm.db.snappydb.models.Book;
import com.ilya.litosh.roomvsrealm.models.CRUDType;
import com.ilya.litosh.roomvsrealm.views.DBResultView;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.FlowableSubscriber;
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
    public void showRoomResult(Context context, int type, int rows, long id) {
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
                                System.out.println(phones.size() + " элементов");
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
                Observable.fromCallable(() -> {
                    List<Phone> phones = new ArrayList<>();
                    long start, end;
                    double result;
                    start = System.currentTimeMillis();
                    for(int i = 0; i < rows; i++){
                        Phone samsung = new Phone();
                        samsung.setName("Samsung");
                        samsung.setModel("i9150");
                        phones.add(samsung);
                        //db.getPhoneDAO().addPhone(samsung);
                    }
                    db.getPhoneDAO().addPhones(phones);

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
            case CRUDType.READ_SEARCHING:
                db.getPhoneDAO().getPhoneById(id)
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
                                System.out.println("id:" + phones.get(0).getId() + " name:" + phones.get(0).getName());
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
        }
    }

    @Override
    public void showRealmResult(Context context, int type, int rows, long id) {
        RealmService realmService = new RealmService();
        switch (type){
            case CRUDType.READ:
                realmService.getAllCarsRx()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Car>>() {
                            long start1, end1;
                            double result1;
                            @Override
                            public void onSubscribe(Disposable d) {
                                start1 = System.currentTimeMillis();
                            }

                            @Override
                            public void onNext(List<Car> cars) {
                                end1 = System.currentTimeMillis();
                                result1 = (end1 + .0 - start1)/1000;

                                StringBuilder s1 = new StringBuilder();
                                s1.append(result1).append(" сек.");
                                getViewState().showResult(s1.toString());
                                System.out.println(cars.size() + " элементов");
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
            case CRUDType.READ_SEARCHING:
                realmService.getAllCarsRx()
                        .subscribe(new Observer<List<Car>>() {
                            long start, end;
                            double result;
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
                                getViewState().showResult(s.toString());
                                System.out.println(cars.size() + " ------- id:"
                                        + cars.get(10).getId() + " color:" + cars.get(10).getColor());
                                System.out.println("id:" + cars.get(43001).getId() + " color:" + cars.get(43001).getColor());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                /*realmService.getCarById(id)
                        .subscribe(new Observer<Car>() {
                            long start, end;
                            double result;
                            @Override
                            public void onSubscribe(Disposable d) {
                                start = System.currentTimeMillis();
                            }

                            @Override
                            public void onNext(Car car) {
                                end = System.currentTimeMillis();
                                result = (end + .0 - start)/1000;

                                StringBuilder s = new StringBuilder();
                                s.append(result).append(" сек.");
                                getViewState().showResult(s.toString());
                                System.out.println(" id:" + car.getId() + " color:" + car.getColor());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });*/
                break;
        }
    }

    @Override
    public void showGreenDAOResult(Context context, int type, int rows, long id) {
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
                    dbService.addFruits(rows);
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
            case CRUDType.READ_SEARCHING:
                AtomicLong start1 = new AtomicLong();
                AtomicLong end1 = new AtomicLong();
                AtomicReference<Double> result1 = new AtomicReference<>((double) 0);
                dbService.getFruitByID(id)
                        .subscribe(new Observer<Fruit>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                start1.set(System.currentTimeMillis());
                            }

                            @Override
                            public void onNext(Fruit fruit) {
                                /*System.out.println("Найден " + fruit.getId()
                                        + " " + fruit.getName());*/
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                end1.set(System.currentTimeMillis());
                                result1.set((end1.get() + .0 - start1.get())/1000);
                                StringBuilder s = new StringBuilder();
                                s.append(result1.get()).append(" сек.");
                                getViewState().showResult(s.toString());
                            }
                        });
        }
    }

    @Override
    public void showOBoxResult(Context context, int type, int rows, long id) {
        OBoxService oBoxService = new OBoxService();
        switch (type){
            case CRUDType.CREATE:
                AtomicLong start = new AtomicLong();
                AtomicLong end = new AtomicLong();
                AtomicReference<Double> result = new AtomicReference<>((double) 0);
                Observable.fromCallable(() -> {
                    List<Figure> figures = new ArrayList<>();
                    for(int i = 0; i < rows; i++){
                        Figure square = new Figure();
                        square.setName("Квадрат");
                        square.setColor("Красный");
                        square.setArea(100);
                        figures.add(square);
                        //oBoxService.addFigure(square);
                        //oBoxService.addFigureRx(square).subscribe();
                    }
                    oBoxService.addFiguresRx(figures).subscribe();
                    return new Object();
                }).doOnSubscribe(disposable -> {
                    start.set(System.currentTimeMillis());
                }).doOnComplete(() -> {
                    end.set(System.currentTimeMillis());
                    result.set((end.get() + .0 - start.get())/1000);
                    StringBuilder s = new StringBuilder();
                    s.append(result.get()).append(" сек.");
                    getViewState().showResult(s.toString());
                    //System.out.println("Элементов в таблице сейчас: " + oBoxService.getAllFigures().size());
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
                break;
            case CRUDType.READ:
                AtomicLong start1 = new AtomicLong();
                AtomicLong end1 = new AtomicLong();
                AtomicReference<Double> result1 = new AtomicReference<>((double) 0);
                oBoxService.getAllFiguresRx()
                        .subscribe(new Observer<List<Figure>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                start1.set(System.currentTimeMillis());
                            }

                            @Override
                            public void onNext(List<Figure> figures) {
                                end1.set(System.currentTimeMillis());
                                result1.set((end1.get() + .0 - start1.get())/1000);
                                StringBuilder s1 = new StringBuilder();
                                s1.append(result1.get()).append(" сек.");
                                getViewState().showResult(s1.toString());
                                System.out.println("Кол-во: " + figures.size());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case CRUDType.READ_SEARCHING:
                oBoxService.getFigureById(id)
                        .subscribe(new Observer<Figure>() {
                            long start, end;
                            double result;
                            @Override
                            public void onSubscribe(Disposable d) {
                                start = System.currentTimeMillis();
                            }

                            @Override
                            public void onNext(Figure figure) {
                                end = System.currentTimeMillis();
                                result = (end + .0 - start)/1000;

                                StringBuilder s = new StringBuilder();
                                s.append(result).append(" сек.");
                                getViewState().showResult(s.toString());
                                System.out.println("id:" + figure.getId() + " name:" + figure.getName());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
        }
    }

    @Override
    public void showSnappyDBResult(Context context, int type, int rows, long id) {
        SnappyDBService snappyDBService = new SnappyDBService();
        switch (type){
            case CRUDType.CREATE:
                AtomicLong start = new AtomicLong();
                AtomicLong end = new AtomicLong();
                AtomicReference<Double> result = new AtomicReference<>((double) 0);
                Observable.fromCallable(() -> {
                    for(int i = 0; i < rows; i++){
                        Book book = new Book();
                        book.setAuthor("Толстой");
                        book.setDate(2005);
                        book.setName("Пессель");
                        book.setPagesCount(1000);
                        snappyDBService.addBook(book, "android:"+String.valueOf(i)).subscribe();
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
            case CRUDType.READ:
                AtomicLong start1 = new AtomicLong();
                AtomicLong end1 = new AtomicLong();
                AtomicReference<Double> result1 = new AtomicReference<>((double) 0);
                snappyDBService.getBooks()
                        .subscribe(new Observer<List<Book>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                start1.set(System.currentTimeMillis());
                            }

                            @Override
                            public void onNext(List<Book> books) {
                                end1.set(System.currentTimeMillis());
                                result1.set((end1.get() + .0 - start1.get())/1000);
                                StringBuilder s1 = new StringBuilder();
                                s1.append(result1.get()).append(" сек.");
                                getViewState().showResult(s1.toString());
                                System.out.println(books.size() + " элементов");
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case CRUDType.READ_SEARCHING:
                snappyDBService.getBookByKey((int)id)
                        .subscribe(new Observer<Book>() {
                            long start, end;
                            double result;
                            @Override
                            public void onSubscribe(Disposable d) {
                                start = System.currentTimeMillis();
                            }

                            @Override
                            public void onNext(Book book) {
                                end = System.currentTimeMillis();
                                result = (end + .0 - start)/1000;

                                StringBuilder s1 = new StringBuilder();
                                s1.append(result).append(" сек.");
                                getViewState().showResult(s1.toString());
                                System.out.println("Name:" + book.getName() + " author:" + book.getAuthor());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
        }
    }

}
