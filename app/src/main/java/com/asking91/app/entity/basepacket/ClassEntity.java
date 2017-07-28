package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wxwang on 2016/11/1.
 */
public class ClassEntity implements Parcelable {

    @SerializedName("version_id")
    private int versionId;

    @SerializedName("version_level_id")
    private int versionLevelId;

    @SerializedName("textbook")
    private String textbook;

    @SerializedName("level_code")
    private String levelCode;

    @SerializedName("level_id")
    private int levelId;

    @SerializedName("level_name")
    private String levelName;

    @SerializedName("state")
    private int state;

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTextbook() {
        return textbook;
    }

    public void setTextbook(String textbook) {
        this.textbook = textbook;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getVersionLevelId() {
        return versionLevelId;
    }

    public void setVersionLevelId(int versionLevelId) {
        this.versionLevelId = versionLevelId;
    }

    public ClassEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.versionId);
        dest.writeInt(this.versionLevelId);
        dest.writeString(this.textbook);
        dest.writeString(this.levelCode);
        dest.writeInt(this.levelId);
        dest.writeString(this.levelName);
        dest.writeInt(this.state);
    }

    protected ClassEntity(Parcel in) {
        this.versionId = in.readInt();
        this.versionLevelId = in.readInt();
        this.textbook = in.readString();
        this.levelCode = in.readString();
        this.levelId = in.readInt();
        this.levelName = in.readString();
        this.state = in.readInt();
    }

    public static final Creator<ClassEntity> CREATOR = new Creator<ClassEntity>() {
        @Override
        public ClassEntity createFromParcel(Parcel source) {
            return new ClassEntity(source);
        }

        @Override
        public ClassEntity[] newArray(int size) {
            return new ClassEntity[size];
        }
    };
}
