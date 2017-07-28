package com.asking91.app.ui.adapter.supertutorial;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.asking91.app.R;
import com.asking91.app.entity.onlinetest.ResultEntity;
import com.asking91.app.entity.supertutorial.SuperBuyClearanceEntity;
import com.asking91.app.ui.supertutorial.buy.exercises.SuperTopicFragment;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxiao on 2016/12/16.
 */

public class SuperBuyClearanceTopicAskRecycleOptionsAdapter extends RecyclerView.Adapter<SuperBuyClearanceTopicAskRecycleOptionsAdapter.ViewHolder> {

    private List<SuperBuyClearanceEntity.OptionsBean> answerOptions;
    private Context context;
    private LayoutInflater layoutInflater;

    public void setSubmit(boolean submit) {
        isSubmit = submit;
    }

    public void setId(String id) {
        this.id = id;
    }

    private RadioButton view;

    /**
     * 题目id
     */
    private String id;
    private Map<Integer, RadioButton> lays;
    private SuperTopicFragment.SwitchFragmentCall switchFragmentCall;

    public SuperBuyClearanceTopicAskRecycleOptionsAdapter(Context context, List<SuperBuyClearanceEntity.OptionsBean> answerOptions,
                                                          SuperTopicFragment.SwitchFragmentCall switchFragmentCall) {
        this.context = context;
        this.answerOptions = answerOptions;
        this.switchFragmentCall = switchFragmentCall;
        layoutInflater = LayoutInflater.from(context);
        lays = new ArrayMap<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_common_recycle_options, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SuperBuyClearanceEntity.OptionsBean answerOption = answerOptions.get(position);
        holder.rBtn.setText(answerOption.getOptionName());
        holder.rBtn.setChecked(answerOption.isSelect());

        lays.put(position, holder.rBtn);
        if (answerOption.getResultEntity() != null) {
            ResultEntity resultEntity = answerOption.getResultEntity();
            boolean isOption = resultEntity.getAnswerResult().equals(answerOptions.get(position).getOptionName());
            boolean isRight = resultEntity.getAnswerResult().equals(resultEntity.getRightResult());//答案是否正确
            ImageView mStateIv = (ImageView) ((RelativeLayout) (lays.get(position).getParent())).findViewById(R.id.state_bg);
            lays.get(position).setEnabled(false);
            if (isOption) {
                if (isRight) {//正确答案
                    mStateIv.setImageResource(R.mipmap.ask_right);
                } else {//错误答案
                    mStateIv.setImageResource(R.mipmap.ask_wrong);
                }
                mStateIv.setVisibility(View.VISIBLE);
            } else {
                if (!isRight && answerOptions.get(position).getOptionName().equals(resultEntity.getRightResult())) {
                    mStateIv.setImageResource(R.mipmap.ask_wrong);
                }
            }
            if (answerOptions.get(position).getOptionName().equals(resultEntity.getRightResult())) {
                mStateIv.setImageResource(R.mipmap.ask_right);
            }
            holder.rBtn.setText("");
        } else {
            holder.rBtn.setOnClickListener(new View.OnClickListener() {//选项点击
                @Override
                public void onClick(View v) {
                    if (view != null && view != v) {//没选中的
                        view.setChecked(false);
                    }
                    if (view != v) {
                        view = (RadioButton) v;
                        view.setChecked(true);
                        //选中的答案
                        switchFragmentCall.sendNextFragmentMessage(id, view.getText().toString());
                    }
                }
            });
        }
    }

    /**
     * 是否提交过
     */
    private boolean isSubmit;

    /**
     * 更新结果，将正确答案实体传入
     */
    public void updateResult(ResultEntity resultEntity) {
        isSubmit = true;//设置已经提交过
        for (int i = 0; i < answerOptions.size(); i++) {
            String optionName = answerOptions.get(i).getOptionName();
            String result = resultEntity.getAnswerResult();
            boolean isOption = TextUtils.equals(optionName, result);
            boolean isRight = TextUtils.equals(resultEntity.getAnswerResult(), resultEntity.getRightResult());
            ImageView mStateIv = (ImageView) ((RelativeLayout) (lays.get(i).getParent())).findViewById(R.id.state_bg);
            lays.get(i).setEnabled(false);
            if (isOption) {
                if (isRight) {
                    mStateIv.setImageResource(R.mipmap.ask_right);
                } else {
                    mStateIv.setImageResource(R.mipmap.ask_wrong);
                }
                mStateIv.setVisibility(View.VISIBLE);
            } else {
                if (!isRight && answerOptions.get(i).getOptionName().equals(resultEntity.getRightResult())) {
                    mStateIv.setImageResource(R.mipmap.ask_wrong);
                }
            }
            if (answerOptions.get(i).getOptionName().equals(resultEntity.getRightResult())) {//如果某项是正确答案
                mStateIv.setImageResource(R.mipmap.ask_right);
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
            ButterKnife.bind(this, itemView);
        }
    }
}
