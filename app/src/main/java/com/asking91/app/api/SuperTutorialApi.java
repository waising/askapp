package com.asking91.app.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wxiao on 2016/10/31.
 * 超级辅导课
 */

public interface SuperTutorialApi {

    //----------------超级辅导课--------------------------------
    /**获取超级辅导课已购买科目——进入超级辅导课的时候使用*/
    @POST("coach/getSubjectCacaLogByUserId")
    Observable<ResponseBody> getSuperSelect();

    /**获取课本年级*/
    @POST("freeStudyClassic/version/{versionId}/level")
    Observable<ResponseBody> freeStudyVersion(@Path("versionId") String versionId);


//    version/488/knowledge/1089/kind?limit=5&start=0
    /**超级辅导课阿思可博士来讲题*/
    @POST("freeStudyClassic/version/{versionLevelId}/knowledge/{id}/kind")
    Observable<ResponseBody> getSuperFreeCoach(@Path("versionLevelId") String versionLevelId, @Path("id") String id, @Query("start") int start, @Query("limit") int limit);
    /**获取变式题*/
    @POST("freeStudyClassic/subject/subjectMul")
    Observable<ResponseBody> getSubjectMul(@Query("kindId") String kindId, @Query("catalogCode") String catalogCode, @Query("start") int start, @Query("limit") int limit);
    /**
     * 提交变式题练习
     * answerstr:{"subList":[{"id":"3A833818-77DB-44D6-A744-529756BF5FD4","subject_type":{"type_id":"1"},"user_answer":"D"}]}
     * code:M*/
    @POST("learn/subject/freeStudyClassic/like/test")
    Observable<ResponseBody> subject(@Query("answerstr") String answerstr, @Query("code") String code);

    //*****************************已购买*****************************
    /**获取版本信息*/
    @GET("coachapi/superlesson/version/{catalog}/{catalogCode}")
    Observable<ResponseBody> superlesson(@Path("catalog") String catalog
                                                    ,@Path("catalogCode") String catalogCode);

    /**获取课本年级*/
    @GET("coachapi/superlesson/level/{versionId}")
    Observable<ResponseBody> superlessonlevel(@Path("versionId") String versionId);

    /**获取章节目录*/
    @GET("courseapi/synclesson/{versionLevelId}")
    Observable<ResponseBody> superlessontree(@Path("versionLevelId") String versionLevelId);


    /**获取超级辅导课阿思可博士有话说列表*/
    @GET("courseapi/synclesson/{levelId}/{knowledgeId}/{type}")
    Observable<ResponseBody> getSuperBuyFragment1(@Path("levelId") String levelId, @Path("knowledgeId")String knowledgeId, @Path("type") int type);

    /**超级辅导课阿思可博士来讲题*/
    @POST("coach/version/{versionLevelId}/knowledge/{id}/kindClassic")
    Observable<ResponseBody> getSuperBuyCoach(@Path("versionLevelId") String versionLevelId, @Path("id") String id, @Query("start") int start, @Query("limit") int limit);

    /**演练大冲关获取题类
     * subjectCatalog--M3*/
    @GET("courseapi/sprint/{subjectCatalog}/{tipId}")
    Observable<ResponseBody> getSubjectTopic(@Path("subjectCatalog") String subjectCatalog, @Path("tipId") String tipId);

    /**演练大冲关获取题目**/
    @GET("courseapi/sprint/{subjectCatalog}/{tipId}/{topicId}")
    Observable<ResponseBody> getAllSubjectClassic(@Path("subjectCatalog") String subjectCatalog, @Path("tipId") String tipId
            , @Path("topicId") String topicId, @Query("start") int start, @Query("limit") int limit);

    /**演练大冲关提交题目
     * answerstr--{"subList":[{"id":"0FCFA645-7491-49BC-BF0C-02F04E367379","subject_type":{"type_id":"1"},"user_answer":"B"}]}
     * code--M*/
    @POST("coach/subjectClassic/answer")
    Observable<ResponseBody> subjectClassic(@Query("answerstr") String answerstr, @Query("code") String code);

    /**音频播放
     * prefix--1--有话说==2--问答时间==3来讲题（课堂总结没有音频）
     * suffix--获取的音频的内容在列表的顺序+1（第0个及最开头的一个播放音频的suffix=1）*/
    @GET("courseapi/synclesson/voice/{gradeId}/{tipId}/{prefix}/{suffix}")
    Observable<ResponseBody> getVoicePath(@Path("gradeId") String gradeId, @Path("tipId") String tipId, @Path("prefix") int prefix, @Path("suffix") int suffix);


    /**获取章节点*/
    @GET("firstreview/zhangjd/{orgId}")
    Observable<ResponseBody> firstreviewzhangjd(@Path("orgId") String orgId);

    /**获取课时节点*/
    @GET("firstreview/kesjd/{pid}")
    Observable<ResponseBody> firstreviewkesjd(@Path("pid") String pid);

    /**阿思可博士考情分析接口*/
    @GET("firstreview/kaoqfx/{pid}")
    Observable<ResponseBody> firstreviewkaoqfx(@Path("pid") String pid);

    /**实战演练接口*/
    @GET("firstreview/shizyl/{pid}")
    Observable<ResponseBody> firstreviewshizyl(@Path("pid") String pid);

    /**备高考三要求*/
    @GET("firstreview/beigk/{pid}/{index}")
    Observable<ResponseBody> firstreviewbeigk(@Path("pid") String pid,@Path("index")  String index);

    /**典题*/
    @GET("firstreview/diant/{pid}")
    Observable<ResponseBody> firstreviewdiant(@Path("pid") String pid);

    /**获取树节点*/
    @GET("secondreview/tree")
    Observable<ResponseBody> secondreviewtree(@Query("orgId") String orgId);

    /**典题*/
    @GET("secondreview/zhuant")
    Observable<ResponseBody> secondreviewzhuant(@Query("pid") String pid,@Query("field") String field);
}

