package com.ilya.litosh.roomvsrealm.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ilya.litosh.roomvsrealm.views.DbChooserView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;

@InjectViewState
public class DbChooserPresenter extends MvpPresenter<DbChooserView> implements IChooser {

    public DbChooserPresenter(){
    }

    @Override
    public void setAdapter(Context context, int resId) {
        List<String> data = Arrays.asList(context.getResources().getStringArray(resId));
        Observable.just(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<String> data) {
                        getViewState().setDBAdapter(data);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
