package com.asking91.app.ui.adapter.onlineqa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.onlineqa.OnlineQaMyAnswerEntity;
import com.asking91.app.global.OnlineQAConstant;
import com.asking91.app.ui.onlineqa.OnlineQADetailActivity;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.util.JLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.asking91.app.global.OnlineQAConstant.versionTv;

/**
 * Created by wxiao on 2016/11/8.
 */

public class OnlineQaMyAnwserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OnlineQaMyAnswerEntity.OnlineQaMyAnswerDetailEntity> entities;
    private Context context;

    public OnlineQaMyAnwserAdapter(Context context,List<OnlineQaMyAnswerEntity.OnlineQaMyAnswerDetailEntity>  entities) {
        this.context = context;
        this.entities = entities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onlineqa_myqa_answer, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OnlineQaMyAnswerEntity.OnlineQaMyAnswerDetailEntity e = entities.get(position);
        final ViewHolder v = (ViewHolder) holder;
        int n = e.getDescription().indexOf(OnlineQAConstant.imgHead);
        if(n>-1&&n<e.getDescription().length()){
            String str = e.getDescription().substring(n+OnlineQAConstant.imgHead.length());
            int n2 = str.indexOf(OnlineQAConstant.imgEnd)+OnlineQAConstant.imgEnd.length();
            v.img.setImageUrl(str.substring(0, n2));
            v.img.setVisibility(View.VISIBLE);
        }else{
            v.img.setVisibility(View.GONE);
        }
        v.time.setText(e.getCreateDateFmt());
        v.title.setText(e.getTitle());
        String action = e.getKm();
        if (action != null) {
            String level = "";
            if (null != e.getLevelId()) {
                n = Integer.parseInt(e.getLevelId())-7;
                if (n < versionTv.length&&n>=0) {
                    level = versionTv[n];
                }
            }
            if (action.indexOf("M") != -1) {
                v.versionTv.setText("【 数学-" + level + "】");
            } else if (action.indexOf("P") != -1) {
                v.versionTv.setText("【 物理-" + level + "】");
            }
        }
        v.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OnlineQADetailActivity.class);
                intent.putExtra("id",e.getId());
                intent.putExtra("userId", e.getUserId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (entities == null) {
            return 0;
        } else {
            return entities.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img)
        AskSimpleDraweeView img;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.version_tv)
        TextView versionTv;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.layout)
        RelativeLayout layout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
