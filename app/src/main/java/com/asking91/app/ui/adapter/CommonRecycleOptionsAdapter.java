package com.asking91.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.AnswerOption;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxiao on 2016/12/16.
 */

public class CommonRecycleOptionsAdapter extends RecyclerView.Adapter<CommonRecycleOptionsAdapter.ViewHolder> {

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


    public CommonRecycleOptionsAdapter(Context context, List<AnswerOption> answerOptions, int parentPosition) {
        this.context = context;
        this.answerOptions = answerOptions;
        this.parentPosition = parentPosition;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view =  View.inflate(parent.getContext(), R.layout.item_common_recycle_options, null);
        View view = layoutInflater.inflate(R.layout.item_common_recycle_options,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AnswerOption option = answerOptions.get(position);
        holder.rBtn.setTag(R.id.key_answer, option.getOptionName());
        holder.rBtn.setTag(R.id.key_select_id, option.getSelectId());
        holder.rBtn.setTag(R.id.key_position, position);
        holder.rBtn.setText(option.getOptionName());
        if(!isSubmit) {
            holder.rBtn.setEnabled(true);
            holder.rBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int selectPosition = Integer.parseInt(String.valueOf(v.getTag(R.id.key_select_id)));
                        int position = Integer.parseInt(String.valueOf(v.getTag(R.id.key_position)));
                        if (selectPosition != position) {
                            if (selectPosition >= 0) {
                                //修改之前数据并刷新
                                answerOptions.get(selectPosition).setSelectId(position);
                                notifyItemChanged(Integer.parseInt(String.valueOf(v.getTag(R.id.key_select_id))));
                            }
                            //设置当前数据
//                            ((BaseActivity) mContext).updateOptions(parentPosition, position, String.valueOf(v.getTag(R.id.key_answer)));
//                            answerOptions.get(position).setSelectPosition(position);
                        }
                }
            });
        }else{
            holder.rBtn.setEnabled(false);//禁止点击
        }
    }

    @Override
    public int getItemCount() {
        return answerOptions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rBtn)
        RadioButton rBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
