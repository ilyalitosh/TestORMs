package com.ilya.litosh.roomvsrealm.models;

public final class ResultString {

    private static StringBuilder s = new StringBuilder();

    private ResultString(){

    }

    /**
     * Returns time string
     * @param start start time
     * @param end end time
     */
    public static String getResult(long start, long end){
        s.setLength(0);
        s.append((end + .0 - start)/1000)
            .append(" сек.");
        return s.toString();
    }

}
