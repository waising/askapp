package com.asking91.app.ui.basepacket.list;

import com.asking91.app.global.BasePacketConstant;
import com.asking91.app.global.PayConstant;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.util.CommonUtil;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *
 */
public class BasePacketPresenter extends BasePacketContract.Presenter {


    @Override
    public void getSubjectCacaLogList() {
        mRxManager.add(mModel.getSubjectCacaLogList()
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
                        mView.onSuccess(BasePacketConstant.Knowledge.KNOWLEDGE_LIST,body);
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
    public void checkUserInfo() {
        mRxManager.add(mModel.checkUserInfo()
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
                        mView.onSuccess(PayConstant.Pay.CHECKUSERINFO,body);
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
