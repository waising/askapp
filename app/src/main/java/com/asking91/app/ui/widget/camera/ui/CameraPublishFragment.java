package com.asking91.app.ui.widget.camera.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.asking91.app.Asking91;
import com.asking91.app.R;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.camera.utils.ParamHelper;
import com.asking91.app.ui.widget.camera.utils.ScreenUtils;
import com.asking91.app.ui.widget.camera.views.CommCropView;

import java.io.File;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 拍照裁切截界面
 * Created by jswang on 2017/2/17.
 */

public class CameraPublishFragment extends Fragment implements OnClickListener {
    protected View rootView;
    protected CommCropView cropView;
    protected View submit;
    protected View back;
    protected RelativeLayout opt_layout;
    private View ll_tip;
    protected String pic_url;
    protected int evenType;
    protected int flag;
    protected int pic_info_orient;
    protected int pic_orient = 2;

    private Bundle mBundle;


    public static CameraPublishFragment newInstance(Bundle bundle) {
        CameraPublishFragment fragment = new CameraPublishFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("pic_url", pic_url);
        this.mBundle = bundle;
    }

    private int cropOrient = -1;

    private com.asking91.app.ui.widget.camera.utils.h.c o = new com.asking91.app.ui.widget.camera.utils.h.c() {

        public void a(int i) {
            if (cropOrient != i) {
                setOrientation(i);
                cropOrient = i;
            }
        }
    };

    private void setOrientation(final int orient) {
        if (cropOrient != orient) {
            cropView.post(new Runnable() {
                public void run() {
                    //横屏截图模式
                    if (orient == 1 && flag != 1) {
                        cropView.setOrientation(2);
                        cropOrient = 2;
                    } else {
                        cropView.setOrientation(orient);
                        cropOrient = orient;
                    }
                }
            });
        }
    }

    public void onResume() {
        super.onResume();
        com.asking91.app.ui.widget.camera.utils.h.a(getApplicationContext()).a(this.o);
    }

    public void onPause() {
        super.onPause();
        com.asking91.app.ui.widget.camera.utils.h.a(getApplicationContext()).b(this.o);
    }

    protected Context getApplicationContext() {
        return getActivity() != null ? getActivity().getApplicationContext() : Asking91.getmInstance().getApplicationContext();
    }

    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(R.layout.camera_image_publish_layout, null);
        cropView = (CommCropView) this.rootView.findViewById(R.id.camera_image_publish_crop_layer);


        submit = this.rootView.findViewById(R.id.camera_image_publish_edit_opt_submit);
        back = this.rootView.findViewById(R.id.camera_image_publish_edit_opt_back);
        opt_layout = (RelativeLayout) this.rootView.findViewById(R.id.camera_image_publish_edit_opt_layout);

        cropView.setShowCrop(false);

        submit.setOnClickListener(this);
        back.setOnClickListener(this);

        ll_tip = rootView.findViewById(R.id.ll_tip);

        return this.rootView;
    }

    private void setBtnAnimation(View view, int i, int i2) {
        if (view != null) {
            Animation rotateAnimation = new RotateAnimation((float) i, (float) i2, 1, 0.5f, 1, 0.5f);
            rotateAnimation.setInterpolator(new AccelerateInterpolator());
            rotateAnimation.setFillAfter(true);
            view.startAnimation(rotateAnimation);
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Object obj = ParamHelper.acceptParams(
                CameraPublishFragment.class.getName()).get("pic_bitmap");
        Bitmap bitmap = obj != null ? (Bitmap) obj : null;
        if (getArguments() == null) {
            return;
        }
        evenType = getArguments().getInt("evenType");
        flag = getArguments().getInt("flag");
        pic_url = getArguments().getString("pic_url");
        pic_info_orient = getArguments().getInt("pic_info_orient");
        pic_orient = getArguments().getInt("pic_orient", 2);
        if (TextUtils.isEmpty(pic_url) && mBundle != null) {
            pic_url = mBundle.getString("pic_url");
        }
        cropView.setCameraInfoRotation(pic_info_orient, 2);

        if (flag == 1) {//0 扫描题目  1：头像,头像跳转过来，提示布局隐藏
            ll_tip.setVisibility(View.GONE);
            setBtnAnimation(opt_layout.getChildAt(0), 0, -90);
        } else
            setBtnAnimation(ll_tip, 0, 90);

        if (bitmap != null) {
            initCropView(bitmap);
        } else if (!TextUtils.isEmpty(pic_url)) {
            cropView.setIsFromAlbum(true);
            loadImg(pic_url);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.camera_image_publish_edit_opt_submit) {
            RectF cropRect = cropView.getCropRect();
            if (cropRect == null || cropRect.isEmpty()) {
                Toast.makeText(getActivity(), "请先框选你要提交的问题!", Toast.LENGTH_SHORT).show();
                return;
            }
            Bitmap bitmap = cropView.getResultBitmap();
            if (bitmap != null) {
                File info = BitmapUtil.saveImage(bitmap);
                if (info != null)
                    EventBus.getDefault().post(new AppEventType(evenType, info.getAbsolutePath(), bitmap));
                getActivity().finish();
            }
        } else if (v.getId() == R.id.camera_image_publish_edit_opt_back) {
            ((CameraActivity) getActivity()).goToPage(CameraMultiFragment.newInstance(evenType, flag));
        }
    }

    private void initCropView(Bitmap bitmap) {
        if (bitmap == null) {
            Toast.makeText(getActivity(), "图片数据已丢失，重拍一次!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        cropView.setImageBitmap(bitmap);
        back.setClickable(true);
    }


    @SuppressLint({"NewApi"})
    private void loadImg(final String str) {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = runLoadImg(str);
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new rx.Observer<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        initCropView(bitmap);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    protected Bitmap runLoadImg(String path) {
        return (path.contains("content://") || path.contains("file://")) ?
                BitmapUtil.getBmpFromUriSafely(getActivity(), Uri.parse(path),
                        ScreenUtils.getScreenWidth(getActivity()),
                        ScreenUtils.getScreenHeight(getActivity()))
                : BitmapUtil.getBmpFromFileSafely(getActivity(), path,
                ScreenUtils.getScreenWidth(getActivity()),
                ScreenUtils.getScreenHeight(getActivity()));
    }
}
