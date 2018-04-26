package com.ilya.litosh.roomvsrealm.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ilya.litosh.roomvsrealm.db.greendao.models.DaoMaster;
import com.ilya.litosh.roomvsrealm.db.greendao.models.DaoSession;
import com.ilya.litosh.roomvsrealm.db.objectbox.models.MyObjectBox;
import com.ilya.litosh.roomvsrealm.db.ormlite.DBHelper;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.greenrobot.greendao.database.Database;

import io.objectbox.BoxStore;
import io.realm.Realm;

public class App extends Application {

    private static DaoSession daoWritingSession;
    private static DaoSession daoReadingSession;
    private static BoxStore boxStore;
    private static DB snappyDBSession;
    private static DBHelper ormliteHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        // Realm
        Realm.init(this);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "greendao-db");
        Database dbWrite = helper.getWritableDb();
        Database dbRead = helper.getReadableDb();
        daoWritingSession = new DaoMaster(dbWrite).newSession();
        daoReadingSession = new DaoMaster(dbRead).newSession();

        ormliteHelper = new DBHelper(this);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        boxStore = MyObjectBox.builder().androidContext(this).build();
        try {
            snappyDBSession = DBFactory.open(App.this, "snappydb-db");
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public static DaoSession getDaoWritingSession(){
        return daoWritingSession;
    }

    public static DaoSession getDaoReadingSession(){
        return daoReadingSession;
    }

    public static BoxStore getOBoxSession(){
        return boxStore;
    }

    public static DB getSnappyDBSession(){
        return snappyDBSession;
    }

    public static DBHelper getOrmliteHelper(){
        return ormliteHelper;
    }

}
