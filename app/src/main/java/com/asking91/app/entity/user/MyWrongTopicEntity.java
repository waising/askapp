package com.asking91.app.entity.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zqshen on 2016/12/15.
 */

public class MyWrongTopicEntity implements Parcelable {

    private String userId;
    private String subjectId;
    private int count;
    private int errorCount;
    private long lastAnswerDate;
    private SubjectKindBean subjectKind;
    @SerializedName("subject_description_html")
    private String subjectDescriptionHtml;
    @SerializedName("right_answer")
    private String rightAnswer;
    private List<AnswerIdListBean> answerIdList;
    private List<OptionsBean> options;

    public boolean isSelect;

    public String analysisTopic;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public long getLastAnswerDate() {
        return lastAnswerDate;
    }

    public void setLastAnswerDate(long lastAnswerDate) {
        this.lastAnswerDate = lastAnswerDate;
    }

    public SubjectKindBean getSubjectKind() {
        return subjectKind;
    }

    public void setSubjectKind(SubjectKindBean subjectKind) {
        this.subjectKind = subjectKind;
    }

    public String getSubjectDescriptionHtml() {
        return subjectDescriptionHtml;
    }

    public void setSubjectDescriptionHtml(String subjectDescriptionHtml) {
        this.subjectDescriptionHtml = subjectDescriptionHtml;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public List<AnswerIdListBean> getAnswerIdList() {
        return answerIdList;
    }

    public void setAnswerIdList(List<AnswerIdListBean> answerIdList) {
        this.answerIdList = answerIdList;
    }

    public List<OptionsBean> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsBean> options) {
        this.options = options;
    }

    public static class SubjectKindBean implements Parcelable {

        private String subject_kind_id;
        private String subject_kind_name;
        private String tip_id;
        private String answer_object_id;
        private String subject_kind_code;
        private String subject_kind_type;
        private String weight;
        private String answer_object_name;

        public String getSubject_kind_id() {
            return subject_kind_id;
        }

        public void setSubject_kind_id(String subject_kind_id) {
            this.subject_kind_id = subject_kind_id;
        }

        public String getSubject_kind_name() {
            return subject_kind_name;
        }

        public void setSubject_kind_name(String subject_kind_name) {
            this.subject_kind_name = subject_kind_name;
        }

        public String getTip_id() {
            return tip_id;
        }

        public void setTip_id(String tip_id) {
            this.tip_id = tip_id;
        }

        public String getAnswer_object_id() {
            return answer_object_id;
        }

        public void setAnswer_object_id(String answer_object_id) {
            this.answer_object_id = answer_object_id;
        }

        public String getSubject_kind_code() {
            return subject_kind_code;
        }

        public void setSubject_kind_code(String subject_kind_code) {
            this.subject_kind_code = subject_kind_code;
        }

        public String getSubject_kind_type() {
            return subject_kind_type;
        }

        public void setSubject_kind_type(String subject_kind_type) {
            this.subject_kind_type = subject_kind_type;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getAnswer_object_name() {
            return answer_object_name;
        }

        public void setAnswer_object_name(String answer_object_name) {
            this.answer_object_name = answer_object_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.subject_kind_id);
            dest.writeString(this.subject_kind_name);
            dest.writeString(this.tip_id);
            dest.writeString(this.answer_object_id);
            dest.writeString(this.subject_kind_code);
            dest.writeString(this.subject_kind_type);
            dest.writeString(this.weight);
            dest.writeString(this.answer_object_name);
        }

        public SubjectKindBean() {
        }

        protected SubjectKindBean(Parcel in) {
            this.subject_kind_id = in.readString();
            this.subject_kind_name = in.readString();
            this.tip_id = in.readString();
            this.answer_object_id = in.readString();
            this.subject_kind_code = in.readString();
            this.subject_kind_type = in.readString();
            this.weight = in.readString();
            this.answer_object_name = in.readString();
        }

        public static final Creator<SubjectKindBean> CREATOR = new Creator<SubjectKindBean>() {
            @Override
            public SubjectKindBean createFromParcel(Parcel source) {
                return new SubjectKindBean(source);
            }

            @Override
            public SubjectKindBean[] newArray(int size) {
                return new SubjectKindBean[size];
            }
        };
    }

    public static class AnswerIdListBean implements Parcelable {

        private String paperId;
        private String id;
        private String userId;
        private String subId;
        private long createDate;
        private String createDate_str;
        private SubjectKindBeanX subjectKind;
        private boolean right;
        private List<TipsBean> tips;

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
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

        public String getSubId() {
            return subId;
        }

