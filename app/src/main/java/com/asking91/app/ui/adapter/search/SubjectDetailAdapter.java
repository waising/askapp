package com.asking91.app.ui.adapter.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.KnowledgeTypeDetailEntity;
import com.asking91.app.ui.widget.AskMathView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by zqshen on 2016/11/24.
 */

public class SubjectDetailAdapter extends RecyclerView.Adapter<SubjectDetailAdapter.MessageViewHolder> {

    private Context mContext;
    List<KnowledgeTypeDetailEntity> SubjectDetailEntity;
    private LayoutInflater inflater;
    String subjectTitle, detailsAnswerHtml;

    public SubjectDetailAdapter(Context context, String subjectTitle, String detailsAnswerHtml, List<KnowledgeTypeDetailEntity> SubjectDetailEntity) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = context;
        this.SubjectDetailEntity = SubjectDetailEntity;
        this.subjectTitle = subjectTitle;
        this.detailsAnswerHtml = detailsAnswerHtml;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_subject_detail, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {
        if (SubjectDetailEntity != null && SubjectDetailEntity.size() > 0) {
            String tabContentHtml = SubjectDetailEntity.get(position).getTabContentHtml();
            if (!TextUtils.isEmpty(tabContentHtml)) {
                String str = ("题目" + "<br/>" + "<p>" + subjectTitle + "<br/>" + tabContentHtml);
                holder.mathView.setText(str);
            }
        } else {
            if ( !TextUtils.isEmpty(detailsAnswerHtml)) {
                String str = ("题目" + "<br/>" + "<p>" + subjectTitle + "<br/>" + detailsAnswerHtml);
                holder.mathView.setText(str);
            }
        }
    }

    @Override
    public int getItemCount() {
        return SubjectDetailEntity.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mathView)
        AskMathView mathView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
