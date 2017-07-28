package com.asking91.app.ui.basepacket.choose;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import rx.Observable;

/**
 *
 */
public class ChooseModel implements ChooseContract.Model {

    @Override
    public Observable<Object> getCourseList(String catalog) {
        return Networks.getInstance().getBasePacketApi()
                .getCourseList(catalog).compose(RxSchedulers.<Object>io_main());
    }

    @Override
    public Observable<Object> getClassVersionList(String versionId) {
        return Networks.getInstance().getBasePacketApi()
                .getClassVersionList(versionId).compose(RxSchedulers.<Object>io_main());
    }

    @Override
    public Observable<Object> getKnowledgeList(String versionLevelId) {
        return Networks.getInstance().getBasePacketApi()
                .getKnowledgeList(versionLevelId).compose(RxSchedulers.<Object>io_main());
    }


    public Observable<Object> getKnowledgeClassicList(String versionLevelId) {
        return Networks.getInstance().getBasePacketApi()
                .getKnowledgeClassicList(versionLevelId).compose(RxSchedulers.<Object>io_main());
    }
}
