package com.asking91.app.ui.juniorhigh;

import android.content.Context;
import android.content.SharedPreferences;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 初升高播放进度管理
 * Created by linbin on 2017/6/5.
 */

public class JuniorToHighProgressManager {

    public static final String PREFERENCE_NAME = "playProgress";

    private static JuniorToHighProgressManager juniorToHighProgressManager;


    private HashMap<String, String> hashmap = new HashMap<>();

    private JuniorToHighProgressManager() {


    }

    /**
     * 同步锁，避免两个线程同时访问，
     *
     * @return
     */
    public static JuniorToHighProgressManager getInstance() {

        if (juniorToHighProgressManager == null) {
            synchronized (JuniorToHighProgressManager.class) {
                if (juniorToHighProgressManager == null) {
                    juniorToHighProgressManager = new JuniorToHighProgressManager();

                }
            }
        }

        return juniorToHighProgressManager;
    }

    /**
     * 保存进度值
     */
    public void putHashMapValue(Context context, String userId, String goodsId, String progress) {
        hashmap.put(goodsId, progress);
        putHashMap(context, userId, hashmap);
    }


    public void putHashMap(Context context, String userId, HashMap<String, String> hashmap) {
        JSONArray mJsonArray = new JSONArray();
        Iterator<Map.Entry<String, String>> iterator = hashmap.entrySet().iterator();


        JSONObject object = null;
        while (iterator.hasNext()) {
            object = new JSONObject();
            Map.Entry<String, String> entry = iterator.next();
            try {
                object.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mJsonArray.put(object);
        }
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userId, mJsonArray.toString());
        editor.commit();
    }

    public HashMap<String, String> getHashMap(Context context, String userName) {

        HashMap<String, String> itemMap = new HashMap<String, String>();
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String result = sp.getString(userName, "");
        try {
            JSONArray array = new JSONArray(result);

            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                Iterator<String> sIterator = itemObject.keys();
                while (sIterator.hasNext()) {
                    // 获得key
                    String keyObject = sIterator.next();
                    // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可
                    String value = itemObject.getString(keyObject);
                    itemMap.put(keyObject, value);

                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemMap;
    }


    /**
     * 清除SharePrefence值
     */

    private void clearSharePrefence(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }


}
