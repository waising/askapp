package com.asking91.app.ui.oto;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.global.OnlineQAConstant;
import com.asking91.app.global.OtoConstant;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.DeviceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wxiao on 2016/11/11.
 * 一对一答疑--查看详情
 */

public class OtoDetailActivity extends SwipeBackActivity {


    @BindView(R.id.content)
    RelativeLayout content;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.pic_take)
    AskSimpleDraweeView picTake;
    @BindView(R.id.tBtn1)
    RadioButton tBtn1;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.tBtn11)
    RadioButton tBtn11;
    @BindView(R.id.t3)
    TextView t3;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.title_more_menu)
    TextView titleMoreMenu;
    @BindView(R.id.rbtn1)
    RadioButton rbtn1;

    private String subject = "M2", grade = "07";
    private int askMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oto_detail);
        ((RelativeLayout) findViewById(R.id.content)).addView(LayoutInflater.from(this).inflate(R.layout.layout_toolbar_oto_waiting, null, false));
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        super.initView();
        userNameTv.setText("查看详情");
        titleMoreMenu.setText("重新提问");
        titleMoreMenu.setVisibility(View.GONE);
        picTake.setImageBitmap(CommonUtil.decodeBitmapWithOrientationMax(getIntent().getStringExtra("picTakePath"),
                DeviceUtil.getScreenWidth(mthis), DeviceUtil.getScreenHeight(mthis)));
        subject = getIntent().getStringExtra("subject");
        askMoney = getIntent().getIntExtra("askMoney", 0);
        grade = getIntent().getStringExtra("grade");
        rbtn1.setText(askMoney+"");
        if(subject.indexOf("M")!=-1){
            tBtn1.setText("数学");
        }else if(subject.indexOf("P")!=-1){
            tBtn1.setText("物理");
        }
        for(int i=0;i<OtoConstant.versionTvValues.length;i++){
            if(grade.equals(OtoConstant.versionTvValues[i])){
                tBtn11.setText(OnlineQAConstant.versionTv[i]);
            }
        }
    }


    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @OnClick({R.id.title_more_menu, R.id.back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_more_menu:

                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
