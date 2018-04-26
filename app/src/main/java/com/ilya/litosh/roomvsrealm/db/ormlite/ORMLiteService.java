package com.ilya.litosh.roomvsrealm.db.ormlite;

import com.ilya.litosh.roomvsrealm.app.App;
import com.ilya.litosh.roomvsrealm.db.ormlite.models.Student;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ORMLiteService implements IORMLiteService {
    @Override
    public void addStudent(Student student) {
        try {
            App.getOrmliteHelper()
                    .getStudentDAO()
                    .create(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getStudents() {
        try {
            return App.getOrmliteHelper()
                    .getStudentDAO()
                    .queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Observable<Student> addStudentRx(Student student) {
        Dao<Student, Integer> studentDao = App.getOrmliteHelper().getStudentDAO();

        return Observable.just(student)
                .doOnNext(student1 -> {
                    studentDao.create(student);
                });
    }

    @Override
    public Observable<List<Student>> getStudentsRx() {
        Dao<Student, Integer> studentDao = App.getOrmliteHelper().getStudentDAO();

        return Observable.just(Student.class)
                .flatMap(new Function<Class<Student>, ObservableSource<List<Student>>>() {
                    @Override
                    public ObservableSource<List<Student>> apply(Class<Student> studentClass) throws Exception {
                        return Observable.just(studentDao.queryForAll());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Student> getStudentById(int id) {
        Dao<Student, Integer> studentDao = App.getOrmliteHelper().getStudentDAO();

        return Observable.just(Student.class)
                .flatMap(new Function<Class<Student>, ObservableSource<Student>>() {
                    @Override
                    public ObservableSource<Student> apply(Class<Student> studentClass) throws Exception {
                        return Observable.just(studentDao.queryForId(id));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
