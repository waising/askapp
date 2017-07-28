package com.asking91.app.ui.coupon;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.coupon.CouponList;
import com.asking91.app.entity.coupon.CouponList.ContentBeanX.ContentBean;
import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.global.PayConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.juniorhigh.JuniorToHighModel;
import com.asking91.app.ui.juniorhigh.JuniorToHighPresenter;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.DateUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

;

/**
 * 优惠券列表
 * Created by wxiao on 2016/11/25.
 */

public class CouponListActivity extends BaseFrameActivity<JuniorToHighPresenter, JuniorToHighModel> {


    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    CommAdapter mAdapter;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_list);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
        loadData();
    }

    /**
     * 请求数据
     */
    private void loadData() {
        mPresenter.couponList(0, 100, 1, "3", null, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                JSONObject jsonRes = JSON.parseObject(res);
                String listStr = jsonRes.getString("content");
                ArrayList<ContentBean> list = (ArrayList<ContentBean>) JSON.parseArray(listStr, ContentBean.class);
                if (list != null && list.size() > 0) {//有数据
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    mAdapter.setData(list);
                } else {//没优惠券
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                }
            }

            @Override
            public void onResultFail() {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }

            @Override
            public void onResultFail(RequestEntity resultEntity) {
                if (resultEntity.getCode() == HttpCodeConstant.CONNECT_401) {
                    showShortToast("未登录");
                }
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, "优惠券");
        recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommAdapter(this);
        recycler.setAdapter(mAdapter);

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
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }


    class CommViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_price_num)
        TextView tv_price_num;//多少元现金券
        @BindView(R.id.tv_available_condition)
        TextView tv_available_condition;//满几元可用
        @BindView(R.id.tv_available_type)
        TextView tv_available_type;//限购买初升高衔接课使用
        @BindView(R.id.tv_expiry_date)
        TextView tv_expiry_date;//有效期至
        @BindView(R.id.tv_expiry_day)
        TextView tv_expiry_day;//几天后到期,


        public CommViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CommAdapter extends RecyclerView.Adapter<CommViewHolder> {
        private Context mContext;

        ArrayList<CouponList.ContentBeanX.ContentBean> mList;

        public CommAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_coupon_list, parent, false));
        }

        @Override
        public void onBindViewHolder(final CommViewHolder holder, final int position) {
            final ContentBean item = mList.get(position);
            if (item != null) {

                ContentBean.EventBean event = item.getEvent();
                if (event != null) {
                    ContentBean.EventBean.RuleBean ruleBean = event.getRule();
                    if (ruleBean != null) {
                        holder.tv_price_num.setText(PayConstant.formatPrice(ruleBean.getOff() + ""));
                        holder.tv_available_condition.setText(mContext.getString(R.string.purchase_format, PayConstant.formatPrice(ruleBean.getPurchase() + "")));
                        holder.tv_available_type.setText(event.getRemark());
                        holder.tv_expiry_date.setText(mContext.getString(R.string.closing_date_format, DateUtil.getTime(new Date(ruleBean.getClosingDate()))));
                        int betweendays;
                        try {
                            betweendays = DateUtil.daysBetween(System.currentTimeMillis(), ruleBean.getClosingDate());
                            holder.tv_expiry_day.setText(mContext.getString(R.string.active_time_format, betweendays + 1 + ""));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {//每项的点击事件
                @Override
                public void onClick(View v) {

                }
            });


        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public void setData(ArrayList<CouponList.ContentBeanX.ContentBean> list) {
            mList = list;
            notifyDataSetChanged();
        }


    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
