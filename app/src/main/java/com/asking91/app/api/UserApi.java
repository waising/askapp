package com.asking91.app.api;

import com.asking91.app.BuildConfig;

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
 *
 */
public interface UserApi {


    //-------------------登录注册---------------------------

    /**
     * 登录
     *
     * @param userName
     * @param pwd
     * @return
     */
    @POST(BuildConfig.API_SSO_URL + "sso/applogin")
    Observable<Object> ssoLogin(@Query("username") String userName, @Query("password") String pwd);

    /**
     * 获取注册验证码
     *
     * @param mobile
     * @return
     */
    @POST(BuildConfig.API_SSO_URL + "pass/register/requestMessage/mobile")
    Observable<Object> getYZM(@Query("mobile") String mobile);

    /**
     * 注册
     *
     * @return
     */
    @POST(BuildConfig.API_SSO_URL + "pass/register/appSave2")
    Observable<Object> register(@Query("mobile") String mobile, @Query("passWord") String passWord,
                                @Query("verifyCode") String verifyCode,
                                @Query("inviteCode") String inviteCode);

    /**
     * 退出登录
     *
     * @return
     */
    @POST(BuildConfig.API_SSO_URL + "service/user/loginOut")
    Observable<ResponseBody> loginOut();


    @POST(BuildConfig.API_URL + "service/user/loginOut")
    Observable<ResponseBody> loginOut2();

    /**
     * 忘记密码
     *
     * @return
     */
    @POST(BuildConfig.API_SSO_URL + "pass/resetpw/appsave")
    Observable<Object> resetPass(@Query("mobile") String mobile, @Query("password") String password, @Query("verifyCode") String verifyCode);

    /**
     * 获取忘记密码的验证码
     *
     * @param mobile
     * @return
     */
    @POST(BuildConfig.API_SSO_URL + "modify/requestMessage/mobile")
    Observable<Object> getResetPassYZM(@Query("mobile") String mobile);

    /**
     * 获取用户头像
     *
     * @return
     */
    @POST("user/getUser/{id}")
    Observable<ResponseBody> getAvatar(@Path("id") String id);

    /**
     * 获取用户信息
     *
     * @return
     */
    @POST(BuildConfig.API_SSO_URL + "user/getAppUser")
    Observable<ResponseBody> getAppUser();

    /**
     * 获取省份数据
     * regionCode:0 获取省份数据，传入省份ID获取城市数据，
     * 传入城市ID获得城市对应区县数据
     */
    @POST(BuildConfig.API_SSO_URL + "getRegionInfo")
    Observable<ResponseBody> getRegionInfo(@Query("regionCode") String regionCode);

    /*获取学校数据*/
    @POST(BuildConfig.API_SSO_URL + "getSchoolInfo")
    Observable<ResponseBody> getSchoolInfo(@Query("regionCode") String regionCode);

    /**
     * 修改用户信息
     * {
     * "ticket":"TK-0773bee7-fea3-43bc-b4e1-1d22b818a8cd3790312168",
     * …（需要修改的信息key:value）
     * region_code--只要县的region_code
     * region_name--学校所在地
     * levelId 年级
     * area 居住地
     * classId班级
     * nickName昵称
     * }
     */
    @POST(BuildConfig.API_SSO_URL + "user/updateUser")
    Observable<ResponseBody> updateUser(@Query("ticket") String ticket, @Query("name") String name, @Query("nickName") String nickName, @Query("sex") String sex,
                                        @Query("birthdayStr") String birthday, @Query("region_name") String regionName,
                                        @Query("region_code") String regionCode, @Query("school_name") String schoolName,
                                        @Query("remark") String remark, @Query("area") String area,
                                        @Query("levelId") String levelId, @Query("classId") String classId, @Query("avatar") String avatar);

    //    /**更新接口
    @GET("/commonapi/datametas/search/findByKey?key=AndroidUpdate")
    Observable<ResponseBody> updateAPKUrl();

    /**
     * 检查用户学校信息是否完善
     *
     * @return
     */
    @POST("user/checkUserInfo")
    Observable<ResponseBody> checkUserInfo();

    /**
     * 完善学校信息
     *
     * @param schoolId
     * @param schoolName
     * @param regionCode
     * @param regionName
     * @param name
     * @return
     */
    @POST("user/completeUser")
    Observable<ResponseBody> completeUserSchool(@Query("school_id") String schoolId,
                                                @Query("school_name") String schoolName,
                                                @Query("region_code") String regionCode,
                                                @Query("region_name") String regionName,
                                                @Query("name") String name);

    /**
     * 签到
     *
     * @return
     */
    @POST("user/api/v1/sign")
    Observable<ResponseBody> sign();

    /**
     * 判断今日是否已经签到
     *
     * @return
     */
    @POST("user/api/v1/checkTodaySign")
    Observable<ResponseBody> checkTodaySign();

    /**
     * 获取签到的天数和最大连续签到次数
     *
     * @return
     */
    @POST("user/api/v1/dailySign")
    Observable<ResponseBody> dailySign();

    /**
     * 推荐接口
     *
     * @return
     */
    @POST("user/api/v1/invite")
    Observable<ResponseBody> invite();


    /**
     * 1.学生获取信息接口
     *
     * @return
     */
    @Multipart
    @POST(BuildConfig.API_OTO_RE_URL + "order/build")
    Observable<ResponseBody> orderbuild(@PartMap Map<String, RequestBody> params);

    /**
     * 1.学生获取信息接口
     *
     * @param userName
     * @return
     */
    @GET(BuildConfig.API_OTO_RE_URL + "student/info")
    Observable<ResponseBody> studentinfo(@Query("userName") String userName);

