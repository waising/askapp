package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SubjectType implements Parcelable {

    @SerializedName("type_id")
    private String typeId;
    @SerializedName("type_name")
    private String typeName;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.typeId);
        dest.writeString(this.typeName);
    }

    public SubjectType() {
    }

    protected SubjectType(Parcel in) {
        this.typeId = in.readString();
        this.typeName = in.readString();
    }

    public static final Creator<SubjectType> CREATOR = new Creator<SubjectType>() {
        @Override
        public SubjectType createFromParcel(Parcel source) {
            return new SubjectType(source);
        }

        @Override
        public SubjectType[] newArray(int size) {
            return new SubjectType[size];
        }
    };
}
