package com.asking91.app.ui.register;

import android.content.Context;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import rx.Observable;

/**
 * Created by wxiao on 2016/10/25.
 */

public interface RegisterContract {
    /**注册*/

    interface Model extends BaseModel {
        Observable<Object> getYZM(Context context, String mobile);
        Observable<Object> register(Context context, String mobile, String passWord, String verifyCode,String inviteCode);
    }

    interface View extends BaseView {
        void onYZMSuccess(Object obj);
        void onRegisterSuccess(Object obj);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        /**获取验证码*/
        public abstract void getYZM(Context context, String mobile);
        /**注册*/
        public abstract void register(Context context, String mobile, String passWord, String verifyCode,String inviteCode);
    }
}
