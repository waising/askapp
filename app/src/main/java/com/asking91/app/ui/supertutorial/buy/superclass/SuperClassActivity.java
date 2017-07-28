package com.asking91.app.ui.supertutorial.buy.superclass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.supertutorial.StudyHistory;
import com.asking91.app.ui.supertutorial.SuperSelectContract;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.supertutorial.SupergeTreeActivity;
import com.asking91.app.ui.supertutorial.buy.classify.SuperClassifyActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.AskRadioGroup;
import com.asking91.app.ui.widget.camera.comm.AppManager;
import com.asking91.app.ui.widget.camera.ui.BaseEvenActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;

import static com.asking91.app.R.id.toolBar;

/**
 * Created by jswang on 2017/5/8.
 */

public class SuperClassActivity extends BaseEvenActivity<SuperSelectPresenterImpl, SuperSelectModelImpl> implements SuperSelectContract.View
        , Toolbar.OnMenuItemClickListener {
    @BindView(toolBar)
    Toolbar mToolBar;

    @BindView(R.id.tv_class)
    TextView tv_class;

    @BindView(R.id.foot_radio_group)
    AskRadioGroup foot_radio_group;

    String gradeId = "";
    String verName = "";
    String versionName = "";
    String knowledgeId;
    String knowledgeName;
    String action;

    /**
     * 1-表示来自上次学习的记录
     */
    int isFromStudyHis = 0;

    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supertutorial_superclass);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
        gradeId = getIntent().getStringExtra("gradeId");
        verName = getIntent().getStringExtra("verName");
        versionName = getIntent().getStringExtra("versionName");
        knowledgeId = getIntent().getStringExtra("knowledgeId");
        knowledgeName = getIntent().getStringExtra("knowledgeName");
        action = getIntent().getStringExtra("action");
        isFromStudyHis = getIntent().getIntExtra("isFromStudyHis", 0);
        EventBus.getDefault().post(new StudyHistory(0, knowledgeName, gradeId, knowledgeId, knowledgeName, versionName, verName, action));
        saveSchedule();
    }

    /**
     * 保存进度
     */
    private void saveSchedule() {
        mPresenter.saveSchedule(this, gradeId, 0.0, getIntent().getStringExtra("schedule"), "", "", new ApiRequestListener<String>() {//获取我的课堂数据
            @Override
            public void onResultSuccess(String res) {
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, verName);
        mToolBar.inflateMenu(R.menu.menu_more_image);
        mToolBar.setOnMenuItemClickListener(this);

        setFrameId(R.id.fragment);
        fragments.add(SuperBuySuperClassTalkingFragment.newInstance(getIntent().getExtras()));
        fragments.add(SuperBuySuperClassQuestionTimeFragment.newInstance(getIntent().getExtras()));
        fragments.add(SuperBuySuperClassSpeakerFragment.newInstance(getIntent().getExtras()));
        fragments.add(SuperBuySuperClassSummaryFragment.newInstance(getIntent().getExtras()));

        setCurrFragment(fragments.get(0));
        toFragment(fragments.get(0));

        foot_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_tab1:
                        toFragment(fragments.get(0));
                        break;
                    case R.id.rb_tab2:
                        toFragment(fragments.get(1));
                        break;
                    case R.id.rb_tab3:
                        toFragment(fragments.get(2));
                        break;
                    case R.id.rb_tab4:
                        toFragment(fragments.get(3));
                        break;
                }
            }
        });

        tv_class.setText(knowledgeName);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_action_more:
                if (isFromStudyHis == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("showType", 1);
                    bundle.putString("action", action);
                    bundle.putString("verName", verName);
                    openActivity(SuperClassifyActivity.class, bundle);
                } else {
                    AppManager.getAppManager().finishActivity(SupergeTreeActivity.class);
                }
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
                initFragmentData();
                break;
        }
    }

    /**
     * 初始化4个子fragment界面的内容
     */
    private void initFragmentData() {
        ((SuperBuySuperClassTalkingFragment) fragments.get(0)).initLoadData(gradeId, knowledgeId);
        ((SuperBuySuperClassQuestionTimeFragment) fragments.get(1)).initLoadData(gradeId, knowledgeId);
        ((SuperBuySuperClassSpeakerFragment) fragments.get(2)).initLoadData(gradeId, knowledgeId);
        ((SuperBuySuperClassSummaryFragment) fragments.get(3)).initLoadData(gradeId, knowledgeId);
    }

    @OnClick({R.id.tv_class})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_class:
                if (isFromStudyHis == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("gradeId", gradeId);
                    bundle.putString("verName", verName);
                    bundle.putString("versionName", versionName);
                    bundle.putInt("showType", 0);
                    openResultActivity(SupergeTreeActivity.class, bundle);
                }
                finish();
                break;
        }
    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
    }

    @Override
    public void onRequestEnd() {
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
    public void onRefreshData(ResponseBody res) {

    }

    @Override
    public void onLoadMoreData(ResponseBody res) {

    }

    @Override
    public void onResultSuccess(String method, ResponseBody res) {

    }

    @Override
    public void onResultSuccess(String method, ResponseBody res, int position) {

    }
}
