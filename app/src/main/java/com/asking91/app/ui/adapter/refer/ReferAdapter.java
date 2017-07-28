package com.asking91.app.ui.adapter.refer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.refer.ReferEntity;
import com.asking91.app.ui.refer.ReferDetailActivity;
import com.asking91.app.util.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wxwang on 2016/11/7.
 */
public class ReferAdapter extends RecyclerView.Adapter<ReferAdapter.ViewHolder> {

    private Context mContext;
    List<ReferEntity> mDatas;

    public ReferAdapter(Context context, List<ReferEntity> datas){
        this.mContext = context;
        this.mDatas = datas;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReferAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_refer_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ReferEntity referEntity = mDatas.get(position);
        holder.mTitleTv.setText(referEntity.getTitle());
        holder.mDateTv.setText(referEntity.getCreateDateFmt());
        holder.mIdTv.setText(referEntity.getId());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.refer_title_tv)
        TextView mTitleTv;

        @BindView(R.id.refer_date_tv)
        TextView mDateTv;

        @BindView(R.id.refer_id_tv)
        TextView mIdTv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick({R.id.refer_item_ly,R.id.refer_item_cv,R.id.refer_title_tv})
        public void onClick(){
            Bundle bundle = new Bundle();
            bundle.putString("referId", String.valueOf(mIdTv.getText()));
            CommonUtil.openActivity(mContext, ReferDetailActivity.class,bundle);
        }
    }
}
