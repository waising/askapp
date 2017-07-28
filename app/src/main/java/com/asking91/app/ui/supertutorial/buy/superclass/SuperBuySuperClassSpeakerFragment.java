package com.asking91.app.ui.supertutorial.buy.superclass;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.entity.supertutorial.SuperClassComeToSpeak;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.adapter.supertutorial.SuperClassSpeakerAdapter;
import com.asking91.app.ui.supertutorial.SuperSelectContract;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.MultiStateView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by wxiao on 2016/11/30.
 * 免费试学--来讲题
 */

public class SuperBuySuperClassSpeakerFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> implements SuperSelectContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    private SuperClassSpeakerAdapter superClassSpeakerAdapter;
    private List<SuperClassComeToSpeak> dataList = new ArrayList<>();
    private String versionLevelId;
    private String knowledgeId;

    public static SuperBuySuperClassSpeakerFragment newInstance(Bundle bundle) {
        SuperBuySuperClassSpeakerFragment fragment = new SuperBuySuperClassSpeakerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_supertutorial_superclass_speaker);
        ButterKnife.bind(this, getContentView());

        Bundle bundle = getArguments();
        if (bundle != null) {
            versionLevelId = bundle.getString("gradeId");
            knowledgeId = bundle.getString("knowledgeId");
        }
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        superClassSpeakerAdapter = new SuperClassSpeakerAdapter(getContext(), dataList, getArguments());
        recyclerView.setAdapter(superClassSpeakerAdapter);

        if (versionLevelId != null && knowledgeId != null) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            mPresenter.getSuperBuyFragment1(versionLevelId, knowledgeId, 4);//获取来讲题接口
        }
    }

    /**
     * 初始化数据
     *
     * @param versionLevelId
     * @param knowledgeId
     */
    public void initLoadData(String versionLevelId, String knowledgeId) {
        this.versionLevelId = versionLevelId;
        this.knowledgeId = knowledgeId;
        if (multiStateView != null) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            mPresenter.getSuperBuyFragment1(versionLevelId, knowledgeId, 4);//获取来讲题接口
        }
    }

    /**
     * 数据成功请求回来
     * @param obj
     */
    @Override
    public void onGetSuperSelectSuccess(ResponseBody obj) {
        try {

            String result = obj.string();
            JSONObject jsenRes = JSON.parseObject(result);
            String content = jsenRes.getString("content");
            dataList.clear();
            dataList.addAll(JSON.parseArray(content, SuperClassComeToSpeak.class));
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            superClassSpeakerAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {
    }

    @Override
    public void onResultSuccess(String method, ResponseBody res) {
    }

    @Override
    public void onResultSuccess(String method, ResponseBody res, int position) {

    }
}
