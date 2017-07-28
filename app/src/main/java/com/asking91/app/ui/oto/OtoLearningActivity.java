package com.asking91.app.ui.oto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.global.OtoConstant;
import com.asking91.app.ui.widget.camera.ui.BaseFragmentActivity;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.camera.utils.ParamHelper;
import com.asking91.app.ui.widget.camera.utils.ScreenUtils;
import com.asking91.app.ui.widget.dialog.OtoWaitingLogoutDialog;
import com.asking91.app.ui.widget.doodle.ActionTypeEnum;
import com.asking91.app.ui.widget.doodle.DoodleView;
import com.asking91.app.ui.widget.doodle.SupportActionType;
import com.asking91.app.ui.widget.doodle.Transaction;
import com.asking91.app.ui.widget.doodle.TransactionCenter;
import com.asking91.app.ui.widget.doodle.action.MyPath;
import com.asking91.app.util.DateUtil;
import com.asking91.app.util.JLog;
import com.asking91.app.util.RxCountDown;
import com.asking91.app.util.extension.RTSAttachment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hanvon.HWCloudManager;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.rts.RTSCallback;
import com.netease.nimlib.sdk.rts.RTSManager;
import com.netease.nimlib.sdk.rts.constant.RTSEventType;
import com.netease.nimlib.sdk.rts.constant.RTSTunnelType;
import com.netease.nimlib.sdk.rts.model.RTSCommonEvent;
import com.netease.nimlib.sdk.rts.model.RTSControlEvent;
import com.netease.nimlib.sdk.rts.model.RTSData;
import com.netease.nimlib.sdk.rts.model.RTSOnlineAckEvent;
import com.netease.nimlib.sdk.rts.model.RTSOptions;
import com.netease.nimlib.sdk.rts.model.RTSTunData;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 正在答疑界面
 * Created by wwx on 2017/01/25.
 */

public class OtoLearningActivity extends BaseFragmentActivity<OtoAskPresent, OtoAskModel> implements OtoAskPresent.View {
    private final String TAG = OtoLearningActivity.class.getName();
    @BindView(R.id.iv_img)
    SimpleDraweeView iv_img;

    @BindView(R.id.again)
    TextView again;

    @BindView(R.id.tv_time)
    Chronometer tv_time;

    @BindView(R.id.end)
    TextView end;

    @BindView(R.id.scroll_view)
    ScrollView scroll_view;

    @BindView(R.id.doodle_view)
    DoodleView mDoodleView;

    private String account;      // 对方帐号
    private String sessionId;    // 会话的唯一标识
    private RTSData sessionInfo; // 本次会话的信息
    private boolean finishFlag = false; // 结束标记，避免多次回调onFinish
    /**
     * 汉王云名片识别
     */
    private HWCloudManager hwCloudManagerFormula;
    private MaterialDialog materialDialog;

    /**
     * 订单的ID
     */
    private String orderId;
    private String qiNiuUrl;
    /**
     * 为0时4.学生首单退款接口
     */
    int askTimes;

    private String teaName;
    private String teaAvatar;
    private String grade;

