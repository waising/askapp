package com.asking91.app.entity.supertutorial;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wxiao on 2016/10/18.
 *
 */

public class SuperSelectEntity implements Parcelable {
    @SerializedName("subject_catalog_id")
    private int subjectCatalogId;//": "2",
    @SerializedName("subject_catalog_name")
    private String subjectCatalogName;//": "数学",
    @SerializedName("subject_catalog_code")
    private String subjectCatalogCode;//": "M2",
    private boolean checked;//": true..


    public SuperSelectEntity() {
    }

    public SuperSelectEntity(JSONObject object) {
        try {
            setSubjectCatalogId(object.getInt("subjectCatalogId"));
            setSubjectCatalogName(object.getString("subjectCatalogName"));
            setSubjectCatalogCode(object.getString("subjectCatalogCode"));
            setChecked(object.getBoolean("checked"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getSubjectCatalogName() {
        return subjectCatalogName;
    }

    public int getSubjectCatalogId() {
        return subjectCatalogId;
    }

    public void setSubjectCatalogId(int subjectCatalogId) {
        this.subjectCatalogId = subjectCatalogId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setSubjectCatalogName(String subjectCatalogName) {
        this.subjectCatalogName = subjectCatalogName;

    }

    public String getSubjectCatalogCode() {
        return subjectCatalogCode;
    }

    public void setSubjectCatalogCode(String subjectCatalogCode) {
        this.subjectCatalogCode = subjectCatalogCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.subjectCatalogId);
        dest.writeString(this.subjectCatalogName);
        dest.writeString(this.subjectCatalogCode);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected SuperSelectEntity(Parcel in) {
        this.subjectCatalogId = in.readInt();
        this.subjectCatalogName = in.readString();
        this.subjectCatalogCode = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Creator<SuperSelectEntity> CREATOR = new Creator<SuperSelectEntity>() {
        @Override
        public SuperSelectEntity createFromParcel(Parcel source) {
            return new SuperSelectEntity(source);
        }

        @Override
        public SuperSelectEntity[] newArray(int size) {
            return new SuperSelectEntity[size];
        }
    };
}
