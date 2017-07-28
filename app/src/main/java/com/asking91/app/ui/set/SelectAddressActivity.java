package com.asking91.app.ui.set;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.asking91.app.R;
import com.asking91.app.entity.onlineqa.OnlineQADetailEntity;
import com.asking91.app.entity.user.UserEntity;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.mine.SelectAddressAdapter;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.mine.MineContract;
import com.asking91.app.ui.mine.MineModel;
import com.asking91.app.ui.mine.MinePresenter;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;

// 修改省市县

public class SelectAddressActivity extends BaseFrameActivity<MinePresenter, MineModel> implements MineContract.View{

    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout swipeLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    SelectAddressAdapter selectAddressAdapter;
    String token, strAddress = "",strRegionCode;

    private final static int GET_BACK_SCHOOL_LOCAL = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynote);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        token = getUserConstant().getToken();
        if(token==null){
            CommonUtil.openActivity(this,LoginActivity.class,null);
        }
        mPresenter.getRegionInfo("0");
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.basepacket_plase_choose));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    SelectAddressAdapter.ClickItem clickItem = new SelectAddressAdapter.ClickItem() {
        @Override
        public void setClickItem(String regionCode, String regionName) {
            mPresenter.getRegionInfo(regionCode);
            strAddress+=(regionName+" - ");
            strRegionCode = regionCode;
        }
    };

    @Override
    public void onSuccess(int type, ResponseBody body) {
        swipeLayout.refreshComplete();
        List<UserEntity> userEntity = CommonUtil.parseDataToList(body, new TypeToken<List<UserEntity>>() {
        });
        if(userEntity!=null && userEntity.size()>0){
            if(selectAddressAdapter==null){
                selectAddressAdapter = new SelectAddressAdapter(this,userEntity);
            }else{
                selectAddressAdapter.setData(userEntity);
                selectAddressAdapter.notifyDataSetChanged();
            }

            selectAddressAdapter.getClickItem(clickItem);
            recyclerView.setAdapter(selectAddressAdapter);
        }else{
            if(strAddress!=null && strAddress.length()>1){
                String substring = strAddress.substring(0, strAddress.length() - 3);
                int resultCode = GET_BACK_SCHOOL_LOCAL; // 222 是省市县
                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                bundle.putString("address",substring);
                bundle.putString("RegionCode",strRegionCode);
                intent.putExtras(bundle);
                setResult(resultCode,intent);
                SelectAddressActivity.this.finish();
            }
        }
    }
    @Override
    public void onError(int type) {
        swipeLayout.refreshComplete();
    }
    @Override
    public void onRefreshData(ResponseBody responseBody) {
    }
    @Override
    public void onLoadMoreData(ResponseBody responseBody) {
    }
    @Override
    public void onRequestStart() {

    }
    @Override
    public void onRequestEnd() {

    }







}
