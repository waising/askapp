package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxwang on 2016/11/7.
 */
public class KnowledgeTypeEntity implements Parcelable {

    @SerializedName("kind_name")
    private String kindName;

    @SerializedName("kind_id")
    private String kindId;

    @SerializedName("connect_html")
    private String connectHtml;

    @SerializedName("tip_name")
    private String tipName;

    @SerializedName("subject")
    private SubjectEntity subjectEntity;

    private int reviewSize;
    private String type;

    @SerializedName("tabList")
    List<KnowledgeTypeDetailEntity> typeDetails;

    public String getConnectHtml() {
        return connectHtml;
    }

    public void setConnectHtml(String connectHtml) {
        this.connectHtml = connectHtml;
    }

    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public int getReviewSize() {
        return reviewSize;
    }

    public void setReviewSize(int reviewSize) {
        this.reviewSize = reviewSize;
    }

    public SubjectEntity getSubjectEntity() {
        return subjectEntity;
    }

    public void setSubjectEntity(SubjectEntity subjectEntity) {
        this.subjectEntity = subjectEntity;
    }

    public String getTipName() {
        return tipName;
    }

    public void setTipName(String tipName) {
        this.tipName = tipName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<KnowledgeTypeDetailEntity> getTypeDetails() {
        return typeDetails;
    }

    public void setTypeDetails(List<KnowledgeTypeDetailEntity> typeDetails) {
        this.typeDetails = typeDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kindName);
        dest.writeString(this.kindId);
        dest.writeString(this.connectHtml);
        dest.writeString(this.tipName);
        dest.writeParcelable(this.subjectEntity, flags);
        dest.writeInt(this.reviewSize);
        dest.writeString(this.type);
        dest.writeList(this.typeDetails);
    }

    public KnowledgeTypeEntity() {
    }

    protected KnowledgeTypeEntity(Parcel in) {
        this.kindName = in.readString();
        this.kindId = in.readString();
        this.connectHtml = in.readString();
        this.tipName = in.readString();
        this.subjectEntity = in.readParcelable(SubjectEntity.class.getClassLoader());
        this.reviewSize = in.readInt();
        this.type = in.readString();
        this.typeDetails = new ArrayList<KnowledgeTypeDetailEntity>();
        in.readList(this.typeDetails, KnowledgeTypeDetailEntity.class.getClassLoader());
    }

    public static final Creator<KnowledgeTypeEntity> CREATOR = new Creator<KnowledgeTypeEntity>() {
        @Override
        public KnowledgeTypeEntity createFromParcel(Parcel source) {
            return new KnowledgeTypeEntity(source);
        }

        @Override
        public KnowledgeTypeEntity[] newArray(int size) {
            return new KnowledgeTypeEntity[size];
        }
    };
}

