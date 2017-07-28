package com.asking91.app.ui.supertutorial.selected;

import com.asking91.app.entity.RequestEntity;

/**
 * Created by jswang on 2017/3/1.
 */

public class ApiRequestListener<T> {
    public  void onResultSuccess(T res){
    }
    public  void onResultFail(){
    }

    public  void onResultFail(RequestEntity requestEntity){
    }
}
