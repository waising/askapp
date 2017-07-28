package com.asking91.app.ui.resetpass;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asking91.app.R;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.RxCountDown;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by wxiao on 2016/10/26.
 * 重置密码
 */

public class ResetPassActivity extends BaseFrameActivity<ResetPassPresenter, ResetPassModel> implements ResetPassContract.View {

    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.user_info_rl)
    RelativeLayout userInfoRl;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.register_account)
    EditText registerAccount;
    @BindView(R.id.tmp_icon1)
    ImageView tmpIcon1;
    @BindView(R.id.register_yzm)
    EditText registerYzm;
    @BindView(R.id.register_yzm_btn)
    Button registerYzmBtn;
    @BindView(R.id.register_pass)
    EditText registerPass;
    @BindView(R.id.register_pass_again)
    EditText registerPassAgain;
    @BindView(R.id.register_ok)
    Button registerOk;
    private MaterialDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        mLoadingDialog = getLoadingDialog().build();
        toolBar.setTitle("重置密码");
    }

    @Override
    public void initLoad() {
        super.initLoad();

    }

    @Override
    public void initListener() {
        super.initListener();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onRequestStart() {
        mLoadingDialog.show();
    }

    @Override
    public void onRequestEnd() {
        mLoadingDialog.dismiss();
    }

    @OnClick({R.id.register_yzm_btn, R.id.register_ok})
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
                mLoadingDialog.setContent("发送验证码中...");
                mLoadingDialog.show();
                mPresenter.getYZMRestPass(mthis, registerAccount.getText().toString());
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
                mLoadingDialog.setContent("等待中...");
                mLoadingDialog.show();
                mPresenter.resetPass( registerAccount.getText().toString(), registerPass.getText().toString(), registerYzm.getText().toString());
                break;
        }
    }
    private int time = 60;
    @Override
    public void onYZMSuccess(Object obj) {
        mLoadingDialog.dismiss();
        RxCountDown.countdown(time)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        registerYzmBtn.setClickable(false);
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        time = 60;
                        registerYzmBtn.setText("获取验证码");
                        registerYzmBtn.setClickable(true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        registerYzmBtn.setText(integer +"秒后重试");
                    }
                });
    }

    @Override
    public void onResetPassSuccess(Object obj) {
        mLoadingDialog.dismiss();
        Map<String, Object> map = CommonUtil.parseDataToMap(obj);
        showShortToast(map.get("msg")+"");
        if(map.get("flag").equals("0")){
            finish();
        }
    }
}
