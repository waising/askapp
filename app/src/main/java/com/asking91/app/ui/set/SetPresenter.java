package com.asking91.app.ui.set;

import com.asking91.app.entity.RequestEntity;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseSubscriber;
import com.asking91.app.mvpframe.BaseView;
import com.asking91.app.util.CommonUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wxiao on 2016/12/7.
 */

public class SetPresenter extends BasePresenter<SetModel, SetPresenter.View> {
    interface View extends BaseView {
        void onSuccess(String method, String responseBody);
        void onSuccess(String method, ResponseBody baseRsqEntity);

        void onRequestSuccess(int requestType, ResponseBody responseBody);

        void onRequestError(String method, RequestEntity requestEntity);

    }

    public void getQiniuToken() {
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
                        mView.onSuccess("GetQiniuToken", baseRsqEntity);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError(CommonUtil.getRequestEntity(throwable, "GetQiniuToken"));
                    }
                }));
    }

    /**
     * 退出
     */
    public void logout() {
        mRxManager.add(mModel.logout()
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
                    public void call(ResponseBody baseRsqEntity) {
                        try {
                            mView.onSuccess("logout", baseRsqEntity.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError("logout", CommonUtil.getRequestEntity(throwable));
                    }
                })
        );

        mRxManager.add(mModel.logout2()
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
                    public void call(ResponseBody baseRsqEntity) {
                        try {
                            mView.onSuccess("logout", baseRsqEntity.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError("logout", CommonUtil.getRequestEntity(throwable));
                    }
                })
        );
    }
/*    *//**更新个人资料*//*
    public void updateUser(String ticket,String name, String nickName, String sex,
                           String birthday, String regionName,
                           String regionCode, String schoolName,
                           String remark, String area,
                           String levelId, String classId){
        mRxManager.add(mModel.logout()
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
                    public void call(ResponseBody baseRsqEntity) {
                        try {
                            mView.onSuccess("updateUser",baseRsqEntity.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError("updateUser",CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }*/

    /**
     * 更新个人资料
     */
    public void updateUser(String ticket, String name, String nickName, String sex,
                           String birthday, String regionName,
                           String regionCode, String schoolName,
                           String remark, String area,
                           String levelId, String classId,String avatar) {
        mRxManager.add(mModel.updateUser(ticket, name, nickName, sex, birthday, regionName,
                regionCode, schoolName, remark, area, levelId, classId,avatar)
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
                    public void call(ResponseBody baseRsqEntity) {
                        try {
                            mView.onSuccess("updateUser", baseRsqEntity.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError("updateUser", CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }

    //    获取学校
    public void presenterGetSchoolInfo(final int requestType, String regionCode) {
        mRxManager.add(mModel.modelGetSchoolInfo(regionCode)
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
                               }
                           }
                )
        );
    }

    /**
     * 更新APK
     */
    public void updateApk() {
        mRxManager.add(mModel.updateApk()
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
                    public void call(ResponseBody baseRsqEntity) {
                        try {
                            mView.onSuccess("updateApk", baseRsqEntity.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onRequestError("updateApk", CommonUtil.getRequestEntity(throwable));
                    }
                })

        );
    }
}
