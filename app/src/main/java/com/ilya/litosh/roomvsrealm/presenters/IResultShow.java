package com.ilya.litosh.roomvsrealm.presenters;

import android.content.Context;

public interface IResultShow {

    void showRoomResult(Context context, int type, int rows, long id);

    void showRealmResult(Context context, int type, int rows, long id);

    void showGreenDAOResult(Context context, int type, int rows, long id);

    void showOBoxResult(Context context, int type, int rows, long id);

    void showSnappyDBResult(Context context, int type, int rows, long id);

}
