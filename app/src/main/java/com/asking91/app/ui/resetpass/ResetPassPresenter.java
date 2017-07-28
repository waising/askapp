package com.asking91.app.ui.resetpass;

import android.content.Context;

import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.util.CommonUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wxiao on 2016/10/27.
 */

public class ResetPassPresenter extends ResetPassContract.Presenter {
    @Override
    public void getYZMRestPass(Context context, String mobile) {
        mRxManager.add(mModel.getYZMRestPass(context, mobile)
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
    public void resetPass(String mobile, String password, String verifyCode) {
        mRxManager.add(mModel.resetPass(mobile, password, verifyCode)
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
                        mView.onResetPassSuccess(obj);
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
