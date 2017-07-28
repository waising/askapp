package com.asking91.app.ui.adapter.onlineqa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.onlineqa.OnlineQADetailEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.ui.onlineqa.OnlineQaAnwserActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.ui.widget.MultiStateView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxiao on 2016/11/9.
 */

public class OnlineQaAnwserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int Left = 1;
    private static final int Right = 2;

    private Context context;
    private LayoutInflater inflater;
    private boolean userFlag;
    private String userId;
    private List<OnlineQADetailEntity.AnwserMoreEntity> anwserMoreEntities;

    public OnlineQaAnwserAdapter(Context context, boolean userFlag, String userId, List<OnlineQADetailEntity.AnwserMoreEntity> anwserMoreEntities) {
        this.context = context;
        this.userFlag = userFlag;
        this.userId = userId;
        inflater = LayoutInflater.from(context);
        this.anwserMoreEntities = anwserMoreEntities;
    }
    @Override
    public int getItemCount() {
        return anwserMoreEntities.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Left) {
            View v = inflater.inflate(R.layout.item_onlineqa_anwser_left, parent, false);
            return new LeftViewHolder(v);
        } else {
            View v = inflater.inflate(R.layout.item_onlineqa_anwser_right, parent, false);
            return new RightViewHolder(v);
        }
//        View v = inflater.inflate(R.layout.item_onlineqa_anwser_center, parent, false);
//        return new CenterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==Left){
            setLeftData((LeftViewHolder) holder, anwserMoreEntities.get(position));
        }else{
            setRightData((RightViewHolder) holder, anwserMoreEntities.get(position));
        }
//        setCenterData((CenterViewHolder) holder, anwserMoreEntities.get(position));
    }

    private void setCenterData(final CenterViewHolder holder, OnlineQADetailEntity.AnwserMoreEntity e){
            if(e.getAnswerTime()!=null) {
                holder.time.setText(e.getAnswerTime());
                if (e.getAnswer() != null) {
                    holder.mathView.setWebText(e.getAnswer());
                } else {
                    holder.multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                }
            }
            else {
                holder.time.setText(e.getAskTime());
                if (e.getAsk() != null) {
                    holder.mathView.setWebText(e.getAsk());
                } else {
                    holder.multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                }
            }
    }

    static class CenterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.mathView)
        AskMathView mathView;
        @BindView(R.id.multiStateView)
        MultiStateView multiStateView;

        CenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            mvName.setMaxHeight(350);
            mathView.formatMath().showWebImage(multiStateView);
        }
    }

    private void setLeftData(final LeftViewHolder holder, final OnlineQADetailEntity.AnwserMoreEntity e){
        holder.userImgIv.setImageUrl(Constants.USER_AVATAR+userId);
        if(!userFlag){
            holder.bg.setBackgroundResource(R.drawable.onlineqa_blue_left);
            holder.time.setText(e.getAskTime());
            if(e.getAsk()!=null) {
                holder.mathView.setWebText(e.getAsk());
            }else{
                holder.multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }else{
            holder.bg.setBackgroundResource(R.drawable.onlineqa_white_left);
            holder.time.setText(e.getAnswerTime());
            if(e.getAnswer()!=null) {
                holder.mathView.setWebText(e.getAnswer());
            }else{
                holder.multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }
    }

    private void setRightData(final RightViewHolder holder, OnlineQADetailEntity.AnwserMoreEntity e){
        holder.userImgIv.setImageUrl(Constants.USER_AVATAR+((OnlineQaAnwserActivity)context).getUserConstant().getUserId());
        if(userFlag){
            holder.bg.setBackgroundResource(R.drawable.onlineqa_blue_right);
            holder.time.setText(e.getAskTime());
            if(e.getAsk()!=null) {
                holder.mathView.setWebText(e.getAsk());
            }else{
                holder.multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }else{
            holder.bg.setBackgroundResource(R.drawable.onlineqa_white_right);
            holder.time.setText(e.getAnswerTime());
            if(e.getAnswer()!=null) {
                holder.mathView.setWebText(e.getAnswer());
            }else{
                holder.multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (userFlag) {//提问者
            if (anwserMoreEntities.get(position).getAskTime() == null) {//提问是null
                return Left;
            } else {
                return Right;
            }
        } else {
            if (anwserMoreEntities.get(position).getAnswerTime() == null) {
                return Left;
            } else {
                return Right;
            }
        }
    }

    static class LeftViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.user_img_iv)
        AskSimpleDraweeView userImgIv;
        @BindView(R.id.mathView)
        AskMathView mathView;
        @BindView(R.id.multiStateView)
        MultiStateView multiStateView;
        @BindView(R.id.bg)
        LinearLayout bg;

        LeftViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            mvName.setMaxHeight(350);
            mathView.formatMath().showWebImage(multiStateView);
        }
    }

    static class RightViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.mathView)
        AskMathView mathView;
        @BindView(R.id.multiStateView)
        MultiStateView multiStateView;
        @BindView(R.id.bg)
        LinearLayout bg;
        @BindView(R.id.user_img_iv)
        AskSimpleDraweeView userImgIv;

        RightViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            mvName.setMaxHeight(350);
            mathView.formatMath().showWebImage(multiStateView);
        }
    }


}
