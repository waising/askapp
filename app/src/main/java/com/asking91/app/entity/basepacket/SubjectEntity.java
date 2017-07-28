package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxwang on 2016/11/7.
 */
public class SubjectEntity implements Parcelable {
    public String getSelectAnswer() {
        return selectAnswer;
    }

    public void setSelectAnswer(String selectAnswer) {
        this.selectAnswer = selectAnswer;
    }
//_______________超级辅导课-来讲题-查询解析-变式题使用_________________
    /**选择的结果--*/
    private String selectAnswer;
    /**是否提交过*/
    private boolean isSubmit;

//______________超级辅导课-来讲题-查询解析-变式题使用__________________

    public boolean isSubmit() {
        return isSubmit;
    }

    public void setSubmit(boolean submit) {
        isSubmit = submit;
    }



    @SerializedName("createDate")
    private String createDate;

    @SerializedName("user_answer")
    private String userAnswer;

    @SerializedName("subjectKind")
    private String subjectKind;

    @SerializedName("subject_description_html")
    private String subjectDescriptionHtml;

    @SerializedName("right_answer")
    private String rightAnswer;

    @SerializedName("subject_catalog_code")
    private String subjectCatalogCode;

    @SerializedName("subject_catalog_name")
    private String subjectCatalogName;

    @SerializedName("createDateStr")
    private String createDateStr;

    @SerializedName("details_answer_html")
    private String detailsAnswerHtml;

    @SerializedName("answer_right_size")
    private int answerRightSize;

    @SerializedName("answer_size")
    private int answerSize;

    @SerializedName("approval_staff_id")
    private String approvalStaffId;

    @SerializedName("create_staff_id")
    private String createStaffId;

    @SerializedName("create_staff_name")
    private String createStaffName;

    @SerializedName("details_answer")
    private String detailsAnswer;

    @SerializedName("difficulty")
    private int difficulty;

    @SerializedName("have_child")
    private String haveChild;

    @SerializedName("mistake_rate")
    private String mistakeRate;

    @SerializedName("options_num")
    private int optionsNum;

    private String random;

    @SerializedName("refer_subject_id")
    private String referSubjectId;

    @SerializedName("refer_subject_title")
    private String referSubjectTitle;

    @SerializedName("state_name")
    private String stateName;

    @SerializedName("subject_code")
    private String subjectCode;

    @SerializedName("subject_description")
    private String subjectDescription;

    @SerializedName("subject_type")
    private SubjectType subjectType;

    @SerializedName("tab_ids")
    private String tabIds;

    @SerializedName("zt_state")
    private boolean ztState;

    @SerializedName("zt_size")
    private int ztSize;

    @SerializedName("gkt_state")
    private boolean gktState;

    private String errorAnswer;

    @SerializedName("options")
    List<AnswerOption> answerOptions;

    @SerializedName("tips")
    List<SubjectKnowledgeEntity> subjectKnowledgeEntityNames;

    private String right;
    private String id;
    private String state;

    public SubjectEntity() {
    }


    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public int getAnswerRightSize() {
        return answerRightSize;
    }

    public void setAnswerRightSize(int answerRightSize) {
        this.answerRightSize = answerRightSize;
    }

    public int getAnswerSize() {
        return answerSize;
    }

    public void setAnswerSize(int answerSize) {
        this.answerSize = answerSize;
    }

    public String getApprovalStaffId() {
        return approvalStaffId;
    }

