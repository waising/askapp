package com.asking91.app.util;

import android.content.Context;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 极光推送
 * Created by wxwang on 2017/7/3.
 */
public class JPushUtil {

    private static String TAG = JPushUtil.class.getSimpleName();
    /**
     * 别名推送的内容
     * @param context
     * @param alias
     */
    public static void jPushAlias(Context context,String alias){
        JPushInterface.setAlias(context, //上下文对象
                alias, //别名
                new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        JLog.d("alias", "set alias result is" + i);
                        if(i==0){
                            JLog.i(TAG,"设置别名推送成功:"+s);
                        }
                    }
                });
    }


    /**
     * tags推送的内容
     * @param context
     * @param tags
     */
    public static void jPushTags(Context context,Set<String> tags){
        JPushInterface.setTags(context, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                JLog.d("tags", "set tag result is" + i);
                if(i==0){
                    JLog.i(TAG,"设置标签推送成功:"+s);
                }
            }
        });
    }
}
