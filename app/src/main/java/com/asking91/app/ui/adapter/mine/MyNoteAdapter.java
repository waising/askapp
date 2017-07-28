package com.asking91.app.ui.adapter.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.onlineqa.OnlineQADetailEntity;
import com.asking91.app.ui.mine.mynote.MyNoteDetailActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.util.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 我的笔记adapter
 * Created by zqshen on 2016/11/24.
 */

public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteAdapter.MessageViewHolder> {

    private Context mContext;
    List<OnlineQADetailEntity.AnwserEntity> list;

    public MyNoteAdapter(Context context, List<OnlineQADetailEntity.AnwserEntity> list) {
        this.mContext = context;
        this.list = list;
    }

    public void setData(List<OnlineQADetailEntity.AnwserEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mynote, parent, false));
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {
        if (list != null && list.size() > 0) {
            // 笔记内容
            final String content = list.get(position).getContent();
            String createTimeFmt = list.get(position).getCreateTimeFmt(); // 笔记创建时间
            // 笔记ID
            final String id = list.get(position).getId();

            if (content != null) {
                holder.mathView.setText(content);
            } else {
                holder.mathView.setText("");
            }
            if (createTimeFmt != null) {
                holder.mTvDate.setText(createTimeFmt);
            } else {
                holder.mTvDate.setText("");
            }
            holder.imgEdit.setOnClickListener(new View.OnClickListener() {//跳转修改笔记页面
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    bundle.putString("content", content);
                    CommonUtil.openActivity(mContext, MyNoteDetailActivity.class, bundle);
                }
            });
            holder.layout.setOnClickListener(new View.OnClickListener() {//跳转修改笔记页面
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    bundle.putString("content", content);
                    CommonUtil.openActivity(mContext, MyNoteDetailActivity.class, bundle);
                }
            });

/*            holder.mvName.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    bundle.putString("content", content);
                    CommonUtil.openCameraActivity(mContext, MyNoteDetailActivity.class, bundle);
                    return false;
                }
            });*/

        }

    }

    CallDelNote CallDelNote;

    public void startDelNote(CallDelNote CallDelNote) {
        this.CallDelNote = CallDelNote;
    }

    public interface CallDelNote {
        void delNote(int currentPosition);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.mathView)
        AskMathView mathView;
        @BindView(R.id.img_edit)
        ImageView imgEdit;
        @BindView(R.id.img_del)
        ImageView imgDel;
        @BindView(R.id.layout)
        LinearLayout layout;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.img_edit, R.id.img_del, R.id.mathView})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_edit:
                    // OnlineQADetailEntity.AnwserEntity anwserEntity = list.get(getAdapterPosition());
                    break;
                case R.id.img_del://删除按钮
                    CallDelNote.delNote(getAdapterPosition());
                    break;
                case R.id.mathView:
                   // ToastUtil.showMessage("测试 mvName ");  // 这个没效果
                    break;
            }
        }

    }
}
