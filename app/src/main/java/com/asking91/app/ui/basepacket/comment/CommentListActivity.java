package com.asking91.app.ui.basepacket.comment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.asking91.app.R;
import com.asking91.app.common.SpaceItemDecoration;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.basepacket.CommentEntity;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.basepacket.KnowledgeCommentAdapter;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by wxwang on 2016/11/10.
 */
public class CommentListActivity extends BaseFrameActivity<CommentPresenter,CommentModel>
                                                implements CommentContract.View {

    @BindView(R.id.toolBar)
    android.support.v7.widget.Toolbar mToolbar;

    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout mSwipeLayout;

    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @BindView(R.id.recycler_comment)
    RecyclerView mCommentRv;

    KnowledgeCommentAdapter mKnowledgeCommentAdapter;
    List<CommentEntity> mDatas;

    private int start = 1;
    private int limit = 10;
    String tipId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
    }


    @Override
    public void initView(){
        super.initView();
        setToolbar(mToolbar, getString(R.string.comment_list));
        LinearLayoutManager mgr = new LinearLayoutManager(this);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        mCommentRv.setLayoutManager(mgr);

        mCommentRv.addItemDecoration(new SpaceItemDecoration(10).isFirstTop(true));

        mCommentRv.setAdapter(mKnowledgeCommentAdapter);

    }

    @Override
    public void initData(){
        super.initData();
        tipId = getIntent().getStringExtra("tipId");

        mDatas = new ArrayList<>();
        mKnowledgeCommentAdapter = new KnowledgeCommentAdapter(this,mDatas);
    }

    @Override
    public void initLoad(){
        super.initLoad();
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        //mPresenter.getReviewList(tipId,start,limit);
        mSwipeLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.autoRefresh();
            }
        });
    }

    @Override
    public void onSuccess(Object obj) {}

    @Override
    public void onRefreshData(Object obj) {
        Map<String,Object> map = CommonUtil.parseDataToMap(obj);
        List<CommentEntity> list = CommonUtil.parseDataToList(map.get("list"),new TypeToken<List<CommentEntity>>(){});
        if (list==null || list.size() == 0) {
            mSwipeLayout.refreshComplete();
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            return;
        }
        mDatas.clear();
        mDatas.addAll(list);
        mKnowledgeCommentAdapter.notifyDataSetChanged();

        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @Override
    public void onLoadMoreData(Object obj) {
        Map<String,Object> map = CommonUtil.parseDataToMap(obj);
        List<CommentEntity> list = CommonUtil.parseDataToList(map.get("list"),new TypeToken<List<CommentEntity>>(){});
        mDatas.addAll(list);
        mKnowledgeCommentAdapter.notifyDataSetChanged();

        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @Override
    public void initListener(){
        super.initListener();
        mSwipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                start+=limit;
                mPresenter.getReviewList(tipId,start,limit);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                start = 0;
                mPresenter.getReviewList(tipId,start,limit);
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
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

}
