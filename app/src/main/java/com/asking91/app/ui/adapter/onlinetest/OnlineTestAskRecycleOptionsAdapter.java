package com.asking91.app.ui.adapter.onlinetest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.AnswerOption;
import com.asking91.app.ui.onlinetest.topicask.OnlineTestTopicAskActivity;
import com.asking91.app.ui.onlinetest.topicask.OnlineTestTopicAskFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxiao on 2016/12/16.
 */

public class OnlineTestAskRecycleOptionsAdapter extends RecyclerView.Adapter<OnlineTestAskRecycleOptionsAdapter.ViewHolder> {

    private List<AnswerOption> answerOptions;
    private Context context;
    private LayoutInflater layoutInflater;

    public void setSubmit(boolean submit) {
        isSubmit = submit;
    }

    /**是否提交过*/
    private boolean isSubmit;

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }

    /**当前题目对应在列表的position*/
    private int parentPosition =-1;

    public void setId(String id) {
        this.id = id;
    }

    private RadioButton view;

    /**题目id*/
    private String id;

    OnlineTestTopicAskFragment.SwitchFragmentCall switchFragmentCall;
    private RecyclerView recyclerView;
    public OnlineTestAskRecycleOptionsAdapter(Context context, List<AnswerOption> answerOptions, int parentPosition, RecyclerView recyclerView) {
        this.context = context;
        this.answerOptions = answerOptions;
        this.parentPosition = parentPosition;
        this.recyclerView = recyclerView;
        layoutInflater = LayoutInflater.from(context);
        switchFragmentCall = (OnlineTestTopicAskFragment.SwitchFragmentCall)context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view =  View.inflate(parent.getContext(), R.layout.item_common_recycle_options, null);
        View view = layoutInflater.inflate(R.layout.item_common_recycle_options,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AnswerOption answerOption = answerOptions.get(position);
        holder.rBtn.setText(answerOption.getOptionName());
        holder.rBtn.setChecked(answerOption.getSelectId()==position);
        if(answerOption.getResultEntity()!=null){
            boolean isOption = answerOption.getResultEntity().getAnswerResult().equals(answerOption.getOptionName());
            boolean isRight = answerOption.getResultEntity().getAnswerResult().equals(answerOption.getResultEntity().getRightResult());
//            holder.rBtn.setChecked(false);
            if(isOption){
                if(isRight){
                    holder.stateBg.setImageResource(R.mipmap.ask_right);
                }else{
                    holder.stateBg.setImageResource(R.mipmap.ask_wrong);
                }
            }else{
                if(!isRight&&answerOption.getOptionName().equals(answerOption.getResultEntity().getRightResult())) {
                    holder.stateBg.setImageResource(R.mipmap.ask_wrong);
                }
            }
            if(answerOption.getOptionName().equals(answerOption.getResultEntity().getRightResult())){
                holder.stateBg.setImageResource(R.mipmap.ask_right);
            }
            holder.rBtn.setEnabled(false);
        }else{

            holder.rBtn.setTag(R.id.key_answer, answerOption.getOptionName());
            holder.rBtn.setTag(R.id.key_select_id, answerOption.getSelectId());
            holder.rBtn.setTag(R.id.key_position, position);
            holder.rBtn.setTag(R.id.key_holder, holder);
            holder.rBtn.setEnabled(true);
            holder.rBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openNext((ViewHolder)v.getTag(R.id.key_holder));
                    ((OnlineTestTopicAskActivity)context).updateOptions(parentPosition,
                            Integer.parseInt(String.valueOf(v.getTag(R.id.key_position))));
                }
            });
        }
    }

    private void openNext(ViewHolder holder){
        if(!isSubmit) {
            if (view != null && view != holder.rBtn) {
                view.setChecked(false);
            }
            if (view != holder.rBtn) {
                view = holder.rBtn;
                holder.rBtn.setChecked(true);
                //跳转
                switchFragmentCall.sendNextFragmentMessage(id, holder.rBtn.getText().toString());
            }
        }
    }

    @Override
    public int getItemCount() {
        return answerOptions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rBtn)
        RadioButton rBtn;
        @BindView(R.id.state_bg)
        ImageView stateBg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
