package com.ilya.litosh.roomvsrealm.db.snappydb.models;

/**
 * Created by ilya_ on 22.04.2018.
 */

public class Book {

    private String name;

    private String author;

    private int date;

    private int pagesCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

}
