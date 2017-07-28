package com.asking91.app.ui.supertutorial.buy.superclass;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.supertutorial.SuperClassQuestionTimeEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.adapter.supertutorial.SuperClassQuestionTimeAdapter;
import com.asking91.app.ui.supertutorial.SuperSelectContract;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.MusicPlayer;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by wxiao on 2016/11/29.
 * 免费试学-问答时间
 */

public class SuperBuySuperClassQuestionTimeFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> implements SuperSelectContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerComment;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    private ArrayList<SuperClassQuestionTimeEntity> dataList = new ArrayList<>();
    private SuperClassQuestionTimeAdapter superClassQuestionTimeAdapter;
    /**
     * 判断是否重新选择过章节内容
     */
    private boolean isChange = true;
    private String versionLevelId;
    private String knowledgeId;

    public static SuperBuySuperClassQuestionTimeFragment newInstance(Bundle bundle) {
        SuperBuySuperClassQuestionTimeFragment fragment = new SuperBuySuperClassQuestionTimeFragment();;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_supertutorial_superclass_questiontime);
        ButterKnife.bind(this, getContentView());

        Bundle bundle = getArguments();
        if (bundle != null) {
            versionLevelId = bundle.getString("gradeId");
            knowledgeId = bundle.getString("knowledgeId");
        }
    }

    private MusicPlayer musicPlayer;

    @Override
    public void initData() {
        super.initData();
        musicPlayer = MusicPlayer.getPlayer();
        superClassQuestionTimeAdapter = new SuperClassQuestionTimeAdapter(getContext(), dataList, recyclerComment, musicPlayer, this);
    }

    private int prePosition = -1;
    private String musicUrl;
    private AskSimpleDraweeView voice;

    /**
     * 播放Adapter里音频
     */
    public void playVoice(int position, AskSimpleDraweeView voice) {
        this.voice = voice;
        musicPlayer.bindVoice(getActivity(), voice);
        if (isChange) {//有重新选择章节
            mPresenter.getVoicePath(versionLevelId,knowledgeId, 2, position + 1, position+1);
        } else {
            if (musicPlayer != null) {
                if (prePosition != position) {
                    mPresenter.getVoicePath(versionLevelId,knowledgeId, 2, position + 1, position+1);
                } else if (musicPlayer.isPlaying()) {
                    musicPlayer.pause();
                    voice.setBackgroundResource(R.mipmap.super_talking_voice);
                } else {
                    musicPlayer.play(musicUrl);
                    voice.setBackgroundResource(R.mipmap.super_talking_voice_stop);
                }
            }
        }
    }

    @Override
    public void initView() {
        super.initView();
        recyclerComment.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerComment.setAdapter(superClassQuestionTimeAdapter);
    }

    @Override
    public void initLoad() {
        super.initLoad();
        if (versionLevelId != null && knowledgeId != null) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            mPresenter.getSuperBuyFragment1(versionLevelId, knowledgeId, 2);
        }
    }

    public void initLoadData(String versionLevelId, String knowledgeId) {
        isChange = true;
        if (musicPlayer != null && musicPlayer.isPlaying()) {
            musicPlayer.pause();
            prePosition = -1;
        }
        this.versionLevelId = versionLevelId;
        this.knowledgeId = knowledgeId;
        if (multiStateView != null) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            mPresenter.getSuperBuyFragment1(versionLevelId, knowledgeId, 2);
        }
    }

    @Override
    public void onDestroyView() {
        if (musicPlayer.isPlaying()) {
            musicPlayer.pause();
        }
        super.onDestroyView();
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }


    @Override
    public void onGetSuperSelectSuccess(ResponseBody obj) {
        try {
            String result = obj.string();
            dataList.clear();
            dataList.addAll(JSON.parseArray(result,SuperClassQuestionTimeEntity.class));
            superClassQuestionTimeAdapter.notifyDataSetChanged();
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFreeStudySuccess(ResponseBody obj) {

    }

    @Override
    public void onFreeStudyVersionSuccess(ResponseBody obj) {

    }

    @Override
    public void onRefreshData(ResponseBody res) {

    }

    @Override
    public void onLoadMoreData(ResponseBody res) {

    }

    @Override
    public void onResultSuccess(String method, ResponseBody res) {

    }

    @Override
    public void onResultSuccess(String method, ResponseBody res, int position) {
        if (method.equals("getVoicePath")) {
            try {
                isChange = false;
                musicUrl = res.string();
                musicPlayer.play(musicUrl);
                prePosition = position;
                if(voice!=null)
                    voice.setBackgroundResource(R.mipmap.super_talking_voice_stop);
            } catch (IOException e) {
                e.printStackTrace();
                showShortToast("暂无音频信息");
            }
        }
    }
}
