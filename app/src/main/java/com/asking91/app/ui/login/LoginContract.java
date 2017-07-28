
package com.asking91.app.ui.login;

import android.content.Context;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 *
 */

public interface LoginContract {

    interface Model extends BaseModel {
        Observable<Object> ssoLogin(Context context, String userName, String pwd);
        Observable<ResponseBody> getNIMLogin(String accid);
    }

    interface View extends BaseView {
        void onLoginSuccess(Object obj);
        void onSuccess(ResponseBody body);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        /**登录*/
        public abstract void ssoLogin(Context context, String userName, String pwd);
    }



}
