package com.asking91.app.ui.onlineqa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.onlineqa.OnlineQADetailEntity;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.onlineqa.OnlineQADetailAdapter;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.RecycleViewDivider;
import com.asking91.app.util.JLog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;


/**
 * Created by wxiao on 2016/11/2.
 * 在线问答--问题详情
 */

public class OnlineQADetailActivity extends BaseFrameActivity<OnlineQAPresenterImpl, OnlineQAModelImpl> implements  OnlineQAContract.View{

    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.title_more)
    ImageView titleMore;
    @BindView(R.id.title_click)
    LinearLayout titleClick;
    @BindView(R.id.user_info_rl)
    RelativeLayout userInfoRl;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout swipeLayout;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    @BindView(R.id.reply)
    Button reply;

    private String id, userId;
    private OnlineQADetailAdapter onlineQADetailAdapter;
    private OnlineQADetailEntity e;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlineqa_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        id = getIntent().getStringExtra("id");
        userId = getIntent().getStringExtra("userId");
        registerReceiver(receiver, new IntentFilter("com.asking91.app.ui.onlineqa.OnlineQADetailActivity"));
    }

    @Override
    public void initView() {
        super.initView();
        //设置分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(this, R.color.login_line)));
        recyclerView.setLayoutManager(new LinearLayoutManager(mthis));
        toolBar.setTitle("问题详情");
        if(userId!=null&&userId.equals(getUserConstant().getUserEntity().getId())){
            flag = true;
            reply.setVisibility(View.GONE);
        }else{
            flag = false;
            reply.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initLoad() {
        super.initLoad();
        multiStateView.setViewState(multiStateView.VIEW_STATE_LOADING);
        mPresenter.onlineQADetail(id);
    }
    /**采纳问题*/
    public void adoptAnswer(String id, String anserId, int position){
        mPresenter.onlineQaAdoptAnswer(id, anserId, position);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(onlineQADetailAdapter!=null){
                onlineQADetailAdapter.updateAnswerSize(intent.getStringExtra("anserId"));
                mPresenter.onlineQADetail(id);
            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void initListener() {
        super.initListener();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @OnClick({R.id.reply})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.reply://我要回答
                if(e==null){
                    showShortToast("暂无数据");
                }else {
                    openActivity(OnlineQaAnwserActivity.class, getIntent().getExtras());
                }
                break;
        }
    }

    @Override
    public void onOnlineQAonlineQASuccess(ResponseBody obj) {
        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
        try {
            String str = obj.string();
            JLog.logi(mthis.getClass().getSimpleName(), str);
            JSONObject object = new JSONObject(str);
            JLog.i(mthis.getClass().getSimpleName(),"onOnlineQAonlineQASuccess=="+object.toString());
            String x=object.getString("userQA");
            OnlineQADetailEntity eTmp = new Gson().fromJson(x, OnlineQADetailEntity.class);
            if(eTmp.getState().equals("2")){
                reply.setVisibility( View.GONE);//已采纳就不显示我要回答
            }
            if(e==null){
                e = eTmp;
                onlineQADetailAdapter = new OnlineQADetailAdapter(mthis, e, flag);
                recyclerView.setAdapter(onlineQADetailAdapter);
            }else{
                onlineQADetailAdapter.insertData(eTmp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onRefreshData(ResponseBody obj) {

    }

    @Override
    public void onLoadMoreData(ResponseBody obj) {

    }

    @Override
    public void onSuccess(String method, String str) {
        if(method.indexOf("onlineQaAdoptAnswer")!=-1){//采纳问题
            multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
            finish();
//            //采纳成功
            int position = Integer.parseInt(method.substring("onlineQaAdoptAnswer-".length()));
            onlineQADetailAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onRequestStart() {
        multiStateView.setViewState(multiStateView.VIEW_STATE_LOADING);

    }

    @Override
    public void onRequestEnd() {
        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);

    }
}
