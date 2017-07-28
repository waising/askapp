package com.asking91.app.entity.onlinetest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxwang on 2016/11/19.
 */
public class CommitPaperEntity implements Parcelable {

    public Answer answer(String id,String userAnswer){
        return new Answer(id,userAnswer);
    }

    public AnswerType answerType(String typeId,List<Answer> list){
        return new AnswerType(typeId,list);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<AnswerType> getList() {
        return list;
    }

    public void setList(List<AnswerType> list) {
        this.list = list;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    private String paperId;
    private String state;
    @SerializedName("list")
    private List<AnswerType> list;


    public static class AnswerType implements Parcelable {
        private String typeId;

        public AnswerType(String typeId,List<Answer> list){
            this.typeId = typeId;
            this.list = list;
        }
        public List<Answer> getList() {
            return list;
        }

        public void setList(List<Answer> list) {
            this.list = list;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        @SerializedName("list")
        private List<Answer> list;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.typeId);
            dest.writeList(this.list);
        }

        public AnswerType() {
        }

        protected AnswerType(Parcel in) {
            this.typeId = in.readString();
            this.list = new ArrayList<Answer>();
            in.readList(this.list, Answer.class.getClassLoader());
        }

        public static final Creator<AnswerType> CREATOR = new Creator<AnswerType>() {
            @Override
            public AnswerType createFromParcel(Parcel source) {
                return new AnswerType(source);
            }

            @Override
            public AnswerType[] newArray(int size) {
                return new AnswerType[size];
            }
        };
    }


    public static class Answer implements Parcelable {
        private String id;

        public String getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(String userAnswer) {
            this.userAnswer = userAnswer;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @SerializedName("user_answer")
        private String userAnswer;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.userAnswer);
        }

        public Answer() {
        }

        public Answer(String id,String userAnswer) {
            this.id = id;
            this.userAnswer = userAnswer;
        }


        protected Answer(Parcel in) {
            this.id = in.readString();
            this.userAnswer = in.readString();
        }

        public static final Creator<Answer> CREATOR = new Creator<Answer>() {
            @Override
            public Answer createFromParcel(Parcel source) {
                return new Answer(source);
            }

            @Override
            public Answer[] newArray(int size) {
                return new Answer[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paperId);
        dest.writeString(this.state);
        dest.writeList(this.list);
    }

    public CommitPaperEntity() {
    }

    protected CommitPaperEntity(Parcel in) {
        this.paperId = in.readString();
        this.state = in.readString();
        this.list = new ArrayList<AnswerType>();
        in.readList(this.list, AnswerType.class.getClassLoader());
    }

    public static final Parcelable.Creator<CommitPaperEntity> CREATOR = new Parcelable.Creator<CommitPaperEntity>() {
        @Override
        public CommitPaperEntity createFromParcel(Parcel source) {
            return new CommitPaperEntity(source);
        }

        @Override
        public CommitPaperEntity[] newArray(int size) {
            return new CommitPaperEntity[size];
        }
    };
}


