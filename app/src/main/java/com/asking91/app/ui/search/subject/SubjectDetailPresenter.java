package com.asking91.app.ui.search.subject;

import com.alibaba.fastjson.JSON;
import com.asking91.app.global.ReferConstant;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.util.CommonUtil;
import com.hanvon.HWCloudManager;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zqshen on 2016/11/24
 */
public class SubjectDetailPresenter extends SubjectDetailContract.Presenter {

    // 搜知识点
    @Override
    public void presenterSearchKnowledge(final int requestType, String subjectCatalog, String q) {
        mRxManager.add(mModel.modelSearchKnowledge(subjectCatalog, q)
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
                    public void call(ResponseBody responseBody) {
                        mView.onRequestSuccess(requestType, responseBody);
                        //mView.onRefreshData(object);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    //获取题目
    @Override
    public void presenterSearchSubject(final int type, String subjectCatalog, String q, int start, int limit) {
        Map<String, RequestBody> params = new HashMap<>();
        params.put("q",CommonUtil.getRequestBody(q));
        params.put("start",CommonUtil.getRequestBody(start+""));
        params.put("limit",CommonUtil.getRequestBody(limit+""));

        mRxManager.add(mModel.modelSearchSubject(subjectCatalog, params)
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
                               public void call(ResponseBody responseBody) {
                                   mView.onRequestSuccess(type, responseBody);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                               }
                           }
                )
        );
    }

    // 获取题目解析
    public void presenterSubjectDetail(final int type, String subjectCatalog, String subId) {
        mRxManager.add(mModel.modelSubjectDetail(subjectCatalog, subId)
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
                               public void call(ResponseBody responseBody) {
                                   mView.onRequestSuccess(type, responseBody);
                                   // mView.onRefreshData(responseBody);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                               }
                           }
                )
        );
    }

    /**
     * 获取汉王云题目解析内容
     *
     * @param picPath
     */
    public void getHwyQuestion(final HWCloudManager hwCloudManagerFormula, final String picPath) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        String resStr = hwCloudManagerFormula.formulaOCRLanguage4Https(picPath);
                        String reDecode = CommonUtil.decode1(resStr);
                        String result = JSON.parseObject(reDecode).getString("result");
                        subscriber.onNext(result);
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String result) {
                                mView.onSuccess("getHwyQuestion-next", result);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.onInternetError("getHwyQuestion");
                            }
                        })
        );
    }

    // 添加收藏
    public void saveCollection(final int requestType, int type, String title, String objId) {
        mRxManager.add(mModel.saveCollection(type, title, objId)
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
                        mView.onRequestSuccess(requestType, body);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestEnd();
                        mView.onError(ReferConstant.Refer.save);
                    }
                })
        );
    }

    // 检查收藏状态
    public void checkCollection(final int requestType, int type, String id) {
        mRxManager.add(mModel.checkCollection(type, id)
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
                        mView.onRequestSuccess(requestType, body);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestEnd();
                        mView.onError(ReferConstant.Refer.check);
                    }
                })
        );
    }


}