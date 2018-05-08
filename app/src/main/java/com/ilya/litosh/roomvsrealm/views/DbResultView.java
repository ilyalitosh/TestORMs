package com.ilya.litosh.roomvsrealm.views;

import com.arellomobile.mvp.MvpView;

public interface DbResultView extends MvpView {

    /**
     * Shows string result
     * @param s result string
     */
    void showResult(String s);

}
