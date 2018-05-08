package com.ilya.litosh.roomvsrealm.views;

import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface DbChooserView extends MvpView {

    /**
     * Sets DbSpinner adapter
     * @param data titles db
     */
    void setDBAdapter(List<String> data);

}
