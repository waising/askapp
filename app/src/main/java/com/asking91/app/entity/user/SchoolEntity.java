package com.asking91.app.entity.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wxwang on 2017/2/8.
 */
public class SchoolEntity implements Parcelable {
    private String id;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(String schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @SerializedName("school_name")
    private String schoolName;
    @SerializedName("region_code")
    private String regionCode;
    @SerializedName("school_level")
    private String schoolLevel;
    private int sort;

    private String address;
    private String mobilephone;
    private String telephone;
    private String postcode;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.schoolName);
        dest.writeString(this.regionCode);
        dest.writeString(this.schoolLevel);
        dest.writeInt(this.sort);
        dest.writeString(this.address);
        dest.writeString(this.mobilephone);
        dest.writeString(this.telephone);
        dest.writeString(this.postcode);
    }

    public SchoolEntity() {
    }

    protected SchoolEntity(Parcel in) {
        this.id = in.readString();
        this.schoolName = in.readString();
        this.regionCode = in.readString();
        this.schoolLevel = in.readString();
        this.sort = in.readInt();
        this.address = in.readString();
        this.mobilephone = in.readString();
        this.telephone = in.readString();
        this.postcode = in.readString();
    }

    public static final Parcelable.Creator<SchoolEntity> CREATOR = new Parcelable.Creator<SchoolEntity>() {
        @Override
        public SchoolEntity createFromParcel(Parcel source) {
            return new SchoolEntity(source);
        }

        @Override
        public SchoolEntity[] newArray(int size) {
            return new SchoolEntity[size];
        }
    };
}
