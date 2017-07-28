package com.asking91.app.ui.adapter.pay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.pay.AskMoney;
import com.asking91.app.global.PayConstant;
import com.asking91.app.ui.pay.PayAskMoneyActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 阿思币充值的adapter
 * Created by wxwang on 2016/10/27.
 */

public class PayAskMoneyAdapter extends RecyclerView.Adapter<PayAskMoneyAdapter.ViewHolder> {

    private List<AskMoney> mDatas;
    private Context mContext;


    public PayAskMoneyAdapter(Context context, List<AskMoney> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pay_ask_money_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AskMoney askMoneyEntity = mDatas.get(position);

        //金币数量和rmb
        holder.mAskMoneyTv.setText(String.valueOf(askMoneyEntity.getValue()));//金币数量
        holder.mMoneyTv.setText(PayConstant.formatPrice(String.valueOf(askMoneyEntity.getPrice())));//价格

        holder.frameLayout.setOnClickListener(new View.OnClickListener() {//跳转
            @Override
            public void onClick(View v) {

                ((PayAskMoneyActivity) mContext).jumpToConfirmPay(PayConstant.formatPrice(String.valueOf(askMoneyEntity.getPrice())), String.valueOf(askMoneyEntity.getValue()), askMoneyEntity.getCommodityId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ask_money_fl)
        FrameLayout frameLayout;

        @BindView(R.id.ask_money_tv)
        TextView mAskMoneyTv;//金币数量
        @BindView(R.id.money_tv)
        TextView mMoneyTv;//价格

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
