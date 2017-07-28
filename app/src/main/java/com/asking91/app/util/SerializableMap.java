package com.asking91.app.util;

import com.asking91.app.entity.onlinetest.ResultEntity;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by wxwang on 2016/11/18.
 */
public class SerializableMap implements Serializable {
    private Map<String,ResultEntity> map;
    public Map<String,ResultEntity> getMap()
    {
        return map;
    }
    public void setMap(Map<String,ResultEntity> map)
    {
        this.map=map;
    }
}
