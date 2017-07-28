package com.asking91.app.ui.adapter.onlineqa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.onlineqa.OnlineQADetailEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.OnlineQAConstant;
import com.asking91.app.ui.onlineqa.OnlineQADetailActivity;
import com.asking91.app.ui.onlineqa.OnlineQaAnwserActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.JLog;

/**
 * Created by wxiao on 2016/10/31.
 */

public class OnlineQADetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    //    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private View headView, normarlView;

    //---数学公式测试
//    private String js = "<script src=\"http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=default\"></script>";
//    private String tex = "This come from string. You can insert inline formula:" +
//            " \\(ax^2 + bx + c = 0\\) " +
//            "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$";
    private Context mContext;
    private OnlineQADetailEntity onlineQADetailEntity;
    private LayoutInflater inflater;
    private boolean flag;

    public OnlineQADetailAdapter(Context context, OnlineQADetailEntity onlineQADetailEntity, boolean flag) {
        this.mContext = context;
        this.onlineQADetailEntity = onlineQADetailEntity;
        inflater = LayoutInflater.from(mContext);
        this.flag = flag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            headView = inflater.inflate(R.layout.onlineqa_detail_head, parent, false);
            return new AskViewHolder(headView);
        }else {
            normarlView = inflater.inflate(R.layout.item_onlineqa_detail, parent, false);
            return new AskViewHolder(normarlView);
        }
    }

    private TextView text;
    //更新
    public void updateAnswerSize(String anserId){
        if(!((OnlineQADetailActivity)mContext).getUserConstant().getUserId().equals(onlineQADetailEntity.getUserId())){//是自己就是追问，不需要+1
            //不是自己回答，看问题Id是不是null
            if(null==anserId||anserId.length()==0){
                int size = Integer.parseInt(String.valueOf(text.getText().toString()));
                if(size!=0) {
                    text.setText(String.valueOf(Integer.parseInt(String.valueOf(text.getText().toString())) + 1));
                }else{
                    text.setText("1");
                }
            }
        }
    }

    public void insertData(OnlineQADetailEntity entity){
        int preSize = onlineQADetailEntity.getList().size();
        for(int i=preSize;i<entity.getList().size();i++){
            onlineQADetailEntity.getList().add(entity.getList().get(i));
        }
        //变化添加
        notifyItemRangeChanged(preSize, entity.getList().size()-preSize);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mHolder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_NORMAL) {
                setMessageViewData((AskViewHolder)mHolder, onlineQADetailEntity.getList().get(position - 1), position);
        } else if (type == TYPE_HEADER) {
                setHeadViewData((AskViewHolder)mHolder, onlineQADetailEntity, position);
        }
    }


    @Override
    public int getItemCount() {
        if(onlineQADetailEntity==null){
            return 0;
        }
        if(onlineQADetailEntity.getList()==null){
            return 1;
        }
        return onlineQADetailEntity.getList().size() + 1;
    }

    class AskViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout replyTop;
        TextView title;
        TextView versionTv;
        TextView ask;
        TextView a;
        TextView anwserTip;

        AskSimpleDraweeView userImgIv;
        TextView userName;
        TextView time;
        LinearLayout answerMore;
        TextView go;
        TextView ok;
        MultiStateView multiStateView;
        AskMathView maxHeightWebView;
        AskMathView mathView;
        LinearLayout bottom;

        ImageView adoptImg;

        public AskViewHolder(View itemView) {
            super(itemView);
            userImgIv = (AskSimpleDraweeView) itemView.findViewById(R.id.user_img_iv);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            time = (TextView) itemView.findViewById(R.id.time);
            if(itemView==headView){
                replyTop = (RelativeLayout) itemView.findViewById(R.id.reply_top);
                if(!flag){
                    replyTop.setVisibility(View.VISIBLE);
                }else{
                    replyTop.setVisibility(View.GONE);
                }
                title = (TextView) itemView.findViewById(R.id.title);
                maxHeightWebView = (AskMathView) itemView.findViewById(R.id.mathView);
//                maxHeightWebView.setMaxHeight(500);
                maxHeightWebView.formatMath().showWebImage(multiStateView);
                multiStateView = (MultiStateView) itemView.findViewById(R.id.multiStateView);
                versionTv = (TextView) itemView.findViewById(R.id.version_tv);
                ask = (TextView) itemView.findViewById(R.id.ask);
                a = (TextView) itemView.findViewById(R.id.a);
                anwserTip = (TextView) itemView.findViewById(R.id.anwser_tip);
            }else if(normarlView==itemView){
                multiStateView = (MultiStateView) itemView.findViewById(R.id.multiStateView);
                mathView = (AskMathView) itemView.findViewById(R.id.mathView);
                mathView.formatMath().showWebImage(multiStateView);
                adoptImg = (ImageView) itemView.findViewById(R.id.adoptImg);
//                answerContent = (TextView) itemView.findViewById(R.id.answer_content);
                go = (TextView) itemView.findViewById(R.id.go);
                ok = (TextView) itemView.findViewById(R.id.ok);
                bottom = (LinearLayout) itemView.findViewById(R.id.bottom);
                if(flag){
                    bottom.setVisibility(View.VISIBLE);
                }else{
                    bottom.setVisibility(View.GONE);
                }
            }
        }
    }

    private void setHeadViewData(final AskViewHolder holder, final OnlineQADetailEntity e, int position) {
        holder.userImgIv.setImageUrl(Uri.decode(Constants.USER_AVATAR + e.getUserId()));
        holder.userName.setText(e.getUserName());
        holder.time.setText(e.getCreateDateFmt());
        holder.title.setText(e.getTitle());
        String action = e.getKm();
        if (action != null) {
            String level = "";
            if (null != e.getLevelId()) {
                int n = Integer.parseInt(e.getLevelId())-7;
                if (n < OnlineQAConstant.versionTv.length) {
                    level = OnlineQAConstant.versionTv[n];
                }
            }
            if (action.indexOf("M") != -1) {
                holder.versionTv.setText("【 数学-" + level + "】");
            } else if (action.indexOf("P") != -1) {
                holder.versionTv.setText("【 物理-" + level + "】");
            }
        }
//        if (e.getDescription() == null) {
//            holder.multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
//        } else {
//            holder.mvName.setText(e.getDescription());
            holder.maxHeightWebView.setWebText(e.getDescription());
//        }
        holder.ask.setText(e.getCaifu() + "");
        holder.a.setText(e.getAnswerSize() + "");
        holder.anwserTip.setText("回答（共 " + e.getAnswerSize() + " 条）");
        text = holder.a;
    }
    private void setMessageViewData(final AskViewHolder holder, final OnlineQADetailEntity.AnwserEntity e, int position) {
        holder.userImgIv.setImageUrl(Constants.USER_AVATAR + e.getUserId());
        holder.userName.setText(e.getUserName());
        holder.time.setText(e.getCreateDateFmt());
        if (e.getContent() == null) {
            holder.multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else {
            holder.mathView.setText(e.getContent());
        }

        if(flag&&onlineQADetailEntity.getState().equals("1")){//未被采纳
            holder.bottom.setVisibility(View.VISIBLE);
            holder.go.setTag(R.id.key_id, onlineQADetailEntity.getId());//题目ID
            holder.go.setTag(R.id.key_anserId, e.getId());//回答的ID
            holder.go.setTag(R.id.key_position, position);
            holder.go.setOnClickListener(new View.OnClickListener() {//追问
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OnlineQaAnwserActivity.class);
                    intent.putExtra("id", String.valueOf(v.getTag(R.id.key_id)));
                    intent.putExtra("anserId", String.valueOf(v.getTag(R.id.key_anserId)));
                    intent.putExtra("userId", onlineQADetailEntity.getUserId());//用户ID
                    mContext.startActivity(intent);
                }
            });
            holder.ok.setTag(R.id.key_id, onlineQADetailEntity.getId());//题目ID
            holder.ok.setTag(R.id.key_anserId, e.getId());//回答的ID
            holder.ok.setTag(R.id.key_position, position);
            holder.ok.setOnClickListener(new View.OnClickListener() {//采纳
                @Override
                public void onClick(View v) {
                    ((OnlineQADetailActivity)mContext).adoptAnswer(String.valueOf(v.getTag(R.id.key_id)),
                            String.valueOf(v.getTag(R.id.key_anserId)), Integer.parseInt(String.valueOf(R.id.key_position)));
                }
            });
        }else if(onlineQADetailEntity.getState().equals("2")){//已采纳
            holder.bottom.setVisibility(View.GONE);
        }
        holder.adoptImg.setVisibility(e.isAdopt() ? View.VISIBLE : View.GONE);
//        holder.answerContent.setText(e.getContent());

    }

}
