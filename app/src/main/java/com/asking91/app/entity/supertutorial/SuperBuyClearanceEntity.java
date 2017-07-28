package com.asking91.app.entity.supertutorial;

import com.alibaba.fastjson.annotation.JSONField;
import com.asking91.app.entity.onlinetest.ResultEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxiao on 2016/12/4.
 */

public class SuperBuyClearanceEntity {

    private String id;
    @JSONField(name="subject_description")
    private String subjectDescription;
    @JSONField(name="subject_description_html")
    private String subjectDescriptionHtml;
    private String tips;
    private String subjectKind;
    @JSONField(name="tab_ids")
    private String tabIds;
    @JSONField(name="options_num")
    private String optionsNum;

    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    /**
     * 正确答案
     */
    @JSONField(name="right_answer")
    private String rightAnswer;
    @JSONField(name="refer_subject_id")
    private String referSubjectId;
    @JSONField(name="gkt_state")
    private boolean gktState;
    @JSONField(name="zt_state")
    private boolean ztState;
    @JSONField(name="zt_size")
    private int ztSize;
    @JSONField(name="user_answer")
    private String userAnswer;
    private String right;
    private String errorAnswer;
    @JSONField(name="autopost_size")
    private int autopostSize;
    @JSONField(name="answer_size")
    private int answerSize;
    @JSONField(name="answer_right_size")
    private int answerRightSize;
    @JSONField(name="have_child")
    private String haveChild;
    @JSONField(name="refer_subject_title")
    private String referSubjectTitle;
    @JSONField(name="subject_type")
    private SubjectTypeBean subjectType;
    private int difficulty;
    @JSONField(name="mistake_rate")
    private String mistakeRate;
    @JSONField(name="subject_code")
    private String subjectCode;
    @JSONField(name="column_num")
    private int columnNum;
    @JSONField(name="state_name")
    private String stateName;
    private String createDate;
    @JSONField(name="details_answer")
    private String detailsAnswer;
    @JSONField(name="details_answer_html")
    private String detailsAnswerHtml;
    private String createDateStr;
    private String state;
    @JSONField(name="create_staff_id")
    private String createStaffId;
    @JSONField(name="approval_staff_id")
    private String approvalStaffId;
    @JSONField(name="subject_catalog_name")
    private String subjectCatalogName;
    @JSONField(name="subject_catalog_code")
    private String subjectCatalogCode;
    private double random;
    @JSONField(name="create_staff_name")
    private String createStaffName;
    private List<OptionsBean> options = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public String getSubjectDescriptionHtml() {
        return subjectDescriptionHtml;
    }

    public void setSubjectDescriptionHtml(String subjectDescriptionHtml) {
        this.subjectDescriptionHtml = subjectDescriptionHtml;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getSubjectKind() {
        return subjectKind;
    }

    public void setSubjectKind(String subjectKind) {
        this.subjectKind = subjectKind;
    }

    public String getTabIds() {
        return tabIds;
    }

    public void setTabIds(String tabIds) {
        this.tabIds = tabIds;
    }

    public String getOptionsNum() {
        return optionsNum;
    }

    public void setOptionsNum(String optionsNum) {
        this.optionsNum = optionsNum;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getReferSubjectId() {
        return referSubjectId;
    }

    public void setReferSubjectId(String referSubjectId) {
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

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getErrorAnswer() {
        return errorAnswer;
    }

    public void setErrorAnswer(String errorAnswer) {
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

    public String getHaveChild() {
        return haveChild;
    }

    public void setHaveChild(String haveChild) {
        this.haveChild = haveChild;
    }

    public String getReferSubjectTitle() {
        return referSubjectTitle;
    }

    public void setReferSubjectTitle(String referSubjectTitle) {
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

    public String getMistakeRate() {
        return mistakeRate;
    }

    public void setMistakeRate(String mistakeRate) {
        this.mistakeRate = mistakeRate;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDetailsAnswer() {
        return detailsAnswer;
    }

    public void setDetailsAnswer(String detailsAnswer) {
        this.detailsAnswer = detailsAnswer;
    }

    public String getDetailsAnswerHtml() {
        return detailsAnswerHtml;
    }

    public void setDetailsAnswerHtml(String detailsAnswerHtml) {
        this.detailsAnswerHtml = detailsAnswerHtml;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateStaffId() {
        return createStaffId;
    }

    public void setCreateStaffId(String createStaffId) {
        this.createStaffId = createStaffId;
    }

    public String getApprovalStaffId() {
        return approvalStaffId;
    }

    public void setApprovalStaffId(String approvalStaffId) {
        this.approvalStaffId = approvalStaffId;
    }

    public String getSubjectCatalogName() {
        return subjectCatalogName;
    }

    public void setSubjectCatalogName(String subjectCatalogName) {
        this.subjectCatalogName = subjectCatalogName;
    }

    public String getSubjectCatalogCode() {
        return subjectCatalogCode;
    }

    public void setSubjectCatalogCode(String subjectCatalogCode) {
        this.subjectCatalogCode = subjectCatalogCode;
    }

    public double getRandom() {
        return random;
    }

    public void setRandom(double random) {
        this.random = random;
    }

    public String getCreateStaffName() {
        return createStaffName;
    }

    public void setCreateStaffName(String createStaffName) {
        this.createStaffName = createStaffName;
    }

    public List<OptionsBean> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsBean> options) {
        this.options = options;
    }

    public static class SubjectTypeBean {
        /**
         * typeId : 1
         * typeName : 选择题
         */
        @JSONField(name="type_id")
        private String typeId;
        @JSONField(name="type_name")
        private String typeName;

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }

    public static class OptionsBean {
        /**
         * id : null
         * optionName : A
         * optionContent : {1，2，3}
         * optionContentHtml : <p>{1，2，3}</p>
         */

        private String id;
        @JSONField(name="option_name")
        private String optionName;
        @JSONField(name="option_content")
        private String optionContent;
        @JSONField(name="option_content_html")
        private String optionContentHtml;

        private boolean isSelect;

        private ResultEntity resultEntity;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public String getOptionContent() {
            return optionContent;
        }

        public void setOptionContent(String optionContent) {
            this.optionContent = optionContent;
        }

        public String getOptionContentHtml() {
            return optionContentHtml;
        }

        public void setOptionContentHtml(String optionContentHtml) {
            this.optionContentHtml = optionContentHtml;
        }

        public ResultEntity getResultEntity() {
            return resultEntity;
        }

        public void setResultEntity(ResultEntity resultEntity) {
            this.resultEntity = resultEntity;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
