package com.ilya.litosh.roomvsrealm.presenters;

import android.util.SparseArray;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ilya.litosh.roomvsrealm.db.greendao.GreenDAOService;
import com.ilya.litosh.roomvsrealm.db.objectbox.OBoxService;
import com.ilya.litosh.roomvsrealm.db.ormlite.ORMLiteService;
import com.ilya.litosh.roomvsrealm.db.realm.RealmService;
import com.ilya.litosh.roomvsrealm.db.room.RoomService;
import com.ilya.litosh.roomvsrealm.db.snappydb.SnappyDBService;
import com.ilya.litosh.roomvsrealm.models.CRUDType;
import com.ilya.litosh.roomvsrealm.models.DBBaseModel;
import com.ilya.litosh.roomvsrealm.views.DBResultView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class DBResultPresenter extends MvpPresenter<DBResultView>{

    private SparseArray<DBBaseModel> dbModels = new SparseArray<>();

    public DBResultPresenter(){
        dbModels.put(0, new RealmService());
        dbModels.put(1, new RoomService());
        dbModels.put(2, new GreenDAOService());
        dbModels.put(3, new OBoxService());
        dbModels.put(4, new SnappyDBService());
        dbModels.put(5, new ORMLiteService());
    }

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
                System.out.println("---------------- " + e);
            }

            @Override
            public void onComplete() {

            }
        };
        switch (idMethod){
            case CRUDType.CREATE:
                dbModels.get(idDB)
                        .reactiveInsertingRes(rows)
                        .subscribe(observer);
                break;
            case CRUDType.READ:
                dbModels.get(idDB)
                        .reactiveReadingAllRes()
                        .subscribe(observer);
                break;
            case CRUDType.READ_SEARCHING:
                dbModels.get(idDB)
                        .reactiveReadingByIdRes(id)
                        .subscribe(observer);
                break;
            default:
                throw new IllegalArgumentException("Нет такого метода");
        }
    }

}
