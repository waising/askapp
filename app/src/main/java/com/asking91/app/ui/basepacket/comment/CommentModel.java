package com.asking91.app.ui.basepacket.comment;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import rx.Observable;

/**
 * Created by wxwang on 2016/11/4.
 */
public class CommentModel implements CommentContract.Model {

    @Override
    public Observable<Object> saveReview(String comment, String tipId) {
        return Networks.getInstance().getBasePacketApi()
                .saveReview(comment,tipId).compose(RxSchedulers.<Object>io_main());
    }

    @Override
    public Observable<Object> getReviewList(String tipId, int start, int limit) {
        return Networks.getInstance().getBasePacketApi()
                .getReviewList(tipId,start,limit).compose(RxSchedulers.<Object>io_main());
    }
}
