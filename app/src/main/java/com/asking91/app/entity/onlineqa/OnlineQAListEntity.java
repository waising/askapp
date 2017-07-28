package com.asking91.app.entity.onlineqa;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wxiao on 2016/11/1.
 */

public class OnlineQAListEntity implements Parcelable {
    private String id;//"id": "58132b3b0cf2f0c00a7fdc26",
    private String userId;//": "5760ab620cf2c34d483a1b46",
    private String userName;//": "啦啦",
    private String title;//": "求解答",
    private String description;//": "<p><img src=http://7xj9ur.com1.z0.glb.clouddn.com/2016-10-28/1477651250607747570.jpg title=1477651250506747570.jpg alt=\"阿思可\"/></p>",
    private String createDate;//": null,
    @SerializedName("createDate_fmt")
    private String createDateFmt;//    "createDate_fmt": "2016-10-28 18:40:59",
    private int state;//": "1",
    private int caifu;//": 0,
    private String km;//": "M2",
    private String levelId;//": null,
    private String supplementation;//": null,
    @SerializedName("answer_size")
    private int answerSize;//": 0,
    private String list;//": null

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateFmt() {
        return createDateFmt;
    }

    public void setCreateDateFmt(String createDateFmt) {
        this.createDateFmt = createDateFmt;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCaifu() {
        return caifu;
    }

    public void setCaifu(int caifu) {
        this.caifu = caifu;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getSupplementation() {
        return supplementation;
    }

    public void setSupplementation(String supplementation) {
        this.supplementation = supplementation;
    }

    public int getAnswerSize() {
        return answerSize;
    }

    public void setAnswerSize(int answerSize) {
        this.answerSize = answerSize;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.createDate);
        dest.writeString(this.createDateFmt);
        dest.writeInt(this.state);
        dest.writeInt(this.caifu);
        dest.writeString(this.km);
        dest.writeString(this.levelId);
        dest.writeString(this.supplementation);
        dest.writeInt(this.answerSize);
        dest.writeString(this.list);
    }

    public OnlineQAListEntity() {
    }

    protected OnlineQAListEntity(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.createDate = in.readString();
        this.createDateFmt = in.readString();
        this.state = in.readInt();
        this.caifu = in.readInt();
        this.km = in.readString();
        this.levelId = in.readString();
        this.supplementation = in.readString();
        this.answerSize = in.readInt();
        this.list = in.readString();
    }

    public static final Parcelable.Creator<OnlineQAListEntity> CREATOR = new Parcelable.Creator<OnlineQAListEntity>() {
        @Override
        public OnlineQAListEntity createFromParcel(Parcel source) {
            return new OnlineQAListEntity(source);
        }

        @Override
        public OnlineQAListEntity[] newArray(int size) {
            return new OnlineQAListEntity[size];
        }
    };
}
