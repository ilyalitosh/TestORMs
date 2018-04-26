package com.ilya.litosh.roomvsrealm.presenters.result;

class ResultStringBuilder {

    private String s;

    private ResultStringBuilder(){

    }

    ResultStringBuilder(String s){
        this.s = s;
    }

    public ResultStringBuilder appendEndText(){
        s += " сек.";
        return this;
    }

    @Override
    public String toString() {
        return s;
    }
}
