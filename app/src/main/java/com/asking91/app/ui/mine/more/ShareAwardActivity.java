package com.asking91.app.ui.mine.more;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asking91.app.R;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.mine.MineContract;
import com.asking91.app.ui.mine.MineModel;
import com.asking91.app.ui.mine.MinePresenter;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.camera.utils.APPUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import okhttp3.ResponseBody;

/**
 * Created by jswang on 2017/3/23.
 */

public class ShareAwardActivity extends BaseFrameActivity<MinePresenter, MineModel> implements MineContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.et_invicode)
    TextView et_invicode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_share_award);
        ButterKnife.bind(this);
        // 初始化ShareSDK
        ShareSDK.initSDK(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.recommend_reward));

        mPresenter.invite(this, new ApiRequestListener<ArrayMap<String, String>>() {
            @Override
            public void onResultSuccess(ArrayMap<String, String> resMap) {
                if (TextUtils.equals(resMap.get("flag"), "0")) {
                    et_invicode.setText(resMap.get("inviteCode"));
                }
            }
        });
    }

    @OnClick({R.id.btn_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_share:
                APPUtil.showShare(this, "邀请码:" + et_invicode.getText().toString(), String.format("同学们,我在使用阿思可在线复习重点,快快下载体验吧～" +
                                ",下载链接：%s ", Constants.APK_TENCENT_DOWN_URL), Constants.APK_TENCENT_DOWN_URL
                        , new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                if (platform.getName() == QQ.NAME) {
                                    Toast.makeText(ShareAwardActivity.this, platform.getName() + "分享成功", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {

                            }

                            @Override
                            public void onCancel(Platform platform, int i) {

                            }
                        });
                break;
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onError(int type) {

    }

    @Override
    public void onRefreshData(ResponseBody body) {

    }

    @Override
    public void onLoadMoreData(ResponseBody body) {

    }

    @Override
    public void onSuccess(int type, ResponseBody body) {
    }

}
