package com.asking91.app.ui.basepacket.details;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BaseMoreView;
import com.asking91.app.mvpframe.BasePresenter;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxwang on 2016/11/4.
 */
public interface KnowledgeDetailContract  {
    interface Model extends BaseModel {
        Observable<Object> getKnowledgeDetail(String tip_id);
        Observable<Object> getKnowledge(String versionLevelId,String id);
        Observable<Object> getKnowledgeKind(String versionLevelId,String id,int start,int limit);
        Observable<Object> getSameKnowledgeKind(String kindId,String subjectId);
        Observable<Object> saveCollection(int type, String title,String tipId,String km);
        Observable<Object> checkCollection(int type,String id);
        Observable<ResponseBody> deleteCollection(String ticket, String id);
    }

    interface View extends BaseMoreView<Object> {
        void onSuccess(int type,Object obj);
        void onDeleteCollect(ResponseBody body);
        void onError(int type);
    }

    abstract class Presenter extends BasePresenter<KnowledgeDetailContract.Model, KnowledgeDetailContract.View> {
        public abstract void getKnowledgeDetail(String tip_id);
        public abstract void getKnowledge(String versionLevelId,String id);
        public abstract void getKnowledgeKind(String versionLevelId,String id,int start,int limit);
        public abstract void getSameKnowledgeKind(String kindId,String subjectId);
        public abstract void saveCollection(int  type, String title,String tipId,String km);
        public abstract void checkCollection(int type,String id);
        public abstract void  deleteCollection(String ticket, String id);
    }
}
