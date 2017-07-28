package com.asking91.app.ui.onlineqa;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.onlineqa.OnlineQaMyAskEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.adapter.onlineqa.OnlineQaMyAskAdapter;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.ui.widget.MultiStateView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;

/**
 * Created by wxiao on 2016/11/8.
 * 我的提问
 */

public class OnlineQaMyAskFragment extends BaseFrameFragment<OnlineQaMyQaPresenter, OnlineQaMyQaModel> implements OnlineQaMyQaPresenter.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout swipeLayout;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;

    private List<OnlineQaMyAskEntity.OnlineQaMyAskDetailEntity> entities;
    private OnlineQaMyAskAdapter onlineQaMyQaAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_onlineqa1);
        ButterKnife.bind(this,getContentView());
    }

    @Override
    public void initData() {
        super.initData();
        entities = new ArrayList<>();
        onlineQaMyQaAdapter = new OnlineQaMyAskAdapter(getContext(), entities);
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(onlineQaMyQaAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                    start += limit;
                    mPresenter.onMyAsk(start, limit + "", "");
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                start = 0;
                mPresenter.onMyAsk(start, limit + "", "");
            }
        });
    }

    private int start = 0, limit = 10, state;
    @Override
    public void initLoad() {
        super.initLoad();
        state = multiStateView.getViewState();
        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeLayout.autoRefresh();
            }
        });
    }

    @Override
    public void onRequestStart() {
        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
    }


    @Override
    public void onRequestEnd() {
        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
        swipeLayout.refreshComplete();
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        multiStateView.setViewState(multiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onSuccess(ResponseBody res) {
        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
        try {
            OnlineQaMyAskEntity onlineQaMyAskEntity = new Gson().fromJson(res.string(), OnlineQaMyAskEntity.class);
            entities.addAll(onlineQaMyAskEntity.getList());
            onlineQaMyQaAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(String method, String str) {

    }

    @Override
    public void onRefreshData(ResponseBody res) {
        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
        try {
            OnlineQaMyAskEntity onlineQaMyAskEntity = new Gson().fromJson(res.string(), OnlineQaMyAskEntity.class);
            entities.clear();
            entities.addAll(onlineQaMyAskEntity.getList());
            onlineQaMyQaAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadMoreData(ResponseBody res) {
        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
        try {
            OnlineQaMyAskEntity onlineQaMyAskEntity = new Gson().fromJson(res.string(), OnlineQaMyAskEntity.class);
            entities.addAll(onlineQaMyAskEntity.getList());
            onlineQaMyQaAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
