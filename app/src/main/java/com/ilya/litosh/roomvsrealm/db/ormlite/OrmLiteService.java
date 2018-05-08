package com.ilya.litosh.roomvsrealm.db.ormlite;

import android.util.Log;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.ormlite.models.Student;
import com.ilya.litosh.roomvsrealm.models.DbBaseModel;
import com.ilya.litosh.roomvsrealm.models.IEntityGenerator;
import com.ilya.litosh.roomvsrealm.models.ResultString;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.sql.SQLException;
import java.util.List;

public class OrmLiteService implements DbBaseModel, IEntityGenerator<Student> {

    private static final String TAG = "OrmLiteService";

    @Override
    public String insertingRes(int rows) {
        long start = System.currentTimeMillis();
        for(int i = 0; i < rows; i++){
            try {
                App.getOrmliteHelper().getStudentDAO().create(generateEntity(0));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ResultString.getResult(start, System.currentTimeMillis());
    }

    @Override
    public Observable<String> reactiveInsertingRes(int rows) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            for(int i = 0; i < rows; i++){
                try {
                    App.getOrmliteHelper().getStudentDAO().create(generateEntity(0));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return ResultString.getResult(start, System.currentTimeMillis());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingAllRes() {
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            List<Student> students = App.getOrmliteHelper().getStudentDAO().queryForAll();
            end = System.currentTimeMillis();
            Log.i(TAG, "Найдено: " + students.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingAllRes() {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            long end = 0;
            try {
                List<Student> students = App.getOrmliteHelper()
                                                .getStudentDAO()
                                                .queryForAll();
                end = System.currentTimeMillis();
                Log.i(TAG, "Найдено: " + students.size());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ResultString.getResult(start, end);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String readingByIdRes(int id) {
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            Student student = App.getOrmliteHelper().getStudentDAO().queryForId(id);
            end = System.currentTimeMillis();
            Log.i(TAG, student.getId() + " "
                    + student.getFirstName() + " "
                    + student.getSecondName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResultString.getResult(start, end);
    }

    @Override
    public Observable<String> reactiveReadingByIdRes(int id) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            long end = 0;
            try {
                Student student = App.getOrmliteHelper().getStudentDAO().queryForId(id);
                end = System.currentTimeMillis();
                Log.i(TAG, student.getId() + " "
                        + student.getFirstName() + " "
                        + student.getSecondName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ResultString.getResult(start, end);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Student generateEntity(long id) {
        Student student = new Student();
        student.setFirstName("Илья");
        student.setSecondName("Литош");
        student.setAge(21);

        return student;
    }
}
