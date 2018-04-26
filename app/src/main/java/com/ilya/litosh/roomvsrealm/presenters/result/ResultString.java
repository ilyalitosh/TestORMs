package com.ilya.litosh.roomvsrealm.presenters.result;

public class ResultString {

    private String s;

    public ResultString(){

    }

    public ResultString(String s){
        this.s += s;
    }

    @Override
    public String toString() {
        return s;
    }
}
