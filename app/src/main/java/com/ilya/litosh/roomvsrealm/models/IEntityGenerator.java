package com.ilya.litosh.roomvsrealm.models;

public interface IEntityGenerator<T> {

    /**
     * генерирует указанную сущность
     * @param id id сущности, если id не нужно указывать, то поставить 0
     */
    T generateEntity(long id);

}
