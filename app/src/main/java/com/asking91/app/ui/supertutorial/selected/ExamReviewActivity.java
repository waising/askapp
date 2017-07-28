package com.asking91.app.ui.supertutorial.selected;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.entity.supertutorial.ExamReviewTree;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 一轮复习，二轮复习界面，主要的activity
 * Created by jswang on 2017/2/28.
 */

public class ExamReviewActivity extends SwipeBackActivity {

    /**
     * M2-初中数学（8）  P2-初中物理（6）  M3-高中数学（9.5）  P3-高中物理（7）
     */
    private String action;

    private ArrayList<Fragment> fragments;
    @BindView(R.id.toolBar)//标题
            Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supertutorial_buy);
        ButterKnife.bind(this);
        setFrameId(R.id.fragment);


    }

    @Override
    public void initData() {
        super.initData();
        action = getIntent().getStringExtra("action");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ExamReviewTree e = (ExamReviewTree) bundle.getSerializable("e");
            String verName = bundle.getString("verName");
            fragments = new ArrayList<>();
            fragments.add(ExamReviewFirstFragment.newInstance(e, action, verName));
            fragments.add(ExamReviewSecondFragment.newInstance(e, action, verName));
            String fromWhere = getIntent().getStringExtra("fromWhere");
            if (fromWhere.equals("oneRound")) {
                setToolbar(toolBar, "一轮复习");//标题栏
                setCurrFragment(fragments.get(0));
                toFragment(fragments.get(0));//显示第一轮复习

            } else {
                setToolbar(toolBar, "二轮复习");//标题栏
                setCurrFragment(fragments.get(1));
                toFragment(fragments.get(1));//显示第二轮复习
            }

        }
        setSwipeBackEnable(false);
    }


}
