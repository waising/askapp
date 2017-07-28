package com.asking91.app.api;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wxwang on 2016/11/19.
 */
public interface PublicDataApi {
    /**
     * 检出试卷答案
     */
    @POST("space/noteBook/save")
    Observable<ResponseBody> saveNode(@Query("content") String content);

    /**
     * 收藏
     */
    @POST("space/collection/save")
    Observable<ResponseBody> saveCollection(@Query("type") int type, @Query("tille") String title,
                                            @Query("objId") String objId);

    /**
     * 检查收藏
     */
    @POST("space/collection/isCollected")
    Observable<ResponseBody> checkCollection(@Query("type") int type, @Query("id") String id);

    /* 获取在线检测的记录 */
    @POST("space/paper/search")
    Observable<ResponseBody> getTestRecord(@Query("subjectCatalog") String subjectCatalog,
                                           @Query("start") int start,
                                           @Query("limit") int limit);

    /* 获取我的收藏*/
    @POST("space/collection/list/")
    Observable<ResponseBody> getMyFavor(@Query("ticket") String ticket,
                                        @Query("type") int type,
                                        @Query("start") int start,
                                        @Query("limit") int limit);

    /* 删除我的收藏 */
    @POST("space/collection/remove/")
    Observable<ResponseBody> delMyFavor(@Query("ticket") String ticket,
                                        @Query("id") String id);

    public abstract void presenterGetMyFavSubjectDetail(int requestType, String km, int objId);

    /* 请求我的收藏里的题目详情和解析 */
    @POST("space/collection/subject/{km}/{objId}")
    Observable<ResponseBody> getMyFavSubjectDetail(@Path("km") String km,
                                                   @Path("objId") String objId);


    @POST("search/{subjectCatalog}/{subId}/convertTabLClassic")
    Observable<ResponseBody> convertTabLClassic(@Path("subjectCatalog") String subjectCatalog,
                                                @Path("subId") String subId
    );

    /* 获取我的笔记 */
    @POST("space/noteBook/list")
    Observable<ResponseBody> getMyNote(@Query("ticket") String ticket,
                                       @Query("start") int start,
                                       @Query("limit") int limit);

    /* 记（保存）笔记 */
    @POST("space/noteBook/save/")
    Observable<ResponseBody> createMyNote(@Query("content") String content);

    /* 修改笔记 */
    @POST("space/noteBook/save/")
    Observable<ResponseBody> alterMyNote(@Query("content") String content,
                                         @Query("id") String id);

    /* 删除用户笔记 */
    @POST("space/noteBook/delete")
    Observable<ResponseBody> delMyNote(@Query("ticket") String ticket,
                                       @Query("id") String id);

    /* 获取阿思币消费记录 */
    @GET("user/integralLog")
    Observable<ResponseBody> getAskMoneyRecords(
            @Query("start") int start,
            @Query("limit") int limit);

    /* 获取我的购买记录 */
    @POST("commodity/wap/userOrder")
    Observable<ResponseBody> getMyBuyRecords(@Query("ticket") String ticket,
                                             @Query("start") int start,
                                             @Query("limit") int limit);

    /* 删除我的购买记录 */
    @POST("commodity/space/order/delete")
    Observable<ResponseBody> delMyBuyRecords(@Query("orderId") String orderId);

    /* 获取我的错题本 */
    @Multipart
    @POST("space/errorCollection/{subjectCatalog}/subject/list")
    Observable<ResponseBody> getMyWrongTopic(@Path("subjectCatalog") String subjectCatalog,
                                             @PartMap Map<String, RequestBody> params
    );

    /* 获取错题本解析 */
    @POST("search/{subject_catalog}/{subjectId}/convertTabLClassic")
    Observable<ResponseBody> getMyWrongTopicAnalysis(@Path("subject_catalog") String subjectCatalog,
                                                     @Path("subjectId") String subjectId
    );

    /* 获取错题版本 */
    @POST("coach/{subject_catalog}/version")
    Observable<ResponseBody> getMyWrongVersions(@Path("subject_catalog") String subjectCatalog,
                                                @Query("type") String type
    );

    /* 获取错题年级 */
    @POST("coach/version/{version_id}/level")
    Observable<ResponseBody> getMyWrongGrade(@Path("version_id") String versionId,
                                             @Query("type") String type
    );

    // 过后得把上面那些请求参数改规范下


    /**
     * 初升高首页
     *
     * @param start
     * @param limit
     * @return
     */
    @POST("productapi/product/courseManage/V1/findListByPage")
    Observable<ResponseBody> juniorToHigh(@Query("courseTypeId") String courseTypeId,
                                          @Query("start") int start,
                                          @Query("limit") int limit);

    /**
     * 初升高全套课程
     *
     * @return
     */
    @POST("productapi/product/package/V1/find")
    Observable<ResponseBody> juniorToHighAllCourse(@Query("packageId") String packageId);


}
