package com.asking91.app.entity.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wxwang on 2016/12/1.
 */
public class AddressEntity implements Parcelable {

    private int sort;
    @SerializedName("level_id")
    private int levelId;
    @SerializedName("region_code")
    private String regionCode;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @SerializedName("region_name")
    private String regionName;
    @SerializedName("parent_code")
    private String parentCode;
    private String id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sort);
        dest.writeInt(this.levelId);
        dest.writeString(this.regionCode);
        dest.writeString(this.regionName);
        dest.writeString(this.parentCode);
        dest.writeString(this.id);
    }

    public AddressEntity() {
    }

    protected AddressEntity(Parcel in) {
        this.sort = in.readInt();
        this.levelId = in.readInt();
        this.regionCode = in.readString();
        this.regionName = in.readString();
        this.parentCode = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<AddressEntity> CREATOR = new Parcelable.Creator<AddressEntity>() {
        @Override
        public AddressEntity createFromParcel(Parcel source) {
            return new AddressEntity(source);
        }

        @Override
        public AddressEntity[] newArray(int size) {
            return new AddressEntity[size];
        }
    };
}
