package com.asking91.app.ui.basepacket.details;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.basepacket.KnowledgeDetailEntity;
import com.asking91.app.global.BasePacketConstant;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.adapter.basepacket.KnowledgeDetailAdapter;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by wxwang on 2016/11/4.
 */
public class KnowledgeDetailFragment extends BaseFrameFragment<KnowledgeDetailPresenter,KnowledgeDetailModel>
        implements KnowledgeDetailContract.View {

    @BindView(R.id.recycler_detail)
    RecyclerView recyclerView;

    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    String versionLevelId,id;

    List<KnowledgeDetailEntity> mDatas;
    KnowledgeDetailAdapter knowledgeDetailAdapter;

    CallBackValue callBackvalue;

    String tipId,tipName,km;
    int reviewSize = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_knowledge_detail);
        ButterKnife.bind(this,getContentView());
    }

    @Override
    public void initData() {
        super.initData();
        versionLevelId = getArguments().getString("versionLevelId");
        id = getArguments().getString("id");
        km = getArguments().getString("type");

        mDatas = new ArrayList<>();
        knowledgeDetailAdapter = new KnowledgeDetailAdapter(getContext(),mDatas,recyclerView);

        callBackvalue =(CallBackValue) getActivity();
    }

    @Override
    public void initView() {
        super.initView();
        LinearLayoutManager mgr = new LinearLayoutManager(getContext());
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mgr);

        recyclerView.setAdapter(knowledgeDetailAdapter);

        mMultiStateView.setErrorVisibility(View.VISIBLE);
    }

    @Override
    public void initLoad(){
        super.initLoad();
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        if(versionLevelId == null){
            mPresenter.getKnowledgeDetail(id);
        }else{
            mPresenter.getKnowledge(versionLevelId,id);
        }

        if(getUserConstant().isTokenLogin())
            mPresenter.checkCollection(Constants.Collect.knowledge,tipId);
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
    }

    @Override
    public void onSuccess(int type,Object obj) {

        switch (type){
            case BasePacketConstant.KnowledgeDetail.GET_KNOWEDGE:
                getKnowledge(obj);
                break;
            case BasePacketConstant.KnowledgeDetail.SAVE_COLLECTION:
                Map<String,Object> map = CommonUtil.parseDataToMap(obj);

                if("0".equals(map.get("flag")))
                    callBackvalue.SendChangeCollImg(String.valueOf(map.get("msg")),true);
                else
                    showShortToast("收藏失败"+map.get("msg").toString());
                break;
            case BasePacketConstant.KnowledgeDetail.CHECK_COLLECTION:

                break;
        }

    }


    /**
     * 删除收藏
     * @param body
     */
    @Override
    public void onDeleteCollect(ResponseBody body) {
        Map<String,Object> map = CommonUtil.parseDataToMap(body);
        if(map.get("flag")!=null && "0".equals(String.valueOf(map.get("flag")))){
            callBackvalue.SendChangeCollImg(String.valueOf(map.get("msg")),false);
        }
    }

    @Override
    public void onError(int type) {
        if(type == BasePacketConstant.KnowledgeDetail.CHECK_COLLECTION){

        }else if(type == BasePacketConstant.KnowledgeDetail.SAVE_COLLECTION){
            showShortToast("收藏失败");
        }
    }

    private void getKnowledge(Object obj){
        try {
            Map<String,Object> map = CommonUtil.parseDataToMap(obj);
            List<KnowledgeDetailEntity> list = CommonUtil.parseDataToList(map.get("attrList"),new TypeToken<List<KnowledgeDetailEntity>>(){});

            mDatas.clear();
            KnowledgeDetailEntity knowledgeDetailEntity = null;
            //删除不正确数据
            if (list!=null && list.size()>0){
                Iterator it = list.iterator();
                while(it.hasNext()){
                    knowledgeDetailEntity = (KnowledgeDetailEntity)it.next();
                    if (knowledgeDetailEntity.getAttrId()==0){
                        reviewSize = knowledgeDetailEntity.getReviewSize();

                        it.remove();
                    }

                }

                tipName = list.get(0).getTipName();
                tipId = list.get(0).getTipId();

                mDatas.addAll(list);

                knowledgeDetailAdapter.setReviewSize(reviewSize);
                knowledgeDetailAdapter.notifyDataSetChanged();
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);

            }else{
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }

        }catch (Exception e){
            e.printStackTrace();
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }

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

    }

    @Override
    public void onRefreshData(Object o) {
        getKnowledge(o);

    }

    @Override
    public void onLoadMoreData(Object o) {

    }

    public interface CallBackValue{
        void SendChangeCollImg(String msg,boolean isCollected);
    }

    public void collectionSave(){
        if(!TextUtils.isEmpty(tipName) && !TextUtils.isEmpty(tipId)){
            mPresenter.saveCollection(Constants.Collect.knowledge,tipName,tipId,km);
        }else{
            showShortToast("参数缺失，该知识点无法收藏");
        }
    }

    public void collectionDelete(){
        if(!TextUtils.isEmpty(tipId)){
            mPresenter.deleteCollection(tipName,tipId);
        }else{
            showShortToast("参数缺失，该知识点无法删除");
        }
    }
}
