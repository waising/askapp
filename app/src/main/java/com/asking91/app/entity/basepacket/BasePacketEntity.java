package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wxwang on 2016/10/28.
 */

public class BasePacketEntity implements Parcelable {

    public BasePacketEntity(){

    }

    public int getImgurl() {
        return imgurl;
    }

    public void setImgurl(int imgurl) {
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int imgurl;
    private String name;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.imgurl);
        dest.writeString(this.type);
    }

    public BasePacketEntity(Parcel in) {
        this.name = in.readString();
        this.imgurl = in.readInt();
        this.type = in.readString();
    }

    public static final Creator<BasePacketEntity> CREATOR = new Creator<BasePacketEntity>() {
        @Override
        public BasePacketEntity createFromParcel(Parcel source) {
            return new BasePacketEntity(source);
        }

        @Override
        public BasePacketEntity[] newArray(int size) {
            return new BasePacketEntity[size];
        }
    };
}
