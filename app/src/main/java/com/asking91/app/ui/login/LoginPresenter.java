
package com.asking91.app.ui.login;

import android.content.Context;
import android.widget.Toast;

import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.JLog;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *
 */
public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void ssoLogin(Context context, String userName, String pwd) {
        mRxManager.add(mModel.ssoLogin(context, userName, pwd)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object obj) {
                        JLog.logi("REQUEST==", obj.toString());
                        mView.onLoginSuccess(obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }

    public void getNIMLogin(final Context context,String accid, final ApiRequestListener mListener) {
        mRxManager.add(mModel.getNIMLogin(accid)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        try{
                            String resStr = body.string();
                            mListener.onResultSuccess(resStr);
                        }catch (Exception e){
                            Toast.makeText(context ,"网易token获取失败", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

}
