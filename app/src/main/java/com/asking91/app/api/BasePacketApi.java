package com.asking91.app.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wxwang on 2016/10/31.
 */

public interface BasePacketApi {

    /**
     *获取科目
     * @param
     * @return
     */
    @POST("learn/getSubjectCacaLogByUserId")
    Observable<ResponseBody> getSubjectCacaLogList();

    /**
     * 获取版本信息
     *
     * @param
     * @return
     */
    @GET("learn/{subjectCatalog}/version")
    Observable<Object> getCourseList(@Path("subjectCatalog") String subjectCatalog);

    /**获取课本年级*/
    @POST("learn/version/{versionId}/level")
    Observable<Object> getClassVersionList(@Path("versionId") String versionId);

    /**获取章节目录*/
    @POST("lean/version/level/{versionLevelId}/knowledge")
    Observable<Object> getKnowledgeList(@Path("versionLevelId") String versionLevelId);

    /**获取章节目录--x新*/
    @POST("coach/version/level/{classLevelId}/knowledgeClassic")
    Observable<Object> getKnowledgeClassicList(@Path("classLevelId") String classLevelId);

    /**获取知识点*/
    @POST("lean/version/{versionLevelId}/knowledge/{id}/attr")
    Observable<Object> getKnowledge(@Path("versionLevelId") String versionLevelId,@Path("id") String id);

    /**获取知识点详情*/
    @POST("lean/version/{versionLevelId}/knowledge/{id}/kind")
    Observable<Object> getKnowledgeKind(@Path("versionLevelId") String versionLevelId,
                                        @Path("id") String id,
                                        @Query("start") int start,
                                        @Query("limit") int limit);
    /**获取同类题库*/
    @POST("M/freeStudy/{kindId}/like/{subjectId}")
    Observable<Object> getSameKnowledgeKind(@Path("kindId") String versionLevelId,@Path("subjectId") String subjectId);

    /**收藏*/
    @POST("space/collection/save")
    Observable<Object> saveCollection(@Query("type") int type,@Query("tille") String title,
                                      @Query("objId") String tipId,@Query("km") String km);

    /**检查收藏*/
    @POST("space/collection/isCollected")
    Observable<Object> checkCollection(@Query("type") int type,@Query("id") String id);

    /**评论*/
    @POST("review/add")
    Observable<Object> saveReview(@Query("comment") String comment, @Query("tipId") String tipId);

    /**评论列表*/
    @POST("review/list")
    Observable<Object> getReviewList(@Query("tipId") String tipId,@Query("start") int start,
                                     @Query("limit") int limit);

    /**获取知识点详情_搜知识入口过来的*/
    @POST("lean/knowledge/{tip_id}/attr")
    Observable<Object> getKnowledgeDetail(@Path("tip_id") String tip_id);
}
