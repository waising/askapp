package com.asking91.app.ui.adapter.pay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.asking91.app.R;
import com.asking91.app.entity.pay.PayClassEntity;
import com.asking91.app.ui.pay.PayServerActivity;
import com.asking91.app.ui.pay.PaySupActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/10/27.
 */

public class PayClassAdapter extends RecyclerView.Adapter<PayClassAdapter.ViewHolder> {

    private List<PayClassEntity> mDatas;
    private Context mContext;

    public void setPrePosition(int mPrePosition) {
        this.mPrePosition = mPrePosition;
    }

    private int mPrePosition = -1;

    private RadioButton preBtn;
    public PayClassAdapter(Context context, List<PayClassEntity> datas){
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pay_class_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PayClassEntity payClassEntity = mDatas.get(position);

        holder.mClassBtn.setText(payClassEntity.getCourseContent());
        holder.id = payClassEntity.getId();
        if(mPrePosition == -1) {
            holder.mClassBtn.setChecked(position == 0);
            if(position==0)
                preBtn = holder.mClassBtn;
        }

        holder.mClassBtn.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPrePosition==-1)mPrePosition=0;
                if(mPrePosition!=position) {
                    notifyItemChanged(mPrePosition);
                    preBtn.setChecked(false);
                }

                holder.mClassBtn.setChecked(true);
                mPrePosition = position;
                preBtn = holder.mClassBtn;

                if(mContext instanceof PaySupActivity){
                    ((PaySupActivity) mContext).setData(payClassEntity.getId(),payClassEntity.getCommodityPrice());
                }else{
                    ((PayServerActivity) mContext).setData(payClassEntity.getId(),payClassEntity.getCommodityPrice());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.class_btn)
        RadioButton mClassBtn;

        public String id;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
