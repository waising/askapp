package com.asking91.app.api;

import com.asking91.app.BuildConfig;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wxiao on 2016/10/31.
 * 在线问答
 */

public interface OnlineQAApi {

    //-------------------在线问答-------------------------------------
    @POST("QA/list")
    Observable<ResponseBody> onlineQA(@Query("type") String type, @Query("state") String state, @Query("start") int start, @Query("limit") int limit,
                                @Query("km") String km);
    @POST("QA/list")
    Observable<ResponseBody> onlineQA(@Query("type") String type, @Query("state") String state, @Query("start") int start, @Query("limit") int limit,
                                @Query("km") String km, @Query("levelId") String levelId);

    @POST("QA/list")
    Observable<ResponseBody> onlineQA(@Query("type") String type, @Query("state") String state, @Query("start") int start, @Query("limit") int limit);
    @POST("QA/list")
    Observable<ResponseBody> onlineQA(@Query("type") String type, @Query("start") int start, @Query("limit") int limit);

    @POST("QA/list")
    Observable<ResponseBody> onlineQA(@Body JSONObject object);

    /**1.5.9.单个问题详情*/
    @POST("/QA/app/{id}")
    Observable<ResponseBody> onlineQADetail(@Path("id") String id);

    /**1.5.5.在线提问保存*/
    @POST("QA/space/save")
    Observable<ResponseBody> onlineQASubmit(@Query("km") String km, @Query("levelId") String levelId, @Query("caifu") String caifu, @Query("title") String title, @Query("description") String description);
    /**1.5.5.在线提问保存*/
    @POST("QA/space/save")
    Observable<ResponseBody> onlineQASubmit(@Query("km") String km, @Query("caifu") String caifu, @Query("title") String title, @Query("description") String description);
//请求 URL: http://192.168.9.201:8080/QA/space/myAsk/list
    /**我的问题*/
    @POST("QA/space/myAsk/list")
    Observable<ResponseBody> onlineQAMyAsk(@Query("start") int start, @Query("limit") String limit, @Query("query") String query);

//    请求 URL: http://192.168.9.201:8080/QA/space/myAnswer/list
    /**我的回答*/
    @POST("QA/space/myAnswer/list")
    Observable<ResponseBody> onlineQAMyAnswer(@Query("type") String type, @Query("start") int start, @Query("limit") String limit, @Query("query") String query);
    /**回答问题保存*/
    @POST("QA/space/anser")
    Observable<ResponseBody> onlineQAAnser(@Query("id") String id, @Query("content") String content);
    /**追问*/
    @POST("QA/space/againAsk")
    Observable<ResponseBody> onlineQaAgainAsk(@Query("id") String id, @Query("anserId") String anserId, @Query("html") String html);
    /**追答*/
    @POST("QA/space/againAnswer")
    Observable<ResponseBody> onlineQaAgainAnswer(@Query("id") String id, @Query("anserId") String anserId, @Query("html") String html);
    /**采纳问题*/
    @POST("QA/space/adopt")
    Observable<ResponseBody> onlineQaAdoptAnswer(@Query("id") String id, @Query("anserId") String anserId);

    /**1.5.4.获取在线提问上传照片返回*/
    @Multipart
    @POST("QA/space/appAskPhotoContent")
    Observable<ResponseBody> onlineQASubmitPic(@Part MultipartBody.Part part);

    /**单个问题列表*/
    @POST("QA/ByuserId/app/{id}")
    Observable<ResponseBody> onlineQATalk(@Path("id") String id);
}
