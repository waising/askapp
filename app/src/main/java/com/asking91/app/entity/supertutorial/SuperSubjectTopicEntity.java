package com.asking91.app.entity.supertutorial;

/**
 * Created by wxiao on 2016/12/4.
 * 演练大冲关的题型
 */

public class SuperSubjectTopicEntity {
    /**
     * topic_id : 1
     * topic_name : 概念理解篇
     */

    private String topic_id;
    private String topic_name;

    public boolean isSelect;

    public SuperSubjectTopicEntity() {

    }

    public SuperSubjectTopicEntity(String topic_id, String topic_name) {
        this.topic_id = topic_id;
        this.topic_name = topic_name;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

}
