package com.ilya.litosh.roomvsrealm.app;

import android.app.Application;

import com.ilya.litosh.roomvsrealm.db.greendao.models.DaoMaster;
import com.ilya.litosh.roomvsrealm.db.greendao.models.DaoSession;
import com.ilya.litosh.roomvsrealm.db.objectbox.models.MyObjectBox;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappyDB;
import com.snappydb.SnappydbException;

import org.greenrobot.greendao.database.Database;

import io.objectbox.BoxStore;

public class App extends Application {

    private static DaoSession daoWritingSession;
    private static DaoSession daoReadingSession;
    private static BoxStore boxStore;
    private static DB snappyDBSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "greendao-db");
        Database dbWrite = helper.getWritableDb();
        Database dbRead = helper.getReadableDb();
        daoWritingSession = new DaoMaster(dbWrite).newSession();
        daoReadingSession = new DaoMaster(dbRead).newSession();

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

}
