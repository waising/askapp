package com.asking91.app.ui.oto;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.asking91.app.R;
import com.asking91.app.entity.user.NimTokenEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.AskSimpleDraweeView;
import com.asking91.app.ui.widget.camera.comm.AppManager;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.camera.utils.ParamHelper;
import com.asking91.app.ui.widget.dialog.OtoWaitingDialog;
import com.asking91.app.ui.widget.dialog.OtoWaitingLogoutDialog;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.FileUtils;
import com.asking91.app.util.RxCountDown;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.rts.RTSManager;
import com.netease.nimlib.sdk.rts.model.RTSData;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;

/**
 * Created by wxiao on 2016/11/14.
 * 等待接单
 */

public class TeaWaitingActivity extends BaseFrameActivity<OtoAskPresent, OtoAskModel> implements OtoAskPresent.View {
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.draweeView)
    AskSimpleDraweeView draweeView;
    @BindView(R.id.title_more_menu)
    TextView title_more_menu;

    @BindView(R.id.root_tea_waiting)
    View root_tea_waiting;

    @BindView(R.id.tv_img)
    ImageView tv_img;

    @BindView(R.id.tv_tea_name)
    TextView tv_tea_name;

    @BindView(R.id.tv_time)
    Chronometer tv_time;

    @BindView(R.id.ll_out_time)
    View ll_out_time;

    OtoWaitingLogoutDialog outDialog;
    OtoWaitingDialog waitingDialog;

    int askMoney;
    String subject;
    String grade;
    String picTakePath;
    String picName;
    String qiNiuUrl;
    String picHwyResult;

    String orderId;
    String teaAvatar;

    String teacherAccount;
    String teaName;
    String userName;
    String userAvatar;
    /**
     * 为0时4.学生首单退款接口
     */
    int askTimes;

    /**
     * 0-等待接单   1-正在审题
     */
    int orderState = 0;

    boolean isActivityDestory = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oto_tea_analyze_waiting);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
        askMoney = getIntent().getIntExtra("askMoney", 0);
        subject = getIntent().getStringExtra("subject");
        grade = getIntent().getStringExtra("grade");
        picTakePath = getIntent().getStringExtra("picTakePath");
        picName = getIntent().getStringExtra("picName");
        qiNiuUrl = getIntent().getStringExtra("qiNiuUrl");
        picHwyResult = getIntent().getStringExtra("picHwyResult");

        askTimes = getIntent().getIntExtra("askTimes", 0);
        userAvatar = getIntent().getStringExtra("userAvatar");
    }

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("等待接单");
        root_tea_waiting.setVisibility(View.GONE);
        //倒计时60秒显示等待提示
        RxCountDown.countDown(60, new RxCountDown.CountDownListener() {
            @Override
            public void onComplete() {
                if (waitingDialog == null) {
                    waitingDialog = new OtoWaitingDialog(mthis);
                } else {
                    waitingDialog.show();
                }
            }
        });

        ll_out_time.setVisibility(View.GONE);
        RxCountDown.countDown(60 * 3, new RxCountDown.CountDownListener() {
            @Override
            public void onComplete() {
                ll_out_time.setVisibility(View.VISIBLE);
            }
        });

        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                if (draweeView.getController().getAnimatable() != null) {
                    draweeView.getController().getAnimatable().start();
                }
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
            }
        };
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(Uri.parse(Constants.GifHeard + "animation.gif"))
                .build();
        draweeView.setController(controller);

        userName = getUserConstant().getUserName();
        orderbulid();
    }

    private void orderbulid() {
        mPresenter.orderbuild(TeaWaitingActivity.this, subject, grade, picHwyResult, qiNiuUrl, askMoney + "", userName, userName, userAvatar, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String resStr) {
                ArrayMap<String, String> resMap = JSON.parseObject(resStr, new TypeReference<ArrayMap<String, String>>() {
                });
                orderId = resMap.get("orderId");
                loginNIMClient();
                orderstate(orderId);
            }
        });
    }

    private void orderstate(final String orderId) {
        mPresenter.orderstate(this, orderId, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                JSONObject resObject = JSON.parseObject(res);
                Double state = resObject.getDouble("state");
                String teacher = resObject.getString("teacher");
                if (state < 0) {
                    isActivityDestory = true;
                    showShortToast("老师已取消答疑");
                    finish();
                } else if (state == 2) {
                    try {
                        if (root_tea_waiting.getVisibility() == View.GONE) {
                            tv_title.setText("正在审题");
                            // 将计时器清零
                            tv_time.setBase(SystemClock.elapsedRealtime());
                            //开始计时
                            tv_time.start();

                            if (!TextUtils.isEmpty(teacher)) {
                                JSONObject teaObject = JSON.parseObject(teacher);
                                teaAvatar = teaObject.getString("avatar");
                                teacherAccount=teaObject.getString("account");
                                if (!TextUtils.isEmpty(teaAvatar)) {
                                    BitmapUtil.displayUserImage(TeaWaitingActivity.this, teaAvatar, tv_img);
                                }
                                teaName = teaObject.getString("name");
                                tv_tea_name.setText(teaName);
                            }
                        }
                    } catch (Exception e) {
                    }

                    if (draweeView.getController().getAnimatable() != null) {
                        draweeView.getController().getAnimatable().stop();
                    }
                    orderState = 2;
                    root_tea_waiting.setVisibility(View.VISIBLE);
                    title_more_menu.setVisibility(View.GONE);
                }
                //倒计时10秒显示等待提示
                RxCountDown.countDown(3, new RxCountDown.CountDownListener() {
                    @Override
                    public void onComplete() {
                        if (!isActivityDestory) {
                            orderstate(orderId);
                        }
                    }
                });
            }
        });
    }

    private void loginNIMClient() {
        StatusCode status = NIMClient.getStatus();
        //如果是登陆状态
        if (status.getValue() == StatusCode.LOGINED.getValue() && !TextUtils.isEmpty(getUserConstant().getWYToken())) {
            //监听白板
            openLearningActivity();
        } else {
            //获取网易im登陆token
            String userName = getUserConstant().getUserName();
            mPresenter.getNIMLogin(userName, new ApiRequestListener<String>() {
                @Override
                public void onResultSuccess(String resStr) {
                    reNimTokenEntity(resStr);
                }
            });
        }
    }

    private void reNimTokenEntity(String res) {
        //获取网易云im token
        final NimTokenEntity nimToken = JSON.parseObject(res, NimTokenEntity.class);
        LoginInfo info = new LoginInfo(getUserConstant().getUserName().toLowerCase(), nimToken.getToken()); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        //登录成功
                        //缓存网易token
                        // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                        getUserConstant().saveWYToken(nimToken.getToken());
                        openLearningActivity();
                    }

                    @Override
                    public void onFailed(int code) {
                    }

                    @Override
                    public void onException(Throwable exception) {
                    }
                };
        NIMClient.getService(AuthService.class).login(info).setCallback(callback);
    }

    private Observer<RTSData> mObserver;

    public void openLearningActivity() {
        mObserver = new Observer<RTSData>() {
            @Override
            public void onEvent(RTSData rtsData) {
                if (!isActivityDestory) {
                    HashMap<String, Object> mParams = ParamHelper.acquireParamsReceiver(TeaWaitingActivity.class.getName());
                    mParams.put("rtsData", rtsData);
                    mParams.put("orderId", orderId);
                    mParams.put("teaName", teaName);
                    mParams.put("teaAvatar", teaAvatar);
                    mParams.put("teacherAccount", teacherAccount);
                    mParams.put("qiNiuUrl", qiNiuUrl);
                    mParams.put("askTimes", askTimes);
                    mParams.put("grade", grade);
                    openActivity(OtoLearningActivity.class);

                    AppManager.getAppManager().finishActivity(OtoAskActivity.class);
                    finish();

                    try {
                        FileUtils.deleteFile(new File(picTakePath));
                    } catch (Exception e) {
                    }
                }
            }
        };
        RTSManager.getInstance().observeIncomingSession(mObserver, true);
    }

    @OnClick({R.id.back, R.id.tv_tea_c_analyze, R.id.title_more_menu, R.id.tv_camera, R.id.tv_qustion})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tea_c_analyze:
            case R.id.back:
                backActivity(0);
                break;
            case R.id.title_more_menu:
                openActivity(OtoDetailActivity.class, getIntent().getExtras());
                break;
            case R.id.tv_camera:
                backActivity(1);
                break;
            case R.id.tv_qustion:
                backActivity(0);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backActivity(0);
    }

    private void backActivity(final int type) {
        if (outDialog == null) {
            outDialog = new OtoWaitingLogoutDialog(mthis);
            outDialog.setLogoutListener(new OtoWaitingLogoutDialog.LououtListener() {
                @Override
                public void click() {
                    try {
                        if (mObserver != null) {
                            RTSManager.getInstance().observeIncomingSession(mObserver, false);
                        }
                    } catch (Exception e) {
                    }
                    mPresenter.ordercancel(TeaWaitingActivity.this, orderId, new ApiRequestListener<String>() {
                        @Override
                        public void onResultSuccess(String resStr) {
                            backOP(type);
                        }

                        @Override
                        public void onResultFail() {
                            backOP(type);
                        }
                    });
                }
            });
        }
        outDialog.show();
    }

    private void backOP(final int type) {
        if (type == 1) {
            EventBus.getDefault().post(new AppEventType(AppEventType.RE_CAMERA_QUS_REQUEST));
            AppManager.getAppManager().finishActivity(OtoAskActivity.class);
        }
        isActivityDestory = true;
        finish();
    }

    @Override
    public void onSuccess(String methodName, ResponseBody body) {
    }

    @Override
    public void onSuccess(String methodName, String string) {
    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestEnd() {
    }

    @Override
    protected void onDestroy() {
        isActivityDestory = true;
        super.onDestroy();
    }
}
