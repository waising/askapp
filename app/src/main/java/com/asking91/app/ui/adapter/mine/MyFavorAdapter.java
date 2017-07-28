package com.asking91.app.ui.adapter.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.AnswerOption;
import com.asking91.app.entity.basepacket.CommentEntity;
import com.asking91.app.entity.basepacket.SubjectEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.ui.basepacket.details.KnowledgeDetailActivity;
import com.asking91.app.ui.refer.ReferDetailActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.StarView;
import com.asking91.app.util.CommonUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 我的收藏adapter
 * Created by zqshen on 2016/12/09.
 */

public class MyFavorAdapter extends SwipeMenuAdapter<MyFavorAdapter.ViewHolder> {

    private Context mContext;
    int subjectCatalog;

    List<CommentEntity> commentEntity;
    List<SubjectEntity> subjectDetailEntity;

    public MyFavorAdapter(Context context, int subjectCatalog, List<CommentEntity> commentEntity, List<SubjectEntity> subjectDetailEntity) {
        this.mContext = context;
        this.subjectCatalog = subjectCatalog;
        this.commentEntity = commentEntity;
        this.subjectDetailEntity = subjectDetailEntity;
    }

    public void setData(int subjectCatalog, List<CommentEntity> commentEntity, List<SubjectEntity> subjectDetailEntity) {
        this.subjectCatalog = subjectCatalog;
        this.commentEntity = commentEntity;
        this.subjectDetailEntity = subjectDetailEntity;
        notifyDataSetChanged();
    }

