package com.ilya.litosh.roomvsrealm.db.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ilya.litosh.roomvsrealm.db.room.models.Phone;

import java.util.List;

@Dao
public interface PhoneDAO {

    @Insert
    void addPhone(Phone phone);

    @Delete
    void deletePhone(Phone phone);

    @Query("SELECT * FROM phone")
    List<Phone> getAllPhones();

}
