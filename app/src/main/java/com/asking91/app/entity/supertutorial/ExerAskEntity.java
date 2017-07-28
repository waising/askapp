package com.asking91.app.entity.supertutorial;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.entity.onlinetest.ResultEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jswang on 2017/3/2.
 */

public class ExerAskEntity implements Serializable {

    public String _id;
    public String name;
    public String subject_description_html;
    public String right_answer;
    public String details_answer_html;
    public int difficulty;

    public String xiangxjt;
    public String xiangjbj;
    public String qiaoxqj;

    public SubjectType subject_type;

    public boolean showReult = false;
    public boolean showReultBtn = false;

    public boolean  isExpand = false;

    public List<OptionsBean> options = new ArrayList<>();

    public List<OptionsBean> biansts = new ArrayList<>();

    public List<OptionsBean> bianst = new ArrayList<>();

    public String silyx;

    public static class SubjectType implements Serializable{
        /**
         * 1-选择题  2-解答题
         */
        public String type_id;
        public String type_name;
    }

    public static class OptionsBean implements Serializable{
        public String _id;
        public String option_name;
        public String option_content_html;

        public String subject_description_html;
        public String details_answer_html;

        public List<OptionsBean> options = new ArrayList<>();

        public SubjectType getSubject_type() {
            return subject_type;
        }

        public void setSubject_type(SubjectType subject_type) {
            this.subject_type = subject_type;
        }

        public SubjectType subject_type;

        private ResultEntity resultEntity;
        public boolean isSelect = false;

        public String getOptionName() {
            return option_name;
        }


        public String getOptionContentHtml() {
            return option_content_html;
        }


        public void setResultEntity(ResultEntity resultEntity){
            this.resultEntity = resultEntity;
        }

        public ResultEntity getResultEntity(){
            return resultEntity;
        }

    }

    public String getSubjectTypeId(){
        if(subject_type != null ){
            return subject_type.type_id;
        }
        return  "";
    }

    public String getSilyx(){
        try {
            JSONObject jsonObj = JSON.parseObject(silyx);
            return  jsonObj.getString("content");
        }catch(Exception e){}
        return  "";
    }

    public String getXiangjbj(){
        try {
            JSONObject jsonObj = JSON.parseObject(xiangjbj);
            return  jsonObj.getString("content");
        }catch(Exception e){}
        return  "";
    }

    public String getQiaoxqj(){
        try {
            JSONObject jsonObj = JSON.parseObject(qiaoxqj);
            return  jsonObj.getString("content");
        }catch(Exception e){}
        return  "";
    }

    public List<OptionsBean>  getOptions(){
        return  options;
    }

}
