package com.asking91.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.asking91.app.common.BaseActivity;

/**
 * Created by wxwang on 2016/11/14.
 */
public class LabelEntity implements Parcelable {

    public LabelEntity(int icon,String name,Class<? extends BaseActivity> context){
        this.icon = icon;
        this.name = name;
        this.context = context;
    }

    public LabelEntity(int icon,String name){
        this.icon = icon;
        this.name = name;
    }

    public LabelEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public LabelEntity(String id, String name,Boolean isSelect) {
        this.id = id;
        this.name = name;
        this.isSelect = isSelect;
    }

    public LabelEntity(String id,String tmpId, String name) {
        this.id = id;
        this.tmpId = tmpId;
        this.name = name;
    }

    public LabelEntity(int icon,String id,String name){
        this.icon = icon;
        this.id = id;
        this.name = name;
    }

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

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

    private String name;
    private String id;
    private Boolean isSelect = false;

    public Class<? extends BaseActivity> getContext() {
        return context;
    }

    public void setContext(Class<? extends BaseActivity> context) {
        this.context = context;
    }

    private Class<? extends BaseActivity> context;

    public String getTmpId() {
        return tmpId;
    }

    public void setTmpId(String tmpId) {
        this.tmpId = tmpId;
    }

    private String tmpId;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    private int icon;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeValue(this.isSelect);
        dest.writeSerializable(this.context);
        dest.writeString(this.tmpId);
        dest.writeInt(this.icon);
    }

    protected LabelEntity(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.isSelect = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.context = (Class<? extends BaseActivity>) in.readSerializable();
        this.tmpId = in.readString();
        this.icon = in.readInt();
    }

    public static final Parcelable.Creator<LabelEntity> CREATOR = new Parcelable.Creator<LabelEntity>() {
        @Override
        public LabelEntity createFromParcel(Parcel source) {
            return new LabelEntity(source);
        }

        @Override
        public LabelEntity[] newArray(int size) {
            return new LabelEntity[size];
        }
    };
}
