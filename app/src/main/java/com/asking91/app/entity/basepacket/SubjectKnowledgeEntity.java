package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SubjectKnowledgeEntity implements Parcelable {
    @SerializedName("tip_name")
    private String tipName;

    @SerializedName("tip_id")
    private String tipId;

    private String version;

    @SerializedName("tip_code")
    private String tipCode;

    private String parentId;

    private boolean leaf;

    private int stage;

    @SerializedName("subject_id")
    private String subjectId;

    @SerializedName("level_id")
    private String levelId;

    private String qtip;
    @SerializedName("tip_name_py")
    private String tipNamePy;

    private int clicks;

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getQtip() {
        return qtip;
    }

    public void setQtip(String qtip) {
        this.qtip = qtip;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTipCode() {
        return tipCode;
    }

    public void setTipCode(String tipCode) {
        this.tipCode = tipCode;
    }

    public String getTipId() {
        return tipId;
    }

    public void setTipId(String tipId) {
        this.tipId = tipId;
    }

    public String getTipName() {
        return tipName;
    }

    public void setTipName(String tipName) {
        this.tipName = tipName;
    }

    public String getTipNamePy() {
        return tipNamePy;
    }

    public void setTipNamePy(String tipNamePy) {
        this.tipNamePy = tipNamePy;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tipName);
        dest.writeString(this.tipId);
        dest.writeString(this.version);
        dest.writeString(this.tipCode);
        dest.writeString(this.parentId);
        dest.writeByte(this.leaf ? (byte) 1 : (byte) 0);
        dest.writeInt(this.stage);
        dest.writeString(this.subjectId);
        dest.writeString(this.levelId);
        dest.writeString(this.qtip);
        dest.writeString(this.tipNamePy);
        dest.writeInt(this.clicks);
    }

    public SubjectKnowledgeEntity() {
    }

    protected SubjectKnowledgeEntity(Parcel in) {
        this.tipName = in.readString();
        this.tipId = in.readString();
        this.version = in.readString();
        this.tipCode = in.readString();
        this.parentId = in.readString();
        this.leaf = in.readByte() != 0;
        this.stage = in.readInt();
        this.subjectId = in.readString();
        this.levelId = in.readString();
        this.qtip = in.readString();
        this.tipNamePy = in.readString();
        this.clicks = in.readInt();
    }

    public static final Creator<SubjectKnowledgeEntity> CREATOR = new Creator<SubjectKnowledgeEntity>() {
        @Override
        public SubjectKnowledgeEntity createFromParcel(Parcel source) {
            return new SubjectKnowledgeEntity(source);
        }

        @Override
        public SubjectKnowledgeEntity[] newArray(int size) {
            return new SubjectKnowledgeEntity[size];
        }
    };
}
