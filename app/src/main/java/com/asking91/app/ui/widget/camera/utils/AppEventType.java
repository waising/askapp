package com.asking91.app.ui.widget.camera.utils;

/**
 * Created by jswang on 2017/2/21.
 */

public class AppEventType {
    public static final int HOME_CAMERA_REQUEST = 0X12;
    public static final int ONLINE_QA_AN_CAMERA_REQUEST = 0X13;
    public static final int ONLINE_QA_AS_CAMERA_REQUEST = 0X14;
    public static final int OTO_QA_AS_CAMERA_REQUEST = 0X15;
    public static final int SEARCH_SUBJECT_CAMERA_REQUEST = 0X16;
    public static final int SEARCH_DETAIL_CAMERA_REQUEST = 0X17;
    public static final int SET_PER_MODI_CAMERA_REQUEST = 0X18;

    public static final int RE_CAMERA_QUS_REQUEST = 0X19;

    public static final int CATA_SELECT_REQUEST = 0X20;
    //
    public static final int CATA_ORIENTATION_REQUEST = 0X21;

    public static final int CATA_SubjectTop_REQUEST = 0X22;

    public static final int UPDATE_APP_REQUEST = 0X23;




    public static final int REF_USER_DATA = 0X24;


    public static final int CLASSIFY_REQUEST = 0X33;

    public static final int LOGIN_SUCCESS = 0X99;
    public static final int LOGIN_OUT = 0X100;

    public static final int SINGLE_PAY_SUCCESS = 0X44;//单套支付
    public static final int ALL_PAY_SUCCESS = 0X68;//全套支付
    public static final int PROGRESS_SUCCESS = 0X55;


    public static final int SYNCHRONOUS_PAY_SUCCESS = 0X66;//同步课程购买成功

    public static final int ASK_COIN_SUCCESS = 0X77;//阿思币购买成功

    public static final int SUBMIT_SUCCESS = 0X34;//评价提交成功

    public static final int REGISTER_SUCCESS = 0X30;//注册成功

    public static final int PRICE = 0X97;//课程购买的价格

    public static final int COURSE_PRICE = 0X102;//课程购买的价格


    public static final int SUPER_CLASSIFY_SELECT = 0X98;


    public static final int COUPON_SUCCESS = 0X101;


    public static final int CHOOSE_MONTH = 0X103;
    public int  type;
    public Object[]  values;



    public  AppEventType(int type, Object...  value){
        this.type = type;
        this.values = value;
    }

    public  AppEventType(int type){
        this.type = type;
    }

}
