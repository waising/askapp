package com.asking91.app.api;

import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wxwang on 2016/10/31.
 */

public interface ReferApi {

    /**
     *获取资讯
     * @param
     * @return
     */
    @POST("consultation/listById")
    Observable<ResponseBody> getReferList(@Query("id") String id,
                                          @Query("start") int start,
                                          @Query("limit") int limit);

    @POST("consultation/{referId}")
    Observable<ResponseBody> getReferDetail(@Path("referId") String referId);
}
