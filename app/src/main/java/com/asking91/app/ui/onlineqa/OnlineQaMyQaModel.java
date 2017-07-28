package com.asking91.app.ui.onlineqa;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxiao on 2016/11/8.
 */

public class OnlineQaMyQaModel implements BaseModel {
    public Observable<ResponseBody> onMyAsk(int start, String limit, String query){
        return Networks.getInstance().getOnlineQAApi().onlineQAMyAsk(start, limit, query)
                .compose(RxSchedulers.<ResponseBody>io_main());
    };

    public Observable<ResponseBody> onMyAnswer(String type, int start, String limit, String query){
        return Networks.getInstance().getOnlineQAApi().onlineQAMyAnswer(type, start, limit, query)
                .compose(RxSchedulers.<ResponseBody>io_main());
    };

    public Observable<ResponseBody> onlineQaAgainAsk(String id, String anserId, String html){
        return Networks.getInstance().getOnlineQAApi().onlineQaAgainAsk(id, anserId, html)
                .compose(RxSchedulers.<ResponseBody>io_main());
    };
    public Observable<ResponseBody> onlineQaAgainAnswer(String id, String anserId, String html){
        return Networks.getInstance().getOnlineQAApi().onlineQaAgainAnswer(id, anserId, html)
                .compose(RxSchedulers.<ResponseBody>io_main());
    };
    public Observable<ResponseBody> onlineQATalk(String id){
        return Networks.getInstance().getOnlineQAApi().onlineQATalk(id).compose(RxSchedulers.<ResponseBody>io_main());
    };

}
