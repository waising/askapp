package com.asking91.app.mvpframe.base;

import android.os.Bundle;
import android.text.TextUtils;

import com.asking91.app.common.BaseFragment;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;
import com.asking91.app.mvpframe.util.TUtil;


/**
 * Created by quan on 16/9/20.
 */

public abstract  class BaseFrameFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment implements BaseView {

    public P mPresenter;

    public M mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.mContext = this.getContext();
            mPresenter.setVM(this, mModel);
        }

    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        String msg = HttpCodeConstant.getErrorMsg(requestEntity);
        if (!TextUtils.isEmpty(msg)){
            showShortToast(msg);
        }
//        showShortToast(msg);
//        JLog.e("REQUEST_ERROR ==== ", msg);
    }

}
