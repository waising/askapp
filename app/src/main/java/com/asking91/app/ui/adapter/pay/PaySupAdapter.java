package com.asking91.app.ui.adapter.pay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.pay.CourseDetailEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by linbin on 2017/6/27.
 */

public class PaySupAdapter extends RecyclerView.Adapter<PaySupAdapter.CommViewHolder> {


    public int mPosition = 0;


    private MyItemClickListener mItemClickListener;

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }


    private ArrayList<CourseDetailEntity> mDatas;
    private Context mContext;

    public PaySupAdapter(Context context, ArrayList<CourseDetailEntity> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }


    public CourseDetailEntity getItemData(int position) {
        return mDatas.get(position);
    }

    @Override
    public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_course, parent, false), mItemClickListener);
    }

    @Override
    public void onBindViewHolder(CommViewHolder holder, int position) {
        final CourseDetailEntity item = mDatas.get(position);
        if (item != null) {
            if (item != null) {
                holder.tvCourseName.setText(item.getPackageName());
                if (mPosition == position) {
                    holder.tvCourseName.setSelected(true);
                } else {
                    holder.tvCourseName.setSelected(false);
                }

            }


        }
    }


    public ArrayList<CourseDetailEntity> getDataList() {
        return mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }


    class CommViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_course_name)
        TextView tvCourseName;
        private MyItemClickListener mListener;

        public CommViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }
    }

}
