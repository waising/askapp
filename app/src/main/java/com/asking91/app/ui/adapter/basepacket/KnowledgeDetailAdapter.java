package com.asking91.app.ui.adapter.basepacket;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.KnowledgeDetailEntity;
import com.asking91.app.global.UserConstant;
import com.asking91.app.ui.basepacket.comment.CommentActivity;
import com.asking91.app.ui.basepacket.comment.CommentListActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.CommonUtil;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/10/27.
 */

public class KnowledgeDetailAdapter extends RecyclerView.Adapter<KnowledgeDetailAdapter.ViewHolder> {

    private static final int UNSELECTED = -1;

    private List<KnowledgeDetailEntity> mDatas;
    private Context mContext;

    private RecyclerView recyclerView;
    private int selectedItem = UNSELECTED;

    private String tipId;

    public int getReviewSize() {
        return reviewSize;
    }

    public void setReviewSize(int reviewSize) {
        this.reviewSize = reviewSize;
    }

    int reviewSize = 0;
    int prePosition = -1;

    public KnowledgeDetailAdapter(Context context, List<KnowledgeDetailEntity> datas,RecyclerView recyclerView){
        this.mContext = context;
        this.mDatas = datas;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_knowledge_detail_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.position = position;
        final KnowledgeDetailEntity item = mDatas.get(position);

        holder.mTypeNameTv.setText(item.getTypeName());
        holder.mMathView.setText(item.getContentHtml());

        holder.mImageView.setSelected(false);
        holder.mExpandableLayout.collapse(false);


        holder.mImageView.setImageResource(R.mipmap.attr_right);

        if (!TextUtils.isEmpty(item.getTipId())){
            tipId = item.getTipId();
        }

        if(!UserConstant.getInstance(mContext).isTokenLogin()){
            holder.mCommentBtn.setVisibility(View.GONE);
        }

        if (reviewSize>0){
            holder.mCommentListBtn.setVisibility(View.VISIBLE);
            holder.mCommentListBtn.setText(holder.commentList+"("+reviewSize+")");
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.expandable_layout)//展开布局
        ExpandableLayout mExpandableLayout;
        @BindView(R.id.type_name_tv)
        TextView mTypeNameTv;
        @BindView(R.id.multiStateView)
        MultiStateView msView;
        @BindView(R.id.mathView)
        AskMathView mMathView;
        @BindView(R.id.expand_iv)
        ImageView mImageView;
        @BindView(R.id.expand_rl)
        RelativeLayout mRl;
        @BindView(R.id.comment_btn)
        Button mCommentBtn;
        @BindView(R.id.comment_list_btn)
        Button mCommentListBtn;
        @BindString(R.string.comment_list)
        String commentList;

        int position;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mMathView.showWebImage(mContext,msView).formatMath();

            mRl.setOnClickListener(this);
            mCommentBtn.setOnClickListener(this);
            mCommentListBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

                switch (v.getId()){
                    case R.id.comment_btn:
                        Bundle bundle = new Bundle();
                        bundle.putString("typeName",mDatas.get(position).getTypeName());
                        bundle.putString("contentHtml",mDatas.get(position).getContentHtml());
                        if(TextUtils.isEmpty(mDatas.get(position).getTipId())){
                            bundle.putString("tipId",String.valueOf(mDatas.get(position).getAttrId()));
                        }else{
                            bundle.putString("tipId",mDatas.get(position).getTipId());
                        }
                        CommonUtil.openActivity(mContext, CommentActivity.class,bundle);
                        break;
                    case R.id.comment_list_btn:
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("tipId",tipId);
                        CommonUtil.openActivity(mContext, CommentListActivity.class,bundle1);
                        break;
                    case R.id.expand_rl://展开点击
                        ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
                        if (holder != null){
                            holder.mImageView.setSelected(false);
                            holder.mExpandableLayout.collapse();//折叠
                        }
                        if (position == selectedItem){
                            selectedItem = UNSELECTED;
                        }else{
                            mImageView.setSelected(true);//加减号按钮
                            mExpandableLayout.expand();//展开
                            selectedItem = position;
                        }

                        mImageView.setImageResource(R.mipmap.attr_down);//减号

                        if(prePosition>-1){
                            ViewHolder holder1 = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(prePosition);
                            holder1.mImageView.setImageResource(R.mipmap.attr_right);

                            notifyItemChanged(prePosition);
                        }

                        prePosition = selectedItem;
                        break;
            }


        }
    }
}
