package com.asking91.app.entity.refer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wxwang on 2016/11/23.
 */
public class ReferEntity implements Parcelable {
    private String createDate;
    @SerializedName("next_id")
    private String nextId;
    private String connect;
    @SerializedName("createDate_fmt")
    private String createDateFmt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
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

    public String getHotFlag() {
        return hotFlag;
    }

    public void setHotFlag(String hotFlag) {
        this.hotFlag = hotFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(String newFlag) {
        this.newFlag = newFlag;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    @SerializedName("hot_flag")
    private String hotFlag;
    @SerializedName("new_flag")
    private String newFlag;
    @SerializedName("last_id")
    private String lastId;
    private String source;
    private String id;
    private String type;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createDate);
        dest.writeString(this.nextId);
        dest.writeString(this.connect);
        dest.writeString(this.createDateFmt);
        dest.writeString(this.title);
        dest.writeString(this.hotFlag);
        dest.writeString(this.newFlag);
        dest.writeString(this.lastId);
        dest.writeString(this.source);
        dest.writeString(this.id);
        dest.writeString(this.type);
    }

    public ReferEntity() {
    }

    protected ReferEntity(Parcel in) {
        this.createDate = in.readString();
        this.nextId = in.readString();
        this.connect = in.readString();
        this.createDateFmt = in.readString();
        this.title = in.readString();
        this.hotFlag = in.readString();
        this.newFlag = in.readString();
        this.lastId = in.readString();
        this.source = in.readString();
        this.id = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<ReferEntity> CREATOR = new Parcelable.Creator<ReferEntity>() {
        @Override
        public ReferEntity createFromParcel(Parcel source) {
            return new ReferEntity(source);
        }

        @Override
        public ReferEntity[] newArray(int size) {
            return new ReferEntity[size];
        }
    };
}
