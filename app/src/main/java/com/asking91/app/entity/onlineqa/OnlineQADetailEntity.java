package com.asking91.app.entity.onlineqa;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wxiao on 2016/11/4.
 * 问题详情
 */

public class OnlineQADetailEntity {
    private String id;//": "5805d7520cf2872e1f37db85",
    private String userId;//": "57ed18337a81c3c9c883e9c7",
    private String userName;//": "CZ9000",
    private String title;//": "求解答",
    private String description;//": "<p><img src=\"http://7xj9ur.com1.z0.glb.clouddn.com/2016-10-18/1476777771856013384.png\" title=\"1476777771856013384.png\" alt=\"1476323775146080806.png\"/></p>",
    private String createDate;//": 1476777810044,
    @SerializedName("createDate_fmt")
    private String createDateFmt;//": "2016-10-18 16:03:30",
    private String state;//": "1",
    private int caifu;//": 0,
    private String km;//": "M",
    private String levelId;//": "7",
    @SerializedName("answer_size")
    private int answerSize;//": 1,
//    @SerializedName("userQA")
    private List<AnwserEntity> list;//"list":

    public AnwserMoreEntity createAnwserMoreEntity(String askTime, String ask, String answerTime, String answer){
        return new AnwserMoreEntity(askTime, ask, answerTime, answer);
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public int getCaifu() {
        return caifu;
    }

    public void setCaifu(int caifu) {
        this.caifu = caifu;
    }

    public int getAnswerSize() {
        return answerSize;
    }

    public void setAnswerSize(int answerSize) {
        this.answerSize = answerSize;
    }

    public List<AnwserEntity> getList() {
        return list;
    }

    public void setList(List<AnwserEntity> list) {
        this.list = list;
    }

    public class AnwserEntity{
        private String id;//": "f42a7314-95a5-4a20-a40a-5939166cd15e",
        //private int createDate;//": 1478224594643,
        @SerializedName("createDate_fmt")
        private String createDateFmt;//": "2016-11-04 09:56:34",
        private String userId;//": "5681dc026be6adfd05759b35",
        private String content;//": "<p>111</p>",
        private String createTime;
        @SerializedName("createTime_fmt")
        private String createTimeFmt;//": "<p>111</p>",
        private String userName;//": "CZ4001",
        private int type;//": 1,
        private int goodSize;//": 0,
        private int lousySize;//": 0,
        private boolean adopt;//": false
        private List<AnwserMoreEntity> list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        //public int getCreateDate() {
//            return createDate;
//        }

        //public void setCreateDate(int createDate) {
          //  this.createDate = createDate;
        //}

        public String getCreateDateFmt() {
            return createDateFmt;
        }

        public void setCreateDateFmt(String createDateFmt) {
            this.createDateFmt = createDateFmt;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTimeFmt() {
            return createTimeFmt;
        }

        public void setCreateTimeFmt(String createTimeFmt) {
            this.createTimeFmt = createTimeFmt;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getGoodSize() {
            return goodSize;
        }

        public void setGoodSize(int goodSize) {
            this.goodSize = goodSize;
        }

        public int getLousySize() {
            return lousySize;
        }

        public void setLousySize(int lousySize) {
            this.lousySize = lousySize;
        }

        public boolean isAdopt() {
            return adopt;
        }

        public void setAdopt(boolean adopt) {
            this.adopt = adopt;
        }

        public List<AnwserMoreEntity> getList() {
            return list;
        }

        public void setList(List<AnwserMoreEntity> list) {
            this.list = list;
        }
        //        "_id" : "311431c5-4ae8-47aa-9b14-9af7b471f7b8",
//                "createDate" : ISODate("2016-03-07T02:22:55.230Z"),
//        "createDateFmt" : "2016-03-07 10:22:55",
//                "userId" : "567b49556be637e7a5e3cff8",
//                "content" : "<p>这是什么意思哈。</p>",
//                "userName" : "GZ0123",
//                "type" : 1,
//                "goodSize" : 0,
//                "lousySize" : 0,
//                "adopt" : true,
//                "list"
    }

    public class AnwserMoreEntity {
        private String askTime;//": 1476867751110,
        private String ask;//": "<p>qqq</p>"
        private String answerTime;//" : ISODate("2016-03-07T02:28:45.556Z"),
        private String answer;//" : "<p>噢，还是不知道，算了<br/></p>"

        public String getAnswerId() {
            return answerId;
        }

        public void setAnswerId(String answerId) {
            this.answerId = answerId;
        }
        /**表示ID*/
        private String answerId;

        public AnwserMoreEntity(String askTime, String ask, String answerTime, String answer) {
            this.askTime = askTime;
            this.ask = ask;
            this.answerTime = answerTime;
            this.answer = answer;
        }

        public String getAskTime() {
            return askTime;
        }

        public void setAskTime(String askTime) {
            this.askTime = askTime;
        }

        public String getAsk() {
            return ask;
        }

        public void setAsk(String ask) {
            this.ask = ask;
        }


        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getAnswerTime() {

            return answerTime;
        }

        public void setAnswerTime(String answerTime) {
            this.answerTime = answerTime;
        }
    }

}
