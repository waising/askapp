package com.asking91.app.entity.oto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jswang on 2017/3/28.
 */

public class OtoRecord {
    public String id;

    public int state;
    public OtoStudent student;
    public OtoSubject subject;
    public OtoTeacher teacher;
    public OtoEvaluation evaluation;


    public OtoTime time;
    public OtoBill bill;


    public Complain complain;

    public Whiteboard whiteboard;
    public Audio audio;
    public ArrayList<String> videos;//videos的List,录像的url路径,取最后一个

    public class OtoSubject {
        public int grade;
        public String subject;
        public List<SubjectImage> images = new ArrayList<>();//取第一章

    }

    public class OtoTeacher {
        public String name;
        public String account;
        public String code;
        public String avatar;//头像
        public String toStudent;//教师提点
    }

    public class OtoEvaluation {
        public int reward;//谢师币
        public String suggest; //建议
        public String star;//评价星星数
    }


    public class OtoTime {
        public String uploadTime;
        public int holdingSeconds;
        public String connectTime;
        public String endTime;
        public String startTime;
    }

    public class OtoBill {
        public String price;
    }


    public class Complain {//投诉
        public String details;//投诉详情
        public String id;
        public String reason;//投诉原因
        public String state;

    }


    public class OtoStudent {
        public Double integral;
        public String askTimes;//为0，是首单.askTimes==null或者其他不显示首单免费

    }

    public class Whiteboard {
        public WhiteboardTimeMsg whiteboardTimeMsg;
        public WhiteboardCallerFile whiteboardCallerFile;
    }

    public class WhiteboardTimeMsg {
        public int eventType;
        public long channelId;
        public String members;
        public String status;
        public long createtime;
        public int live;
        public int duration;
        public String type;
    }

    public class WhiteboardCallerFile {
        public String filename;
        public int size;
        public String url;
        public long channelid;
        public boolean caller;
        public boolean mix;
        public String user;
        public String type;
        public String md5;
    }

    public class Audio {
        public AudioCallerFile audioCallerFile;
        public AudioTimeMsg audioTimeMsg;
    }

    public class AudioCallerFile {
        public String filename;
        public int size;
        public String url;
        public long channelid;
        public boolean caller;
        public boolean mix;
        public String user;
        public String type;
        public String md5;
    }

    public class AudioTimeMsg {
        public int eventType;
        public long channelId;
        public String members;
        public String status;
        public long createtime;
        public int live;
        public int duration;
        public String type;
    }
}
