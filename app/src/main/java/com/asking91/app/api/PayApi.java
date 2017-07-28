package com.asking91.app.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wxwang on 2016/11/29.
 */
public interface PayApi {




    /**
     * 获取充值提交数据
     *
     * @param
     * @return
     */
    @POST("commodity/space/appCharge")
    Observable<ResponseBody> getAppCharge(@Query("commodityId") String commodityId,
                                          @Query("rechargeId") String rechargeId,
                                          @Query("num") int num,
                                          @Query("versionId") int versionId,
                                          @Query("type") String type,
                                          @Query("payType") String payType,
                                          @Query("clientIP") String clientIP);

    /**
     * 继续支付
     *
     * @param
     * @return
     */
    @POST("commodity/space/appRecharge")
    Observable<ResponseBody> getAppReCharge(@Query("orderId") String orderId,
                                            @Query("type") String type,
                                            @Query("payType") String payType);

    /**
     * 支付成功后刷新用户数据
     *
     * @return
     */
    @POST("commodity/space/appChargeSuccess")
    Observable<ResponseBody> appChargeSuccess(@Query("order_no") String orderNo);


    /**
     * 获取辅导课和知识包信息
     *
     * @param subjectCatalog
     * @param months         套餐月份 默认12
     * @param type           1：基础知识包套餐 2：超级辅导课套餐
     * @return
     */
    @POST("commodity/getCommodityList")
    Observable<ResponseBody> getCommodityList(@Query("subjectCatalog") String subjectCatalog,
                                              @Query("months") int months,
                                              @Query("type") int type);

    /**
     * 超级辅导课
     *
     * @param subjectCatalog
     * @return
     */
    @POST("coach/{subjectCatalog}/version")
    Observable<ResponseBody> versionClassic(@Path("subjectCatalog") String subjectCatalog);

    /**
     * 基础知识包
     *
     * @param subjectCatalog
     * @return
     */
    @POST("{subjectCatalog}/version")
    Observable<ResponseBody> version(@Path("subjectCatalog") String subjectCatalog);

    /**
     * 获取支付凭据charge
     *
     * @return
     */
    @POST("payment/charge/get")
    Observable<ResponseBody> payGetCharge(@Query("orderType") String orderType,
                                          @Query("payType") String payType,
                                          @Query("commodityList") String[] commodityList,
                                          @Query("deviceType") String deviceType);

    @GET("paymentapi/payment/commodity/{versionLevelId}")
    Observable<ResponseBody> paymentcommodity(@Path("versionLevelId") String versionLevelId);


    /**
     * 获取支付凭据charge新接口
     *
     * @return
     */
    @POST("paymentNewapi/payment/charge/get")
    Observable<ResponseBody> newPayGetCharge(@Query("orderType") String orderType,
                                             @Query("payType") String payType,
                                             @Query("commodityId") String commodityId,
                                             @Query("deviceType") String deviceType,
                                             @Query("marketId") String marketId
    );


    /**
     * 课程购买套餐第一层列表
     *
     * @param packageTypeId
     * @return
     */
    @POST("productapi/product/productType/findList")
    Observable<ResponseBody> findClassList(@Query("packageTypeId") String packageTypeId);

    /**
     * 课程购买套餐第二层列表
     *
     * @param packageTypeId
     * @return
     */
    @POST("productapi/product/package/findListByPage")
    Observable<ResponseBody> findClassDeailList(@Query("packageTypeId") String packageTypeId,
                                                @Query("timeLimit") String timeLimit,
                                                @Query("start") String start,
                                                @Query("limit") String limit
    );

    /**
     * 获取ask套餐
     *
     * @return
     */
    @POST("productapi/product/askCurrency/findByPage")
    Observable<ResponseBody> getAskMoney(@Query("start") int start,
                                         @Query("limit") int limit);

}
