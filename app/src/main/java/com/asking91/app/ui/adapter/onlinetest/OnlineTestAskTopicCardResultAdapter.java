package com.asking91.app.ui.adapter.onlinetest;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.onlinetest.ResultEntity;
import com.asking91.app.global.OnlineTestConstant;
import com.asking91.app.ui.onlinetest.topicask.OnlineTestTopicAskCardActivity;
import com.asking91.app.ui.onlinetest.topicask.OnlineTestTopicAskResultActivity;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wxwang on 2016/11/16.
 */
public class OnlineTestAskTopicCardResultAdapter extends RecyclerView.Adapter<OnlineTestAskTopicCardResultAdapter.ViewHolder> {

    Context mContext;
    Map<String,ResultEntity> mDatas;
    int type = 0;

    public OnlineTestAskTopicCardResultAdapter(Context context, Map<String,ResultEntity> map, int type) {
        this.mContext = context;
        this.type = type;
        this.mDatas = map;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OnlineTestAskTopicCardResultAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onlinetest_topic_ask_result_list,null,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ResultEntity resultEntity = mDatas.get(position+"");

        if(resultEntity!=null){

            if (OnlineTestConstant.CardType.ASK_CARD ==type){
                holder.mResultNum.setText(String.valueOf(resultEntity.getNum()));
                holder.mResultLy.setBackgroundResource(!"".equals(resultEntity.getAnswerResult())?R.mipmap.ask_card_bg_1:R.mipmap.ask_card_bg);
                holder.mResultNum.setTextColor(!"".equals(resultEntity.getAnswerResult())? ContextCompat.getColor(mContext,R.color.white):ContextCompat.getColor(mContext,R.color.colorText_88));
            }else{
                boolean isRight = resultEntity.getAnswerResult().equals(resultEntity.getRightResult());
                holder.mResultImg.setImageResource(isRight?R.mipmap.ask_result_right:R.mipmap.ask_result_wrong);
                holder.mResultLy.setBackgroundResource(isRight?R.mipmap.ask_result_bg:R.mipmap.aks_result_bg_1);
                holder.mResultNum.setText(String.valueOf(resultEntity.getNum()));
            }

        }

        holder.mResultImg.setVisibility(OnlineTestConstant.CardType.ASK_CARD ==type ?View.GONE:View.VISIBLE);
        holder.mResultNum.setVisibility(OnlineTestConstant.CardType.ASK_CARD==type ? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ask_result_img)
        ImageView mResultImg;

        @BindView(R.id.ask_result_num)
        TextView mResultNum;

        @BindView(R.id.result_ly)
        LinearLayout mResultLy;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick({R.id.result_ly,R.id.ask_result_img,R.id.ask_result_num})
        public void onClick(View v) {
            if (mContext instanceof OnlineTestTopicAskCardActivity){
                ((OnlineTestTopicAskCardActivity)mContext).onBackResultActivity(String.valueOf(mResultNum.getText()),mDatas);
            }
            else {
                ((OnlineTestTopicAskResultActivity) mContext).onBackResultActivity(String.valueOf(mResultNum.getText()),mDatas);
            }
        }
    }
}
