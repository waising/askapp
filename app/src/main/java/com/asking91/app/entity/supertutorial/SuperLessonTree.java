package com.asking91.app.entity.supertutorial;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SuperLessonTree implements Parcelable {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }

    public List<SuperLessonTree> getChildren() {
        return children;
    }

    public void setChildren(List<SuperLessonTree> children) {
        this.children = children;
    }

    private boolean leaf;

    private int free;

    private int purchased;//0未购买，1已购买

    private List<SuperLessonTree> children;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeByte(this.leaf ? (byte) 1 : (byte) 0);
        dest.writeInt(this.free);
        dest.writeInt(this.purchased);
        dest.writeTypedList(this.children);
    }

    public SuperLessonTree() {
    }

    protected SuperLessonTree(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.leaf = in.readByte() != 0;
        this.free = in.readInt();
        this.purchased = in.readInt();
        this.children = in.createTypedArrayList(SuperLessonTree.CREATOR);
    }

    public static final Creator<SuperLessonTree> CREATOR = new Creator<SuperLessonTree>() {
        @Override
        public SuperLessonTree createFromParcel(Parcel source) {
            return new SuperLessonTree(source);
        }

        @Override
        public SuperLessonTree[] newArray(int size) {
            return new SuperLessonTree[size];
        }
    };
}
