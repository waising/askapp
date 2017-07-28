package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class KnowledgeTypeDetailEntity implements Parcelable {
    @SerializedName("tab_id")
    private String tabId;
    @SerializedName("tab_content")
    private String tabContent;
    @SerializedName("tab_content_html")
    private String tabContentHtml;

    @SerializedName("answer_object_id")
    private String answerObjectId;

    @SerializedName("subject_kind_id")
    private String subjectKindId;

    @SerializedName("tab_type")
    private String tabType;

    @SerializedName("tab_type_name")
    private String tabTypeName;

    private int reviewSize;

    @SerializedName("order_id")
    private String orderId;

    public String getAnswerObjectId() {
        return answerObjectId;
    }

    public void setAnswerObjectId(String answerObjectId) {
        this.answerObjectId = answerObjectId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getReviewSize() {
        return reviewSize;
    }

    public void setReviewSize(int reviewSize) {
        this.reviewSize = reviewSize;
    }

    public String getSubjectKindId() {
        return subjectKindId;
    }

    public void setSubjectKindId(String subjectKindId) {
        this.subjectKindId = subjectKindId;
    }

    public String getTabContent() {
        return tabContent;
    }

    public void setTabContent(String tabContent) {
        this.tabContent = tabContent;
    }

    public String getTabContentHtml() {
        return tabContentHtml;
    }

    public void setTabContentHtml(String tabContentHtml) {
        this.tabContentHtml = tabContentHtml;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
    }

    public String getTabTypeName() {
        return tabTypeName;
    }

    public void setTabTypeName(String tabTypeName) {
        this.tabTypeName = tabTypeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tabId);
        dest.writeString(this.tabContent);
        dest.writeString(this.tabContentHtml);
        dest.writeString(this.answerObjectId);
        dest.writeString(this.subjectKindId);
        dest.writeString(this.tabType);
        dest.writeString(this.tabTypeName);
        dest.writeInt(this.reviewSize);
        dest.writeString(this.orderId);
    }

    public KnowledgeTypeDetailEntity() {
    }

    protected KnowledgeTypeDetailEntity(Parcel in) {
        this.tabId = in.readString();
        this.tabContent = in.readString();
        this.tabContentHtml = in.readString();
        this.answerObjectId = in.readString();
        this.subjectKindId = in.readString();
        this.tabType = in.readString();
        this.tabTypeName = in.readString();
        this.reviewSize = in.readInt();
        this.orderId = in.readString();
    }

    public static final Creator<KnowledgeTypeDetailEntity> CREATOR = new Creator<KnowledgeTypeDetailEntity>() {
        @Override
        public KnowledgeTypeDetailEntity createFromParcel(Parcel source) {
            return new KnowledgeTypeDetailEntity(source);
        }

        @Override
        public KnowledgeTypeDetailEntity[] newArray(int size) {
            return new KnowledgeTypeDetailEntity[size];
        }
    };
}
