package com.asking91.app.ui.adapter.supertutorial;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.asking91.app.R;
import com.asking91.app.entity.supertutorial.SuperClassQuestionTimeEntity;
import com.asking91.app.ui.supertutorial.buy.superclass.SuperBuySuperClassQuestionTimeFragment;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.MusicPlayer;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/11/7.
 */
public class SuperClassQuestionTimeAdapter extends RecyclerView.Adapter<SuperClassQuestionTimeAdapter.ViewHolder> {

    private static final int UNSELECTED = -1;
    private List<SuperClassQuestionTimeEntity> mDatas;
    private Context mContext;
    private RecyclerView recyclerView;
    private int selectedItem = UNSELECTED;
    private MusicPlayer musicPlayer;
    int prePosition = -1;
    private Fragment fragment;
    private Map<Integer, ExpandableLayout> exLays;

    public SuperClassQuestionTimeAdapter(Context context, List<SuperClassQuestionTimeEntity> datas,
                                         RecyclerView recyclerView, MusicPlayer musicPlayer, Fragment fragment) {
        this.mContext = context;
        this.mDatas = datas;
        this.recyclerView = recyclerView;
        this.musicPlayer = musicPlayer;
        this.fragment = fragment;
        exLays = new ArrayMap<>();
    }

    @Override
    public SuperClassQuestionTimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuperClassQuestionTimeAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_supertutorial_superclass_questiontime, parent, false));
    }

    @Override
    public void onBindViewHolder(final SuperClassQuestionTimeAdapter.ViewHolder holder, final int position) {
        holder.position = position;
        final SuperClassQuestionTimeEntity item = mDatas.get(position);
        holder.titleMathView.setSelected(true);
        holder.titleMathView.setWebViewTextSize(14);
        holder.titleMathView.setText("问题" + (position + 1) + "：" + item.getTipQuestionDataName());
        holder.mImageView.setSelected(false);
        holder.mExpandableLayout.collapse(false);
        exLays.put(position, holder.mExpandableLayout);
        holder.mMathView.setText(item.getTipQuestionDataContentHtml());
        holder.mImageView.setImageResource(R.mipmap.attr_right);
        holder.voice.setTag(R.id.key_position, position);
        holder.voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment instanceof SuperBuySuperClassQuestionTimeFragment) {
                    ((SuperBuySuperClassQuestionTimeFragment) fragment).playVoice(Integer.parseInt(v.getTag(R.id.key_position) + ""),holder.voice);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.expandable_layout)
        ExpandableLayout mExpandableLayout;
        @BindView(R.id.titleMathView)
        AskMathView titleMathView;
        @BindView(R.id.multiStateView)
        MultiStateView msView;
        @BindView(R.id.mathView)
        AskMathView mMathView;
        @BindView(R.id.expand_iv)
        ImageView mImageView;
        @BindView(R.id.expand_rl)
        RelativeLayout mRl;
        @BindView(R.id.voice)
        AskSimpleDraweeView voice;

        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mMathView.formatMath().showWebImage(msView);
            titleMathView.formatMath();
            voice.setBackgroundResource(R.mipmap.super_talking_voice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SuperClassQuestionTimeAdapter.ViewHolder holder = (SuperClassQuestionTimeAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
            if (holder != null) {
                holder.mImageView.setSelected(false);
                holder.mExpandableLayout.collapse();
            }
            if (position == selectedItem) {
                selectedItem = UNSELECTED;
            } else {//展开
                mImageView.setSelected(true);
                mExpandableLayout.expand();
                selectedItem = position;
            }
            mImageView.setImageResource(R.mipmap.attr_down);
            setItemImg(prePosition);
            prePosition = selectedItem;
        }
    }

    public void setItemImg(int prePosition) {
        if (prePosition > -1) {
            exLays.get(prePosition).collapse();
            ((ImageView) ((LinearLayout) exLays.get(prePosition).getParent()).findViewById(R.id.expand_iv)).setImageResource(R.mipmap.attr_right);
        }

    }
}
