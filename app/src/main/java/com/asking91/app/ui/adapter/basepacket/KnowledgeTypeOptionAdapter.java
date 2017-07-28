package com.asking91.app.ui.adapter.basepacket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.AnswerOption;
import com.asking91.app.ui.widget.AskMathView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/11/7.
 */
public class KnowledgeTypeOptionAdapter extends RecyclerView.Adapter<KnowledgeTypeOptionAdapter.ViewHolder> {

    private Context mContext;
    List<AnswerOption> mOptionDatas;

    public KnowledgeTypeOptionAdapter(Context context, List<AnswerOption> datas){
        this.mContext = context;
        this.mOptionDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new KnowledgeTypeOptionAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_knowledge_type_option_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        AnswerOption answerOption = mOptionDatas.get(position);
        holder.mOptionNameTv.setText(answerOption.getOptionName().concat("、"));
        if(!"".equals(answerOption.getOptionContentHtml())){
            holder.mOptionContentTv.setText(answerOption.getOptionContentHtml());
        }
    }

    @Override
    public int getItemCount() {
        return mOptionDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.optionName)
        TextView mOptionNameTv;

        @BindView(R.id.option_content_mathView)
        AskMathView mOptionContentTv;

        int position;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mOptionContentTv.formatMath().showWebImage();
        }
    }
}
