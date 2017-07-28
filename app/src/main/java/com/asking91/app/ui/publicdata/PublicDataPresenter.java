package com.asking91.app.ui.publicdata;

import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.util.CommonUtil;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *在线检测
 */
public class PublicDataPresenter extends PublicDataContract.Presenter {

    @Override
    public void saveNode(String content) {
        mRxManager.add(mModel.saveNode(content)
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
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        mView.onSuccess(Constants.PublicData.SaveNode,body);
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
