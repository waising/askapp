package com.asking91.app.ui.supertutorial.selected;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.pay.SynchronousCourseEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.adapter.supertutorial.SuperTutorialSelectAdapter;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;

import java.util.ArrayList;

/**
 * 选择的fragment
 */

public class SuperTutorialSelectFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> {

    RecyclerView recycler;

    private SuperTutorialSelectAdapter mAdapter;
    ArrayList<SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX.CourseListBean> mList;


    public static SuperTutorialSelectFragment newInstance(ArrayList<SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX.CourseListBean> courseList) {
        SuperTutorialSelectFragment fragment = new SuperTutorialSelectFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", courseList);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_supersupial_select, null);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bundle bundleArg = getArguments();
        if (bundleArg != null) {
            mList = (ArrayList<SynchronousCourseEntity.NodelistBeanX.NodelistBean.ValueBeanXX.CourseListBean>) bundleArg.getSerializable("list");
            if (mList != null && mList.size() > 0) {
                mAdapter = new SuperTutorialSelectAdapter(getActivity());
                mAdapter.setData(mList);
                recycler.setAdapter(mAdapter);

            }

        }


        return rootView;
    }


    public void onRequestStart() {

    }

    public void onRequestError(RequestEntity requestEntity) {

    }

    public void onRequestEnd() {

    }


}
