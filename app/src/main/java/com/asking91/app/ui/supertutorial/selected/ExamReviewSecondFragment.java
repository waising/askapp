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
 * 二轮复习
 * Created by jswang on 2017/3/1.
 */

public class ExamReviewSecondFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> implements
        View.OnClickListener {
    View rootView;
    AskRadioGroup radio_group;
    TextView tv_section;
    ArrayList<ExamReviewTree> dataList = new ArrayList<>();

    List<Fragment> fragments = new ArrayList<>();
    ExamReviewTree e;
    private String action;

    private String verName;
    AskMathView mvName;
    public static ExamReviewSecondFragment newInstance(ExamReviewTree e , String action, String verName) {
        ExamReviewSecondFragment fragment = new ExamReviewSecondFragment();
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
            EventBus.getDefault().post(new StudyHistory(2,e.name,e,action,verName));
        }
    }

    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(R.layout.fragment_exam_review_second, null);
        radio_group = (AskRadioGroup) rootView.findViewById(R.id.radio_group);
        tv_section = (TextView) rootView.findViewById(R.id.tv_section);
        mvName= (AskMathView) rootView.findViewById(R.id.mv_name);
        fragments.add(AskDoctorSecondFragment.newInstance(e.id));
        fragments.add(ExamSecondRequireFragment.newInstance(e.id));

        setCurrFragment(fragments.get(0));
        toFragment(fragments.get(0));

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn1:
                        toFragment(fragments.get(0));
                        break;
                    case R.id.rbtn2:
                        toFragment(fragments.get(1));
                        break;
                }
            }
        });

        if (e != null) {
            setSectionText(e.name);
        }
        tv_section.setOnClickListener(this);
        return this.rootView;
    }

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.CATA_SELECT_REQUEST://选中某讲后回传回来
                ExamReviewTree p = (ExamReviewTree) event.values[1];
                setDate(p);
                break;
        }
    }


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




    private void setDate(ExamReviewTree p) {
        setSectionText(p.name);
        initDataView(p.id);
    }

    private void initDataView(String pid) {
        ((AskDoctorSecondFragment) fragments.get(0)).initLoadData(pid);
        ((ExamSecondRequireFragment) fragments.get(1)).initLoadData(pid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_section:
                turnRunBuy();
                break;
        }
    }

    private void turnRunBuy() {
        Bundle bundle = new Bundle();
        bundle.putString("action", action);
        bundle.putString("verName", verName);
        bundle.putInt("fromWhere",SuperMenuActivity.FROM_ROUND_TWO);
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

    public void setCurrFragment(Fragment mCurrFragment) {
        this.mCurrFragment = mCurrFragment;
    }

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
