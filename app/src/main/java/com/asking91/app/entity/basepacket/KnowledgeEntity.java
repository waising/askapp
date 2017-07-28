package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wxwang on 2016/11/1.
 */
public class KnowledgeEntity implements Parcelable {

    @SerializedName("parentId")
    private String parentId;

    @SerializedName("text")
    private String text;

    @SerializedName("leaf")
    private boolean leaf;

    @SerializedName("pid")
    private String pid;

    @SerializedName("qtip")
    private String qtip;

    @SerializedName("iconCls")
    private String iconCls;

    @SerializedName("level")
    private String level;

    @SerializedName("id")
    private String id;

    @SerializedName("state")
    private String state;

    @SerializedName("type")
    private int type;

    public boolean isShowType() {
        return showType;
    }

    public void setShowType(boolean showType) {
        this.showType = showType;
    }

    @SerializedName("show_type")
    private boolean showType;

    @SerializedName("attributes")
    private KnowledgeDetailEntity knowledgeDetailEntity;

    @SerializedName("children")
    private List<KnowledgeEntity> knowledgeList;

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public KnowledgeDetailEntity getKnowledgeDetailEntity() {
        return knowledgeDetailEntity;
    }

    public void setKnowledgeDetailEntity(KnowledgeDetailEntity knowledgeDetailEntity) {
        this.knowledgeDetailEntity = knowledgeDetailEntity;
    }

    public List<KnowledgeEntity> getKnowledgeList() {
        return knowledgeList;
    }

    public void setKnowledgeList(List<KnowledgeEntity> knowledgeList) {
        this.knowledgeList = knowledgeList;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQtip() {
        return qtip;
    }

    public void setQtip(String qtip) {
        this.qtip = qtip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public KnowledgeEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parentId);
        dest.writeString(this.text);
        dest.writeByte(this.leaf ? (byte) 1 : (byte) 0);
        dest.writeString(this.pid);
        dest.writeString(this.qtip);
        dest.writeString(this.iconCls);
        dest.writeString(this.level);
        dest.writeString(this.id);
        dest.writeString(this.state);
        dest.writeInt(this.type);
        dest.writeByte(this.showType ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.knowledgeDetailEntity, flags);
        dest.writeTypedList(this.knowledgeList);
    }

    protected KnowledgeEntity(Parcel in) {
        this.parentId = in.readString();
        this.text = in.readString();
        this.leaf = in.readByte() != 0;
        this.pid = in.readString();
        this.qtip = in.readString();
        this.iconCls = in.readString();
        this.level = in.readString();
        this.id = in.readString();
        this.state = in.readString();
        this.type = in.readInt();
        this.showType = in.readByte() != 0;
        this.knowledgeDetailEntity = in.readParcelable(KnowledgeDetailEntity.class.getClassLoader());
        this.knowledgeList = in.createTypedArrayList(KnowledgeEntity.CREATOR);
    }

    public static final Creator<KnowledgeEntity> CREATOR = new Creator<KnowledgeEntity>() {
        @Override
        public KnowledgeEntity createFromParcel(Parcel source) {
            return new KnowledgeEntity(source);
        }

        @Override
        public KnowledgeEntity[] newArray(int size) {
            return new KnowledgeEntity[size];
        }
    };
}
