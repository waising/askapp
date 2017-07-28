package com.asking91.app.entity.pay;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wxwang on 2016/11/30.
 */
public class PayEntity implements Parcelable {
    @SerializedName("orderId")
    private String orderId;
    @SerializedName("commodityId")
    private String commodityId;
    @SerializedName("rechargeId")
    private String rechargeId;
    @SerializedName("num")
    private int num;
    @SerializedName("versionId")
    private int versionId;
    @SerializedName("type")
    private String type;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }
    @SerializedName("payType")
    private String payType;
    private String clientIP;

    public PayEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.commodityId);
        dest.writeString(this.rechargeId);
        dest.writeInt(this.num);
        dest.writeInt(this.versionId);
        dest.writeString(this.type);
        dest.writeString(this.payType);
        dest.writeString(this.clientIP);
    }

    protected PayEntity(Parcel in) {
        this.orderId = in.readString();
        this.commodityId = in.readString();
        this.rechargeId = in.readString();
        this.num = in.readInt();
        this.versionId = in.readInt();
        this.type = in.readString();
        this.payType = in.readString();
        this.clientIP = in.readString();
    }

    public static final Creator<PayEntity> CREATOR = new Creator<PayEntity>() {
        @Override
        public PayEntity createFromParcel(Parcel source) {
            return new PayEntity(source);
        }

        @Override
        public PayEntity[] newArray(int size) {
            return new PayEntity[size];
        }
    };
}
