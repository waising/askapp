package com.asking91.app.ui.supertutorial.buy.superclass;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.supertutorial.SummaryEntity;
import com.asking91.app.entity.supertutorial.SuperSummaryEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.supertutorial.SuperSelectContract;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.MusicPlayer;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by wxiao on 2016/11/30.
 * 免费试学--课堂总结
 */

public class SuperBuySuperClassSummaryFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> implements SuperSelectContract.View {


    @BindView(R.id.tip1)
    TextView tip1;
    @BindView(R.id.voice)
    AskSimpleDraweeView voice;
    @BindView(R.id.mathView)
    AskMathView mathView;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;

    public static SuperBuySuperClassSummaryFragment newInstance(Bundle bundle) {
        SuperBuySuperClassSummaryFragment fragment = new SuperBuySuperClassSummaryFragment();
        ;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_supertutorial_superclass_summary);
        ButterKnife.bind(this, getContentView());

        Bundle bundle = getArguments();
        if (bundle != null) {
            versionLevelId = bundle.getString("gradeId");
            knowledgeId = bundle.getString("knowledgeId");
        }
    }

    private MusicPlayer musicPlayer;

    @Override
    public void initView() {
        super.initView();
        musicPlayer = MusicPlayer.getPlayer();
        voice.setBackgroundResource(R.mipmap.super_talking_voice);
        mathView.formatMath().showWebImage(multiStateView);
        tip1.setText("课堂总结");
    }

    @Override
    public void initLoad() {
        super.initLoad();
        if (versionLevelId != null && knowledgeId != null) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            mPresenter.getSuperBuyFragment1(versionLevelId, knowledgeId, 3);
        }
    }

    private String musicUrl;

    @OnClick(R.id.voice)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voice:
                if (musicUrl == null) {
                    mPresenter.getVoicePath(versionLevelId, knowledgeId, 1, 1);
                } else {
                    if (!musicPlayer.isPlaying()) {
                        musicPlayer.play(musicUrl);
                        //voice.getController().getAnimatable().start();
                        voice.setBackgroundResource(R.mipmap.super_talking_voice_stop);
                    } else {
                        //voice.getController().getAnimatable().stop();
                        voice.setBackgroundResource(R.mipmap.super_talking_voice);
                        musicPlayer.pause();
                    }
                }
                break;
        }
    }

    private String versionLevelId, knowledgeId;

    public void initLoadData(String versionLevelId, String knowledgeId) {
        this.versionLevelId = versionLevelId;
        this.knowledgeId = knowledgeId;
        if (multiStateView != null) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            mPresenter.getSuperBuyFragment1(versionLevelId, knowledgeId, 3);
        }
    }

    private SuperSummaryEntity superSummaryEntity;

    @Override
    public void onGetSuperSelectSuccess(ResponseBody obj) {
        try {
            String result = obj.string();
//            superSummaryEntity = new Gson().fromJson(result, SuperSummaryEntity.class);
//            if (superSummaryEntity != null && superSummaryEntity.getAttrList() != null && superSummaryEntity.getAttrList().size() != 0) {
//                mathView.setWebText(superSummaryEntity.getAttrList().get(0).getContentHtml());
//                multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
//            } else {
//                voice.setVisibility(View.GONE);
//                multiStateView.setViewState(multiStateView.VIEW_STATE_EMPTY);
//            }


            SummaryEntity summaryEntity = new Gson().fromJson(result, SummaryEntity.class);
            if (summaryEntity != null) {
                mathView.setWebText(summaryEntity.getAttrContentHtml());
                multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            } else {
                voice.setVisibility(View.GONE);
                multiStateView.setViewState(multiStateView.VIEW_STATE_EMPTY);
            }

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
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onResultSuccess(String method, ResponseBody res) {
        if (method.equals("getVoicePath")) {
            try {
                musicUrl = res.string();
                musicPlayer.play(musicUrl);
                //voice.getController().getAnimatable().start();
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
