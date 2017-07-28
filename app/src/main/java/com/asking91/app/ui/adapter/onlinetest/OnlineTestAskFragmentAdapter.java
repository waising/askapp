package com.asking91.app.ui.adapter.onlinetest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.asking91.app.entity.onlinetest.ResultEntity;
import com.asking91.app.entity.onlinetest.TopicEntity;
import com.asking91.app.ui.onlinetest.topicask.OnlineTestTopicAskFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wxwang on 2016/11/16.
 */
public class OnlineTestAskFragmentAdapter extends FragmentStatePagerAdapter {

    private List<OnlineTestTopicAskFragment> fragments = new ArrayList<>();
    private int maxFragment = 0;
    private List<TopicEntity> mTopicEntityList;

    public boolean isShowAnswerDetail() {
        return isShowAnswerDetail;
    }

    public void setShowAnswerDetail(boolean showAnswerDetail) {
        isShowAnswerDetail = showAnswerDetail;
    }

    /**是否显示解析*/
    private boolean isShowAnswerDetail;

    public void setAnswerMap(Map<String, ResultEntity> answerMap) {
        this.answerMap = answerMap;
    }

    private Map<String,ResultEntity> answerMap = null;
    public OnlineTestAskFragmentAdapter(FragmentManager fm) {
        super(fm);
        mTopicEntityList = new ArrayList<>();
    }

    public OnlineTestAskFragmentAdapter fragments(List<OnlineTestTopicAskFragment> list){
        this.fragments = list;
        return this;
    }

    public void setData(List<TopicEntity> topicEntityList){
        this.mTopicEntityList = topicEntityList;
    }

    public OnlineTestAskFragmentAdapter maxFragment(int maxFragment){
        this.maxFragment = maxFragment;
        return this;
    }

    @Override
    public int getItemPosition(Object object) {
        if (object.getClass().getName().equals(OnlineTestTopicAskFragment.class.getName())) {

            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {
        OnlineTestTopicAskFragment onlineTestTopicAskFragment = fragments.get(position);
        onlineTestTopicAskFragment.setParentPosition(position);
        onlineTestTopicAskFragment.setShowAnswerDetail(isShowAnswerDetail);
        if (mTopicEntityList!=null && mTopicEntityList.size()>0) {
            onlineTestTopicAskFragment.setData(mTopicEntityList.get(position), position);
//            if(isShowAnswerDetail) {
//                onlineTestTopicAskFragment.notifyOnlineTestAskTopicOptionAdapter(answerMap.get(String.valueOf(position)));
//            }
        }

        return onlineTestTopicAskFragment;
    }

    @Override
    public int getCount() {
        return mTopicEntityList.size();
    }
}
