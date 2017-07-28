package com.asking91.app.entity.onlineqa;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wxiao on 2016/11/8.
 */

public class OnlineQaMyAskEntity {
    private int total;
    private List<OnlineQaMyAskDetailEntity> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OnlineQaMyAskDetailEntity> getList() {
        return list;
    }

    public void setList(List<OnlineQaMyAskDetailEntity> list) {
        this.list = list;
    }

    public class OnlineQaMyAskDetailEntity{
        @SerializedName("answer_size")
        private int answerSize;//: 0
        private int caifu;//: 0
        private String createDate;//: null
        @SerializedName("createDate_fmt")
        private String createDateFmt;//: "2016-02-02 22:25:42"
        private String description;//: "<p>还是测试一下，看看如何</p>"
        private String id;//: "56b0bc660cf254b9b7682403"
        private String km;//: "M2"
        private String list;//: null
        private String state;//: "1"
        private String supplementation;//: null
        private String title;//: "再来一道吧"
        private String userId;//: "567b49546be637e7a5e3c812"
        private String userName;//: "CZ0001"
        private String levelId;//": "7",

        public String getLevelId() {
            return levelId;
        }

        public void setLevelId(String levelId) {
            this.levelId = levelId;
        }

        public int getAnswerSize() {
            return answerSize;
        }

        public void setAnswerSize(int answerSize) {
            this.answerSize = answerSize;
        }

        public int getCaifu() {
            return caifu;
        }

        public void setCaifu(int caifu) {
            this.caifu = caifu;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateDateFmt() {
            return createDateFmt;
        }

        public void setCreateDateFmt(String createDateFmt) {
            this.createDateFmt = createDateFmt;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKm() {
            return km;
        }

        public void setKm(String km) {
            this.km = km;
        }

        public String getList() {
            return list;
        }

        public void setList(String list) {
            this.list = list;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getSupplementation() {
            return supplementation;
        }

        public void setSupplementation(String supplementation) {
            this.supplementation = supplementation;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
