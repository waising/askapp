package com.asking91.app.ui.widget.camera.ui;

import android.os.Bundle;
import android.text.TextUtils;

import com.asking91.app.common.BaseActivity;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;
import com.asking91.app.mvpframe.util.TUtil;

/**
 * Created by jswang on 2017/2/22.
 */

public abstract class BaseFragmentActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity implements BaseView {
    public P mPresenter;
    public M mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.mContext = this;
            mPresenter.setVM(this, mModel);
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        String msg = HttpCodeConstant.getErrorMsg(requestEntity);
        if (!TextUtils.isEmpty(msg)) {
            showShortToast(msg);
        }
    }
}
