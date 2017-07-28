package com.asking91.app.api;

import android.support.v4.util.ArrayMap;

import com.asking91.app.Asking91;
import com.asking91.app.BuildConfig;
import com.asking91.app.global.UserConstant;
import com.asking91.app.interceptor.AuthInterceptor;
import com.asking91.app.util.JLog;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */
public class Networks {

    private static final int DEFAULT_TIMEOUT = 10;

    private static Retrofit retrofit;
    private static String mToken = "";

    public static String getToken() {
        return "".equals(mToken)? UserConstant.getInstance(Asking91.getmInstance().getApplicationContext()).getToken():mToken;
    }

    public static void setToken(String mToken) {
        Networks.mToken = mToken;
    }

    private static Networks mNetworks;

    public static Networks getInstance() {

        if (mNetworks == null) {
            mNetworks = new Networks();
        }
        return mNetworks;
    }

    public Retrofit getRetrofit(){
        return retrofitBuilder();
    }



    //--------------------- 模块api--------------
    //---------------------BEGIN--------------

    private static PublicDataApi publicDataApi;
    private static UserApi mUserApi;

    private static SuperTutorialApi superTutorialApi;

    private static OnlineQAApi onlineQAApi;

    private static SearchApi searchApi;

    private static OtoApi otoApi;

    //基础知识包
    private static BasePacketApi basePacketApi;

    //在线检测
    private static OnlineTestApi onlineTestApi;

    //资讯
    private static ReferApi referApi;

    //支付
    private static PayApi payApi;


    private static CouponApi couponApi;

    //---------------------END--------------

    //------------------------get -----------------

    public PublicDataApi getPublicDataApi() {
        return publicDataApi == null ? configRetrofit(PublicDataApi.class) : publicDataApi;
    }

    public UserApi getUserApi() {
        return mUserApi == null ? configRetrofit(UserApi.class) : mUserApi;
    }

    public SuperTutorialApi getSuperTutorialApi(){
        return superTutorialApi == null ? configRetrofit(SuperTutorialApi.class) : superTutorialApi;
    }

    public OnlineQAApi getOnlineQAApi(){
        return onlineQAApi == null ? configRetrofit(OnlineQAApi.class) : onlineQAApi;
    }

    public SearchApi getSearchApi(){
        return searchApi == null ? configRetrofit(SearchApi.class) : searchApi;
    }

    public OtoApi getOtoApi(){
        return otoApi == null ? configRetrofit(OtoApi.class) : otoApi;
    }

    public BasePacketApi getBasePacketApi(){
        return basePacketApi == null ? configRetrofit(BasePacketApi.class) : basePacketApi;
    }

    public OnlineTestApi getOnlineTestApi() {
        return onlineTestApi == null ? configRetrofit(OnlineTestApi.class) : onlineTestApi;
    }

    public ReferApi getReferApi() {
        return referApi == null ? configRetrofit(ReferApi.class) : referApi;
    }

    public PayApi getPayApi() {
        return payApi == null ? configRetrofit(PayApi.class) : payApi;
    }
    public CouponApi getCouponApi() {
        return couponApi == null ? configRetrofit(CouponApi.class) : couponApi;
    }
    //---------------------基础配置-----------------
    private <T> T configRetrofit(Class<T> service) {
        retrofit = retrofitBuilder();

        return retrofit.create(service);
    }

    private Retrofit retrofitBuilder(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .client(configClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private final ArrayMap<String, List<Cookie>> cookieStore = new ArrayMap<>();

    private OkHttpClient configClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        //token拦截器
//        okHttpClient.addNetworkInterceptor(new TokenInterceptor());
        //为所有请求添加头部 Header 配置的拦截器
        Interceptor headerIntercept = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("X-Client-Platform", "Android");
                builder.addHeader("X-Client-Version", BuildConfig.VERSION_NAME);
                builder.addHeader("X-Client-Build", String.valueOf(BuildConfig.VERSION_CODE));

                builder.removeHeader("Accept");
                builder.addHeader("Accept", "*/*");
                builder.addHeader("Authorization", getToken());
                builder.addHeader("x-requested-with"," x-requested-with");

                Request request = builder.build();

                return chain.proceed(request);
            }
        };

        // Log信息拦截器
        if (BuildConfig.LOG_DEBUG) {
            Interceptor loggingIntercept = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    ResponseBody responseBody = response.body();
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();
                    Charset UTF8 = Charset.forName("UTF-8");
                    JLog.i("REQUEST_URL", request.toString());
                    JLog.logi("REQUEST_JSON", buffer.clone().readString(UTF8));
//                    Object o = buffer.clone().readString(UTF8);
//                    JLog.logi("REQUEST_JSON2", o.toString());

                    return response;
                }
            };
            okHttpClient.addInterceptor(loggingIntercept);
        }

        okHttpClient.dns(OkHttpDns.getInstance(Asking91.getmInstance().getApplicationContext()));

        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.addNetworkInterceptor(headerIntercept);
        okHttpClient.addNetworkInterceptor(new AuthInterceptor());

        //自动管理cookie
        okHttpClient.cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                cookieStore.put(httpUrl.host(), list);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                List<Cookie> cookies = cookieStore.get(httpUrl.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });

        return okHttpClient.build();
    }

}
