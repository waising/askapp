package com.asking91.app.ui.onlineqa;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxiao on 2016/10/31.
 */

public interface OnlineQAContract {

    interface View extends BaseView {
        void onOnlineQAonlineQASuccess(ResponseBody obj);
        void onRefreshData(ResponseBody obj);
        void onLoadMoreData(ResponseBody obj);
        void onSuccess(String method, String str);
    }

    interface Model extends BaseModel {
        Observable<ResponseBody> onlineQAonlineQA(String type, String state, int start, int limit, String km, String levelId);
        Observable<ResponseBody> onlineQAonlineQA(String type, String state, int start, int limit, String km);
        Observable<ResponseBody> onlineQAonlineQA(String type, String state, int start, int limit);
        Observable<ResponseBody> onlineQAonlineQA(String type, int start, int limit);
        Observable<ResponseBody> onlineQaAgainAsk(String id, String anserId, String html);
        Observable<ResponseBody> onlineQaAgainAnswer(String id, String anserId, String html);
//        Observable<Object> onlineQAonlineQA(JSONObject object);
        Observable<ResponseBody> onlineQAAnser(String id, String content);
        Observable<ResponseBody> otoGetToken();
        Observable<ResponseBody> onSubmitPicSuccess(MultipartBody.Part part);
        Observable<ResponseBody> onlineQaAdoptAnswer(String id, String anserId);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void onlineQAonlineQA(String type, String state, int start, int limit, String km, String levelId);
        public abstract void onlineQAonlineQA(String type, String state, int start, int limit, String km);
        public abstract void onlineQAonlineQA(String type, String state, int start, int limit);
        public abstract void onlineQAonlineQA(String type, int start, int limit);
//        public abstract void onlineQAonlineQA(JSONObject object);
        /**回答问题保存*/
        public abstract void onlineQAAnser(String id, String content);
        public abstract void  onSubmitPicSuccess(MultipartBody.Part part);
        public abstract void  onlineQaAdoptAnswer(String id, String anserId, int position);

    }


}