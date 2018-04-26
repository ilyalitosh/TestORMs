package com.ilya.litosh.roomvsrealm.db.ormlite.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "students")
public class Student {

    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    private int id;

    @DatabaseField(columnName = "first_name", dataType = DataType.STRING)
    private String firstName;

    @DatabaseField(columnName = "second_name", dataType = DataType.STRING)
    private String secondName;

    @DatabaseField(columnName = "age", dataType = DataType.INTEGER)
    private int age;

    public Student(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



}