    public void setApprovalStaffId(String approvalStaffId) {
        this.approvalStaffId = approvalStaffId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getCreateStaffId() {
        return createStaffId;
    }

    public void setCreateStaffId(String createStaffId) {
        this.createStaffId = createStaffId;
    }

    public String getCreateStaffName() {
        return createStaffName;
    }

    public void setCreateStaffName(String createStaffName) {
        this.createStaffName = createStaffName;
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getErrorAnswer() {
        return errorAnswer;
    }

    public void setErrorAnswer(String errorAnswer) {
        this.errorAnswer = errorAnswer;
    }

    public boolean isGktState() {
        return gktState;
    }

    public void setGktState(boolean gktState) {
        this.gktState = gktState;
    }

    public String getHaveChild() {
        return haveChild;
    }

    public void setHaveChild(String haveChild) {
        this.haveChild = haveChild;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SubjectKnowledgeEntity> getSubjectKnowledgeEntityNames() {
        return subjectKnowledgeEntityNames;
    }

    public void setSubjectKnowledgeEntityNames(List<SubjectKnowledgeEntity> subjectKnowledgeEntityNames) {
        this.subjectKnowledgeEntityNames = subjectKnowledgeEntityNames;
    }

    public String getMistakeRate() {
        return mistakeRate;
    }

    public void setMistakeRate(String mistakeRate) {
        this.mistakeRate = mistakeRate;
    }

    public int getOptionsNum() {
        return optionsNum;
    }

    public void setOptionsNum(int optionsNum) {
        this.optionsNum = optionsNum;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getReferSubjectId() {
        return referSubjectId;
    }

    public void setReferSubjectId(String referSubjectId) {
        this.referSubjectId = referSubjectId;
    }

    public String getReferSubjectTitle() {
        return referSubjectTitle;
    }

    public void setReferSubjectTitle(String referSubjectTitle) {
        this.referSubjectTitle = referSubjectTitle;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getSubjectCatalogCode() {
        return subjectCatalogCode;
    }

    public void setSubjectCatalogCode(String subjectCatalogCode) {
        this.subjectCatalogCode = subjectCatalogCode;
    }

    public String getSubjectCatalogName() {
        return subjectCatalogName;
    }

    public void setSubjectCatalogName(String subjectCatalogName) {
        this.subjectCatalogName = subjectCatalogName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
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

    public String getSubjectKind() {
        return subjectKind;
    }

    public void setSubjectKind(String subjectKind) {
        this.subjectKind = subjectKind;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public String getTabIds() {
        return tabIds;
    }

    public void setTabIds(String tabIds) {
        this.tabIds = tabIds;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getZtSize() {
        return ztSize;
    }

    public void setZtSize(int ztSize) {
        this.ztSize = ztSize;
    }

    public boolean isZtState() {
        return ztState;
    }

    public void setZtState(boolean ztState) {
        this.ztState = ztState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createDate);
        dest.writeString(this.userAnswer);
        dest.writeString(this.subjectKind);
        dest.writeString(this.subjectDescriptionHtml);
        dest.writeString(this.rightAnswer);
        dest.writeString(this.subjectCatalogCode);
        dest.writeString(this.subjectCatalogName);
        dest.writeString(this.createDateStr);
        dest.writeString(this.detailsAnswerHtml);
        dest.writeInt(this.answerRightSize);
        dest.writeInt(this.answerSize);
        dest.writeString(this.approvalStaffId);
        dest.writeString(this.createStaffId);
        dest.writeString(this.createStaffName);
        dest.writeString(this.detailsAnswer);
        dest.writeInt(this.difficulty);
        dest.writeString(this.haveChild);
        dest.writeString(this.mistakeRate);
        dest.writeInt(this.optionsNum);
        dest.writeString(this.random);
        dest.writeString(this.referSubjectId);
        dest.writeString(this.referSubjectTitle);
        dest.writeString(this.stateName);
        dest.writeString(this.subjectCode);
        dest.writeString(this.subjectDescription);
        dest.writeParcelable(this.subjectType, flags);
        dest.writeString(this.tabIds);
        dest.writeByte(this.ztState ? (byte) 1 : (byte) 0);
        dest.writeInt(this.ztSize);
        dest.writeByte(this.gktState ? (byte) 1 : (byte) 0);
        dest.writeString(this.errorAnswer);
        dest.writeList(this.answerOptions);
        dest.writeList(this.subjectKnowledgeEntityNames);
        dest.writeString(this.right);
        dest.writeString(this.id);
        dest.writeString(this.state);
    }

    protected SubjectEntity(Parcel in) {
        this.createDate = in.readString();
        this.userAnswer = in.readString();
        this.subjectKind = in.readString();
        this.subjectDescriptionHtml = in.readString();
        this.rightAnswer = in.readString();
        this.subjectCatalogCode = in.readString();
        this.subjectCatalogName = in.readString();
        this.createDateStr = in.readString();
        this.detailsAnswerHtml = in.readString();
        this.answerRightSize = in.readInt();
        this.answerSize = in.readInt();
        this.approvalStaffId = in.readString();
        this.createStaffId = in.readString();
        this.createStaffName = in.readString();
        this.detailsAnswer = in.readString();
        this.difficulty = in.readInt();
        this.haveChild = in.readString();
        this.mistakeRate = in.readString();
        this.optionsNum = in.readInt();
        this.random = in.readString();
        this.referSubjectId = in.readString();
        this.referSubjectTitle = in.readString();
        this.stateName = in.readString();
        this.subjectCode = in.readString();
        this.subjectDescription = in.readString();
        this.subjectType = in.readParcelable(SubjectType.class.getClassLoader());
        this.tabIds = in.readString();
        this.ztState = in.readByte() != 0;
        this.ztSize = in.readInt();
        this.gktState = in.readByte() != 0;
        this.errorAnswer = in.readString();
        this.answerOptions = new ArrayList<AnswerOption>();
        in.readList(this.answerOptions, AnswerOption.class.getClassLoader());
        this.subjectKnowledgeEntityNames = new ArrayList<SubjectKnowledgeEntity>();
        in.readList(this.subjectKnowledgeEntityNames, SubjectKnowledgeEntity.class.getClassLoader());
        this.right = in.readString();
        this.id = in.readString();
        this.state = in.readString();
    }

    public static final Creator<SubjectEntity> CREATOR = new Creator<SubjectEntity>() {
        @Override
        public SubjectEntity createFromParcel(Parcel source) {
            return new SubjectEntity(source);
        }

        @Override
        public SubjectEntity[] newArray(int size) {
            return new SubjectEntity[size];
        }
    };
}

