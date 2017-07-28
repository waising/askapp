package com.asking91.app.ui.juniorhigh;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * linbin
 */
public interface JuniorToHighContract {
    interface Model extends BaseModel {
        Observable<ResponseBody> juniorToHigh(String courseTypeId, int start, int limit);

        Observable<ResponseBody> JuniorToHighAllCourse(String packageId);

        Observable<ResponseBody> couponList(int start, int limit, int plateform, String eventType, String product);

        Observable<ResponseBody> saveSchedule(String commodityId, int schedulePercent, String scheduleTitle, String scheduleId, String scheduleContent);
        Observable<ResponseBody> couponRegister();

        Observable<ResponseBody> goodsCoupon(String commodityId);


    }

    interface View extends BaseView {
        void onSuccess(int type, ResponseBody body);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

    }

}
