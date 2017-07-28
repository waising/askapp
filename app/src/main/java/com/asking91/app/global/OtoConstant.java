package com.asking91.app.global;

/**
 * Created by wxiao on 2016/11/11.
 */

public interface OtoConstant {
    /**学科对应的id*/
    String[] subjectValues = {"M","P"};
    /**年级对应的id*/
    String[] versionTvValues = {"07","08", "09", "10", "11", "12"};
    /**荣联通讯录*/
    String RLTXL_KEY = "aaf98f89524954cc0152580140ca1bc3";
    String RLTXL_TOKEN = "cae43d78c06055ded68b493705896702";
    /**汉王云*/
    String HWY_KEY = "a2f10384-59ca-472f-ad67-2471d7694c53";
    /**七牛上传存放文件的投递至*/
    String QiNiuHead = "http://7xj9ur.com1.z0.glb.clouddn.com/";

    public interface Oto {
        String nimLogin = "NIMLOGIN";//网易云信token
    }
}
