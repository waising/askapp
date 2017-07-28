package com.asking91.app.ui.supertutorial.selected;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.entity.supertutorial.ExerAskEntity;
import com.asking91.app.ui.widget.indicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 变式题页面
 * Created by jswang on 2017/5/9.
 */

public class SubjectDetailActivity extends SwipeBackActivity {
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.indicator)
    TabPageIndicator indicator;

    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    CommPagerAdapter mPagerAdapter;

    /**
     * 1-一轮复习备高考|3个要求 0-二轮复习题型诊断分析
     */
    private int showType;
    private ExerAskEntity mEntity;

    public List<ExerAskEntity.OptionsBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_subject_detail);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);

        showType = getIntent().getIntExtra("showType", 0);
        mEntity = (ExerAskEntity) getIntent().getSerializableExtra("ExerAskEntity");
        dataList.clear();
        if (showType == 1) {
            dataList.addAll(mEntity.bianst);
        } else {
            dataList.addAll(mEntity.biansts);
        }
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, "变式题");

        mPagerAdapter = new CommPagerAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(mPagerAdapter);
        indicator.setResourceid(R.layout.layout_indicator_tab_view1);
        indicator.setViewPager(mViewpager);
        mPagerAdapter.notifyDataSetChanged();
        indicator.notifyDataSetChanged();
    }

    public class CommPagerAdapter extends FragmentStatePagerAdapter {

        public CommPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Fragment getItem(int position) {
            ExerAskEntity.OptionsBean e = dataList.get(position);
            return SubjectDetailFragment.newInstance(position,e);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "变式题"+(position+1);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
