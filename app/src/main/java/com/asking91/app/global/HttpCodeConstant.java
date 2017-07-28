package com.asking91.app.global;

import com.asking91.app.entity.RequestEntity;

/**
 * Created by wxwang on 2016/10/31.
 */

public class HttpCodeConstant {


    public final static int TIME_OUT = 999;
    public final static int CONNECT_FAIL = 1000;//连接失败

    public final static int CONNECT_404 = 404;//
    public final static int CONNECT_401= 401;//
    public final static int CONNECT_502= 502;//

    public static final int HTTP_NO_NETWORK = 999;//无网络标识
    public static final int APP_ERROR = 99999;//未知错误

    public static String getErrorMsg(RequestEntity requestEntity){
        String errMsg = "";
        switch (requestEntity.getCode()){
            case CONNECT_404:
            case CONNECT_502:
                errMsg = "服务接口访问失败";
                break;
            case CONNECT_401:
                errMsg = "未授权的访问";
                break;
            case TIME_OUT:
                errMsg = "访问服务器超时";
                break;
            case CONNECT_FAIL:
                errMsg = "服务接口访问失败";
                break;
            case APP_ERROR:
                //errMsg = "未知错误";
                break;
        }

        return errMsg;
    }
}
