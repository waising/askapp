package com.asking91.app.entity.onlinetest;

import android.os.Parcel;
import android.os.Parcelable;

import com.asking91.app.entity.basepacket.AnswerOption;
import com.asking91.app.entity.basepacket.SubjectType;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.List;

/**
 * Created by wxwang on 2016/11/16.
 */
public class TopicEntity implements Parcelable {
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**选择操作的结果保存*/
    private String answer = "";


    @SerializedName("answer_right_size")
    private int answerRightSize;
    @SerializedName("answer_size")
    private int answerSize;
    @SerializedName("approval_staff_id")
    private String approvalStaffId;
    @SerializedName("autopost_size")
    private int autopostSize;
    @SerializedName("create_staff_id")
    private String createStaffId;
    @SerializedName("create_staff_name")
    private String createStaffName;
    private Date createDate;
    private String createDateStr;
    @SerializedName("details_answer")
    private String detailsAnswer;
    @SerializedName("details_answer_html")
    private String detailsAnswerHtml;
    private int difficulty;
    private String errorAnswer;
    @SerializedName("gkt_state")
    private boolean gktState;

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    @SerializedName("have_child")

    private String haveChild;
    private String id;
    @SerializedName("mistake_rate")
    private String mistakeRate;
    @SerializedName("options_num")
    private String optionsNum;
    private String random;
    @SerializedName("refer_subject_id")
    private String referSubjectId;
    @SerializedName("refer_subject_title")
    private String referSubjectTitle;
    private String right;
    @SerializedName("right_answer")
    private String rightAnswer;
    private String state;
    @SerializedName("state_name")
    private String stateName;
    @SerializedName("subject_catalog_code")
    private String subjectCatalogCode;
    @SerializedName("subject_catalog_name")
    private String subjectCatalogName;
    @SerializedName("subject_code")
    private String subjectCode;
    @SerializedName("subject_description")
    private String subjectDescription;
    @SerializedName("subject_description_html")
    private String subjectDescriptionHtml;

    public int getAnswerSize() {
        return answerSize;
    }

    public void setAnswerSize(int answerSize) {
        this.answerSize = answerSize;
    }

    public List<AnswerOption> getAnswerOptionList() {
        return answerOptionList;
    }

    public void setAnswerOptionList(List<AnswerOption> answerOptionList) {
        this.answerOptionList = answerOptionList;
    }

    public int getAnswerRightSize() {
        return answerRightSize;
    }

    public void setAnswerRightSize(int answerRightSize) {
        this.answerRightSize = answerRightSize;
    }

    public String getApprovalStaffId() {
        return approvalStaffId;
    }

    public void setApprovalStaffId(String approvalStaffId) {
        this.approvalStaffId = approvalStaffId;
    }

    public int getAutopostSize() {
        return autopostSize;
    }

    public void setAutopostSize(int autopostSize) {
        this.autopostSize = autopostSize;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
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

    public String getMistakeRate() {
        return mistakeRate;
    }

    public void setMistakeRate(String mistakeRate) {
        this.mistakeRate = mistakeRate;
    }

    public String getOptionsNum() {
        return optionsNum;
    }

    public void setOptionsNum(String optionsNum) {
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

    public String getTabIds() {
        return tabIds;
    }

    public void setTabIds(String tabIds) {
        this.tabIds = tabIds;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
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

    @SerializedName("subject_type")
    private SubjectType subjectType;
    private String subjectKind;
    @SerializedName("tab_ids")
    private String tabIds;
    private String tips;
    @SerializedName("user_answer")
    private String userAnswer;
    @SerializedName("zt_size")
    private int ztSize;
    @SerializedName("zt_state")
    private boolean ztState;
    @SerializedName("options")
    private List<AnswerOption> answerOptionList;

    public TopicEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.answerRightSize);
        dest.writeInt(this.answerSize);
        dest.writeString(this.approvalStaffId);
        dest.writeInt(this.autopostSize);
        dest.writeString(this.createStaffId);
        dest.writeString(this.createStaffName);
        dest.writeSerializable(this.createDate);
        dest.writeString(this.createDateStr);
        dest.writeString(this.detailsAnswer);
        dest.writeString(this.detailsAnswerHtml);
        dest.writeInt(this.difficulty);
        dest.writeString(this.errorAnswer);
        dest.writeByte(this.gktState ? (byte) 1 : (byte) 0);
        dest.writeString(this.haveChild);
        dest.writeString(this.id);
        dest.writeString(this.mistakeRate);
        dest.writeString(this.optionsNum);
        dest.writeString(this.random);
        dest.writeString(this.referSubjectId);
        dest.writeString(this.referSubjectTitle);
        dest.writeString(this.right);
        dest.writeString(this.rightAnswer);
        dest.writeString(this.state);
        dest.writeString(this.stateName);
        dest.writeString(this.subjectCatalogCode);
        dest.writeString(this.subjectCatalogName);
        dest.writeString(this.subjectCode);
        dest.writeString(this.subjectDescription);
        dest.writeString(this.subjectDescriptionHtml);
        dest.writeParcelable(this.subjectType, flags);
        dest.writeString(this.subjectKind);
        dest.writeString(this.tabIds);
        dest.writeString(this.tips);
        dest.writeString(this.userAnswer);
        dest.writeInt(this.ztSize);
        dest.writeByte(this.ztState ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.answerOptionList);
    }

    protected TopicEntity(Parcel in) {
        this.answerRightSize = in.readInt();
        this.answerSize = in.readInt();
        this.approvalStaffId = in.readString();
        this.autopostSize = in.readInt();
        this.createStaffId = in.readString();
        this.createStaffName = in.readString();
        this.createDate = (Date) in.readSerializable();
        this.createDateStr = in.readString();
        this.detailsAnswer = in.readString();
        this.detailsAnswerHtml = in.readString();
        this.difficulty = in.readInt();
        this.errorAnswer = in.readString();
        this.gktState = in.readByte() != 0;
        this.haveChild = in.readString();
        this.id = in.readString();
        this.mistakeRate = in.readString();
        this.optionsNum = in.readString();
        this.random = in.readString();
        this.referSubjectId = in.readString();
        this.referSubjectTitle = in.readString();
        this.right = in.readString();
        this.rightAnswer = in.readString();
        this.state = in.readString();
        this.stateName = in.readString();
        this.subjectCatalogCode = in.readString();
        this.subjectCatalogName = in.readString();
        this.subjectCode = in.readString();
        this.subjectDescription = in.readString();
        this.subjectDescriptionHtml = in.readString();
        this.subjectType = in.readParcelable(SubjectType.class.getClassLoader());
        this.subjectKind = in.readString();
        this.tabIds = in.readString();
        this.tips = in.readString();
        this.userAnswer = in.readString();
        this.ztSize = in.readInt();
        this.ztState = in.readByte() != 0;
        this.answerOptionList = in.createTypedArrayList(AnswerOption.CREATOR);
    }

    public static final Creator<TopicEntity> CREATOR = new Creator<TopicEntity>() {
        @Override
        public TopicEntity createFromParcel(Parcel source) {
            return new TopicEntity(source);
        }

        @Override
        public TopicEntity[] newArray(int size) {
            return new TopicEntity[size];
        }
    };
}
