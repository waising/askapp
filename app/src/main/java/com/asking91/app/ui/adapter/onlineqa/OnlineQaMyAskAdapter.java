package com.asking91.app.ui.adapter.onlineqa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.onlineqa.OnlineQaMyAskEntity;
import com.asking91.app.ui.onlineqa.OnlineQADetailActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.asking91.app.global.OnlineQAConstant.versionTv;

/**
 * Created by wxiao on 2016/11/8.
 */

public class OnlineQaMyAskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OnlineQaMyAskEntity.OnlineQaMyAskDetailEntity> entities;
    private Context context;

    public OnlineQaMyAskAdapter(Context context,List<OnlineQaMyAskEntity.OnlineQaMyAskDetailEntity> entities) {
        this.context = context;
        this.entities = entities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onlineqa_myqa_ask, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OnlineQaMyAskEntity.OnlineQaMyAskDetailEntity e = entities.get(position);
        final ViewHolder v = (ViewHolder) holder;
        v.time.setText(e.getCreateDateFmt());
        v.title.setText(e.getTitle());
        v.ask.setText(e.getCaifu() + "");
        v.a.setText(e.getAnswerSize() + "");
        if(e.getDescription()==null){
            v.multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }else {
            v.mathView.setText(e.getDescription());
        }
        String action = e.getKm();
        if (action != null) {
            String level = "";
            if (null != e.getLevelId()) {
                int n = Integer.parseInt(e.getLevelId())-7;
                if (n < versionTv.length) {
                    level = versionTv[n];
                }
            }
            if (action.indexOf("M") != -1) {
                v.versionTv.setText("【 数学-" + level + "】");
            } else if (action.indexOf("P") != -1) {
                v.versionTv.setText("【 物理-" + level + "】");
            }
        }
        v.lay.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.title_top)
        ImageView titleTop;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.mathView)
        AskMathView mathView;
        @BindView(R.id.multiStateView)
        MultiStateView multiStateView;
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.version_tv)
        TextView versionTv;
        @BindView(R.id.ask)
        TextView ask;
        @BindView(R.id.a)
        TextView a;
        @BindView(R.id.lay)
        LinearLayout lay;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
