package com.asking91.app.ui.supertutorial;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxiao on 2016/10/28.
 */

public interface SuperSelectContract {
    interface View extends BaseView {
        void onGetSuperSelectSuccess(ResponseBody obj);

        void onFreeStudySuccess(ResponseBody obj);

        void onFreeStudyVersionSuccess(ResponseBody obj);

        void onRefreshData(ResponseBody res);

        void onLoadMoreData(ResponseBody res);

        void onResultSuccess(String method, ResponseBody res);

        void onResultSuccess(String method, ResponseBody res, int position);
    }

    interface Model extends BaseModel {
        Observable<ResponseBody> getSuperSelect();

        Observable<ResponseBody> freeStudyVersion(String versionId);

        Observable<ResponseBody> getSuperFreeCoach(String versionLevelId, String id, int start, int limit);

        //已购买
        Observable<ResponseBody> superlesson(String catalog, String catalogCode);

        Observable<ResponseBody> superlessonlevel(String versionId);

        Observable<ResponseBody> superlessontree(String versionLevelId);

        Observable<ResponseBody> getSuperBuyFragment1(String levelId, String knowledgeId, int type);

        Observable<ResponseBody> getSubjectTopic(String subjectCatalog, String tipId);

        Observable<ResponseBody> getSuperBuyCoach(String versionLevelId, String id, int start, int limit);

        Observable<ResponseBody> getAllSubjectClassic(String subjectCatalog, String tipId, String subjectType, int start, int limit);

        Observable<ResponseBody> subjectClassic(String answerstr, String code);

        Observable<ResponseBody> getVoicePath(String gradeId, String levelId, int prefix, int suffix);

        Observable<ResponseBody> getSubjectMul(String kindId, String catalogCode, int start, int limit);

        Observable<ResponseBody> subject(String answerstr, String code);

        Observable<ResponseBody> firstreviewzhangjd(String orgId);

        Observable<ResponseBody> firstreviewkesjd(String pid);

        Observable<ResponseBody> firstreviewkaoqfx(String pid);

        Observable<ResponseBody> firstreviewshizyl(String pid);

        Observable<ResponseBody> firstreviewbeigk(String pid, String index);

        Observable<ResponseBody> firstreviewdiant(String pid);

        Observable<ResponseBody> secondreviewtree(String orgId);

        Observable<ResponseBody> secondreviewzhuant(String pid, String field);

        //yonghu学校资料完整性
        Observable<ResponseBody> checkUserInfo();

        Observable<ResponseBody> findByEdition(String editionId, String subjectId);

        Observable<ResponseBody> findBySuperlesson(String editionId, String subjectId);

        Observable<ResponseBody> SynchronousCourse(String productId);

        Observable<ResponseBody> myCourse(int start, int limit);

        Observable<ResponseBody> myCoursePdfAndUrl(String commodityId);

        Observable<ResponseBody> saveSchedule(String commodityId, double schedulePercent, String scheduleTitle, String scheduleId, String scheduleContent);

        /**
         * 练吧错题提交接口
         */
        Observable<ResponseBody> submitError(String subjectCatalog, String subjectId, String userAnswer);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getSuperSelect();

        /**
         * 获取课本年级
         */
        public abstract void freeStudyVersion(String versionId);

        /**
         * 超级辅导课阿思可博士来讲题
         */
        public abstract void getSuperFreeCoach(String versionLevelId, String id, int start, int limit);

        /**
         * 获取超级辅导课阿思可博士有话说列表
         */
        public abstract void getSuperBuyFragment1(String levelId, String knowledgeId, int type);

        /**
         * 超级辅导课阿思可博士来讲题
         */
        public abstract void getSuperBuyCoach(String versionLevelId, String id, int start, int limit);

        /**
         * 演练大冲关提交题目
         */
        public abstract void subjectClassic(String answerstr, String code);

        /**
         * 获取音频文件
         */
        public abstract void getVoicePath(String gradeId, String levelId, int prefix, int suffix);

        /**
         * 获取变式题
         */
        public abstract void getSubjectMul(String kindId, String catalogCode, int start, int limit);

        /**
         * 提交变式题练习
         */
        public abstract void subject(String answerstr, String code, int position);

        public abstract void checkUserInfo();

        /**
         * 练吧错题提交接口
         */
        public abstract void submitError(String subjectCatalog, String subjectId, String userAnswer);

    }

}