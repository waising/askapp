package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wxwang on 2016/11/10.
 * 评论纠错
 */
public class CommentEntity implements Parcelable {
    private String id;
    private String subld;
    private String kindType;
    private String comment;
    private String createTime;
    private String objId;
    private String userName;
    @SerializedName("createTime_fmt")
    private String createTimeFmt;
    @SerializedName("tille")
    private String title;
    private String km; // km 指科目类型
    private String userId;
    private String state;
    private String tzState;

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKindType() {
        return kindType;
    }

    public void setKindType(String kindType) {
        this.kindType = kindType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubld() {
        return subld;
    }

    public void setSubld(String subld) {
        this.subld = subld;
    }

    public String getTzState() {
        return tzState;
    }

    public void setTzState(String tzState) {
        this.tzState = tzState;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
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

    private String advise;

    public String getCreateTimeFmt() {
        return createTimeFmt;
    }

    public void setCreateTimeFmt(String createTimeFmt) {
        this.createTimeFmt = createTimeFmt;
    }

    public String geTtille() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CommentEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.subld);
        dest.writeString(this.kindType);
        dest.writeString(this.comment);
        dest.writeString(this.createTime);
        dest.writeString(this.objId);
        dest.writeString(this.userName);
        dest.writeString(this.createTimeFmt);
        dest.writeString(this.title);
        dest.writeString(this.km);
        dest.writeString(this.userId);
        dest.writeString(this.state);
        dest.writeString(this.tzState);
        dest.writeString(this.advise);
    }

    protected CommentEntity(Parcel in) {
        this.id = in.readString();
        this.subld = in.readString();
        this.kindType = in.readString();
        this.comment = in.readString();
        this.createTime = in.readString();
        this.objId = in.readString();
        this.userName = in.readString();
        this.createTimeFmt = in.readString();
        this.title = in.readString();
        this.km = in.readString();
        this.userId = in.readString();
        this.state = in.readString();
        this.tzState = in.readString();
        this.advise = in.readString();
    }

    public static final Creator<CommentEntity> CREATOR = new Creator<CommentEntity>() {
        @Override
        public CommentEntity createFromParcel(Parcel source) {
            return new CommentEntity(source);
        }

        @Override
        public CommentEntity[] newArray(int size) {
            return new CommentEntity[size];
        }
    };
}
