package com.asking91.app.ui.search.subject;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by zqshen on 2016/11/24.
 */

public interface SubjectDetailContract {

    interface Model extends BaseModel{
        // 搜知识点
        Observable<ResponseBody> modelSearchKnowledge(String subjectCatalog, String q);
        // 搜題目
        Observable<ResponseBody> modelSearchSubject(String subjectCatalog,Map<String, RequestBody> params);
        // 获取题目详情
        Observable<ResponseBody> modelSubjectDetail(String subjectCatalog, String subId );
        // 添加收藏
        Observable<ResponseBody> saveCollection(int type, String title,String objId);
        // 检查收藏状态
        Observable<ResponseBody> checkCollection(int type,String id);
    }
    interface View extends BaseView{
        void onRequestSuccess(int type, ResponseBody responseBody);
        void onSuccess(String methodName, String string);
        void onInternetError(String methodName);
        void onError(int type);

    }
    abstract  class Presenter extends BasePresenter<Model,View>{
        // 搜知识点
        public abstract void presenterSearchKnowledge(int requestType, String subjectCatalog,String q );
        // 搜題目
        public abstract  void presenterSearchSubject(int type, String subjectCatalog, String q, int start, int limit);
        // 获取题目详情
        public abstract  void presenterSubjectDetail(int type, String subjectCatalog, String subId);
        // 添加收藏
        public abstract void  saveCollection(int requestType, int type, String title,String objId);
        // 检查收藏状态
        public abstract void  checkCollection(int requestType, int type,String id);
    }

}
