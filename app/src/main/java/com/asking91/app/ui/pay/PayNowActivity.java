package com.asking91.app.ui.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.global.PayConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.SystemUtil;
import com.pingplusplus.android.Pingpp;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 支付页面
 * Created by wxwang on 2016/11/30.
 */
public class PayNowActivity extends BaseFrameActivity<PayPresenter, PayModel>
        implements PayContract.View {

    private static final String CLIENTIP = "127.0.0.1";

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    Map<Integer, String> payMap;
    String rechargeId = "", commodityId = "", orderId = "", payType = "";
    int versionId = 0;
    long mExitTime;

    String charge;
    //PayEntity payEntity;

    private String orderType;

    private String[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
    }

    /**
     * 2
     */
    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.pay_title));
    }

    /**
     * 1
     */
    @Override
    public void initData() {
        super.initData();
//        payEntity = new PayEntity();
//
//        rechargeId = getIntent().getStringExtra("rechargeId");
//        commodityId = getIntent().getStringExtra("commodityId");
//        versionId = getIntent().getIntExtra("versionId", 0);
//
//        orderId = getIntent().getStringExtra("orderId");
//        payType = getIntent().getStringExtra("payType");


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            orderType = bundle.getString("orderType");
            ids = bundle.getStringArray("commodityList");
        }


        setMap();
    }

    int[] viewTypeId = new int[]{R.id.alipay_ly, R.id.unionpay_ly, R.id.wechatpay_ly};

    boolean isRePay = false;

    /**
     * 3
     */
    @Override
    public void initLoad() {
        super.initLoad();
//        if (!TextUtils.isEmpty(orderId) && !TextUtils.isEmpty(payType)) {//从购买记录的继续支付跳转过来
//            isRePay = true;
//            reCharge(viewTypeId[Integer.valueOf(payType)]);
//        }

//
//        if (!getUserConstant().isUserDataPerfect())
//            mPresenter.checkUserInfo();


    }

    @OnClick({R.id.alipay_ly, R.id.wechatpay_ly, R.id.unionpay_ly})
    public void onClick(View view) {

        if (view.getId() == R.id.wechatpay_ly && !SystemUtil.isClientAvilible(this,"com.tencent.mm")) {
            showShortToast("没有检测到微信客户端");
            return;
        }


        if ((System.currentTimeMillis() - mExitTime) > 2000) {//避免重复点击
            mExitTime = System.currentTimeMillis();
//            if (isRePay) {//继续支付
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

            String[] payinfo = payMap.get(view.getId()).split("_");
            mPresenter.payGetCharge(orderType, payinfo[1], ids);

        }
    }

//    /**
//     * 继续支付接口
//     *
//     * @param payTypeId
//     */
//    private void reCharge(int payTypeId) {
//        payEntity.setOrderId(orderId);
//        String[] payinfo = payMap.get(payTypeId).split("_");
//        payEntity.setType(payinfo[0]);
//        payEntity.setPayType(payinfo[1]);
//
//        mPresenter.getAppReCharge(payEntity);
//    }

    /**
     * 成功回调
     *
     * @param type
     * @param body
     */
    @Override
    public void onSuccess(int type, ResponseBody body) {
        switch (type) {
//            case PayConstant.Pay.charge://立即支付接口和继续支付接口
//                pay(body);
//                break;
            case PayConstant.Pay.chargeSuccess://支付成功刷新用户信息
                paySuccess(body);
                break;
            case PayConstant.Pay.CHECKUSERINFO://资料是否完善
//                Map<String, Object> map = CommonUtil.parseDataToMap(body);
//                getUserConstant().setIsUserDataPerfect(map.get("flag") != null && "0".equals(map.get("flag")));
                break;
            case PayConstant.Pay.getCharge://新接口
                payNow(body);
                break;
        }
    }

    /**
     * 立即支付接口和继续支付接口
     */
//    private void pay(ResponseBody body) {
//        try {
//            Map<String, Object> map = CommonUtil.parseDataToMap(body);
//            if (map.get("flag") != null && "0".equals(map.get("flag"))) {
//                charge = String.valueOf(map.get("charge"));
//                Pingpp.createPayment(this, charge);//调用支付端
//            } else {
//                showShortToast(String.valueOf(map.get("msg")));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            showShortToast("支付失败！");
//        }
//    }
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

    /**
     * 支付成功刷新用户信息
     *
     * @param body
     */
    private void paySuccess(ResponseBody body) {
        try {
            Map<String, Object> map = CommonUtil.parseDataToMap(body);
            if (map.get("flag") != null && "0".equals(map.get("flag"))) {
                showShortToast(String.valueOf(map.get("msg")));

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
                        //获取订单
                        String orderNo = (String) CommonUtil.parseDataToMap(charge).get("order_no");
                        //刷新用户信息
                        mPresenter.appChargeSuccess(orderNo);

                        //ruguo没有完善资料则去完善资料
//                        if (!getUserConstant().isUserDataPerfect()) {
//                            Bundle bundle = new Bundle();
//                            bundle.putString("openCameraActivity", SuperClassifyActivity.class.getName());
//                            openCameraActivity(MineSchoolInfoActivity.class, bundle);
//                        }
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
}
