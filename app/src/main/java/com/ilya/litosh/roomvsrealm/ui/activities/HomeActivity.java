package com.ilya.litosh.roomvsrealm.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ilya.litosh.roomvsrealm.R;
import com.ilya.litosh.roomvsrealm.db.ormlite.ORMLiteService;
import com.ilya.litosh.roomvsrealm.db.ormlite.models.Student;
import com.ilya.litosh.roomvsrealm.models.CRUDType;
import com.ilya.litosh.roomvsrealm.presenters.DBChooserPresenter;
import com.ilya.litosh.roomvsrealm.presenters.DBResultPresenter;
import com.ilya.litosh.roomvsrealm.presenters.TypeChooserPresenter;
import com.ilya.litosh.roomvsrealm.views.DBChooserView;
import com.ilya.litosh.roomvsrealm.views.DBResultView;
import com.ilya.litosh.roomvsrealm.views.TypeChooserView;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

//import io.realm.Realm;

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
    private EditText inputId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        inputId = findViewById(R.id.input_id);
    }

    private void initListeners(){
        submitButton.setOnClickListener(v -> {
            switch (spinnerType.getSelectedItemPosition()){
                case CRUDType.CREATE:
                    switch (spinnerDB.getSelectedItemPosition()){
                        case 0:
                            dbResultPresenter.showRealmResult(HomeActivity.this,
                                    CRUDType.CREATE,
                                    Integer.valueOf(inputRows.getText().toString()),
                                    0);
                            break;
                        case 1:
                            dbResultPresenter.showRoomResult(HomeActivity.this,
                                    CRUDType.CREATE,
                                    Integer.valueOf(inputRows.getText().toString()),
                                    0);
                            break;
                        case 2:
                            dbResultPresenter.showGreenDAOResult(HomeActivity.this,
                                    CRUDType.CREATE,
                                    Integer.valueOf(inputRows.getText().toString()),
                                    0);
                            break;
                        case 3:
                            dbResultPresenter.showOBoxResult(HomeActivity.this,
                                    CRUDType.CREATE,
                                    Integer.valueOf(inputRows.getText().toString()),
                                    0);
                            break;
                        case 4:
                            dbResultPresenter.showSnappyDBResult(HomeActivity.this,
                                    CRUDType.CREATE,
                                    Integer.valueOf(inputRows.getText().toString()),
                                    0);
                            break;
                        case 5:
                            dbResultPresenter.showORMLiteResult(HomeActivity.this,
                                    CRUDType.CREATE,
                                    Integer.valueOf(inputRows.getText().toString()),
                                    0);
                            break;
                    }
                    break;
                case CRUDType.READ:
                    switch (spinnerDB.getSelectedItemPosition()){
                        case 0:
                            dbResultPresenter.showRealmResult(HomeActivity.this,
                                    CRUDType.READ,
                                    0,
                                    0);
                            break;
                        case 1:
                            dbResultPresenter.showRoomResult(HomeActivity.this,
                                    CRUDType.READ,
                                    0,
                                    0);
                            break;
                        case 2:
                            dbResultPresenter.showGreenDAOResult(HomeActivity.this,
                                    CRUDType.READ,
                                    0,
                                    0);
                            break;
                        case 3:
                            dbResultPresenter.showOBoxResult(HomeActivity.this,
                                    CRUDType.READ,
                                    0,
                                    0);
                            break;
                        case 4:
                            dbResultPresenter.showSnappyDBResult(HomeActivity.this,
                                    CRUDType.READ,
                                    0,
                                    0);
                            break;
                        case 5:
                            dbResultPresenter.showORMLiteResult(HomeActivity.this,
                                    CRUDType.READ,
                                    0,
                                    0);
                    }
                    break;
                case CRUDType.READ_SEARCHING:
                    switch (spinnerDB.getSelectedItemPosition()){
                        case 0:
                            dbResultPresenter.showRealmResult(HomeActivity.this,
                                    CRUDType.READ_SEARCHING,
                                    0,
                                    Integer.valueOf(inputId.getText().toString()));
                            break;
                        case 1:
                            dbResultPresenter.showRoomResult(HomeActivity.this,
                                    CRUDType.READ_SEARCHING,
                                    0,
                                    Integer.valueOf(inputId.getText().toString()));
                            break;
                        case 2:
                            dbResultPresenter.showGreenDAOResult(HomeActivity.this,
                                    CRUDType.READ_SEARCHING,
                                    0,
                                    Integer.valueOf(inputId.getText().toString()));
                            break;
                        case 3:
                            dbResultPresenter.showOBoxResult(HomeActivity.this,
                                    CRUDType.READ_SEARCHING,
                                    0,
                                    Integer.valueOf(inputId.getText().toString()));
                            break;
                        case 4:
                            dbResultPresenter.showSnappyDBResult(HomeActivity.this,
                                    CRUDType.READ_SEARCHING,
                                    0,
                                    Integer.valueOf(inputId.getText().toString()));
                            break;
                        case 5:
                            dbResultPresenter.showORMLiteResult(HomeActivity.this,
                                    CRUDType.READ_SEARCHING,
                                    0,
                                    Integer.valueOf(inputId.getText().toString()));
                            break;
                    }
                    break;
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
