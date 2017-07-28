package com.asking91.app.ui.onlinetest.topic;

import com.asking91.app.global.BasePacketConstant;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.ui.onlinetest.topicask.OnlineTestTopicAskActivity;
import com.asking91.app.ui.onlinetest.topicask.OnlineTestTopicAskResultActivity;
import com.asking91.app.util.CommonUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *在线检测
 */
public class OnlineTestTopicPresenter extends OnlineTestTopicContract.Presenter {

    @Override
    public void getVersionList(String subjectCatalog) {
        mRxManager.add(mModel.getVersionList(subjectCatalog)
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
                        mView.onSuccess(BasePacketConstant.Choose.COURSE_VERSION,body);
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
    public void getClassLevelList(String versionId) {
        mRxManager.add(mModel.getClassLevelList(versionId)
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
                        mView.onSuccess(BasePacketConstant.Choose.CLASS_LEVEL,body);
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
    public void getKnowledgeClassicList(String classLevelId) {
        mRxManager.add(mModel.getKnowledgeClassicList(classLevelId)
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
                        mView.onSuccess(BasePacketConstant.Choose.KNOWLEDGE,body);
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
    public void execPaper(String subjectCatalog, String postStr) {
        Map<String, RequestBody> params = new HashMap<>();
        params.put("post_str",CommonUtil.getRequestBody(postStr));
        mRxManager.add(mModel.execPaper(subjectCatalog,params)
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
                        mView.onSuccess(OnlineTestTopicActivity.EXECPAPER,body);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable,String.valueOf(OnlineTestTopicActivity.EXECPAPER)));
                    }
                })

        );
    }

    @Override
    public void getPaper(String subjectCatalog, String id) {
        mRxManager.add(mModel.getPaper(subjectCatalog,id)
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
                        mView.onSuccess(OnlineTestTopicAskActivity.GETPAPER,body);
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
    public void getAnswer(String subjectCatalog, String answerstr) {
        Map<String, RequestBody> params = new HashMap<>();
        params.put("answerstr",CommonUtil.getRequestBody(answerstr));

        mRxManager.add(mModel.getAnswer(subjectCatalog,params)
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
                        mView.onSuccess(OnlineTestTopicAskResultActivity.GETANSWER,body);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }
}
