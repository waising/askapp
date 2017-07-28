package com.asking91.app.entity.supertutorial;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wxiao on 2016/10/18.
 */

public class SuperFreeBean3 implements Parcelable {
    private String parentId;//": null,
    private String text;//": "第1章 绪论",
    private boolean leaf;//": false,
    private String pid;//": null,
    private String qtip;//": null,
    private String iconCls;//";: null,
    private String level;//": null,
    private String children;//": null,
    private String id;//": "513",
    private String state;//": null,
    private String type;//": 0,
    private String attributes;//": null

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
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

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }


    public SuperFreeBean3() {
    }

    public SuperFreeBean3(JSONObject o) {
        try {
            setParentId(o.getString("parentId"));
            setText(o.getString("text"));
            setLeaf(o.getBoolean("leaf"));
            setPid(o.getString("pid"));
            setQtip(o.getString("qtip"));
            setIconCls(o.getString("iconCls"));
            setLevel(o.getString("level"));
            setChildren(o.getString("children"));
            setId(o.getString("id"));
            setState(o.getString("state"));
            setType(o.getString("type"));
            setAttributes(o.getString("attributes"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        dest.writeString(this.children);
        dest.writeString(this.id);
        dest.writeString(this.state);
        dest.writeString(this.type);
        dest.writeString(this.attributes);
    }

    protected SuperFreeBean3(Parcel in) {
        this.parentId = in.readString();
        this.text = in.readString();
        this.leaf = in.readByte() != 0;
        this.pid = in.readString();
        this.qtip = in.readString();
        this.iconCls = in.readString();
        this.level = in.readString();
        this.children = in.readString();
        this.id = in.readString();
        this.state = in.readString();
        this.type = in.readString();
        this.attributes = in.readString();
    }

    public static final Creator<SuperFreeBean3> CREATOR = new Creator<SuperFreeBean3>() {
        @Override
        public SuperFreeBean3 createFromParcel(Parcel source) {
            return new SuperFreeBean3(source);
        }

        @Override
        public SuperFreeBean3[] newArray(int size) {
            return new SuperFreeBean3[size];
        }
    };
}
