package com.asking91.app.ui.refer;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.asking91.app.R;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.refer.ReferEntity;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.refer.ReferAdapter;
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
 * Created by wxwang on 2016/11/7.
 */
public class ReferListActivity extends BaseFrameActivity<ReferPresenter,ReferModel>
        implements ReferContract.View  {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout mSwipeLayout;

    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    List<ReferEntity> mDatas;
    ReferAdapter mReferAdapter;

    LabelEntity labelEntity;

    private int START = 0;
    private int LIMIT = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_list);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, getString(R.string.refer_list));

        LinearLayoutManager mgr = new LinearLayoutManager(this);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mgr);
        //设置分割线
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(
//                this, LinearLayoutManager.VERTICAL, 8, ContextCompat.getColor(this, R.color.colorText_c7)));
        mRecyclerView.setAdapter(mReferAdapter);

        mToolBar.setTitle(labelEntity.getName());
    }

    @Override
    public void initData(){
        super.initData();

        labelEntity = getIntent().getParcelableExtra("refer");

        mDatas = new ArrayList<>();
        mReferAdapter = new ReferAdapter(this,mDatas);

    }

    @Override
    public void initLoad(){
        super.initLoad();
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
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

        mSwipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                START+=LIMIT;
                mPresenter.getReferList(labelEntity.getId(),START,LIMIT);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                START = 0;
                mPresenter.getReferList(labelEntity.getId(),START,LIMIT);
            }
        });
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {
        if (mSwipeLayout != null) mSwipeLayout.refreshComplete();
    }

    @Override
    public void onSuccess(int type,ResponseBody body) {
    }

    @Override
    public void onError(int type) {

    }

    @Override
    public void onRequestError(RequestEntity requestEntity){
        super.onRequestError(requestEntity);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onRefreshData(ResponseBody body) {
        try{
            //解析json
            Map<String,Object> map = CommonUtil.parseDataToMap(body);

            List<ReferEntity> list = CommonUtil.parseDataToList(map.get("list"),new TypeToken<List<ReferEntity>>(){});

            if (list == null || list.size() == 0){
                mSwipeLayout.refreshComplete();
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                return;
            }

            mDatas.clear();
            mDatas.addAll(list);
            mReferAdapter.notifyDataSetChanged();

            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);


        }catch (Exception e){
            e.printStackTrace();
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onLoadMoreData(ResponseBody body) {
        try{
            //解析json
            Map<String,Object> map = CommonUtil.parseDataToMap(body);
            List<ReferEntity> list = CommonUtil.parseDataToList(map.get("list"),new TypeToken<List<ReferEntity>>(){});
            mDatas.addAll(list);
            mReferAdapter.notifyDataSetChanged();
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

        }catch (Exception e){
            e.printStackTrace();
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }
}
