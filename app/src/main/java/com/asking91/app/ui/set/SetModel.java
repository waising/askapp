package com.asking91.app.ui.set;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxiao on 2016/12/7.
 */

public class SetModel implements BaseModel {
    public Observable<ResponseBody> logout(){
        return Networks.getInstance().getUserApi().loginOut().compose(RxSchedulers.<ResponseBody>io_main());
    };

    public Observable<ResponseBody> logout2(){
        return Networks.getInstance().getUserApi().loginOut2().compose(RxSchedulers.<ResponseBody>io_main());
    };

    // 更新个人信息
    public Observable<ResponseBody> updateUser( String ticket, String name, String nickName, String sex,
                                               String birthday, String regionName,
                                               String regionCode, String schoolName,
                                               String remark, String area,
                                               String levelId, String classId,String avatar){
        return Networks.getInstance().getUserApi().updateUser( ticket,name, nickName, sex, birthday, regionName, regionCode, schoolName,
                remark, area, levelId, classId,avatar).compose(RxSchedulers.<ResponseBody>io_main());
    };

    // 获取学校
    public Observable<ResponseBody> modelGetSchoolInfo( String regionCode){
        return Networks.getInstance().getUserApi().getSchoolInfo(regionCode).compose(RxSchedulers.<ResponseBody>io_main());
    };

    public Observable<ResponseBody> updateApk(){
        return Networks.getInstance().getUserApi().updateAPKUrl().compose(RxSchedulers.<ResponseBody>io_main());
    };

    public Observable<ResponseBody> qiniutoken(){
        return Networks.getInstance().getOtoApi().qiniutoken()
                .compose(RxSchedulers.<ResponseBody>io_main());
    };
}
