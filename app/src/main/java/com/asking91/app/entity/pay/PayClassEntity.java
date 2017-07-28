package com.asking91.app.entity.pay;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wxwang on 2016/11/30.
 */
public class PayClassEntity implements Parcelable {
    private String id;
    private String creatorId;
    private String commodityName;
    private String commodityPrice;
    private String stage;
    private String subjectCatalogId;
    private String subjectCatalogCode;
    private String subjectCatalogName;
    private String courseContent;

    public String getTextbookVersion() {
        return textbookVersion;
    }

    public void setTextbookVersion(String textbookVersion) {
        this.textbookVersion = textbookVersion;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
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

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public boolean isOnShelves() {
        return onShelves;
    }

    public void setOnShelves(boolean onShelves) {
        this.onShelves = onShelves;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getSubjectCatalogCode() {
        return subjectCatalogCode;
    }

    public void setSubjectCatalogCode(String subjectCatalogCode) {
        this.subjectCatalogCode = subjectCatalogCode;
    }

    public String getSubjectCatalogId() {
        return subjectCatalogId;
    }

    public void setSubjectCatalogId(String subjectCatalogId) {
        this.subjectCatalogId = subjectCatalogId;
    }

    public String getSubjectCatalogName() {
        return subjectCatalogName;
    }

    public void setSubjectCatalogName(String subjectCatalogName) {
        this.subjectCatalogName = subjectCatalogName;
    }

    private String textbookVersion;
    private boolean onShelves;
    private boolean delete;
    private String months;
    @SerializedName("level_id")
    private String levelId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.creatorId);
        dest.writeString(this.commodityName);
        dest.writeString(this.commodityPrice);
        dest.writeString(this.stage);
        dest.writeString(this.subjectCatalogId);
        dest.writeString(this.subjectCatalogCode);
        dest.writeString(this.subjectCatalogName);
        dest.writeString(this.courseContent);
        dest.writeString(this.textbookVersion);
        dest.writeByte(this.onShelves ? (byte) 1 : (byte) 0);
        dest.writeByte(this.delete ? (byte) 1 : (byte) 0);
        dest.writeString(this.months);
        dest.writeString(this.levelId);
    }

    public PayClassEntity() {
    }

    protected PayClassEntity(Parcel in) {
        this.id = in.readString();
        this.creatorId = in.readString();
        this.commodityName = in.readString();
        this.commodityPrice = in.readString();
        this.stage = in.readString();
        this.subjectCatalogId = in.readString();
        this.subjectCatalogCode = in.readString();
        this.subjectCatalogName = in.readString();
        this.courseContent = in.readString();
        this.textbookVersion = in.readString();
        this.onShelves = in.readByte() != 0;
        this.delete = in.readByte() != 0;
        this.months = in.readString();
        this.levelId = in.readString();
    }

    public static final Parcelable.Creator<PayClassEntity> CREATOR = new Parcelable.Creator<PayClassEntity>() {
        @Override
        public PayClassEntity createFromParcel(Parcel source) {
            return new PayClassEntity(source);
        }

        @Override
        public PayClassEntity[] newArray(int size) {
            return new PayClassEntity[size];
        }
    };
}
