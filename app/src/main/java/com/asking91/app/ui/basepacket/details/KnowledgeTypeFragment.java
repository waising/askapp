package com.asking91.app.ui.basepacket.details;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.asking91.app.R;
import com.asking91.app.common.SpaceItemDecoration;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.basepacket.KnowledgeTypeEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.adapter.basepacket.KnowledgeTypeAdapter;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.RecycleViewDivider;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;

/**
 * Created by wxwang on 2016/11/4.
 */
public class KnowledgeTypeFragment extends BaseFrameFragment<KnowledgeDetailPresenter,KnowledgeDetailModel>
implements KnowledgeDetailContract.View {

    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout mSwipeLayout;

    @BindView(R.id.recycler_type)
    RecyclerView recyclerView;

    @BindView(R.id.typeMultiStateView)
    MultiStateView mMultiStateView;

    String versionLevelId,id;

    List<KnowledgeTypeEntity> mDatas;

    KnowledgeTypeAdapter knowledgeTypeAdapter;

    private int START = 0;
    private int LIMIT = 2;
    //获取知识点
    boolean isKnowledge = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_knowledge_type);
        ButterKnife.bind(this,getContentView());
    }

    @Override
    public void initData() {
        super.initData();
        versionLevelId = getArguments().getString("versionLevelId");
        id = getArguments().getString("id");

        mDatas = new ArrayList<>();
        knowledgeTypeAdapter = new KnowledgeTypeAdapter(getContext(),mDatas);

    }

    @Override
    public void initView() {
        super.initView();
        LinearLayoutManager mgr = new LinearLayoutManager(getActivity());
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mgr);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.HORIZONTAL, 3, ContextCompat.getColor(getContext(), R.color.main_bg)));

        recyclerView.setAdapter(knowledgeTypeAdapter);

        mMultiStateView.setErrorVisibility(View.VISIBLE);
    }

    @Override
    public void initLoad(){
        super.initLoad();
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        //mPresenter.getKnowledgeKind("13", "1224",START,LIMIT);
        mSwipeLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.autoRefresh();
            }
        });
    }

    @Override
    public void initListener(){
        super.initListener();

        mMultiStateView.setErrorOnClickLinstener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLoad();
            }
        });

        mSwipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                START+=LIMIT;
                mPresenter.getKnowledgeKind(versionLevelId, id,START,LIMIT);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                START = 0;
                mPresenter.getKnowledgeKind(versionLevelId, id,START,LIMIT);
            }
        });
    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onRequestEnd() {
        if (mSwipeLayout != null) mSwipeLayout.refreshComplete();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
//            if (mMultiStateView.getViewState() == MultiStateView.VIEW_STATE_ERROR){
//                initData();
//            }
        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public void onRefreshData(Object obj) {
        try{
            //解析json
            Map<String,Object> map = CommonUtil.parseDataToMap(obj);

            List<KnowledgeTypeEntity> list = CommonUtil.parseDataToList(map.get("list"),new TypeToken<List<KnowledgeTypeEntity>>(){});

            if (list == null || list.size() == 0){
                mSwipeLayout.refreshComplete();
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                return;
            }

            mDatas.clear();
            mDatas.addAll(list);
            knowledgeTypeAdapter.notifyDataSetChanged();

            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);


        }catch (Exception e){
            e.printStackTrace();
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onLoadMoreData(Object obj) {
        try{
            //解析json
            Map<String,Object> map = CommonUtil.parseDataToMap(obj);
            List<KnowledgeTypeEntity> list = CommonUtil.parseDataToList(map.get("list"),new TypeToken<List<KnowledgeTypeEntity>>(){});
            mDatas.addAll(list);
            knowledgeTypeAdapter.notifyDataSetChanged();
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

        }catch (Exception e){
            e.printStackTrace();
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onSuccess(int type, Object obj) {

    }

    @Override
    public void onDeleteCollect(ResponseBody body) {

    }

    @Override
    public void onError(int type) {

    }
}
