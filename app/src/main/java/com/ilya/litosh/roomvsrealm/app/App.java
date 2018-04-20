package com.ilya.litosh.roomvsrealm.app;

import android.app.Application;

import com.ilya.litosh.roomvsrealm.db.greendao.models.DaoMaster;
import com.ilya.litosh.roomvsrealm.db.greendao.models.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private static DaoSession daoWritingSession;
    private static DaoSession daoReadingSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "greendao-db");
        Database dbWrite = helper.getWritableDb();
        Database dbRead = helper.getReadableDb();
        daoWritingSession = new DaoMaster(dbWrite).newSession();
        daoReadingSession = new DaoMaster(dbRead).newSession();

    }

    public static DaoSession getDaoWritingSession(){
        return daoWritingSession;
    }

    public static DaoSession getDaoReadingSession(){
        return daoReadingSession;
    }

}
