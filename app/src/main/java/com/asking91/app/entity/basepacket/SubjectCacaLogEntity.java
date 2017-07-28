package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wxwang on 2016/11/11.
 */
public class SubjectCacaLogEntity implements Parcelable {
    @SerializedName("subject_catalog_id")
    private String subjectCatalogId;
    @SerializedName("subject_catalog_name")
    private String subjectCatalogName;
    @SerializedName("subject_catalog_code")
    private String subjectCatalogCode;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getSubjectCatalogCode() {
        return subjectCatalogCode;
    }

    public void setSubjectCatalogCode(String subjectCatalogCode) {
        this.subjectCatalogCode = subjectCatalogCode;
    }

    public String getSubjectCatalogId() {
        return subjectCatalogId;
    }

    public void setSubjectCatalogId(String subjectCatalogId) {
        this.subjectCatalogId = subjectCatalogId;
    }

    public String getSubjectCatalogName() {
        return subjectCatalogName;
    }

    public void setSubjectCatalogName(String subjectCatalogName) {
        this.subjectCatalogName = subjectCatalogName;
    }

    private boolean checked;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subjectCatalogId);
        dest.writeString(this.subjectCatalogName);
        dest.writeString(this.subjectCatalogCode);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    public SubjectCacaLogEntity() {
    }

    protected SubjectCacaLogEntity(Parcel in) {
        this.subjectCatalogId = in.readString();
        this.subjectCatalogName = in.readString();
        this.subjectCatalogCode = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SubjectCacaLogEntity> CREATOR = new Parcelable.Creator<SubjectCacaLogEntity>() {
        @Override
        public SubjectCacaLogEntity createFromParcel(Parcel source) {
            return new SubjectCacaLogEntity(source);
        }

        @Override
        public SubjectCacaLogEntity[] newArray(int size) {
            return new SubjectCacaLogEntity[size];
        }
    };
}
