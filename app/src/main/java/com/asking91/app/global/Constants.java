package com.asking91.app.global;

import android.os.Environment;

import java.io.File;

/**
 *
 */
public class Constants {

    public static final String GifHeard = "asset://com.asking91.app/";
    public final static String APP_BASE_PATH = "Asking91";
    /**
     * 该文件夹下存放各种数据初始化，不能在此删除
     */
    public final static String APP_CACHE_PATH = "Asking91/cache/";
    /**
     * 缓存文件，这下面包括：安装包、拍照缓存、灰度照片保存，缓存清理位置
     */
    public final static String APP_CACHE_CACHE_PATH = APP_CACHE_PATH + "cache/";
    /**
     * 缓存灰度照片位置
     */
    public final static String APP_CACHE_IMG_GRAY_PATH = APP_CACHE_CACHE_PATH + "img/gray/";
    /**
     * 拍照保存位置
     */
    public final static String APP_CACHE_IMG_PATH = APP_CACHE_CACHE_PATH + "img/";
    /**
     * 更新包位置
     */
    public final static String APP_CACHE_APK_PATH = APP_CACHE_PATH + "update/";

    /**
     * 更新包位置
     */
    public final static String APP_CACHE_WEB_PATH = APP_CACHE_PATH + "webCache/";



    /**
     * 获取用户头像
     */
    public final static String USER_AVATAR = "https://ssos.91asking.com/znSSO"+ "/user/avatar/";

    public final static String PLATFORM = "Android";

    public static final int PAGE_START = 0;

    public static final String ASK_HOST = "phphub.org";

    public static final String DEEP_LINK_PREFIX = "phphub://";

    public static final String MD_HTML_PREFIX = "<html><head><style type=\"text/css\">html,body{padding:4px 8px 4px 8px;font-family:'sans-serif-light';color:#303030;}h1,h2,h3,h4,h5,h6{font-family:'sans-serif-condensed';}a{color:#388E3C;text-decoration:underline;}img{height:auto;width:325px;margin:auto;}</style></head><body>";

    public static final String SuperTutorialHtmlCss = "<html><head><style type=\"text/css\">body{color:#888888;vertical-align:middle;}</style></head><body>";
    //    public static final String SuperTutorialHtmlCss = "<html><head><style type=\"text/css\"> .over {color:#38c1ff;} body{color:#888888;vertical-align:middle;}</style></head><body onclick=\"javascript:this.className = this.className!='over'?'over':''\">";
    public static final String MD_HTML_SUFFIX = "</body></html>";

    /**
     * 用户注册协议
     */
    public static final String PROTOCOL = "http://7xj9ur.com1.z0.glb.clouddn.com/app/agreement.html";


    /**
     * 初升高pdf下载存储位置
     */
    public final static String APP_JUNIOR_TO_HIGH_PDF_PATH = APP_BASE_PATH+"/DownLoad/";

    public interface Key {
        String TOKEN = "ticket";
        String IS_LOGIN = "is_login";
        String USER_DATA = "user_data";
        String IS_USER_DATA_PERFECT = "is_user_data_perfect";

        String WEB_URL = "web_url";
        String WEB_TITLE = "web_title";
        String WEB_IMAGE_URL = "web_image_url";
        /**
         * 网易Token
         */
        String WYBAIBAN_TOKEN = "wybaiban_token";
        String WYBAIBAN_ACCOUT = "wybaiban_accout";

        String THEME_MODE = "theme_mode";

        String SuperFreeLevelId = "super_free_level_id";
        String SuperFreeknowledgeId = "super_free_knowledge_id";

        String StudyHistory = "StudyHistory";

        String LOGIN_STATE = "login_state";//登录状态
    }

    public interface Theme {
        String Blue = "blue";
        String White = "white";
        String Gray = "gray";
        String Transparent = "transparent";
    }

    public interface Login {
        String UserName = "UserName";
        String Password = "Password";
        String IsRemember = "IsRemember";
    }

    public interface SubjectCatalog {
        String QBXK = "";//全部学科
        String CZSX = "M2";//初中数学
        String CZWL = "P2";//初中物理
        String GZSX = "M3";//高中数学
        String GZWL = "P3";//高中物理
    }

    public interface PublicData {
        int SaveNode = 1;//记笔记
    }


    //收藏类型
    public interface Collect {
        int knowledge = 1;//知识点
        int title = 2;//题目
        int paper = 3;//试卷
        int refer = 4;//教育资讯
        int other = 5;//其他
    }

    public interface Pay {
        int supClass = 2;//超级辅导课
        int basepack = 1;//基础知识包
    }

    public interface activity {
        String toOtherActivity = "TOOTHERACTIVITY";
        String interceptorInvoker = "INTERCEPTOR_INVOKER";
    }

    /**
     * 字符编码
     */
    public static final String encode = "utf-8";
    /**
     * 首页顶部的轮播图片
     */
    public static final String[] main_top_pics = new String[]{"http://oimp5umbz.bkt.clouddn.com/appbanner1.png", "http://oimp5umbz.bkt.clouddn.com/appbanner2.png", "http://7xj9ur.com2.z0.glb.qiniucdn.com/appbanner3.png", "http://oimp5umbz.bkt.clouddn.com/appbanner4.png", "http://oimp5umbz.bkt.clouddn.com/appbanner5.png"};
    public static final String[] main_top_big_pics = new String[]{"http://oimp5umbz.bkt.clouddn.com/appbanner1_1.png", "http://oimp5umbz.bkt.clouddn.com/appbanner2_1.png"};


    public static final String[] main_top_pics_junior_to_high_math = new String[]{"http://oimp5umbz.bkt.clouddn.com/appban_shuxue.png"};
    public static final String[] main_top_big_pics_junior_to_high_math = new String[]{"http://oimp5umbz.bkt.clouddn.com/appban_shuxue_1.png"};

    public static final String[] main_top_pics_junior_to_high_physics = new String[]{"http://oimp5umbz.bkt.clouddn.com/appban_wuli.png"};
    public static final String[] main_top_big_pics_junior_to_high_physic = new String[]{"http://oimp5umbz.bkt.clouddn.com/appban_wuli_1.png"};
    //基础知识包
    public static final String basepacket_info_pic = "http://oimp5umbz.bkt.clouddn.com/basepacketInfo.png";

    public static final String APK_DOWN_URL = "http://oimp5umbz.bkt.clouddn.com/Asking91.apk";


    public static final String APK_TENCENT_DOWN_URL = "http://app.qq.com/#id=detail&appid=1105236441";

    public static String getAppCachePath(String path) {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + path);
        if (file.exists() && !file.isDirectory()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 确认订单支付跳转页面类型
     */
    public interface ConfirmOrder {
        int ask_coin = 0;//阿思币购买
        int synchronous_course = 1;//同步课程购买
        int buy_record = 2;//购买记录
        int single_course = 3;//单套课程购买
        int full_course = 4;//全套课程购买
        int course_buy = 5;//课程购买
    }


    /**
     * 初升高详情页
     */
    public interface JuniorToHighDetail {
        int not_puchased = 0;//未购买
        int puchased = 1;//已购买
        int present = 2;//赠品
        int mycourse = 3;//我的课程
        int junior_to_high = 4;//初升高
    }

    /**
     * 初升高
     */
    public interface JuniorToHigh {
        String math = "KM01";//数学
        String physic = "KM02";//物理
        String all_math = "TC01";//全套数学
        String all_physic = "TC02";//全套物理

    }

}
