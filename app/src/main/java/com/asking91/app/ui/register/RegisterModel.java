package com.asking91.app.ui.register;

import android.content.Context;

import com.asking91.app.api.Networks;
import com.asking91.app.entity.user.RegisterEntity;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import rx.Observable;

/**
 * Created by wxiao on 2016/10/25.
 */

public class RegisterModel implements RegisterContract.Model {
    @Override
    public Observable<Object> getYZM(Context context, String mobile) {
        return Networks.getInstance().getUserApi().getYZM(mobile)
                .compose(RxSchedulers.<Object>io_main());
    }

    @Override
    public Observable<Object> register(Context context, String mobile, String passWord, String verifyCode,String inviteCode) {
        RegisterEntity entity = new RegisterEntity();
        entity.setMobile(mobile);
        entity.setPassWord(passWord);
        entity.setVerifyCode(verifyCode);
        entity.setInviteCode(inviteCode);
//        entity.setSex(1);
//        return Networks.getInstance().getUserApi().register(entity)
//                .compose(RxSchedulers.<Object>io_main());
        return Networks.getInstance().getUserApi().register(mobile, passWord, verifyCode,inviteCode)
                .compose(RxSchedulers.<Object>io_main());
    }
}
