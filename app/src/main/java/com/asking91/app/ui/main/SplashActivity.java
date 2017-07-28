package com.asking91.app.ui.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.asking91.app.BuildConfig;
import com.asking91.app.R;
import com.asking91.app.util.SharePreferencesHelper;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by wxwang on 2017/2/7.
 */
public class SplashActivity extends Activity {

    private static final int ANIM_TIME = 1100;

    private static final float SCALE_END = 1.13F;

    private static final int[] Imgs={R.mipmap.background};

    @BindView(R.id.iv_entry)
    ImageView mIVEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 判断是否是第一次开启应用
        boolean isFirstOpen = SharePreferencesHelper.getInstance(this).getBoolean(SharePreferencesHelper.FIRST_OPEN, true);
        //引导页启动次数
        int openTimes = SharePreferencesHelper.getInstance(this).getInt(SharePreferencesHelper.OPEN_TIMES, 0);

        // 如果是第一次启动，则先进入功能引导页
//        if (isFirstOpen || openTimes<3) {
//            Intent intent = new Intent(this, WelcomeActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }

        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.acitvity_splash);
        ButterKnife.bind(this);

//        //获取发布渠道
//        String channel = SystemUtil.getChannel(getApplicationContext());
//        //设置对应的启动页
//        if(channel.equals("fir11")){
//
//        }
        if(BuildConfig.DEBUG)
            openMainActivity();
        else
            startMainActivity();
    }

    private void startMainActivity(){
        Random random = new Random(SystemClock.elapsedRealtime());//SystemClock.elapsedRealtime() 从开机到现在的毫秒数（手机睡眠(sleep)的时间也包括在内）
        mIVEntry.setImageResource(Imgs[random.nextInt(Imgs.length)]);

        //2s
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>()
                {

                    @Override
                    public void call(Long aLong)
                    {
                        startAnim();
                    }
                });
    }

    /**
     * 屏蔽物理返回按钮
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startAnim() {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIVEntry, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIVEntry, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIM_TIME).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter()
        {

            @Override
            public void onAnimationEnd(Animator animation)
            {

                openMainActivity();
            }
        });

    }

    private void openMainActivity(){
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        overridePendingTransition(0,0);
        SplashActivity.this.finish();
    }
}
