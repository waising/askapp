package com.asking91.app.ui.pay;

import com.asking91.app.api.Networks;
import com.asking91.app.entity.pay.PayEntity;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxwang on 2016/11/14.
 */
public class PayModel implements PayContract.Model {



    @Override
    public Observable<ResponseBody> getAppCharge(PayEntity payEntity) {
        return Networks.getInstance().getPayApi()
                .getAppCharge(payEntity.getCommodityId(), payEntity.getRechargeId(), payEntity.getNum(), payEntity.getVersionId(),
                        payEntity.getType(), payEntity.getPayType(), payEntity.getClientIP()).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getAppReCharge(PayEntity payEntity) {
        return Networks.getInstance().getPayApi()
                .getAppReCharge(payEntity.getOrderId(),
                        payEntity.getType(), payEntity.getPayType()).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> appChargeSuccess(String orderNo) {
        return Networks.getInstance().getPayApi()
                .appChargeSuccess(orderNo).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> versionClassic(String subjectCatalog) {
        return Networks.getInstance().getPayApi()
                .versionClassic(subjectCatalog).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> version(String subjectCatalog) {
        return Networks.getInstance().getPayApi()
                .version(subjectCatalog).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getCommodityList(String subjectCatalog, int months, int type) {
        return Networks.getInstance().getPayApi()
                .getCommodityList(subjectCatalog, months, type).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> checkUserInfo() {
        return Networks.getInstance().getUserApi()
                .checkUserInfo().compose(RxSchedulers.<ResponseBody>io_main());
    }


    @Override
    public Observable<ResponseBody> payGetCharge(String orderType, String payType, String[] commodityList) {
        return Networks.getInstance().getPayApi()
                .payGetCharge(orderType, payType, commodityList, "android").compose(RxSchedulers.<ResponseBody>io_main());

    }

    public Observable<ResponseBody> paymentcommodity(String versionLevelId) {
        return Networks.getInstance().getPayApi().paymentcommodity(versionLevelId).compose(RxSchedulers.<ResponseBody>io_main());

    }

    /**
     * 课程购买第一层
     *
     * @param packageTypeId
     * @return
     */
    @Override
    public Observable<ResponseBody> findClassList(String packageTypeId) {
        return Networks.getInstance().getPayApi().findClassList(packageTypeId).compose(RxSchedulers.<ResponseBody>io_main());

    }

    /**
     * 课程购买第二层
     *
     * @param packageTypeId
     * @return
     */
    @Override
    public Observable<ResponseBody> findClassDetailList(String packageTypeId, String timeLimit, String start, String limit) {
        return Networks.getInstance().getPayApi().findClassDeailList(packageTypeId, timeLimit, start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }

    /**
     * 新的支付接口
     * @param orderType
     * @param payType

     * @return
     */
    @Override
    public Observable<ResponseBody> payNewGetCharge(String orderType, String payType, String commodityId,String marketId) {
        return Networks.getInstance().getPayApi()
                .newPayGetCharge(orderType, payType, commodityId, "android",marketId).compose(RxSchedulers.<ResponseBody>io_main());

    }

    /**
     * 获取阿思币列表
     * @param start
     * @param limit
     * @return
     */

    @Override
    public Observable<ResponseBody> getAskMoney(int start, int limit) {
        return Networks.getInstance().getPayApi()
                .getAskMoney(start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }


}
