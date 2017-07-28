package com.asking91.app.ui.oto;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.asking91.app.global.OtoConstant;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.mvpframe.BaseView;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.JLog;
import com.asking91.app.util.QiniuUtil;
import com.hanvon.HWCloudManager;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by wxiao on 2016/11/11.
 */

public class OtoAskPresent extends BasePresenter<OtoAskModel, OtoAskPresent.View> {
    public interface View extends BaseView {
        void onSuccess(String methodName, ResponseBody baseRsqEntity);

        void onSuccess(String methodName, String string);

    }

    public void otoLogin(String telephone) {
        mRxManager.add(mModel.otoLogin(telephone)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new BaseSubscriber(super.mContext) {
                            @Override
                            public void call() {
                                super.call();
                                mView.onRequestStart();
                            }
                        })
//                .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBody>() {
                            @Override
                            public void call(ResponseBody baseRsqEntity) {
                                mView.onSuccess("otoLogin", baseRsqEntity);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.onRequestError(CommonUtil.getRequestEntity(throwable, "otoLogin"));
                            }
                        })

        );
    }

    public void otoImg(String telephone, final String action, String text, int askMoney, String imgUrlString, String subjectCatalog,
                       int score, int askCoin, String suggest, int bind, int linkFlag) {
        mRxManager.add(mModel.otoImg(telephone.toUpperCase(), action, text, askMoney, imgUrlString, subjectCatalog, score, askCoin, suggest, bind, linkFlag)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody baseRsqEntity) {
                        mView.onSuccess("otoImg-" + action, baseRsqEntity);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        JLog.i("otoImgotoImgotoImg", "action=" + action);
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable, "otoImg"));
                    }
                })

        );
    }

    public void qiniutoken() {
        mRxManager.add(mModel.qiniutoken()
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody baseRsqEntity) {
                        mView.onSuccess("otoGetToken", baseRsqEntity);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable, "otoLogin"));
                    }
                }));
    }

    public void upload(final String path, final String key, final String token) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        try{
                            BitmapUtil.createImageThumbnail(mContext,path,path,500,80);
                        }catch(Exception e){}

                        QiniuUtil.getUploadManager().put(path, key, token, new UpCompletionHandler() {
                            @Override
                            public void complete(String keys, ResponseInfo info, JSONObject response) {
                                if (info.isOK()) {
                                    subscriber.onNext(key);
                                    subscriber.onCompleted();
                                } else {
                                    subscriber.onError(new Throwable(info.statusCode + ""));
                                }
                            }
                        }, null);
                    }
                }).doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                String code = throwable.toString();
                                if (code.equals("-4") || code.equals("-5")) {//token失效、过期
                                    return mModel.qiniutoken();
                                } else {
                                    mView.onRequestError(CommonUtil.getRequestEntity(throwable, "upload_retrywhen"));
                                    return null;
                                }
                            }
                        });

                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                mView.onSuccess("upload-onNext", s);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.onRequestError(CommonUtil.getRequestEntity(throwable, "upload"));
                            }
                        })
        );
    }

    /**
     * 获取汉王云题目解析内容
     *
     */
    public void getHwyQuestion(final HWCloudManager hwCloudManagerFormula, final String picTakePath, final Bitmap caBitmap) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        String greyPath = picTakePath;
                        if(caBitmap == null){
                            greyPath = BitmapUtil.saveGreyBitmap(picTakePath,caBitmap);
                        }
                        String result = "";
                        try{
                            String re = hwCloudManagerFormula.formulaOCRLanguage4Https(greyPath);
                            if(!TextUtils.isEmpty(re)){
                                String reDecode = CommonUtil.decode1(re);
                                result = JSON.parseObject(reDecode).getString("result");
                            }
                        }catch(Exception e){}
                        subscriber.onNext(result);
                    }
                }).doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String responseBody) {
                                mView.onSuccess("getHwyQuestion-next", responseBody);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.onRequestError(CommonUtil.getRequestEntity(throwable, "getHwyQuestion"));
                            }
                        })
        );
    }

    public void getNIMLogin(String accid) {
        mRxManager.add(mModel.getNIMLogin(accid)
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
                        mView.onSuccess(OtoConstant.Oto.nimLogin, body);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }

    private RequestBody getRequestBody(String value) {
        if (TextUtils.isEmpty(value)) {
            value = "";
        }
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public void orderbuild(final Context context, String subject, String grade, String parse, String url, String wanted
            , String account, String name, String avatar, final ApiRequestListener mListener) {
        if (TextUtils.isEmpty(parse)) {
            parse = "";
        }
        Map<String, RequestBody> params = new HashMap<>();
        params.put("subject", getRequestBody(subject));
        params.put("grade", getRequestBody(grade));
        params.put("parse", getRequestBody(parse));
        params.put("url", getRequestBody(url));
        params.put("wanted", getRequestBody(wanted));
        params.put("account", getRequestBody(account));
        params.put("name", getRequestBody(name));
        params.put("avatar", getRequestBody(avatar));
        mRxManager.add(mModel.orderbuild(params)
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

    public void getNIMLogin(String accid, final ApiRequestListener mListener) {
        mRxManager.add(mModel.getNIMLogin(accid)
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
                        try {
                            String resStr = body.string();
                            mListener.onResultSuccess(resStr);
                        } catch (Exception e) {
                            //Toast.makeText(context, "网易token获取失败", Toast.LENGTH_SHORT).show();
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

    public void orderstate(final Context context, String id, final ApiRequestListener mListener) {
        mRxManager.add(mModel.orderstate(id)
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

    public void orderfirstorder(final Context context, String id, final ApiRequestListener mListener) {
        mRxManager.add(mModel.orderfirstorder(id)
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
                        mListener.onResultFail();
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    public void ordercheckbill(final Context context, String id, final ApiRequestListener mListener) {
        mRxManager.add(mModel.ordercheckbill(id)
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
                        mListener.onResultFail();
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    public void studentfavor(final Context context, String userName, String account, final ApiRequestListener mListener) {
        mRxManager.add(mModel.studentfavor(userName, account)
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


    public void studentUnFavor(final Context context, String userName, String account, final ApiRequestListener mListener) {
        mRxManager.add(mModel.studentUnFavor(userName, account)
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




    public void orderevaluate(final Context context, String id, String reward, String star, String suggest, final ApiRequestListener mListener) {
        mRxManager.add(mModel.orderevaluate(id, reward, star, suggest)
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

    public void ordercancel(final Context context, String id, final ApiRequestListener mListener) {
        mRxManager.add(mModel.ordercancel(id)
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
                        mListener.onResultFail();
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

    public void studentInfo(final Context context, String id, final ApiRequestListener mListener) {
        mRxManager.add(mModel.ordercheckbill(id)
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
                        mListener.onResultFail();
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

}
