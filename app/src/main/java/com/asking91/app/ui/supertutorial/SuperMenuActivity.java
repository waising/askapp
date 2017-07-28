package com.asking91.app.ui.supertutorial;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.ui.supertutorial.buy.classify.SuperClassifyActivity;
import com.asking91.app.ui.supertutorial.selected.RoundReviewActivity;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 已购买课程页面
 * Created by jswang on 2017/2/27.
 */

public class SuperMenuActivity extends SwipeBackActivity {
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.iv_class)
    View ivClass;
    @BindView(R.id.iv_exercises)
    View ivExercises;
    @BindView(R.id.iv_exam)//精学图标
            View ivExam;
    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;//展开控件
    @BindView(R.id.iv_round_one)//一轮复习
            ImageView ivRoundOne;
    @BindView(R.id.iv_round_two)//二轮复习
            ImageView ivRoundTwo;

    private String action;
    private String verName;
    /**
     * 一轮复习
     */
    public static final int FROM_ROUND_ONE = 0;
    /**
     * 二轮复习
     */
    public static final int FROM_ROUND_TWO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supertutorial_menu);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        action = getIntent().getStringExtra("action");
        verName = getIntent().getStringExtra("verName");
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, verName);

        //高考精学
        if ("M3".equals(action) || "P3".equals(action)) {
            ivExam.setBackgroundResource(R.mipmap.supertutorial_exam_tip);
        } else if ("M2".equals(action) || "P2".equals(action)) {
            //中考精学
            ivExam.setBackgroundResource(R.mipmap.supertutorial_exam_zk_tip);
        }
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @OnClick({R.id.iv_class, R.id.iv_exercises, R.id.iv_exam, R.id.iv_round_one, R.id.iv_round_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_class://超级课堂
                turnRunBuy(0);
                break;
            case R.id.iv_exercises://演练大冲关
                turnRunBuy(1);
                break;
            case R.id.iv_exam://中高考精学
                view.setSelected(!view.isSelected());
                if (view.isSelected()) {
                    expandableLayout.expand();
                } else {
                    expandableLayout.collapse();
                }

                break;
            case R.id.iv_round_one://一轮复习
                turnRound(FROM_ROUND_ONE);
                break;
            case R.id.iv_round_two://二轮复习
                turnRound(FROM_ROUND_TWO);
                break;
        }
    }

    /**
     * 超级课堂和演练大冲关
     *
     * @param currIndex
     */

    /**
     * 0-超级课堂  1-演练大冲关
     *
     * @param showType
     */
    private void turnRunBuy(int showType) {
        Bundle bundle = new Bundle();
        bundle.putInt("showType", showType);
        bundle.putString("action", action);
        bundle.putString("verName", verName);
        //if (getUserConstant().isUserDataPerfect()) {
            openActivity(SuperClassifyActivity.class, bundle);
//        } else {
//            bundle.putString("openCameraActivity", SuperClassifyActivity.class.getName());
//            openCameraActivity(MineSchoolInfoActivity.class, bundle);
//        }
    }


    /**
     * 一轮,二轮复习页
     */
    private void turnRound(int fromWhere) {
        Bundle bundle = new Bundle();
        bundle.putString("action", action);
        bundle.putString("verName", verName);
        bundle.putInt("fromWhere", fromWhere);
        //if (getUserConstant().isUserDataPerfect()) {//是否填写学校信息
            openActivity(RoundReviewActivity.class, bundle);
//        } else {//未填写，跳转到填写学校页
//            bundle.putString("openCameraActivity", ExamReviewActivity.class.getName());
//            openCameraActivity(MineSchoolInfoActivity.class, bundle);
//        }
    }


}
