package com.asking91.app.entity.onlinetest;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 结果 里面有正确答案
 * Created by wxwang on 2016/11/18.
 */
public class ResultEntity implements Parcelable,Serializable {

    public String getRightResult() {
        return rightResult;
    }

    public void setRightResult(String rightResult) {
        this.rightResult = rightResult;
    }

    public String getAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(String answerResult) {
        this.answerResult = answerResult;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    /**
     * 正确答案
     */
    private String rightResult;
    /**
     * 题目序号
     */
    private int num;
    //自己回答
    private String answerResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //题目ID
    private String id;

    public ResultEntity() {
    }
    public ResultEntity(String id,String answerResult,int num) {
        this.id = id;
        this.answerResult = answerResult;
        this.num = num;
    }

    public ResultEntity(String id,String answerResult,String rightResult,int num) {
        this.id = id;
        this.answerResult = answerResult;
        this.rightResult = rightResult;
        this.num = num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rightResult);
        dest.writeInt(this.num);
        dest.writeString(this.answerResult);
        dest.writeString(this.id);
    }

    protected ResultEntity(Parcel in) {
        this.rightResult = in.readString();
        this.num = in.readInt();
        this.answerResult = in.readString();
        this.id = in.readString();
    }

    public static final Creator<ResultEntity> CREATOR = new Creator<ResultEntity>() {
        @Override
        public ResultEntity createFromParcel(Parcel source) {
            return new ResultEntity(source);
        }

        @Override
        public ResultEntity[] newArray(int size) {
            return new ResultEntity[size];
        }
    };
}
