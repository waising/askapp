package com.asking91.app.ui.register;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asking91.app.R;
import com.asking91.app.common.BaseRsqEntity;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CommonUtil;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by wxiao on 2016/10/25.
 */

public class RegisterActivity extends BaseFrameActivity<RegisterPresenter, RegisterModel> implements RegisterContract.View {

    @BindView(R.id.line)
    View line;
    @BindView(R.id.register_account)
    EditText registerAccount;
    @BindView(R.id.register_pass)
    EditText registerPass;
    @BindView(R.id.register_pass_again)
    EditText registerPassAgain;
    @BindView(R.id.register_invite_code)
    EditText registerInviteCode;
    @BindView(R.id.register_yzm)
    EditText registerYzm;
    @BindView(R.id.register_yzm_btn)
    Button registerYzmBtn;
    @BindView(R.id.register_chbx)
    CheckBox registerChbx;
    @BindView(R.id.lay_register_chbx)
    LinearLayout layRegisterChbx;
    @BindView(R.id.register_chbx_text)
    TextView registerChbxText;
    @BindView(R.id.register_ok)
    Button registerOk;
    private MaterialDialog mLoadingDialog;

    @BindView(R.id.register_rel)
    RelativeLayout regRel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }

    @Override
    public void initView() {
        super.initView();
        mLoadingDialog = getLoadingDialog().build();
        //registerReceiver(receiver, new IntentFilter("com.asking91.app.ui.register.RegisterActivity.ok"));
    }

    @Override
    protected void onDestroy() {
        //unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onRequestStart() {
        mLoadingDialog.show();
    }

    @Override
    public void onRequestEnd() {
        mLoadingDialog.dismiss();
    }


    @Override
    public void onRequestError(RequestEntity requestEntity) {
        mLoadingDialog.dismiss();
    }


    @Override
    public void onYZMSuccess(Object obj) {
        mLoadingDialog.dismiss();
        Gson gson= new Gson();
        BaseRsqEntity baseRsqEntity = gson.fromJson(obj.toString(), BaseRsqEntity.class);
        if(baseRsqEntity.getFlag()==0){
            timeSend();
        }else{
            if(obj.toString().indexOf("已注册")!=-1){
                showShortToast("手机号已注册");
            }else{
                showShortToast("注册失败");
            }
//            showShortToast(obj.toString());
        }

    }
    private Handler handler;
    private int time = 60;
    /**
     * 已发送了验证码
     */
    private void timeSend(){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                registerYzmBtn.setText(time +"秒后重试");
                time--;
                if(handler!=null&&time!=-1){
                    handler.postDelayed(this, 1000);
                }else{
                    time = 60;
                    registerYzmBtn.setText("点击发送验证码");
                }
            }
        }, 1000);
    }

    @Override
    public void onRegisterSuccess(Object obj) {
        mLoadingDialog.dismiss();
        Map map = CommonUtil.parseDataToMap(obj);
        showShortToast(map.get("msg").toString());
        if("0".equals(map.get("flag"))){
            // 注册用户埋点
            mManService.getMANAnalytics().userRegister(registerAccount.getText().toString());
            EventBus.getDefault().post(new AppEventType(AppEventType.REGISTER_SUCCESS));
            finish();
        }
    }

    @OnClick({R.id.register_yzm_btn, R.id.register_ok, R.id.lay_register_chbx, R.id.register_chbx_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_yzm_btn:
                if(registerAccount.getText().toString().isEmpty()){
                    showShortToast("手机号不能为空");
                    return;
                }
                if(registerAccount.getText().toString().length()!=11){
                    CommonUtil.Toast(mthis, "请输入正确手机号");
                    return;
                }
                getLoadingDialog().content("验证码发送中...");
                mPresenter.getYZM(mthis, registerAccount.getText().toString());
                mLoadingDialog.show();
                break;
            case R.id.lay_register_chbx://直接点击已同意
                registerChbx.setChecked(!registerChbx.isChecked());
                break;
            case R.id.register_chbx_text:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.Key.WEB_URL, Constants.PROTOCOL);
                bundle.putString(Constants.Key.WEB_TITLE, "服务条款");
                openActivity(WebViewProtocolActivity.class, bundle);
                break;
            case R.id.register_ok:
                if(registerAccount.getText().toString().isEmpty()){
                    CommonUtil.Toast(mthis, "手机号不能为空");
                    return;
                }
                if(registerAccount.getText().toString().length()!=11){
                    CommonUtil.Toast(mthis, "请输入正确手机号");
                    return;
                }
                if(registerPass.getText().toString().isEmpty()){
                    CommonUtil.Toast(mthis, "密码不能为空");
                    return;
                }
                if(registerPassAgain.getText().toString().isEmpty()){
                    CommonUtil.Toast(mthis, "请再次确认密码");
                    return;
                }
                if(!registerPass.getText().toString().equals(registerPassAgain.getText().toString())){
                    CommonUtil.Toast(mthis, "两次输入密码不一样");
                    registerPassAgain.setText("");
                    return;
                }
                if(registerYzm.getText().toString().isEmpty()){
                    CommonUtil.Toast(mthis, "请输入手机验证码");
                    return;
                }
                if(!registerChbx.isChecked()){
                    CommonUtil.Toast(mthis, "请先阅读并同意条款内容");
                    return;
                }
                getLoadingDialog().content("注册中...");
                mPresenter.register(mthis, registerAccount.getText().toString(), registerPass.getText().toString(),
                        registerYzm.getText().toString(),registerInviteCode.getText().toString());
                break;
        }
    }

    /**
     * 接收服务条款的“我同意”操作
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getBooleanExtra("tag", false)){
                registerChbx.setChecked(true);
            }
        }
    };

    @Override
    public void setStatusBar(){
        StatusBarUtil.setTranslucentForImageView(this,0,regRel);
    }
}
