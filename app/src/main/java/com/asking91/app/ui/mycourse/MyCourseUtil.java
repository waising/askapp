package com.asking91.app.ui.mycourse;

import android.content.Context;
import android.text.TextUtils;

import com.asking91.app.global.UserConstant;
import com.asking91.app.ui.juniorhigh.JuniorToHighProgressManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linbin on 2017/6/30.
 */

public class MyCourseUtil {


    /**
     * 查找进度值
     *
     * @return
     */
    public static String findProgress(String commodityId, Context context) {
        String userId = UserConstant.getInstance(context).getUserId();
        if (!TextUtils.isEmpty(userId)) {
            HashMap<String, String> hashMap = JuniorToHighProgressManager.getInstance().getHashMap(context, userId);
            if (hashMap != null && hashMap.size() > 0) {
                for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                    if (entry.getKey().equals(commodityId)) {
                        return entry.getValue();
                    }
                }
            } else {
                return "";
            }
        }
        return "";
    }
}
