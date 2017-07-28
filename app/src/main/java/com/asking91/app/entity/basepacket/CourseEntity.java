package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wxwang on 2016/11/1.
 */
public class CourseEntity implements Parcelable {

    @SerializedName("version_id")
    private int versionId;

    @SerializedName("subject_catalog_id")
    private int subjectCatalogId;

    @SerializedName("subject_catalog_code")
    private String subjectCatalogCode;

    @SerializedName("subject_catalog_name")
    private String subjectCatalogName;

    @SerializedName("version_name")
    private String versionName;

    @SerializedName("version_state")
    private String versionState;

    @SerializedName("agency")
    private String agency;

    @SerializedName("stage")
    private int stage;

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getSubjectCatalogCode() {
        return subjectCatalogCode;
    }

    public void setSubjectCatalogCode(String subjectCatalogCode) {
        this.subjectCatalogCode = subjectCatalogCode;
    }

    public int getSubjectCatalogId() {
        return subjectCatalogId;
    }

    public void setSubjectCatalogId(int subjectCatalogId) {
        this.subjectCatalogId = subjectCatalogId;
    }

    public String getSubjectCatalogName() {
        return subjectCatalogName;
    }

    public void setSubjectCatalogName(String subjectCatalogName) {
        this.subjectCatalogName = subjectCatalogName;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionState() {
        return versionState;
    }

    public void setVersionState(String versionState) {
        this.versionState = versionState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.versionId);
        dest.writeInt(this.subjectCatalogId);
        dest.writeString(this.subjectCatalogCode);
        dest.writeString(this.subjectCatalogName);
        dest.writeString(this.versionName);
        dest.writeString(this.versionState);
        dest.writeString(this.agency);
        dest.writeInt(this.stage);
    }

    public CourseEntity() {
    }

    protected CourseEntity(Parcel in) {
        this.versionId = in.readInt();
        this.subjectCatalogId = in.readInt();
        this.subjectCatalogCode = in.readString();
        this.subjectCatalogName = in.readString();
        this.versionName = in.readString();
        this.versionState = in.readString();
        this.agency = in.readString();
        this.stage = in.readInt();
    }

    public static final Creator<CourseEntity> CREATOR = new Creator<CourseEntity>() {
        @Override
        public CourseEntity createFromParcel(Parcel source) {
            return new CourseEntity(source);
        }

        @Override
        public CourseEntity[] newArray(int size) {
            return new CourseEntity[size];
        }
    };

    public String getSubjectImgKey() {
        return versionName+":"+subjectCatalogCode+stage;
    }

}
