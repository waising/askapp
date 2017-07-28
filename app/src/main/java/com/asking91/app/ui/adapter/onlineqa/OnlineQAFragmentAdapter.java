package com.asking91.app.ui.adapter.onlineqa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.onlineqa.OnlineQAListEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.OnlineQAConstant;
import com.asking91.app.ui.onlineqa.OnlineQAActivity;
import com.asking91.app.ui.onlineqa.OnlineQADetailActivity;
import com.asking91.app.ui.onlineqa.OnlineQaAnwserActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wxiao on 2016/10/31.
 */

public class OnlineQAFragmentAdapter extends RecyclerView.Adapter<OnlineQAFragmentAdapter.MessageViewHolder> {
    private Context mContext;
    private List<OnlineQAListEntity> mMessageList;
    private LayoutInflater inflater;

    public OnlineQAFragmentAdapter(Context context, List<OnlineQAListEntity> messageList) {
        this.mContext = context;
        this.mMessageList = messageList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_onlineqalist, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {
        final OnlineQAListEntity e = mMessageList.get(position);
        holder.layout.setTag(R.id.key_position, position);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OnlineQADetailActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("position", Integer.parseInt(String.valueOf(v.getTag(R.id.key_position))));
                intent.putExtra("id", e.getId());
                intent.putExtra("userId", e.getUserId());
                mContext.startActivity(intent);
            }
        });

        BitmapUtil.displayUserImage(mContext, Constants.USER_AVATAR + e.getUserId(), holder.userImgIv);
        //   holder.userImgIv.setImageUrl(Constants.USER_AVATAR + e.getUserId());
        holder.userName.setText(e.getUserName());
        holder.time.setText(e.getCreateDateFmt());
        holder.title.setText(e.getTitle());
        String action = e.getKm();
        if (action != null) {
            String level = "";
            if (null != e.getLevelId()) {
                int n = Integer.parseInt(e.getLevelId()) - 7;
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
        if (e.getDescription() == null) {
            holder.multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else {
            holder.mathView.setText(e.getDescription());
        }
        holder.ask.setText(e.getCaifu() + "");
        holder.a.setText(e.getAnswerSize() + "");
        holder.answer.setTag(R.id.key_position, position);
        if (!e.getUserId().equals(((OnlineQAActivity) mContext).getUserConstant().getUserId())) {//不是自己，就可以回答；自己要去详情里面追问
            holder.answer.setText("立刻回答");
            holder.answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 立刻回答
                    Intent intent = new Intent(mContext, OnlineQaAnwserActivity.class);
                    intent.putExtra("type", 0);
                    intent.putExtra("position", Integer.parseInt(String.valueOf(v.getTag(R.id.key_position))));
                    intent.putExtra("id", e.getId());
                    intent.putExtra("userId", e.getUserId());
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.answer.setText("查看详情");
            holder.answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //查看详情
                    Intent intent = new Intent(mContext, OnlineQADetailActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("position", Integer.parseInt(String.valueOf(v.getTag(R.id.key_position))));
                    intent.putExtra("id", e.getId());
                    intent.putExtra("userId", e.getUserId());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_img_iv)
        ImageView userImgIv;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.title_top)
        ImageView titleTop;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.mathView)
        AskMathView mathView;
        @BindView(R.id.multiStateView)
        MultiStateView multiStateView;
        @BindView(R.id.version_tv)
        TextView versionTv;
        @BindView(R.id.ask)
        TextView ask;
        @BindView(R.id.a)
        TextView a;
        @BindView(R.id.answer)
        TextView answer;
        @BindView(R.id.layout)
        LinearLayout layout;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            mvName.setMaxHeight(250);
            mathView.formatMath().showWebImage(multiStateView);
        }
    }
}
