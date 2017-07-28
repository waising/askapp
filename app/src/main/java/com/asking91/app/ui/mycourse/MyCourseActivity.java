package com.asking91.app.ui.mycourse;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.mycourse.CourseInfo;
import com.asking91.app.entity.mycourse.MyCourse;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.mycourse.MyCourseAdapter;
import com.asking91.app.ui.juniorhigh.JuniorToHighDetailActivity;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.RecycleViewDivider;
import com.asking91.app.util.CommonUtil;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * 我的课程界面
 * create by linbin
 */
public class MyCourseActivity extends BaseFrameActivity<SuperSelectPresenterImpl, SuperSelectModelImpl> implements MyCourseClickListner {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout swipeLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    String token;
    int start = 0, limit = 5;
    MyCourseAdapter myCourseAdapter;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    /**
     * list
     */
    private List<MyCourse> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycourse);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        token = getUserConstant().getToken();
        if (token == null) {//是否登录
            CommonUtil.openActivity(this, LoginActivity.class, null);
        }
    }

    @Override
    public void initLoad() {
        super.initLoad();
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        getDataNow();
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, "我的课程");//标题栏
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, UIUtil.dip2px(this, 8), ContextCompat.getColor(this, R.color.colorTest_f8)));
        //取消全屏滑动
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setPtrHandler(new PtrDefaultHandler2() {//刷新控件
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                //　上拉加载更多
                start += limit;
                getDataNow();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                // 上拉刷新
                start = 0;
                list.clear();
                swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);
                getDataNow();

            }
        });
    }


    /**
     * 页面数据请求
     */
    private void getDataNow() {
        mPresenter.myCourse(this, start, limit, new ApiRequestListener<String>() {//获取我的课堂数据
            @Override
            public void onResultSuccess(String res) {
                swipeLayout.refreshComplete(); // 关闭刷新控件动画
                ArrayList<MyCourse> listCourse = (ArrayList<MyCourse>) JSON.parseArray(res, MyCourse.class);
                if (listCourse != null && listCourse.size() > 0) {//
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    list.addAll(listCourse); // 实现分页加载更多所需要的容器数据
                    refshAdapt();

                } else {
                    if (start > 0) {//上拉加载更多的情况下，没数据的情况下，加载到最后一页情况
                        // 后台返回的 total 值是 double 类型，不能用 int 整型去接，麻烦，暂时先用这个方法判断
/*                if(map.get("total")!=null){
                    Integer total = Integer.valueOf((String) map.get("total"));
                    if(total<=start){
                        showShortToast("没有更多数据了");
                    }*/
                        swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH);
                        showShortToast(R.string.no_more_data);
                    } else {//没数据情况
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    }

                }


            }


            @Override
            public void onResultFail() {
                super.onResultFail();
                if (swipeLayout != null) {
                    swipeLayout.refreshComplete();
                }
                start = 0;
                swipeLayout.setMode(PtrFrameLayout.Mode.REFRESH);
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                showShortToast(R.string.no_more_data);

            }
        });
    }

    /**
     * 填充页面数据
     */
    public void refshAdapt() {
        if (myCourseAdapter == null) {
            myCourseAdapter = new MyCourseAdapter(this, list);
            myCourseAdapter.setClickListner(this);
            recyclerView.setAdapter(myCourseAdapter);
        } else {
            myCourseAdapter.setData(list);
            myCourseAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestEnd() {
    }

    @Override
    public void click(int purchaseState, final String progress, final int listPositon, final String commodityId) {

        mPresenter.myCoursePdfAndUrl(this, commodityId, new ApiRequestListener<String>() {//获取我的课堂数据
            @Override
            public void onResultSuccess(String res) {
                CourseInfo courseInfo = JSON.parseObject(res, CourseInfo.class);
                if (courseInfo != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("purchaseState", Constants.JuniorToHighDetail.puchased);//购买状态
                    bundle.putString("progress", progress);
                    bundle.putInt("listPositon", listPositon);//list中的position
                    bundle.putString("commodityId", commodityId);
                    bundle.putSerializable("urlAndPdf", (Serializable) courseInfo.getCourseDataArray());//pdf和url
                    openActivity(JuniorToHighDetailActivity.class, bundle);
                }


            }
        });


    }


    /**
     * 修改笔记界面成功保存回来时,重新刷新界面
     */
//    @Override
//    protected void onResume() {
//        String str = SharePreferencesHelper.getInstance(this).getString("meNote");//修改笔记是否成功
//        if (str.equals("noteSaveSuccess")) {
//            if (list.size() > 0) {
//                myCourseAdapter.notifyDataSetChanged();
//                list.clear();
//            }
//            mPresenter.presenterMyNote(token, start, limit);
//        }
//        super.onResume();
//    }


}
