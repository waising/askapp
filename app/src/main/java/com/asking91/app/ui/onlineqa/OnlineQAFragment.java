package com.asking91.app.ui.onlineqa;

import com.asking91.app.common.BaseFragment;

/**
 * Created by wxiao on 2016/10/31.
 * 在线问答--待解决
 */

public class OnlineQAFragment extends BaseFragment {
    protected String km, levelId;
    /**
     * 刷新界面列表
     */
    public void refreshListData(String km, String levelId){
        if(!this.levelId.equals(levelId)&&!this.km.equals(km)){
            //刷新界面
            this.km = km;
            this.levelId = levelId;
            onRefreshListData(km, levelId);
        }
    }

    /**
     * 真正刷新数据列表
     */
    protected void onRefreshListData(String km, String levelId){};
}
