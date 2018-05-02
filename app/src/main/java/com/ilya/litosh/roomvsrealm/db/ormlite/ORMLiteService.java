package com.ilya.litosh.roomvsrealm.db.ormlite;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.ormlite.models.Student;
import com.ilya.litosh.roomvsrealm.models.DBBaseModel;

import java.sql.SQLException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ORMLiteService implements DBBaseModel {
    @Override
    public String insert(int rows) {
        long start = System.currentTimeMillis();
        for(int i = 0; i < rows; i++){
            Student student = new Student();
            student.setFirstName("Илья");
            student.setSecondName("Литош");
            student.setAge(21);
            try {
                App.getOrmliteHelper().getStudentDAO().create(student);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return String.valueOf((System.currentTimeMillis() + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> insertRx(int rows) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            for(int i = 0; i < rows; i++){
                Student student = new Student();
                student.setFirstName("Илья");
                student.setSecondName("Литош");
                student.setAge(21);
                try {
                    App.getOrmliteHelper().getStudentDAO().create(student);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return String.valueOf((System.currentTimeMillis() + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String getAll() {
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            List<Student> students = App.getOrmliteHelper().getStudentDAO().queryForAll();
            end = System.currentTimeMillis();
            System.out.println("Найдено: " + students.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf((end + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> getAllRx() {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            long end = 0;
            try {
                List<Student> students = App.getOrmliteHelper().getStudentDAO().queryForAll();
                end = System.currentTimeMillis();
                System.out.println("Найдено: " + students.size());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return String.valueOf((end + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String getById(int id) {
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            Student student = App.getOrmliteHelper().getStudentDAO().queryForId(id);
            end = System.currentTimeMillis();
            System.out.println(student.getId() + " "
                    + student.getFirstName() + " "
                    + student.getSecondName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf((end + .0 - start)/1000) + " сек.";
    }

    @Override
    public Observable<String> getByIdRx(int id) {
        return Observable.fromCallable(() -> {
            long start = System.currentTimeMillis();
            long end = 0;
            try {
                Student student = App.getOrmliteHelper().getStudentDAO().queryForId(id);
                end = System.currentTimeMillis();
                System.out.println(student.getId() + " "
                        + student.getFirstName() + " "
                        + student.getSecondName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return String.valueOf((end + .0 - start)/1000) + " сек.";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
