package com.asking91.app.ui.pay;

import com.asking91.app.entity.pay.PayEntity;
import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxwang on 2016/11/14.
 */
public interface PayContract {
    interface Model extends BaseModel {

        Observable<ResponseBody> getAppCharge(PayEntity payEntity);
        Observable<ResponseBody> getAppReCharge(PayEntity payEntity);
        Observable<ResponseBody> appChargeSuccess(String orderNo);
        Observable<ResponseBody> versionClassic(String subjectCatalog);
        Observable<ResponseBody> version(String subjectCatalog);
        Observable<ResponseBody> getCommodityList(String subjectCatalog,int months,int type);

        Observable<ResponseBody> paymentcommodity(String versionLevelId);

        Observable<ResponseBody> checkUserInfo();
        Observable<ResponseBody> payGetCharge(String orderType, String payType, String[] commodityList);
        Observable<ResponseBody> findClassList(String packageTypeId);

        Observable<ResponseBody> findClassDetailList(String packageTypeId,String timeLimit,String start,String limit);

        Observable<ResponseBody> payNewGetCharge(String orderType, String payType, String commodityId,String marketId);
        Observable<ResponseBody> getAskMoney(int start,int limit);

    }

    interface View extends BaseView {
        void onSuccess(int type, ResponseBody body);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public abstract void getAppCharge(PayEntity payEntity);
        public abstract void getAppReCharge(PayEntity payEntity);
        public abstract void appChargeSuccess(String orderNo);
        public abstract void versionClassic(String subjectCatalog);
        public abstract void version(String subjectCatalog);
        public abstract void getCommodityList(String subjectCatalog,int months,int type);
        public abstract void checkUserInfo();

    }

}
