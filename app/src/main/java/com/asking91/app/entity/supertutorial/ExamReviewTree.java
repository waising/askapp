package com.asking91.app.entity.supertutorial;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wxiao on 2016/12/4.
 */

public class ExamReviewTree implements Serializable{
    public String _id;
    private String index;
    private String text;

    public String id;
    public String name;

    public boolean isExpand = false;


    public ArrayList<ExamReviewTree> list = new ArrayList<>();

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public ExamReviewTree() {
    }

}
