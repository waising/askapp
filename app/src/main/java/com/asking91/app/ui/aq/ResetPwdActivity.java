package com.asking91.app.ui.aq;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;

import com.asking91.app.R;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CommonUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;

public class ResetPwdActivity extends BaseFrameActivity<AqPresenter, AqModel> implements AqContract.View {

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.old_pass)
    EditText oldPassEt;
    @BindView(R.id.new_pass)
    EditText newPassEt;

    @BindView(R.id.new_again_pass)
    EditText newAgainPassEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, getString(R.string.reset_pwd));
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onSuccess(ResponseBody body) {
        Map map = CommonUtil.parseDataToMap(body);
        showShortToast(map.get("msg").toString());
        if ("0".equals(map.get("flag"))) {

            //tuichu
            getUserConstant().logout();
            EventBus.getDefault().post(new AppEventType(AppEventType.LOGIN_OUT));

            this.finish();
        }
    }

    @OnClick(R.id.reset_pwd_ok)
    public void onClick() {
        changePassword(oldPassEt.getText().toString(), newPassEt.getText().toString(), newAgainPassEt.getText().toString());
    }

    public void changePassword(String originalPassword, String newPassWord, String confirmPassword) {
        if (vetifyOriginalPassword(originalPassword) || vetifyNewPassWord(newPassWord) || vetifyConfirmPassword(confirmPassword)
                || vetifyPassword(originalPassword, newPassWord, confirmPassword)) {

            return;
        }

        mPresenter.chagePassword(originalPassword, newPassWord, confirmPassword);
    }

    /**
     * 验证原密码
     *
     * @param originalPassword
     * @return
     */
    private boolean vetifyOriginalPassword(String originalPassword) {
        if (TextUtils.isEmpty(originalPassword)) {
            showShortToast(getString(R.string.please_enter_old_pass));
            return true;
        } else if (originalPassword.length() < 6 || originalPassword.length() > 20) {
            showShortToast(getString(R.string.vertify_old_pass));
            return true;
        }
        return false;
    }

    /**
     * 验证新密码
     *
     * @param newPassWord
     * @return
     */
    private boolean vetifyNewPassWord(String newPassWord) {
        if (TextUtils.isEmpty(newPassWord)) {
            showShortToast(getString(R.string.please_enter_new_pass));
            return true;
        } else if (newPassWord.length() < 6 || newPassWord.length() > 20) {
            showShortToast(getString(R.string.vertify_new_pass));
            return true;
        }
        return false;
    }

    /**
     * 验证确认密码
     *
     * @param confirmPassword
     * @return
     */
    private boolean vetifyConfirmPassword(String confirmPassword) {
        if (TextUtils.isEmpty(confirmPassword)) {
            showShortToast(getString(R.string.please_enter_pass1));
            return true;
        } else if (confirmPassword.length() < 6 || confirmPassword.length() > 20) {
            showShortToast(getString(R.string.please_enter_pass1_err));
            return true;
        }
        return false;
    }


    /**
     * 旧密码新密码一样
     *
     * @param originalPassword
     * @param newPassWord
     * @param confirmPassword
     * @return true表示不通过
     */
    private boolean vetifyPassword(String originalPassword, String newPassWord, String confirmPassword) {
        if (!newPassWord.equals(confirmPassword)) {
            showShortToast(getString(R.string.password_no_equal));
            return true;
        } else if (TextUtils.equals(originalPassword, newPassWord)) {
            showShortToast(getString(R.string.please_old_new_same));
            return true;
        }
        return false;
    }
}
