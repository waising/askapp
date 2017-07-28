package com.asking91.app.ui.adapter.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.search.KnowledgeEntity;
import com.asking91.app.ui.basepacket.details.KnowledgeDetailActivity;
import com.asking91.app.util.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by zqshen on 2016/11/24.
 */

public class SearchKnowledgeAdapter extends RecyclerView.Adapter<SearchKnowledgeAdapter.MessageViewHolder> {

    private Context mContext;

    List<KnowledgeEntity> knowledgeEntities;
    private LayoutInflater inflater;
    String type;
    String title = "知识详情";

    public SearchKnowledgeAdapter(Context context, List<KnowledgeEntity> knowledgeEntities, String type) {
        this.mContext = context;
        this.knowledgeEntities = knowledgeEntities;
        this.type = type;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_search_knowledge, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {
        final KnowledgeEntity list = knowledgeEntities.get(position);
            if (list.getTipName() != null && !TextUtils.isEmpty(list.getTipName())) {
                holder.mTextview.setText(list.getTipName());
                final String tip_id = list.getTipId();
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(tip_id) && !TextUtils.isEmpty(type)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("type", type);
                            bundle.putString("id", tip_id);
                            bundle.putString("title", title);
                            bundle.putString("knowledge", list.getTipName());
                            bundle.putString("me", "searchDetail");
                            CommonUtil.openActivity(mContext, KnowledgeDetailActivity.class, bundle); // 跳转到基础知识包详情
                        }
                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return knowledgeEntities.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView img_search;
        @BindView(R.id.textview)
        TextView mTextview;
        @BindView(R.id.linearLayout)
        LinearLayout layout;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
