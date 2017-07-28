package com.asking91.app.ui.supertutorial;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wxiao on 2016/10/28
 */

public class SuperSelectModelImpl implements SuperSelectContract.Model {

    @Override
    public Observable<ResponseBody> getSuperSelect() {
        return Networks.getInstance().getSuperTutorialApi().getSuperSelect().compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> freeStudyVersion(String versionId) {
        return Networks.getInstance().getSuperTutorialApi().freeStudyVersion(versionId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getSuperBuyFragment1(String levelId, String knowledgeId, int type) {
        return Networks.getInstance().getSuperTutorialApi().getSuperBuyFragment1(levelId, knowledgeId, type).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getSuperFreeCoach(String versionLevelId, String id, int start, int limit) {
        return Networks.getInstance().getSuperTutorialApi().getSuperFreeCoach(versionLevelId, id, start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> superlesson(String catalog, String catalogCode) {
        return Networks.getInstance().getSuperTutorialApi().superlesson(catalog, catalogCode).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> superlessonlevel(String versionId) {
        return Networks.getInstance().getSuperTutorialApi().superlessonlevel(versionId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> superlessontree(String versionLevelId) {
        return Networks.getInstance().getSuperTutorialApi().superlessontree(versionLevelId).compose(RxSchedulers.<ResponseBody>io_main());
    }


    @Override
    public Observable<ResponseBody> getSubjectTopic(String subjectCatalog, String tipId) {
        return Networks.getInstance().getSuperTutorialApi().getSubjectTopic(subjectCatalog, tipId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getSuperBuyCoach(String versionLevelId, String id, int start, int limit) {
        return Networks.getInstance().getSuperTutorialApi().getSuperBuyCoach(versionLevelId, id, start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getAllSubjectClassic(String subjectCatalog, String tipId, String subjectType, int start, int limit) {
        return Networks.getInstance().getSuperTutorialApi().getAllSubjectClassic(subjectCatalog, tipId, subjectType, start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> subjectClassic(String answerstr, String code) {
        return Networks.getInstance().getSuperTutorialApi().subjectClassic(answerstr, code).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getVoicePath(String gradeId, String levelId, int prefix, int suffix) {
        return Networks.getInstance().getSuperTutorialApi().getVoicePath(gradeId, levelId, prefix, suffix).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> getSubjectMul(String kindId, String catalogCode, int start, int limit) {
        return Networks.getInstance().getSuperTutorialApi().getSubjectMul(kindId, catalogCode, start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> subject(String answerstr, String code) {
        return Networks.getInstance().getSuperTutorialApi().subject(answerstr, code).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> firstreviewzhangjd(String orgId) {
        return Networks.getInstance().getSuperTutorialApi().firstreviewzhangjd(orgId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> firstreviewkesjd(String pid) {
        return Networks.getInstance().getSuperTutorialApi().firstreviewkesjd(pid).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> firstreviewkaoqfx(String pid) {
        return Networks.getInstance().getSuperTutorialApi().firstreviewkaoqfx(pid).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> firstreviewshizyl(String pid) {
        return Networks.getInstance().getSuperTutorialApi().firstreviewshizyl(pid).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> firstreviewbeigk(String pid, String index) {
        return Networks.getInstance().getSuperTutorialApi().firstreviewbeigk(pid, index).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> firstreviewdiant(String pid) {
        return Networks.getInstance().getSuperTutorialApi().firstreviewdiant(pid).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> secondreviewtree(String orgId) {
        return Networks.getInstance().getSuperTutorialApi().secondreviewtree(orgId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> secondreviewzhuant(String pid, String field) {
        return Networks.getInstance().getSuperTutorialApi().secondreviewzhuant(pid, field).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> checkUserInfo() {
        return Networks.getInstance().getUserApi()
                .checkUserInfo().compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> findByEdition(String editionId, String subjectId) {
        return Networks.getInstance().getUserApi()
                .findByEdition(editionId, subjectId).compose(RxSchedulers.<ResponseBody>io_main());
    }


    @Override
    public Observable<ResponseBody> findBySuperlesson(String subject
            , String grade) {
        return Networks.getInstance().getUserApi()
                .findBySuperlesson(subject, grade).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> SynchronousCourse(String productId
    ) {
        return Networks.getInstance().getUserApi()
                .SynchronousCourse(productId).compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> myCourse(int start, int limit) {
        return Networks.getInstance().getUserApi()
                .myCourse(start, limit).compose(RxSchedulers.<ResponseBody>io_main());
    }


    /**
     * 我的课程pdf和mp4路径
     *
     * @return
     */
    @Override
    public Observable<ResponseBody> myCoursePdfAndUrl(String commodityId) {
        return Networks.getInstance().getUserApi()
                .myCoursePdfAndUrl(commodityId).compose(RxSchedulers.<ResponseBody>io_main());
    }


    /**
     * 保存进度值
     */
    @Override
    public Observable<ResponseBody> saveSchedule(String commodityId, double schedulePercent, String scheduleTitle, String scheduleId, String scheduleContent) {
        return Networks.getInstance().getUserApi()
                .saveSchedule(commodityId, schedulePercent, scheduleTitle, scheduleId, scheduleContent).compose(RxSchedulers.<ResponseBody>io_main());
    }

    /**
     * 提价错题
     * @param subjectCatalog
     * @param subjectId
     * @param userAnswer
     * @return
     */
    @Override
    public Observable<ResponseBody> submitError(String subjectCatalog, String subjectId, String userAnswer) {
        return Networks.getInstance().getUserApi()
                .submitError(subjectCatalog, subjectId, userAnswer).compose(RxSchedulers.<ResponseBody>io_main());
    }


}