package com.asking91.app.ui.mine;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 *
 */
public class MineModel implements MineContract.Model {

    @Override
    public Observable<ResponseBody> getRegionInfo(String regionCode) {
        return Networks.getInstance().getUserApi()
                .getRegionInfo(regionCode).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getSchoolInfo(int requestType, String regionCode) {
        return Networks.getInstance().getUserApi().getSchoolInfo(regionCode).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> completeUserSchool(String schoolId, String schoolName, String regionCode, String regionName, String name) {
        return Networks.getInstance().getUserApi().completeUserSchool(schoolId,schoolName,regionCode,regionName,name)
                .compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getAvatar(String id) {
        return Networks.getInstance().getUserApi()
                .getAvatar(id).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getAppUser() {
        return Networks.getInstance().getUserApi()
                .getAppUser().compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> loginOut() {
        return Networks.getInstance().getUserApi()
                .loginOut().compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> sign() {
        return Networks.getInstance().getUserApi()
                .sign().compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> checkTodaySign() {
        return Networks.getInstance().getUserApi()
                .checkTodaySign().compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> dailySign() {
        return Networks.getInstance().getUserApi()
                .dailySign().compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> invite() {
        return Networks.getInstance().getUserApi()
                .invite().compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> orderhistory(String start, String limit, String account
            , String role) {
        return Networks.getInstance().getUserApi()
                .orderhistory(start,limit,account,role).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> studentcomplain(String reason, String details, String id) {
        return Networks.getInstance().getUserApi()
                .studentcomplain(reason,details,id).compose(RxSchedulers.<ResponseBody>io_main());
    }
}
