package com.asking91.app.ui.onlineqa;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxiao on 2016/11/8.
 */

public class OnlineQAAskModel implements BaseModel {
    public Observable<ResponseBody> onSubmitPicSuccess(MultipartBody.Part part){
        return Networks.getInstance().getOnlineQAApi().onlineQASubmitPic(part)
                .compose(RxSchedulers.<ResponseBody>io_main());
    };

    public Observable<ResponseBody> onSubmit(String km, String caifu, String title, String description){
        return Networks.getInstance().getOnlineQAApi().onlineQASubmit(km, caifu, title, description)
                .compose(RxSchedulers.<ResponseBody>io_main());
    };

    public Observable<ResponseBody> onSubmit(String km, String levelId, String caifu, String title, String description){
        return Networks.getInstance().getOnlineQAApi().onlineQASubmit(km,levelId, caifu, title, description)
                .compose(RxSchedulers.<ResponseBody>io_main());
    };

}
