package com.asking91.app.ui.search.subject;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
* Created by zqshen on 2016/11/24
*/
public class SubjectDetailModel implements SubjectDetailContract.Model{

    // 搜知识点
    @Override
    public Observable<ResponseBody> modelSearchKnowledge(String subjectCatalog, String q) {
        return Networks.getInstance().getSearchApi().getKnowledge(subjectCatalog,q).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 搜题目
    @Override
    public Observable<ResponseBody> modelSearchSubject(String subjectCatalog,Map<String, RequestBody> params) {
        return Networks.getInstance().getSearchApi()
                .getSubject(subjectCatalog,params).compose(RxSchedulers.<ResponseBody>io_main());
    }
    // 获取题目详情
    @Override
    public Observable<ResponseBody> modelSubjectDetail(String subjectCatalog, String subId) {
        return Networks.getInstance().getSearchApi()
                .getSubjectDetails(subjectCatalog,subId).compose(RxSchedulers.<ResponseBody>io_main());
    }
    // 添加收藏
    @Override
    public Observable<ResponseBody> saveCollection(int type, String title, String objId) {
        return Networks.getInstance().getPublicDataApi()
                .saveCollection(type,title,objId).compose(RxSchedulers.<ResponseBody>io_main());
    }
    // 检查收藏状态
    @Override
    public Observable<ResponseBody> checkCollection(int type, String id) {
        return Networks.getInstance().getPublicDataApi()
                .checkCollection(type,id).compose(RxSchedulers.<ResponseBody>io_main());
    }
}

