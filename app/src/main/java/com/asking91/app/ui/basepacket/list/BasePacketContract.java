package com.asking91.app.ui.basepacket.list;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 *
 */

public interface BasePacketContract {

    interface Model extends BaseModel {
        Observable<ResponseBody> getSubjectCacaLogList();
        Observable<ResponseBody> checkUserInfo();
    }

    interface View extends BaseView {
        void onSuccess(int type,ResponseBody body);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getSubjectCacaLogList();
        public abstract void checkUserInfo();
    }



}
