package com.ilya.litosh.roomvsrealm.presenters;

import android.util.Log;
import android.util.SparseArray;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ilya.litosh.roomvsrealm.db.greendao.GreenDaoService;
import com.ilya.litosh.roomvsrealm.db.objectbox.ObjectBoxService;
import com.ilya.litosh.roomvsrealm.db.ormlite.OrmLiteService;
import com.ilya.litosh.roomvsrealm.db.realm.RealmService;
import com.ilya.litosh.roomvsrealm.db.room.RoomService;
import com.ilya.litosh.roomvsrealm.db.snappydb.SnappyDbService;
import com.ilya.litosh.roomvsrealm.models.CrudType;
import com.ilya.litosh.roomvsrealm.models.DbBaseModel;
import com.ilya.litosh.roomvsrealm.views.DbResultView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class DbResultPresenter extends MvpPresenter<DbResultView>{

    private SparseArray<DbBaseModel> mDbModels = new SparseArray<>();
    private static final String TAG = "DbResultPresenter";

    public DbResultPresenter(){
        mDbModels.put(0, new RealmService());
        mDbModels.put(1, new RoomService());
        mDbModels.put(2, new GreenDaoService());
        mDbModels.put(3, new ObjectBoxService());
        mDbModels.put(4, new SnappyDbService());
        mDbModels.put(5, new OrmLiteService());
    }

    /**
     * Executes request to DB
     * @param idDB db id
     * @param idMethod method id
     * @param rows rows count
     * @param id id for searching entity
     */
    public void execute(int idDB, int idMethod, int rows, int id){
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                getViewState().showResult(s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, e.toString());
            }

            @Override
            public void onComplete() {

            }
        };
        switch (idMethod){
            case CrudType.CREATE:
                mDbModels.get(idDB)
                        .reactiveInsertingResult(rows)
                        .subscribe(observer);
                break;
            case CrudType.READ:
                mDbModels.get(idDB)
                        .reactiveReadingAllResult()
                        .subscribe(observer);
                break;
            case CrudType.READ_SEARCHING:
                mDbModels.get(idDB)
                        .reactiveReadingByIdResult(id)
                        .subscribe(observer);
                break;
            default:
                throw new IllegalArgumentException("Нет такого метода");
        }
    }

}
