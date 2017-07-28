package com.asking91.app.ui.pay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.pay.CourseDetailEntity;
import com.asking91.app.entity.pay.CourseEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.PayConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.indicator.TabPageIndicatorJuniorTohigh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;

import static com.asking91.app.R.id.pay_now;
import static com.asking91.app.R.id.tv_pay_money;

/**
 * 课程购买界面
 * Created by wxwang on 2016/11/30.
 */
public class CourseBuyActivity extends BaseFrameActivity<PayPresenter, PayModel>
        implements PayContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;


    @BindView(R.id.viewPager)
    ViewPager viewPager;

    ArrayList<CourseEntity> tabList = new ArrayList<>();
    @BindView(R.id.indicator)
    TabPageIndicatorJuniorTohigh indicator;
    @BindView(tv_pay_money)//支付价格
            TextView tvPayMoney;

    @BindView(pay_now)//
            TextView tvPayNow;
    CourseDetailEntity courseDetailEntity;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @BindView(R.id.ll_bottom)//
            LinearLayout llBottom;

    /**
     * 保存点击过的数据
     */
    HashMap<String, HashMap<String, CourseDetailEntity>> chooseMap = new HashMap<>();
    /**
     * 每个科目类型选中的月份
     */
    HashMap<String, String> chooseMonthMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_buy);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    private CommAdapter mAdapter;

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.pay_super_class));
        indicator.setResourceid(R.layout.layout_pay_up);//tab页布局
        tvPayMoney.setTextColor(this.getResources().getColor(R.color.dark_yellow));
        tvPayMoney.setTextSize(19);
        tvPayNow.setBackgroundResource(R.drawable.btn_red_bg);
        mAdapter = new CommAdapter(CourseBuyActivity.this.getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        indicator.setViewPager(viewPager);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String chooseMonth = findChooseMonth(tabList.get(position).getPackageTypeName());
                CourseDetailEntity courseDetailEntity = findChooseData(tabList.get(position).getPackageTypeName(), chooseMonth);
                setPrice(courseDetailEntity);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        mPresenter.findClassList("TC-TBKT", new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                ArrayList<CourseEntity> list = (ArrayList<CourseEntity>) JSON.parseArray(res, CourseEntity.class);
                if (list != null && list.size() > 0) {//有数据
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    tabList.addAll(list);
                    initChooseMonth(tabList);
                    initChooseMap(tabList);
                    mAdapter.notifyDataSetChanged();
                    indicator.notifyDataSetChanged();
                } else {
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);

                }

            }

            @Override
            public void onResultFail() {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        });

    }

    private void initChooseMap(ArrayList<CourseEntity> tabList) {
        for (CourseEntity courseEntity :
                tabList) {
            HashMap<String, CourseDetailEntity> map = new HashMap<>();
            CourseDetailEntity courseDetailEntity1 = new CourseDetailEntity();
            CourseDetailEntity courseDetailEntity2 = new CourseDetailEntity();
            // 添加元素
            map.put("12", courseDetailEntity1);
            map.put("6", courseDetailEntity2);
            // 添加到大集合
            chooseMap.put(courseEntity.getPackageTypeName(), map);
        }
    }

    /**
     * 初始化选中的月份
     *
     * @param tabList
     */
    private void initChooseMonth(ArrayList<CourseEntity> tabList) {
        for (CourseEntity courseEntity :
                tabList) {
            chooseMonthMap.put(courseEntity.getPackageTypeName(), "");
        }

    }

    /**
     * 设置选中对象
     *
     * @param packName
     * @param month
     * @param courseDetailEntity
     */
    private void setChooseData(String packName, String month, CourseDetailEntity courseDetailEntity) {
        if (chooseMap != null && chooseMap.size() > 0) {
            for (Map.Entry<String, HashMap<String, CourseDetailEntity>> entry : chooseMap.entrySet()) {
                if (entry.getKey().equals(packName)) {
                    HashMap<String, CourseDetailEntity> keyvalue = chooseMap.get(packName);
                    keyvalue.put(month, courseDetailEntity);
                }
            }
        }

    }

    /**
     * 根据课程和月份查找选中的data
     *
     * @param packName
     * @param month
     */
    private CourseDetailEntity findChooseData(String packName, String month) {
        if (chooseMap != null && chooseMap.size() > 0) {
            for (Map.Entry<String, HashMap<String, CourseDetailEntity>> entry : chooseMap.entrySet()) {
                if (entry.getKey().equals(packName)) {
                    HashMap<String, CourseDetailEntity> keyvalue = chooseMap.get(packName);
                    for (Map.Entry<String, CourseDetailEntity> entity : keyvalue.entrySet()) {

                        if (entity.getKey().equals(month)) {
                            return entity.getValue();
                        }
                    }
                }

            }
        }
        return null;
    }


    @Override
    public void initListener() {
        super.initListener();

    }


    @Override
    public void onSuccess(int type, ResponseBody body) {
        switch (type) {

        }
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
            return PaySupLearnTimeFragment.newInstance(tabList.get(position).getPackageTypeId(), tabList.get(position).getPackageTypeName());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position).getPackageTypeName();
        }

        @Override
        public int getCount() {
            return tabList.size();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.PRICE://设置价格
                CourseDetailEntity courseDetail = (CourseDetailEntity) event.values[0];
                if (courseDetail != null) {
                    try {
                        courseDetailEntity = (CourseDetailEntity) courseDetail.deepCopy();
                        String month = (String) event.values[1];
                        String packName = (String) event.values[2];
                        setChooseData(packName, month, courseDetailEntity);
                        setPrice(courseDetailEntity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case AppEventType.CHOOSE_MONTH://选中的月份
                String packName = (String) event.values[0];
                String month = (String) event.values[1];
                setChooseMonth(packName, month);
                CourseDetailEntity courseDetailEntity = findChooseData(packName, month);
                setPrice(courseDetailEntity);
                break;

        }
    }

    private void setPrice(CourseDetailEntity courseDetailEntity) {
        if (courseDetailEntity != null) {
            if (!TextUtils.isEmpty(courseDetailEntity.getPackagePrice())) {
                tvPayMoney.setText(this.getString(R.string.money_format, PayConstant.formatPrice(courseDetailEntity.getPackagePrice())));
            } else {
                tvPayMoney.setText("");
            }
        }
    }

    /**
     * 找到对应的课程。替换选中的月份
     *
     * @param packName
     * @param month
     */
    private void setChooseMonth(String packName, String month) {
        if (chooseMonthMap != null && chooseMonthMap.size() > 0) {
            for (Map.Entry<String, String> entry : chooseMonthMap.entrySet()) {
                if (entry.getKey().equals(packName)) {
                    chooseMonthMap.put(packName, month);
                }
            }
        }
    }


    private String findChooseMonth(String packName) {
        if (chooseMonthMap != null && chooseMonthMap.size() > 0) {
            for (Map.Entry<String, String> entry : chooseMonthMap.entrySet()) {
                if (entry.getKey().equals(packName)) {
                    return entry.getValue();
                }
            }
        }
        return "";
    }


    @OnClick({R.id.pay_now})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_now://支付，跳转到确认订单页
                Bundle bundle = new Bundle();
                bundle.putString("imageUrl", courseDetailEntity.getImgUrl());
                bundle.putString("className", courseDetailEntity.getPackageTypeName() + "-" + courseDetailEntity.getPackageName());//初中数学这种
                bundle.putString("price", courseDetailEntity.getPackagePrice());
                bundle.putString("commodityId", courseDetailEntity.getCommodityId());
                bundle.putString("timeLimit", courseDetailEntity.getTimeLimit() + "个月");
                bundle.putInt("fromWhere", Constants.ConfirmOrder.course_buy);
                openActivity(PayConfrimActivity.class, bundle);
                break;
        }
    }

}
