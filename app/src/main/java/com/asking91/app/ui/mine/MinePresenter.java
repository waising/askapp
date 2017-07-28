package com.asking91.app.ui.mine;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.asking91.app.global.MineConstant;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.util.CommonUtil;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *
 */
public class MinePresenter extends MineContract.Presenter {

    // 获取省、市、县（三个都用这个接口）
    @Override
    public void getRegionInfo(String regionCode) {
        mRxManager.add(mModel.getRegionInfo(regionCode)
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
                        mView.onSuccess(0,body);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    //    获取学校
    public void getSchoolInfo(final int requestType, String regionCode) {
        mRxManager.add(mModel.getSchoolInfo(requestType,regionCode)
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
                                   mView.onSuccess(requestType, responseBody);
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

    @Override
    public void completeUserSchool(String schoolId, String schoolName, String regionCode, String regionName, String name) {
        mRxManager.add(mModel.completeUserSchool(schoolId,schoolName,regionCode,regionName,name)
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
                                   mView.onSuccess(MineConstant.UserSchool.completeUserSchool, responseBody);
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

    @Override
    public void getAvatar(String id) {
        mRxManager.add(mModel.getAvatar(id)
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
                        mView.onSuccess(MineConstant.Mine.avatar,body);
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
    public void getAppUser() {
        mRxManager.add(mModel.getAppUser()
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
                        mView.onSuccess(MineConstant.Mine.userInfo,body);
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
    public void loginOut() {
        mRxManager.add(mModel.loginOut()
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
                        mView.onSuccess(MineConstant.Mine.loginOut,body);
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
    public void sign() {
        mRxManager.add(mModel.sign()
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
                        mView.onSuccess(MineConstant.Mine.sign,body);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    public void checkTodaySign(final Context context,final ApiRequestListener mListener) {
        mRxManager.add(mModel.checkTodaySign()
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
                        try{
                            String resStr = body.string();
                            ArrayMap<String,String> map = JSON.parseObject(resStr,new TypeReference<ArrayMap<String,String>>(){});
                            String flag = String.valueOf(map.get("flag"));
                            mListener.onResultSuccess(flag);
                        }catch (Exception e){
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

    public void dailySign(final Context context,final ApiRequestListener mListener) {
        mRxManager.add(mModel.dailySign()
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
                        resSuccess(context,"",body,mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    public void orderhistory(final Context context,String start, String limit, String account
            , String role,final ApiRequestListener mListener) {
        mRxManager.add(mModel.orderhistory(start,limit,account,role)
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
                        resSuccess(context,"content",body,mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    public void invite(final Context context,final ApiRequestListener mListener) {
        mRxManager.add(mModel.invite()
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
                        resSuccess(context,"",body,mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    public void studentcomplain(final Context context,String reason, String details, String id,final ApiRequestListener mListener) {
        mRxManager.add(mModel.studentcomplain(reason,details,id)
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
                        resSuccess(context,"",body,mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    private void resSuccess(Context context, String valueKey, ResponseBody obj, ApiRequestListener mListener){
        try{
            String resStr = obj.string();
            ArrayMap<String,String> map = JSON.parseObject(resStr,new TypeReference<ArrayMap<String,String>>(){});
            String flag = String.valueOf(map.get("flag"));
            if(TextUtils.equals("0",flag)){
                if(!TextUtils.isEmpty(valueKey)){
                    String result = String.valueOf(map.get(valueKey));
                    mListener.onResultSuccess(result);
                }else{
                    mListener.onResultSuccess(map);
                }
            }else{
                mListener.onResultFail();
                Toast.makeText(context ,String.valueOf(map.get("msg")), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            mListener.onResultFail();
            e.printStackTrace();
        }
    }
}
