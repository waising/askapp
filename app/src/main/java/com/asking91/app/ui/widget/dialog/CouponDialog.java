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
import com.asking91.app.entity.coupon.CouponRegister;
import com.asking91.app.global.PayConstant;
import com.asking91.app.util.DateUtil;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 优惠券的弹窗
 * create by linbin
 */

public class CouponDialog extends DialogFragment {


    Unbinder unbinder;

    @BindView(R.id.tv_available_price)
    TextView tvAvailablePrice;
    @BindView(R.id.tv_price_num)
    TextView tvPriceNum;
    @BindView(R.id.tv_available_type)
    TextView tvAvailableType;
    @BindView(R.id.tv_expiry_date)
    TextView tvExpiryDate;
    @BindView(R.id.tv_expiry_day)
    TextView tvExpiryDay;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题栏
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// 设置背景透明
        View view = inflater.inflate(R.layout.dialog_coupon, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }


    public static CouponDialog newInstance(CouponRegister couponRegister) {
        CouponDialog fragment = new CouponDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("courseRegister", couponRegister);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * @param
     */
    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            CouponRegister couponRegister = (CouponRegister) bundle.getSerializable("courseRegister");
            if (couponRegister != null) {
                CouponRegister.EventBean eventBean = couponRegister.getEvent();
                if (eventBean != null) {
                    CouponRegister.EventBean.RuleBean ruleBean = eventBean.getRule();
                    if (ruleBean != null) {
                        tvPriceNum.setText(PayConstant.formatPrice(ruleBean.getOff() + ""));
                        tvAvailablePrice.setText(PayConstant.formatPrice(ruleBean.getPurchase() + ""));
                        tvAvailableType.setText(eventBean.getRemake());
                        tvExpiryDate.setText(getString(R.string.closing_date_format, DateUtil.getTime(new Date(ruleBean.getClosingDate()))));
                        int betweendays = 0;
                        try {
                            betweendays = DateUtil.daysBetween(System.currentTimeMillis(), ruleBean.getClosingDate());
                            tvExpiryDay.setText(getString(R.string.active_time_format, betweendays + 1 + ""));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.close)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close://取消
                dismiss();
                break;
        }
    }


}
