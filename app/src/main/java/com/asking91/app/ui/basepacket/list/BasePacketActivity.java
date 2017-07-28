package com.asking91.app.ui.basepacket.list;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.asking91.app.R;
import com.asking91.app.common.SpaceItemDecoration;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.basepacket.BasePacketEntity;
import com.asking91.app.entity.basepacket.SubjectCacaLogEntity;
import com.asking91.app.global.BasePacketConstant;
import com.asking91.app.global.Constants;
import com.asking91.app.global.UserConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.basepacket.BasePacketAdapter;
import com.asking91.app.ui.pay.PayServerActivity;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.CommonUtil;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.view.BigImageView;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class BasePacketActivity extends BaseFrameActivity<BasePacketPresenter, BasePacketModel> implements Toolbar.OnMenuItemClickListener ,
        BasePacketContract.View{

    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;

    @BindView(R.id.basepacket_rv)
    RecyclerView recylcer;

    @BindView(R.id.mBigImage)
    BigImageView mBigImageView;

    @BindString(R.string.main_t1)
    String main_t1;

    BasePacketAdapter basePacketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basepacket);

        ButterKnife.bind(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_buy://判断登陆状态
                CommonUtil.openAuthActivity(this, PayServerActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, main_t1);
        mToolBar.inflateMenu(R.menu.menu_go_buy);

        //判断登陆状态
        if (UserConstant.getInstance(this).isTokenLogin()) {
            mBigImageView.setVisibility(View.GONE);
            GridLayoutManager mgr=new GridLayoutManager(this,2);
            recylcer.addItemDecoration(new SpaceItemDecoration(10));
            recylcer.setLayoutManager(mgr);
        }else{
            showBigImageView();
        }
    }

    private void showBigImageView(){
        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        multiStateView.setVisibility(View.GONE);
        mBigImageView.setVisibility(View.VISIBLE);
        BigImageViewer.prefetch(Uri.parse(Constants.basepacket_info_pic));
        mBigImageView.showImage(Uri.parse(Constants.basepacket_info_pic));
    }

    @Override
    public void initListener() {
        super.initListener();
        mToolBar.setOnMenuItemClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initLoad() {
        super.initLoad();
        if (UserConstant.getInstance(this).isTokenLogin()) {
            mPresenter.getSubjectCacaLogList();

            //查看资料是否完善
//            if(!getUserConstant().isUserDataPerfect())
//                mPresenter.checkUserInfo();
        }
    }

    @Override
    public void onRequestStart() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        //
        mToolBar.getMenu().getItem(0).setVisible(false);
    }

    @Override
    public void onRequestEnd() {

    }

    BasePacketEntity getEntity(String name, int imgUrl, String type){
        BasePacketEntity basePacketEntity = new BasePacketEntity();
        basePacketEntity.setName(name);
        basePacketEntity.setImgurl(imgUrl);
        basePacketEntity.setType(type);

        return basePacketEntity;
    }

    @Override
    public void onSuccess(int type,ResponseBody body) {
        switch (type){
            case BasePacketConstant.Knowledge.KNOWLEDGE_LIST:
                setBasePacketData(body);
                break;
            case BasePacketConstant.Knowledge.CHECKUSERINFO:
                //资料是否完善
//                Map<String,Object> map = CommonUtil.parseDataToMap(body);
//                getUserConstant().setIsUserDataPerfect(map.get("flag")!=null&& "0".equals(map.get("flag")));
                break;
        }

    }

    private Map<String,BasePacketEntity> getMipMap(){
        ArrayMap<String ,BasePacketEntity> map = new ArrayMap<>();
        map.put(Constants.SubjectCatalog.CZSX,getEntity(getString(R.string.ask_czsx),R.mipmap.backpacket_czsx, Constants.SubjectCatalog.CZSX));
        map.put(Constants.SubjectCatalog.CZWL,getEntity(getString(R.string.ask_czwl),R.mipmap.backpacket_czwl, Constants.SubjectCatalog.CZWL));
        map.put(Constants.SubjectCatalog.GZSX,getEntity(getString(R.string.ask_gzsx),R.mipmap.backpacket_gzsx, Constants.SubjectCatalog.GZSX));
        map.put(Constants.SubjectCatalog.GZWL,getEntity(getString(R.string.ask_gzwl),R.mipmap.backpacket_gzwl, Constants.SubjectCatalog.GZWL));

        return map;
    }

    private void setBasePacketData(ResponseBody body){
        try {
            ArrayMap<String,Object> map = CommonUtil.parseDataToMap(body);
            if(map.get("flag")!=null&& "1".equals(map.get("flag"))){
                showShortToast(String.valueOf(map.get("msg")));
                return;
            }
            List<SubjectCacaLogEntity> list = CommonUtil.parseDataToList(map.get("list"),new TypeToken<List<SubjectCacaLogEntity>>(){});

            if(list !=null && list.size()>0){
                basePacketAdapter = new BasePacketAdapter(this,list);
//                basePacketAdapter.setUserDataPerfect(getUserConstant().isUserDataPerfect());
                recylcer.setAdapter(basePacketAdapter);
                basePacketAdapter.setMap(getMipMap());
                basePacketAdapter.notifyDataSetChanged();
                multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }else{
                showBigImageView();
            }

        } catch (Exception e) {
            e.printStackTrace();
            multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }
}
