package com.ilya.litosh.roomvsrealm.presenters;

import android.content.Context;

public interface IChooser {

    /**
     * Sets spinner adapter
     * @param context activity context
     * @param resId array resource id
     */
    void setAdapter(Context context, int resId);

}
