package com.asking91.app.ui.adapter.onlinetest;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.AnswerOption;
import com.asking91.app.ui.onlinetest.topicask.OnlineTestTopicAskActivity;
import com.asking91.app.ui.onlinetest.topicask.OnlineTestTopicAskFragment;
import com.asking91.app.ui.widget.AskMathView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/11/16.
 */
public class OnlineTestAskTopicOptionAdapter extends RecyclerView.Adapter<OnlineTestAskTopicOptionAdapter.ViewHolder> {

    Context mContext;
    List<AnswerOption> mDatas;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    /**数据列表的位置*/
    private int parentPosition;

    //题目ID
    private String id;
    OnlineTestTopicAskFragment.SwitchFragmentCall switchFragmentCall;
    public OnlineTestAskTopicOptionAdapter(Context context, List<AnswerOption> datas, int parentPosition) {
        this.mContext = context;
        this.mDatas = datas;
        this.parentPosition = parentPosition;
        switchFragmentCall = (OnlineTestTopicAskFragment.SwitchFragmentCall)mContext;
        lays = new ArrayMap<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OnlineTestAskTopicOptionAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_onlinetest_topic_ask_list,parent,false));
    }
    private View view;

    public void setSubmit(boolean submit) {
        isSubmit = submit;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        AnswerOption answerOption = mDatas.get(position);
        String optionName = answerOption.getOptionName();
        holder.mTopicOptionTv.setText(optionName);
        holder.mTopicOptionMv.setText(answerOption.getOptionContentHtml());

        if(answerOption.getResultEntity()!=null){
            boolean isOption = answerOption.getResultEntity().getAnswerResult().equals(answerOption.getOptionName());
            boolean isRight = answerOption.getResultEntity().getAnswerResult().equals(answerOption.getResultEntity().getRightResult());
            if(isOption){
                if(isRight){
                    holder.mTopicOptionLy.setBackgroundResource(R.mipmap.right_rech_bg);
                    holder.mStateIv.setImageResource(R.mipmap.ask_right);
                }else{
                    holder.mTopicOptionLy.setBackgroundResource(R.mipmap.wrong_rech_bg);
                    holder.mStateIv.setImageResource(R.mipmap.ask_wrong);
                }
                holder.mStateIv.setVisibility(View.VISIBLE);
            }else{
                if(!isRight&&answerOption.getOptionName().equals(answerOption.getResultEntity().getRightResult()))
                    holder.mTopicOptionLy.setBackgroundResource(R.mipmap.wrong_rech_bg);
            }

            if(answerOption.getOptionName().equals(answerOption.getResultEntity().getRightResult())){
                holder.mTopicOptionLy.setBackgroundResource(R.mipmap.right_rech_bg);
            }
            holder.mTopicOptionLy.setEnabled(false);
        }else{
            holder.mTopicOptionLy.setBackgroundResource(answerOption.getSelectId()==position ? R.mipmap.wrong_rech_bg : R.mipmap.rech_bg);
            holder.mTopicOptionLy.setTag(R.id.key_position, position);
            holder.mTopicOptionLy.setTag(R.id.key_id, parentPosition);
            holder.mTopicOptionLy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openNext(holder);
                    ((OnlineTestTopicAskActivity)mContext).updateOptions(Integer.parseInt(String.valueOf(v.getTag(R.id.key_id))),
                            Integer.parseInt(String.valueOf(v.getTag(R.id.key_position))));

                }
            });
        }
        lays.put(position, holder.mTopicOptionLy);
    }
    private ArrayMap<Integer, RelativeLayout> lays;
    /**是否提交过*/
    private boolean isSubmit;

    private void openNext(final ViewHolder holder){
        if(!isSubmit) {
            if (view != null && view != holder.mTopicOptionLy) {
                view.setBackgroundResource(R.mipmap.rech_bg);
            }
            if (view != holder.mTopicOptionLy) {
                view = holder.mTopicOptionLy;
                holder.mTopicOptionLy.setBackgroundResource(R.mipmap.wrong_rech_bg);
                //跳转
                switchFragmentCall.sendNextFragmentMessage(id, holder.mTopicOptionTv.getText().toString());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.topic_option_ly)
        RelativeLayout mTopicOptionLy;

        @BindView(R.id.topic_option_tv)
        TextView mTopicOptionTv;

        @BindView(R.id.mathView)
        AskMathView mTopicOptionMv;

//        @BindView(R.id.multiStateView)
//        MultiStateView msView;
        @BindView(R.id.ask_state_iv)
        ImageView mStateIv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mTopicOptionMv.formatMath().showWebImage();
        }
    }
}
