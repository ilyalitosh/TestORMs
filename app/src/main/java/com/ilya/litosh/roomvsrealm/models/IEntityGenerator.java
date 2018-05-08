package com.ilya.litosh.roomvsrealm.models;

public interface IEntityGenerator<T> {

    /**
     * Generates chosen entity
     * @param id entity id, if don't need id, use 0
     */
    T generateEntity(long id);

}
