package com.asking91.app.ui.supertutorial.buy.classify.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.supertutorial.SuperLesson;
import com.asking91.app.ui.supertutorial.OnCommItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jswang on 2017/5/8.
 */

public class VersionAdapter  extends RecyclerView.Adapter<VersionAdapter.CommViewHolder>{
    private List<SuperLesson> mDatas;
    private Context mContext;

    OnCommItemClickListener<SuperLesson> mListener;

    public VersionAdapter(Context context, List<SuperLesson> datas, OnCommItemClickListener<SuperLesson> mListener){
        this.mContext = context;
        this.mDatas = datas;
        this.mListener = mListener;
    }

    @Override
    public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_supertutorial_classify,parent,false));
    }

    @Override
    public void onBindViewHolder(CommViewHolder holder, int position) {
        final SuperLesson e = mDatas.get(position);
        holder.item_name.setSelected(e.isSelect);
        holder.item_name.setText(e.getVersionName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (SuperLesson ii : mDatas) {
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
