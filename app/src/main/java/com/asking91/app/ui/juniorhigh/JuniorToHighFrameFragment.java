package com.asking91.app.ui.juniorhigh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.coupon.CouponRegister;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.mycourse.MyCourseActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.dialog.CouponDialog;
import com.asking91.app.ui.widget.indicator.TabPageIndicatorJuniorTohigh;
import com.asking91.app.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * 初升高Fragment外层
 * Created by linbin on 2016/10/26.
 */

public class JuniorToHighFrameFragment extends BaseFrameFragment<JuniorToHighPresenter, JuniorToHighModel> {


    ArrayList<String> tabList = new ArrayList<>();
    @BindView(R.id.indicator)
    TabPageIndicatorJuniorTohigh indicator;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private CouponDialog couponDialog;


    @BindView(R.id.tv_toolbar)
    TextView tvTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_junior_to_high_frame);
        ButterKnife.bind(this, getContentView());
        EventBus.getDefault().register(this);
        tabList.add(getString(R.string.online_dialog1_t1)); // 数学
        tabList.add(getString(R.string.online_dialog1_t2)); // 物理
        indicator.setResourceid(R.layout.layout_indicator_tab_view_junior_to_high);//tab页布局
        CommAdapter mAdapter = new CommAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        indicator.setViewPager(viewPager);
        tvTitle.setText("初升高衔接课");
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
            Fragment f;
            if (position == 0) {//
                f = JuniorToHighFragment.newInstance(Constants.JuniorToHigh.math);
            } else {//
                f = JuniorToHighFragment.newInstance(Constants.JuniorToHigh.physic);
            }
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position);
        }

        @Override
        public int getCount() {
            return tabList.size();
        }
    }

    @Override
    public void initData() {
        super.initData();
    }


    @Override
    public void initListener() {
        super.initListener();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.tv_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right://我的课程
                CommonUtil.openAuthActivity(getActivity(), MyCourseActivity.class);
                break;
        }
    }


    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.COUPON_SUCCESS://注册成功后的登录
                couponRegister();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * 优惠券注册弹窗
     */
    private void couponRegister() {

        mPresenter.couponRegister(new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                List<CouponRegister> list = JSON.parseArray(res, CouponRegister.class);
                if (list != null && list.size() > 0) {
                    if (couponDialog == null) {
                        couponDialog = CouponDialog.newInstance(list.get(0));
                    }
                    couponDialog.show(JuniorToHighFrameFragment.this.getChildFragmentManager(), "");
                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
