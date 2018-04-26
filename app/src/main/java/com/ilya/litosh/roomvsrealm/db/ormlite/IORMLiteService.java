package com.ilya.litosh.roomvsrealm.db.ormlite;

import com.ilya.litosh.roomvsrealm.db.ormlite.models.Student;

import java.util.List;

import io.reactivex.Observable;

public interface IORMLiteService {

    void addStudent(Student student);

    List<Student> getStudents();

    //RxAndroid
    Observable<Student> addStudentRx(Student student);

    Observable<List<Student>> getStudentsRx();

    Observable<Student> getStudentById(int id);

}
