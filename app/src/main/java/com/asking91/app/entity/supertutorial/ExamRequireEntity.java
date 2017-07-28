package com.asking91.app.entity.supertutorial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jswang on 2017/3/3.
 */

public class ExamRequireEntity {
    public String _id;
    public String content;

    public String name;
    public String zhanycm;

    public List<ExerAskEntity> diant = new ArrayList<>();

    public List<ExerAskEntity> diants = new ArrayList<>();

    public boolean isExpand = false;
}
