package com.asking91.app.ui.supertutorial.buy.exercises.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.supertutorial.SuperSubjectTopicEntity;
import com.asking91.app.ui.supertutorial.OnCommItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jswang on 2017/5/9.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.CommViewHolder>{

    private List<SuperSubjectTopicEntity> mDatas;
    private Context mContext;

    OnCommItemClickListener<SuperSubjectTopicEntity> mListener;

    public SubjectAdapter(Context context, List<SuperSubjectTopicEntity> datas, OnCommItemClickListener<SuperSubjectTopicEntity> mListener){
        this.mContext = context;
        this.mDatas = datas;
        this.mListener = mListener;
    }

    @Override
    public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_super_subject,parent,false));
    }

    @Override
    public void onBindViewHolder(CommViewHolder holder, int position) {
        final SuperSubjectTopicEntity e = mDatas.get(position);
        holder.item_name.setSelected(e.isSelect);
        holder.item_name.setText(e.getTopic_name());
        holder.item_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (SuperSubjectTopicEntity ii : mDatas) {
                    ii.isSelect = false;
                }
                e.isSelect = true;
                mListener.OnCommItem(e);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class CommViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_name)
        TextView item_name;

        public CommViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
