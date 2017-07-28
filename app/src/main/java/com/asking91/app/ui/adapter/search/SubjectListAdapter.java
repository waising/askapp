package com.asking91.app.ui.adapter.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.WebAppClient;
import com.asking91.app.entity.basepacket.AnswerOption;
import com.asking91.app.entity.basepacket.SubjectEntity;
import com.asking91.app.entity.basepacket.SubjectType;
import com.asking91.app.ui.search.subject.SubjectDetailActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.StarView;
import com.asking91.app.util.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by zqshen on 2016/11/24.
 */

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.MessageViewHolder> {

    private Context mContext;
    List<SubjectEntity> list;
    String subjectCatalog;
    private String strOptionContent = ""; // 选择题选项

    public SubjectListAdapter(Context context, List<SubjectEntity> list, String subjectCatalog) {
        this.mContext = context;
        this.list = list;
        this.subjectCatalog = subjectCatalog;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_subject_list, parent, false);
        MessageViewHolder messageViewHolder = new MessageViewHolder(inflate);
        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, final int position) {
        final SubjectEntity subjectEntity = list.get(position);
        final SubjectType subjectType = subjectEntity.getSubjectType();
        final String subjectDescriptionHtml = subjectEntity.getSubjectDescriptionHtml(); // subjectDescriptionHtml 是 typeId 为1、2、4 的标题
        if (subjectType.getTypeId() != null && !TextUtils.isEmpty(subjectType.getTypeId())) {
            // typeId 是后台传给我们的，1是选择题，2是解答题，3是判断题，4是填空题
            if (subjectType.getTypeId().equals("2") || subjectType.getTypeId().equals("4") || subjectType.getTypeId().equals("3")) {
                // typeId 解答题2, 判断题3， 填空题4
                if (subjectDescriptionHtml != null) {
                    holder.mathView.setText(subjectDescriptionHtml);
                    holder.multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }// 解答题、判断题和填空题没有abcd选项，所以不用给 mRecyclerView 设置适配器
                else {
                    //设置状态
                    holder.multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                }
            }
            //选择题
            if (subjectType.getTypeId().equals("1")) {
                List<AnswerOption> answerOptions = subjectEntity.getAnswerOptions();
                strOptionContent = "";
                for (int i = 0; i < answerOptions.size(); i++) {
                    String optionName = answerOptions.get(i).getOptionName(); // 选项名称
                    String optionContentHtml = answerOptions.get(i).getOptionContentHtml(); // 选项内容
                    if (optionContentHtml != null && optionContentHtml.contains("</p>")) {
                        strOptionContent += (optionName + "、" + optionContentHtml.substring(3, optionContentHtml.length() - 4) + "<br/>");
                    } else {
                        strOptionContent += (optionName + "、" + optionContentHtml + "<br/>");
                    }
                }
                subjectEntity.setSubjectDescription(subjectDescriptionHtml + "<br/>" + strOptionContent); // 自己新增
                holder.mathView.setText(subjectEntity.getSubjectDescription());
            }

            // 设置难度值
            if (subjectEntity.getDifficulty() > 0) {
                holder.ratingBar.setmStarNum(subjectEntity.getDifficulty());
                holder.multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            } else {
                holder.ratingBar.setmStarNum(0);
            }
            // 跳转到详情页面
            holder.mtvAddFavor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initTurnToDetail(subjectEntity, subjectType, subjectDescriptionHtml);
                }
            });
/*            holder.mvName.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    initTurnToDetail(subjectEntity, subjectType, subjectDescriptionHtml);
                    return false;
                }
            });*/
        }
    }

    public void initTurnToDetail(SubjectEntity subjectEntity, SubjectType subjectType, String subjectDescriptionHtml) {
        String referSubjectId = ""; // 这边要改
        //String referSubjectId = subjectEntity.getReferSubjectId();
        Bundle bundle = new Bundle();
        bundle.putString("me", "meWord");
        if (subjectType.getTypeId().equals("1")) { // 1是选择题
            bundle.putString("subjectTitle", subjectEntity.getSubjectDescription());
        }
        if (subjectType.getTypeId().equals("2") || subjectType.getTypeId().equals("3") || subjectType.getTypeId().equals("4")) {
            bundle.putString("subjectTitle", subjectDescriptionHtml); // 针对 typeId 为2、3、4的情况，不写else 以防以后出现其他情况
        }
        bundle.putString("referSubjectId", referSubjectId); //这边过后改掉，不需要再根据referSubjectId去请求详细解析
        bundle.putString("subjectCatalog", subjectCatalog);
        if (TextUtils.isEmpty(referSubjectId)) {
            String detailsAnswerHtml = subjectEntity.getDetailsAnswerHtml(); // 获取题目解析
            bundle.putString("detailsAnswerHtml", detailsAnswerHtml);
        }
        CommonUtil.openActivity(mContext, SubjectDetailActivity.class, bundle);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mathView)
        AskMathView mathView;
        @BindView(R.id.ratingBar)
        StarView ratingBar;
        @BindView(R.id.layout_option)
        LinearLayout layout_option;
        @BindView(R.id.item_layout)
        RelativeLayout item_layout;
        @BindView(R.id.recyclerView)
        RecyclerView mRecyclerView;
        @BindView(R.id.multiStateView)
        MultiStateView multiStateView;
        @BindView(R.id.tv_subject)
        TextView mtv_subject;
        @BindView(R.id.tvAddFavor)
        TextView mtvAddFavor;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mathView.formatMath().showWebImage();
            mathView.setWebViewClient(new WebAppClient(mContext, multiStateView, mathView.getMathWebView()));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }
}
