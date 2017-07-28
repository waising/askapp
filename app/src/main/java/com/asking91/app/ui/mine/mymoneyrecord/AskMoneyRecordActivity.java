package com.asking91.app.ui.mine.mymoneyrecord;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.user.IntegralEntity;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.mine.MyAskMoneyRecordAdapter;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.mine.mytestrecord.TestRecordContract;
import com.asking91.app.ui.mine.mytestrecord.TestRecordModel;
import com.asking91.app.ui.mine.mytestrecord.TestRecordPresenter;
import com.asking91.app.ui.pay.PayAskMoneyActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.ui.widget.RecycleViewDivider;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.ResUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;

/**
 * 阿思币消费记录
 */
public class AskMoneyRecordActivity extends BaseFrameActivity<TestRecordPresenter, TestRecordModel> implements TestRecordContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.mtoolbar)
    Toolbar mToolBar;
    @BindView(R.id.tv_balance)
    TextView mtvBalance; // 余额
    @BindView(R.id.tv_recharge)
    TextView mtvRecharge; // 充值

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    int start = 0, limit = 10;
    MyAskMoneyRecordAdapter adapter;
    List<IntegralEntity> mList;
    /**
     * 首次进入
     */
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askmoney_record);
        ButterKnife.bind(this);

        //取消全屏滑动
        //getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);
        // setSwipeBackEnable(false);

    }

    @Override
    public void initLoad() {
        super.initLoad();
        mSwipeLayout.post(new Runnable() {
            @Override
            public void run() {
                isFirst = true;
                mSwipeLayout.autoRefresh();

            }
        });
    }

    public void getData() {
        mPresenter.presenterAskMoneyRecords(start, limit);
    }

    @Override
    public void initData() {
        super.initData();
        if (!getUserConstant().isTokenLogin()) {
            CommonUtil.openActivity(this, LoginActivity.class, null);
        }
        //阿思币
        mtvBalance.setText(String.valueOf(getUserConstant().getUserEntity().getIntegral()));
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, getString(R.string.ask_money_record));
        mCollapsingToolbar.setTitle(getResources().getString(R.string.ask_money_record));

        LinearLayoutManager mgr = new LinearLayoutManager(this);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mgr);
        //设置recyclerView的行间距
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, ResUtil.dp2px(this, 1), ContextCompat.getColor(this, R.color.color_da)));//设置分割线
        //recyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        //recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。

        adapter = new MyAskMoneyRecordAdapter(this);
        recyclerView.setAdapter(adapter);
        mList = new ArrayList<>();
        adapter.setData(mList);
    }


    @Override
    public void initListener() {
        super.initListener();

        mSwipeLayout.setViewPager(recyclerView);
        mSwipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                start += limit;
                getData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                start = 0;
                getData();
            }
        });

        mtvRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.openAuthActivity(mthis, PayAskMoneyActivity.class);
            }
        });
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        if (mSwipeLayout != null) mSwipeLayout.refreshComplete();
    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestEnd() {
        if (mSwipeLayout != null) mSwipeLayout.refreshComplete();
    }

    public void refsh() {
        //   adapter.setData(list, subjectCatalog);
        //  adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestSuccess(int requestType, ResponseBody responseBody) {

    }

    @Override
    public void onRequestSuccess(ResponseBody responseBody) {

    }

    @Override
    public void onRefreshData(ResponseBody responseBody) {

        try {
            Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
            if (map != null && map.get("integralLogs") != null) {
                List<IntegralEntity> list = CommonUtil.parseDataToList(map.get("integralLogs"), new TypeToken<List<IntegralEntity>>() {
                });

                if (list != null && list.size() > 0) {
                    mList.clear();
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();
                    if (isFirst) {//首次
                        requestUserInfo(getUserConstant().getUserName());
                        isFirst = false;
                    }
                } else {
                    mSwipeLayout.refreshComplete();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            mSwipeLayout.refreshComplete();
        }
    }

    @Override
    public void onLoadMoreData(ResponseBody responseBody) {
        try {
            //解析json
            Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
            if (map != null && map.get("integralLogs") != null) {
                List<IntegralEntity> list = CommonUtil.parseDataToList(map.get("integralLogs"), new TypeToken<List<IntegralEntity>>() {
                });
                if (list != null && list.size() > 0) {
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();

                } else {
                    mSwipeLayout.refreshComplete();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            mSwipeLayout.refreshComplete();
        }
    }


    @Override
    public void onInternetError(String methodName) {

    }

    /**
     * 请求用户信息
     */
    private void requestUserInfo(String userName) {
        mPresenter.studentinfo(this, userName, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String resStr) {
                JSONObject resObject = JSON.parseObject(resStr);
                double integral = resObject.getDouble("integral");
                if (integral != getUserConstant().getUserEntity().getIntegral()) {
                    getUserConstant().getUserEntity().setIntegral(integral);
                    getUserConstant().saveUserData(new Gson().toJson(getUserConstant().getUserEntity()));
                    mtvBalance.setText(String.valueOf(getUserConstant().getUserEntity().getIntegral()));
                    EventBus.getDefault().post(new AppEventType(AppEventType.ASK_COIN_SUCCESS));
                }

            }

            @Override
            public void onResultFail() {

            }
        });
    }


}
