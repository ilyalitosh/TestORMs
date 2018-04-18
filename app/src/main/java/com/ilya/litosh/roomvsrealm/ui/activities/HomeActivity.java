package com.ilya.litosh.roomvsrealm.ui.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ilya.litosh.roomvsrealm.R;
import com.ilya.litosh.roomvsrealm.db.room.RoomService;
import com.ilya.litosh.roomvsrealm.db.room.models.Phone;
import com.ilya.litosh.roomvsrealm.models.CRUDType;
import com.ilya.litosh.roomvsrealm.presenters.DBChooserPresenter;
import com.ilya.litosh.roomvsrealm.presenters.DBResultPresenter;
import com.ilya.litosh.roomvsrealm.presenters.TypeChooserPresenter;
import com.ilya.litosh.roomvsrealm.views.DBChooserView;
import com.ilya.litosh.roomvsrealm.views.DBResultView;
import com.ilya.litosh.roomvsrealm.views.TypeChooserView;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class HomeActivity extends MvpAppCompatActivity implements DBChooserView, TypeChooserView, DBResultView {

    @InjectPresenter
    DBChooserPresenter dbChooserPresenter;
    @InjectPresenter
    TypeChooserPresenter typeChooserPresenter;
    @InjectPresenter
    DBResultPresenter dbResultPresenter;

    private AppCompatSpinner spinnerDB;
    private AppCompatSpinner spinnerType;
    private TextView dbResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Realm
        Realm.init(this);

        spinnerDB = findViewById(R.id.choose_db_spinner);
        spinnerType = findViewById(R.id.choose_type_spinner);
        dbResultView = findViewById(R.id.test_db_result_textview);

        dbChooserPresenter.setAdapter(this, R.array.db_list);
        typeChooserPresenter.setAdapter(this, R.array.type_list);

        dbResultPresenter.showRealmResult(this, CRUDType.READ);

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

    @Override
    public void showRoomResult(String s) {
        dbResultView.setText(s);
    }

    @Override
    public void showRealmResult(String s) {
        dbResultView.setText(s);
    }
}
