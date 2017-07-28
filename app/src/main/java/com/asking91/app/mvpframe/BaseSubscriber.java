package com.asking91.app.mvpframe;

import android.content.Context;

import com.asking91.app.R;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.NetworkUtils;

import rx.functions.Action0;

/**
 * Created by wxwang on 2016/12/19.
 */
public abstract class BaseSubscriber implements Action0 {

    private Context mContext;

    public BaseSubscriber(Context context){
        this.mContext = context;
    }

    @Override
    public void call() {
        if(!NetworkUtils.isNetworkAvailable(mContext)){
            CommonUtil.Toast(mContext,mContext.getString(R.string.network_error));
            return;
        }
    }
}
