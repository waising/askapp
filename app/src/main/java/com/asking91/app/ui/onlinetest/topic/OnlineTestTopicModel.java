package com.asking91.app.ui.onlinetest.topic;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by wxwang on 2016/11/14.
 */
public class OnlineTestTopicModel implements OnlineTestTopicContract.Model {

    @Override
    public Observable<ResponseBody> getVersionList(String subjectCatalog) {
        return Networks.getInstance().getOnlineTestApi()
                .getVersionList(subjectCatalog).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getClassLevelList(String versionId) {
        return Networks.getInstance().getOnlineTestApi()
                .getClassLevelList(versionId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getKnowledgeClassicList(String classLevelId) {
        return Networks.getInstance().getOnlineTestApi()
                .getKnowledgeClassicList(classLevelId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> execPaper(String subjectCatalog, Map params) {
        return Networks.getInstance().getOnlineTestApi()
                .execPater(subjectCatalog,params).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getPaper(String subjectCatalog, String id) {
        return Networks.getInstance().getOnlineTestApi()
                .getPaper(subjectCatalog,id).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getAnswer(String subjectCatalog, Map params) {
        return Networks.getInstance().getOnlineTestApi()
                .getAnswer(subjectCatalog,params).compose(RxSchedulers.<ResponseBody>io_main());
    }
}
