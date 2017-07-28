package com.asking91.app.ui.onlineqa;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.mvpframe.rx.RxSchedulers;
import com.asking91.app.util.CommonUtil;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
* Created by wxiao on 2016/10/31
*/

public class OnlineQAPresenterImpl extends OnlineQAContract.Presenter{

    public Observer<ResponseBody> onlineQAonlineQA(final int pageIndex) {
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
            public void onNext(ResponseBody obj) {
                if (pageIndex == 0) {
                    mView.onRefreshData(obj);
                } else {
                    mView.onLoadMoreData(obj);
                }
            }
        };
    }

    @Override
    public void onlineQAonlineQA(String type, String state, int start, int limit, String km, String levelId) {
        mRxManager.add(mModel.onlineQAonlineQA(type, state, start, limit, km, levelId)
                .subscribe(onlineQAonlineQA(start)));}

    @Override
    public void onlineQAonlineQA(String type, String state, int start, int limit, String km) {
        mRxManager.add(mModel.onlineQAonlineQA(type, state, start, limit, km)
                .subscribe(onlineQAonlineQA(start))
        );
    }

    @Override
    public void onlineQAonlineQA(String type, String state, int start, int limit) {
        mRxManager.add(mModel.onlineQAonlineQA(type, state, start, limit)
                        .subscribe(onlineQAonlineQA(start))
        );
    }

    @Override
    public void onlineQAonlineQA(String type, int start, int limit) {
        mRxManager.add(mModel.onlineQAonlineQA(type, start, limit)
                        .subscribe(onlineQAonlineQA(start))
        );
    }

    @Override
    public void onlineQAAnser(String id, String content) {
        mRxManager.add(Networks.getInstance().getOnlineQAApi().onlineQAAnser(id, content)
                .compose(RxSchedulers.<ResponseBody>io_main())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        try {
                            mView.onSuccess("onlineQAAnser", obj.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    public void onlineQADetail(String id) {
        mRxManager.add(Networks.getInstance().getOnlineQAApi().onlineQADetail(id)
                .compose(RxSchedulers.<ResponseBody>io_main())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        mView.onOnlineQAonlineQASuccess(obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    public void onlineQaAgainAsk(String id, String anserId, String html){
        mRxManager.add(mModel.onlineQaAgainAsk(id, anserId, html)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody baseRsqEntity) {
                        try {
                            mView.onSuccess("onlineQaAgainAsk",baseRsqEntity.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }
    public void onlineQaAgainAnswer(String id, String anserId, String html){
        mRxManager.add(mModel.onlineQaAgainAnswer(id, anserId, html)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody baseRsqEntity) {
                        try {
                            mView.onSuccess("onlineQaAgainAnswer",baseRsqEntity.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
    public void onSubmitPicSuccess(MultipartBody.Part part) {
        mRxManager.add(mModel.onSubmitPicSuccess(part)
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
                            public void call(final ResponseBody baseRsqEntity) {
                                try {
                                    mView.onSuccess("onSubmitPicSuccess", baseRsqEntity.string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.onRequestError(CommonUtil.getRequestEntity(throwable));
//                        mView.onError(throwable);
                            }
                        })
        );
    }

    @Override
    public void onlineQaAdoptAnswer(String id, String anserId, final int position) {
        mRxManager.add(mModel.onlineQaAdoptAnswer(id, anserId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody baseRsqEntity) {
                        try {
                            mView.onSuccess("onlineQaAdoptAnswer-"+position,baseRsqEntity.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

//    public void otoGetToken(){
//        mRxManager.add(mModel.otoGetToken()
//                .doOnSubscribe(new BaseSubscriber(super.mContext) {
//                    @Override
//                    public void call() {
//                        super.call();
//                        mView.onRequestStart();
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<ResponseBody>() {
//                    @Override
//                    public void call(ResponseBody baseRsqEntity) {
//                        try {
//                            mView.onSuccess("otoGetToken", baseRsqEntity.string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        CommonUtil.getRequestEntity(throwable,"otoLogin");
//                    }
//                }));
//    }
//
//    public void upload(final String path, final String key, final String token) {
//        initQiNiuUpload();
//        mRxManager.add(Observable.create(new Observable.OnSubscribe<String>() {
//                    @Override
//                    public void call(final Subscriber<? super String> subscriber) {
//                        uploadManager.put(path, key, token, new UpCompletionHandler() {
//                            @Override
//                            public void complete(String keys, ResponseInfo info, JSONObject response) {
//                                if(info.isOK()){
//                                    subscriber.onNext(key);
//                                    subscriber.onCompleted();
//                                }else{
//                                    subscriber.onError(new Throwable(info.statusCode+""));
//                                }
//                            }
//                        }, null);
//                    }
//                }).doOnSubscribe(new BaseSubscriber(super.mContext) {
//                    @Override
//                    public void call() {
//                        super.call();
//                        mView.onRequestStart();
//                    }
//                })
//                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
//                    @Override
//                    public Observable<?> call(Observable<? extends Throwable> observable) {
//                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
//                            @Override
//                            public Observable<?> call(Throwable throwable) {
//                                String code = throwable.toString();
//                                if(code.equals("-4")||code.equals("-5")){//token失效、过期
//                                    return mModel.otoGetToken();
//                                }else{
//                                    CommonUtil.getRequestEntity(throwable,"upload_retrywhen");
//                                    return null;
//                                }
//                            }
//                        });
//
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        mView.onSuccess("upload-onNext", s);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        CommonUtil.getRequestEntity(throwable,"upload");
//                    }
//                })
//        );
//    }
//
//    private static UploadManager uploadManager;
//    public void initQiNiuUpload() {
//        if(uploadManager==null) {
//            Configuration config = new Configuration.Builder()
//                    .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
//                    .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
//                    .connectTimeout(10) // 链接超时。默认10秒
//                    .responseTimeout(60) // 服务器响应超时。默认60秒
////                .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
////                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
//                    .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
//                    .build();
//            //————http上传,指定zone的具体区域——
//            //Zone.zone0:华东
//            //Zone.zone1:华北
//            //Zone.zone2:华南
//            //Zone.zoneNa0:北美
//            // 重用uploadManager。一般地，只需要创建一个uploadManager对象
//            uploadManager = new UploadManager();
//        }
//    }



//    @Override
//    public void onlineQAonlineQA(JSONObject object) {
//        mRxManager.add(mModel.onlineQAonlineQA(object)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        mView.onRequestStart();
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Object>() {
//                    @Override
//                    public void call(Object obj) {
//                        mView.onOnlineQAonlineQASuccess(obj);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        mView.onInternetError();
//                        mView.onRequestEnd();
//                    }
//                })
//        );
//    }



}