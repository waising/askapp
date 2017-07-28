package com.asking91.app.ui.juniorhigh;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.juniorTohigh.JuniorToHighEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.adapter.juniorhigh.JuniorToHighAdapter;
import com.asking91.app.ui.adapter.juniorhigh.JuniorToHighTopPagerAdapter;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.camera.utils.AppEventType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 初升高Fragment
 * Created by linbin on 2016/10/26.
 */

public class JuniorToHighFragment extends BaseFrameFragment<JuniorToHighPresenter, JuniorToHighModel> {

    ViewPager viewPager;

    private List<String> mMathDataList;
    private List<String> mMathBigDataList;
    private List<String> mPhysicsDataList;
    private List<String> mPhysicsBigDataList;


    private JuniorToHighTopPagerAdapter mExamplePagerAdapter;

    private JuniorToHighAdapter juniorToHighAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    private String courseType;


    /**
     * 是否可见状态
     */
    private boolean isVisible;

    /**
     * 标志位，View已经初始化完成。
     * 2016/04/29
     * 用isAdded()属性代替
     * 2016/05/03
     * isPrepared还是准一些,isAdded有可能出现onCreateView没走完但是isAdded了
     */
    private boolean isPrepared;
    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;
    long startTime;


    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout swipeLayout;
    int start = 0, limit = 5;

    public static final int FIRST_LOAD = 0;
    public static final int REFRESH = 1;
    public static final int LOAD_MORE = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_junior_to_high);//设置View
        ButterKnife.bind(this, getContentView());
        EventBus.getDefault().register(this);
    }


    public static JuniorToHighFragment newInstance(String courseType) {
        JuniorToHighFragment fragment = new JuniorToHighFragment();
        Bundle bundle = new Bundle();
        bundle.putString("courseType", courseType);
        fragment.setArguments(bundle);
        return fragment;
    }


    /**
     * 请求list
     */
    private void requestList(final String courseType, final int loadType) {
        if (loadType == FIRST_LOAD) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        }

        mPresenter.JuniorToHighList(courseType, start, limit, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                swipeLayout.refreshComplete(); // 关闭刷新控件动画
                //  startTime = System.currentTimeMillis();  //開始時間
                JSONObject jsonRes = JSON.parseObject(res);
                String listStr = jsonRes.getString("list");
                boolean completeFlag = jsonRes.getBoolean("completeFlag");//已录完，未录完标记
                ArrayList<JuniorToHighEntity> list = (ArrayList<JuniorToHighEntity>) JSON.parseArray(listStr, JuniorToHighEntity.class);
                if (list != null && list.size() > 0) {//有数据
                    showData(courseType, list, completeFlag, loadType);
                } else {
                    showData(courseType, new ArrayList<JuniorToHighEntity>(), false, loadType);

                    if (start > 0) {
                        swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH);
                        Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onResultFail() {
                if (loadType == FIRST_LOAD) {
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                }
                if (swipeLayout != null) {
                    swipeLayout.refreshComplete();
                }
                start = 0;
                swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH);
             //   showShortToast(getString(R.string.no_more_data));
            }

            @Override
            public void onResultFail(RequestEntity resultEntity) {
                if (resultEntity.getCode() == HttpCodeConstant.CONNECT_401) {
                    showShortToast("未登录");
                }
                if (loadType == FIRST_LOAD) {
                    showData(courseType, new ArrayList<JuniorToHighEntity>(), false, loadType);
                }
            }
        });
    }


    /**
     * @param courseType
     * @param list
     * @param notShowFooter
     */

    private void showData(String courseType, ArrayList<JuniorToHighEntity> list, boolean notShowFooter, int loadType) {
        if (loadType == FIRST_LOAD) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }

        juniorToHighAdapter.addDatas(list, loadType);
        setFooter(recyclerView, notShowFooter);

        if (courseType.equals(Constants.JuniorToHigh.math)) {//数学
            mExamplePagerAdapter = new JuniorToHighTopPagerAdapter(mMathDataList, mMathBigDataList, Constants.JuniorToHigh.all_math);
        } else {//物理
            mExamplePagerAdapter = new JuniorToHighTopPagerAdapter(mPhysicsDataList, mPhysicsBigDataList, Constants.JuniorToHigh.all_physic);
        }
        viewPager.setAdapter(mExamplePagerAdapter);

