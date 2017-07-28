package com.asking91.app.ui.onlinetest.topicask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.BaseFragment;
import com.asking91.app.common.SpaceItemDecoration;
import com.asking91.app.entity.basepacket.AnswerOption;
import com.asking91.app.entity.onlinetest.ResultEntity;
import com.asking91.app.entity.onlinetest.TopicEntity;
import com.asking91.app.ui.adapter.onlinetest.OnlineTestAskRecycleOptionsAdapter;
import com.asking91.app.ui.widget.AskMathView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/11/16.
 */
public class OnlineTestTopicAskFragment extends BaseFragment {
    private static final String TAG = OnlineTestTopicAskFragment.class.getName();

    @BindView(R.id.topic_index_tv)
    TextView mTopicIndexTv;
    @BindView(R.id.topic_mathview)
    AskMathView mTopicMv;
    @BindView(R.id.mathView)
    AskMathView mTopicAnswerMv;
    @BindView(R.id.topic_rv)
    RecyclerView mTopicRv;
    boolean isRef;
    private TopicEntity mTopicEntity;
    private int index = 0;
    private List<AnswerOption> mDatas;

    public int getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }

    /**数据列表的位置*/
    private int parentPosition;
//    private OnlineTestAskTopicOptionAdapter onlineTestAskTopicOptionAdapter;
    private OnlineTestAskRecycleOptionsAdapter recycleOptionsAdapter;

    public boolean isShowAnswerDetail() {
        return isShowAnswerDetail;
    }

    public void setShowAnswerDetail(boolean showAnswerDetail) {
        isShowAnswerDetail = showAnswerDetail;
    }

    /**是否显示解析*/
    private boolean isShowAnswerDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_onlinetest_topic_ask);
        ButterKnife.bind(this,getContentView());
    }

    @Override
    public void initView(){
        super.initView();
//        LinearLayoutManager mgr = new LinearLayoutManager(getContext());
//        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager mgr=new GridLayoutManager(getContext(),4);
        mTopicRv.setLayoutManager(mgr);
        mTopicRv.addItemDecoration(new SpaceItemDecoration(30).isFirstTop(true));
//        mTopicRv.setAdapter(onlineTestAskTopicOptionAdapter);
        mTopicRv.setAdapter(recycleOptionsAdapter);
        mTopicAnswerMv.formatMath().showWebImage(getContext());
        mTopicMv.formatMath().showWebImage(getContext());

        mTopicAnswerMv.setVisibility(View.GONE);
        mTopicAnswerMv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightyellow));
    }

    @Override
    public void initData(){
        super.initData();
        mDatas = new ArrayList<>();
//        onlineTestAskTopicOptionAdapter = new OnlineTestAskTopicOptionAdapter(getContext(),mDatas, parentPosition);
        recycleOptionsAdapter = new OnlineTestAskRecycleOptionsAdapter(getContext(), mDatas, parentPosition, mTopicRv);
    }

    @Override
    public void initListener(){
        super.initListener();
    }

    public void setData(TopicEntity topicEntity,int index){
        if(mTopicEntity ==null || !topicEntity.equals(mTopicEntity)){
            this.mTopicEntity = topicEntity;
            this.index = index+1;
            isRef = false;
        }
        else
            isRef= true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mTopicEntity!=null){
//            if(!isScoreBack)
//                ++index;
            mTopicIndexTv.setText(index+".");
            String answerTmp = "";
            for(AnswerOption a:mTopicEntity.getAnswerOptionList()){
                if (a.getOptionContentHtml() != null && a.getOptionContentHtml().contains("</p>")) {
                    answerTmp = answerTmp + a.getOptionName() + ". " + a.getOptionContentHtml().substring(3, a.getOptionContentHtml().length()-4) + "<br/>";
                } else {
                    answerTmp = answerTmp + a.getOptionName() + ". " + a.getOptionContentHtml() + "<br/>";
                }
            }
            mTopicMv.setText(mTopicEntity.getSubjectDescriptionHtml() + answerTmp);
            mTopicAnswerMv.setText(mTopicEntity.getDetailsAnswerHtml());

            mDatas.clear();
            mDatas.addAll(mTopicEntity.getAnswerOptionList());
//            onlineTestAskTopicOptionAdapter.setId(mTopicEntity.getId());
//            onlineTestAskTopicOptionAdapter.notifyDataSetChanged();
            recycleOptionsAdapter.setId(mTopicEntity.getId());
            recycleOptionsAdapter.notifyDataSetChanged();
            //设置是否显示
            mTopicAnswerMv.setVisibility(isShowAnswerDetail ? View.VISIBLE : View.GONE);
        }

    }

    public interface SwitchFragmentCall {
        void sendNextFragmentMessage(String id,String answer);
        void sendIndex(int index);
    }

    public void notifyOnlineTestAskTopicOptionAdapter(ResultEntity resultEntity){
//        if(mDatas!=null && mDatas.size()>0) {
//            for (AnswerOption answerOption : mDatas) {
//                answerOption.setResultEntity(resultEntity);
//            }
//            mTopicAnswerMv.setText(mTopicEntity.getDetailsAnswerHtml());
//            mTopicAnswerMv.setVisibility(View.VISIBLE);
        recycleOptionsAdapter.notifyDataSetChanged();
//            onlineTestAskTopicOptionAdapter.notifyDataSetChanged();
//            onlineTestAskTopicOptionAdapter.setSubmit(true);
//            onlineTestAskTopicOptionAdapter.updateResult(resultEntity);
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //从答题卡回来，已经提交结果
//        if(onlineTestAskTopicOptionAdapter!=null) {
//            onlineTestAskTopicOptionAdapter.setSubmit(true);//设置标志
//            mTopicAnswerMv.setVisibility(View.VISIBLE);
//        }

        if(recycleOptionsAdapter!=null){
            recycleOptionsAdapter.setSubmit(true);
            mTopicAnswerMv.setVisibility(View.VISIBLE);
        }

    }
}
