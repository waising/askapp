package com.asking91.app.ui.juniorhigh;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.util.CommonUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 初升高
 */
public class JuniorToHighPresenter extends JuniorToHighContract.Presenter {

    /**
     * 初升高课程list
     *
     * @param mListener
     */
    public void JuniorToHighList(String courseTypeId, int start, int limit, final ApiRequestListener<String> mListener) {
        mRxManager.add(mModel.juniorToHigh(courseTypeId, start, limit)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        resSuccess("0", "content", body, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RequestEntity resultEntity = CommonUtil.getRequestEntity(throwable);
                        if (resultEntity.getCode() == HttpCodeConstant.CONNECT_401) {
                            mListener.onResultFail(resultEntity);
                        } else {
                            resSuccess("1", "content", null, mListener);
                        }

                    }
                })

        );
    }

    /**
     * 请求成功
     *
     * @param successFlag
     * @param valueKey
     * @param obj
     * @param mListener
     */
    private void resSuccess(String successFlag, String valueKey, ResponseBody obj, ApiRequestListener mListener) {
        try {
            String resStr = obj.string();
            JSONObject jsonRes = JSON.parseObject(resStr);
            String flag = jsonRes.getString("flag");
            if (TextUtils.equals(successFlag, flag)) {
                if (!TextUtils.isEmpty(valueKey)) {
                    String result = jsonRes.getString(valueKey);
                    mListener.onResultSuccess(result);
                } else {
                    mListener.onResultSuccess(jsonRes);
                }
            } else {
                mListener.onResultFail();
                //    Toast.makeText(mContext, jsonRes.getString("msg"), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            mListener.onResultFail();
            CommonUtil.getRequestEntity(e);
            e.printStackTrace();
        }
    }

    /**
     * 初升高全套课程
     *
     * @param mListener
     */
    public void JuniorToHighAllCourse(String packageId, final ApiRequestListener<String> mListener) {
        mRxManager.add(mModel.JuniorToHighAllCourse(packageId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        resSuccess("0", "content", body, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        CommonUtil.getRequestEntity(throwable);
                    }
                })

        );
    }

    /**
     * list
     *
     * @param mListener
     */
    public void couponList(int start, int limit, int plateform, String eventType, String product, final ApiRequestListener<String> mListener) {
        mRxManager.add(mModel.couponList(start, limit, plateform, eventType, product)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        resSuccess("0", "content", body, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RequestEntity resultEntity = CommonUtil.getRequestEntity(throwable);
                        if (resultEntity.getCode() == HttpCodeConstant.CONNECT_401) {
                            mListener.onResultFail(resultEntity);
                        } else {
                            resSuccess("1", "content", null, mListener);
                        }

                    }
                })

        );
    }

    /**
     * 保存我的课程的进度
     *
     * @param
     */

    public void saveSchedule(String commodityId, int schedulePercent, String scheduleTitle, String scheduleId, String scheduleContent, final ApiRequestListener<String> mListener) {
        mRxManager.add(mModel.saveSchedule(commodityId, schedulePercent, scheduleTitle, scheduleId, scheduleContent)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        resSuccess("0", "content", body, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RequestEntity resultEntity = CommonUtil.getRequestEntity(throwable);
                        if (resultEntity.getCode() == HttpCodeConstant.CONNECT_401) {
                            mListener.onResultFail(resultEntity);
                        } else {
                            resSuccess("1", "content", null, mListener);
                        }

                    }
                })

        );
    }

    /**
     * 领取注册后优惠卷
     */

    public void couponRegister(final ApiRequestListener<String> mListener) {
        mRxManager.add(mModel.couponRegister()
                        .subscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBody>() {
                            @Override
                            public void call(ResponseBody body) {
//                        resSuccess("0", "content", body, mListener);

                                try {
                                    mListener.onResultSuccess(body.string());
                                } catch (IOException e) {
                                    mView.onRequestError(CommonUtil.getRequestEntity(e));
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                RequestEntity resultEntity = CommonUtil.getRequestEntity(throwable);
                                if (resultEntity.getCode() == HttpCodeConstant.CONNECT_401) {
                                    mListener.onResultFail(resultEntity);
                                } else {
                                    CommonUtil.getRequestEntity(throwable);
                                }

                            }
                        })

        );
    }


//    public void superlessontree(String versionLevelId, final ApiRequestListener<String> mListener) {
//        mRxManager.add(mModel.superlessontree(versionLevelId)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new BaseSubscriber(super.mContext) {
//                    @Override
//                    public void call() {
//                        super.call();
//                        mView.onRequestStart();
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<ResponseBody>() {
//                    @Override
//                    public void call(ResponseBody obj) {
//                        try {
//                            mListener.onResultSuccess(obj.string());
//                        } catch (Exception e) {
//                            mView.onRequestError(CommonUtil.getRequestEntity(e));
//                        }
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
//                    }
//                })
//        );
//    }


    /**
     * 某个商品的优惠卷
     */

    public void goodsCoupon(String commodityId, final ApiRequestListener<String> mListener) {
        mRxManager.add(mModel.goodsCoupon(commodityId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        resSuccess("0", "content", body, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RequestEntity resultEntity = CommonUtil.getRequestEntity(throwable);
                        if (resultEntity.getCode() == HttpCodeConstant.CONNECT_401) {
                            mListener.onResultFail(resultEntity);
                        } else {
                            CommonUtil.getRequestEntity(throwable);
                        }

                    }
                })

        );
    }
}
