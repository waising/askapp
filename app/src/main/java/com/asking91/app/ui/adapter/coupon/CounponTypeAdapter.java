package com.asking91.app.ui.adapter.coupon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.juniorTohigh.GoodsCoupon;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by linbin on 2017/6/16.
 */

public class CounponTypeAdapter extends RecyclerView.Adapter<CounponTypeAdapter.CommViewHolder> {


    private MyItemClickListener mItemClickListener;
    private List<String> mDatas;


    private ArrayList<GoodsCoupon> mCouponDatas;
    private Context mContext;

    public int mPosition = 0;

    public CounponTypeAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<String> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void setDataModel(ArrayList<GoodsCoupon> datas) {
        this.mCouponDatas = datas;
    }


    public String getItem(int position) {
        return mDatas.get(position);
    }

    public GoodsCoupon getDataModel(int position) {
        return mCouponDatas.get(position);
    }

    @Override
    public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_coupon_type, parent, false), mItemClickListener);
    }

    @Override
    public void onBindViewHolder(CommViewHolder holder, int position) {
        final String name = mDatas.get(position);
        holder.tvCouponName.setText(name);
        if (mPosition == position) {
            holder.llCoupon.setSelected(true);
        } else {
            holder.llCoupon.setSelected(false);
        }

    }

    public interface MyItemClickListener {
        void onItemClick(View view, int postion, ArrayList<GoodsCoupon> datas);
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class CommViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_coupon_name)
        TextView tvCouponName;


        @BindView(R.id.ll_coupon)
        LinearLayout llCoupon;
        private MyItemClickListener mListener;

        public CommViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getPosition(), mCouponDatas);
            }
        }
    }
}
