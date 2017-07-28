package com.asking91.app.ui.oto;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxiao on 2016/11/11.
 */

public class OtoAskModel implements BaseModel {

    public Observable<ResponseBody> otoLogin(String telephone){
        return Networks.getInstance().getOtoApi().otoLogin(telephone)
                .compose(RxSchedulers.<ResponseBody>io_main());
    };

//    public Observable<ResponseBody> otoImg(String telephone,int askMoney, String action, String subjectCatalog, MultipartBody.Part part){
//        return Networks.getInstance().getOtoApi().otoImg(telephone, action, subjectCatalog, askMoney, part)
//                .compose(RxSchedulers.<ResponseBody>io_main());
//    };

    public Observable<ResponseBody> otoImg(String telephone, String action, String text, int askMoney, String imgUrlString, String subjectCatalog,
                                           int score, int askCoin, String suggest, int bind,int linkFlag){
        return Networks.getInstance().getOtoApi().otoImg(telephone, action, text, askMoney, imgUrlString, subjectCatalog, score, askCoin, suggest, bind,linkFlag)
                .compose(RxSchedulers.<ResponseBody>io_main());
    };

//    public Observable<ResponseBody> otoGetToken(String telephone){
//        return Networks.getInstance().getOtoApi().otoGetToken(telephone)
//                .compose(RxSchedulers.<ResponseBody>io_main());
//    };

    public Observable<ResponseBody> qiniutoken(){
        return Networks.getInstance().getOtoApi().qiniutoken()
                .compose(RxSchedulers.<ResponseBody>io_main());
    };

    public Observable<ResponseBody> getNIMLogin(String accid) {
        return Networks.getInstance().getUserApi()
                .getNIMLogin(accid).compose(RxSchedulers.<ResponseBody>io_main());
    }


    public Observable<ResponseBody> orderbuild(Map<String, RequestBody> params) {
        return Networks.getInstance().getUserApi()
                .orderbuild(params).compose(RxSchedulers.<ResponseBody>io_main());
    }

    public Observable<ResponseBody> studentinfo(String userName) {
        return Networks.getInstance().getUserApi()
                .studentinfo(userName).compose(RxSchedulers.<ResponseBody>io_main());
    }

    public Observable<ResponseBody> nimtoken(String accid) {
        return Networks.getInstance().getUserApi()
                .getNIMLogin(accid).compose(RxSchedulers.<ResponseBody>io_main());
    }

    public Observable<ResponseBody> orderstate(String id) {
        return Networks.getInstance().getUserApi()
                .orderstate(id).compose(RxSchedulers.<ResponseBody>io_main());
    }

    public Observable<ResponseBody> ordercancel(String id) {
        return Networks.getInstance().getUserApi()
                .ordercancel(id).compose(RxSchedulers.<ResponseBody>io_main());
    }

    public Observable<ResponseBody> studentfavor(String userName,String account) {
        return Networks.getInstance().getUserApi()
                .studentfavor(userName,account).compose(RxSchedulers.<ResponseBody>io_main());
    }

    public Observable<ResponseBody> studentUnFavor(String userName,String account) {
        return Networks.getInstance().getUserApi()
                .studentUnFavor(userName,account).compose(RxSchedulers.<ResponseBody>io_main());
    }


    public Observable<ResponseBody> orderevaluate(String id,String reward,String star,String suggest) {
        return Networks.getInstance().getUserApi()
                .orderevaluate(id, reward,star,suggest).compose(RxSchedulers.<ResponseBody>io_main());
    }

    public Observable<ResponseBody> orderfirstorder(String id) {
        return Networks.getInstance().getUserApi()
                .orderfirstorder(id).compose(RxSchedulers.<ResponseBody>io_main());
    }

    public Observable<ResponseBody> ordercheckbill(String id) {
        return Networks.getInstance().getUserApi()
                .ordercheckbill(id).compose(RxSchedulers.<ResponseBody>io_main());
    }


}
