package com.asking91.app.ui.onlineqa;

import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.mvpframe.BaseView;
import com.asking91.app.util.CommonUtil;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by wxiao on 2016/11/8.
 */

public class OnlineQAAskPresenter extends BasePresenter<OnlineQAAskModel,OnlineQAAskPresenter.View> {


    interface View extends BaseView{
        void onSubmitPicSuccess(ResponseBody res);
        void onSubmitSuccess(ResponseBody res);
//        void onError(Throwable throwable);
    }

    public void submitPicture(MultipartBody.Part part){
        mRxManager.add(mModel.onSubmitPicSuccess(part)
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
                    public void call(final ResponseBody baseRsqEntity) {
                        mView.onSubmitPicSuccess(baseRsqEntity);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
//                        mView.onError(throwable);
                    }
                })

        );
    }

    public void submit(String km,String levelId,String caifu, String title, String description){
        mRxManager.add(mModel.onSubmit(km, levelId, caifu, title, description)
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
                    public void call(ResponseBody baseRsqEntity) {
                        mView.onSubmitSuccess(baseRsqEntity);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }

    public void submit(String km, String caifu, String title, String description){
        mRxManager.add(mModel.onSubmit(km, caifu, title, description)
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
                    public void call(ResponseBody baseRsqEntity) {
                        mView.onSubmitSuccess(baseRsqEntity);
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
