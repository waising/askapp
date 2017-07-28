package com.asking91.app.ui.basepacket.choose;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import rx.Observable;

/**
 *
 */
public interface ChooseContract {

    interface Model extends BaseModel {
        Observable<Object> getCourseList(String catalog);
        Observable<Object> getClassVersionList(String versionId);
        Observable<Object> getKnowledgeList(String versionLevelId);
        Observable<Object> getKnowledgeClassicList(String versionLevelId);
    }

    interface View extends BaseView {
        void onSuccess(int type,Object obj);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getCourseList(String catalog);
        public abstract void getClassVersionList(String versionId);
        public abstract void getKnowledgeList(String versionLevelId);
        public abstract void getKnowledgeClassicList(String versionLevelId);
    }

}
