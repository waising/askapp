package com.asking91.app.ui.widget.camera.ui;

import android.os.Bundle;
import android.text.TextUtils;

import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;
import com.asking91.app.mvpframe.util.TUtil;
import com.asking91.app.ui.widget.camera.comm.AppManager;

import de.greenrobot.event.EventBus;

/**
 * Created by jswang on 2017/2/22.
 */

public abstract class BaseEvenActivity<P extends BasePresenter, M extends BaseModel> extends SwipeBackActivity implements BaseView {
    public P mPresenter;
    public M mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
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
        EventBus.getDefault().unregister(this);
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        String msg = HttpCodeConstant.getErrorMsg(requestEntity);
        if (!TextUtils.isEmpty(msg)) {
            showShortToast(msg);
        }
//        showShortToast(msg);
//        JLog.e("REQUEST_ERROR ==== ", msg);
    }
}
