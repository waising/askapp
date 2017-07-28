package com.asking91.app.entity.onlineqa;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wxiao on 2016/11/8.
 */

public class OnlineQaMyAnswerEntity {
    private int total;
    private List<OnlineQaMyAnswerDetailEntity> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OnlineQaMyAnswerDetailEntity> getList() {
        return list;
    }

    public void setList(List<OnlineQaMyAnswerDetailEntity> list) {
        this.list = list;
    }

    public class OnlineQaMyAnswerDetailEntity{

        private String id;//": "5811affef14cf6667f1445ad",
        private String userId;//": "5811afb8f14cc94c4d214e05",
        private String userName;//": "150****6792",
        private String title;//": "求解答",
        private String description;//": "1111<p><img src=http://7xj9ur.com1.z0.glb.clouddn.com/2016-10-27/1477554151189365427.jpg title=1477554151087365427.jpg alt=\"阿思可\"/></p>",
        private String createDate;//": null,
        private String state;//": "1",
        private String caifu;//": 0,
        private String km;//": "M3",

        public String getLevelId() {
            return levelId;
        }

        public void setLevelId(String levelId) {
            this.levelId = levelId;
        }

        private String levelId;//": null,
        private String supplementation;//": null,
        @SerializedName("answer_size")
        private String answerSize;//": 1,
        private String list;//": null

        @SerializedName("createDate_fmt")
        private String createDateFmt;//":"2016-02-02 22:25:42",

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreateDateFmt() {
            return createDateFmt;
        }

        public void setCreateDateFmt(String createDateFmt) {
            this.createDateFmt = createDateFmt;
        }

        public String getSupplementation() {
            return supplementation;
        }

        public void setSupplementation(String supplementation) {
            this.supplementation = supplementation;
        }

        public String getList() {
            return list;
        }

        public void setList(String list) {
            this.list = list;
        }

        public String getAnswerSize() {
            return answerSize;
        }

        public void setAnswerSize(String answerSize) {
            this.answerSize = answerSize;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCaifu() {
            return caifu;
        }

        public void setCaifu(String caifu) {
            this.caifu = caifu;
        }

        public String getKm() {
            return km;
        }

        public void setKm(String km) {
            this.km = km;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }


    }
}
