package com.asking91.app.common;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.juniorTohigh.GoodsCoupon;
import com.asking91.app.entity.juniorTohigh.JuniorToHighAllCourseEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.global.PayConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.juniorhigh.JuniorToHighModel;
import com.asking91.app.ui.juniorhigh.JuniorToHighPresenter;
import com.asking91.app.ui.pay.PayConfrimActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CommonUtil;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.view.BigImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 初升高顶部滚图
 * Created by wxiao on 2016/12/9.
 */

public class BigImgShowActivity extends BaseFrameActivity<JuniorToHighPresenter, JuniorToHighModel> {

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.mBigImage)
    BigImageView mBigImageView;


    private String imgPath;

    private String title;

    @BindView(R.id.pay_bottom)
    LinearLayout llBottomPay;


    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;

    private String goodsId;
    /**
     * 商品价格
     */
    private String goodsPrice;
    /**
     * 套餐名称
     */
    private String packageName;
    private ArrayList<String> presentName;

    ArrayList<GoodsCoupon> couponList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_show);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        super.initData();
        imgPath = getIntent().getStringExtra("imgPath");
        if (!imgPath.startsWith("http")) {
            imgPath = "file://" + imgPath;
        }
        title = getIntent().getStringExtra("title");
        String p = getIntent().getStringExtra("p");
        if (p.equals("0"))//初升高全套课程
        {
            toolBar.setVisibility(View.GONE);
            String courseType = getIntent().getStringExtra("courseType");
            if (!TextUtils.isEmpty(courseType)) {
                requestAllCourse(courseType);
            }
        } else {//一对一答疑
            setToolbar(toolBar, TextUtils.isEmpty(title) ? "详情介绍" : title);
            llBottomPay.setVisibility(View.GONE);
        }

    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void initView() {
        super.initView();

        if (!TextUtils.isEmpty(imgPath)) {
            BigImageViewer.prefetch(Uri.parse(imgPath));
            mBigImageView.showImage(Uri.parse(imgPath));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.pay_now})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_now://立即购买
                Bundle bundle = new Bundle();
                bundle.putString("goodsId", goodsId);//商品id
                bundle.putString("courseName", packageName);//课程名
                bundle.putString("price", goodsPrice);//价格
                bundle.putInt("fromWhere", Constants.ConfirmOrder.full_course);
                bundle.putStringArrayList("presentName", presentName);
                //  bundle.putSerializable("couponList", ScreenList(couponList));//优惠券list
                bundle.putSerializable("couponList", couponList);
                openActivity(PayConfrimActivity.class, bundle);
                break;

        }
    }

    /**
     * 全套课程信息
     */
    private void requestAllCourse(String courseType) {

        mPresenter.JuniorToHighAllCourse(courseType, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                if (!TextUtils.isEmpty(res)) {//有全套套餐情况
                    llBottomPay.setVisibility(View.VISIBLE);
                    JuniorToHighAllCourseEntity juniorToHighAllCourseEntity = CommonUtil.data2Clazz(res, JuniorToHighAllCourseEntity.class);
                    tvPayMoney.setText(getString(R.string.money_format, PayConstant.formatPrice(juniorToHighAllCourseEntity.getPackagePrice())));
                    goodsId = juniorToHighAllCourseEntity.getCommodityId();
                    goodsPrice = juniorToHighAllCourseEntity.getPackagePrice();
                    packageName = juniorToHighAllCourseEntity.getPackageName();
                    presentName = juniorToHighAllCourseEntity.getPresentName();
                    requestCoupon(juniorToHighAllCourseEntity.getCommodityId());
                }
            }

            @Override
            public void onResultFail() {//没全套套餐情况
                llBottomPay.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.ALL_PAY_SUCCESS://
                finish();
                break;
        }
    }

    public ArrayList<String> ScreenList(ArrayList<GoodsCoupon> list) {
        ArrayList<String> cuponList = null;
        if (list != null && list.size() > 0) {
            cuponList = new ArrayList<String>();

            for (int i = 0; i < list.size(); i++) {
                GoodsCoupon item = list.get(i);
                if (item != null) {
                    GoodsCoupon.EventBean event = item.getEvent();
                    if (event != null) {
                        GoodsCoupon.EventBean.RuleBean ruleBean = event.getRule();
                        if (ruleBean != null) {
                            cuponList.add(PayConstant.formatPrice(ruleBean.getOff() + "") + "元现金券");
                        }

                    }
                }

            }

        }
        return cuponList;
    }

    private void requestCoupon(String commodityId) {
        mPresenter.goodsCoupon(commodityId, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                ArrayList<GoodsCoupon> list = (ArrayList<GoodsCoupon>) JSON.parseArray(res, GoodsCoupon.class);
                if (list != null && list.size() > 0) {
                    couponList = list;
                }

            }

            @Override
            public void onResultFail() {
            }

            @Override
            public void onResultFail(RequestEntity resultEntity) {
                if (resultEntity.getCode() == HttpCodeConstant.CONNECT_401) {
                    showShortToast("未登录");
                }
            }
        });
    }
}
