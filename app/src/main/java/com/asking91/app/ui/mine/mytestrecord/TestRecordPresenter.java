package com.asking91.app.ui.mine.mytestrecord;


import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.util.CommonUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.asking91.app.util.CommonUtil.getRequestBody;

/**
 * Created by zqshen on 2016/11/24
 */
public class TestRecordPresenter extends TestRecordContract.Presenter {

    public Observer<ResponseBody> getListObserver(final int pageIndex) {
        return new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {
                mView.onRequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                mView.onRequestError(CommonUtil.getRequestEntity(e));
            }

            @Override
            public void onNext(ResponseBody body) {
                if (pageIndex == Constants.PAGE_START) {
                    mView.onRefreshData(body);
                } else {
                    mView.onLoadMoreData(body);
                }
            }
        };
    }

    // 获取阿思币消费记录
    @Override
    public void presenterAskMoneyRecords(int start, int limit) {
        mRxManager.add(mModel.modelAskMoneyRecords(start, limit)
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                }).subscribe(getListObserver(start))

        );
    }

    // 获取检测记录
    @Override
    public void presenterTestRecord(final int type, String subjectCatalog, int start, int limit) {
        mRxManager.add(mModel.modelTestRecord(subjectCatalog, start, limit)
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
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 获取我的笔记
    @Override
    public void presenterMyNote(String ticket, int start, int limit) {
        mRxManager.add(mModel.modelMyNote(ticket, start, limit)
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
                                   mView.onRequestSuccess(responseBody);
                                   // mView.onRefreshData(responseBody);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 新建（保存）笔记
    @Override
    public void presenterCreateMyNote(String content) {
        mRxManager.add(mModel.modelCreateMyNote(content)
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
                                   mView.onRequestSuccess(responseBody);
                                   // mView.onRefreshData(responseBody);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 修改我的笔记
    @Override
    public void presenterAlterMyNote(String content, String id) {
        mRxManager.add(mModel.modelAlterMyNote(content, id)
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
                                   mView.onRequestSuccess(responseBody);
                                   // mView.onRefreshData(responseBody);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 删除笔记请求
    @Override
    public void presenterDelMyNote(String ticket, String id) {
        mRxManager.add(mModel.modelDelMyNote(ticket, id)
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
                                   //mView.onRequestSuccess(responseBody);
                                   mView.onRefreshData(responseBody);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 获取购买记录
    @Override
    public void presenterMyBuyRecords(String ticket, int start, int limit) {
        mRxManager.add(mModel.modelMyBuyRecords(ticket, start, limit)
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
                                   mView.onRequestSuccess(responseBody);
                                   // mView.onRefreshData(responseBody);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 删除购买记录
    @Override
    public void presenterDelMyBuyRecords(String orderId) {
        mRxManager.add(mModel.modelDelMyBuyRecords(orderId)
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
                                   mView.onRequestSuccess(responseBody);
                                   // mView.onRefreshData(responseBody);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 获取错题本
    @Override
    public void modelMyWrongTopic(final int requestType, String subjectCatalog,String postStr) {
        Map<String, RequestBody> params = new HashMap<>();
        params.put("post_str", getRequestBody(postStr));
        mRxManager.add(mModel.modelMyWrongTopic(subjectCatalog, params)
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
                                   // mView.onRefreshData(responseBody);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 获取错题版本
    @Override
    public void presenterMyWrongVersions(final int requestType, String subjectCatalog, final String type) {
        mRxManager.add(mModel.modelMyWrongVersions(subjectCatalog, type)
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
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 获取错题年级
    @Override
    public void presenterMyWrongGrade(final int requestType, String versionId, String type) {
        mRxManager.add(mModel.modelMyWrongGrade(versionId, type)
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
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 获取错题本解析
    @Override
    public void presenterMyWrongTopicAnalysis(String subjectCatalog, String subjectId) {
        mRxManager.add(mModel.modelMyWrongTopicAnalysis(subjectCatalog, subjectId)
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
                                   mView.onRefreshData(responseBody);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 获取我的收藏
    @Override
    public void presenterMyFavor(final int requestType, String ticket, final int type, int start, int limit) {
        mRxManager.add(mModel.modelMyFavor(requestType, ticket, type, start, limit)
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
                                   if(requestType == 333){
                                       mView.onRefreshData(responseBody);
                                   }else{
                                       mView.onRequestSuccess(requestType, responseBody);
                                   }
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 删除我的收藏
    public void presenterDelMyFavor(final int requestType, String ticket, String id) {
        mRxManager.add(mModel.modelDelMyFavor(requestType, ticket, id)
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
                                   // mView.onRefreshData(responseBody);
                                   mView.onRequestSuccess(requestType, responseBody);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    // 获取我的收藏里的题目列表及详细解析
    @Override
    public void presenterGetMyFavSubjectDetail(final int requestType, String km, String objId) {
        mRxManager.add(mModel.modelGetMyFavSubjectDetail(km, objId )
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
                                   // mView.onRefreshData(responseBody);
                                   mView.onRequestSuccess(requestType, responseBody);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                                   mView.onInternetError(throwable.toString());
                               }
                           }
                )
        );
    }

    public void convertTabLClassic(String subjectCatalog, String subjectId, ApiRequestListener mListener) {
        baseReqStr(mModel.convertTabLClassic(subjectCatalog, subjectId), mListener);
    }


    public void studentinfo(final Context context, String userName, final ApiRequestListener mListener) {
        mRxManager.add(mModel.studentinfo(userName)
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
                        resSuccess(context, "content", body, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }
    private void resSuccess(Context context, String valueKey, ResponseBody obj, ApiRequestListener mListener) {
        try {
            String resStr = obj.string();
            ArrayMap<String, String> map = parseObject(resStr, new TypeReference<ArrayMap<String, String>>() {
            });
            String flag = String.valueOf(map.get("flag"));
            if (TextUtils.equals("0", flag)) {
                if (!TextUtils.isEmpty(valueKey)) {
                    String result = String.valueOf(map.get(valueKey));
                    mListener.onResultSuccess(result);
                } else {
                    mListener.onResultSuccess(map);
                }
            } else {
                Toast.makeText(context, String.valueOf(map.get("msg")), Toast.LENGTH_SHORT).show();
                mListener.onResultFail();
            }
        } catch (Exception e) {
            mListener.onResultFail();
            e.printStackTrace();
        }
    }

}