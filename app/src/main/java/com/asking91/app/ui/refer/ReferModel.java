package com.asking91.app.ui.refer;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;
import com.asking91.app.ui.basepacket.list.BasePacketContract;
import com.flyco.animation.NewsPaperEnter;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 *
 */
public class ReferModel implements ReferContract.Model {

    @Override
    public Observable<ResponseBody> getReferList(String id, int start, int limit) {
        return Networks.getInstance().getReferApi()
                .getReferList(id,start,limit).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getReferDetail(String referId) {
        return Networks.getInstance().getReferApi()
                .getReferDetail(referId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> saveCollection(int type, String title, String objId) {
        return Networks.getInstance().getPublicDataApi()
                .saveCollection(type,title,objId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> checkCollection(int type, String id) {
        return Networks.getInstance().getPublicDataApi()
                .checkCollection(type,id).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> deleteCollection(String ticket, String id) {
        return Networks.getInstance().getPublicDataApi()
                .delMyFavor(ticket,id).compose(RxSchedulers.<ResponseBody>io_main());
    }

}
