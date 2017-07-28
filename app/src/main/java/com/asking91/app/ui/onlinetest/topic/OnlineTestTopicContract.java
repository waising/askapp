package com.asking91.app.ui.onlinetest.topic;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxwang on 2016/11/14.
 */
public interface OnlineTestTopicContract {
    interface Model extends BaseModel {
        Observable<ResponseBody> getVersionList(String subjectCatalog);
        Observable<ResponseBody> getClassLevelList(String versionId);
        Observable<ResponseBody> getKnowledgeClassicList(String classLevelId);
        Observable<ResponseBody> execPaper(String subjectCatalog, Map map);
        Observable<ResponseBody> getPaper(String subjectCatalog, String id);
        Observable<ResponseBody> getAnswer(String subjectCatalog, Map map);
    }

    interface View extends BaseView {
        void onSuccess(int type,ResponseBody body);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getVersionList(String subjectCatalog);
        public abstract void getClassLevelList(String versionId);
        public abstract void getKnowledgeClassicList(String classLevelId);
        public abstract void execPaper(String subjectCatalog, String postStr);
        public abstract void getPaper(String subjectCatalog, String id);
        public abstract void getAnswer(String subjectCatalog, String answerstr);
    }

}
