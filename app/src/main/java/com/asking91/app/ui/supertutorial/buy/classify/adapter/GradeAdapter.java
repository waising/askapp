package com.asking91.app.ui.supertutorial.buy.classify.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.supertutorial.SuperLessonLevel;
import com.asking91.app.ui.supertutorial.OnCommItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jswang on 2017/5/8.
 */

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.CommViewHolder>  {
    private List<SuperLessonLevel> mDatas;
    private Context mContext;

    OnCommItemClickListener<SuperLessonLevel> mListener;

    public GradeAdapter(Context context, List<SuperLessonLevel> datas, OnCommItemClickListener<SuperLessonLevel> mListener){
        this.mContext = context;
        this.mDatas = datas;
        this.mListener = mListener;
    }

    @Override
    public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_supertutorial_classify_grade,parent,false));
    }

    @Override
    public void onBindViewHolder(CommViewHolder holder, int position) {
        final SuperLessonLevel e = mDatas.get(position);
        holder.item_name.setSelected(e.isSelect);
        holder.item_name.setText(e.getTextbook());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnCommItem(e);
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
