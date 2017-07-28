package com.asking91.app.entity.juniorTohigh;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by linbin on 2017/5/29.
 */

public class JuniorToHighEntity implements Serializable {


    /**
     * _id : 59292e1b4c0e2018cc0e4f9a
     * byeNumber : 10
     * courseTypeId : 123
     * description : aa
     * marketingId : 0.5
     * isActive : false
     * marketingName : 打五折
     * courseName : 测试课程47
     * teacher : 陈老师
     * createTime : 2017-05-27 15:43:23
     * coursePrice : 1
     * courseDataId : ["http://7xj9ur.com1.z0.glb.clouddn.com/%E7%AC%AC%E4%B8%80%E8%AE%B2%20%20%20%E5%9B%A0%E5%BC%8F%E5%88%86%E8%A7%A3.pdf","http://7xj9ur.com1.z0.glb.clouddn.com/2017.05.24-15.06.49%E7%8E%8B.mp4"]
     * detail : adfasdaaaaaaaaaaaa
     * targetUser : 7年级
     * purchaseState : 0
     * freeTime : 3
     */
    private String courseDataId;


    private String progress;
    private String _id;

    private String byeNumber;
    private String courseTypeId;
    private String commodityId;

    /**
     * 课程简介
     */
    private String description;
    private String marketingId;
    private boolean isActive;
    private String marketingName;
    private String courseName;
    private String teacher;
    private String createTime;
    private String coursePrice;
    private String detail;
    private String targetUser;
    private int purchaseState;
    private String freeTime;//免费试看时间
    private int purchasedNum;//购买人数
    private int buyNumber;//购买基数
    private String courseTypeName;//课程类型名称

    private String courseTypeFullName;//单套课程标签名

    private int isPresent;//是否赠品 1.赠品 0.非赠品赠品

    /**
     * 教师图片地址
     */
    private String teacherImgUrl;

    private String teacherNickName;


    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getTeacherNickName() {
        return teacherNickName;
    }

    public void setTeacherNickName(String teacherNickName) {
        this.teacherNickName = teacherNickName;
    }

    public String getTeacherImgUrl() {
        return teacherImgUrl;
    }

    public void setTeacherImgUrl(String teacherImgUrl) {
        this.teacherImgUrl = teacherImgUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(int buyNumber) {
        this.buyNumber = buyNumber;
    }



    private ArrayList<CourseDataArrayBean> courseDataArray;//pdf和播放路径的url

    public String getCourseTypeFullName() {
        return courseTypeFullName;
    }

    public void setCourseTypeFullName(String courseTypeFullName) {
        this.courseTypeFullName = courseTypeFullName;
    }

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getPurchasedNum() {
        return purchasedNum;
    }

    public void setPurchasedNum(int purchasedNum) {
        this.purchasedNum = purchasedNum;
    }

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }


    public String getByeNumber() {
        return byeNumber;
    }

    public void setByeNumber(String byeNumber) {
        this.byeNumber = byeNumber;
    }

    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(String marketingId) {
        this.marketingId = marketingId;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getMarketingName() {
        return marketingName;
    }

    public void setMarketingName(String marketingName) {
        this.marketingName = marketingName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
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

    public int getPurchaseState() {
        return purchaseState;
    }

    public void setPurchaseState(int purchaseState) {
        this.purchaseState = purchaseState;
    }

    public String getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(String freeTime) {
        this.freeTime = freeTime;
    }

    public ArrayList<CourseDataArrayBean> getCourseDataArray() {
        return courseDataArray;
    }

    public void setCourseDataArray(ArrayList<CourseDataArrayBean> courseDataArray) {
        this.courseDataArray = courseDataArray;
    }

    public String getCourseDataId() {
        return courseDataId;
    }

    public void setCourseDataId(String courseDataId) {
        this.courseDataId = courseDataId;
    }


}
