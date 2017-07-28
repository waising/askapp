package com.asking91.app.ui.oto;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.asking91.app.R;
import com.asking91.app.common.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import tcking.github.com.giraffeplayer.GiraffePlayer;


/**
 * Created by wxiao on 2016/11/25.
 * 答疑详情
 */

public class OtoRecordDetailActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolBar;


    GiraffePlayer player;//播放器
    @BindView(R.id.iv_img)
    SimpleDraweeView iv_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oto_record_detail);
        ButterKnife.bind(this);
        player = new GiraffePlayer(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(toolBar, "答疑详情");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String videoUrl = bundle.getString("videoUrl");
            String imageUrl = bundle.getString("imageUrl");
            if (!TextUtils.isEmpty(videoUrl)) {
                player.setUrl(videoUrl + "?avvod/m3u8");//MP4路径
                player.setSmallUrl(videoUrl + "?vframe/jpg/offset/10/");//缩略图路径
                ImageLoader.getInstance().displayImage(videoUrl + "?vframe/jpg/offset/10/", player.$.imageView(R.id.iv_small));
                player.setBackGroundWhite(this);
            }
            if (!TextUtils.isEmpty(imageUrl)) {
                iv_img.setImageURI(imageUrl);
            }
        }
    }

    @Override
    public void initListener() {
        super.initListener();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 横竖屏界面切换
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
            int ori = newConfig.orientation; //获取屏幕方向
            if (ori == newConfig.ORIENTATION_LANDSCAPE) {//横屏
                toolBar.setVisibility(View.GONE);
                //去掉虚拟按键全屏显示
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                iv_img.setVisibility(View.GONE);

            } else if (ori == newConfig.ORIENTATION_PORTRAIT) {//竖屏
                toolBar.setVisibility(View.VISIBLE);
                //去掉虚拟按键全屏显示
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                iv_img.setVisibility(View.VISIBLE);

            }
        }
    }


}
