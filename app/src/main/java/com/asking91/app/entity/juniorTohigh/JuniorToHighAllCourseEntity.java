package com.asking91.app.entity.juniorTohigh;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 全套课程
 * Created by linbin on 2017/5/29.
 */

public class JuniorToHighAllCourseEntity implements Serializable {


    /**
     * packagePrice : 1
     * description : 123
     * timeLimit : 1
     * imgUrl : 123
     * packageName : 初升高全套
     * detail : 123123123123
     * targetUser : 大叔
     * commodityId : 593527fe69bfa6084bcb6684
     */

    private String packagePrice;
    private String description;
    private int timeLimit;
    private String imgUrl;
    private String packageName;
    private String detail;
    private String targetUser;
    private String commodityId;
    private ArrayList<String> presentName;

    public ArrayList<String> getPresentName() {
        return presentName;
    }

    public void setPresentName(ArrayList<String> presentName) {
        this.presentName = presentName;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }
}
