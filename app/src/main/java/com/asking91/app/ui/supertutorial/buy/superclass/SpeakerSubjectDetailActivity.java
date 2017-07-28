package com.asking91.app.ui.supertutorial.buy.superclass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.asking91.app.R;
import com.asking91.app.common.BaseActivity;
import com.asking91.app.entity.supertutorial.SuperClassComeToSpeak;
import com.asking91.app.ui.widget.indicator.TabPageIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxiao on 2016/12/12.
 * 来讲题-查看解析-变式题的详情页面
 */

public class SpeakerSubjectDetailActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.indicator)
    TabPageIndicator indicator;

    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    CommPagerAdapter mPagerAdapter;


    private List<SuperClassComeToSpeak.SubjectmulsBean> subjectmuls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supertutotial_speaker_detail_version);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, "变式题");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            subjectmuls = (List<SuperClassComeToSpeak.SubjectmulsBean>) bundle.getSerializable("variant_problem_list");
            mPagerAdapter = new CommPagerAdapter(getSupportFragmentManager());
            mViewpager.setAdapter(mPagerAdapter);
            indicator.setResourceid(R.layout.layout_indicator_tab_view1);
            indicator.setViewPager(mViewpager);
        }

    }


    public class CommPagerAdapter extends FragmentStatePagerAdapter {

        public CommPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return subjectmuls.size();
        }

        @Override
        public Fragment getItem(int position) {
            SuperClassComeToSpeak.SubjectmulsBean e = subjectmuls.get(position);
            return SpeakerSubjectDetailFragment.newInstance(position, e);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "变式题" + (position + 1);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }


}
