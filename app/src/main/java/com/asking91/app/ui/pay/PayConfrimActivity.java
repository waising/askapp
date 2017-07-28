package com.asking91.app.ui.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.entity.juniorTohigh.GoodsCoupon;
import com.asking91.app.entity.pay.PayEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.PayConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.dialog.CouponTypePopupWindow;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.SystemUtil;
import com.pingplusplus.android.Pingpp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;


/**
 * 支付确认页面
 * Created by wxwang on 2016/11/30.
 */
public class PayConfrimActivity extends BaseFrameActivity<PayPresenter, PayModel>
        implements PayContract.View {

    private static final String CLIENTIP = "127.0.0.1";

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    Map<Integer, String> payMap;
    String commodityId = "", orderId = "", payType = "";
    int versionId = 0;
    long mExitTime;

    String charge;
    PayEntity payEntity;

    /**
     * 从哪个界面跳转过来
     */
    private int fromWhere;


    @BindView(R.id.ll_ask_coin)//阿思币跳转过来界面
            LinearLayout llAskCoin;
    @BindView(R.id.ll_synchronous_courses)//同步课程购买跳转过来界面
            LinearLayout llSynchronousCourses;

    @BindView(R.id.single_all_course)//单套课程和全套课程购买界面
            LinearLayout llSingleAllCourses;


    @BindView(R.id.pay_now)//支付按钮
            TextView tvPayNow;


    @BindView(R.id.all_purchase)//全套课程简介
            LinearLayout llAllPurchase;
    @BindView(R.id.view_line_all)//
            View view_line_all;


    @BindView(R.id.recycler_all_purchase)//全套课程简介recyclerView
            RecyclerView recyclerAllPurchase;
    private int mSelected = -1;//记录选中哪一个
    /**
     * 三种支付方式单选
     */
    private final int[] llPayWay = {R.id.alipay_ly, R.id.wechatpay_ly, R.id.unionpay_ly,};
    private List<LinearLayout> llPayWayList;//支付方式

    /**
     * 三种支付方式,默认支付宝
     */
    private int pay_style = R.id.alipay_ly;


    @BindView(R.id.tv_pay_money)//支付价格
            TextView tvPayMoney;


    @BindView(R.id.ask_money_tv)
    TextView mAskMoneyTv;//图片中金币数量
    @BindView(R.id.money_tv)
    TextView mMoneyTv;///图片中金币价格

    @BindView(R.id.tv_ask_coin_num)
    TextView tvAskCoinNum;//金币数量

    @BindView(R.id.ask_coin_price)
    TextView tvAskCoinPrice;//金币价格
    private String orderType;

    private String[] ids;

    @BindView(R.id.book)
    ImageView book;
    @BindView(R.id.tv_class_name)
    TextView tvClassName;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;

    @BindView(R.id.tv_synchronous_course_price)//同步课程的价格
            TextView SynchronousCoursePrice;

    private String subjectImgKey;

    private String goodsId;
    @BindView(R.id.class_price)
    TextView tvClassPrice;
    @BindView(R.id.class_name)
    TextView className;

    private String coinNum;
    @BindView(R.id.tv_tag)
    TextView tvTag;
    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;

    @BindView(R.id.rel_main_layout)
    RelativeLayout relMainLayout;
    @BindView(R.id.tv_coupon_content)
    TextView tvCouponContent;
    @BindView(R.id.bg_gray)
    View bgView;


    ArrayList<GoodsCoupon> couponList;

    ArrayList<String> couponListStr;
    private String payPrice;
    /**
     * 学习时限
     */
    @BindView(R.id.tv_time_limit)
    TextView tvTimeLimit;

    @BindView(R.id.tv_limit_date)
    TextView tv_limit_date;

    @BindView(R.id.ll_time_limit)
    LinearLayout ll_time_limit;

    @BindView(R.id.ll_limit_coupon)
    LinearLayout ll_limit_coupon;


    /**
     * 上一次点击position
     */
    private int lastPosition;


    private String couponId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_copy);
        ButterKnife.bind(this);
        initPayWayList();
        updateRadioCycle(0);
    }

    /**
     * 初始化支付方式
     */
    private void initPayWayList() {
        llPayWayList = new ArrayList<>();
        for (int res : llPayWay) {
            View view = findViewById(res);
            llPayWayList.add((LinearLayout) view);
            view.setOnClickListener(onClickListner);
        }
    }

    /**
     * 2
     */
    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.pay_title));
        tvPayNow.setText("立即支付");//改变支付按钮样式
        tvPayNow.setBackgroundResource(R.drawable.btn_red_normal);
        tvPayNow.setOnClickListener(onClickListner);
        llCoupon.setOnClickListener(onClickListner);
    }

    /**
     * 1
     */
    @Override
    public void initData() {
        super.initData();
        payEntity = new PayEntity();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            fromWhere = bundle.getInt("fromWhere");
            commodityId = bundle.getString("commodityId");
            versionId = bundle.getInt("versionId", 0);
            orderId = bundle.getString("orderId");
            payType = bundle.getString("payType");
            String payMoney = bundle.getString("payMoney");//应支付的价格
            switch (fromWhere) {
                case Constants.ConfirmOrder.ask_coin://阿思币购买
                    coinNum = bundle.getString("coinNum");//金币数量
                    llAskCoin.setVisibility(View.VISIBLE);
                    llSynchronousCourses.setVisibility(View.GONE);
                    llSingleAllCourses.setVisibility(View.GONE);
                    tvPayMoney.setText(getString(R.string.money_format, priceFormat(payMoney)));
                    mAskMoneyTv.setText(coinNum);
                    mMoneyTv.setText(payMoney);
                    tvAskCoinNum.setText(getString(R.string.coin_format, coinNum));
                    tvAskCoinPrice.setText(getString(R.string.price_format, payMoney));
                    break;
                case Constants.ConfirmOrder.course_buy://课程购买
                    BitmapUtil.displayImage(bundle.getString("imageUrl"), book, true);

                    ll_time_limit.setVisibility(View.VISIBLE);
                    tvClassName.setText(bundle.getString("className"));//初中数学这种
                    tvTimeLimit.setText(bundle.getString("timeLimit"));//学习时限
                    try {
                        BigDecimal price = new BigDecimal(bundle.getString("price"));
                        Double money = price.divide(new BigDecimal("100"), 3, RoundingMode.HALF_UP).doubleValue();
                        SynchronousCoursePrice.setText("¥ " + money);//单价
                        tvPayMoney.setText(getString(R.string.money_format, priceFormat(String.valueOf(money))));//应付价格
                    } catch (Exception e) {
                    }
                    tvVersionName.setVisibility(View.GONE);
                    goodsId = bundle.getString("commodityId");
                    setBuyRecordAndSynchronousVisble();
                    ll_limit_coupon.setVisibility(View.GONE);
                    break;
                case Constants.ConfirmOrder.synchronous_course://同步课程购买

                    ll_time_limit.setVisibility(View.VISIBLE);
                    BitmapUtil.displayImage(bundle.getString("imageUrl"), book, true);
                    tvClassName.setText(bundle.getString("className"));//初中数学这种
                    tvVersionName.setText(bundle.getString("versionName"));
                    String timeLimit = bundle.getString("timeLimit");
                    if (!TextUtils.isEmpty(timeLimit)) {
                        tvTimeLimit.setText((int) (Float.parseFloat(timeLimit)) + "个月");//学习时限
                    }
                    String favorableStart = bundle.getString("favorableStart");
                    String favorableEnd = bundle.getString("favorableEnd");
                    if (!TextUtils.isEmpty(favorableStart) && !TextUtils.isEmpty(favorableEnd)) {
                        if (compareTime(favorableStart, favorableEnd))//显示惠字
                        {
                            ll_limit_coupon.setVisibility(View.VISIBLE);
                            tv_limit_date.setText(favorableStart.substring(5, 10) + "~" + favorableEnd.substring(5, 10));
                        } else {
                            ll_limit_coupon.setVisibility(View.GONE);
                        }
                    } else {
                        ll_limit_coupon.setVisibility(View.GONE);
                    }
                    if ((!TextUtils.isEmpty(favorableStart)) && (!TextUtils.isEmpty(favorableEnd))) {

                    }
                    try {
                        BigDecimal price = new BigDecimal(bundle.getString("price"));
                        Double money = price.divide(new BigDecimal("100"), 3, RoundingMode.HALF_UP).doubleValue();
                        SynchronousCoursePrice.setText("¥ " + money);//单价
                        tvPayMoney.setText(getString(R.string.money_format, priceFormat(String.valueOf(money))));//应付价格
                    } catch (Exception e) {
                    }

                    goodsId = bundle.getString("commodityId");
//                    subjectImgKey = bundle.getString("subjectImgKey");
//
//                    try {
//                        String imageName = PayConstant.getVersionImages(subjectImgKey);
//                        if (!TextUtils.isEmpty(imageName)) {
//                            InputStream in;
//                            in = getAssets().open("images/km/" + imageName);
//                            Bitmap bmp = BitmapFactory.decodeStream(in);
//                            book.setImageBitmap(bmp);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } catch (OutOfMemoryError e) {
//                        e.printStackTrace();
//                    }

                    setBuyRecordAndSynchronousVisble();
                    break;
                case Constants.ConfirmOrder.buy_record://购买记录
                    setBuyRecordAndSynchronousVisble();
                    break;
                case Constants.ConfirmOrder.single_course://单套课程购买
                    setSingleAllCourseVisible();
                    llAllPurchase.setVisibility(View.GONE);
                    className.setText(bundle.getString("courseName"));
                    payPrice = bundle.getString("price");
                    tvClassPrice.setText("¥ " + PayConstant.formatPrice(payPrice));
                    tvPayMoney.setText(getString(R.string.money_format, PayConstant.formatPrice(payPrice)));
                    couponVisible(bundle);
                    goodsId = bundle.getString("goodsId");
                    tvTag.setText(bundle.getString("courseTypeFullName"));
                    break;
                case Constants.ConfirmOrder.full_course://全套课程购买
                    setSingleAllCourseVisible();
                    view_line_all.setVisibility(View.GONE);
                    tvTag.setVisibility(View.GONE);
                    className.setText(bundle.getString("courseName"));
                    payPrice = bundle.getString("price");
                    tvClassPrice.setText("¥ " + PayConstant.formatPrice(payPrice));
                    tvPayMoney.setText(getString(R.string.money_format, PayConstant.formatPrice(payPrice)));
                    couponVisible(bundle);
                    goodsId = bundle.getString("goodsId");
                    tvTag.setText(bundle.getString("courseName"));
                    ArrayList<String> presentName = bundle.getStringArrayList("presentName");
                    if (presentName != null && presentName.size() > 0) {
                        llAllPurchase.setVisibility(View.VISIBLE);
                        CommAdapter commAdapter = new CommAdapter(this, presentName);
                        recyclerAllPurchase.setLayoutManager(new LinearLayoutManager(this));
                        recyclerAllPurchase.setAdapter(commAdapter);
                    }

                    break;
            }
        }

        setMap();
    }

    /**
     * 优惠券可见
     *
     * @param bundle
     */
    private void couponVisible(Bundle bundle) {
        couponList = (ArrayList<GoodsCoupon>) bundle.getSerializable("couponList");

        if (couponList != null && couponList.size() > 0) {

            couponListStr = ScreenList(couponList);
            couponId = couponList.get(0).getId();
            if (couponListStr != null && couponListStr.size() > 0) {
                llCoupon.setVisibility(View.VISIBLE);
                String couponItem = couponListStr.get(0);
                if (!TextUtils.isEmpty(couponItem)) {
                    tvCouponContent.setText(couponItem);
                    tvPayMoney.setText(getString(R.string.money_format, minusCouponPrice(payPrice, couponItem.substring(0, couponItem.length() - 4))));
                }
                couponListStr.add("不使用优惠");
            }

        }


    }


    /**
     * 购买记录，同步课程view可见
     */
    private void setBuyRecordAndSynchronousVisble() {
        llAskCoin.setVisibility(View.GONE);
        llSynchronousCourses.setVisibility(View.VISIBLE);
        llSingleAllCourses.setVisibility(View.GONE);
    }

    /**
     * 单套课程，全套课程view可见
     */
    private void setSingleAllCourseVisible() {
        llAskCoin.setVisibility(View.GONE);
        llSynchronousCourses.setVisibility(View.GONE);
        llSingleAllCourses.setVisibility(View.VISIBLE);
    }

    int[] viewTypeId = new int[]{R.id.alipay_ly, R.id.unionpay_ly, R.id.wechatpay_ly};

    boolean isRePay = false;

    /**
     * 3
     */
    @Override
    public void initLoad() {
        super.initLoad();
        if (!TextUtils.isEmpty(orderId) && !TextUtils.isEmpty(payType)) {//从购买记录中的继续支付跳转过来
            isRePay = true;
            reCharge(viewTypeId[Integer.valueOf(payType)]);
        }


//        if (!getUserConstant().isUserDataPerfect())
//            mPresenter.checkUserInfo();
    }

