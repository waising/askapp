package com.asking91.app.global;

/**
 * Created by wxwang on 2016/11/15.
 */
public class OnlineTestConstant {
    //课程选择数
    public static final int KNOWLEDGE_MAX_SIZE = 10;

    //选择题题数
    public static final int XZT_SIZE = 10;

    public interface TopicType{
        String XZT = "1";//选择题
    }

    public interface CardType{
        int ASK_CARD = 1;//答题卡
        int ASK_RESULT = 2;//结果
    }
}
