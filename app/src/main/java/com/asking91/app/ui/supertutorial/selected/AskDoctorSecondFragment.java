package com.asking91.app.ui.supertutorial.selected;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.ui.widget.MultiStateView;

/**
 * 博士有话说界面
 * Created by jswang on 2017/3/2.
 */

public class AskDoctorSecondFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> {
    String pid;

    AskMathView mathView;
    MultiStateView multiStateView;
    TextView tip1;
    AskSimpleDraweeView voice;
    RelativeLayout topTip;

    public static AskDoctorSecondFragment newInstance(String pid) {
        AskDoctorSecondFragment fragment = new AskDoctorSecondFragment();
        Bundle bundle = new Bundle();
        bundle.putString("pid", pid);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            pid = bundle.getString("pid");
        }
    }


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_supertutorial_superclass_talking, null);
        mathView = (AskMathView) rootView.findViewById(R.id.mathView);
        multiStateView = (MultiStateView) rootView.findViewById(R.id.multiStateView);
        tip1 = (TextView) rootView.findViewById(R.id.tip1);
        voice = (AskSimpleDraweeView) rootView.findViewById(R.id.voice);
        voice.setVisibility(View.INVISIBLE);
        topTip = (RelativeLayout) rootView.findViewById(R.id.top_tip);

        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
        if (!TextUtils.isEmpty(pid)) {
            initLoadData(pid);
        }
        return rootView;
    }

    public void initLoadData(String pid) {
        this.pid = pid;
        if (multiStateView != null) {
            multiStateView.setViewState(multiStateView.VIEW_STATE_LOADING);
        }
        mPresenter.secondreviewzhuant(getActivity(), pid, "askbsyhs", new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                if (mathView != null) {
                    mathView.formatMath().showWebImage(multiStateView);
                    if (!TextUtils.isEmpty(res)) {
                        String content = JSON.parseObject(res).getString("askbsyhs");
                        mathView.setWebText(content);
                        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
                    } else {
                        multiStateView.setViewState(multiStateView.VIEW_STATE_EMPTY);
                    }

                }
            }
        });
    }


    public void onRequestStart() {

    }

    public void onRequestError(RequestEntity requestEntity) {

    }

    public void onRequestEnd() {

    }
}
