package com.asking91.app.entity.supertutorial;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by wxiao on 2016/11/30.
 */

public class SuperSelectMapEntity implements Serializable {
    private Map<String, Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
