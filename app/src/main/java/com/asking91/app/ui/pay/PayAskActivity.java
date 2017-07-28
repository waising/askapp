package com.asking91.app.ui.pay;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 充值界面
 * Created by wxwang on 2016/11/30.
 */
public class PayAskActivity extends SwipeBackActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ask);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.pay));
    }

    @OnClick({R.id.pay_ask_class_btn, R.id.pay_ask_money_btn, R.id.synchronous_course_purchase_btn})
    public void onClick(View view) {
        Class clz = null;
        switch (view.getId()) {
            case R.id.pay_ask_class_btn://课程购买
               // clz = PaySupActivity.class;
                clz = CourseBuyActivity.class;
                break;
            case R.id.pay_ask_money_btn://阿思币充值

                clz = PayAskMoneyActivity.class;
                break;
//            case R.id.pay_ask_server_btn://服务购买
//                clz = PayServerActivity.class;
//                break;
            case R.id.synchronous_course_purchase_btn://同步课程购买

                clz = SynchronousCoursePurchaseActivity.class;

                break;
        }
        //跳转
        openActivity(clz);
    }


    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.SYNCHRONOUS_PAY_SUCCESS://
                finish();
            case AppEventType.ASK_COIN_SUCCESS:
                finish();
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
