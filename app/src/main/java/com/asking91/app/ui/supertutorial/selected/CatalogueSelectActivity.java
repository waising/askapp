package com.asking91.app.ui.supertutorial.selected;

/**
 * Created by jswang on 2017/3/2.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.asking91.app.R;
import com.asking91.app.common.BaseActivity;
import com.asking91.app.entity.supertutorial.ExamReviewTree;
import com.asking91.app.ui.widget.indicator.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择课时，章节activity
 *  Created by jswang on 2017/3/2.
 */
public class CatalogueSelectActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    public static ArrayList<ExamReviewTree> dataList = new ArrayList<>();//第一轮，第二轮复习的list第一层数据，第一轮是章节，第二轮是的一层

    /**
     * 类型为6时只有一页
     */
    int actionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cataloguedialog_dialog);
        ButterKnife.bind(this);

        actionType = getIntent().getIntExtra("actionType", 0);
    }

    @Override
    public void initView() {
        super.initView();
        toolBar.setTitle("选择课时");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CardFragmentPagerAdapter mCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager());
        view_pager.setPageMargin((int) dpToPixels(5, this));
        view_pager.setAdapter(mCardAdapter);

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(view_pager);

        mCardAdapter.notifyDataSetChanged();
        indicator.notifyDataSetChanged();
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    class CardFragmentPagerAdapter extends FragmentStatePagerAdapter {


        public CardFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount() {
            int size = dataList.size();
            if (actionType == 6 || actionType == 10) {//初中物理只有一页，ctionType == 10的时候是第二轮复习，只有一页
                size = 1;
            }
            return size;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f;
            if (actionType == 6 || actionType == 10) {//第二轮复习的时候，初中物理时只有一页，只有第一层
                f = CardFragment.newInstance(actionType, dataList);
            } else {//其他类型的时候
                f = CardFragment.newInstance(dataList.get(position));//具体de
            }
            return f;
        }

    }
}
