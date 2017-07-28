package com.asking91.app.ui.supertutorial.buy.exercises.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.asking91.app.entity.supertutorial.SuperBuyClearanceEntity;
import com.asking91.app.ui.supertutorial.buy.exercises.SuperTopicFragment;

import java.util.List;

/**
 *
 * Created by jswang on 2017/5/9.
 */

public class SubjectPagerAdapter extends FragmentStatePagerAdapter {
    private List<SuperBuyClearanceEntity> topicList;

    String action;

    public SubjectPagerAdapter(FragmentManager fm, String action,List<SuperBuyClearanceEntity> topicList) {
        super(fm);
        this.action = action;
        this.topicList = topicList;
    }

    @Override
    public int getCount() {
        return topicList.size();
    }

    @Override
    public Fragment getItem(int position) {
        SuperBuyClearanceEntity e = topicList.get(position);
        return SuperTopicFragment.newInstance(e,position+1,action);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (position+1)+"";
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
