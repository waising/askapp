package com.asking91.app.ui.supertutorial.selected;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.util.MusicPlayer;

import butterknife.OnClick;

/**
 * Created by jswang on 2017/3/2.
 */

public class AskDoctorTackFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> {
    String pid;

    AskMathView mathView;
    MultiStateView multiStateView;
    TextView tip1;
    AskSimpleDraweeView voice;
    RelativeLayout topTip;

    private boolean isChange = true;

    public static AskDoctorTackFragment newInstance() {
        AskDoctorTackFragment fragment = new AskDoctorTackFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_supertutorial_superclass_talking, null);
        mathView = (AskMathView)rootView.findViewById(R.id.mathView);
        multiStateView = (MultiStateView)rootView.findViewById(R.id.multiStateView);
        tip1 = (TextView)rootView.findViewById(R.id.tip1);
        voice = (AskSimpleDraweeView)rootView.findViewById(R.id.voice);
        topTip = (RelativeLayout)rootView.findViewById(R.id.top_tip);

        musicPlayer = MusicPlayer.getPlayer().bindVoice(getActivity(),voice);
        //voice.setImageUrl(Constants.GifHeard+"voice.gif");
        voice.setBackgroundResource(R.mipmap.super_talking_voice);
        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);

        if(!TextUtils.isEmpty(pid)){
            initLoadData(pid);
        }

        return rootView;
    }

    public void initLoadData(String pid) {
        this.pid = pid;
        isChange = true;
        if(musicPlayer!=null&&musicPlayer.isPlaying()){
            musicPlayer.pause();
        }
        if(multiStateView !=null){
            multiStateView.setViewState(multiStateView.VIEW_STATE_LOADING);
        }
        mPresenter.firstreviewkaoqfx(getActivity(),pid,new ApiRequestListener<String>(){
            @Override
            public void onResultSuccess(String res) {
                if (mathView != null) {
                    mathView.formatMath().showWebImage(multiStateView);
                    if(!TextUtils.isEmpty(res)) {
                        mathView.setWebText(res);
                        multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
                    }else{
                        multiStateView.setViewState(multiStateView.VIEW_STATE_EMPTY);
                    }

                }
            }
        });
    }

    private MusicPlayer musicPlayer;
    private String musicUrl;
    @OnClick(R.id.voice)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.voice:
                if(isChange){//有重新选择章节
                    if(!musicPlayer.isPlaying()){
                       // mPresenter.getVoicePath(versionLevelId, knowledgeId, 1, 0);
                    }else{
                        musicPlayer.pause();
                        voice.setBackgroundResource(R.mipmap.super_talking_voice);
                    }
                }else{
                    if(!musicPlayer.isPlaying()){
                        musicPlayer.play(musicUrl);
                        voice.setBackgroundResource(R.mipmap.super_talking_voice_stop);
                    }else{
                        musicPlayer.pause();
                        voice.setBackgroundResource(R.mipmap.super_talking_voice);
                    }
                }
                break;
        }
    }

    public void onRequestStart() {

    }

    public void onRequestError(RequestEntity requestEntity) {

    }

    public void onRequestEnd() {

    }

    @Override
    public void onDestroyView() {
        if(musicPlayer.isPlaying()){//释放音频资源
            musicPlayer.pause();
        }
        super.onDestroyView();
    }
}
