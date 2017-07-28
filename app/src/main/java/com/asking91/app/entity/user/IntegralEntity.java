package com.asking91.app.entity.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wxwang on 2017/5/26.
 * 金币记录
 */

public class IntegralEntity implements Parcelable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isSignState() {
        return signState;
    }

    public void setSignState(boolean signState) {
        this.signState = signState;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    private String id;
    private String userId;
    private long createTime;
    private String fromUserId;
    private double integral;

    //1:减 2：加
    private int type;
    private int state;
    private boolean signState;
    private double count;
    private String fromType;
    private String fromId;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private String remark;

    public IntegralEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeLong(this.createTime);
        dest.writeString(this.fromUserId);
        dest.writeDouble(this.integral);
        dest.writeInt(this.type);
        dest.writeInt(this.state);
        dest.writeByte(this.signState ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.count);
        dest.writeString(this.fromType);
        dest.writeString(this.fromId);
        dest.writeString(this.remark);
    }

    protected IntegralEntity(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.createTime = in.readLong();
        this.fromUserId = in.readString();
        this.integral = in.readDouble();
        this.type = in.readInt();
        this.state = in.readInt();
        this.signState = in.readByte() != 0;
        this.count = in.readDouble();
        this.fromType = in.readString();
        this.fromId = in.readString();
        this.remark = in.readString();
    }

    public static final Creator<IntegralEntity> CREATOR = new Creator<IntegralEntity>() {
        @Override
        public IntegralEntity createFromParcel(Parcel source) {
            return new IntegralEntity(source);
        }

        @Override
        public IntegralEntity[] newArray(int size) {
            return new IntegralEntity[size];
        }
    };
}
