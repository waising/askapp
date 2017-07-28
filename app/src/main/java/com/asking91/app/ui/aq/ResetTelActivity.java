package com.asking91.app.ui.aq;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;

import com.asking91.app.R;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.util.JLog;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class ResetTelActivity extends BaseFrameActivity<AqPresenter, AqModel> implements AqContract.View {

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.old_tel)
    EditText oldTelEt;

    @BindView(R.id.new_tel)
    EditText newTelEt;

    @BindView(R.id.new_again_pass)
    EditText newAgainTelEt;

    @BindView(R.id.get_code)
    EditText codeEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_tel);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, getString(R.string.reset_tel));
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onSuccess(ResponseBody body) {
        try {
            JLog.i("11111111111",body.string());
        } catch (IOException e) {


        }
        showShortToast("");
    }

    @OnClick(R.id.reset_tel_ok)
    public void onClick(){
        changeTel(oldTelEt.getText().toString(),newTelEt.getText().toString(),codeEt.getText().toString());
    }

    public void changeTel(String oldTel, String newTel, String code) {

        if(vetifyOriginalTel(oldTel) && vetifyNewTel(newTel)){

        }
        //mPresenter.chagePassword(originalPassword,newPassWord,confirmPassword);
    }


    private boolean vetifyOriginalTel(String oldTel) {
        if (TextUtils.isEmpty(oldTel)) {
            showShortToast("请输入手机号码");
            return true;
        } else if (oldTel.length() != 11) {
            showShortToast("请输入正确的手机号码");
            return true;
        }
        return false;
    }

    private boolean vetifyNewTel(String newTelWord) {
        if (TextUtils.isEmpty(newTelWord)) {
            showShortToast("输入新手机号码");
            return true;
        } else if (newTelWord.length() != 11) {
            showShortToast("新手机号码输入不正确");
            return true;
        }
        return false;
    }
}
