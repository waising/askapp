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
import com.asking91.app.entity.onlinetest.ResultEntity;
import com.asking91.app.entity.supertutorial.SuperBuyClearanceEntity;
import com.asking91.app.ui.supertutorial.buy.exercises.SuperTopicFragment;
import com.asking91.app.ui.widget.AskMathView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/11/16.
 */
public class SuperBuyClearanceTopicAskOptionAdapter extends RecyclerView.Adapter<SuperBuyClearanceTopicAskOptionAdapter.ViewHolder> {

    private Context mContext;
    private List<SuperBuyClearanceEntity.OptionsBean> mDatas;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    private View view;
    //题目ID
    private String id;
    private SuperTopicFragment.SwitchFragmentCall switchFragmentCall;
    private ArrayMap<Integer, RelativeLayout> lays ;
    public SuperBuyClearanceTopicAskOptionAdapter(Context context, List<SuperBuyClearanceEntity.OptionsBean> datas, SuperTopicFragment.SwitchFragmentCall switchFragmentCall) {
        this.mContext = context;
        this.mDatas = datas;
        this.switchFragmentCall = switchFragmentCall;
        lays = new ArrayMap<>();
    }
    /**设置状态*/
    private  boolean scrollState=false;

    public void setScrollState(boolean scrollState) {
        this.scrollState = scrollState;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuperBuyClearanceTopicAskOptionAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_supertutorial_buy_clearance_topic_ask_list,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        SuperBuyClearanceEntity.OptionsBean optionsBean = mDatas.get(position);
        String optionName = optionsBean.getOptionName();
        holder.mTopicOptionTv.setText(optionName);

//        if(optionsBean.getResultEntity()!=null){
//            boolean isOption = optionsBean.getResultEntity().getAnswerResult().equals(optionsBean.getOptionName());
//            boolean isRight = optionsBean.getResultEntity().getAnswerResult().equals(optionsBean.getResultEntity().getRightResult());
//            if(isOption){
//                if(isRight){
//                    holder.mTopicOptionLy.setBackgroundResource(R.mipmap.right_rech_bg);
//                    holder.mStateIv.setImageResource(R.mipmap.ask_right);
//                }else{
//                    holder.mTopicOptionLy.setBackgroundResource(R.mipmap.wrong_rech_bg);
//                    holder.mStateIv.setImageResource(R.mipmap.ask_wrong);
//                }
//                holder.mStateIv.setVisibility(View.VISIBLE);
//            }else{
//                if(!isRight&&optionsBean.getOptionName().equals(optionsBean.getResultEntity().getRightResult()))
//                    holder.mTopicOptionLy.setBackgroundResource(R.mipmap.wrong_rech_bg);
//            }
//
//            if(optionsBean.getOptionName().equals(optionsBean.getResultEntity().getRightResult())){
//                holder.mTopicOptionLy.setBackgroundResource(R.mipmap.right_rech_bg);
//            }
//        }else{
            lays.put(position, holder.mTopicOptionLy);
            holder.mTopicOptionLy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isSubmit) {
                        if (view != null && view != holder.mTopicOptionLy) {
                            view.setBackgroundResource(R.mipmap.rech_bg);
                        }
                        if (view != holder.mTopicOptionLy) {
                            view = holder.mTopicOptionLy;
                            holder.mTopicOptionLy.setBackgroundResource(R.mipmap.wrong_rech_bg);
                            //跳转
                            switchFragmentCall.sendNextFragmentMessage(id, holder.mTopicOptionTv.getText().toString());
                        }
                    }
                }
            });
//        }
        holder.mTopicOptionMv.setText(optionsBean.getOptionContent());
    }
    /**是否提交过*/
    private boolean isSubmit;
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
        @BindView(R.id.topic_option_ly)
        RelativeLayout mTopicOptionLy;

        @BindView(R.id.topic_option_tv)
        TextView mTopicOptionTv;

        @BindView(R.id.mathView)
        AskMathView mTopicOptionMv;

        @BindView(R.id.ask_state_iv)
        ImageView mStateIv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mTopicOptionMv.formatMath().showWebImage();
        }
    }

}
