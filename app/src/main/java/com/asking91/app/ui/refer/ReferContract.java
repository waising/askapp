package com.asking91.app.ui.refer;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BaseMoreView;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 *
 */

public interface ReferContract {

    interface Model extends BaseModel {
        Observable<ResponseBody> getReferList(String id,int start,int limit);
        Observable<ResponseBody> getReferDetail(String referId);
        Observable<ResponseBody> saveCollection(int type, String title,String objId);
        Observable<ResponseBody> checkCollection(int type,String id);
        Observable<ResponseBody> deleteCollection(String ticket, String id);
    }

    interface View extends BaseMoreView<ResponseBody> {
        void onSuccess(int type,ResponseBody body);
        void onError(int type);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getReferList(String id,int start,int limit);
        public abstract void getReferDetail(String referId);
        public abstract void  saveCollection(int type, String title,String objId);
        public abstract void  checkCollection(int type,String id);
        public abstract void  deleteCollection(String ticket, String id);
    }

}
