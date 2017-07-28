package com.asking91.app.ui.basepacket.comment;

import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.util.CommonUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wxwang on 2016/11/4.
 */
public class CommentPresenter extends CommentContract.Presenter {

    public Observer<Object> getMoreListObserver(final int pageIndex) {
        return new Observer<Object>() {
            @Override
            public void onCompleted() {
                mView.onRequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                mView.onRequestError(CommonUtil.getRequestEntity(e));
            }

            @Override
            public void onNext(Object obj) {
                if (pageIndex == Constants.PAGE_START) {
                    mView.onRefreshData(obj);
                } else {
                    mView.onLoadMoreData(obj);
                }
            }
        };
    }


    @Override
    public void saveReview(String comment, String tipId) {
        mRxManager.add(mModel.saveReview(comment,tipId)
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
                        mView.onSuccess(obj);
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
    public void getReviewList(String tipId, int start, int limit) {
        mRxManager.add(mModel.getReviewList(tipId,start,limit)
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribe(getMoreListObserver(start)));
    }
}
