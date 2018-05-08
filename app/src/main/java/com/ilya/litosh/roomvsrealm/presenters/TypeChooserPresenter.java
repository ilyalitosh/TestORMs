package com.ilya.litosh.roomvsrealm.presenters;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ilya.litosh.roomvsrealm.views.TypeChooserView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;

@InjectViewState
public class TypeChooserPresenter extends MvpPresenter<TypeChooserView> implements IChooser {

    private static final String TAG = "TypeChooserPresenter";

    public TypeChooserPresenter(){

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
                        getViewState().setTypeAdapter(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
