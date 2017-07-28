package com.asking91.app.ui.refer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.asking91.app.R;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.refer.ReferEntity;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.refer.ReferAdapter;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by wwx on 2016/10/28.
 */

public class ReferActivity extends BaseFrameActivity<ReferPresenter,ReferModel>
implements ReferContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    @BindView(R.id.refer_rv)
    RecyclerView mReferRv;

    ArrayMap<Integer,LabelEntity> mLabels;
    List<ReferEntity> mDatas;
    ReferAdapter mReferAdapter;
    private int START = 0;
    private int LIMIT = 3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        setDatas();

        mDatas = new ArrayList<>();
        mReferAdapter = new ReferAdapter(this,mDatas);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, getString(R.string.refer));
        LinearLayoutManager mgr = new LinearLayoutManager(this);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        mReferRv.setLayoutManager(mgr);
        //设置分割线
//        mReferRv.addItemDecoration(new RecycleViewDivider(
//                getContext(), LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(getContext(), R.color.white)));

        mReferRv.setAdapter(mReferAdapter);

        multiStateView.setErrorVisibility(View.VISIBLE);

    }

    @Override
    public void initListener() {
        super.initListener();
        multiStateView.setErrorOnClickLinstener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getReferList(mLabels.get(R.id.refer_more_tv).getId(),0,LIMIT);
            }
        });
    }

    @Override
    public void initLoad(){
        super.initLoad();

        mPresenter.getReferList(mLabels.get(R.id.refer_more_tv).getId(),START,LIMIT);
    }

    //赋值
    @SuppressLint("UseSparseArrays")
    public void setDatas(){
        mLabels = new ArrayMap<>();
        mLabels.put(R.id.zkzx_iv,new LabelEntity(R.mipmap.refer_nav_2,"65e16c27-c4d2-4827-83c4-3cff5c39480a",getString(R.string.refer_zkzx)));
        mLabels.put(R.id.rdht_iv,new LabelEntity(R.mipmap.refer_nav_3,"fefeb071-98ac-4878-9664-cbecd1297702",getString(R.string.refer_rdht)));
        mLabels.put(R.id.gkzx_iv,new LabelEntity(R.mipmap.refer_nav_1,"5813be93-50cb-439e-9675-2c3cce7b9b98",getString(R.string.refer_gkzx)));

        mLabels.put(R.id.refer_sx_iv,new LabelEntity(R.mipmap.refer_nav_1,"6a43ebdb-cb69-478b-b560-5d94076919df",getString(R.string.refer_qwsx)));
        mLabels.put(R.id.refer_sx_tv, mLabels.get(R.id.refer_sx_iv));

        mLabels.put(R.id.refer_wl_iv,new LabelEntity(R.mipmap.refer_nav_1,"94e798ff-38ab-461b-98c1-152ed726bb28",getString(R.string.refer_qwwl)));
        mLabels.put(R.id.refer_wl_tv, mLabels.get(R.id.refer_wl_iv));

        mLabels.put(R.id.refer_hx_iv,new LabelEntity(R.mipmap.refer_nav_1,"6e12b4d5-722e-484a-8a69-0a86b7b657d1",getString(R.string.refer_qwhx)));
        mLabels.put(R.id.refer_hx_tv, mLabels.get(R.id.refer_hx_iv));

        mLabels.put(R.id.refer_xl_iv,new LabelEntity(R.mipmap.refer_nav_1,"80de2eed-9570-4436-926d-183745b50b6a",getString(R.string.refer_qwxl)));
        mLabels.put(R.id.refer_xl_tv, mLabels.get(R.id.refer_xl_iv));

        //更多
        mLabels.put(R.id.refer_more_tv,new LabelEntity(0,"f732c6ee-6f2d-4153-8a7b-09d0833edebb",getString(R.string.refer_xhwy)));
    }

    @OnClick({R.id.zkzx_iv,R.id.rdht_iv,R.id.gkzx_iv,R.id.refer_sx_iv,R.id.refer_sx_tv,R.id.refer_wl_iv,R.id.refer_wl_tv,
        R.id.refer_hx_iv,R.id.refer_hx_tv,R.id.refer_xl_iv,R.id.refer_xl_tv,R.id.refer_more_tv})
    public void onClick(View view){
        Bundle bundle = new Bundle();
        bundle.putParcelable("refer",mLabels.get(view.getId()));
        openActivity(ReferListActivity.class,bundle);
    }

    @Override
    public void onSuccess(int type,ResponseBody body) {
    }

    @Override
    public void onError(int type) {

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onRequestError(RequestEntity requestEntity){
        super.onRequestError(requestEntity);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onRefreshData(ResponseBody body) {
        ArrayMap<String,Object> map = CommonUtil.parseDataToMap(body);

        List<ReferEntity> list = CommonUtil.parseDataToList(map.get("list"),new TypeToken<List<ReferEntity>>(){});
        mDatas.clear();
        if(list!=null && list.size()>0){
            mDatas.addAll(list);
            mReferAdapter.notifyDataSetChanged();
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }else
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void onLoadMoreData(ResponseBody body) {

    }

}
