package com.ilya.litosh.roomvsrealm.views;

import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface TypeChooserView extends MvpView {

    void setTypeAdapter(List<String> data);

}
