
package com.asking91.app.ui.aq;

import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.util.CommonUtil;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *
 */
public class AqPresenter extends AqContract.Presenter {

    @Override
    public void chagePassword(String oldPass, String pass, String pass1) {
        mRxManager.add(mModel.chagePassword(oldPass,pass, pass1)
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
                        mView.onSuccess(body);
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
