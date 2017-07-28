package com.asking91.app.entity.onlinetest;

import android.os.Parcel;
import android.os.Parcelable;

import com.asking91.app.entity.basepacket.SubjectEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wxwang on 2016/11/21.
 */
public class RightAnswerEntity implements Parcelable {
    private String description;
    @SerializedName("row_id")
    private int rowId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public List<SubjectEntity> getSubjectEntityList() {
        return subjectEntityList;
    }

    public void setSubjectEntityList(List<SubjectEntity> subjectEntityList) {
        this.subjectEntityList = subjectEntityList;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    private String typeId;
    @SerializedName("list")
    private List<SubjectEntity> subjectEntityList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeInt(this.rowId);
        dest.writeString(this.typeId);
        dest.writeTypedList(this.subjectEntityList);
    }

    public RightAnswerEntity() {
    }

    protected RightAnswerEntity(Parcel in) {
        this.description = in.readString();
        this.rowId = in.readInt();
        this.typeId = in.readString();
        this.subjectEntityList = in.createTypedArrayList(SubjectEntity.CREATOR);
    }

    public static final Parcelable.Creator<RightAnswerEntity> CREATOR = new Parcelable.Creator<RightAnswerEntity>() {
        @Override
        public RightAnswerEntity createFromParcel(Parcel source) {
            return new RightAnswerEntity(source);
        }

        @Override
        public RightAnswerEntity[] newArray(int size) {
            return new RightAnswerEntity[size];
        }
    };
}
