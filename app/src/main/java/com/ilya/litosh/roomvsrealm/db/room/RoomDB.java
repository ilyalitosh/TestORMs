package com.ilya.litosh.roomvsrealm.db.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ilya.litosh.roomvsrealm.db.room.models.Phone;

@Database(entities = {Phone.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {

    public abstract PhoneDAO getPhoneDAO();

}
