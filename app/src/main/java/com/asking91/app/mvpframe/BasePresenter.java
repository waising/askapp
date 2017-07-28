package com.asking91.app.mvpframe;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.mvpframe.rx.RxManager;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.util.CommonUtil;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 *
 */
public abstract class BasePresenter<M, V> {
    public Context mContext;
    public M mModel;
    public V mView;
    public RxManager mRxManager = new RxManager();

    public void setVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
    }


    public void onDestroy() {
        mRxManager.clear();
    }

    public void baseReqStr(Observable<ResponseBody> mObservable, final ApiRequestListener mListener) {
        mRxManager.add(mObservable
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        try {
                            String result = obj.string();
                            mListener.onResultSuccess(result);
                        } catch (Exception e) {
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mListener.onResultFail();
                        String msg = HttpCodeConstant.getErrorMsg(CommonUtil.getRequestEntity(throwable));
                        if (!TextUtils.isEmpty(msg)) {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                })

        );
    }
}
