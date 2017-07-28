package com.asking91.app.ui.oto;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.oto.OtoRecord;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.camera.utils.ParamHelper;
import com.asking91.app.util.DateUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;


/**
 * Created by wxiao on 2016/11/25.
 * 答疑结束
 */

public class OtoEvaluateActivity extends BaseFrameActivity<OtoAskPresent, OtoAskModel> implements OtoAskPresent.View {

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.money)
    TextView money;

    @BindView(R.id.balance)
    TextView balance;

    @BindView(R.id.user_img_iv)
    ImageView userImgIv;

    @BindView(R.id.teacherName)
    TextView teacherName;

    @BindView(R.id.cb_favor)
    CheckBox cb_favor;

    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_askmoney)
    TextView tv_askmoney;

    String orderId;
    String teaName;
    String teaAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oto_evaluate);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);

        HashMap<String, Object> mParams = ParamHelper.acceptParams(OtoLearningActivity.class.getName());
        orderId = (String) mParams.get("orderId");
        teaName = (String) mParams.get("teaName");
        teaAvatar = (String) mParams.get("teaAvatar");
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, "答疑结束");
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.ordercheckbill(this, orderId, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                initUserInfo(res);
            }

            @Override
            public void onResultFail() {
                Toast.makeText(OtoEvaluateActivity.this, "订单结算失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUserInfo(String res) {
        try {
            OtoRecord e = JSON.parseObject(res, OtoRecord.class);
            if (e.bill != null) {
                money.setText(e.bill.price);
            }
            if (e.student != null) {
                balance.setText(e.student.integral+"");
            }
            if (e.time != null) {
                time.setText(DateUtil.getTimeExpend(e.time.startTime, e.time.endTime));
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        setToolbar(toolBar, "评价详情");

        teacherName.setText(teaName);
        teaAvatar = getIntent().getStringExtra("teaAvatar");
        if (!TextUtils.isEmpty(teaAvatar)) {
            BitmapUtil.displayUserImage(this, teaAvatar, userImgIv);
        }

        cb_favor.setChecked(false);

        tv_askmoney.setVisibility(View.VISIBLE);
        tv_askmoney.setText(1 + "枚");
        tv_content.setVisibility(View.VISIBLE);
        tv_content.setText("");
    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
    }

    @Override
    public void onRequestEnd() {
    }

    @Override
    public void onSuccess(String methodName, ResponseBody baseRsqEntity) {
    }

    @Override
    public void onSuccess(String methodName, String string) {
    }
}
