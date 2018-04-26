package com.ilya.litosh.roomvsrealm.presenters.result;

public class ResultString{

    private ResultStringBuilder s;

    private ResultString(){

    }

    public ResultString(String s){
        this.s = new ResultStringBuilder(s);
        this.s.appendEndText();
    }

    @Override
    public String toString() {
        return s.toString();
    }
}
