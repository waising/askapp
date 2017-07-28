package com.asking91.app.ui.resetpass;

import android.content.Context;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import rx.Observable;

/**
 * Created by wxiao on 2016/10/27.
 */

public class ResetPassModel implements ResetPassContract.Model {
    @Override
    public Observable<Object> getYZMRestPass(Context context, String mobile) {
        return Networks.getInstance().getUserApi().getResetPassYZM(mobile).compose(RxSchedulers.<Object>io_main());
    }

    @Override
    public Observable<Object> resetPass(String mobile, String password, String verifyCode) {
        return Networks.getInstance().getUserApi().resetPass(mobile, password, verifyCode).compose(RxSchedulers.io_main());
    }
}
