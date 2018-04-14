package com.ilya.litosh.roomvsrealm.ui.activities;

import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ilya.litosh.roomvsrealm.R;
import com.ilya.litosh.roomvsrealm.db.realm.RealmService;
import com.ilya.litosh.roomvsrealm.db.realm.models.Car;
import com.ilya.litosh.roomvsrealm.db.room.PhoneDAO;
import com.ilya.litosh.roomvsrealm.db.room.RoomService;
import com.ilya.litosh.roomvsrealm.db.room.models.Phone;
import com.ilya.litosh.roomvsrealm.presenters.DBChooserPresenter;
import com.ilya.litosh.roomvsrealm.presenters.TypeChooserPresenter;
import com.ilya.litosh.roomvsrealm.views.DBChooserView;
import com.ilya.litosh.roomvsrealm.views.TypeChooserView;

import java.util.List;
import java.util.Set;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class HomeActivity extends MvpAppCompatActivity implements DBChooserView, TypeChooserView {

    @InjectPresenter
    DBChooserPresenter dbChooserPresenter;
    @InjectPresenter
    TypeChooserPresenter typeChooserPresenter;

    private AppCompatSpinner spinnerDB;
    private AppCompatSpinner spinnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Realm
        /*Car car = new Car();
        car.setColor("green");
        car.setFuelCapacity(250);
        car.setPrice(2000);
        Realm.init(this);
        RealmService dbService = new RealmService();
        dbService.addCar(car);
        List<Car> carList = dbService.getAllCars();
        for(Car l: carList){
            System.out.println(l.getId() + " "
                    + l.getPrice() + " "
                    + l.getFuelCapacity() + " "
                    + l.getColor());
        }*/
        // Room
        RoomService db = Room.databaseBuilder(this, RoomService.class, "room-databse").build();
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Phone samsung = new Phone();
                samsung.setId(1);
                db.getPhoneDAO().deletePhone(samsung);

                List<Phone> phones = db.getPhoneDAO().getAllPhones();
                for(Phone phone : phones){
                    System.out.println(phone.getId() + " " + phone.getName() + " " + phone.getModel());
                }
            }
        }).start();*/
        /*db.getPhoneDAO().getAllPhones()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Phone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Phone> phones) {
                        /*Phone samsung = new Phone();
                        samsung.setName("Motorola");
                        samsung.setModel("KAkaya-nibud");
                        db.getPhoneDAO().deletePhone(samsung);

                        for(Phone phone : phones){
                            System.out.println(phone.getId() + " " + phone.getName() + " " + phone.getModel());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
        android.arch.lifecycle.Observer a = new android.arch.lifecycle.Observer() {
            @Override
            public void onChanged(@Nullable Object o) {

            }
        };

        spinnerDB = findViewById(R.id.choose_db_spinner);
        spinnerType = findViewById(R.id.choose_type_spinner);

        dbChooserPresenter.setAdapter(this, R.array.db_list);
        typeChooserPresenter.setAdapter(this, R.array.type_list);

    }

    @Override
    public void setDBAdapter(List<String> data) {
        spinnerDB.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                data));
    }

    @Override
    public void setTypeAdapter(List<String> data) {
        spinnerType.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                data));
    }
}
