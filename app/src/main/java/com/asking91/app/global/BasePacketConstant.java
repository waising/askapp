package com.asking91.app.global;

/**
 * Created by wxwang on 2016/10/31.
 */

public class BasePacketConstant {
    public interface Choose {
        int COURSE_VERSION = 2;//课程版本
        int CLASS_LEVEL = 3;//年级
        int KNOWLEDGE = 1;//知识点
    }

    public interface Knowledge {
        int KNOWLEDGE_DETAIL = 1;//知识点详情
        int KNOWLEDGE_TYPE = 2;//类型

        int KNOWLEDGE_LIST = 3;//列表
        int CHECKUSERINFO = 4;//用户信息完整性
    }

    public interface KnowledgeDetail {
        int SAVE_COLLECTION = 1;//保存知识点
        int CHECK_COLLECTION = 2;//检查知识点
        int GET_KNOWEDGE = 3;//获取知识点
        int DELETE_KNOWEDGE = 4;//删除知识点
    }

    public interface KnowledgeType {
        int GET_SAME_KNOWLEDGEKIND = 1;//同类题型
        int GET_KNOWLEDGEKIND = 2;//获取题型
    }


    public interface Subject {
        String SUBJECT__CHOOSE = "1";//选择题
    }
}