        public void setSubId(String subId) {
            this.subId = subId;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getCreateDate_str() {
            return createDate_str;
        }

        public void setCreateDate_str(String createDate_str) {
            this.createDate_str = createDate_str;
        }

        public SubjectKindBeanX getSubjectKind() {
            return subjectKind;
        }

        public void setSubjectKind(SubjectKindBeanX subjectKind) {
            this.subjectKind = subjectKind;
        }

        public boolean isRight() {
            return right;
        }

        public void setRight(boolean right) {
            this.right = right;
        }

        public List<TipsBean> getTips() {
            return tips;
        }

        public void setTips(List<TipsBean> tips) {
            this.tips = tips;
        }

        public static class SubjectKindBeanX implements Parcelable {

            private String subject_kind_id;
            private String subject_kind_name;
            private String tip_id;
            private String answer_object_id;
            private String subject_kind_code;
            private String subject_kind_type;
            private String weight;
            private String answer_object_name;

            public String getSubject_kind_id() {
                return subject_kind_id;
            }

            public void setSubject_kind_id(String subject_kind_id) {
                this.subject_kind_id = subject_kind_id;
            }

            public String getSubject_kind_name() {
                return subject_kind_name;
            }

            public void setSubject_kind_name(String subject_kind_name) {
                this.subject_kind_name = subject_kind_name;
            }

            public String getTip_id() {
                return tip_id;
            }

            public void setTip_id(String tip_id) {
                this.tip_id = tip_id;
            }

            public String getAnswer_object_id() {
                return answer_object_id;
            }

            public void setAnswer_object_id(String answer_object_id) {
                this.answer_object_id = answer_object_id;
            }

            public String getSubject_kind_code() {
                return subject_kind_code;
            }

            public void setSubject_kind_code(String subject_kind_code) {
                this.subject_kind_code = subject_kind_code;
            }

            public String getSubject_kind_type() {
                return subject_kind_type;
            }

            public void setSubject_kind_type(String subject_kind_type) {
                this.subject_kind_type = subject_kind_type;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getAnswer_object_name() {
                return answer_object_name;
            }

            public void setAnswer_object_name(String answer_object_name) {
                this.answer_object_name = answer_object_name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.subject_kind_id);
                dest.writeString(this.subject_kind_name);
                dest.writeString(this.tip_id);
                dest.writeString(this.answer_object_id);
                dest.writeString(this.subject_kind_code);
                dest.writeString(this.subject_kind_type);
                dest.writeString(this.weight);
                dest.writeString(this.answer_object_name);
            }

            public SubjectKindBeanX() {
            }

            protected SubjectKindBeanX(Parcel in) {
                this.subject_kind_id = in.readString();
                this.subject_kind_name = in.readString();
                this.tip_id = in.readString();
                this.answer_object_id = in.readString();
                this.subject_kind_code = in.readString();
                this.subject_kind_type = in.readString();
                this.weight = in.readString();
                this.answer_object_name = in.readString();
            }

            public static final Creator<SubjectKindBeanX> CREATOR = new Creator<SubjectKindBeanX>() {
                @Override
                public SubjectKindBeanX createFromParcel(Parcel source) {
                    return new SubjectKindBeanX(source);
                }

                @Override
                public SubjectKindBeanX[] newArray(int size) {
                    return new SubjectKindBeanX[size];
                }
            };
        }

        public static class TipsBean implements Parcelable {

            private String tip_name;
            private String tip_id;
            private String version;
            private String tip_code;
            private String parentId;
            private boolean leaf;
            private int stage;
            private String subject_id;
            private String qtip;
            private String tip_name_py;
            private int clicks;

            public String getTip_name() {
                return tip_name;
            }

            public void setTip_name(String tip_name) {
                this.tip_name = tip_name;
            }

            public String getTip_id() {
                return tip_id;
            }

            public void setTip_id(String tip_id) {
                this.tip_id = tip_id;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getTip_code() {
                return tip_code;
            }

            public void setTip_code(String tip_code) {
                this.tip_code = tip_code;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public boolean isLeaf() {
                return leaf;
            }

            public void setLeaf(boolean leaf) {
                this.leaf = leaf;
            }

            public int getStage() {
                return stage;
            }

            public void setStage(int stage) {
                this.stage = stage;
            }

            public String getSubject_id() {
                return subject_id;
            }

            public void setSubject_id(String subject_id) {
                this.subject_id = subject_id;
            }

            public String getQtip() {
                return qtip;
            }

            public void setQtip(String qtip) {
                this.qtip = qtip;
            }

            public String getTip_name_py() {
                return tip_name_py;
            }

            public void setTip_name_py(String tip_name_py) {
                this.tip_name_py = tip_name_py;
            }

            public int getClicks() {
                return clicks;
            }

            public void setClicks(int clicks) {
                this.clicks = clicks;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.tip_name);
                dest.writeString(this.tip_id);
                dest.writeString(this.version);
                dest.writeString(this.tip_code);
                dest.writeString(this.parentId);
                dest.writeByte(this.leaf ? (byte) 1 : (byte) 0);
                dest.writeInt(this.stage);
                dest.writeString(this.subject_id);
                dest.writeString(this.qtip);
                dest.writeString(this.tip_name_py);
                dest.writeInt(this.clicks);
            }

            public TipsBean() {
            }

            protected TipsBean(Parcel in) {
                this.tip_name = in.readString();
                this.tip_id = in.readString();
                this.version = in.readString();
                this.tip_code = in.readString();
                this.parentId = in.readString();
                this.leaf = in.readByte() != 0;
                this.stage = in.readInt();
                this.subject_id = in.readString();
                this.qtip = in.readString();
                this.tip_name_py = in.readString();
                this.clicks = in.readInt();
            }

            public static final Creator<TipsBean> CREATOR = new Creator<TipsBean>() {
                @Override
                public TipsBean createFromParcel(Parcel source) {
                    return new TipsBean(source);
                }

                @Override
                public TipsBean[] newArray(int size) {
                    return new TipsBean[size];
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
            dest.writeString(this.id);
            dest.writeString(this.userId);
            dest.writeString(this.subId);
            dest.writeLong(this.createDate);
            dest.writeString(this.createDate_str);
            dest.writeParcelable(this.subjectKind, flags);
            dest.writeByte(this.right ? (byte) 1 : (byte) 0);
            dest.writeList(this.tips);
        }

        public AnswerIdListBean() {
        }

        protected AnswerIdListBean(Parcel in) {
            this.paperId = in.readString();
            this.id = in.readString();
            this.userId = in.readString();
            this.subId = in.readString();
            this.createDate = in.readLong();
            this.createDate_str = in.readString();
            this.subjectKind = in.readParcelable(SubjectKindBeanX.class.getClassLoader());
            this.right = in.readByte() != 0;
            this.tips = new ArrayList<TipsBean>();
            in.readList(this.tips, TipsBean.class.getClassLoader());
        }

        public static final Creator<AnswerIdListBean> CREATOR = new Creator<AnswerIdListBean>() {
            @Override
            public AnswerIdListBean createFromParcel(Parcel source) {
                return new AnswerIdListBean(source);
            }

            @Override
            public AnswerIdListBean[] newArray(int size) {
                return new AnswerIdListBean[size];
            }
        };
    }

    public static class OptionsBean implements Parcelable {

        private String id;
        private String option_name;
        private String option_content;
        private String option_content_html;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOption_name() {
            return option_name;
        }

        public void setOption_name(String option_name) {
            this.option_name = option_name;
        }

        public String getOption_content() {
            return option_content;
        }

        public void setOption_content(String option_content) {
            this.option_content = option_content;
        }

        public String getOption_content_html() {
            return option_content_html;
        }

        public void setOption_content_html(String option_content_html) {
            this.option_content_html = option_content_html;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.option_name);
            dest.writeString(this.option_content);
            dest.writeString(this.option_content_html);
        }

        public OptionsBean() {
        }

        protected OptionsBean(Parcel in) {
            this.id = in.readString();
            this.option_name = in.readString();
            this.option_content = in.readString();
            this.option_content_html = in.readString();
        }

        public static final Creator<OptionsBean> CREATOR = new Creator<OptionsBean>() {
            @Override
            public OptionsBean createFromParcel(Parcel source) {
                return new OptionsBean(source);
            }

            @Override
            public OptionsBean[] newArray(int size) {
                return new OptionsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.subjectId);
        dest.writeInt(this.count);
        dest.writeInt(this.errorCount);
        dest.writeLong(this.lastAnswerDate);
        dest.writeParcelable(this.subjectKind, flags);
        dest.writeString(this.subjectDescriptionHtml);
        dest.writeString(this.rightAnswer);
        dest.writeList(this.answerIdList);
        dest.writeList(this.options);
    }

    public MyWrongTopicEntity() {
    }

    protected MyWrongTopicEntity(Parcel in) {
        this.userId = in.readString();
        this.subjectId = in.readString();
        this.count = in.readInt();
        this.errorCount = in.readInt();
        this.lastAnswerDate = in.readLong();
        this.subjectKind = in.readParcelable(SubjectKindBean.class.getClassLoader());
        this.subjectDescriptionHtml = in.readString();
        this.rightAnswer = in.readString();
        this.answerIdList = new ArrayList<AnswerIdListBean>();
        in.readList(this.answerIdList, AnswerIdListBean.class.getClassLoader());
        this.options = new ArrayList<OptionsBean>();
        in.readList(this.options, OptionsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MyWrongTopicEntity> CREATOR = new Parcelable.Creator<MyWrongTopicEntity>() {
        @Override
        public MyWrongTopicEntity createFromParcel(Parcel source) {
            return new MyWrongTopicEntity(source);
        }

        @Override
        public MyWrongTopicEntity[] newArray(int size) {
            return new MyWrongTopicEntity[size];
        }
    };
}
