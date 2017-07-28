package com.asking91.app.entity.basepacket;

import android.os.Parcel;
import android.os.Parcelable;

import com.asking91.app.entity.onlinetest.ResultEntity;
import com.google.gson.annotations.SerializedName;

public class AnswerOption implements Parcelable {
    /**选中的答案的ID*/
    private int selectId = -1;

    @SerializedName("option_name")
    private String optionName;

    @SerializedName("option_content")
    private String optionContent;

    @SerializedName("option_content_html")
    private String optionContentHtml;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getOptionContentHtml() {
        return optionContentHtml;
    }

    public void setOptionContentHtml(String optionContentHtml) {
        this.optionContentHtml = optionContentHtml;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public ResultEntity getResultEntity() {
        return resultEntity;
    }

    public void setResultEntity(ResultEntity resultEntity) {
        this.resultEntity = resultEntity;
    }

    ResultEntity resultEntity;

    public AnswerOption() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.optionName);
        dest.writeString(this.optionContent);
        dest.writeString(this.optionContentHtml);
        dest.writeString(this.id);
        dest.writeParcelable(this.resultEntity, flags);
    }

    protected AnswerOption(Parcel in) {
        this.optionName = in.readString();
        this.optionContent = in.readString();
        this.optionContentHtml = in.readString();
        this.id = in.readString();
        this.resultEntity = in.readParcelable(ResultEntity.class.getClassLoader());
    }

    public static final Creator<AnswerOption> CREATOR = new Creator<AnswerOption>() {
        @Override
        public AnswerOption createFromParcel(Parcel source) {
            return new AnswerOption(source);
        }

        @Override
        public AnswerOption[] newArray(int size) {
            return new AnswerOption[size];
        }
    };

    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }
}
