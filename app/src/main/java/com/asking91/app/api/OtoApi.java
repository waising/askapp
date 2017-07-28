package com.asking91.app.api;

import com.asking91.app.BuildConfig;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wxiao on 2016/11/11.
 */

public interface OtoApi {

    /**将上传后的题目放入队列*/
    @POST(BuildConfig.API_OTO_URL+"OTO/ask.do")//外网
    Observable<ResponseBody> otoImg(@Query("telephone") String telephone, @Query("action") String action, @Query("text") String text,
                                    @Query("askMoney") int askMoney, @Query("imgUrl") String imgUrl, @Query("subjectCatalog") String subjectCatalog,
                                    @Query("score") int score, @Query("askCoin") int askCoin, @Query("suggest") String suggest, @Query("bind") int bind,
                                    @Query("linkFlag") int linkFlag);
    /**
     * 后台自动登录一对一服务
     * @param telephone
     * @return
     */
    @POST(BuildConfig.API_OTO_URL+"OTO/studentLogin.do")//外网
    Observable<ResponseBody> otoLogin(@Query("telephone") String telephone);

    /**
     * 获取七牛Token
     * @param telephone
     * @return
     */
    @POST(BuildConfig.API_OTO_URL+"OTO/ask.do?action=uploadToken")
    Observable<ResponseBody> otoGetToken(@Query("telephone") String telephone);


    @GET("cloudapi/qiniu/token")
    Observable<ResponseBody> qiniutoken();
}
