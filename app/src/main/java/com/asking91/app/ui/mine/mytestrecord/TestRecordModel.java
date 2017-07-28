package com.asking91.app.ui.mine.mytestrecord;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by zqshen on 2016/11/24
 */
public class TestRecordModel implements TestRecordContract.Model {

    // 获取阿思币消费记录
    @Override
    public Observable<ResponseBody> modelAskMoneyRecords(int start, int limit) {
        return Networks.getInstance().getPublicDataApi()
                .getAskMoneyRecords(start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 获取检测记录
    @Override
    public Observable<ResponseBody> modelTestRecord(String subjectCatalog, int start, int limit) {
        return Networks.getInstance().getPublicDataApi()
                .getTestRecord(subjectCatalog, start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 获取我的笔记
    @Override
    public Observable<ResponseBody> modelMyNote(String ticket, int start, int limit) {
        return Networks.getInstance().getPublicDataApi()
                .getMyNote(ticket, start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 修改我的笔记
    @Override
    public Observable<ResponseBody> modelAlterMyNote(String content, String id) {
        return Networks.getInstance().getPublicDataApi()
                .alterMyNote(content, id).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 新增笔记
    @Override
    public Observable<ResponseBody> modelCreateMyNote(String content) {
        return Networks.getInstance().getPublicDataApi()
                .createMyNote(content).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 删除我的笔记
    @Override
    public Observable<ResponseBody> modelDelMyNote(String ticket, String id) {
        return Networks.getInstance().getPublicDataApi()
                .delMyNote(ticket, id).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 获取购买记录
    @Override
    public Observable<ResponseBody> modelMyBuyRecords(String ticket, int start, int limit) {
        return Networks.getInstance().getPublicDataApi()
                .getMyBuyRecords(ticket, start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 删除购买记录
    @Override
    public Observable<ResponseBody> modelDelMyBuyRecords(String orderId) {
        return Networks.getInstance().getPublicDataApi()
                .delMyBuyRecords(orderId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 获取错题本
    @Override
    public Observable<ResponseBody> modelMyWrongTopic(String subjectCatalog, Map<String, RequestBody> params) {
        return Networks.getInstance().getPublicDataApi()
                .getMyWrongTopic(subjectCatalog, params).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 获取错题版本
    @Override
    public Observable<ResponseBody> modelMyWrongVersions(String subjectCatalog, String type) {
        return Networks.getInstance().getPublicDataApi()
                .getMyWrongVersions(subjectCatalog, type).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 获取错题年级
    @Override
    public Observable<ResponseBody> modelMyWrongGrade(String versionId, String type) {
        return Networks.getInstance().getPublicDataApi()
                .getMyWrongGrade(versionId, type).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 获取错题本解析
    @Override
    public Observable<ResponseBody> modelMyWrongTopicAnalysis(String subjectCatalog, String subjectId) {
        return Networks.getInstance().getPublicDataApi()
                .getMyWrongTopicAnalysis(subjectCatalog, subjectId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 请求我的收藏
    @Override
    public Observable<ResponseBody> modelMyFavor(int requestType, String ticket, int type, int start, int limit) {
        return Networks.getInstance().getPublicDataApi()
                .getMyFavor(ticket, type, start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 删除我的收藏
    @Override
    public Observable<ResponseBody> modelDelMyFavor(int requestType, String ticket, String id) {
        return Networks.getInstance().getPublicDataApi()
                .delMyFavor(ticket, id).compose(RxSchedulers.<ResponseBody>io_main());
    }

    // 获取我的收藏里的题目列表及详细解析
    @Override
    public Observable<ResponseBody> modelGetMyFavSubjectDetail(String km, String objId) {
        return Networks.getInstance().getPublicDataApi()
                .getMyFavSubjectDetail(km, objId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    public Observable<ResponseBody> convertTabLClassic(String subjectCatalog, String subjectId) {
        return Networks.getInstance().getPublicDataApi()
                .convertTabLClassic(subjectCatalog, subjectId).compose(RxSchedulers.<ResponseBody>io_main());
    }


    public Observable<ResponseBody> studentinfo(String userName) {
        return Networks.getInstance().getUserApi()
                .studentinfo(userName).compose(RxSchedulers.<ResponseBody>io_main());
    }


}
