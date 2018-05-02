package com.ilya.litosh.roomvsrealm.db.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ilya.litosh.roomvsrealm.db.room.models.Phone;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface PhoneDAO {

    @Insert
    void addPhone(Phone phone);

    @Insert
    void addPhones(List<Phone> phones);

    @Query("SELECT * FROM phone")
    List<Phone> getAllPhones();

    @Delete
    void deletePhone(Phone phone);

    @Query("SELECT * FROM phone WHERE id=:id")
    Phone getPhoneById(long id);

    @Query("SELECT * FROM phone")
    Flowable<List<Phone>> getAllPhonesRx();

    @Query("SELECT * FROM phone WHERE id=:id")
    Flowable<Phone> getPhoneByIdRx(long id);

}
