package com.asking91.app.entity.onlinetest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wxwang on 2016/11/16.
 */
public class TopicType implements Parcelable {
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

    public List<TopicEntity> getTopicEntityList() {
        return topicEntityList;
    }

    public void setTopicEntityList(List<TopicEntity> topicEntityList) {
        this.topicEntityList = topicEntityList;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    private String description;
    @SerializedName("list")
    private List<TopicEntity> topicEntityList;
    @SuppressWarnings("row_id")
    private int rowId;
    private String typeId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeTypedList(this.topicEntityList);
        dest.writeInt(this.rowId);
        dest.writeString(this.typeId);
    }

    public TopicType() {
    }

    protected TopicType(Parcel in) {
        this.description = in.readString();
        this.topicEntityList = in.createTypedArrayList(TopicEntity.CREATOR);
        this.rowId = in.readInt();
        this.typeId = in.readString();
    }

    public static final Parcelable.Creator<TopicType> CREATOR = new Parcelable.Creator<TopicType>() {
        @Override
        public TopicType createFromParcel(Parcel source) {
            return new TopicType(source);
        }

        @Override
        public TopicType[] newArray(int size) {
            return new TopicType[size];
        }
    };
}
