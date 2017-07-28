package com.asking91.app.ui.widget.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.asking91.app.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 没有wifi的弹窗
 * create by linbin
 */

public class NoWifiWarningDialog extends DialogFragment {


    Unbinder unbinder;


    private ClickListner mListner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题栏
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// 设置背景透明
        View view = inflater.inflate(R.layout.dialog_no_wifi, null);
        unbinder = ButterKnife.bind(this, view);
        initData(view);
        return view;
    }

    /**
     * @param view
     */
    private void initData(View view) {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_ok, R.id.btn_cancle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok://继续播放
                if (mListner != null) {
                    dismiss();
                    mListner.ok();
                }
                break;
            case R.id.btn_cancle://取消
                if (mListner != null) {
                    dismiss();
                    mListner.dissmiss();
                }

                break;
        }
    }


    /**
     * 回调监听
     */
    public interface ClickListner {
        void ok();//确认

        void dissmiss();
    }


    /**
     * 设置监听
     *
     * @param clickListner
     */
    public void setClickListner(ClickListner clickListner) {
        mListner = clickListner;
    }


}
