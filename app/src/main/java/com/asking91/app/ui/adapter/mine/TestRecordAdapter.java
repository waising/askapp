package com.asking91.app.ui.adapter.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.onlinetest.PaperEntity;
import com.asking91.app.ui.onlinetest.topicask.OnlineTestTopicAskActivity;
import com.asking91.app.util.CommonUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by zqshen on 2016/11/24.
 */

public class TestRecordAdapter extends RecyclerView.Adapter<TestRecordAdapter.MessageViewHolder> {

    private Context mContext;
    List<PaperEntity> list;
    String subjectCatalog;
    private final SimpleDateFormat dateFormat;
    private long createDate = 0;
    private String subTitle;

    public TestRecordAdapter(Context context, List<PaperEntity> list, String subjectCatalog) {
        this.mContext = context;
        this.list = list;
        this.subjectCatalog = subjectCatalog;
        dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    }

    public void setDate(List<PaperEntity> list, String subjectCatalog) {
        this.list = list;
        this.subjectCatalog = subjectCatalog;
        notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_test_record_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {

        if (list != null && list.size() > 0) {
            String paperName = list.get(position).getPaperName(); // 标题
            final boolean end = list.get(position).isEnd(); // 是否完成答题
            createDate = list.get(position).getCreateDate();// 时间
            final String id = list.get(position).getId();
            if (createDate != 0) {
                String sd = dateFormat.format(new Date(createDate));   // 时间戳转换成时间
                holder.mtvYear.setText(sd);
            } else {
                holder.mtvYear.setText("");
            }
            if (end) {
                holder.mImageView.setImageResource(R.mipmap.ic_completed);
            } else {
                holder.mImageView.setImageResource(R.mipmap.ic_unfinished);
            }
            if (paperName != null) {
                if (paperName.length() > 21) {
                    // 后台返回的数据和UI不一致，so 自己截取数据了
                    subTitle = paperName.substring(21, paperName.length());
                } else {
                    subTitle = paperName.substring(0, paperName.length());
                }
                if (subTitle != null) {
                    holder.mtvTitle.setText(subTitle);
                } else {
                    holder.mtvTitle.setText(paperName);
                }
            } else {
                holder.mtvTitle.setText("");
            }
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (end) {
                        // 已完成答题
                    } else {
                        //未完成跳转出题界面
                        Bundle bundle = new Bundle();
                        bundle.putString("subjectCatalog", subjectCatalog);
                        bundle.putString("paperId", id);
    /*                //版本
                    bundle.putString("versionName", (String) mVersionTv.getText());
                    //年级
                    bundle.putString("className", (String) mClassTv.getText());*/
                        CommonUtil.openActivity(OnlineTestTopicAskActivity.class, bundle);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mtvTitle;
        @BindView(R.id.tv_year)
        TextView mtvYear;
        @BindView(R.id.imageView)
        ImageView mImageView;
        @BindView(R.id.item_layout)
        RelativeLayout itemLayout;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
