package com.asking91.app.ui.basepacket.details;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxwang on 2016/11/4.
 */
public class KnowledgeDetailModel implements KnowledgeDetailContract.Model {
    @Override
    public Observable<Object> getKnowledge(String versionLevelId, String id) {
        return Networks.getInstance().getBasePacketApi()
                .getKnowledge(versionLevelId,id).compose(RxSchedulers.<Object>io_main());
    }

    @Override
    public Observable<Object> getKnowledgeDetail(String tip_id) {
        return Networks.getInstance().getBasePacketApi()
                .getKnowledgeDetail(tip_id).compose(RxSchedulers.<Object>io_main());
    }

    @Override
    public Observable<Object> getKnowledgeKind(String versionLevelId, String id,int start,int limit) {
        return Networks.getInstance().getBasePacketApi()
                .getKnowledgeKind(versionLevelId,id,start,limit).compose(RxSchedulers.<Object>io_main());
    }

    @Override
    public Observable<Object> getSameKnowledgeKind(String kindId, String subjectId) {
        return Networks.getInstance().getBasePacketApi()
                .getSameKnowledgeKind(kindId,subjectId).compose(RxSchedulers.<Object>io_main());
    }

    @Override
    public Observable<Object> saveCollection(int type, String title, String tipId, String km) {
        return Networks.getInstance().getBasePacketApi()
                .saveCollection(type,title,tipId,km).compose(RxSchedulers.<Object>io_main());
    }

    @Override
    public Observable<Object> checkCollection(int type, String id) {
        return Networks.getInstance().getBasePacketApi()
                .checkCollection(type,id).compose(RxSchedulers.<Object>io_main());
    }

    @Override
    public Observable<ResponseBody> deleteCollection(String ticket, String id) {
        return Networks.getInstance().getPublicDataApi()
        .delMyFavor(ticket,id).compose(RxSchedulers.<ResponseBody>io_main());
    }

}
