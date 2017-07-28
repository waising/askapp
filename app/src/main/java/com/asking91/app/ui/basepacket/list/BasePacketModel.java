package com.asking91.app.ui.basepacket.list;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 *
 */
public class BasePacketModel implements BasePacketContract.Model {


    @Override
    public Observable<ResponseBody> getSubjectCacaLogList() {
        return Networks.getInstance().getBasePacketApi()
                .getSubjectCacaLogList().compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> checkUserInfo() {
        return Networks.getInstance().getUserApi()
                .checkUserInfo().compose(RxSchedulers.<ResponseBody>io_main());
    }
}
