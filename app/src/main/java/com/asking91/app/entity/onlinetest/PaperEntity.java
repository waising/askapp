package com.asking91.app.entity.onlinetest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wxwang on 2016/11/16.
 */
public class PaperEntity implements Parcelable {
    private int browseSize;
    @SerializedName("create_user_name")
    private String createUserName;
    private long createDate;
    private String createDateStr;
    private int difficulty;
    private int downSize;
    private boolean end;
    private String id;
    @SerializedName("level_id")
    private String levelId;
    @SerializedName("level_name")
    private String levelName;
    private boolean open;
    @SerializedName("operation_type")
    private int operationType;
    private int orgId;
    private String paperDesc;
    private String paperName;
    private String parentId;
    private int stage;

    public int getBrowseSize() {
        return browseSize;
    }

    public void setBrowseSize(int browseSize) {
        this.browseSize = browseSize;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDownSize() {
        return downSize;
    }

    public void setDownSize(int downSize) {
        this.downSize = downSize;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getPaperDesc() {
        return paperDesc;
    }

    public void setPaperDesc(String paperDesc) {
        this.paperDesc = paperDesc;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(String scoreNum) {
        this.scoreNum = scoreNum;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubjectCatalog() {
        return subjectCatalog;
    }

    public void setSubjectCatalog(String subjectCatalog) {
        this.subjectCatalog = subjectCatalog;
    }

    public String getSubNum() {
        return subNum;
    }

    public void setSubNum(String subNum) {
        this.subNum = subNum;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public List<TopicType> getTopicTypeList() {
        return topicTypeList;
    }

    public void setTopicTypeList(List<TopicType> topicTypeList) {
        this.topicTypeList = topicTypeList;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getXztErrNum() {
        return xztErrNum;
    }

    public void setXztErrNum(String xztErrNum) {
        this.xztErrNum = xztErrNum;
    }

    public String getXztNum() {
        return xztNum;
    }

    public void setXztNum(String xztNum) {
        this.xztNum = xztNum;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    private String state;
    private String subjectCatalog;

    @SerializedName("version_id")
    private String versionId;
    private String versionName;
    private String endTime;
    private String xztNum;
    private String xztErrNum;
    private String scoreNum;
    private String subNum;
    private String testTime;
    private String year;
    @SerializedName("topicTypeList")
    private List<TopicType> topicTypeList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.browseSize);
        dest.writeString(this.createUserName);
        dest.writeLong(this.createDate);
        dest.writeString(this.createDateStr);
        dest.writeInt(this.difficulty);
        dest.writeInt(this.downSize);
        dest.writeByte(this.end ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeString(this.levelId);
        dest.writeString(this.levelName);
        dest.writeByte(this.open ? (byte) 1 : (byte) 0);
        dest.writeInt(this.operationType);
        dest.writeInt(this.orgId);
        dest.writeString(this.paperDesc);
        dest.writeString(this.paperName);
        dest.writeString(this.parentId);
        dest.writeInt(this.stage);
        dest.writeString(this.state);
        dest.writeString(this.subjectCatalog);
        dest.writeString(this.versionId);
        dest.writeString(this.versionName);
        dest.writeString(this.endTime);
        dest.writeString(this.xztNum);
        dest.writeString(this.xztErrNum);
        dest.writeString(this.scoreNum);
        dest.writeString(this.subNum);
        dest.writeString(this.testTime);
        dest.writeString(this.year);
        dest.writeTypedList(this.topicTypeList);
    }

    public PaperEntity() {
    }

    protected PaperEntity(Parcel in) {
        this.browseSize = in.readInt();
        this.createUserName = in.readString();
        this.createDate = in.readLong();
        this.createDateStr = in.readString();
        this.difficulty = in.readInt();
        this.downSize = in.readInt();
        this.end = in.readByte() != 0;
        this.id = in.readString();
        this.levelId = in.readString();
        this.levelName = in.readString();
        this.open = in.readByte() != 0;
        this.operationType = in.readInt();
        this.orgId = in.readInt();
        this.paperDesc = in.readString();
        this.paperName = in.readString();
        this.parentId = in.readString();
        this.stage = in.readInt();
        this.state = in.readString();
        this.subjectCatalog = in.readString();
        this.versionId = in.readString();
        this.versionName = in.readString();
        this.endTime = in.readString();
        this.xztNum = in.readString();
        this.xztErrNum = in.readString();
        this.scoreNum = in.readString();
        this.subNum = in.readString();
        this.testTime = in.readString();
        this.year = in.readString();
        this.topicTypeList = in.createTypedArrayList(TopicType.CREATOR);
    }

    public static final Creator<PaperEntity> CREATOR = new Creator<PaperEntity>() {
        @Override
        public PaperEntity createFromParcel(Parcel source) {
            return new PaperEntity(source);
        }

        @Override
        public PaperEntity[] newArray(int size) {
            return new PaperEntity[size];
        }
    };
}
