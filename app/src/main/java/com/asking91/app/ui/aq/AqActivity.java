package com.asking91.app.ui.aq;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class AqActivity extends BaseFrameActivity<AqPresenter, AqModel> {

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.aq_tel)
    TextView aqTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aq);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, getString(R.string.aq));

        if(getUserConstant().getUserEntity()!=null)
            aqTel.setText(getUserConstant().getUserEntity().getUserName().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
    }


    public void onEventMainThread(AppEventType event) {//强制更新
        if(event.type == AppEventType.LOGIN_OUT){
            //更改完密码 退出此页面
            //退出登录
            this.finish();
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @OnClick({R.id.change_pwd,R.id.change_tel})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.change_pwd:
                CommonUtil.openAuthActivity(this,ResetPwdActivity.class);
                break;
            case R.id.change_tel:
                showShortToast("此功能正在完善。。");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
