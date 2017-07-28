package com.asking91.app.entity.juniorTohigh;

import java.io.Serializable;

/**
 * Created by linbin on 2017/7/4.
 */

public  class CourseDataArrayBean implements Serializable {
    /**
     * courseDataName : 测试课程1
     * courseDataType : pdf
     * courseDataUrl : http://7xj9ur.com1.z0.glb.clouddn.com/20170525_test_course.pdf
     */

    private String courseDataName;
    private String courseDataType;
    private String courseDataUrl;
    private String courseDataTypeName;

    public String getCourseDataTypeName() {
        return courseDataTypeName;
    }

    public void setCourseDataTypeName(String courseDataTypeName) {
        this.courseDataTypeName = courseDataTypeName;
    }

    public String getCourseDataName() {
        return courseDataName;
    }

    public void setCourseDataName(String courseDataName) {
        this.courseDataName = courseDataName;
    }

    public String getCourseDataType() {
        return courseDataType;
    }

    public void setCourseDataType(String courseDataType) {
        this.courseDataType = courseDataType;
    }

    public String getCourseDataUrl() {
        return courseDataUrl;
    }

    public void setCourseDataUrl(String courseDataUrl) {
        this.courseDataUrl = courseDataUrl;
    }


}
