package com.asking91.app.ui.supertutorial.selected;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.supertutorial.ExamReviewTree;
import com.asking91.app.entity.supertutorial.StudyHistory;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.supertutorial.SuperMenuActivity;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.AskRadioGroup;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 一轮复习
 * Created by jswang on 2017/3/1.
 */

public class ExamReviewFirstFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> implements
        View.OnClickListener {
    View rootView;
    AskRadioGroup radio_group;
    TextView tv_section;//顶部的章节项，如果是初中物理，没有章节，只显示第几讲
    TextView tc_content;//顶部的几讲项，
    AskMathView mvName;


    ArrayList<ExamReviewTree> dataList = new ArrayList<>();//章节点数据

    List<Fragment> fragments = new ArrayList<>();

    /**
     * M2-初中数学（8）  P2-初中物理（6）  M3-高中数学（9.5）  P3-高中物理（7），四种分类
     */


    ExamReviewTree e;

    private String action;

    private String verName;

    public static ExamReviewFirstFragment newInstance(ExamReviewTree e, String action, String verName) {
        ExamReviewFirstFragment fragment = new ExamReviewFirstFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("e", e);
        bundle.putString("action", action);
        bundle.putString("verName", verName);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            e = (ExamReviewTree) bundle.getSerializable("e");
            action = bundle.getString("action");
            verName = bundle.getString("verName");
            EventBus.getDefault().post(new StudyHistory(1,e.getText(),e,action,verName));
        }
    }

    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(R.layout.fragment_exam_review_first, null);
        rootView.findViewById(R.id.ll_class).setOnClickListener(this);
        radio_group = (AskRadioGroup) rootView.findViewById(R.id.radio_group);
        tv_section = (TextView) rootView.findViewById(R.id.tv_section);
        tc_content = (TextView) rootView.findViewById(R.id.tc_content);
        mvName = (AskMathView) rootView.findViewById(R.id.mv_name);
        fragments.add(AskDoctorFirstFragment.newInstance(e._id));
        fragments.add(ExamFirstRequireFragment.newInstance(e._id));
        fragments.add(ExamReviewExerFragment.newInstance(e._id));

        setCurrFragment(fragments.get(0));
        toFragment(fragments.get(0));
//底部的几个tab页切换
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn1://博士考情分析页面
                        toFragment(fragments.get(0));
                        break;
                    case R.id.rbtn2://考点
                        toFragment(fragments.get(1));
                        break;
                    case R.id.rbtn3://实战演练
                        toFragment(fragments.get(2));
                        break;
                }
            }
        });
        setSectionText(e.getText());
        return this.rootView;
    }

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.CATA_SELECT_REQUEST://选中某讲后回传回来
                ExamReviewTree p = (ExamReviewTree) event.values[0];
                ExamReviewTree e = (ExamReviewTree) event.values[1];
                setData(p, e);
                break;
        }
    }

    /**
     * 显示顶部第几讲
     */
    private void setSectionText(String content) {
        if (CommonUtil.isMath(content)) {//如果包含英文字符，即包含英文公式的
            mvName.setVisibility(View.VISIBLE);
            tv_section.setVisibility(View.GONE);
            mvName.setText(content);
        } else {//不包含英文公式的
            tv_section.setText(content);
            tv_section.setVisibility(View.VISIBLE);
            mvName.setVisibility(View.GONE);
        }

    }

    private void setData(ExamReviewTree p, ExamReviewTree e) {
        setSectionText(e.getText());
        initDataView(e._id);
    }

    /**
     * 传递给底部三个tab页，页面请求
     *
     * @param pid
     */
    private void initDataView(String pid) {
        ((AskDoctorFirstFragment) fragments.get(0)).initLoadData(pid);
        ((ExamFirstRequireFragment) fragments.get(1)).initLoadData(pid, 1 + "");
        ((ExamReviewExerFragment) fragments.get(2)).initLoadData(pid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_class://头部选择章节
                turnRunBuy();
                break;
        }
    }

    /**
     * 头部选择，跳转到具体章节页activity
     */
    private void turnRunBuy() {
        Bundle bundle = new Bundle();
        bundle.putString("action", action);
        bundle.putString("verName", verName);
        bundle.putInt("fromWhere", SuperMenuActivity.FROM_ROUND_ONE);
        openActivity(RoundReviewActivity.class, bundle);
        getActivity().finish();
    }

    public void onRequestStart() {

    }

    public void onRequestError(RequestEntity requestEntity) {

    }

    public void onRequestEnd() {

    }

    protected Fragment mCurrFragment;

    /**
     * 设置当前显示的fragement
     *
     * @param mCurrFragment
     */
    public void setCurrFragment(Fragment mCurrFragment) {
        this.mCurrFragment = mCurrFragment;
    }

    /**
     * 跳转fragment
     *
     * @param toFragment
     */
    protected void toFragment(Fragment toFragment) {
        if (mCurrFragment == null) {
            showShortToast("mCurrFragment is null!");
            return;
        }
        if (toFragment == null) {
            showShortToast("toFragment is null!");
            return;
        }
        if (toFragment.isAdded()) {
            getChildFragmentManager().beginTransaction().hide(mCurrFragment)
                    .show(toFragment).commit();
        } else {
            getChildFragmentManager().beginTransaction().hide(mCurrFragment)
                    .add(R.id.fragment, toFragment).show(toFragment)
                    .commit();
        }
        mCurrFragment = toFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
