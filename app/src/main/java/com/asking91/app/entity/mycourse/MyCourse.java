package com.asking91.app.entity.mycourse;

import java.io.Serializable;
import java.util.List;

/**
 * 我的课程
 * Created by linbin on 2017/6/23.
 */

public class MyCourse implements Serializable{

    /**
     * 布局类型
     */
    private int type;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * commodityId : TC-TC-TBKT-01-TC01
     * commodityType : TC
     * schedulePercent : 0
     * scheduleTitle :
     * scheduleId :
     * createTime : 2017-06-29 16:42:56
     * commodityName : 初升高数学测试1
     * commodityTypeName : 同步课堂-初中数学
     * childCommodityList : [{"commodityId":"KC-KM01-123asd","commodityType":"KC","schedulePercent":0,"scheduleTitle":"","scheduleId":"","createTime":"2017-06-29 16:42:56","commodityName":"初升高数学测试2","commodityTypeName":"初升高衔接课-数学"},{"commodityId":"KC-KM01-12asd","commodityType":"KC","schedulePercent":0,"scheduleTitle":"","scheduleId":"","createTime":"2017-06-29 16:42:56","commodityName":"初升高数学测试2","commodityTypeName":"初升高衔接课-数学"},{"commodityId":"KC-KM01-1111","commodityType":"KC","schedulePercent":0,"scheduleTitle":"","scheduleId":"","createTime":"2017-06-29 16:42:56","commodityName":"初升高数学测试3","commodityTypeName":"初升高衔接课-数学"}]
     */

    private String commodityId;
    private String commodityType;
    private int schedulePercent;
    private String scheduleTitle;
    private String scheduleId;
    private String createTime;
    private String commodityName;
    private String commodityTypeName;




    private List<ChildCommodityListBean> childCommodityList;

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

    public int getSchedulePercent() {
        return schedulePercent;
    }

    public void setSchedulePercent(int schedulePercent) {
        this.schedulePercent = schedulePercent;
    }

    public String getScheduleTitle() {
        return scheduleTitle;
    }

    public void setScheduleTitle(String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityTypeName() {
        return commodityTypeName;
    }

    public void setCommodityTypeName(String commodityTypeName) {
        this.commodityTypeName = commodityTypeName;
    }

    public List<ChildCommodityListBean> getChildCommodityList() {
        return childCommodityList;
    }

    public void setChildCommodityList(List<ChildCommodityListBean> childCommodityList) {
        this.childCommodityList = childCommodityList;
    }

    public static class ChildCommodityListBean implements Serializable{
        /**
         * commodityId : KC-KM01-123asd
         * commodityType : KC
         * schedulePercent : 0
         * scheduleTitle :
         * scheduleId :
         * createTime : 2017-06-29 16:42:56
         * commodityName : 初升高数学测试2
         * commodityTypeName : 初升高衔接课-数学
         */

        private String commodityId;
        private String commodityType;
        private int schedulePercent;
        private String scheduleTitle;
        private String scheduleId;
        private String createTime;
        private String commodityName;
        private String commodityTypeName;


        private String scheduleContent;

        public String getScheduleContent() {
            return scheduleContent;
        }

        public void setScheduleContent(String scheduleContent) {
            this.scheduleContent = scheduleContent;
        }

        /**
         * 是否赠品
         */
        private int isPresent;


        public int getIsPresent() {
            return isPresent;
        }

        public void setIsPresent(int isPresent) {
            this.isPresent = isPresent;
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

        public int getSchedulePercent() {
            return schedulePercent;
        }

        public void setSchedulePercent(int schedulePercent) {
            this.schedulePercent = schedulePercent;
        }

        public String getScheduleTitle() {
            return scheduleTitle;
        }

        public void setScheduleTitle(String scheduleTitle) {
            this.scheduleTitle = scheduleTitle;
        }

        public String getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(String scheduleId) {
            this.scheduleId = scheduleId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getCommodityTypeName() {
            return commodityTypeName;
        }

        public void setCommodityTypeName(String commodityTypeName) {
            this.commodityTypeName = commodityTypeName;
        }
    }




}
