package com.asking91.app.ui.supertutorial.buy.classify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.pay.SynchronousCourseEntity;
import com.asking91.app.ui.supertutorial.SuperSelectContract;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.supertutorial.SupergeTreeActivity;
import com.asking91.app.ui.supertutorial.selected.SuperTutorialSelectFragment;
import com.asking91.app.ui.widget.camera.ui.BaseEvenActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.indicator.TabPageIndicatorJuniorTohigh;
import com.asking91.app.util.SharePreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

import static com.asking91.app.ui.widget.camera.utils.AppEventType.SUPER_CLASSIFY_SELECT;


/**
 * 版本选择页面
 * create  by linbin
 */

public class SuperClassifyActivity extends BaseEvenActivity<SuperSelectPresenterImpl, SuperSelectModelImpl> implements SuperSelectContract.View {
    @BindView(R.id.toolBar)
    Toolbar toolBar;


    private int showType;
    private String action;
    private String verName;



    private SynchronousCourseEntity.NodelistBeanX item;


    private CommAdapter commonAdapter;

    @BindView(R.id.indicator)
    TabPageIndicatorJuniorTohigh indicator;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supertutorial_classify);
        ButterKnife.bind(this);

        showType = getIntent().getIntExtra("showType", 0);
        action = getIntent().getStringExtra("action");
        verName = getIntent().getStringExtra("verName");

        Bundle bundleArg = getIntent().getExtras();
        if (bundleArg != null) {
            item = (SynchronousCourseEntity.NodelistBeanX) bundleArg.getSerializable("item");
            if (item != null) {
                commonAdapter = new CommAdapter(getSupportFragmentManager());
                viewPager.setAdapter(commonAdapter);
                indicator.setResourceid(R.layout.layout_indicator_tab_view1);
                indicator.setViewPager(viewPager);
            }

        }
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, verName);

    }


    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        showShortToast("暂无题目");
        finish();
    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onGetSuperSelectSuccess(ResponseBody obj) {

    }

    @Override
    public void onFreeStudySuccess(ResponseBody obj) {


    }

    @Override
    public void onResultSuccess(String method, ResponseBody res) {
    }

    @Override
    public void onFreeStudyVersionSuccess(ResponseBody obj) {

    }

    @Override
    public void onRefreshData(ResponseBody res) {

    }

    @Override
    public void onLoadMoreData(ResponseBody res) {

    }

    @Override
    public void onResultSuccess(String method, ResponseBody res, int position) {

    }


    class CommAdapter extends FragmentStatePagerAdapter {

        public CommAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SuperTutorialSelectFragment.newInstance(item.getNodelist().get(position).getValue().getCourseList());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return item.getNodelist().get(position).getValue().getProductName();
        }

        @Override
        public int getCount() {
            return item.getNodelist().size();
        }


    }

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case SUPER_CLASSIFY_SELECT://选中哪一项
                SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX.CourseListBean item = (SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX.CourseListBean) event.values[0];
                if (item != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("gradeId", item.getCommodityId());
                    bundle.putInt("showType", showType);
                    bundle.putString("action", action);
                    bundle.putString("verName", verName);
                    bundle.putString("courseTypeName", item.getCourseTypeName());
                    bundle.putString("versionName", String.format("%s-%s", item.getCourseTypeName(), item.getCourseName()));//教材版本名
                    bundle.putString("courseName",item.getCourseName());
                    bundle.putString("price",item.getCoursePrice()+"");
                    bundle.putString("imageUrl",item.getCourseImgUrl());
                    bundle.putString("timeLimit",item.getTimeLimit());
                    bundle.putString("favorableStart",item.getFavorableStartStr());
                    bundle.putString("favorableEnd",item.getFavorableEndStr());

                    //保存选取信息
                    String data = String.format("%s,%s,%s,%s,%s,%s", item.getCommodityId(), showType, action, verName, item.getCourseTypeName(), item.getCourseName());
                    SharePreferencesHelper.getInstance(getApplicationContext()).putString("super_test_data", data);

                    openResultActivity(SupergeTreeActivity.class, bundle);

                }
                break;

        }
    }

}
