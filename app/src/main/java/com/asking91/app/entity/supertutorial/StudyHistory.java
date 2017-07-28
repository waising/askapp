package com.asking91.app.entity.supertutorial;

/**
 * Created by jswang on 2017/5/17.
 */

public class StudyHistory {

    /**
     * 0-超级辅导课  1-一轮  2-二论
     */
    public int type;

    public String studyName;

    public String[] values;
    public ExamReviewTree e;

    public StudyHistory() {
    }

    public StudyHistory(int type, String studyName, String... values) {
        this.type = type;
        this.studyName = studyName;
        this.values = values;
    }

    public StudyHistory(int type, String studyName, ExamReviewTree e, String... values) {
        this.type = type;
        this.studyName = studyName;
        this.e = e;
        this.values = values;
    }
}
