package com.asking91.app.entity.supertutorial;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

public class SuperLesson implements Parcelable {

    @JSONField(name="versionId")
    private String versionId;

    @JSONField(name="versionName")
    private String versionName;

    @JSONField(name="versionState")
    private String versionState;

    @JSONField(name="subjectCatalogId")
    private String subjectCatalogId;

    @JSONField(name="stage")
    private String stage;

    @JSONField(name="agency")
    private String agency;

    @JSONField(name="sort")
    private String sort;

    @JSONField(name="purchased")
    private String purchased;

    @JSONField(name="purchasedMsg")
    private String purchasedMsg;

    public  boolean isSelect;


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

    public String getVersionState() {
        return versionState;
    }

    public void setVersionState(String versionState) {
        this.versionState = versionState;
    }

    public String getSubjectCatalogId() {
        return subjectCatalogId;
    }

    public void setSubjectCatalogId(String subjectCatalogId) {
        this.subjectCatalogId = subjectCatalogId;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPurchased() {
        return purchased;
    }

    public void setPurchased(String purchased) {
        this.purchased = purchased;
    }

    public String getPurchasedMsg() {
        return purchasedMsg;
    }

    public void setPurchasedMsg(String purchasedMsg) {
        this.purchasedMsg = purchasedMsg;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.versionId);
        dest.writeString(this.versionName);
        dest.writeString(this.versionState);
        dest.writeString(this.subjectCatalogId);
        dest.writeString(this.stage);
        dest.writeString(this.agency);
        dest.writeString(this.sort);
        dest.writeString(this.purchased);
        dest.writeString(this.purchasedMsg);
    }

    public SuperLesson() {
    }

    protected SuperLesson(Parcel in) {
        this.versionId = in.readString();
        this.versionName = in.readString();
        this.versionState = in.readString();
        this.subjectCatalogId = in.readString();
        this.stage = in.readString();
        this.agency = in.readString();
        this.sort = in.readString();
        this.purchased = in.readString();
        this.purchasedMsg = in.readString();
    }

    public static final Parcelable.Creator<SuperLesson> CREATOR = new Parcelable.Creator<SuperLesson>() {
        @Override
        public SuperLesson createFromParcel(Parcel source) {
            return new SuperLesson(source);
        }

        @Override
        public SuperLesson[] newArray(int size) {
            return new SuperLesson[size];
        }
    };
}
