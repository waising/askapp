package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wxwang on 2016/11/1.
 */
public class KnowledgeDetailEntity implements Parcelable {

    @SerializedName("tip_name")
    private String tipName;

    @SerializedName("tip_id")
    private String tipId;

    @SerializedName("contentHtml")
    private String contentHtml;

    @SerializedName("attrId")
    private int attrId;

    @SerializedName("typeName")
    private String typeName;

    private int reviewSize;

    public int getAttrId() {
        return attrId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getTipId() {
        return tipId;
    }

    public void setTipId(String tipId) {
        this.tipId = tipId;
    }

    public String getTipName() {
        return tipName;
    }

    public void setTipName(String tipName) {
        this.tipName = tipName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public KnowledgeDetailEntity() {
    }

    public int getReviewSize() {
        return reviewSize;
    }

    public void setReviewSize(int reviewSize) {
        this.reviewSize = reviewSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tipName);
        dest.writeString(this.tipId);
        dest.writeString(this.contentHtml);
        dest.writeInt(this.attrId);
        dest.writeString(this.typeName);
        dest.writeInt(this.reviewSize);
    }

    protected KnowledgeDetailEntity(Parcel in) {
        this.tipName = in.readString();
        this.tipId = in.readString();
        this.contentHtml = in.readString();
        this.attrId = in.readInt();
        this.typeName = in.readString();
        this.reviewSize = in.readInt();
    }

    public static final Creator<KnowledgeDetailEntity> CREATOR = new Creator<KnowledgeDetailEntity>() {
        @Override
        public KnowledgeDetailEntity createFromParcel(Parcel source) {
            return new KnowledgeDetailEntity(source);
        }

        @Override
        public KnowledgeDetailEntity[] newArray(int size) {
            return new KnowledgeDetailEntity[size];
        }
    };
}
