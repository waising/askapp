package com.asking91.app.entity.supertutorial;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wxiao on 2016/12/6.
 */

public class SuperBuyClearanceSubmitEntity {
    /**
     * id : 79750816-1474-4D05-97BB-41DB7AE95415
     * subjectDescription : null
     * subjectDescriptionHtml : null
     * tips : null
     * subjectKind : null
     * tabIds : null
     * optionsNum : null
     * options : null
     * rightAnswer : B
     * referSubjectId : null
     * gktState : false
     * ztState : false
     * ztSize : 0
     * userAnswer : C
     * right : false
     * errorAnswer : null
     * autopostSize : 0
     * answerSize : 0
     * answerRightSize : 0
     * haveChild : null
     * referSubjectTitle : null
     * subjectType : {"type_id":"1","type_name":null}
     * difficulty : 0
     * mistakeRate : null
     * subjectCode : null
     * columnNum : 2
     * stateName : null
     * createDate : null
     * detailsAnswer : null
     * detailsAnswerHtml : <p>解：与1非常接近的全体实数是不确定的，所以构不成集合，选项A不正确；<br/>某班17岁以下的学生，在班级确定的情况下17岁以下学生是明确的，满足几何元素的特点，故可以构成集合．所以选项B正确；<br/>高一年级视力比较好的同学是不确定的，所以选项C不正确；<br/>与无理数$\pi$相差很小的全体实数不确定，所以选项D不正确．<br/>故选B．</p>
     * createDateStr : null
     * state : null
     * createStaffId : null
     * approvalStaffId : null
     * subjectCatalogName : null
     * subjectCatalogCode : null
     * random : null
     * createStaffName : null
     */

