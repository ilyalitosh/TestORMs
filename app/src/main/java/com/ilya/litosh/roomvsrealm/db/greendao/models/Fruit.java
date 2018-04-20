package com.ilya.litosh.roomvsrealm.db.greendao.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

@Entity
public class Fruit {

    @Id(autoincrement = true)
    @Index(unique = true)
    private long id = 0;

    private String name;

    private String color;

    private int weight;

    @Generated(hash = 1271857976)
    public Fruit(long id, String name, String color, int weight) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.weight = weight;
    }

    @Generated(hash = 2030614587)
    public Fruit() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
