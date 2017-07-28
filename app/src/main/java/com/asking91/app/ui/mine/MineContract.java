package com.asking91.app.ui.mine;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BaseMoreView;
import com.asking91.app.mvpframe.BasePresenter;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 *
 */

public interface MineContract {

    interface Model extends BaseModel {
        Observable<ResponseBody> getRegionInfo(String regionCode);

        Observable<ResponseBody> getSchoolInfo(int requestType, String regionCode);

        Observable<ResponseBody> completeUserSchool(String schoolId, String schoolName, String regionCode,
                                                    String regionName,
                                                    String name);

        Observable<ResponseBody> getAvatar(String id);

        Observable<ResponseBody> getAppUser();

        Observable<ResponseBody> loginOut();

        Observable<ResponseBody> sign();

        Observable<ResponseBody> checkTodaySign();

        Observable<ResponseBody> dailySign();

        Observable<ResponseBody> invite();

        Observable<ResponseBody> orderhistory(String start, String limit, String account
                , String role);

        Observable<ResponseBody> studentcomplain(String reason, String details, String id);
    }

    interface View extends BaseMoreView<ResponseBody> {
        void onSuccess(int type, ResponseBody body);

        void onError(int type);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getRegionInfo(String regionCode);

        public abstract void getSchoolInfo(int requestType, String regionCode);

        public abstract void completeUserSchool(String schoolId, String schoolName, String regionCode,
                                                String regionName,
                                                String name);

        public abstract void getAvatar(String id);

        public abstract void getAppUser();

        public abstract void loginOut();

        public abstract void sign();
    }

}
