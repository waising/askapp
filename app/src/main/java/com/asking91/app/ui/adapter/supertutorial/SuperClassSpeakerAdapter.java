package com.asking91.app.ui.adapter.supertutorial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.supertutorial.SuperClassComeToSpeak;
import com.asking91.app.ui.supertutorial.buy.superclass.SuperSpeakerDetailActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.AskSimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/10/27.
 */

public class SuperClassSpeakerAdapter extends RecyclerView.Adapter<SuperClassSpeakerAdapter.ViewHolder> {

    private List<SuperClassComeToSpeak> mDatas;
    private Context mContext;
    private Bundle bundle;


    public SuperClassSpeakerAdapter(Context context, List<SuperClassComeToSpeak> datas, Bundle bundle) {
        this.mContext = context;
        this.mDatas = datas;
        this.bundle = bundle;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_supertutorial_superclass_speaker, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            holder.topTip.setVisibility(View.VISIBLE);
        } else {
            holder.topTip.setVisibility(View.GONE);
        }
        final SuperClassComeToSpeak item = mDatas.get(position);
        holder.titleMathView.setText("题型"+(position+1)+"："+item.getSubjectKindName());
        holder.titleMathView.setWebViewTextColor("题型"+(position+1)+"："+item.getSubjectKindName());
        holder.titleMathView.setWebViewTextSize(14);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnItemClick(item);
            }
        });

        holder.titleMathView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_UP:
                        OnItemClick(item);
                        break;
                }
                return true;
            }
        });
    }

    private void OnItemClick(SuperClassComeToSpeak item){
        Intent intent = new Intent(mContext, SuperSpeakerDetailActivity.class);
        intent.putExtra("SuperClassComeToSpeak.ListBean", item);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tip1)
        TextView tip1;

        @BindView(R.id.voice)
        AskSimpleDraweeView voice;

        @BindView(R.id.top_tip)
        RelativeLayout topTip;

        @BindView(R.id.titleMathView)
        AskMathView titleMathView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
