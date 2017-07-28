package com.asking91.app.ui.widget.camera.utils;

import android.content.Context;

import com.asking91.app.R;

import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

/**
 * Created by jswang on 2017/2/17.
 */

public class APPUtil {
    public static int dpToPx(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    /**
     * 分享
     *
     * @param context
     * @param content
     * @param url
     * @param mListener
     */
    public static void showShare(Context context, String title, String content, String url, PlatformActionListener mListener) {
        OnekeyShare oks = new OnekeyShare();


//        oks.setSilent(!showContentEdit);
//        if (!TextUtils.isEmpty(platformToShare)) {
//            oks.setPlatform(platformToShare);
//        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);

        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();

        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();

        //oks.setAddress("12345678901"); //分享短信的号码和邮件的地址

        //oks.setTitle(context.getString(R.string.app_name));
        oks.setTitle(title);

        oks.setTitleUrl(url);

        oks.setText(content);//文字内容
//        if (oks.getPalform() == QQ.NAME) {
//            oks.setImagePath("http://stcdn.91asking.com/ic_logo.png");  //分享sdcard目录下的图片
//
//        } else {
        oks.setImageUrl("http://stcdn.91asking.com/ic_logo.png");
//        }

        oks.setUrl(url); //微信不绕过审核分享链接

        //oks.setFilePath(testVideo);  //filePath用于视频分享
        //oks.setComment(context.getString(R.string.app_name)); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供

        oks.setSite(context.getString(R.string.app_name));  //QZone分享完之后返回应用时提示框上显示的名称

        oks.setSiteUrl(url);//QZone分享参数

        oks.setVenueName(context.getString(R.string.app_name));

        oks.setVenueDescription(content);

        //oks.setLatitude(23.169f);
        //oks.setLongitude(112.908f);

        if (mListener != null) {
            // 将快捷分享的操作结果将通过OneKeyShareCallback回调
            oks.setCallback(mListener);
        }

        // 去自定义不同平台的字段内容
        // oks.setShareContentCustomizeCallback(new
        // ShareContentCustomizeDemo());

        // 在九宫格设置自定义的图标
        //Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
//        String label = "ShareSDK";
//        View.OnClickListener listener = new View.OnClickListener() {
//            public void onClick(View v) {
//
//            }
//        };
//        oks.setCustomerLogo(logo, label, listener);

        // 为EditPage设置一个背景的View
        //oks.setEditPageBackground(getPage());
        // 隐藏九宫格中的新浪微博
        // oks.addHiddenPlatform(SinaWeibo.NAME);

        // String[] AVATARS = {
        // 		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
        // 		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };
        // oks.setImageArray(AVATARS);              //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

        // 启动分享
        oks.show(context);
    }
}