    private String teacherAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_oto_learning);
        ButterKnife.bind(this);

        HashMap<String, Object> mParams = ParamHelper.acceptParams(TeaWaitingActivity.class.getName());
        orderId = (String) mParams.get("orderId");
        askTimes = (int) mParams.get("askTimes");
        qiNiuUrl = (String) mParams.get("qiNiuUrl");
        teaName = (String) mParams.get("teaName");
        teaAvatar = (String) mParams.get("teaAvatar");
        teacherAccount = (String) mParams.get("teacherAccount");
        grade = (String) mParams.get("grade");

        sessionInfo = (RTSData) mParams.get("rtsData");
        account = sessionInfo.getAccount();
        sessionId = sessionInfo.getLocalSessionId();
    }

    @Override
    public void initData() {
        super.initData();
        hwCloudManagerFormula = new HWCloudManager(mthis, OtoConstant.HWY_KEY);
        try {
            String extraStr = sessionInfo.getExtra();
            BigDecimal scale = new BigDecimal(extraStr);
            BigDecimal screenWidth = new BigDecimal(ScreenUtils.getScreenWidth(this));
            int height = screenWidth.divide(scale, 2, BigDecimal.ROUND_HALF_UP).intValue();
            mDoodleView.getLayoutParams().height = height;
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerInComingObserver(true);
        registerCommonObserver(true);

        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, true);

        if (!TextUtils.isEmpty(qiNiuUrl)) {
            iv_img.setImageURI(qiNiuUrl);
        }

        mDoodleView.setOnScrollToListener(new DoodleView.OnScrollToListener() {
            @Override
            public void OnScrollTo(final Transaction t) {
                scroll_view.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll_view.scrollTo((int) (t.getX() * mDoodleView.getWidth()), (int) (t.getY() * mDoodleView.getHeight()));
                    }
                });
            }

            @Override
            public void OnStartTime() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 将计时器清零
                        tv_time.setBase(SystemClock.elapsedRealtime());
                    }
                });
            }
        });
    }

    /**
     * 监听回话
     *
     * @param register
     */
    private void registerCommonObserver(boolean register) {
        RTSManager.getInstance().observeChannelState(sessionId, channelStateObserver, register);
        RTSManager.getInstance().observeHangUpNotification(sessionId, endSessionObserver, register);
        RTSManager.getInstance().observeReceiveData(sessionId, receiveDataObserver, register);
        //RTSManager.getInstance().observeTimeoutNotification(sessionId, timeoutObserver, register);
        RTSManager.getInstance().observeControlNotification(sessionId, controlObserver, register);
    }

    /**
     * 监听控制消息
     */
    private com.netease.nimlib.sdk.Observer<RTSControlEvent> controlObserver = new com.netease.nimlib.sdk.Observer<RTSControlEvent>() {
        @Override
        public void onEvent(RTSControlEvent rtsControlEvent) {
            //Toast.makeText(mthis, rtsControlEvent.getCommandInfo(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, rtsControlEvent.getCommand() + rtsControlEvent.getCommandInfo());
        }
    };

    /**
     * 监听对方挂断
     */
    private com.netease.nimlib.sdk.Observer<RTSCommonEvent> endSessionObserver = new com.netease.nimlib.sdk.Observer<RTSCommonEvent>() {
        @Override
        public void onEvent(RTSCommonEvent rtsCommonEvent) {
            onFinish();
        }
    };

    /**
     * 监听当前会话的状态
     */
    private com.netease.nimlib.sdk.rts.RTSChannelStateObserver channelStateObserver = new com.netease.nimlib.sdk.rts.RTSChannelStateObserver() {

        @Override
        public void onConnectResult(String sessionId, RTSTunnelType tunType, long channelId, int code, String file) {
        }


        @Override
        public void onChannelEstablished(String sessionId, RTSTunnelType tunType) {
            if (tunType == RTSTunnelType.AUDIO) {
                RTSManager.getInstance().setSpeaker(sessionId, true); // 默认开启扬声器
            }
        }

        @Override
        public void onUserJoin(String sessionId, RTSTunnelType tunType, String account) {

        }

        @Override
        public void onUserLeave(String sessionId, RTSTunnelType tunType, String account, int event) {

        }

        @Override
        public void onDisconnectServer(String sessionId, RTSTunnelType tunType) {
            Toast.makeText(mthis, "onDisconnectServer, tunType=" + tunType.toString(), Toast
                    .LENGTH_SHORT).show();
            if (tunType == RTSTunnelType.DATA) {
                // 如果数据通道断了，那么关闭会话
                Toast.makeText(mthis, "TCP通道断开，自动结束会话", Toast.LENGTH_SHORT).show();
                endSession();
            } else if (tunType == RTSTunnelType.AUDIO) {
                // 如果音频通道断了，那么UI变换
                Toast.makeText(mthis, "音频通道断开，可能无法时时通话", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(String sessionId, RTSTunnelType tunType, int code) {
            Toast.makeText(mthis, "onError, tunType=" + tunType.toString() + ", error=" + code,
                    Toast.LENGTH_LONG).show();
            endSession();
        }

        @Override
        public void onNetworkStatusChange(String sessionId, RTSTunnelType tunType, int value) {
            // 网络信号强弱
        }
    };

    /**
     * 监听回话
     */
    private void acceptSession() {
        RTSOptions options = new RTSOptions().setRecordAudioTun(true).setRecordDataTun(true);
        RTSManager.getInstance().accept(sessionId, options, new RTSCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {//连接成功
                // 判断开启通道是否成功
                if (success) {
                    //acceptView();
                } else {
                    Toast.makeText(mthis, "通道开启失败!请查看LOG", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int code) {//连接失败
                if (code == -1) {
                    Toast.makeText(mthis, "接受会话失败,音频通道同时只能有一个会话开启", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mthis, "接受会话失败, code=" + code, Toast.LENGTH_SHORT).show();
                }
                onFinish();
            }

            @Override
            public void onException(Throwable exception) {//会话中断
                Toast.makeText(mthis, "接受会话异常, e=" + exception.toString(), Toast.LENGTH_SHORT).show();
                onFinish();
            }
        });
    }

    /**
     * 监听收到对方发送的通道数据
     */
    private com.netease.nimlib.sdk.Observer<RTSTunData> receiveDataObserver = new com.netease.nimlib.sdk.Observer<RTSTunData>() {
        @Override
        public void onEvent(RTSTunData rtsTunData) {
            String data = "[parse bytes error]";
            try {
                data = new String(rtsTunData.getData(), 0, rtsTunData.getLength(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Log.d("2222222222",data);
            TransactionCenter.getInstance().onReceive(sessionId, data);
        }
    };


    /**
     * 跳转到答疑结束页面
     */
    private void openOtoEndActivity() {
        HashMap<String, Object> mParams = ParamHelper.acquireParamsReceiver(OtoLearningActivity.class.getName());
        mParams.put("orderId", orderId);
        mParams.put("teaName", teaName);
        mParams.put("teaAvatar", teaAvatar);
        mParams.put("askTimes", askTimes);
        mParams.put("teacherAccount", teacherAccount);
        openActivity(OtoEndActivity.class);
        materialDialog.dismiss();
        finish();
    }

    /**
     * 结束
     */
    private void onFinish() {
        if (!finishFlag) {
            finishFlag = true;
            RTSAttachment attachment = new RTSAttachment((byte) 1);
            IMMessage msg = MessageBuilder.createCustomMessage(account, SessionTypeEnum.P2P, attachment.getContent(), attachment);
            msg.setStatus(MsgStatusEnum.success);
            NIMClient.getService(MsgService.class).saveMessageToLocal(msg, true);

            openOtoEndActivity();
        }

    }

    @Override
    public void initView() {
        super.initView();
        sendBroadcast(new Intent("com.asking91.app.ui.oto.OtoWaitingActivity"));//关闭等待界面
        materialDialog = getLoadingDialog().build();
        materialDialog.setContent("提交中...");
        again.setVisibility(View.GONE);
    }

    private OtoWaitingLogoutDialog otoWaitingLogoutDialog;

    @OnClick({R.id.end, R.id.again})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.end:
                showEndDialog();
                break;
            case R.id.again://再来一题

                break;
        }
    }

    /**
     * 结束答疑
     */
    private void endSession() {
        RTSManager.getInstance().close(sessionId, new RTSCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailed(int code) {
                //Toast.makeText(mthis, "挂断请求错误，code：" + code, Toast.LENGTH_SHORT).show();
                JLog.i(TAG, "挂断请求错误，code：" + code);
            }

            @Override
            public void onException(Throwable exception) {

            }
        });

        AVChatManager.getInstance().hangUp2(0, new AVChatCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailed(int code) {

            }

            @Override
            public void onException(Throwable exception) {

            }
        });
        onFinish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            picTakePath = data.getData().getPath();
            materialDialog.show();
            mPresenter.qiniutoken();
        }
    }

    @Override
    public void initLoad() {
        super.initLoad();
        countTime();//开始计时
        acceptSession();
        initDoodleView();
        openAudio();

    }

    /**
     * 开始计时，
     */
    private void countTime() {
        // 将计时器清零
        tv_time.setBase(SystemClock.elapsedRealtime());
        //开始计时
        tv_time.start();

        int gradeId = Integer.valueOf(grade);//科目id
        BigDecimal askcoin;
        if (gradeId < 10) {
            //初中科目 6阿思可币/60秒
            askcoin = new BigDecimal(6);
        } else {
            //高中科目 8阿思可币/60秒
            askcoin = new BigDecimal(8);
        }
        BigDecimal integral = new BigDecimal(getUserConstant().getUserEntity().getIntegral());//阿思币数
        BigDecimal totalTime = new BigDecimal(0);//总时间
        if (integral.intValue() > 0) {//阿思币能抵用的时间，如果阿思币数大于0,总时间=((阿思币数*60）/阿思可币)。
            totalTime = integral.multiply(new BigDecimal(60)).divide(askcoin, 2, BigDecimal.ROUND_HALF_UP);
        }
        if (askTimes == 0) {//首单
            //首单免费体验时长20分钟，加上20分钟时间
            totalTime = totalTime.add(new BigDecimal(1200));
        }
        int takeTime = totalTime.intValue();
        //倒计时60秒显示等待提示
        RxCountDown.countDown(takeTime, new RxCountDown.CountDownListener() {
            @Override
            public void onComplete() {
                RxCountDown.countDown(180, new RxCountDown.CountDownListener() {//倒计时3分钟结束答疑
                    @Override
                    public void onComplete() {
                        endSession();
                    }
                });
                showLongToast("阿思可币不足，3分钟将终止答疑");
            }
        });
    }

    private String picTakePath, picName, picMsg, picHwyResult;

    @Override
    public void onSuccess(String methodName, ResponseBody baseRsqEntity) {
        if (methodName.equals("otoGetToken")) {
            try {
                String result = baseRsqEntity.string();
                JSONObject jsonRes = JSON.parseObject(result);
                if (TextUtils.equals("0", jsonRes.getString("flag"))) {
                    picMsg = jsonRes.getJSONObject("content").getString("token");
                    picName = DateUtil.currentDateMilltime().replace(":", "-").replace(" ", "_") + "oto_ask.jpg";
                    //使用灰度处理照片传递给汉王云解析
                    String cropPath = BitmapUtil.getGreyPath() + new File(picTakePath).getName().replace(".jpg", "_gray.jpg");
                    mPresenter.getHwyQuestion(hwCloudManagerFormula, cropPath, null);
                } else {
                    materialDialog.dismiss();
                    showShortToast(jsonRes.getString("msg"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSuccess(String methodName, String string) {
        if (methodName.equals("upload-onNext")) {
            JLog.i(mthis.getClass().getSimpleName(), "七牛图片地址：" + OtoConstant.QiNiuHead + picName);
            String tel = getUserConstant().getUserName();
            mPresenter.otoImg(tel, "askAgain", picHwyResult, 0, OtoConstant.QiNiuHead + picName, null, 0, 0, null, 0, 0);//再提交一题
        } else if (methodName.equals("getHwyQuestion-next")) {
            JLog.i("getHwyQuestion-next-onSuccess", "string=" + string);
//            picHwyResult = string.replaceAll("[^(\\u4e00-\\u9fa5)[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]]","");
            picHwyResult = string.replaceAll("^(\\\\u4e00-\\\\u9fa5)", "");
            JLog.i("getHwyQuestion-next-onSuccess", "string=" + picHwyResult);
            if (picHwyResult.isEmpty()) {
                showShortToast("未找到匹配题目");
                materialDialog.dismiss();
                return;
            }
            mPresenter.upload(picTakePath, picName, picMsg);
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onBackPressed() {
        showEndDialog();
    }

    /**
     * 结束答疑弹窗
     */
    private void showEndDialog() {
        if (otoWaitingLogoutDialog == null) {
            otoWaitingLogoutDialog = new OtoWaitingLogoutDialog(mthis, "是否要结束这次辅导？", null, View.GONE, "继续学习", "确定结束");
            otoWaitingLogoutDialog.setLogoutListener(new OtoWaitingLogoutDialog.LououtListener() {
                @Override
                public void click() {
                    endSession();
                }
            });
        }
        otoWaitingLogoutDialog.show();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        // 这里需要重绘
        mDoodleView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        JLog.logd(TAG, "onDestroy");
        if (mDoodleView != null) {
            mDoodleView.end();
        }
        super.onDestroy();
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, false);
        registerInComingObserver(false);
        registerCommonObserver(false);
    }

    com.netease.nimlib.sdk.Observer<StatusCode> userStatusObserver = new com.netease.nimlib.sdk.Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {
                finish();
            }
        }
    };

    private void registerInComingObserver(boolean register) {
        RTSManager.getInstance().observeOnlineAckNotification(sessionId, onlineAckObserver, register);
    }

    /**
     * 被叫方监听在线其他端的接听响应
     */
    private com.netease.nimlib.sdk.Observer<RTSOnlineAckEvent> onlineAckObserver = new com.netease.nimlib.sdk.Observer<RTSOnlineAckEvent>() {
        @Override
        public void onEvent(RTSOnlineAckEvent rtsOnlineAckEvent) {
            if (rtsOnlineAckEvent.getClientType() != ClientType.Android) {
                String client = null;
                switch (rtsOnlineAckEvent.getClientType()) {
                    case ClientType.Web:
                        client = "Web";
                        break;
                    case ClientType.Windows:
                        client = "Windows";
                        break;
                    default:
                        break;
                }
                if (client != null) {
                    String option = rtsOnlineAckEvent.getEvent() == RTSEventType.CALLEE_ONLINE_CLIENT_ACK_AGREE ?
                            "接受" : "拒绝";
                    Toast.makeText(mthis, "白板演示已在" + client + "端被" + option, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mthis, "白板演示已在其他端处理", Toast.LENGTH_SHORT).show();
                }
                onFinish();
            }
        }
    };


    //开启语音
    private void openAudio() {
        //true ：静音
        RTSManager.getInstance().setMute(sessionId, false);
    }


    /**
     * ***************************** 画板 ***********************************
     */
    private void initDoodleView() {
        // add support ActionType
        SupportActionType.getInstance().addSupportActionType(ActionTypeEnum.Path.getValue(), MyPath.class);

        mDoodleView.init(sessionId, account, DoodleView.Mode.BOTH, Color.WHITE, this);

        mDoodleView.setPaintSize(10);
        mDoodleView.setPaintType(ActionTypeEnum.Path.getValue());

        //guanbi 硬件加速
        //mDoodleView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // adjust paint offset
    }

    /**
     * 撤销一步
     */
    private void doodleBack() {
        mDoodleView.paintBack();
    }

    /**
     * 清屏
     */
    private void clear() {
        mDoodleView.clear();
    }
}