//    @OnClick({R.id.alipay_ly, R.id.wechatpay_ly, R.id.unionpay_ly})
//    public void onClick(View view) {
//
//        if (view.getId() == R.id.wechatpay_ly && !SystemUtil.isWeixinAvilible(this)) {
//            showShortToast("没有检测到微信客户端");
//            return;
//        }
//
//
//        if ((System.currentTimeMillis() - mExitTime) > 2000) {//避免重复点击
//            mExitTime = System.currentTimeMillis();
//            if (isRePay) {//继续支付，购买记录中的
//                reCharge(view.getId());
//            } else {//立即支付接口
//                payEntity.setClientIP(CLIENTIP);
//                payEntity.setCommodityId(commodityId);
//                payEntity.setRechargeId(rechargeId);
//                payEntity.setNum(1);//1:立即支付
//                String[] payinfo = payMap.get(view.getId()).split("_");
//                payEntity.setType(payinfo[0]);
//                payEntity.setPayType(payinfo[1]);
//                payEntity.setVersionId(versionId);
//
//                mPresenter.getAppCharge(payEntity);
//            }
//
//        }
//    }

    /**
     * 继续支付接口
     *
     * @param payTypeId
     */
    private void reCharge(int payTypeId) {
        payEntity.setOrderId(orderId);
        String[] payinfo = payMap.get(payTypeId).split("_");
        payEntity.setType(payinfo[0]);
        payEntity.setPayType(payinfo[1]);

        mPresenter.getAppReCharge(payEntity);
    }

    /**
     * 成功回调
     *
     * @param type
     * @param body
     */
    @Override
    public void onSuccess(int type, ResponseBody body) {
        switch (type) {
            case PayConstant.Pay.charge://立即支付接口和继续支付接口
                pay(body);
                break;
            case PayConstant.Pay.chargeSuccess://支付成功刷新用户信息
                paySuccess(body);
                break;
            case PayConstant.Pay.CHECKUSERINFO://资料是否完善
//                Map<String, Object> map = CommonUtil.parseDataToMap(body);
//                getUserConstant().setIsUserDataPerfect(map.get("flag") != null && "0".equals(map.get("flag")));
                break;
            case PayConstant.Pay.getCharge://新接口，同步课程
                payNow(body);
                break;
        }
    }

    /**
     * 立即支付接口和继续支付接口
     */
    private void pay(ResponseBody body) {
        try {
            Map<String, Object> map = CommonUtil.parseDataToMap(body);
            if (map.get("flag") != null && "0".equals(map.get("flag"))) {
                charge = String.valueOf(map.get("charge"));
                Pingpp.createPayment(this, charge);//调用支付端
            } else {
                showShortToast(String.valueOf(map.get("msg")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showShortToast("支付失败！");
        }
    }

    /**
     * 支付成功刷新用户信息
     *
     * @param body
     */
    private void paySuccess(ResponseBody body) {
        try {
            Map<String, Object> map = CommonUtil.parseDataToMap(body);
            if (map.get("flag") != null && "0".equals(map.get("flag"))) {
                //  showShortToast(String.valueOf(map.get("msg")));

                if (!TextUtils.isEmpty(orderId)) {//结束，回退
                    setResult(RESULT_OK);
                }

                this.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - 支付成功
                 * "fail"    - 支付失败
                 * "cancel"  - 取消支付
                 * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
                 */
                    String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                    String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
//                showMsg(result, errorMsg, extraMsg);
                    //成功
                    if ("success".equals(result)) {
//                        //获取订单
//                        String orderNo = (String) CommonUtil.parseDataToMap(charge).get("order_no");
//                        //刷新用户信息
//                        mPresenter.appChargeSuccess(orderNo);

                        //ruguo没有完善资料则去完善资料
//                        if (!getUserConstant().isUserDataPerfect()) {
//                            Bundle bundle = new Bundle();
//                            bundle.putString("openCameraActivity", SuperClassifyActivity.class.getName());
//                            openCameraActivity(MineSchoolInfoActivity.class, bundle);
//                        }
                        finish();
                        Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
                        if (fromWhere == Constants.ConfirmOrder.single_course) {
                            EventBus.getDefault().post(new AppEventType(AppEventType.SINGLE_PAY_SUCCESS));
                        } else if (fromWhere == Constants.ConfirmOrder.full_course) {
                            EventBus.getDefault().post(new AppEventType(AppEventType.ALL_PAY_SUCCESS));
                        } else if (fromWhere == Constants.ConfirmOrder.synchronous_course) {//同步课程购买成功
                            EventBus.getDefault().post(new AppEventType(AppEventType.SYNCHRONOUS_PAY_SUCCESS));
                        } else if (fromWhere == Constants.ConfirmOrder.ask_coin) {//阿思币购买成功
//保存增加的金币
                            getUserConstant().getUserEntity().setIntegral(getUserConstant().getUserEntity().getIntegral() + Integer.parseInt(coinNum));
                            EventBus.getDefault().post(new AppEventType(AppEventType.ASK_COIN_SUCCESS));
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        if (null != msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null != msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    /**
     * 设置几种支付方式
     */
    private void setMap() {
        payMap = new ArrayMap<>();

        payMap.put(R.id.alipay_ly, "0_alipay");
        payMap.put(R.id.unionpay_ly, "1_upacp");
        payMap.put(R.id.wechatpay_ly, "2_wx");
    }

    /**
     * 三种支付方式
     */
    private View.OnClickListener onClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.alipay_ly://支付宝
                    pay_style = R.id.alipay_ly;
                    updateRadioCycle(0);
                    break;
                case R.id.wechatpay_ly://微信
                    pay_style = R.id.wechatpay_ly;
                    updateRadioCycle(1);
                    break;
                case R.id.unionpay_ly://银联
                    pay_style = R.id.unionpay_ly;
                    updateRadioCycle(2);
                    break;
                case R.id.pay_now://支付


                    payWay();
                    break;
                case R.id.ll_coupon://优惠券
                    showPopupWindow(relMainLayout, couponListStr, couponList);
                    break;

            }

        }
    };

    /**
     * 刷新单选状态
     *
     * @param position
     */
    private void updateRadioCycle(int position) {
        if (mSelected != position) {//避免重复点击
            mSelected = position;//记录选中哪一个
            for (int i = 0; i < llPayWayList.size(); i++) {
                if (i == position) {//选中状态下
                    llPayWayList.get(i).setSelected(true);
                } else {//未选中状态下
                    llPayWayList.get(i).setSelected(false);
                }
            }
        }
    }

    /**
     * 保留两位小数
     *
     * @param price
     * @return
     */
    private String priceFormat(String price) {
        double m = Double.parseDouble(price);
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(m);
    }


    /**
     * 支付
     * TC 课程购买 全套
     * <p>
     * KC 同步课程，单套课程购买
     * <p>
     * ASB 阿思币
     *
     * @param
     */
    private void payWay() {
        if (pay_style == R.id.wechatpay_ly && !SystemUtil.isClientAvilible(this,"com.tencent.mm")) {
            showShortToast("没有检测到微信客户端");
            return;
        }
        if ((System.currentTimeMillis() - mExitTime) > 2000) {//避免重复点击
            Toast.makeText(this, "后台正在调用支付系统", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
            String[] payinfo = payMap.get(pay_style).split("_");
            if (fromWhere == Constants.ConfirmOrder.synchronous_course)//同步课程
            {
                mPresenter.newPayGetCharge("KC", payinfo[1], goodsId, null);
            } else if (fromWhere == Constants.ConfirmOrder.single_course)//单套课程
            {
                if (!TextUtils.isEmpty(couponId)) {
                    if (couponId.equals("-2")) {//不使用优惠券
                        mPresenter.newPayGetCharge("KC", payinfo[1], goodsId, null);
                    } else {
                        mPresenter.newPayGetCharge("KC", payinfo[1], goodsId, couponId);
                    }
                } else {
                    mPresenter.newPayGetCharge("KC", payinfo[1], goodsId, null);
                }

            } else if (fromWhere == Constants.ConfirmOrder.course_buy) {//课程购买
                mPresenter.newPayGetCharge("TC", payinfo[1], goodsId, null);
            } else if (fromWhere == Constants.ConfirmOrder.full_course)//全套
            {
                if (!TextUtils.isEmpty(couponId)) {
                    if (couponId.equals("-2")) {//不使用优惠券
                        mPresenter.newPayGetCharge("TC", payinfo[1], goodsId, null);
                    } else {
                        mPresenter.newPayGetCharge("TC", payinfo[1], goodsId, couponId);
                    }
                } else {
                    mPresenter.newPayGetCharge("TC", payinfo[1], goodsId, null);
                }
            } else {
                if (isRePay) {//继续支付，购买记录中的
                    reCharge(pay_style);
                } else {//立即支付接口，阿思币充值中的
                    payEntity.setClientIP(CLIENTIP);
                    mPresenter.newPayGetCharge("ASB", payinfo[1], commodityId, null);

                }
            }

        }

    }


    /**
     * 新接口，同步课程
     *
     * @param body
     */
    private void payNow(ResponseBody body) {
        try {
            String resStr = body.string();
            JSONObject jsonRes = JSON.parseObject(resStr);
            String flag = jsonRes.getString("flag");
            if (TextUtils.equals("0", flag)) {//0表示返回成功
                String result = jsonRes.getString("content");
                JSONObject resobj = JSON.parseObject(result);
                charge = resobj.getString("charge");
                Pingpp.createPayment(this, charge);//调用支付端
            } else {
                showShortToast(String.valueOf(jsonRes.get("msg")));
            }

        } catch (Exception e) {
            e.printStackTrace();
            showShortToast("支付失败！");
        }

    }


    class CommViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;

        public CommViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class CommAdapter extends RecyclerView.Adapter<CommViewHolder> {

        private List<String> mDatas;
        private Context mContext;

        public CommAdapter(Context context, List<String> datas) {
            this.mContext = context;
            this.mDatas = datas;
        }

        @Override
        public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_all_list, parent, false));
        }

        @Override
        public void onBindViewHolder(CommViewHolder holder, int position) {
            final String name = mDatas.get(position);
            holder.tvName.setText(name);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    /**
     * 显示底部优惠券popupWindow
     */
    private void showPopupWindow(RelativeLayout relMain, ArrayList<String> list, ArrayList<GoodsCoupon> couponList) {
        CouponTypePopupWindow savaImagePopupWindow = new CouponTypePopupWindow(this, list, lastPosition, couponList);
        savaImagePopupWindow.setOnClickListent(new CouponTypePopupWindow.OnClickListner() {

            @Override
            public void click(String coupomnType, int position, String id) {
                lastPosition = position;

                couponId = id;
                tvCouponContent.setText(coupomnType);
                if (!coupomnType.equals("不使用优惠")) {
                    tvPayMoney.setText(getString(R.string.money_format, minusCouponPrice(payPrice, coupomnType.substring(0, coupomnType.length() - 4))));
                } else {
                    tvPayMoney.setText(getString(R.string.money_format, PayConstant.formatPrice(payPrice)));
                }
            }

            @Override
            public void show() {
                bgView.setVisibility(View.VISIBLE);
            }

            @Override
            public void dismiss() {
                bgView.setVisibility(View.GONE);
            }
        });
        savaImagePopupWindow.showAtLocation(relMain);//在布局底部
    }


    /**
     * 减去优惠的价格
     *
     * @param itemPrice
     * @return
     */
    public static String minusCouponPrice(String itemPrice, String coupon) {
        String priceStr = null;
        // 金额（单位是分，要除以100）
        if (!TextUtils.isEmpty(itemPrice)) {
            try {
                double d = (new Double(itemPrice)).doubleValue();
                double price = d / 100;
                double couponPrice = (new Double(coupon)).doubleValue();

                BigDecimal b1 = new BigDecimal(Double.toString(price));
                BigDecimal b2 = new BigDecimal(Double.toString(couponPrice));
                priceStr = String.valueOf(b1.subtract(b2).doubleValue());

            } catch (NumberFormatException e) {
            }
        } else {
            priceStr = "";
        }
        return priceStr;
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

                            if ((int) (Float.parseFloat(payPrice)) >= ruleBean.getPurchase()) {//满足满减条件
                                cuponList.add(PayConstant.formatPrice(ruleBean.getOff() + "") + "元现金券");
                            }

                        }

                    }
                }

            }

        }
        return cuponList;
    }


    /**
     * 比较时间
     */
    private boolean compareTime(String startTime, String endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        long endTimeLong = 0;
        long startTimeLong = 0;
        try {
            endTimeLong = simpleDateFormat.parse(endTime).getTime();//当前时间
            startTimeLong = simpleDateFormat.parse(startTime).getTime();//当前时间
            if ((System.currentTimeMillis()) >= startTimeLong && (System.currentTimeMillis() <= endTimeLong))//当前时间>优惠时间 超过优惠时间，不显示惠字
            {
                return true;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
