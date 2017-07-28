package com.asking91.app.ui.main;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.asking91.app.R;
import com.asking91.app.common.BaseActivity;
import com.asking91.app.global.Constants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by Jun on 2016/4/19.
 */
public class PhotoShowActivity extends BaseActivity {

//    @BindView(R.id.toolBar)
//    Toolbar mToolbar;
    @BindView(R.id.photo_iv)
    PhotoDraweeView mPhotoIv;

    @BindView(R.id.photo_ly)
    LinearLayout mPhotoLv;

    private String mImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.black),0);
    }

    @Override
    public void initData() {
        super.initData();
        mImageUrl = getIntent().getExtras().getString(Constants.Key.WEB_IMAGE_URL);

        if(!mImageUrl.startsWith("http")){
            mImageUrl = "file://"+mImageUrl;
        }
    }

    @Override
    public void initView() {
        super.initView();
        mPhotoLv.setBackgroundColor(ContextCompat.getColor(this,R.color.black));
    }

    @Override
    public void initListener() {

        mPhotoIv.setOnViewTapListener (new OnViewTapListener() {

            @Override
            public void onViewTap(View view, float v, float v1) {
                PhotoShowActivity.this.finish();
            }

        });
    }
    @Override
    public void initLoad() {
        super.initLoad();

        if (!TextUtils.isEmpty(mImageUrl)) {
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(mImageUrl);
            controller.setOldController(mPhotoIv.getController());
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null || mPhotoIv == null) {
                        return;
                    }
                    mPhotoIv.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            mPhotoIv.setController(controller.build());
        }
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }
}
