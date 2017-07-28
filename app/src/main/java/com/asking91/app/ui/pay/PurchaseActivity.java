package com.asking91.app.ui.pay;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.asking91.app.R;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.BaseView;
import com.asking91.app.mvpframe.base.BaseFrameActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 课程购买
 * Created by wxwang on 2016/11/30.
 */
public class PurchaseActivity extends BaseFrameActivity<PayPresenter, PayModel> implements BaseView {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        ButterKnife.bind(this);

    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, "课程购买");
    }

    /**
     * 购买课程
     *
     * @param v
     */
    @OnClick({R.id.btn_purchase})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_purchase://购买课程
                Bundle bundle = getIntent().getExtras();
                bundle.putString("commodityId", this.getIntent().getStringExtra("gradeId"));
                bundle.putString("price", getIntent().getStringExtra("price"));
                bundle.putString("className", getIntent().getStringExtra("className"));
                bundle.putString("versionName", "【" +this.getIntent().getStringExtra("versionName")+ "】");
                bundle.putString("timeLimit", this.getIntent().getStringExtra("timeLimit"));
                bundle.putString("favorableStart",this.getIntent().getStringExtra("favorableStart"));
                bundle.putString("favorableEnd",this.getIntent().getStringExtra("favorableEnd"));
                bundle.putInt("fromWhere", Constants.ConfirmOrder.synchronous_course);
                openActivity(PayConfrimActivity.class, bundle);
                break;
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }
}
