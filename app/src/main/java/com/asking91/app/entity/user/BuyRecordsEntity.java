package com.asking91.app.entity.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zqshen on 2016/12/13.
 */

public class BuyRecordsEntity implements Parcelable {

    private String id;
    private String userId;
    private String state;
    @SerializedName("total_fee")
    private String totalFee;
    @SerializedName("order_name")
    private String orderName;
    @SerializedName("pay_type")
    private String payType;
    private long createDate;
    @SerializedName("createDate_str")
    private String createDateStr;
    private long modifyDate;
    @SerializedName("modifyDate_str")
    private String modifyDateStr;
    private List<ProductsBean> products;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyDateStr() {
        return modifyDateStr;
    }

    public void setModifyDateStr(String modifyDateStr) {
        this.modifyDateStr = modifyDateStr;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean implements Parcelable {
        private String id;
        private String userId;
        private String stage;
        private int versionId;
        private String versionName;
        private long createDate;
        private String commodityId;
        private String commodityType;
        private String commodityName;
        private String commodityPrice;
        private String subjectCatalogId;
        private RechargeBean recharge;
        private String subjectCatalogCode;
        private String subjectCatalogName;
        private String courseContent;
        private String months;
        @SerializedName("level_id")
        private String levelId;
        private int num;
        private String state;

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

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public int getVersionId() {
            return versionId;
        }

        public void setVersionId(int versionId) {
            this.versionId = versionId;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityType() {
            return commodityType;
        }

        public void setCommodityType(String commodityType) {
            this.commodityType = commodityType;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getCommodityPrice() {
            return commodityPrice;
        }

        public void setCommodityPrice(String commodityPrice) {
            this.commodityPrice = commodityPrice;
        }

        public String getSubjectCatalogId() {
            return subjectCatalogId;
        }

        public void setSubjectCatalogId(String subjectCatalogId) {
            this.subjectCatalogId = subjectCatalogId;
        }

        public RechargeBean getRecharge() {
            return recharge;
        }

        public void setRecharge(RechargeBean recharge) {
            this.recharge = recharge;
        }

        public String getSubjectCatalogCode() {
            return subjectCatalogCode;
        }

        public void setSubjectCatalogCode(String subjectCatalogCode) {
            this.subjectCatalogCode = subjectCatalogCode;
        }

        public String getSubjectCatalogName() {
            return subjectCatalogName;
        }

        public void setSubjectCatalogName(String subjectCatalogName) {
            this.subjectCatalogName = subjectCatalogName;
        }

        public String getCourseContent() {
            return courseContent;
        }

        public void setCourseContent(String courseContent) {
            this.courseContent = courseContent;
        }

        public String getMonths() {
            return months;
        }

        public void setMonths(String months) {
            this.months = months;
        }

        public String getLevelId() {
            return levelId;
        }

        public void setLevelId(String levelId) {
            this.levelId = levelId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public static class RechargeBean implements Parcelable {

            private String id;
            private int price;
            private int value;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeInt(this.price);
                dest.writeInt(this.value);
            }

            public RechargeBean() {
            }

            protected RechargeBean(Parcel in) {
                this.id = in.readString();
                this.price = in.readInt();
                this.value = in.readInt();
            }

            public static final Creator<RechargeBean> CREATOR = new Creator<RechargeBean>() {
                @Override
                public RechargeBean createFromParcel(Parcel source) {
                    return new RechargeBean(source);
                }

                @Override
                public RechargeBean[] newArray(int size) {
                    return new RechargeBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.userId);
            dest.writeString(this.stage);
            dest.writeInt(this.versionId);
            dest.writeString(this.versionName);
            dest.writeLong(this.createDate);
            dest.writeString(this.commodityId);
            dest.writeString(this.commodityType);
            dest.writeString(this.commodityName);
            dest.writeString(this.commodityPrice);
            dest.writeString(this.subjectCatalogId);
            dest.writeParcelable(this.recharge, flags);
            dest.writeString(this.subjectCatalogCode);
            dest.writeString(this.subjectCatalogName);
            dest.writeString(this.courseContent);
            dest.writeString(this.months);
            dest.writeString(this.levelId);
            dest.writeInt(this.num);
            dest.writeString(this.state);
        }

        public ProductsBean() {
        }

        protected ProductsBean(Parcel in) {
            this.id = in.readString();
            this.userId = in.readString();
            this.stage = in.readString();
            this.versionId = in.readInt();
            this.versionName = in.readString();
            this.createDate = in.readLong();
            this.commodityId = in.readString();
            this.commodityType = in.readString();
            this.commodityName = in.readString();
            this.commodityPrice = in.readString();
            this.subjectCatalogId = in.readString();
            this.recharge = in.readParcelable(RechargeBean.class.getClassLoader());
            this.subjectCatalogCode = in.readString();
            this.subjectCatalogName = in.readString();
            this.courseContent = in.readString();
            this.months = in.readString();
            this.levelId = in.readString();
            this.num = in.readInt();
            this.state = in.readString();
        }

        public static final Creator<ProductsBean> CREATOR = new Creator<ProductsBean>() {
            @Override
            public ProductsBean createFromParcel(Parcel source) {
                return new ProductsBean(source);
            }

            @Override
            public ProductsBean[] newArray(int size) {
                return new ProductsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.state);
        dest.writeString(this.totalFee);
        dest.writeString(this.orderName);
        dest.writeString(this.payType);
        dest.writeLong(this.createDate);
        dest.writeString(this.createDateStr);
        dest.writeLong(this.modifyDate);
        dest.writeString(this.modifyDateStr);
        dest.writeList(this.products);
    }

    public BuyRecordsEntity() {
    }

    protected BuyRecordsEntity(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.state = in.readString();
        this.totalFee = in.readString();
        this.orderName = in.readString();
        this.payType = in.readString();
        this.createDate = in.readLong();
        this.createDateStr = in.readString();
        this.modifyDate = in.readLong();
        this.modifyDateStr = in.readString();
        this.products = new ArrayList<ProductsBean>();
        in.readList(this.products, ProductsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<BuyRecordsEntity> CREATOR = new Parcelable.Creator<BuyRecordsEntity>() {
        @Override
        public BuyRecordsEntity createFromParcel(Parcel source) {
            return new BuyRecordsEntity(source);
        }

        @Override
        public BuyRecordsEntity[] newArray(int size) {
            return new BuyRecordsEntity[size];
        }
    };
}
