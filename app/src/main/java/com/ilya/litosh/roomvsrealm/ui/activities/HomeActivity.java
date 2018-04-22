package com.ilya.litosh.roomvsrealm.ui.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ilya.litosh.roomvsrealm.R;
import com.ilya.litosh.roomvsrealm.db.greendao.GreenDAOService;
import com.ilya.litosh.roomvsrealm.db.greendao.models.Fruit;
import com.ilya.litosh.roomvsrealm.db.objectbox.OBoxService;
import com.ilya.litosh.roomvsrealm.db.objectbox.models.Figure;
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
    private Button submitButton;
    private EditText inputRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Realm
        Realm.init(this);

        initComponents();
        initListeners();

        dbChooserPresenter.setAdapter(this, R.array.db_list);
        typeChooserPresenter.setAdapter(this, R.array.type_list);

    }

    private void initComponents(){
        spinnerDB = findViewById(R.id.choose_db_spinner);
        spinnerType = findViewById(R.id.choose_type_spinner);
        dbResultView = findViewById(R.id.test_db_result_textview);
        submitButton = findViewById(R.id.submit_button);
        inputRows = findViewById(R.id.input_rows);
    }

    private void initListeners(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (spinnerType.getSelectedItemPosition()){
                    case 0:
                        switch (spinnerDB.getSelectedItemPosition()){
                            case 0:
                                dbResultPresenter.showRealmResult(HomeActivity.this,
                                        CRUDType.CREATE,
                                        Integer.valueOf(inputRows.getText().toString()));
                                break;
                            case 1:
                                dbResultPresenter.showRoomResult(HomeActivity.this,
                                        CRUDType.CREATE,
                                        Integer.valueOf(inputRows.getText().toString()));
                                break;
                            case 2:
                                dbResultPresenter.showGreenDAOResult(HomeActivity.this,
                                        CRUDType.CREATE,
                                        Integer.valueOf(inputRows.getText().toString()));
                                break;
                            case 3:
                                dbResultPresenter.showOBoxResult(HomeActivity.this,
                                        CRUDType.CREATE,
                                        Integer.valueOf(inputRows.getText().toString()));
                                break;
                            case 4:
                                break;
                        }
                        break;
                    case 1:
                        switch (spinnerDB.getSelectedItemPosition()){
                            case 0:
                                dbResultPresenter.showRealmResult(HomeActivity.this,
                                        CRUDType.READ,
                                        0);
                                break;
                            case 1:
                                dbResultPresenter.showRoomResult(HomeActivity.this,
                                        CRUDType.READ,
                                        0);
                                break;
                            case 2:
                                dbResultPresenter.showGreenDAOResult(HomeActivity.this,
                                        CRUDType.READ,
                                        0);
                                break;
                            case 3:
                                dbResultPresenter.showOBoxResult(HomeActivity.this,
                                        CRUDType.READ,
                                        0);
                                break;
                            case 4:
                                break;
                        }
                        break;
                }

            }
        });
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
    public void showResult(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dbResultView.setText(s);
            }
        });
    }
}
