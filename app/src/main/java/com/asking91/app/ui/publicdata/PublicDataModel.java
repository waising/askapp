package com.asking91.app.ui.publicdata;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;
import com.asking91.app.ui.onlinetest.topic.OnlineTestTopicContract;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxwang on 2016/11/14.
 */
public class PublicDataModel implements PublicDataContract.Model {

    @Override
    public Observable<ResponseBody> saveNode(String content) {
        return Networks.getInstance().getPublicDataApi()
                .saveNode(content).compose(RxSchedulers.<ResponseBody>io_main());
    }
}
