package com.asking91.app.ui.adapter.mine;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.user.IntegralEntity;
import com.asking91.app.util.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zqshen on 2016/12/09.
 */

public class MyAskMoneyRecordAdapter extends RecyclerView.Adapter<MyAskMoneyRecordAdapter.ViewHolder> {

    List<IntegralEntity> list;
    private Context mContext;


    public MyAskMoneyRecordAdapter(Context context) {
         this.mContext = context;
    }

    public void setData(List<IntegralEntity> integralEntity) {
          this.list = integralEntity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAskMoneyRecordAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_askmoney_record,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        IntegralEntity integralEntity = list.get(position);
        if(integralEntity!=null) {
            //1:-  2:+
            String type = "+";
            if(integralEntity.getType()== 1){
                type = "-";
                holder.mtvMoney.setTextColor(Color.RED);
            }

            holder.mtvMoney.setText(type.concat(String.valueOf(integralEntity.getIntegral())));
            holder.mtvDate.setText(DateUtil.getDateToString(integralEntity.getCreateTime()));
            holder.mtvTitle.setText(integralEntity.getRemark());
        }
    }

    @Override
    public int getItemCount() {
         return list!=null ?list.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mtvTitle;
        @BindView(R.id.tv_date)
        TextView mtvDate;
        @BindView(R.id.tv_money)
        TextView mtvMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