    /**
     * 学生轮询题目状态接口
     *
     * @param id
     * @return
     */
    @GET(BuildConfig.API_OTO_RE_URL + "order/state")
    Observable<ResponseBody> orderstate(@Query("id") String id);

    /**
     * 3.学生取消题目
     *
     * @param id
     * @return
     */
    @POST(BuildConfig.API_OTO_RE_URL + "order/cancel")
    Observable<ResponseBody> ordercancel(@Query("id") String id);


    /**
     * 学生往期解答列表接口
     *
     * @param start
     * @param limit
     * @param account
     * @param role
     * @return
     */
    @GET(BuildConfig.API_OTO_RE_URL + "order/history")
    Observable<ResponseBody> orderhistory(@Query("start") String start, @Query("limit") String limit, @Query("account") String account
            , @Query("role") String role);

    /**
     * 学生收藏教师接口
     *
     * @param userName
     * @param account
     * @return
     */
    @POST(BuildConfig.API_OTO_RE_URL + "student/favor")
    Observable<ResponseBody> studentfavor(@Query("userName") String userName, @Query("account") String account);


    /**
     * 学生取消收藏教师接口
     *
     * @param userName
     * @param account
     * @return
     */
    @POST(BuildConfig.API_OTO_RE_URL + "student/unfavor")
    Observable<ResponseBody> studentUnFavor(@Query("userName") String userName, @Query("account") String account);


    /**
     * 6.学生评价老师接口
     *
     * @param id
     * @param reward
     * @param star
     * @param suggest
     * @return
     */
    @POST(BuildConfig.API_OTO_RE_URL + "order/evaluate")
    Observable<ResponseBody> orderevaluate(@Query("id") String id, @Query("reward") String reward, @Query("star") String star
            , @Query("suggest") String suggest);

    /**
     * 4.学生首单退款接口
     *
     * @param id
     * @param userName
     * @return
     */
    @POST(BuildConfig.API_OTO_RE_URL + "order/firstorder")
    Observable<ResponseBody> orderfirstorder(@Query("id") String id);

    /**
     * 5.学生正常结算接口
     *
     * @param id
     * @return
     */
    @POST(BuildConfig.API_OTO_RE_URL + "order/checkbill")
    Observable<ResponseBody> ordercheckbill(@Query("id") String id);


    /**
     * .获取网易云Token
     *
     * @param accid
     * @return
     */
    @GET("cloudapi/nim/token")
    Observable<ResponseBody> getNIMLogin(@Query("accid") String accid);

    @POST(BuildConfig.API_OTO_RE_URL + "student/complain")
    Observable<ResponseBody> studentcomplain(@Query("reason") String reason, @Query("details") String details, @Query("id") String id);


    /* 修改密码请求 */
    @POST("user/changePass")
    Observable<ResponseBody> chagePassword(@Query("oldPass") String oldPass,
                                           @Query("pass") String pass,
                                           @Query("pass1") String pass1
    );


    /**
     * 根据版本和年级查看商品信息
     *
     * @return
     */
    @POST("payment/commodity/findByEdition")
    Observable<ResponseBody> findByEdition(@Query("editionId") String editionId,
                                           @Query("subjectId") String subjectId);

    /**
     * 获取商品列表第一级
     *
     * @return
     */
    @GET("coachapi/superlesson/version/{subject}/{grade}")
    Observable<ResponseBody> findBySuperlesson(@Path("subject") String subject,
                                               @Path("grade") String grade);


    /**
     * 获取商品列表第一级
     *
     * @return
     */
    @POST("productapi/product/courseType/findTreeListWithAllCourse")
    Observable<ResponseBody> SynchronousCourse(@Query("productId") String productId);


    /**
     * 我的课程
     *
     * @return
     */
    @GET("userreact/userCourse/findByPage")
    Observable<ResponseBody> myCourse(@Query("start") int start,
                                      @Query("limit") int limit);

    /**
     * 保存进度
     *
     * @param commodityId
     * @param schedulePercent
     * @param scheduleTitle
     * @param scheduleId
     * @param scheduleContent
     * @return
     */
    @POST("userreact/userCourse/updateWithSchedule")
    Observable<ResponseBody> saveSchedule(@Query("commodityId") String commodityId,
                                          @Query("schedulePercent") double schedulePercent,
                                          @Query("scheduleTitle") String scheduleTitle,
                                          @Query("scheduleId") String scheduleId,
                                          @Query("scheduleContent") String scheduleContent
    );

    /**
     * 领取注册后优惠卷
     *
     * @return
     */
    @GET("marketapi/userevent/register")
    Observable<ResponseBody> couponRegister(
    );

    /**
     * 我的课程pdf和map4的url
     *
     * @return
     */
    @GET("productapi/product/courseManage/findByCommodityId")
    Observable<ResponseBody> myCoursePdfAndUrl(@Query("commodityId") String commodityId
    );


    /**
     * 获取某个商品可使用的优惠卷
     *
     * @return
     */
    @GET("marketapi/userevent/commodity/{commodityId}")
    Observable<ResponseBody> goodsCoupon(@Path("commodityId") String commodityId
    );


    /**
     * 提交错题
     *
     * @return
     */
    @POST("space/errorCollection/{subjectCatalog}/subject")
    Observable<ResponseBody> submitError(
            @Path("subjectCatalog") String subjectCatalog,
            @Query("subjectId") String subjectId,
            @Query("userAnswer") String userAnswer

    );
}
