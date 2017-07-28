package com.asking91.app.ui.adapter.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.user.BuyRecordsEntity;
import com.asking91.app.ui.mine.mybuyrecords.MyBuyRecordsActivity;
import com.asking91.app.ui.mine.mybuyrecords.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 购买记录adapter
 */

public class MyBuyRecordsAdapter extends SwipeMenuAdapter<MyBuyRecordsAdapter.ZQViewHolder> {

    List<BuyRecordsEntity> list;
    int subjectCatalog;
    Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<BuyRecordsEntity> list, int subjectCatalog) {
        this.list = list;
        this.subjectCatalog = subjectCatalog;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        return LayoutInflater.from(mContext).inflate(R.layout.item_mybuyrecords, parent, false);
    }

    @Override
    public ZQViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new ZQViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(ZQViewHolder holder, int position) {
        if (list != null && list.size() > 0) {
            final BuyRecordsEntity buyRecordsEntity = list.get(position);
            holder.setOnItemClickListener(mOnItemClickListener);
            final String payType = buyRecordsEntity.getPayType(); // 支付类型

            // 我的课程&& 阿思币充值 我的服务
            String orderName = buyRecordsEntity.getOrderName();
            List<BuyRecordsEntity.ProductsBean> products = buyRecordsEntity.getProducts();
            String strDetail = "";
            if (products != null && products.size() > 0) {
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getRecharge() == null) {
                        // recharge 为空是课程、服务购买
                        String versionName = products.get(i).getVersionName(); // 教材版本
                        String months = products.get(i).getMonths(); // 月份
                        String courseContent = products.get(i).getCourseContent(); // 年级
                        String commodityType = products.get(i).getCommodityType();
                        if (!TextUtils.isEmpty(commodityType)) {
                            // commodityType 非空是课程、服务购买
                            if (!TextUtils.isEmpty(versionName) && !TextUtils.isEmpty(months) && !TextUtils.isEmpty(courseContent)) {
                                if (commodityType.equals("2")) {
                                    strDetail = strDetail + "课程购买 -" + "【" + versionName + " - " + months + "个月" + " - " + courseContent + "】" + "\n";
                                }
                                if (commodityType.equals("1")) {
                                    strDetail = strDetail + "服务购买 -" + "【" + versionName + " - " + months + "个月" + " - " + courseContent + "】" + "\n";
                                }
                            }
                        } else {
                            // commodityType 为空是知识点购买
                            if (!TextUtils.isEmpty(versionName) && !TextUtils.isEmpty(months) && !TextUtils.isEmpty(courseContent)) {
                                strDetail = strDetail + "知识点购买 -" + "【" + versionName + " - " + months + "个月" + " - " + courseContent + "】" + "\n";
                            }
                        }
                    } else {
                        // recharge 非空是阿思币充值
                        if (products.size() == 1) {
                            orderName = mContext.getString(R.string.pay_ask_money); // 设置标题为阿思币充值
                        }
                        if (products.get(i).getRecharge() != null) {
                            int value = products.get(i).getRecharge().getValue();
                            strDetail = strDetail + "充值了 " + value + " 个阿思币" + "\n";
                        }
                    }
                }
                if (!TextUtils.isEmpty(strDetail) && strDetail.length() > 2) {
                    strDetail = strDetail.substring(0, strDetail.length() - 1);
                }

                if (orderName != null) {
                    holder.mTvOrderName.setText(orderName); // 设置标题
                } else {
                    holder.mTvOrderName.setText("");
                }
                holder.tvOrderContent.setText(strDetail);
            }

            String totalFee = buyRecordsEntity.getTotalFee(); // 支付金额
            String state = buyRecordsEntity.getState(); // 支付状态
           // String modifyDateStr = buyRecordsEntity.getModifyDateStr(); // 生成订单的时间
            String createDateStr = buyRecordsEntity.getCreateDateStr(); // 下单的时间
            final String id = buyRecordsEntity.getId();
            // 订单总金额（单位是分，要除以100）
            if (!TextUtils.isEmpty(totalFee)) {
                try {
                    double d = (new Double(totalFee)).doubleValue();
                    double price = d / 100;
                    holder.mTvMoney.setText("¥ " + String.valueOf(price));
                } catch (NumberFormatException e) {
                }
            } else {
                holder.mTvMoney.setText("");
            }
/*            // 下单时间（先判断 modifyDate_str 的值是否为空，为空的话那么使用createDate_str ）
            if (modifyDateStr != null && !TextUtils.isEmpty(modifyDateStr)) {
                holder.mTvDate.setText(modifyDateStr);
            } else*/
            if (createDateStr != null) {
                holder.mTvDate.setText(createDateStr); // 下单时间
            } else {
                holder.mTvDate.setText("");
            }
            //订单状态: 0-待支付   1-待支付 , 2-已支付 , 3-订单失效(取消)  4-订单删除
            if (state != null) {
                holder.mTvUser.setTextColor(holder.colorAccent);
                if (state.equals("0") || state.equals("1")) {
                    holder.mTvOrderState.setText(R.string.to_be_pay);
                    holder.mTvUser.setText(R.string.pay_now);
                    holder.mTvUser.setOnClickListener(new View.OnClickListener() {//跳转到支付页面
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putString("orderId", id);
                            bundle.putString("payType", payType);

                            ((MyBuyRecordsActivity) mContext).openPayActivity(bundle);
                        }
                    });
                }
                if (state.equals("2")) {
                    // 已支付
                    holder.mTvOrderState.setText(R.string.be_paid);
                    // 0-支付宝  1- 银联  2-微信
                    if (!TextUtils.isEmpty(payType)) {
                        switch (payType) {//支付方式
                            // 支付宝
                            case "0":
                                holder.mTvUser.setText(R.string.alipay);
                                break;
                            // 银联
                            case "1":
                                holder.mTvUser.setText(R.string.unionpay);
                                break;
                            // 微信
                            case "2":
                                holder.mTvUser.setText(R.string.wechatpay);
                                break;
                        }
                    }
                }
                if (state.equals("3")) {
                    // 订单失效(取消)
                    holder.mTvOrderState.setText(R.string.order_failed);
                    holder.mTvUser.setText("");
                }
                if (state.equals("4")) {
                    // 订单删除
                    holder.mTvOrderState.setText(R.string.order_del);
                    holder.mTvUser.setText("");
                }
            } else {
                holder.mTvOrderState.setText("");
                holder.mTvUser.setText("");
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ZQViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_date)
        TextView mTvDate; // 日期
        @BindView(R.id.tv_order_name)
        TextView mTvOrderName; // 订单标题
        @BindView(R.id.tv_order_content)
        TextView tvOrderContent; // 订单内容
        @BindView(R.id.tv_order_state)
        TextView mTvOrderState; // 订单状态
        @BindView(R.id.tv_money)
        TextView mTvMoney; // 支付金额
        @BindView(R.id.tv_user)
        TextView mTvUser; // 支付方式
        @BindView(R.id.tv_expiration_date)
        TextView mTvExpirationDate; // 到期时间
        @BindColor(R.color.colorAccent)
        int colorAccent;

        public ZQViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }

        OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

    }
}
