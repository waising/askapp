package com.asking91.app.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by linbin on 2017/6/19.
 */

public interface CouponApi {


    /**
     * 优惠券
     *
     * @return
     */
    @GET("marketapi/userevent")
    Observable<ResponseBody> getCouponList(
            @Query("start") int start,
            @Query("limit") int limit,
            @Query("plateform") int plateform,
            @Query("eventType") String eventType,
            @Query("product") String product
    );


}
