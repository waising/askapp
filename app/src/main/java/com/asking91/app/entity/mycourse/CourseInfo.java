package com.asking91.app.entity.mycourse;

import com.asking91.app.entity.juniorTohigh.CourseDataArrayBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linbin on 2017/7/4.
 */


public class CourseInfo implements Serializable{


    /**
     * _id : 5955f39169bfa6057d6c003e
     * teacherImgUrl : http://diy.qqjay.com/u2/2014/1201/ebac41d88a492bddaefc628dfcf42df7.jpg
     * courseTypeName : 人教版
     * courseImgUrl : http://stcdn.91asking.com/ecc6be39-946d-4d31-9e25-8ab6fa87765e1498805111580
     * buyNumber : 1
     * isPresent : 0
     * description :
     * favorableStart : 2017-06-14 00:00:00
     * teacher : 张三
     * coursePrice : 1
     * enable : true
     * freeTime : 0
     * courseId : CS0701
     * marketArray : [{"marketName":"大概如此"}]
     * courseTypeId : BB01
     * commodityId : KC-KC03-XK01-BB01-CS0701
     * favorablePrice : 1
     * courseDataArray : [{"courseDataName":"七年级上","courseDataType":"ZL04","courseDataTypeName":"书本","courseDataUrl":""}]
     * timeLimit : 6
     * sequence : 1
     * courseName : 七年级上
     * teacherNickName :
     * createTime : 2017-06-30 15:35:52
     * favorableEnd : 2017-06-15 00:00:00
     * detail : <p>凄凄切切</p>
     * entryStaff : 管理员
     * targetUser :
     */

    private String _id;
    private String teacherImgUrl;
    private String courseTypeName;
    private String courseImgUrl;
    private int buyNumber;
    private int isPresent;
    private String description;
    private String favorableStart;
    private String teacher;
    private int coursePrice;
    private boolean enable;
    private int freeTime;
    private String courseId;
    private String courseTypeId;
    private String commodityId;
    private int favorablePrice;
    private int timeLimit;
    private int sequence;
    private String courseName;
    private String teacherNickName;
    private String createTime;
    private String favorableEnd;
    private String detail;
    private String entryStaff;
    private String targetUser;
    private List<MarketArrayBean> marketArray;
    private List<CourseDataArrayBean> courseDataArray;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTeacherImgUrl() {
        return teacherImgUrl;
    }

    public void setTeacherImgUrl(String teacherImgUrl) {
        this.teacherImgUrl = teacherImgUrl;
    }

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }

    public String getCourseImgUrl() {
        return courseImgUrl;
    }

    public void setCourseImgUrl(String courseImgUrl) {
        this.courseImgUrl = courseImgUrl;
    }

    public int getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(int buyNumber) {
        this.buyNumber = buyNumber;
    }

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFavorableStart() {
        return favorableStart;
    }

    public void setFavorableStart(String favorableStart) {
        this.favorableStart = favorableStart;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(int coursePrice) {
        this.coursePrice = coursePrice;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(int freeTime) {
        this.freeTime = freeTime;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getFavorablePrice() {
        return favorablePrice;
    }

    public void setFavorablePrice(int favorablePrice) {
        this.favorablePrice = favorablePrice;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherNickName() {
        return teacherNickName;
    }

    public void setTeacherNickName(String teacherNickName) {
        this.teacherNickName = teacherNickName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFavorableEnd() {
        return favorableEnd;
    }

    public void setFavorableEnd(String favorableEnd) {
        this.favorableEnd = favorableEnd;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEntryStaff() {
        return entryStaff;
    }

    public void setEntryStaff(String entryStaff) {
        this.entryStaff = entryStaff;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public List<MarketArrayBean> getMarketArray() {
        return marketArray;
    }

    public void setMarketArray(List<MarketArrayBean> marketArray) {
        this.marketArray = marketArray;
    }

    public List<CourseDataArrayBean> getCourseDataArray() {
        return courseDataArray;
    }

    public void setCourseDataArray(List<CourseDataArrayBean> courseDataArray) {
        this.courseDataArray = courseDataArray;
    }

    public static class MarketArrayBean implements Serializable{
        /**
         * marketName : 大概如此
         */

        private String marketName;

        public String getMarketName() {
            return marketName;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
        }
    }


}
