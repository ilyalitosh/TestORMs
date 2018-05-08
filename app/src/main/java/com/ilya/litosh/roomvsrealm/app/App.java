package com.ilya.litosh.roomvsrealm.app;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.ilya.litosh.roomvsrealm.db.greendao.models.DaoMaster;
import com.ilya.litosh.roomvsrealm.db.greendao.models.DaoSession;
import com.ilya.litosh.roomvsrealm.db.objectbox.models.MyObjectBox;
import com.ilya.litosh.roomvsrealm.db.ormlite.DbHelper;
import com.ilya.litosh.roomvsrealm.db.room.RoomDB;
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
    private static DbHelper ormliteHelper;
    private static RoomDB roomDBSession;

    @Override
    public void onCreate() {
        super.onCreate();
        // Realm
        Realm.init(this);

        // GreenDAO
        DaoMaster.DevOpenHelper helper =
                new DaoMaster.DevOpenHelper(this, "greendao-db");
        Database dbWrite = helper.getWritableDb();
        Database dbRead = helper.getReadableDb();
        daoWritingSession = new DaoMaster(dbWrite).newSession();
        daoReadingSession = new DaoMaster(dbRead).newSession();

        // Room
        roomDBSession = Room.databaseBuilder(
                                this,
                                RoomDB.class,
                                "room-database")
                            .build();

        // ORMLite
        ormliteHelper = new DbHelper(this);

        // ObjectBox
        boxStore = MyObjectBox.builder().androidContext(this).build();

        // SnappyDB
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

    public static DbHelper getOrmliteHelper(){
        return ormliteHelper;
    }

    public static RoomDB getRoomDBSession(){
        return roomDBSession;
    }

}
