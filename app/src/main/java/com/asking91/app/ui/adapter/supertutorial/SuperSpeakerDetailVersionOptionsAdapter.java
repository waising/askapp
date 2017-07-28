package com.asking91.app.ui.adapter.supertutorial;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.AnswerOption;
import com.asking91.app.entity.onlinetest.ResultEntity;
import com.asking91.app.ui.widget.AskMathView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/10/27.
 */

public class SuperSpeakerDetailVersionOptionsAdapter extends RecyclerView.Adapter<SuperSpeakerDetailVersionOptionsAdapter.ViewHolder> {

    private static final int UNSELECTED = -1;
    private List<AnswerOption> mDatas;
    private Context mContext;
    private int selectedItem = UNSELECTED;
    private String tipId;
    int reviewSize = 0;
    int prePosition = -1;
    private int positionParent;
    /**是否提交过*/
    private boolean isSubmit;
    /**之前选择的背景框*/
    private View preView;

    private Map<Integer, RelativeLayout> lays ;

    public SuperSpeakerDetailVersionOptionsAdapter(int positionParent, Context context, List<AnswerOption> datas) {
        this.positionParent = positionParent;
        this.mContext = context;
        this.mDatas = datas;
        lays = new ArrayMap<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_supertutorial_buy_clearance_topic_ask_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AnswerOption optionsBean = mDatas.get(position);
        String optionName = optionsBean.getOptionName();
        holder.mTopicOptionTv.setText(optionName);
        holder.mTopicOptionLy.setTag(R.id.supertutorial_option_key1, position);
        holder.mTopicOptionLy.setTag(R.id.supertutorial_option_key2, optionsBean.getOptionName());
        lays.put(position, holder.mTopicOptionLy);
        holder.mTopicOptionLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSubmit) {
                    //设置选择，无需付界面来控制，只需传值回去给父控件
                    if (prePosition == -1 ){
                        prePosition = Integer.parseInt(v.getTag(R.id.supertutorial_option_key1) + "");
                    }else if(prePosition != Integer.parseInt(v.getTag(R.id.supertutorial_option_key1) + "")) {
                        lays.get(prePosition).setBackgroundResource(R.mipmap.rech_bg);//初始化背景
                        prePosition = Integer.parseInt(v.getTag(R.id.supertutorial_option_key1) + "");
                    }
                    v.setBackgroundResource(R.mipmap.wrong_rech_bg);//设置当前背景
                }
            }
        });
        holder.mTopicOptionMv.setText(optionsBean.getOptionContent());
    }
    /**更新结果*/
    public void updateResult(ResultEntity resultEntity){
        isSubmit = true;//设置已经提交过
        for(int i=0;i<mDatas.size();i++) {
            boolean isOption = resultEntity.getAnswerResult().equals(mDatas.get(i).getOptionName());
            boolean isRight = resultEntity.getAnswerResult().equals(resultEntity.getRightResult());
            if (isOption) {
                ImageView mStateIv = ((ImageView)lays.get(i).findViewById(R.id.ask_state_iv));
                if (isRight) {
                    lays.get(i).setBackgroundResource(R.mipmap.right_rech_bg);
                    mStateIv.setImageResource(R.mipmap.ask_right);
                } else {
                    lays.get(i).setBackgroundResource(R.mipmap.wrong_rech_bg);
                    mStateIv.setImageResource(R.mipmap.ask_wrong);
                }
                mStateIv.setVisibility(View.VISIBLE);
            } else {
                if (!isRight && mDatas.get(i).getOptionName().equals(resultEntity.getRightResult())) {
                    lays.get(i).setBackgroundResource(R.mipmap.wrong_rech_bg);
                }
            }
            if (mDatas.get(i).getOptionName().equals(resultEntity.getRightResult())) {
                lays.get(i).setBackgroundResource(R.mipmap.right_rech_bg);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.topic_option_tv)
        TextView mTopicOptionTv;
        @BindView(R.id.point)
        TextView point;
        @BindView(R.id.mathView)
        AskMathView mTopicOptionMv;
        @BindView(R.id.ask_state_iv)
        ImageView mStateIv;
        @BindView(R.id.topic_option_ly)
        RelativeLayout mTopicOptionLy;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTopicOptionMv.formatMath().showWebImage();
        }
    }
}
