package com.ilya.litosh.roomvsrealm.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ilya.litosh.roomvsrealm.R;
import com.ilya.litosh.roomvsrealm.models.CRUDType;
import com.ilya.litosh.roomvsrealm.presenters.DBChooserPresenter;
import com.ilya.litosh.roomvsrealm.presenters.DBResultPresenter;
import com.ilya.litosh.roomvsrealm.presenters.TypeChooserPresenter;
import com.ilya.litosh.roomvsrealm.views.DBChooserView;
import com.ilya.litosh.roomvsrealm.views.DBResultView;
import com.ilya.litosh.roomvsrealm.views.TypeChooserView;

import java.util.List;

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
            if(!TextUtils.isEmpty(inputId.getText()) && !TextUtils.isEmpty(inputRows.getText())){
                dbResultPresenter.execute(spinnerDB.getSelectedItemPosition(),
                        spinnerType.getSelectedItemPosition(),
                        Integer.valueOf(inputRows.getText().toString()),
                        Integer.valueOf(inputId.getText().toString()));
            }else{
                Toast.makeText(HomeActivity.this,
                                "Какое-то поле пустое",
                                Toast.LENGTH_SHORT)
                        .show();
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
