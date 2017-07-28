package com.asking91.app.api;


import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zqshen on 2016/11/16.
 */

public interface SearchApi {

    // 搜知识
    @POST("search/{subjectCatalog}/knowledge/")
    Observable<ResponseBody> getKnowledge(@Path("subjectCatalog") String subjectCatalog,
                                          @Query("q") String q);

    // 搜题目
    @Multipart
    @POST("search/{subjectCatalog}/subject/list/")
    Observable<ResponseBody> getSubject(@Path("subjectCatalog") String subjectCatalog,@PartMap Map<String, RequestBody> params);

    // 获取题目详情
    @POST("search/{subjectCatalog}/{subId}/convertTabLClassic/")
    Observable<ResponseBody> getSubjectDetails(@Path("subjectCatalog") String subjectCatalog,
                                               @Path("subId") String subId);
}
