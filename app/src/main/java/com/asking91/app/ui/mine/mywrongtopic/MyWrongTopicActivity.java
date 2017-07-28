package com.asking91.app.ui.mine.mywrongtopic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.basepacket.ClassEntity;
import com.asking91.app.entity.basepacket.CourseEntity;
import com.asking91.app.entity.user.MyWrongTopicEntity;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.mine.mytestrecord.TestRecordContract;
import com.asking91.app.ui.mine.mytestrecord.TestRecordModel;
import com.asking91.app.ui.mine.mytestrecord.TestRecordPresenter;
import com.asking91.app.ui.widget.dialog.AlertDialogUtil;
import com.asking91.app.util.CommonUtil;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import okhttp3.ResponseBody;

import static com.asking91.app.global.OnlineQAConstant.classVersionNoAll;
import static com.asking91.app.global.OnlineQAConstant.classVersionValuesNoAll;

/**
 * 错题本
 */
public class MyWrongTopicActivity extends BaseFrameActivity<TestRecordPresenter, TestRecordModel> implements TestRecordContract.View {

    @BindView(R.id.layoutGrade)
    LinearLayout layoutGrade;

    @BindView(R.id.tvSubject)
    TextView tvSubject; //初中数学

    @BindView(R.id.layoutSelectVersions)
    LinearLayout layoutSelectVersions; //选择版本

    @BindView(R.id.tvSelectVersions)
    TextView tvSelectVersions;

    @BindView(R.id.tvGrade)
    TextView tvGrade; // 年级

    @BindView(R.id.tvWrong)
    TextView tvWrong; // 错误类型

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindColor(R.color.colorAccent)
    int colorAccent;

    @BindColor(R.color.colorText_c7)
    int colorText_c7;

    private String subjectCatalog = ""; // subjectCatalog 是科目类型
    private String tag = "";
    private String versionLevelId = "";
    private String courseId = "";
    private int start = 0;
    private int limit = 10;

    private List<CourseEntity> courseList = new ArrayList<>();
    private List<ClassEntity> classList = new ArrayList<>();
    private List<MyWrongTopicEntity> topicList = new ArrayList<>();

    private CommPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wrong_topic);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        tvSubject.setText(R.string.selectsubjects);
        tvSelectVersions.setText(R.string.select_subject_version);
        tvGrade.setText(R.string.basepacket_choose_class);
        setToolbar(mToolbar, getString(R.string.err_note));
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);

        mAdapter = new CommPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        setSubject(0);
    }

    // 科目类型
    private void setSubject(int position) {
        subjectCatalog = classVersionValuesNoAll[position]; // 初中数学 M2

        tvSubject.setTextColor(colorAccent);
        tvSubject.setText(classVersionNoAll[position]);

        mPresenter.presenterMyWrongVersions(1, subjectCatalog, "");
    }


    private void iniWrongTopic() {
        ArrayMap<Object, Object> map = new ArrayMap<>();
        map.put("start", String.valueOf(start));
        map.put("limit", String.valueOf(limit));
        map.put("tag", tag); // tag 错误类型，传""表示请求全部错题
        map.put("version_level_id", versionLevelId);
        map.put("tips", new ArrayList());
        String postStr = JSON.toJSONString(map);
        mPresenter.modelMyWrongTopic(111, subjectCatalog, postStr);
    }

    class CommPagerAdapter extends FragmentStatePagerAdapter {

        public CommPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MyWrongTopicEntity e = topicList.get(position);
            return MyWrongFragment.newInstance(position, subjectCatalog, e);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return topicList.size();
        }
    }


    @OnClick({R.id.layoutSubject, R.id.layoutSelectVersions, R.id.layoutGrade, R.id.layoutWrong})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutSubject:
                // 科目容器(这个保留)
                showSubjectialog();
                break;
            case R.id.layoutSelectVersions:
                showCourseDialog();
                break;
            case R.id.layoutGrade:
                showClassDialog();
                break;
        }
    }

    // 教材版本
    private void setCourseText(String name, String id) {
        courseId = id;
        tvSelectVersions.setTextColor(colorAccent);
        tvSelectVersions.setText(name);
        mPresenter.presenterMyWrongGrade(2, courseId, "1");
    }

    //选择年级
    private void setClassText(String name, String id) {
        versionLevelId = id;
        tvGrade.setTextColor(colorAccent);
        tvGrade.setText(name);

        iniWrongTopic();
    }

    /**
     * 显示选择科目dialog
     */
    private void showSubjectialog() {
        if (classVersionNoAll != null && classVersionNoAll.length > 0) {
            AlertDialogUtil.showNormalListDialogStringArr(this, classVersionNoAll, new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setSubject(position);
                    AlertDialogUtil.dismiss();
                }
            });
        }
    }

    /**
     * 显示选择教材版本 dialog
     */
    private void showCourseDialog() {
        String[] courseArr = new String[courseList.size()];
        for (int i = 0; i < courseList.size(); i++) {
            CourseEntity e = courseList.get(i);
            courseArr[i] = e.getVersionName();

        }
        AlertDialogUtil.showNormalListDialogStringArr(this, courseArr, new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourseEntity e = courseList.get(position);
                setCourseText(e.getVersionName(), String.valueOf(e.getVersionId()));
                AlertDialogUtil.dismiss();
            }
        });
    }

    /**
     * 显示选择年级dialog
     */
    private void showClassDialog() {
        String[] classArr = new String[classList.size()];
        for (int i = 0; i < classList.size(); i++) {
            ClassEntity e = classList.get(i);
            classArr[i] = e.getTextbook() + "(" + e.getLevelName() + ")";
        }
        AlertDialogUtil.showNormalListDialogStringArr(this, classArr, new OnOperItemClickL() {

            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassEntity e = classList.get(position);
                setClassText(e.getTextbook() + "(" + e.getLevelName() + ")", String.valueOf(e.getVersionLevelId()));
                AlertDialogUtil.dismiss();
            }
        });
    }

    /**
     * 显示选择错误类型 dialog
     */
    @Override
    public void onRequestSuccess(int type, ResponseBody responseBody) {
        switch (type) {
            case 1://教材版本
                courseList.clear();
                courseList.addAll(CommonUtil.parseDataToList(responseBody, new TypeToken<List<CourseEntity>>() {
                }));
                if (courseList.size() > 0) {
                    CourseEntity e = courseList.get(0);
                    setCourseText(e.getVersionName(), String.valueOf(e.getVersionId()));
                }
                break;
            case 2://年级
                classList.clear();
                classList.addAll(CommonUtil.parseDataToList(responseBody, new TypeToken<List<ClassEntity>>() {
                }));
                if (classList.size() > 0) {
                    ClassEntity e = classList.get(0);
                    setClassText(e.getTextbook() + "(" + e.getLevelName() + ")", String.valueOf(e.getVersionLevelId()));
                }
                break;
            case 111:// 错题数据
                Map<String, Object> map = CommonUtil.parseDataToMap(responseBody);
                topicList.clear();
                topicList.addAll(CommonUtil.parseDataToList(map.get("list"), new TypeToken<List<MyWrongTopicEntity>>() {
                }));
                mAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public void onRequestSuccess(ResponseBody responseBody) {
    }

    @Override
    public void onRefreshData(ResponseBody responseBody) {
    }

    @Override
    public void onLoadMoreData(ResponseBody responseBody) {

    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestEnd() {
    }

    @Override
    public void onInternetError(String methodName) {
    }

}
