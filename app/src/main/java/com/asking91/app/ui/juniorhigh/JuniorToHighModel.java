package com.asking91.app.ui.juniorhigh;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * 初升高
 * Created by wxwang on 2016/11/14.
 */
public class JuniorToHighModel implements JuniorToHighContract.Model {


    @Override
    public Observable<ResponseBody> juniorToHigh(String courseTypeId, int start, int limit) {
        return Networks.getInstance().getPublicDataApi().juniorToHigh(courseTypeId, start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }


    @Override
    public Observable<ResponseBody> JuniorToHighAllCourse(String packageId) {
        return Networks.getInstance().getPublicDataApi().juniorToHighAllCourse(packageId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> couponList(int start, int limit, int plateform, String eventType, String product) {
        return Networks.getInstance().getCouponApi().getCouponList(start, limit, plateform, eventType, product).compose(RxSchedulers.<ResponseBody>io_main());

    }

    public Observable<ResponseBody> saveSchedule(String commodityId, int schedulePercent, String scheduleTitle, String scheduleId, String scheduleContent) {
        return Networks.getInstance().getUserApi()
                .saveSchedule(commodityId, schedulePercent, scheduleTitle, scheduleId, scheduleContent).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> couponRegister() {
        return Networks.getInstance().getUserApi()
                .couponRegister().compose(RxSchedulers.<ResponseBody>io_main());
    }


    @Override
    public Observable<ResponseBody> goodsCoupon(String commodityId) {
        return Networks.getInstance().getUserApi()
                .goodsCoupon(commodityId).compose(RxSchedulers.<ResponseBody>io_main());
    }


}
