package com.asking91.app.ui.widget.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.asking91.app.R;
import com.asking91.app.entity.juniorTohigh.GoodsCoupon;
import com.asking91.app.ui.adapter.coupon.CounponTypeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Comvee108 on 2016/6/21
 * <p/>
 * 优惠类型的popupWindow
 */
public class CouponTypePopupWindow extends PopupWindow implements CounponTypeAdapter.MyItemClickListener {

    private OnClickListner mOnClickListner;
    Unbinder unbinder;

    @BindView(R.id.recyclerView)//recyclerView
            RecyclerView recycler;

    private Context mContext;


    @BindView(R.id.ll_main)//
            LinearLayout llMain;


    private CounponTypeAdapter counponTypeAdapter;

    public CouponTypePopupWindow(Context context, ArrayList<String> list, int position, ArrayList<GoodsCoupon> couponList) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_coupon_type, null),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        mContext = context;
        setAnimationStyle(R.style.mypopwindow_anim_style);//设置进出动画
//        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        setBackgroundDrawable(dw);
        mContext = context;
        //关闭事件
        setOnDismissListener(new popupDismissListener());
        //     setOutsideTouchable(true);// 设置允许在外点击消失
        initView(getContentView(), list, position,couponList);
    }

    @Override
    public void onItemClick(View view, int postion, ArrayList<GoodsCoupon> datas) {

        if (counponTypeAdapter.mPosition != postion) {//避免重复点击
            counponTypeAdapter.mPosition = postion;
            counponTypeAdapter.notifyDataSetChanged();
            if (mOnClickListner != null) {
                String item = counponTypeAdapter.getItem(postion);

                String id=counponTypeAdapter.getDataModel(postion).getId();
                if (!TextUtils.isEmpty(item)) {
                    dismiss();
                    mOnClickListner.click(item, postion,id);
                }

            }

        }



    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            unbinder.unbind();
            if (mOnClickListner != null) {
                mOnClickListner.dismiss();
            }
        }

    }


    public void showAtLocation(View view) {
        showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        if (mOnClickListner != null) {
            mOnClickListner.show();
        }
    }


    private void initView(View view, ArrayList<String> list, int position,ArrayList<GoodsCoupon> couponList) {
        unbinder = ButterKnife.bind(this, view);
        counponTypeAdapter = new CounponTypeAdapter(mContext);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        recycler.setAdapter(counponTypeAdapter);
        counponTypeAdapter.mPosition = position;
        counponTypeAdapter.setData(list);
        GoodsCoupon goodsCoupon=new GoodsCoupon();
        goodsCoupon.setId("-2");
        couponList.add(goodsCoupon);
        counponTypeAdapter.setDataModel(couponList);
        counponTypeAdapter.setOnItemClickListener(this);

    }


    @OnClick(R.id.close)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close://取消
                dismiss();
                break;
        }
    }

    public void setOnClickListent(OnClickListner onClickListner) {
        this.mOnClickListner = onClickListner;
    }



    public interface OnClickListner {
        void click(String coupomnType, int position,String id);

        void show();

        void dismiss();
    }


}