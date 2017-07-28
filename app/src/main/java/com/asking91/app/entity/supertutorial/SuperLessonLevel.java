package com.asking91.app.entity.supertutorial;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

public class SuperLessonLevel implements Parcelable {

    @JSONField(name="versionLevelId")
    private String versionLevelId;

    @JSONField(name="textbook")
    private String textbook;

    @JSONField(name="versionId")
    private String versionId;

    @JSONField(name="levelId")
    private String levelId;

    @JSONField(name="state")
    private String state;

    @JSONField(name="type")
    private String type;

    @JSONField(name="versionName")
    private String versionName;

    @JSONField(name="purchased")
    private String purchased;

    @JSONField(name="purchasedMsg")
    private String purchasedMsg;

    public  boolean isSelect;

    public String getVersionLevelId() {
        return versionLevelId;
    }

    public void setVersionLevelId(String versionLevelId) {
        this.versionLevelId = versionLevelId;
    }

    public String getTextbook() {
        return textbook;
    }

    public void setTextbook(String textbook) {
        this.textbook = textbook;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
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
        dest.writeString(this.versionLevelId);
        dest.writeString(this.textbook);
        dest.writeString(this.versionId);
        dest.writeString(this.levelId);
        dest.writeString(this.state);
        dest.writeString(this.type);
        dest.writeString(this.versionName);
        dest.writeString(this.purchased);
        dest.writeString(this.purchasedMsg);
    }

    public SuperLessonLevel() {
    }

    protected SuperLessonLevel(Parcel in) {
        this.versionLevelId = in.readString();
        this.textbook = in.readString();
        this.versionId = in.readString();
        this.levelId = in.readString();
        this.state = in.readString();
        this.type = in.readString();
        this.versionName = in.readString();
        this.purchased = in.readString();
        this.purchasedMsg = in.readString();
    }

    public static final Creator<SuperLessonLevel> CREATOR = new Creator<SuperLessonLevel>() {
        @Override
        public SuperLessonLevel createFromParcel(Parcel source) {
            return new SuperLessonLevel(source);
        }

        @Override
        public SuperLessonLevel[] newArray(int size) {
            return new SuperLessonLevel[size];
        }
    };
}
