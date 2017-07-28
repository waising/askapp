package com.asking91.app.ui.adapter.basepacket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.CommentEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/11/7.
 */
public class KnowledgeCommentAdapter extends RecyclerView.Adapter<KnowledgeCommentAdapter.ViewHolder> {

    private Context mContext;
    List<CommentEntity> mDatas;

    public KnowledgeCommentAdapter(Context context, List<CommentEntity> datas){
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new KnowledgeCommentAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CommentEntity commentEntity = mDatas.get(position);
//        holder.userImgIv.
        holder.userNameTv.setText(commentEntity.getUserName());
        holder.commentTv.setText(commentEntity.getComment());
        holder.commentTimeTv.setText(commentEntity.getCreateTimeFmt());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.user_img_iv)
        ImageView userImgIv;

        @BindView(R.id.user_name_tv)
        TextView userNameTv;

        @BindView(R.id.comment_tv)
        TextView commentTv;

        @BindView(R.id.coment_time_tv)
        TextView commentTimeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
