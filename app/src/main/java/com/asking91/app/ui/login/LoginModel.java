package com.asking91.app.ui.login;

import android.content.Context;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 *
 */
public class LoginModel implements LoginContract.Model {

    @Override
    public Observable<Object> ssoLogin(Context context, String userName, String pwd) {
        return Networks.getInstance().getUserApi().ssoLogin(userName,pwd)
                .compose(RxSchedulers.<Object>io_main());
    }

    @Override
    public Observable<ResponseBody> getNIMLogin(String accid) {
        return Networks.getInstance().getUserApi()
                .getNIMLogin(accid).compose(RxSchedulers.<ResponseBody>io_main());
    }

}
