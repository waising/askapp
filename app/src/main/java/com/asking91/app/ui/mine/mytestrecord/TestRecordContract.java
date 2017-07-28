package com.asking91.app.ui.mine.mytestrecord;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BaseMoreView;
import com.asking91.app.mvpframe.BasePresenter;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by zqshen on 2016/11/24.
 */

public interface TestRecordContract {

    interface Model extends BaseModel {
        // 获取阿思币消费记录
        Observable<ResponseBody> modelAskMoneyRecords(int start, int limit);

        // 获取检测记录
        Observable<ResponseBody> modelTestRecord(String subjectCatalog, int start, int limit);

        // 获取我的笔记
        Observable<ResponseBody> modelMyNote(String ticket, int start, int limit);

        // 修改我的笔记
        Observable<ResponseBody> modelAlterMyNote(String content, String id);

        // 新建我的笔记
        Observable<ResponseBody> modelCreateMyNote(String content);

        // 删除我的笔记
        Observable<ResponseBody> modelDelMyNote(String ticket, String id);

        // 获取购买记录
        Observable<ResponseBody> modelMyBuyRecords(String ticket, int start, int limit);

        // 删除购买记录
        Observable<ResponseBody> modelDelMyBuyRecords(String orderId);

        // 获取错题本
        Observable<ResponseBody> modelMyWrongTopic(String subjectCatalog, Map<String, RequestBody> params);

        // 获取错题教材版本
        Observable<ResponseBody> modelMyWrongVersions(String subjectCatalog, String type);

        // 获取错题年级
        Observable<ResponseBody> modelMyWrongGrade(String versionId, String type);

        // 获取错题本解析
        Observable<ResponseBody> modelMyWrongTopicAnalysis(String subjectCatalog, String subjectId);

        // 请求我的收藏
        Observable<ResponseBody> modelMyFavor(int requestType, String ticket, int type, int start, int limit);

        // 删除我的收藏
        Observable<ResponseBody> modelDelMyFavor(int requestType, String ticket, String id);

        // 请求我的收藏里的题目详情和解析
        Observable<ResponseBody> modelGetMyFavSubjectDetail(String km, String objId);

        Observable<ResponseBody> convertTabLClassic(String subjectCatalog, String subjectId);

        Observable<ResponseBody> studentinfo(String username);

    }

    interface View extends BaseMoreView<ResponseBody> {
        void onRequestSuccess(int type, ResponseBody responseBody);
        void onRequestSuccess(ResponseBody responseBody);
        void onInternetError(String methodName);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void presenterAskMoneyRecords(int start, int limit); // 获取阿思币消费记录

        public abstract void presenterTestRecord(int type, String subjectCatalog, int start, int limit); // 获取检测记录

        public abstract void presenterMyNote(String ticket, int start, int limit); // 获取我的笔记

        public abstract void presenterCreateMyNote(String content); // 新建我的笔记

        public abstract void presenterAlterMyNote(String content, String id); // 修改我的笔记

        public abstract void presenterDelMyNote(String ticket, String id); // 删除我的笔记

        public abstract void presenterMyBuyRecords(String ticket, int start, int limit); // 获取购买记录

        public abstract void presenterDelMyBuyRecords(String orderId); // 删除购买记录

        public abstract void modelMyWrongTopic(final int requestType, String subjectCatalog,String postStr); // 获取错题本

        public abstract void presenterMyWrongVersions(int requestType, String subjectCatalog, String type); // 获取错题教材版本

        public abstract void presenterMyWrongGrade(int requestType, String versionId, String type); // 获取错题年级

        public abstract void presenterMyWrongTopicAnalysis(String subjectCatalog, String subjectId); // 获取错题本解析

        public abstract void presenterMyFavor(int requestType, String ticket, int type, int start, int limit); // 请求我的收藏

        public abstract void presenterDelMyFavor(int requestType, String ticket, String id); // 删除我的收藏

        public abstract void presenterGetMyFavSubjectDetail(int requestType, String km, String objId); // 请求我的收藏里的题目详情和解析
    }

}
