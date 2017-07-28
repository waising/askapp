package com.asking91.app.ui.pay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.pay.SynchronousCourseEntity;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.supertutorial.selected.ClassPurchaseFragment;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.indicator.TabPageIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


/**
 * 同步课程购买
 * Created by wxwang on 2016/11/30.
 */
public class SynchronousCoursePurchaseActivity extends BaseFrameActivity<SuperSelectPresenterImpl, SuperSelectModelImpl> {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;


    @BindView(R.id.viewPager)
    ViewPager viewPager;


    @BindView(R.id.indicator)
    TabPageIndicator indicator;

    ArrayList<SynchronousCourseEntity.NodelistBeanX> tabList = new ArrayList<>();

    private String productName;

    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronous_course_purchase);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.synchronous_course));
        indicator.setResourceid(R.layout.layout_synchronous_course);//tab页布局
        final CommAdapter mAdapter = new CommAdapter(SynchronousCoursePurchaseActivity.this.getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        indicator.setViewPager(viewPager);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        mPresenter.SynchronousCourse(this, "KC03", new ApiRequestListener<String>() {//获取同步课堂数据
            @Override
            public void onResultSuccess(String res) {
                ArrayList<SynchronousCourseEntity> list = (ArrayList<SynchronousCourseEntity>) JSON.parseArray(res, SynchronousCourseEntity.class);
                if (list != null && list.size() > 0) {//有数据
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    SynchronousCourseEntity synchronousCourseEntity = list.get(0);
                    if (synchronousCourseEntity != null) {
                        tabList.addAll(synchronousCourseEntity.getNodelist());
                        productName = synchronousCourseEntity.getValue().getProductName();
                        mAdapter.notifyDataSetChanged();
                        indicator.notifyDataSetChanged();
                    }
                } else {
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                }
            }

            @Override
            public void onResultFail() {
                super.onResultFail();
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        });

    }

    @Override
    public void initData() {
        super.initData();


    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void initListener() {
        super.initListener();

    }


    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.SYNCHRONOUS_PAY_SUCCESS://
                finish();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }


    class CommAdapter extends FragmentStatePagerAdapter {

        public CommAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ClassPurchaseFragment.newInstance(tabList.get(position).getNodelist(), productName, tabList.get(position).getValue().getProductName());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position).getValue().getProductName();
        }

        @Override
        public int getCount() {
            return tabList.size();
        }


    }
}
