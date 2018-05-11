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
import com.ilya.litosh.roomvsrealm.presenters.DbChooserPresenter;
import com.ilya.litosh.roomvsrealm.presenters.DbResultPresenter;
import com.ilya.litosh.roomvsrealm.presenters.TypeChooserPresenter;
import com.ilya.litosh.roomvsrealm.views.DbChooserView;
import com.ilya.litosh.roomvsrealm.views.DbResultView;
import com.ilya.litosh.roomvsrealm.views.TypeChooserView;

import java.util.List;

public class HomeActivity extends MvpAppCompatActivity implements DbChooserView, TypeChooserView,
        DbResultView {

    @InjectPresenter
    DbChooserPresenter dbChooserPresenter;
    @InjectPresenter
    TypeChooserPresenter typeChooserPresenter;
    @InjectPresenter
    DbResultPresenter dbResultPresenter;

    private AppCompatSpinner mSpinnerDB;
    private AppCompatSpinner mSpinnerType;
    private TextView mDbResultView;
    private Button mSubmitButton;
    private EditText mInputRows;
    private EditText mInputId;

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
        mSpinnerDB = findViewById(R.id.choose_db_spinner);
        mSpinnerType = findViewById(R.id.choose_type_spinner);
        mDbResultView = findViewById(R.id.test_db_result_textview);
        mSubmitButton = findViewById(R.id.submit_button);
        mInputRows = findViewById(R.id.input_rows);
        mInputId = findViewById(R.id.input_id);
    }

    private void initListeners(){
        mSubmitButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mInputId.getText())
                    && !TextUtils.isEmpty(mInputRows.getText())) {
                dbResultPresenter.execute(mSpinnerDB.getSelectedItemPosition(),
                        mSpinnerType.getSelectedItemPosition(),
                        Integer.valueOf(mInputRows.getText().toString()),
                        Integer.valueOf(mInputId.getText().toString()));
            } else {
                Toast.makeText(HomeActivity.this,
                                "Какое-то поле пустое",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public void setDBAdapter(List<String> data) {
        mSpinnerDB.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                data));
    }

    @Override
    public void setTypeAdapter(List<String> data) {
        mSpinnerType.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                data));
    }

    @Override
    public void showResult(String s) {
        runOnUiThread(() -> {
            mDbResultView.setText(s);
        });
    }
}
