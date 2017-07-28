package com.asking91.app.ui.publicdata;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxwang on 2016/11/14.
 */
public interface PublicDataContract {
    interface Model extends BaseModel {
        Observable<ResponseBody> saveNode(String content);
    }

    interface View extends BaseView {
        void onSuccess(int type, ResponseBody body);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void saveNode(String content);
    }

}
