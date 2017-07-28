package com.asking91.app.ui.pay;

import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.pay.PayEntity;
import com.asking91.app.global.BasePacketConstant;
import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.global.PayConstant;
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
public class PayPresenter extends PayContract.Presenter {


    @Override
    public void getAppCharge(PayEntity payEntity) {
        mRxManager.add(mModel.getAppCharge(payEntity)
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
                        mView.onSuccess(PayConstant.Pay.charge, body);
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
    public void getAppReCharge(PayEntity payEntity) {
        mRxManager.add(mModel.getAppReCharge(payEntity)
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
                        mView.onSuccess(PayConstant.Pay.charge, body);
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
    public void appChargeSuccess(String orderNo) {
        mRxManager.add(mModel.appChargeSuccess(orderNo)
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
                        mView.onSuccess(PayConstant.Pay.chargeSuccess, body);
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
    public void versionClassic(String subjectCatalog) {
        mRxManager.add(mModel.versionClassic(subjectCatalog)
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
                        mView.onSuccess(PayConstant.Pay.versionClassic, body);
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
    public void version(String subjectCatalog) {
        mRxManager.add(mModel.version(subjectCatalog)
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
                        mView.onSuccess(PayConstant.Pay.version, body);
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
    public void getCommodityList(String subjectCatalog, int months, int type) {
        mRxManager.add(mModel.getCommodityList(subjectCatalog, months, type)
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
                        mView.onSuccess(PayConstant.Pay.commodityList, body);
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
    public void checkUserInfo() {
        mRxManager.add(mModel.checkUserInfo()
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
                        mView.onSuccess(BasePacketConstant.Knowledge.CHECKUSERINFO, body);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }


    public void getAskMoney(int start, int limit, final ApiRequestListener<String> mListener) {

        mRxManager.add(mModel.getAskMoney(start,limit)
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

    /**
     * 支付新接口
     *
     * @param orderType
     * @param payType
     * @param commodityList
     */
    public void payGetCharge(String orderType, String payType, String[] commodityList) {
        mRxManager.add(mModel.payGetCharge(orderType, payType, commodityList)
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
                        mView.onSuccess(PayConstant.Pay.getCharge, body);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }

    public void paymentcommodity(String versionLevelId, final ApiRequestListener<String> mListener) {
        mRxManager.add(mModel.paymentcommodity(versionLevelId)
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
                Toast.makeText(mContext, jsonRes.getString("msg"), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            mListener.onResultFail();
            CommonUtil.getRequestEntity(e);
            e.printStackTrace();
        }
    }


    /**
     * 课程套餐详情第一层
     *
     * @param mListener
     */
    public void findClassList(String courseTypeId, final ApiRequestListener<String> mListener) {
        mRxManager.add(mModel.findClassList(courseTypeId)
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


    /**
     * 课程套餐详情第二层
     *
     * @param mListener
     */
    public void findClassDetailList(String packageTypeId, String timeLimit, String start, String limit, final ApiRequestListener<String> mListener) {
        mRxManager.add(mModel.findClassDetailList(packageTypeId, timeLimit, start, limit)
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


    /**
     * 支付新接口
     *
     * @param orderType
     * @param payType
     * @param
     */
    public void newPayGetCharge(String orderType, String payType, String commodityId, String marketId) {
        mRxManager.add(mModel.payNewGetCharge(orderType, payType, commodityId, marketId)
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
                        mView.onSuccess(PayConstant.Pay.getCharge, body);
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
