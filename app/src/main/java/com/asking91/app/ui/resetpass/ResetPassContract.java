package com.asking91.app.ui.resetpass;

import android.content.Context;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import rx.Observable;

/**
 * Created by wxiao on 2016/10/27.
 */

public interface ResetPassContract {

    interface Model extends BaseModel {
        Observable<Object> getYZMRestPass(Context context, String mobile);
        Observable<Object> resetPass(String mobile, String password, String verifyCode);
    }

    interface View extends BaseView{
        void onYZMSuccess(Object obj);
        void onResetPassSuccess(Object obj);
    }

    abstract class  Presenter extends BasePresenter<Model, View>{
        /**获取验证码*/
        public abstract void getYZMRestPass(Context context, String mobile);
        /**忘记密码*/
        public abstract void resetPass(String mobile, String password, String verifyCode);

    }
}
