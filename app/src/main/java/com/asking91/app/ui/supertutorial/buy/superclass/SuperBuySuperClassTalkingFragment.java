package com.asking91.app.ui.supertutorial.buy.superclass;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.supertutorial.SuperSelectContract;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.MusicPlayer;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by wxiao on 2016/11/28.
 * 超级辅导课--免费课程--超级课堂--有话说
 */

public class SuperBuySuperClassTalkingFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> implements SuperSelectContract.View {
    @BindView(R.id.mathView)
    AskMathView mathView;

    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;

    @BindView(R.id.tip1)
    TextView tip1;

    @BindView(R.id.voice)
    AskSimpleDraweeView voice;

    @BindView(R.id.top_tip)
    RelativeLayout topTip;

    /**
     * 判断是否重新选择过章节内容
     */
    private boolean isChange = true;
    private String versionLevelId;
    private String knowledgeId;

    public static SuperBuySuperClassTalkingFragment newInstance(Bundle bundle) {
        SuperBuySuperClassTalkingFragment fragment = new SuperBuySuperClassTalkingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_supertutorial_superclass_talking);
        ButterKnife.bind(this, getContentView());

        Bundle bundle = getArguments();
        if (bundle != null) {
            versionLevelId = bundle.getString("gradeId");
            knowledgeId = bundle.getString("knowledgeId");
        }
    }

    @Override
    public void initView() {
        super.initView();
        musicPlayer = MusicPlayer.getPlayer().bindVoice(getActivity(), voice);

        voice.setBackgroundResource(R.mipmap.super_talking_voice);
        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);

        initLoadData(versionLevelId, knowledgeId);
    }

    public void initLoadData(String versionLevelId, String knowledgeId) {
        this.versionLevelId = versionLevelId;
        this.knowledgeId = knowledgeId;

        isChange = true;
        if (musicPlayer != null && musicPlayer.isPlaying()) {
            musicPlayer.pause();
        }
        if (mathView != null) {
            mathView.formatMath().showWebImage(multiStateView);
            multiStateView.setViewState(multiStateView.VIEW_STATE_LOADING);
            mPresenter.getSuperBuyFragment1(versionLevelId, knowledgeId, 1);
        }
    }

    private MusicPlayer musicPlayer;
    private String musicUrl;

    @OnClick(R.id.voice)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voice:
                if (isChange) {//有重新选择章节
                    if (!musicPlayer.isPlaying()) {
                        mPresenter.getVoicePath(versionLevelId,knowledgeId, 1, 1);
                    } else {
                        musicPlayer.pause();
                        voice.setBackgroundResource(R.mipmap.super_talking_voice);
                    }
                } else {
                    if (!musicPlayer.isPlaying()) {
                        musicPlayer.play(musicUrl);
                        voice.setBackgroundResource(R.mipmap.super_talking_voice_stop);
                    } else {
                        musicPlayer.pause();
                        voice.setBackgroundResource(R.mipmap.super_talking_voice);
                    }
                }
                break;
        }
    }

    @Override
    public void onGetSuperSelectSuccess(ResponseBody obj) {
        try {
            String result = obj.string();
            JSONObject jsonRes = JSON.parseObject(result);
            mathView.setWebText(jsonRes.getString("attrContentHtml"));
            multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        if (requestEntity.getMessage().indexOf("Document not found") != -1) {
            musicPlayer.pause();
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
    public void onDestroyView() {
        if (musicPlayer.isPlaying()) {//释放音频资源
            musicPlayer.pause();
            voice.setBackgroundResource(R.mipmap.super_talking_voice);
        }
        super.onDestroyView();
    }

    @Override
    public void onResultSuccess(String method, ResponseBody res) {
        if (method.equals("getVoicePath")) {
            try {
                isChange = false;
                musicUrl = res.string();
                musicPlayer.play(musicUrl);
                voice.setBackgroundResource(R.mipmap.super_talking_voice_stop);
            } catch (IOException e) {
                e.printStackTrace();
                showShortToast("暂无音频信息");
            }
        }
    }


    @Override
    public void onResultSuccess(String method, ResponseBody res, int position) {

    }

}
