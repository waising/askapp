package com.asking91.app.ui.basepacket.details;

import com.asking91.app.global.BasePacketConstant;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.util.CommonUtil;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wxwang on 2016/11/4.
 */
public class KnowledgeDetailPresenter extends KnowledgeDetailContract.Presenter {

    public Observer<Object> getMoreListObserver(final int pageIndex) {
        return new Observer<Object>() {
            @Override
            public void onCompleted() {
                mView.onRequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                mView.onRequestError(CommonUtil.getRequestEntity(e));
            }

            @Override
            public void onNext(Object obj) {
                if (pageIndex == Constants.PAGE_START) {
                    mView.onRefreshData(obj);
                } else {
                    mView.onLoadMoreData(obj);
                }
            }
        };
    }
    @Override
    public void getKnowledge(String versionLevelId, String id) {
        mRxManager.add(mModel.getKnowledge(versionLevelId,id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object obj) {
                        mView.onSuccess(BasePacketConstant.KnowledgeDetail.GET_KNOWEDGE,obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }

    @Override
    public void getKnowledgeDetail(String tip_id) {
        mRxManager.add(mModel.getKnowledgeDetail(tip_id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object obj) {
                        //mView.onSuccess(BasePacketConstant.KnowledgeDetail.GET_KNOWEDGE,obj);
                        mView.onRefreshData(obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }

    @Override
    public void getKnowledgeKind(String versionLevelId, String id,int start,int limit) {
        mRxManager.add(mModel.getKnowledgeKind(versionLevelId,id,start,limit)
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribe(getMoreListObserver(start)));

    }

    @Override
    public void getSameKnowledgeKind(String kindId, String subjectId) {
        mRxManager.add(mModel.getSameKnowledgeKind(kindId,subjectId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object obj) {
                        mView.onSuccess(BasePacketConstant.KnowledgeType.GET_SAME_KNOWLEDGEKIND,obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }

    @Override
    public void saveCollection(int type, String title, String tipId, String km) {
        mRxManager.add(mModel.saveCollection(type,title,tipId,km)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object obj) {
                        mView.onSuccess(BasePacketConstant.KnowledgeDetail.SAVE_COLLECTION,obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onError(BasePacketConstant.KnowledgeDetail.CHECK_COLLECTION);
//                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }

    @Override
    public void checkCollection(int type, String id) {
        mRxManager.add(mModel.checkCollection(type,id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object obj) {
                        mView.onSuccess(BasePacketConstant.KnowledgeDetail.CHECK_COLLECTION,obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onError(BasePacketConstant.KnowledgeDetail.CHECK_COLLECTION);
//                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }

    @Override
    public void deleteCollection(String ticket, String id) {
        mRxManager.add(mModel.deleteCollection(ticket,id)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new BaseSubscriber(super.mContext) {
                            @Override
                            public void call() {
                                super.call();
                                mView.onRequestStart();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBody>() {
                            @Override
                            public void call(ResponseBody body) {
                                mView.onDeleteCollect(body);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.onError(BasePacketConstant.KnowledgeDetail.DELETE_KNOWEDGE);
//                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                            }
                        })

        );
    }
}
