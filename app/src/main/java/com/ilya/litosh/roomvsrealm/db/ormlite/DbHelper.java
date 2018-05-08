package com.ilya.litosh.roomvsrealm.db.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ilya.litosh.roomvsrealm.db.ormlite.models.Student;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static Dao<Student, Integer> studentDAO = null;

    public DbHelper(Context context) {
        super(context, "ormlite-db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Student.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Student.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Student, Integer> getStudentDAO(){
        try{
            if(studentDAO == null){
                studentDAO = getDao(Student.class);
            }
            return studentDAO;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
