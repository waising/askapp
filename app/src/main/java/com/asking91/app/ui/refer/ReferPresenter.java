package com.asking91.app.ui.refer;

import com.asking91.app.global.Constants;
import com.asking91.app.global.ReferConstant;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.util.CommonUtil;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *
 */
public class ReferPresenter extends ReferContract.Presenter {

    public Observer<ResponseBody> getReferListObserver(final int pageIndex) {
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
            public void onNext(ResponseBody body) {
                if (pageIndex == Constants.PAGE_START) {
                    mView.onRefreshData(body);
                } else {
                    mView.onLoadMoreData(body);
                }
            }
        };
    }

    @Override
    public void getReferList(String id, int start, int limit) {
        mRxManager.add(mModel.getReferList(id,start,limit)
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribe(getReferListObserver(start))
        );
    }

    @Override
    public void getReferDetail(String referId) {
        mRxManager.add(mModel.getReferDetail(referId)
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
                        mView.onSuccess(ReferConstant.Refer.refer_detail,body);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
//                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                        mView.onError(ReferConstant.Refer.refer_detail);
                    }
                })
        );
    }

    @Override
    public void saveCollection(int type, String title, String objId) {
        mRxManager.add(mModel.saveCollection(type,title,objId)
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
                                mView.onSuccess(ReferConstant.Refer.save,body);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
//                        mView.onInternetError();
                                mView.onRequestEnd();
                                mView.onError(ReferConstant.Refer.save);
                            }
                        })
        );
    }

    @Override
    public void checkCollection(int type, String id) {
        mRxManager.add(mModel.checkCollection(type,id)
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
                                mView.onSuccess(ReferConstant.Refer.check,body);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
//                        mView.onInternetError();
                                mView.onRequestEnd();
                                mView.onError(ReferConstant.Refer.check);
                            }
                        })
        );
    }

    @Override
    public void deleteCollection(String ticket, String id) {
        mRxManager.add(mModel.deleteCollection(ticket,id)
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
                                mView.onSuccess(ReferConstant.Refer.delete,body);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
//                        mView.onInternetError();
                                mView.onRequestEnd();
                                mView.onError(ReferConstant.Refer.delete);
                            }
                        })
        );
    }
}
