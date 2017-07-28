package com.asking91.app.util.changescreen;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;

import com.asking91.app.ui.widget.camera.utils.AppEventType;

import de.greenrobot.event.EventBus;

/**
 * Created by wxwang on 2017/3/2.
 */
public class ChangeOrientationHandler extends Handler {
    private Activity activity;

    public ChangeOrientationHandler(Activity ac) {
        super();
        activity = ac;
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == 888) {
            int orientation = msg.arg1;
            if (orientation > 45 && orientation < 135) {
                //activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
//                Log.d(MainActivity.TAG, "横屏翻转: ");
                EventBus.getDefault().post(new AppEventType(AppEventType.CATA_ORIENTATION_REQUEST,ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE));
            } else if (orientation > 135 && orientation < 225) {
                //activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
//                Log.d(MainActivity.TAG, "竖屏翻转: ");
                //EventBus.getDefault().post(new AppEventType(AppEventType.CATA_ORIENTATION_REQUEST,ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT));
            } else if (orientation > 225 && orientation < 315) {
                //activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                Log.d(MainActivity.TAG, "横屏: ");
                //(CameraActivity)activity).changeImgRotation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                EventBus.getDefault().post(new AppEventType(AppEventType.CATA_ORIENTATION_REQUEST,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE));
            } else if ((orientation > 315 && orientation < 360) || (orientation > 0 && orientation < 45)) {
                //activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                Log.d(MainActivity.TAG, "竖屏: ");
                //((CameraActivity)activity).changeImgRotation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                EventBus.getDefault().post(new AppEventType(AppEventType.CATA_ORIENTATION_REQUEST,ActivityInfo.SCREEN_ORIENTATION_PORTRAIT));
            }
        }
        super.handleMessage(msg);
    }
}
