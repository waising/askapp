package com.asking91.app.ui.supertutorial.selected;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asking91.app.R;
import com.asking91.app.common.BaseFragment;
import com.asking91.app.ui.supertutorial.SuperMenuActivity;

/**
 * 章节页的外层嵌套的fragment
 * Created by linbin on 2017/3/2.
 */

public class BranchFrameFragment extends BaseFragment {


    /**
     * M2-初中数学（8）  P2-初中物理（6）  M3-高中数学（9和9.5）  P3-高中物理（7）
     */
    String actionType;

    private String action;
    private String verName;

    private int fromWhere;

    public static BranchFrameFragment newInstance(String actionType, String action, String verName, int fromWhere) {
        BranchFrameFragment fragment = new BranchFrameFragment();
        Bundle bundle = new Bundle();
        bundle.putString("actionType", actionType);
        bundle.putString("action", action);
        bundle.putString("verName", verName);
        bundle.putInt("fromWhere", fromWhere);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            actionType = bundle.getString("actionType");
            action = bundle.getString("action");
            verName = bundle.getString("verName");
            fromWhere = bundle.getInt("fromWhere");
        }
        if (fromWhere == SuperMenuActivity.FROM_ROUND_ONE) {//第一轮复习
            getChildFragmentManager().beginTransaction().add(R.id.frame_branch, CatalogueSelectRoundOneFragment.newInstance(actionType, action, verName), "").commit();
        } else {//第二轮复习
            getChildFragmentManager().beginTransaction().add(R.id.frame_branch, CatalogueSelectRoundTwoFragment.newInstance(actionType, action, verName), "").commit();
        }
        return view;
    }


}