    private String id;
    @SerializedName("subject_description")
    private Object subjectDescription;
    @SerializedName("subject_description_html")
    private Object subjectDescriptionHtml;
    private Object tips;
    private Object subjectKind;
    @SerializedName("tab_ids")
    private Object tabIds;
    @SerializedName("options_num")
    private Object optionsNum;
    private Object options;
    private String rightAnswer;
    @SerializedName("refer_subject_id")
    private Object referSubjectId;
    @SerializedName("gkt_state")
    private boolean gktState;
    @SerializedName("zt_state")
    private boolean ztState;
    @SerializedName("zt_size")
    private int ztSize;
    @SerializedName("user_answer")
    private String userAnswer;
    private boolean right;
    private Object errorAnswer;
    @SerializedName("autopost_size")
    private int autopostSize;
    @SerializedName("answer_size")
    private int answerSize;
    @SerializedName("answer_right_size")
    private int answerRightSize;
    @SerializedName("have_child")
    private Object haveChild;
    @SerializedName("refer_subject_title")
    private Object referSubjectTitle;
    @SerializedName("subject_type")
    private SubjectTypeBean subjectType;
    private int difficulty;
    @SerializedName("mistake_rate")
    private Object mistakeRate;
    @SerializedName("subject_code")
    private Object subjectCode;
    @SerializedName("column_num")
    private int columnNum;
    @SerializedName("state_name")
    private Object stateName;
    private Object createDate;
    @SerializedName("details_answer")
    private Object detailsAnswer;
    @SerializedName("details_answer_html")
    private String detailsAnswerHtml;
    private Object createDateStr;
    private Object state;
    @SerializedName("create_staff_id")
    private Object createStaffId;
    @SerializedName("approval_staff_id")
    private Object approvalStaffId;
    @SerializedName("subject_catalog_name")
    private Object subjectCatalogName;
    @SerializedName("subject_catalog_code")
    private Object subjectCatalogCode;
    private Object random;
    @SerializedName("create_staff_name")
    private Object createStaffName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(Object subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public Object getSubjectDescriptionHtml() {
        return subjectDescriptionHtml;
    }

    public void setSubjectDescriptionHtml(Object subjectDescriptionHtml) {
        this.subjectDescriptionHtml = subjectDescriptionHtml;
    }

    public Object getTips() {
        return tips;
    }

    public void setTips(Object tips) {
        this.tips = tips;
    }

    public Object getSubjectKind() {
        return subjectKind;
    }

    public void setSubjectKind(Object subjectKind) {
        this.subjectKind = subjectKind;
    }

    public Object getTabIds() {
        return tabIds;
    }

    public void setTabIds(Object tabIds) {
        this.tabIds = tabIds;
    }

    public Object getOptionsNum() {
        return optionsNum;
    }

    public void setOptionsNum(Object optionsNum) {
        this.optionsNum = optionsNum;
    }

    public Object getOptions() {
        return options;
    }

    public void setOptions(Object options) {
        this.options = options;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Object getReferSubjectId() {
        return referSubjectId;
    }

    public void setReferSubjectId(Object referSubjectId) {
        this.referSubjectId = referSubjectId;
    }

    public boolean isGktState() {
        return gktState;
    }

    public void setGktState(boolean gktState) {
        this.gktState = gktState;
    }

    public boolean isZtState() {
        return ztState;
    }

    public void setZtState(boolean ztState) {
        this.ztState = ztState;
    }

    public int getZtSize() {
        return ztSize;
    }

    public void setZtSize(int ztSize) {
        this.ztSize = ztSize;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public Object getErrorAnswer() {
        return errorAnswer;
    }

    public void setErrorAnswer(Object errorAnswer) {
        this.errorAnswer = errorAnswer;
    }

    public int getAutopostSize() {
        return autopostSize;
    }

    public void setAutopostSize(int autopostSize) {
        this.autopostSize = autopostSize;
    }

    public int getAnswerSize() {
        return answerSize;
    }

    public void setAnswerSize(int answerSize) {
        this.answerSize = answerSize;
    }

    public int getAnswerRightSize() {
        return answerRightSize;
    }

    public void setAnswerRightSize(int answerRightSize) {
        this.answerRightSize = answerRightSize;
    }

    public Object getHaveChild() {
        return haveChild;
    }

    public void setHaveChild(Object haveChild) {
        this.haveChild = haveChild;
    }

    public Object getReferSubjectTitle() {
        return referSubjectTitle;
    }

    public void setReferSubjectTitle(Object referSubjectTitle) {
        this.referSubjectTitle = referSubjectTitle;
    }

    public SubjectTypeBean getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectTypeBean subjectType) {
        this.subjectType = subjectType;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Object getMistakeRate() {
        return mistakeRate;
    }

    public void setMistakeRate(Object mistakeRate) {
        this.mistakeRate = mistakeRate;
    }

    public Object getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Object subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public Object getStateName() {
        return stateName;
    }

    public void setStateName(Object stateName) {
        this.stateName = stateName;
    }

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    public Object getDetailsAnswer() {
        return detailsAnswer;
    }

    public void setDetailsAnswer(Object detailsAnswer) {
        this.detailsAnswer = detailsAnswer;
    }

    public String getDetailsAnswerHtml() {
        return detailsAnswerHtml;
    }

    public void setDetailsAnswerHtml(String detailsAnswerHtml) {
        this.detailsAnswerHtml = detailsAnswerHtml;
    }

    public Object getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(Object createDateStr) {
        this.createDateStr = createDateStr;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getCreateStaffId() {
        return createStaffId;
    }

    public void setCreateStaffId(Object createStaffId) {
        this.createStaffId = createStaffId;
    }

    public Object getApprovalStaffId() {
        return approvalStaffId;
    }

    public void setApprovalStaffId(Object approvalStaffId) {
        this.approvalStaffId = approvalStaffId;
    }

    public Object getSubjectCatalogName() {
        return subjectCatalogName;
    }

    public void setSubjectCatalogName(Object subjectCatalogName) {
        this.subjectCatalogName = subjectCatalogName;
    }

    public Object getSubjectCatalogCode() {
        return subjectCatalogCode;
    }

    public void setSubjectCatalogCode(Object subjectCatalogCode) {
        this.subjectCatalogCode = subjectCatalogCode;
    }

    public Object getRandom() {
        return random;
    }

    public void setRandom(Object random) {
        this.random = random;
    }

    public Object getCreateStaffName() {
        return createStaffName;
    }

    public void setCreateStaffName(Object createStaffName) {
        this.createStaffName = createStaffName;
    }

    public static class SubjectTypeBean {
        /**
         * type_id : 1
         * type_name : null
         */

        private String type_id;
        private Object type_name;

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public Object getType_name() {
            return type_name;
        }

        public void setType_name(Object type_name) {
            this.type_name = type_name;
        }
    }
}
