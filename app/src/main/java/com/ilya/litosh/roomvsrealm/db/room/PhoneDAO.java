package com.ilya.litosh.roomvsrealm.db.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ilya.litosh.roomvsrealm.db.room.models.Phone;

import io.reactivex.Flowable;

import java.util.List;

@Dao
public interface PhoneDAO {

    /**
     * Insert Phone
     * @param phone Phone entity
     */
    @Insert
    void addPhone(Phone phone);

    /**
     * Insert List<Phone>
     * @param phones phones list
     */
    @Insert
    void addPhones(List<Phone> phones);

    /**
     * Returns all phones from db
     */
    @Query("SELECT * FROM phone")
    List<Phone> getAllPhones();

    /**
     * Removes Phone entity
     * @param phone Phone entity to remove
     */
    @Delete
    void deletePhone(Phone phone);

    /**
     * Returns Phone by id
     * @param id entity id
     */
    @Query("SELECT * FROM phone WHERE id=:id")
    Phone getPhoneById(long id);

    /**
     * Returns Flowable with all phones from db
     */
    @Query("SELECT * FROM phone")
    Flowable<List<Phone>> getAllPhonesRx();

    /**
     * Returns Flowable with phone by id
     * @param id entity id
     */
    @Query("SELECT * FROM phone WHERE id=:id")
    Flowable<Phone> getPhoneByIdRx(long id);

}
