package com.ilya.litosh.roomvsrealm.views;

import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface DBChooserView extends MvpView {

    void setDBAdapter(List<String> data);

}
