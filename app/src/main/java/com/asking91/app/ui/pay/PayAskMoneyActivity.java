package com.asking91.app.ui.pay;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.pay.AskMoney;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.pay.PayAskMoneyAdapter;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CompareAskMoney;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 阿思币充值
 * Created by wxwang on 2016/11/29.
 */
public class PayAskMoneyActivity extends BaseFrameActivity<PayPresenter, PayModel> {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.aks_money_rv)
    RecyclerView mAskMoneyRv;




    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;

    List<AskMoney> mDatas;
    PayAskMoneyAdapter payAskMoneyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ask_money);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.pay_ask_money));

        mAskMoneyRv.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public void initData() {
        super.initData();
        mDatas = new ArrayList<>();
        payAskMoneyAdapter = new PayAskMoneyAdapter(this, mDatas);
        mAskMoneyRv.setAdapter(payAskMoneyAdapter);
    }

    @Override
    public void initLoad() {
        super.initLoad();
//获取ask币套餐列表,
        mPresenter.getAskMoney(0, 100, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                JSONObject jsonRes = JSON.parseObject(res);
                String listStr = jsonRes.getString("list");
                ArrayList<AskMoney> list = (ArrayList<AskMoney>) JSON.parseArray(listStr, AskMoney.class);
                if (list != null && list.size() > 0) {
                    Collections.sort(list, new CompareAskMoney());
                    mDatas.clear();
                    mDatas.addAll(list);
                    payAskMoneyAdapter.notifyDataSetChanged();
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                } else {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                }
            }

            @Override
            public void onResultFail() {

            }
        });
    }


    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }


    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }


    /**
     * 跳转到确认支付界面
     */
    public void jumpToConfirmPay(String payMoney, String coinNum, String commodityId) {
        Bundle bundle = new Bundle();
        bundle.putString("payMoney", payMoney);
        bundle.putString("coinNum", coinNum);
        bundle.putInt("fromWhere", Constants.ConfirmOrder.ask_coin);
        bundle.putString("commodityId", commodityId);
        openActivity(PayConfrimActivity.class, bundle);
    }

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.ASK_COIN_SUCCESS://
                finish();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
