package com.asking91.app.ui.onlineqa;

import com.asking91.app.Asking91;
import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;
import com.asking91.app.util.SharePreferencesHelper;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
* Created by wxiao on 2016/10/31
*/

public class OnlineQAModelImpl implements OnlineQAContract.Model{

    @Override
    public Observable<ResponseBody> onlineQAonlineQA(String type, String state, int start, int limit, String km, String levelId) {
        return Networks.getInstance().getOnlineQAApi().onlineQA(type, state, start, limit, km, levelId)
                .compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> onlineQAonlineQA(String type, String state, int start, int limit, String km ) {
        return Networks.getInstance().getOnlineQAApi().onlineQA(type, state, start, limit, km)
                .compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> onlineQAonlineQA(String type, String state, int start, int limit) {
        return Networks.getInstance().getOnlineQAApi().onlineQA(type, state, start, limit )
                .compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> onlineQAonlineQA(String type, int start, int limit) {
        return Networks.getInstance().getOnlineQAApi().onlineQA(type,  start, limit)
                .compose(RxSchedulers.<ResponseBody>io_main());
    }

    public Observable<ResponseBody> onlineQaAgainAsk(String id, String anserId, String html){
        return Networks.getInstance().getOnlineQAApi().onlineQaAgainAsk(id, anserId, html)
                .compose(RxSchedulers.<ResponseBody>io_main());
    };
    public Observable<ResponseBody> onlineQaAgainAnswer(String id, String anserId, String html){
        return Networks.getInstance().getOnlineQAApi().onlineQaAgainAnswer(id, anserId, html)
                .compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> onlineQAAnser(String id, String content) {
        return Networks.getInstance().getOnlineQAApi().onlineQAAnser(id, content)
                .compose(RxSchedulers.<ResponseBody>io_main());
    }

    public Observable<ResponseBody> otoGetToken(){
        return Networks.getInstance().getOtoApi().otoGetToken(SharePreferencesHelper.getInstance(Asking91.applicationContext).getString(com.asking91.app.global.Constants.Login.UserName, ""))
                .compose(RxSchedulers.<ResponseBody>io_main());
    };

    public Observable<ResponseBody> onSubmitPicSuccess(MultipartBody.Part part){
        return Networks.getInstance().getOnlineQAApi().onlineQASubmitPic(part)
                .compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> onlineQaAdoptAnswer(String id, String anserId) {
        return Networks.getInstance().getOnlineQAApi().onlineQaAdoptAnswer(id, anserId)
                .compose(RxSchedulers.<ResponseBody>io_main());
    }

    ;

//    @Override
//    public Observable<Object> onlineQAonlineQA(JSONObject object) {
//        return Networks.getInstance().getOnlineQAApi().onlineQA(object)
//                .compose(RxSchedulers.<Object>io_main());
//    }
}