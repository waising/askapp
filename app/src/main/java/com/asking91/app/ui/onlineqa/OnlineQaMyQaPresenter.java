package com.asking91.app.ui.onlineqa;

import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.mvpframe.BaseView;
import com.asking91.app.util.CommonUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wxiao on 2016/11/8.
 */

public class OnlineQaMyQaPresenter extends BasePresenter<OnlineQaMyQaModel, OnlineQaMyQaPresenter.View> {
    interface View extends BaseView{
        void onSuccess(ResponseBody res);
        void onSuccess(String method, String str);
        void onRefreshData(ResponseBody res);
        void onLoadMoreData(ResponseBody res);
    }

    public Observer<ResponseBody> onlineAgain(final int pageIndex) {
        return new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {
                mView.onRequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                mView.onRequestError(CommonUtil.getRequestEntity(e));
            }

            @Override
            public void onNext(final ResponseBody res) {
                if (pageIndex == 0) {
                    mView.onRefreshData(res);
                } else {
                    mView.onLoadMoreData(res);
                }
            }


        };
    }
    public void onMyAsk(int start, String limit, String query){
        mRxManager.add(mModel.onMyAsk(start, limit, query)
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribe(onlineAgain(start)));}

    public void onMyAnswer(String type, int start, String limit, String query){
        mRxManager.add(mModel.onMyAnswer(type, start, limit, query)
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribe(onlineAgain(start)));}


//    public void onMyAsk(int start, String limit, String query){
//        mRxManager.add(mModel.onMyAsk(start, limit, query)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        mView.onRequestStart();
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<ResponseBody>() {
//                    @Override
//                    public void call(ResponseBody baseRsqEntity) {
//                        mView.onSuccess(baseRsqEntity);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        mView.onInternetError();
//                        mView.onRequestEnd();
//                    }
//                })
//
//        );
//    }


    public void onlineQaAgainAsk(String id, String anserId, String html){
        mRxManager.add(mModel.onlineQaAgainAsk(id, anserId, html)
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
                        try {
                            mView.onSuccess("onlineQaAgainAsk",baseRsqEntity.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }
    public void onlineQaAgainAnswer(String id, String anserId, String html){
        mRxManager.add(mModel.onlineQaAgainAnswer(id, anserId, html)
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
                        try {
                            mView.onSuccess("onlineQaAgainAnswer",baseRsqEntity.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }

    public void onlineQATalk(String id){
        mRxManager.add(mModel.onlineQATalk(id)
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
                        mView.onSuccess(baseRsqEntity);
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
