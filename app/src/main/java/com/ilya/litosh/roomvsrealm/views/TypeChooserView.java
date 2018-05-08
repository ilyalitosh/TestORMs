package com.ilya.litosh.roomvsrealm.views;

import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface TypeChooserView extends MvpView {

    /**
     * Sets TypeSpinner adapter
     * @param data titles crud type
     */
    void setTypeAdapter(List<String> data);

}
