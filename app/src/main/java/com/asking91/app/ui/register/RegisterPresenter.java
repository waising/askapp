package com.asking91.app.ui.register;

import android.content.Context;

import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.util.CommonUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by wxiao on 2016/10/25.
 */

public class RegisterPresenter extends RegisterContract.Presenter {
    @Override
    public void getYZM(Context context, String mobile) {
        mRxManager.add(mModel.getYZM(context, mobile)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object obj) {
                        mView.onYZMSuccess(obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }

    @Override
    public void register(Context context, String mobile, String passWord, String verifyCode,String inviteCode) {
        mRxManager.add(mModel.register(context, mobile, passWord, verifyCode,inviteCode)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object obj) {
                        mView.onRegisterSuccess(obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }
}
