package com.asking91.app.ui.supertutorial.buy.exercises;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.entity.supertutorial.SuperBuyClearanceEntity;
import com.asking91.app.entity.supertutorial.SuperSubjectTopicEntity;
import com.asking91.app.ui.supertutorial.OnCommItemClickListener;
import com.asking91.app.ui.supertutorial.SuperSelectContract;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.supertutorial.SupergeTreeActivity;
import com.asking91.app.ui.supertutorial.buy.exercises.adapter.SubjectAdapter;
import com.asking91.app.ui.supertutorial.buy.exercises.adapter.SubjectPagerAdapter;
import com.asking91.app.ui.widget.camera.comm.AppManager;
import com.asking91.app.ui.widget.camera.ui.BaseEvenActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by jswang on 2017/5/9.
 */

public class SuperExercisesActivity extends BaseEvenActivity<SuperSelectPresenterImpl, SuperSelectModelImpl> implements SuperSelectContract.View
        , Toolbar.OnMenuItemClickListener {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    @BindView(R.id.tv_class)
    TextView tv_class;

    @BindView(R.id.tv_index)
    TextView tv_index;

    @BindView(R.id.tv_total_index)
    TextView tv_total_index;

    @BindView(R.id.rv_subject)
    RecyclerView rv_subject;

    @BindView(R.id.ex_layout)
    ExpandableLayout ex_layout;

    String gradeId = "";
    String verName = "";
    String versionName = "";
    String knowledgeId;
    String knowledgeName;
    String action;
    String actionType;

    private List<SuperSubjectTopicEntity> subjectList = new ArrayList<>();
    private List<SuperBuyClearanceEntity> topicList = new ArrayList<>();

    SubjectAdapter subjectAdapter;
    SubjectPagerAdapter mPagerAdapter;

    private String topic_id;
    private int start = 0;
    private int limit = 100;
    boolean flagScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_supertutorial_clearance);
        ButterKnife.bind(this);

        gradeId = getIntent().getStringExtra("gradeId");
        verName = getIntent().getStringExtra("verName");
        versionName = getIntent().getStringExtra("versionName");
        knowledgeId = getIntent().getStringExtra("knowledgeId");
        knowledgeName = getIntent().getStringExtra("knowledgeName");
        action = getIntent().getStringExtra("action");
        try {
            actionType = action.substring(0, 1);
        } catch (Exception e) {
            actionType = "M";
        }
    }

    @Override
    public void initView() {
        super.initView();

        setToolbar(mToolBar, "");
        mToolBar.inflateMenu(R.menu.menu_more_image);
        mToolBar.setOnMenuItemClickListener(this);

        setSwipeBackEnable(false);

        subjectAdapter = new SubjectAdapter(this, subjectList, new OnCommItemClickListener<SuperSubjectTopicEntity>() {
            @Override
            public void OnCommItem(SuperSubjectTopicEntity item) {
                topic_id = item.getTopic_id();
                tv_title.setText(item.getTopic_name());
                start = 0;
                loadSubject();
                ex_layout.collapse();
            }
        });
        rv_subject.setLayoutManager(new GridLayoutManager(this, 3));
        rv_subject.setAdapter(subjectAdapter);

        mPagerAdapter = new SubjectPagerAdapter(getSupportFragmentManager(), action, topicList);
        mViewpager.setAdapter(mPagerAdapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tv_index.setText((position + 1) + "");
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        flagScroll = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        flagScroll = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (mViewpager.getCurrentItem() == mViewpager.getAdapter().getCount() - 1 && !flagScroll) {
                            loaMoreData();
                        }
                        flagScroll = true;
                        break;
                }
            }
        });

        tv_class.setText(knowledgeName);
        mPresenter.getSubjectTopic(gradeId, knowledgeId);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_action_more:
                AppManager.getAppManager().finishActivity(SupergeTreeActivity.class);
                finish();
                break;
        }
        return true;
    }


    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.CLASSIFY_REQUEST:
                gradeId = (String) event.values[0];
                knowledgeName = (String) event.values[1];
                knowledgeId = (String) event.values[2];

                tv_class.setText(knowledgeName);
                mPresenter.getSubjectTopic(gradeId, knowledgeId);
                break;
        }
    }

    private boolean isRunLoaMoreData = false;

    private void loaMoreData() {
        if (!isRunLoaMoreData) {
            isRunLoaMoreData = true;
            start = start + 1;
            loadSubject();
        }
    }

    @OnClick({R.id.tv_class, R.id.tv_title, R.id.foot_s})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_class:
                finish();
//                if (!TextUtils.isEmpty(gradeId)) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("gradeId", gradeId);
//                    bundle.putString("verName", verName);
//                    bundle.putString("versionName", versionName);
//                    bundle.putInt("showType", 1);
//                    bundle.putBoolean("isSelectNode", true);
//                    openResultActivity(SupergeTreeActivity.class, bundle);
//                }
                break;
            case R.id.tv_title:
                if (ex_layout.isExpanded()) {
                    ex_layout.collapse();
                } else {
                    ex_layout.expand();
                }
                break;
            case R.id.foot_s:
                ex_layout.collapse();
                break;
        }
    }

    @Override
    public void onResultSuccess(String method, ResponseBody res) {
        if (method.equals("getSubjectTopic")) {
            try {
                subjectList.clear();
                subjectList.addAll(CommonUtil.parseDataToList(res, new TypeToken<List<SuperSubjectTopicEntity>>() {
                }));
                if (subjectList.size() > 0) {
                    SuperSubjectTopicEntity entity = subjectList.get(0);
                    topic_id = entity.getTopic_id();
                    tv_title.setText(entity.getTopic_name());
                    entity.isSelect = true;
                }
                subjectAdapter.notifyDataSetChanged();
                loadSubject();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRefreshData(ResponseBody res) {

        try {
            String resStr = res.string();
            if(resStr.length()>0) {
                JSONObject JsonRes = JSON.parseObject(resStr);
                String subjects = JsonRes.getString("subjects");
                List<SuperBuyClearanceEntity> list = JSON.parseArray(subjects, SuperBuyClearanceEntity.class);
                if (start == 0) {
                    topicList.clear();
                }
                topicList.addAll(list);
                mPagerAdapter.notifyDataSetChanged();
                if (start == 0) {
                    mViewpager.post(new Runnable() {
                        @Override
                        public void run() {
                            mViewpager.setCurrentItem(0);
                        }
                    });
                }
                tv_total_index.setText(topicList.size() + "");
                isRunLoaMoreData = false;
            }else{
                showShortToast("暂无数据");
            }
        }catch (Exception e){}

    }

    private void loadSubject() {
        mPresenter.getAllSubjectClassic(gradeId, knowledgeId, topic_id, start, limit);
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {
        isRunLoaMoreData = false;
    }

    @Override
    public void onGetSuperSelectSuccess(ResponseBody obj) {

    }

    @Override
    public void onFreeStudySuccess(ResponseBody obj) {

    }

    @Override
    public void onFreeStudyVersionSuccess(ResponseBody obj) {

    }


    @Override
    public void onLoadMoreData(ResponseBody res) {

    }

    @Override
    public void onResultSuccess(String method, ResponseBody res, int position) {

    }
}
