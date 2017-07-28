package com.asking91.app.ui.supertutorial;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.util.CommonUtil;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wxiao on 2016/10/28
 */

public class SuperSelectPresenterImpl extends SuperSelectContract.Presenter {

    public void getSubjectTopic(String subjectCatalog, String tipId) {
        mRxManager.add(mModel.getSubjectTopic(subjectCatalog, tipId)
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
                               public void call(ResponseBody obj) {
                                   mView.onResultSuccess("getSubjectTopic", obj);
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                               }
                           }
                ));
    }

    public void getAllSubjectClassic(String subjectCatalog, String tipId, String subjectType, int start, int limit) {
        mRxManager.add(mModel.getAllSubjectClassic(subjectCatalog, tipId, subjectType, start, limit)
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        mView.onRequestEnd();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onRequestError(CommonUtil.getRequestEntity(e));
                    }

                    @Override
                    public void onNext(final ResponseBody res) {
                        mView.onRefreshData(res);
                    }
                }));
    }

    @Override
    public void subjectClassic(String answerstr, String code) {
        mRxManager.add(mModel.subjectClassic(answerstr, code)
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
                    public void call(ResponseBody obj) {
                        mView.onResultSuccess("subjectClassic", obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestEnd();
                    }
                })
        );
    }


    @Override
    public void getVoicePath(String gradeId, String levelId, int prefix, int suffix) {
        mRxManager.add(mModel.getVoicePath(gradeId, levelId, prefix, suffix)
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
                    public void call(ResponseBody obj) {
                        mView.onResultSuccess("getVoicePath", obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    public void getVoicePath(String gradeId, String levelId, int prefix, int suffix, final int position) {
        mRxManager.add(mModel.getVoicePath(gradeId, levelId, prefix, suffix)
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
                    public void call(ResponseBody obj) {
                        mView.onResultSuccess("getVoicePath", obj, position);
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
    public void getSuperSelect() {
        mRxManager.add(mModel.getSuperSelect()
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
                    public void call(ResponseBody obj) {
                        mView.onGetSuperSelectSuccess(obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }

    public void superlesson(String catalog, String catalogCode, final ApiRequestListener<String> mListener) {
        mRxManager.add(mModel.superlesson(catalog, catalogCode)
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
                    public void call(ResponseBody obj) {
                        try {
                            mListener.onResultSuccess(obj.string());
                        } catch (Exception e) {
                            CommonUtil.getRequestEntity(e);
                            mListener.onResultFail();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        CommonUtil.getRequestEntity(throwable);
                        mListener.onResultFail();
                    }
                })
        );
    }


    public void firstreviewzhangjd(final Context context, String orgId, final ApiRequestListener mListener) {
        mRxManager.add(mModel.firstreviewzhangjd(orgId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess(context, "firstReviewNodes", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);
                    }
                })
        );
    }

    public void firstreviewkesjd(final Context context, String pid, final ApiRequestListener mListener) {
        mRxManager.add(mModel.firstreviewkesjd(pid)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess(context, "firstReviewNodes", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);
                    }
                })
        );
    }

    public void firstreviewkaoqfx(final Context context, String pid, final ApiRequestListener mListener) {
        mRxManager.add(mModel.firstreviewkaoqfx(pid)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess(context, "content", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);
                    }
                })
        );
    }

    public void firstreviewshizyl(final Context context, String pid, final ApiRequestListener mListener) {
        mRxManager.add(mModel.firstreviewshizyl(pid)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess(context, "subjects", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);
                    }
                })
        );
    }

    public void firstreviewbeigk(final Context context, String pid, String index, final ApiRequestListener mListener) {
        mRxManager.add(mModel.firstreviewbeigk(pid, index)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess(context, "nodes", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);
                    }
                })
        );
    }

    public void firstreviewdiant(final Context context, String pid, final ApiRequestListener mListener) {
        mRxManager.add(mModel.firstreviewdiant(pid)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess(context, "", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);
                    }
                })
        );
    }

    public void secondreviewtree(final Context context, String orgId, final ApiRequestListener mListener) {
        mRxManager.add(mModel.secondreviewtree(orgId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess1(context, "content", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);
                    }
                })
        );
    }

    public void secondreviewzhuant(final Context context, String pid, String field, final ApiRequestListener mListener) {
        mRxManager.add(mModel.secondreviewzhuant(pid, field)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess1(context, "content", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);


                    }
                })
        );
    }

    private void resSuccess1(Context context, String valueKey, ResponseBody obj, ApiRequestListener mListener) {
        try {
            String resStr = obj.string();
            ArrayMap<String, String> map = JSON.parseObject(resStr, new TypeReference<ArrayMap<String, String>>() {
            });
            String flag = String.valueOf(map.get("flag"));
            if (TextUtils.equals("0", flag)) {//0标示成功
                if (!TextUtils.isEmpty(valueKey)) {
                    String result = String.valueOf(map.get(valueKey));
                    mListener.onResultSuccess(result);
                } else {
                    mListener.onResultSuccess(map);
                }
            } else {
                Toast.makeText(context, String.valueOf(map.get("msg")), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            //Toast.makeText(context , R.string.net_data_err, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void resSuccess(Context context, String valueKey, ResponseBody obj, ApiRequestListener mListener) {
        try {
            String resStr = obj.string();
            ArrayMap<String, String> map = JSON.parseObject(resStr, new TypeReference<ArrayMap<String, String>>() {
            });
            String flag = String.valueOf(map.get("flag"));
            if (TextUtils.equals("1", flag)) {//1标示成功
                if (!TextUtils.isEmpty(valueKey)) {
                    String result = String.valueOf(map.get(valueKey));
                    mListener.onResultSuccess(result);
                } else {
                    mListener.onResultSuccess(map);
                }
            } else {
                Toast.makeText(context, String.valueOf(map.get("msg")), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            //Toast.makeText(context , R.string.net_data_err, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void toastThrowable(Context context, Throwable throwable) {
        String msg = HttpCodeConstant.getErrorMsg(CommonUtil.getRequestEntity(throwable));
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void freeStudyVersion(String versionId) {
        mRxManager.add(mModel.freeStudyVersion(versionId)
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
                    public void call(ResponseBody obj) {
                        mView.onFreeStudySuccess(obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }


    public void superlessonlevel(String versionId, final ApiRequestListener mListener) {
        mRxManager.add(mModel.superlessonlevel(versionId)
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
                    public void call(ResponseBody obj) {
                        try {
                            mListener.onResultSuccess(obj.string());
                        } catch (Exception e) {
                            CommonUtil.getRequestEntity(e);
                            mListener.onResultFail();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        CommonUtil.getRequestEntity(throwable);
                        mListener.onResultFail();
                    }
                })
        );
    }

    public void superlessontree(String versionLevelId, final ApiRequestListener<String> mListener) {
        mRxManager.add(mModel.superlessontree(versionLevelId)
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
                    public void call(ResponseBody obj) {
                        try {
                            mListener.onResultSuccess(obj.string());
                        } catch (Exception e) {
                            mView.onRequestError(CommonUtil.getRequestEntity(e));
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
    public void getSuperBuyFragment1(String levelId, String knowledgeId, int type) {
        mRxManager.add(mModel.getSuperBuyFragment1(levelId, knowledgeId, type)
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
                    public void call(ResponseBody obj) {
                        mView.onGetSuperSelectSuccess(obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestEnd();
                    }
                })
        );
    }

    @Override
    public void getSubjectMul(String kindId, String catalogCode, int start, int limit) {
        mRxManager.add(mModel.getSubjectMul(kindId, catalogCode, start, limit)
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
                    public void call(ResponseBody obj) {
                        mView.onResultSuccess("getSubjectMul", obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestEnd();
                    }
                })
        );
    }

//    @Override
//    public void getSubjectMul(String kindId, String catalogCode, int start, int limit) {
//        mRxManager.add(mModel.getSubjectMul(kindId, catalogCode, start, limit)
//                .subscribe(superCoachAgain(start)));
//    }

    @Override
    public void subject(String answerstr, String code, final int position) {
        mRxManager.add(mModel.subject(answerstr, code)
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
                    public void call(ResponseBody obj) {
                        mView.onResultSuccess("subject", obj, position);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestEnd();
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
                        mView.onResultSuccess("checkUserInfo", body);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }


//    @Override
//    public void getSuperFreeCoach(String versionLevelId, String id, int start, int limit) {
//        mRxManager.add(mModel.getSuperFreeCoach(versionLevelId, id, start, limit)
//                .subscribe(superCoachAgain(start)));
//    }

    @Override
    public void getSuperFreeCoach(String versionLevelId, String id, int start, int limit) {
        mRxManager.add(mModel.getSuperFreeCoach(versionLevelId, id, start, limit)
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
                    public void call(ResponseBody obj) {
                        mView.onResultSuccess("getSuperFreeCoach", obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestEnd();
                    }
                })
        );
    }

    @Override
    public void getSuperBuyCoach(String versionLevelId, String id, int start, int limit) {
        mRxManager.add(mModel.getSuperBuyCoach(versionLevelId, id, start, limit)
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                        mView.onRequestStart();
                    }
                })
                .subscribe(superCoachAgain(start)));
    }

    public Observer<ResponseBody> superCoachAgain(final int pageIndex) {
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
            public void onNext(final ResponseBody res) {
                if (pageIndex == 0) {
                    mView.onRefreshData(res);
                } else {
                    mView.onLoadMoreData(res);
                }
            }


        };
    }

    /**
     * 根据版本和年级查看商品信息
     *
     * @param context
     * @param mListener
     */

    public void findByEdition(final Context context, String editionId, String subjectId, final ApiRequestListener mListener) {
        mRxManager.add(mModel.findByEdition(editionId, subjectId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess1(context, "content", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);
                    }
                })
        );
    }

    public void findBySuperlesson(final Context context, String subject
            , String grade, final ApiRequestListener mListener) {
        baseReqStr(mModel.findBySuperlesson(subject, grade), mListener);
    }

    public void baseReqStr(Observable<ResponseBody> mObservable, final ApiRequestListener mListener) {
        mRxManager.add(mObservable
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        try {
                            String result = obj.string();
                            mListener.onResultSuccess(result);
                        } catch (Exception e) {
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mListener.onResultFail();
                        String msg = HttpCodeConstant.getErrorMsg(CommonUtil.getRequestEntity(throwable));
                        if (!TextUtils.isEmpty(msg)) {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                })

        );
    }


    /**
     * 同步课程接口
     *
     * @param mListener
     */

    public void SynchronousCourse(final Context context, String productId, final ApiRequestListener mListener) {
        mRxManager.add(mModel.SynchronousCourse(productId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess1(context, "content", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);
                    }
                })
        );
    }

    /**
     * 我的课程
     *
     * @param context
     */

    public void myCourse(final Context context, int
            start, int limit, final ApiRequestListener mListener) {
        mRxManager.add(mModel.myCourse(start, limit)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess1(context, "content", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);
                        mListener.onResultFail();
                    }
                })
        );
    }


    /**
     * 我的课程pdf和mp4的url
     *
     * @param context
     * @param commodityId
     * @param mListener
     */

    public void myCoursePdfAndUrl(final Context context, String commodityId, final ApiRequestListener mListener) {

        mRxManager.add(mModel.myCoursePdfAndUrl(commodityId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess1(context, "content", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);
                    }
                })
        );


    }

    /**
     * 保存进度
     *
     * @param context
     * @param commodityId
     * @param schedulePercent
     * @param scheduleTitle
     * @param scheduleId
     * @param scheduleContent
     * @param mListener
     */
    public void saveSchedule(final Context context, String commodityId, double schedulePercent, String scheduleTitle, String scheduleId, String scheduleContent, final ApiRequestListener mListener) {

        mRxManager.add(mModel.saveSchedule(commodityId, schedulePercent, scheduleTitle, scheduleId, scheduleContent)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new BaseSubscriber(super.mContext) {
                    @Override
                    public void call() {
                        super.call();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody obj) {
                        resSuccess1(context, "content", obj, mListener);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toastThrowable(context, throwable);
                    }
                })
        );


    }

    @Override
    public void submitError(String subjectCatalog, String subjectId, String userAnswer) {
        mRxManager.add(mModel.submitError(subjectCatalog, subjectId, userAnswer)
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
                    public void call(ResponseBody obj) {
                        mView.onResultSuccess("errorQuestion", obj);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestEnd();
                    }
                })
        );
    }

}