package com.ilya.litosh.roomvsrealm.presenters;

import android.content.Context;

public interface IResultShow {

    void showRoomResult(Context context, int type, int rows);

    void showRealmResult(Context context, int type, int rows);

    void showGreenDAOResult(Context context, int type);

}
