package com.asking91.app.entity.oto;

/**
 * Created by wxiao on 2016/11/27.
 * 学生结账
 */

public class OtoEndCheckBillEntity {
    private int price;//:          消费阿思币,
    private String holdingTime;//:    通话时长,
    private String teacherName;//:   教师称谓,
    private String remainingTime;//:  剩余时长,
    private int score;//:          获得积分,
    private String teacherNumber;//: 老师编码,

    public int getRemainingAskCoin() {
        return remainingAskCoin;
    }

    public void setRemainingAskCoin(int remainingAskCoin) {
        this.remainingAskCoin = remainingAskCoin;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHoldingTime() {
        return holdingTime;
    }

    public void setHoldingTime(String holdingTime) {
        this.holdingTime = holdingTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    private int remainingAskCoin;//:剩余阿思币
}
