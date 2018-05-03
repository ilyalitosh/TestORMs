package com.ilya.litosh.roomvsrealm.ui.activities

import android.os.Bundle
import android.support.v7.widget.AppCompatSpinner
import android.text.TextUtils
import android.widget.*
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ilya.litosh.roomvsrealm.R
import com.ilya.litosh.roomvsrealm.presenters.DBChooserPresenter
import com.ilya.litosh.roomvsrealm.presenters.DBResultPresenter
import com.ilya.litosh.roomvsrealm.presenters.TypeChooserPresenter
import com.ilya.litosh.roomvsrealm.views.DBChooserView
import com.ilya.litosh.roomvsrealm.views.DBResultView
import com.ilya.litosh.roomvsrealm.views.TypeChooserView

class HomeActivityKt : MvpAppCompatActivity(), DBChooserView, TypeChooserView, DBResultView{

    @InjectPresenter
    var dbChooserPresenter: DBChooserPresenter? = null
    @InjectPresenter
    var typeChooserPresenter: TypeChooserPresenter? = null
    @InjectPresenter
    var dbResultPresenter: DBResultPresenter? = null

    private var spinnerDB: AppCompatSpinner? = null
    private var spinnerType: AppCompatSpinner? = null
    private var dbResultView: TextView? = null
    private var submitButton: Button? = null
    private var inputRows: EditText? = null
    private var inputId: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        initListeners()

    }

    private fun initListeners() {
        submitButton?.setOnClickListener(){
            if(TextUtils.isEmpty(inputId?.text) && TextUtils.isEmpty(inputRows?.text)){
                dbResultPresenter?.execute(spinnerDB!!.selectedItemPosition,
                        spinnerType!!.selectedItemPosition,
                        Integer.valueOf(inputRows?.text?.toString()),
                        0)
            }else{
                Toast.makeText(this, "Какое-то поле пустое", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initComponents(){
        spinnerDB = findViewById(R.id.choose_db_spinner)
        spinnerType = findViewById(R.id.choose_type_spinner)
        dbResultView = findViewById(R.id.test_db_result_textview)
        submitButton = findViewById(R.id.submit_button)
        inputRows = findViewById(R.id.input_rows)
        inputId = findViewById(R.id.input_id)
    }

    override fun showResult(s: String?) {
        dbResultView?.text = s
    }

    override fun setTypeAdapter(data: MutableList<String>?) {
        spinnerType?.adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                data)
    }

    override fun setDBAdapter(data: MutableList<String>?) {
        spinnerDB?.adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                data)
    }

}