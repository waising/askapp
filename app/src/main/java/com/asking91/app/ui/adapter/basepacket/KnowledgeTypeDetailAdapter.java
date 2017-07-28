package com.asking91.app.ui.adapter.basepacket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.KnowledgeTypeDetailEntity;
import com.asking91.app.ui.basepacket.details.KnowledgeTypeDetailActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.JLog;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/11/7.
 */
public class KnowledgeTypeDetailAdapter extends RecyclerView.Adapter<KnowledgeTypeDetailAdapter.ViewHolder>{

    private static final int UNSELECTED = -1;

    private List<KnowledgeTypeDetailEntity> mDatas;
    private Context mContext;

    private RecyclerView recyclerView;
    private int selectedItem = UNSELECTED;

    private int prePosition = -1;

    public KnowledgeTypeDetailAdapter(Context context, List<KnowledgeTypeDetailEntity> datas, RecyclerView recyclerView){
        this.mContext = context;
        this.mDatas = datas;
        this.recyclerView = recyclerView;
    }

    @Override
    public KnowledgeTypeDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new KnowledgeTypeDetailAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_knowledge_type_detail_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final KnowledgeTypeDetailAdapter.ViewHolder holder, int position) {

        holder.position = position;
        final KnowledgeTypeDetailEntity item = mDatas.get(position);

        holder.mTypeNameTv.setText(item.getTabTypeName());
        holder.mMathView.setText(item.getTabContentHtml());

        holder.mImageView.setSelected(false);
        holder.mExpandableLayout.collapse(false);


        holder.mImageView.setImageResource(R.mipmap.attr_right);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.expandable_layout)
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

        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mMathView.formatMath().showWebImage(msView);
            mRl.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            KnowledgeTypeDetailAdapter.ViewHolder holder = (KnowledgeTypeDetailAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
            if (holder != null) {
                holder.mImageView.setSelected(false);
                holder.mExpandableLayout.collapse();
            }
            if (position == selectedItem) {
                selectedItem = UNSELECTED;
            } else {
                mImageView.setSelected(true);
                mExpandableLayout.expand();
                selectedItem = position;
            }

            mImageView.setImageResource(R.mipmap.attr_down);

            setItemImg();
            prePosition = selectedItem;

            ((KnowledgeTypeDetailActivity)mContext).setImgStatus();
        }
    }

    public void setItemImg(){
        if (prePosition > -1) {
            KnowledgeTypeDetailAdapter.ViewHolder holder = (KnowledgeTypeDetailAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(prePosition);
            holder.mImageView.setImageResource(R.mipmap.attr_right);

            notifyItemChanged(prePosition);
        }

    }
}
