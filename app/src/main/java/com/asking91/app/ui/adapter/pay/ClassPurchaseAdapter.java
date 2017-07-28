package com.asking91.app.ui.adapter.pay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.ui.pay.LessonEntity;
import com.asking91.app.ui.widget.WrapContentGridView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.asking91.app.R.id.ll_list;


/**
 * Created by wxwang on 2016/10/27.
 */

public class ClassPurchaseAdapter extends RecyclerView.Adapter<ClassPurchaseAdapter.ViewHolder> {

    private MyItemClickListener mItemClickListener;
    /**
     * 选中位置
     */
    public int choosePosition = -1;


    private RecyclerView recyclerView;
    private ArrayList<LessonEntity> versionDataList;

    public void setData(ArrayList<LessonEntity> versionDataList) {
        this.versionDataList = versionDataList;

    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion, LessonEntity lessonEntity, WrapContentGridView myGridLayout);
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    private Context mContext;


    public ClassPurchaseAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_class_purchase, parent, false), mItemClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final LessonEntity item = versionDataList.get(position);
        if (item != null) {
            holder.tvVersionName.setText(item.getVersionName());//版本名字
        }

        if (item.getPurchased() == 2)//显示,未全部购买，显示
        {
            holder.llBig.setVisibility(View.VISIBLE);
        } else {
            holder.llBig.setVisibility(View.GONE);
        }
        if (choosePosition == position) {
            holder.expandableLayout.expand();
            holder.tvVersionName.setTextColor(mContext.getResources().getColor(R.color.dark_blue));
            holder.ivExpand.setImageResource(R.mipmap.attr_down);
        } else {
            holder.expandableLayout.collapse(false);
            holder.tvVersionName.setTextColor(mContext.getResources().getColor(R.color.dark_gray));
            holder.ivExpand.setImageResource(R.mipmap.attr_right);
        }


    }

    @Override
    public int getItemCount() {
        return versionDataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_verion_name)//版本
                TextView tvVersionName;
        @BindView(R.id.iv_expand)//右边展开按钮
                ImageView ivExpand;
        @BindView(ll_list)
        WrapContentGridView llList;
        @BindView(R.id.expandable_layout)//展开控件
                ExpandableLayout expandableLayout;
        @BindView(R.id.ll_big)
        LinearLayout llBig;
        private MyItemClickListener mListener;

        public ViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getPosition(), versionDataList.get(getPosition()), llList);

            }
        }
    }
}






