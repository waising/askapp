package com.asking91.app.entity.supertutorial;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wxiao on 2016/10/21.
 */

public class SuperFreeFragment1Bean implements Parcelable {
    private String attrId;//": 63,
    private String tip_id;//": "9",
    private String tip_name;//": "集合的含义与表示方法",
    private String typeName;//": "阿思可博士有话说",
    private String contentHtml;//

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getTip_id() {
        return tip_id;
    }

    public void setTip_id(String tip_id) {
        this.tip_id = tip_id;
    }

    public String getTip_name() {
        return tip_name;
    }

    public void setTip_name(String tip_name) {
        this.tip_name = tip_name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.attrId);
        dest.writeString(this.tip_id);
        dest.writeString(this.tip_name);
        dest.writeString(this.typeName);
        dest.writeString(this.contentHtml);
    }

    public SuperFreeFragment1Bean() {
    }

    public SuperFreeFragment1Bean(JSONObject o) {
        try {
            setAttrId(o.getString("attrId"));
            setTip_id(o.getString("tip_id"));
            setTip_name(o.getString("tip_name"));
            setTypeName(o.getString("typeName"));
            setContentHtml(o.getString("contentHtml"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected SuperFreeFragment1Bean(Parcel in) {
        this.attrId = in.readString();
        this.tip_id = in.readString();
        this.tip_name = in.readString();
        this.typeName = in.readString();
        this.contentHtml = in.readString();
    }

    public static final Creator<SuperFreeFragment1Bean> CREATOR = new Creator<SuperFreeFragment1Bean>() {
        @Override
        public SuperFreeFragment1Bean createFromParcel(Parcel source) {
            return new SuperFreeFragment1Bean(source);
        }

        @Override
        public SuperFreeFragment1Bean[] newArray(int size) {
            return new SuperFreeFragment1Bean[size];
        }
    };
}
