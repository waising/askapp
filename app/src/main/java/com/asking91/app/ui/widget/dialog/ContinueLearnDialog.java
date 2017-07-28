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
import android.widget.TextView;

import com.asking91.app.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 继续下一题学习的弹窗
 * create by linbin
 */

public class ContinueLearnDialog extends DialogFragment {


    Unbinder unbinder;


    private ClickListner mListner;
    /**
     * 从哪里跳转过来
     */
    private int fromWhere;

    private int position;
    private String id;
    /**
     * 从高考精学跳转过来
     */
    public static int FROM_COLLEAGE = 0;
    /**
     * 从演练大冲关跳转过来
     */
    public static int FROM_DRILL = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题栏
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// 设置背景透明
        View view = inflater.inflate(R.layout.dialog_continue_learn, null);
        unbinder = ButterKnife.bind(this, view);
        initData(view);
        return view;
    }

    /**
     * @param view
     */
    private void initData(View view) {
        TextView tvOk;
        tvOk = (TextView) view.findViewById(R.id.btn_ok);
        if (fromWhere == FROM_COLLEAGE) {//从高考精学过来
            tvOk.setText("学习下一课时");
        } else {
            tvOk.setText("学习其他题类");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_ok, R.id.btn_cancle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok://确认
                if (mListner != null) {
                    mListner.ok();
                }
                break;
            case R.id.btn_cancle://取消
                dismiss();
                break;
        }
    }


    /**
     * 回调监听
     */
    public interface ClickListner {
        void ok();//确认


    }


    public void setPosition(int position) {
        this.position = position;
    }


    public void setId(String id) {
        this.id = id;
    }


    public void setFromWhere(int fromWhere) {
        this.fromWhere = fromWhere;
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
