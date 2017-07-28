package com.asking91.app.entity.pay;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by jswang on 2017/5/16.
 */

public class PayBookInfo {
    @JSONField(name="_id")
    private String gid;

    private String gradeName;
    private String gradeId;
    private String editionName;
    private String editionId;
    private String commodityId;
    private String commodityType;
    private String subjectId;
    private String versionLevelName;
    private String imgUrl;
    private String versionLevelId;
    private String createTime;
    private String price;
    private String subjectName;
    private String commodityName;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getEditionName() {
        return editionName;
    }

    public void setEditionName(String editionName) {
        this.editionName = editionName;
    }

    public String getEditionId() {
        return editionId;
    }

    public void setEditionId(String editionId) {
        this.editionId = editionId;
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

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getVersionLevelName() {
        return versionLevelName;
    }

    public void setVersionLevelName(String versionLevelName) {
        this.versionLevelName = versionLevelName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVersionLevelId() {
        return versionLevelId;
    }

    public void setVersionLevelId(String versionLevelId) {
        this.versionLevelId = versionLevelId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }
}