//        long consumingTime = System.currentTimeMillis() - startTime; //消耗時間
//        Log.d("执行时间", consumingTime + "");
    }


    @Override
    public void initData() {
        super.initData();
        isFirstLoad = true;
        isPrepared = true;

    }

    @Override
    public void initView() {
        super.initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        juniorToHighAdapter = new JuniorToHighAdapter(getActivity());
        recyclerView.setAdapter(juniorToHighAdapter);
        setHeader(recyclerView);
    }

    @Override
    public void initListener() {
        super.initListener();

    }

    @Override
    public void initLoad() {
        super.initLoad();
        lazyLoad();
    }

    private void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        Bundle bundle = getArguments();
        if (bundle != null) {
            courseType = bundle.getString("courseType");
            mMathDataList = Arrays.asList(Constants.main_top_pics_junior_to_high_math);
            mMathBigDataList = Arrays.asList(Constants.main_top_big_pics_junior_to_high_math);
            mPhysicsDataList = Arrays.asList(Constants.main_top_pics_junior_to_high_physics);
            mPhysicsBigDataList = Arrays.asList(Constants.main_top_big_pics_junior_to_high_physic);

            requestList(courseType, FIRST_LOAD);
            swipeLayout.setPtrHandler(new PtrDefaultHandler2() {//刷新控件
                @Override
                public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
//                    //　上拉加载更多
                    start += limit;
                    requestList(courseType, LOAD_MORE);
                }

                @Override
                public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                    //下拉拉刷新
                    start = 0;
                    swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);
                    requestList(courseType, REFRESH);
                }
            });

        }


    }

    private ShowDialog showDialog;

    public void setShowDialog(ShowDialog showDialog) {
        this.showDialog = showDialog;
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

//    /**
//     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
//     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
//     *
//     * @param hidden hidden True if the fragment is now hidden, false if it is not
//     *               visible.
//     */
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            isVisible = true;
//            onVisible();
//        } else {
//            isVisible = false;
//            onInvisible();
//        }
//    }

    public interface ShowDialog {
        void show();
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 添加header
     *
     * @param view
     */
    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.item_junior_to_high_header_view, view, false);
        viewPager = (ViewPager) header.findViewById(R.id.view_pager);
        juniorToHighAdapter.setHeaderView(header);
    }

    /**
     * 添加尾部布局
     *
     * @param view 已录完,不显示底部，未录完，要显示底部
     */

    private void setFooter(RecyclerView view, boolean notShowFooter) {
        View footer = LayoutInflater.from(getActivity()).inflate(R.layout.item_junior_to_high_footer_view, view, false);
        if (notShowFooter) {//已录完,
            juniorToHighAdapter.setFooterView(null);
        } else {//未录完,没数据
            juniorToHighAdapter.setFooterView(footer);
        }

    }


    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.SINGLE_PAY_SUCCESS:
            case AppEventType.ALL_PAY_SUCCESS:
                requestList(courseType, FIRST_LOAD);
                break;
            case AppEventType.LOGIN_SUCCESS://登录成功，重新刷新页面
                boolean isRegister = (boolean) event.values[0];
                if (isRegister) {
                    EventBus.getDefault().post(new AppEventType(AppEventType.COUPON_SUCCESS));
                }
                requestList(courseType, FIRST_LOAD);
                break;
            case AppEventType.PROGRESS_SUCCESS://刷新当前item项的进度
                int position = (int) event.values[0];
                JuniorToHighEntity juniorToHighEntity = juniorToHighAdapter.getItemData(position);
                if (juniorToHighEntity != null) {
                    juniorToHighAdapter.notifyDataSetChanged();
                }
                break;

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
