package com.asking91.app.ui.supertutorial.selected;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.ui.supertutorial.SuperMenuActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 一轮复习,二轮章节选择界面
 * linbin
 */
public class RoundReviewActivity extends SwipeBackActivity {

    @BindView(R.id.toolBar)//标题
            Toolbar toolBar;

    @BindView(R.id.tv_class_name)//学科
            TextView tvClassName;


    @BindView(R.id.math)//头部理科数学，文科数学切换页
            LinearLayout llMath;


    @BindView(R.id.top_cbox1)
    CheckBox top_cbox1;
    @BindView(R.id.top_cbox2)
    CheckBox top_cbox2;

    /**
     * M2-初中数学（8）  P2-初中物理（6）  M3-高中数学（）  P3-高中物理（7）
     * <p>
     * 高中数据，有两种，高中数学。理科 9。文科9.5
     */
    private String action;
    private String actionType;

    private ArrayList<Fragment> fragments;

    private String verName;//标题名，初中数学，初中物理， 高中数学，高中物理这种


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_review);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setFrameId(R.id.fragment);
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
        action = getIntent().getStringExtra("action");
        if (TextUtils.equals("M2", action)) {//初中数学
            actionType = "8";
        } else if (TextUtils.equals("P2", action)) {//初中物理
            actionType = "6";
        } else if (TextUtils.equals("M3", action)) {//高中数学
          //  actionType = "9";
        } else if (TextUtils.equals("P3", action)) {//高中物理
            actionType = "7";
        }
        String examClass = null;
        verName = getIntent().getStringExtra("verName");
        //高考精学
        if ("M3".equals(action) || "P3".equals(action)) {
            examClass = "高考精学";
        } else if ("M2".equals(action) || "P2".equals(action)) {
            //中考精学
            examClass = "中考精学";
        }

        if (TextUtils.equals("M3", action)) {//高中数学分文科数学，理科数学
            llMath.setVisibility(View.VISIBLE);
            tvClassName.setVisibility(View.GONE);
        } else {
            llMath.setVisibility(View.GONE);
            tvClassName.setVisibility(View.VISIBLE);

        }
        Bundle bundle = getIntent().getExtras();
        int fromWhere = bundle.getInt("fromWhere");
        if (fromWhere == SuperMenuActivity.FROM_ROUND_ONE) {
            setToolbar(toolBar, "一轮复习");//标题栏
            tvClassName.setText(verName + " - " + examClass + " - " + "一轮复习");//学科

        } else {
            setToolbar(toolBar, "二轮复习");//标题栏
            tvClassName.setText(verName + " - " + examClass + " - " + "二轮复习");//学科
        }

        fragments = new ArrayList<>();

        if (TextUtils.equals("M3", action)) {//高中数学分文科数学，理科数学
            fragments.add(BranchFrameFragment.newInstance("9", action, verName, fromWhere));//理科 9
            fragments.add(BranchFrameFragment.newInstance("9.5", action, verName, fromWhere));//。文科9.5
        } else {
            fragments.add(BranchFrameFragment.newInstance(actionType, action, verName, fromWhere));//理科 9
        }
        setCurrFragment(fragments.get(0));
        toFragment(fragments.get(0));

        setSwipeBackEnable(false);
    }

    /**
     * 顶部文理科布局切换
     *
     * @param v
     */
    @OnClick({R.id.top_cbox1, R.id.top_cbox2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_cbox1://理科数学
                if (top_cbox1.isChecked()) {
                    top_cbox2.setChecked(false);
                    top_cbox1.setTextColor(ContextCompat.getColor(mthis, R.color.colorAccent));
                    top_cbox2.setTextColor(ContextCompat.getColor(mthis, R.color.colorText_88));
                    toFragment(fragments.get(0));
                } else {
                    top_cbox1.setChecked(true);
                }
                break;
            case R.id.top_cbox2://文科数学
                if (top_cbox2.isChecked()) {
                    top_cbox1.setChecked(false);
                    top_cbox2.setTextColor(ContextCompat.getColor(mthis, R.color.colorAccent));
                    top_cbox1.setTextColor(ContextCompat.getColor(mthis, R.color.colorText_88));
                    toFragment(fragments.get(1));
                } else {
                    top_cbox2.setChecked(true);
                }
                break;

        }
    }

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
