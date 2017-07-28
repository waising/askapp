package com.asking91.app.ui.onlineqa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.onlineqa.OnlineQAListEntity;
import com.asking91.app.ui.adapter.onlineqa.OnlineQAFragmentAdapter;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.JLog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;

/**
 * Created by wxiao on 2016/10/31.
 * 在线问答--待解决
 */

public class OnlineQAFragment1 extends BaseOnlineFragment<OnlineQAPresenterImpl, OnlineQAModelImpl> implements OnlineQAContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout swipeLayout;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    private List<OnlineQAListEntity> onlineQAListEntities;
    private OnlineQAFragmentAdapter onlineQAFragmentAdapter;
    /**
     * 0--全部问题；1--待解决问题；2--高分问题
     */
    private int page = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_onlineqa1);
        page = getArguments().getInt("page");
        ButterKnife.bind(this, getContentView());
        getContext().registerReceiver(receiver, new IntentFilter("com.asking91.app.ui.onlineqa.OnlineQAFragment1"));
    }

    @Override
    public void initData() {
        super.initData();
        onlineQAListEntities = new ArrayList<>();
        //ListView效果的 LinearLayoutManager
//        LinearLayoutManager mgr = new LinearLayoutManager(getContext());
//        //VERTICAL纵向，类似ListView，HORIZONTAL<span style="font-family: Arial, Helvetica, sans-serif;">横向，类似Gallery</span>
//        mgr.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(mgr);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initView() {
        super.initView();
//        //设置recyclerView的行间距
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                if(parent.getChildLayoutPosition(view)!=0){
//                    outRect.top = CommonUtil.dip2px(getContext(), 11.3f);
//                    JLog.i(mthis.getClass().getSimpleName(),"parent.getChildLayoutPosition(view)="+parent.getChildLayoutPosition(view)+"--outRect.top ="+outRect.top );
//                }
//            }
//        });
        onlineQAFragmentAdapter = new OnlineQAFragmentAdapter(getContext(), onlineQAListEntities);
        recyclerView.setAdapter(onlineQAFragmentAdapter);
    }

    /**回答成功的，回答统计自动+1*/
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int position = intent.getIntExtra("position", -1);
            String anserId = intent.getStringExtra("anserId");
            if(position!=-1) {
                if(!getUserConstant().getUserId().equals(onlineQAListEntities.get(position).getUserId())){//是自己就是追问，不需要+1
                    //不是自己回答，看问题Id是不是null
                    if (null == anserId || anserId.length() == 0) {
                        onlineQAListEntities.get(position).setAnswerSize(onlineQAListEntities.get(position).getAnswerSize() + 1);
                        onlineQAFragmentAdapter.notifyItemChanged(position);
                    }
                }
            }
        }
    };

    @Override
    public void onDestroyView() {
        getContext().unregisterReceiver(receiver);
        super.onDestroyView();
    }

    @Override
    public void initLoad() {
        super.initLoad();
        getData();
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                start += limit;
                getDataNow();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                start = 0;
                onlineQAListEntities.clear();
                getDataNow();
            }
        });
    }

    private int start = 0, limit = 10, state;

    private void getData() {
        state = multiStateView.getViewState();
        if (state == MultiStateView.VIEW_STATE_EMPTY || state == MultiStateView.VIEW_STATE_ERROR) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            getDataNow();
            return;
        }

        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeLayout.autoRefresh();
            }
        });

    }

    private void getDataNow() {
        if (page == 0) {

            mPresenter.onlineQAonlineQA("0", null, start, limit, km, levelId);
            JLog.logi(this.getClass().getSimpleName(), "page=0");
        } else if (page == 1) {
            mPresenter.onlineQAonlineQA("1", "1", start, limit, km, levelId);
//            mPresenter.onlineQAonlineQA("1", "1", start, limit);
            JLog.logi(this.getClass().getSimpleName(), "page=1");
        } else if (page == 2) {
            mPresenter.onlineQAonlineQA("4", "1", start, limit, km, levelId);
//            mPresenter.onlineQAonlineQA("4", "1", start, limit, action);
            JLog.logi(this.getClass().getSimpleName(), "page=2");
        }
    }

    @Override
    public void onOnlineQAonlineQASuccess(ResponseBody obj) {
        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
        try {
            JSONObject object = new JSONObject(obj.string());
            JLog.i(mthis.getClass().getSimpleName(), "onOnlineQAonlineQASuccess==" + object.toString());
            if (object.getString("list") != null) {
                JSONArray array = object.getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    OnlineQAListEntity onlineQAListEntity = new Gson().fromJson(array.getString(i), OnlineQAListEntity.class);
                    onlineQAListEntities.add(onlineQAListEntity);
                }
                onlineQAFragmentAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefreshData(ResponseBody obj) {
        try {
            onlineQAListEntities.clear();
            JSONObject object = new JSONObject(obj.string());
            if (object.getString("list") != null) {
                JSONArray array = object.getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    OnlineQAListEntity onlineQAListEntity = new Gson().fromJson(array.getString(i), OnlineQAListEntity.class);
                    onlineQAListEntities.add(onlineQAListEntity);
                }
                multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                onlineQAFragmentAdapter.notifyDataSetChanged();
            } else {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            showShortToast("解析异常");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadMoreData(ResponseBody obj) {
        try {
            JSONObject object = new JSONObject(obj.string());
            if (object.getString("list") != null) {
                JSONArray array = object.getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    OnlineQAListEntity onlineQAListEntity = new Gson().fromJson(array.getString(i), OnlineQAListEntity.class);
                    onlineQAListEntities.add(onlineQAListEntity);
                }
                onlineQAFragmentAdapter.notifyDataSetChanged();
            } else {
                if (onlineQAListEntities.size() == 0) {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            showShortToast("解析异常");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(String method, String str) {

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        multiStateView.setViewState(multiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onRequestEnd() {
        swipeLayout.refreshComplete();
    }

    @Override
    public void onRefreshListData(String km, String levelId) {
        super.onRefreshListData(km, levelId);
        this.km = km;
        this.levelId = levelId;
        if (swipeLayout != null) {
            swipeLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeLayout.autoRefresh();
                }
            });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
