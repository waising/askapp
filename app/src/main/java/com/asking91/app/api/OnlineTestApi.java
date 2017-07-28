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
 * Created by wxwang on 2016/11/14.
 */
public interface OnlineTestApi {
    /**
     * 获取版本
     * @param
     * @return
     */
    @GET("coach/{subjectCatalog}/version")
    Observable<ResponseBody> getVersionList(@Path("subjectCatalog") String subjectCatalog);

    /**获取课本年级*/
    @POST("coach/version/{versionId}/level")
    Observable<ResponseBody> getClassLevelList(@Path("versionId") String versionId);

    /**获取章节目录*/
    @POST("coach/version/level/{versionLevelId}/knowledgeClassic")
    Observable<ResponseBody> getKnowledgeClassicList(@Path("versionLevelId") String classLevelId);

    /**出卷*/
    @Multipart
    @POST("space/paper/{subjectCatalog}/autoAppPost")
    Observable<ResponseBody> execPater(@Path("subjectCatalog") String subjectCatalog,
                                       @PartMap Map<String, RequestBody> params);
    /**检出试卷内容*/
    @POST("space/paper/{subjectCatalog}/app/{id}")
    Observable<ResponseBody> getPaper(@Path("subjectCatalog") String subjectCatalog,
                                         @Path("id") String id);

    /**检出试卷答案*/
    @Multipart
    @POST("space/paper/{subjectCatalog}/answerweb")
    Observable<ResponseBody> getAnswer(@Path("subjectCatalog") String subjectCatalog,
                                      @PartMap Map<String, RequestBody> params);
}
