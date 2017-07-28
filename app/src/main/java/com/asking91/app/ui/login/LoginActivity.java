package com.asking91.app.ui.login;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asking91.app.Asking91;
import com.asking91.app.BuildConfig;
import com.asking91.app.R;
import com.asking91.app.api.Networks;
import com.asking91.app.entity.IntentCarrier;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.user.NimTokenEntity;
import com.asking91.app.entity.user.UserEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.mvpframe.rx.RxBus;
import com.asking91.app.ui.main.MainActivity;
import com.asking91.app.ui.register.RegisterActivity;
import com.asking91.app.ui.resetpass.ResetPassActivity;
import com.asking91.app.ui.widget.camera.comm.AppManager;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.DeviceUtil;
import com.asking91.app.util.JLog;
import com.asking91.app.util.SharePreferencesHelper;
import com.jaeger.library.StatusBarUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import okhttp3.ResponseBody;

/**
 * 登录页
 */
public class LoginActivity extends BaseFrameActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    @BindView(R.id.icon1)
    ImageView icon1;
    @BindView(R.id.icon2)
    ImageView icon2;

    //先逛一逛
    @BindView(R.id.goMain)
    TextView goMain;
    @BindView(R.id.login_register)
    TextView loginRegister;
    private int isAnimation = 0;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.login_account)
    EditText loginAccount;
    @BindView(R.id.account_img)
    ImageView accountImg;
    @BindView(R.id.inputLayout1)
    RelativeLayout inputLayout1;
    @BindView(R.id.login_pass)
    EditText loginPass;
    @BindView(R.id.pass_img)
    ImageView passImg;
    @BindView(R.id.tmpl1)
    RelativeLayout tmpl1;
    @BindView(R.id.login_remember)
    CheckBox loginRemember;
    @BindView(R.id.inputLayout2)
    RelativeLayout inputLayout2;
    @BindView(R.id.inputLayout)
    LinearLayout inputLayout;
    private MaterialDialog mLoadingDialog;

    @BindView(R.id.login_rel)
    RelativeLayout loginRel;

    IntentCarrier intentCarrier;
    boolean mTootheractivity = false;
    private int screenWidth;

    private boolean isLogin = false;

    private boolean isPwdLayout = false;
    /**
     * 是否注册后的登录
     */
    private boolean isRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTransparent = true;

        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        super.initData();
        mTootheractivity = getIntent().getBooleanExtra(Constants.activity.toOtherActivity, false);
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);
        //清除token
        getUserConstant().saveToken("");
        screenWidth = DeviceUtil.getScreenWidth(mthis);
    }

    @Override
    public void initView() {
        super.initView();
        getLoadingDialog().content("正在登录...");
        mLoadingDialog = getLoadingDialog().build();
//        //设置是否记住密码
        loginRemember.setChecked(SharePreferencesHelper.getInstance(Asking91.applicationContext).getBoolean(Constants.Login.IsRemember));
        if (loginRemember.isChecked()) {
            loginAccount.setText(SharePreferencesHelper.getInstance(Asking91.applicationContext).getString(Constants.Login.UserName, ""));
            loginPass.setText(SharePreferencesHelper.getInstance(Asking91.applicationContext).getString(Constants.Login.Password, ""));
        }
        //数字键盘
        //loginAccount.setInputType(EditorInfo.TYPE_CLASS_PHONE);

        if (BuildConfig.DEBUG) {
            loginAccount.setInputType(EditorInfo.TYPE_CLASS_TEXT);
            //gotoNextInputAnimation();
        }
//            passImg.setImageResource(R.mipmap.back2_select);
//        }


        loginAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (loginAccount.getText().toString().length() == 11) {
                    accountImg.setImageResource(R.drawable.back2_select);
                } else {
                    accountImg.setImageResource(R.drawable.back2);
                }
            }
        });
        loginPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (loginPass.getText().toString().length() == 11) {
                    passImg.setImageResource(R.drawable.back2_select);
                } else {
                    passImg.setImageResource(R.drawable.back2);
                }
            }
        });
    }

    private float x_down;

    @Override
    public void initListener() {
        super.initListener();
        goMain.setOnClickListener(new View.OnClickListener() {//先逛一逛
            @Override
            public void onClick(View v) {
                if (isPwdLayout) {//重置密码
                    openActivity(ResetPassActivity.class);
                } else {//首页
                    openActivity(MainActivity.class);
                    finish();
                }

            }
        });

        loginRel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x_down = event.getX();
                        if (x_down > 100 && x_down < screenWidth - 100) {//判断点击下去的区域

                        } else {
                            return false;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (x_down - event.getX() > 100) {//往前
                            if (isAnimation == 0) {
                                gotoNextInputAnimation();
                                return true;
                            } else {
                                return false;
                            }
                        } else if (event.getX() - x_down > 100) {//往后
                            if (isAnimation == 1) {
                                gotoPreInputAnimation();
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                }
                return true;
            }
        });
    }

    @Override
    public void onRequestStart() {
        if (!isLogin)
            mLoadingDialog.show();
    }

    @Override
    public void onRequestEnd() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        mLoadingDialog.dismiss();
    }

    @Override
    public void onLoginSuccess(final Object msg) {//登录成功
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingDialog.dismiss();
                JLog.logi("LoginActivity", "msg=" + msg);
                Map<String, Object> map = CommonUtil.parseDataToMap(msg);
                if (map.get("flag").equals("0")) {
                    Networks.setToken(map.get("ticket") + "");
                    getUserConstant().saveToken(String.valueOf(map.get("ticket")));
                    getUserConstant().setUserEntity(CommonUtil.data2Clazz(map.get("userInfo"), UserEntity.class));
                    //getUserConstant().saveUserData(new Gson().toJson(getUserConstant().getUserEnti)));
                   // getUserConstant().setIsLoginState(true);//保存登录状态
                    if (mTootheractivity)
                        handler.sendEmptyMessage(0);
                    else {
                        if (getUserConstant().getUserEntity() != null) {

                            // 用户登录埋点
                            mManService.getMANAnalytics().updateUserAccount(getUserConstant().getUserEntity().getNickName(), getUserConstant().getUserEntity().getUserName());

                            RxBus.$().post(getUserConstant().getUserEntity());
                        }

                        isLogin = true;
                        EventBus.getDefault().post(new AppEventType(AppEventType.LOGIN_SUCCESS, isRegister));

                        //清空网易云token
                        getUserConstant().saveWYToken("");
                        showShortToast(R.string.login_success);
                        LoginActivity.this.finish();

//                        //获取网易im登陆token   //放在一对一处登录
//                        String userName = getUserConstant().getUserName();
//                        mPresenter.getNIMLogin(LoginActivity.this, userName, new ApiRequestListener<String>() {
//                            @Override
//                            public void onResultSuccess(String resStr) {
//                                reNimTokenEntity(resStr);
//                            }
//                        });
                    }

                } else {
                    showShortToast(String.valueOf(map.get("msg")));
                }
            }
        }, 500);
    }

    private void reNimTokenEntity(String res) {
        //获取网易云im token
        final NimTokenEntity nimToken = CommonUtil.data2Clazz(res, NimTokenEntity.class);
        LoginInfo info = new LoginInfo(getUserConstant().getUserName().toLowerCase(), nimToken.getToken()); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        //登录成功
                        //缓存网易token
                        // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                        showShortToast(R.string.login_success);
                        getUserConstant().saveWYToken(nimToken.getToken());
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onFailed(int code) {
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onException(Throwable exception) {
                        LoginActivity.this.finish();
                    }
                };
        NIMClient.getService(AuthService.class).login(info).setCallback(callback);
    }

    @Override
    public void onSuccess(ResponseBody body) {
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:// 登录成功
                    intentCarrier = getIntent().getParcelableExtra(Constants.activity.interceptorInvoker);
                    CommonUtil.openActivity(LoginActivity.this, intentCarrier.getmTargetAction(), intentCarrier.getMbundle());
                    LoginActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    };


    @OnClick({R.id.account_img, R.id.pass_img, R.id.login_remember, R.id.login_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account_img:
                JLog.logd("LoginActivity", "R.id.account_img");
                if (loginAccount.getText().toString().isEmpty()) {
                    CommonUtil.Toast(this, getResources().getString(R.string.register_account_hint));
                    return;
                }
                gotoNextInputAnimation();
                break;
            case R.id.pass_img://密码输入页
                JLog.logd("LoginActivity", "R.id.pass_img");
                if (loginPass.getText().toString().isEmpty()) {
                    CommonUtil.Toast(this, getResources().getString(R.string.login_pass_hint));
                    return;
                }
                isLogin = false;
                SharePreferencesHelper.getInstance(this).putString(Constants.Login.UserName, loginAccount.getText().toString());
                SharePreferencesHelper.getInstance(this).putString(Constants.Login.Password, loginPass.getText().toString());
                mPresenter.ssoLogin(this, loginAccount.getText().toString(), loginPass.getText().toString());
                //
                break;
            case R.id.login_remember:
                //设置记住密码标志
                SharePreferencesHelper.getInstance(this).putBoolean(Constants.Login.IsRemember, loginRemember.isChecked());
                break;
            case R.id.login_register:
                //openActivity(RegisterActivity.class);
                Intent intent = new Intent();
                intent.setClass(this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                break;
        }
    }

    /**
     * 从账号进入到密码输入框
     */
    private void gotoNextInputAnimation() {
        ObjectAnimator.ofFloat(inputLayout1, "translationX", 0, CommonUtil.dip2px(this, -300)).setDuration(500).start();
        ObjectAnimator.ofFloat(inputLayout2, "translationX", 0, CommonUtil.dip2px(this, -300)).setDuration(500).start();
        isAnimation++;
        text.setText(getResources().getString(R.string.login_pass_hint));
        loginPass.requestFocus();
        goMain.setText(R.string.login_forget);
        isPwdLayout = true;
    }

    /**
     * 从密码回到账号的输入框
     */
    private void gotoPreInputAnimation() {
        ObjectAnimator.ofFloat(inputLayout1, "translationX", CommonUtil.dip2px(this, -300), 0).setDuration(500).start();
        ObjectAnimator.ofFloat(inputLayout2, "translationX", CommonUtil.dip2px(this, -300), 0).setDuration(500).start();
        isAnimation--;
        text.setText(getResources().getString(R.string.login_account_hint));
        loginAccount.requestFocus();
        goMain.setText(R.string.login_gomain);
        isPwdLayout = false;
    }

    @Override
    public void onBackPressed() {
        JLog.d("LoginActivity", "isAnimation=" + isAnimation);
        if (isAnimation == 1) {
            //返回之前的
            gotoPreInputAnimation();
        }
//        else if(isAnimation==2){
//            gotoPreInputAnimation1();
//        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, loginRel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
        AppManager.getAppManager().finishActivity(this);
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.REGISTER_SUCCESS://注册成功后的登录
                isRegister = true;
                break;
        }
    }


}
