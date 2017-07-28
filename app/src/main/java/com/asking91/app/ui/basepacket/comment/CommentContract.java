package com.asking91.app.ui.basepacket.comment;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import rx.Observable;

/**
 * Created by wxwang on 2016/11/4.
 */
public interface CommentContract {
    interface Model extends BaseModel {
        Observable<Object> saveReview(String comment, String tipId);
        Observable<Object> getReviewList(String tipId, int start, int limit);
    }

    interface View extends BaseView, MoreView {
        void onSuccess(Object obj);
    }

    interface MoreView {
        void onRefreshData(Object obj);
        void onLoadMoreData(Object obj);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void saveReview(String comment,String tipId);
        public abstract void getReviewList(String tipId,int start,int limit);
    }
}
