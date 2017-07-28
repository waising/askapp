package com.asking91.app.global;

import android.text.TextUtils;

/**
 * Created by wxwang on 2016/10/31.
 */

public class PayConstant {

    public static String[] versionImages = {"北京课改版:M2_1.png", "北师大版:M2_2.png", "沪科版:M2_3.png", "华师大版:M2_4.png"
            , "青岛版:M2_5.png", "人教版:M2_6.png", "苏科版:M2_7.png", "湘教版:M2_8.png", "冀教版:M2_9.png", "浙教版:M2_10.png",
            "沪科版:P2_1.png", "人教版:P2_2.png", "北师大版:P2_3.png",
            "北师大版:M3_1.png", "沪教版:M3_2.png", "人教A版:M3_3.png", "人教B版:M3_4.png", "苏教版:M3_5.png", "湘教版:M3_6.png",
            "沪科版:P3_1.png", "教科版:P3_2.png", "鲁科版:P3_3.png", "人教版:P3_4.png", "粤教版:P3_5.png"};


    public static String getVersionImages(String km) {
        try {
            for (String str : versionImages) {
                if (str.contains(km)) {
                    return str.split(":")[1];
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    public interface Pay {
        int recharge = 1;//获取阿思币
        int charge = 2;//获取支付提交数据
        int chargeSuccess = 3;//支付成功刷新用户信息

        int versionClassic = 4;//超级辅导课
        int version = 5;//基础知识包
        int commodityList = 6;//获取套餐信息

        int CHECKUSERINFO = 7;//

        int findByEdition = 8;//根据版本和年级查看商品信息
        int getCharge = 9;//获取支付新接口
    }



    /**
     * 格式化金额
     *
     * @param itemPrice
     * @return
     */
    public static String formatPrice(String itemPrice) {
        String priceStr = null;
        // 金额（单位是分，要除以100）
        if (!TextUtils.isEmpty(itemPrice)) {
            try {
                double d = (new Double(itemPrice)).doubleValue();
                double price = d / 100;
                priceStr = String.valueOf(price);

            } catch (NumberFormatException e) {
            }
        } else {
            priceStr = "";
        }
        return priceStr;
    }


}