    public void removeCommentEntityItem(int index) {

        if (subjectCatalog == Constants.Collect.title) {
            // 收藏的题目
            subjectDetailEntity.remove(index);
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, getItemCount());
        } else {
            //　收藏的知识点和资讯
            commentEntity.remove(index);
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, getItemCount());
        }

    }

    public String getCommentEntityItemId(int index) {
        return commentEntity.get(index).getId();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myfavorite_list, parent, false);
    }

    @Override
    public ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new ViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // holder.position = position;
        switch (subjectCatalog) {
            case Constants.Collect.knowledge:
                // 知识点
                CommentEntity item = commentEntity.get(position);
                final String tille = item.geTtille(); // 标题
                holder.expandRl.setVisibility(View.GONE); // 题目的
                holder.mExpandableLayout.setVisibility(View.GONE); // 展开的
                holder.linearLayout.setVisibility(View.VISIBLE);
                holder.mtvTime.setVisibility(View.GONE);
                if (tille != null) {
                    holder.mtvTitle.setText(tille);
                } else {
                    holder.mtvTitle.setText("");
                }
                break;
            case Constants.Collect.refer:
                // 教育资讯
                if (commentEntity != null && commentEntity.size() > 0) {
                    CommentEntity itemRefer = commentEntity.get(position);
                    final String tilleRefer = itemRefer.geTtille(); // 标题
                    holder.expandRl.setVisibility(View.GONE);
                    holder.mExpandableLayout.setVisibility(View.GONE); // 展开的
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    String createTimeFmt = itemRefer.getCreateTimeFmt(); // 资讯的时间
                    holder.mtvTime.setVisibility(View.VISIBLE);
                    if (createTimeFmt != null) {
                        holder.mtvTime.setText(createTimeFmt);
                    } else {
                        holder.mtvTime.setText("");
                    }
                    if (tilleRefer != null) {
                        holder.mtvTitle.setText(tilleRefer);
                    } else {
                        holder.mtvTitle.setText("");
                    }
                }
                break;
            case Constants.Collect.title:
                // 题目
/*                    holder.mExpandableLayout.collapse(false);
                    holder.mMathView.setWebChromeClient(new WebChromeClient() {

                        @Override
                        public void onProgressChanged(WebView view, int newProgress) {
                            JLog.i("WebChromeClient", "onProgressChanged=" + newProgress);
                            super.onProgressChanged(view, newProgress);
                            if (newProgress >= 100) {
                                holder.msView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                                view.setVisibility(View.VISIBLE);
                            } else {
                                holder.msView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                                view.setVisibility(View.INVISIBLE);
                            }
                        }
                    });*/

                if (subjectDetailEntity != null && subjectDetailEntity.size() > 0) {
                    holder.linearLayout.setVisibility(View.GONE);
                    holder.expandRl.setVisibility(View.VISIBLE);
                    holder.mExpandableLayout.setVisibility(View.VISIBLE); // 展开的
                    SubjectEntity info = subjectDetailEntity.get(position);
                    if (info != null) {
                        String subjectDescriptionHtml = info.getSubjectDescriptionHtml(); // 题目
                        List<AnswerOption> answerOptions = info.getAnswerOptions(); // 题目选项
                        int difficulty = info.getDifficulty();
                        String strOptionContent = "";
                        if (answerOptions != null && answerOptions.size() > 0) {
                            for (int i = 0; i < answerOptions.size(); i++) {
                                String optionName = answerOptions.get(i).getOptionName();
                                String optionContentHtml = answerOptions.get(i).getOptionContentHtml();
                                if (optionContentHtml != null && optionContentHtml.contains("</p>")) {
                                    strOptionContent += (optionName + "、" + optionContentHtml.substring(3, optionContentHtml.length() - 4) + "<br/>");
                                } else {
                                    strOptionContent += (optionName + "、" + optionContentHtml + "<br/>");
                                }
                            }
                        }
                        if (subjectDescriptionHtml != null) {//题目+选项
                            holder.mMathViewTitle.setText(subjectDescriptionHtml + strOptionContent);
                        } else {//没有题目的，只显示选项
                            holder.mMathViewTitle.setText(strOptionContent);
                        }
                        // 设置难度值
                        if (difficulty > 0) {
                            holder.ratingBar.setmStarNum(difficulty);
                        } else {
                            holder.ratingBar.setmStarNum(0);
                        }
                        holder.mMathView.setText(info.getDetailsAnswerHtml());//解析
                    }

                    //查看解析
                    holder.checked_detail.setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View view) {
                            holder.checked_detail.setSelected(!holder.checked_detail.isSelected());

                            if (holder.checked_detail.isSelected()) {
                                holder.mExpandableLayout.expand();
                            } else {
                                holder.mExpandableLayout.collapse();
                            }

                        }
                    });


                }


                break;
        }
    }

    @Override
    public int getItemCount() {
        if (subjectCatalog == Constants.Collect.title) {
            return subjectDetailEntity.size(); // 收藏的题目
        } else {
            return commentEntity.size();  //　收藏的知识点和资讯
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_see)
        RelativeLayout layout_see;//查看解析
        @BindView(R.id.mathView_title)
        AskMathView mMathViewTitle;
        @BindView(R.id.mathView)
        AskMathView mMathView;
        @BindView(R.id.multiStateView)
        MultiStateView msView;
        @BindView(R.id.expandable_layout)
        ExpandableLayout mExpandableLayout;
        @BindView(R.id.ratingBar)
        StarView ratingBar;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;
        @BindView(R.id.tv_title)
        TextView mtvTitle;
        @BindView(R.id.tv_time)
        TextView mtvTime;
        @BindView(R.id.expand_rl)
        RelativeLayout expandRl;
        @BindView(R.id.checked_detail)
        TextView checked_detail;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mMathView.showWebImage(mContext, msView).formatMath();
            mMathViewTitle.showWebImage(mContext, msView).formatMath();
        }

        @OnClick({R.id.linearLayout})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linearLayout:
                    // ToastUtil.showMessage("我我我", Toast.LENGTH_SHORT);
                    String objId = commentEntity.get(getAdapterPosition()).getObjId();
                    String tille = commentEntity.get(getAdapterPosition()).geTtille();
                    // 知识点
                    if (!TextUtils.isEmpty(objId) && subjectCatalog == Constants.Collect.knowledge) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "知识详情");
                        bundle.putString("knowledge", tille);
                        bundle.putString("id", objId);
                        bundle.putString("me", "searchDetail");
                        CommonUtil.openAuthActivity(mContext, KnowledgeDetailActivity.class, bundle);
                    }
                    // 教育资讯
                    if (!TextUtils.isEmpty(objId) && subjectCatalog == Constants.Collect.refer) {
                        Bundle bundle = new Bundle();
                        bundle.putString("referId", objId);
                        CommonUtil.openActivity(mContext, ReferDetailActivity.class, bundle);
                    }
                    break;
            }
        }
    }
}
