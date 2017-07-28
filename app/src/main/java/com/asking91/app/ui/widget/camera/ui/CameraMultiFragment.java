package com.asking91.app.ui.widget.camera.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.asking91.app.R;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.camera.utils.ParamHelper;
import com.asking91.app.ui.widget.camera.utils.ScreenUtils;
import com.asking91.app.ui.widget.camera.views.CameraBottomPanelLayout;
import com.asking91.app.ui.widget.camera.views.CommCameraView;
import com.asking91.app.ui.widget.camera.views.CommCameraView.OnCameraActionCallback;
import com.asking91.app.ui.widget.camera.views.FocusImageView;

import java.io.ByteArrayOutputStream;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 拍照界面
 * Created by jswang on 2017/2/16.
 */

@SuppressWarnings("deprecation")
public class CameraMultiFragment extends Fragment implements OnCameraActionCallback,
        CameraBottomPanelLayout.OnCameraBottomPanelListener, View.OnClickListener {
    protected View rootView;
    private CommCameraView cameraView;
    private CameraBottomPanelLayout footLayout;
    private FocusImageView focusImageView;
    private View cameraLight;
    private View ll_tip;
    private boolean isCanTake = true;
    private boolean E = false;
    private boolean N = false;
    private boolean O = false;
    private int evenType,flag;

    private int pic_orient = 1;

    public static CameraMultiFragment newInstance(int evenType,int flag) {
        CameraMultiFragment fragment = new CameraMultiFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("evenType", evenType);
        bundle.putInt("flag", flag);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            evenType = bundle.getInt("evenType");
            flag = bundle.getInt("flag");
        }
    }

    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        rootView = layoutInflater.inflate(R.layout.camera_multi_camera_layout, null);
        cameraView = (CommCameraView) rootView.findViewById(R.id.camera_search_preview);
        cameraView.setCameraRotation(getActivity().getWindowManager().getDefaultDisplay().getRotation());
        cameraView.setCameraCallback(this);
        cameraView.setCameraType(1);

        footLayout = (CameraBottomPanelLayout) rootView.findViewById(R.id.camera_bottom_panel_layout);
        footLayout.setOnCameraBottomPanelListener(this);
        cameraLight = rootView.findViewById(R.id.camera_control_light);
        cameraLight.setOnClickListener(this);

        focusImageView = (FocusImageView) rootView.findViewById(R.id.focusImageView);

        ll_tip = rootView.findViewById(R.id.ll_tip);
        //如果不是搜题隐藏掉
        if(flag==1){

            //调整方向
            setBtnAnimation(cameraLight,0,-90);
            setBtnAnimation(focusImageView,0,-90);
            setBtnAnimation(footLayout.getC_album(),0,-90);
//            setBtnAnimation(footLayout.getC_cancel(),0,-90);
            setBtnAnimation(footLayout.getC_take(),0,-90);

            ll_tip.setVisibility(View.GONE);
        }else
            setBtnAnimation(ll_tip,0,90);

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
        cameraView.setAttrNeedPreviewData(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private com.asking91.app.ui.widget.camera.utils.h.c Q = new com.asking91.app.ui.widget.camera.utils.h.c() {
        public void a(int i) {
            if (pic_orient != i) {
                pic_orient = i;
            }
        }
    };

    public void onResume() {
        super.onResume();
        if (cameraView != null) {
            cameraView.resetCamera();
        }
        com.asking91.app.ui.widget.camera.utils.h.a(getActivity()).a(this.Q);
    }

    public void onPause() {
        super.onPause();
        if (cameraView != null) {
            cameraView.stopCamera();
        }
        com.asking91.app.ui.widget.camera.utils.h.a(getActivity()).b(this.Q);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_control_light:
                onTorchOpen();
                break;
        }
    }

    private void onTorchOpen() {
        cameraView.toggleTorchOpen();
        if (cameraView.isTorchOpen()) {
            cameraLight.setBackgroundResource(R.mipmap.camera_search_light_on);
            return;
        }
        cameraLight.setBackgroundResource(R.mipmap.camera_search_light_off);
    }

    @Override
    public void onCameraMoving() {
        this.E = false;
    }

    @Override
    public void onCameraOpenFailed() {
    }

    @Override
    public void onFocusEnd(boolean success) {
        this.E = false;
        if (!success) {
            //Toast.makeText(getActivity(),"对焦失败，请调整一下！",Toast.LENGTH_SHORT).show();
            focusImageView.onFocusFailed();
        } else {
            focusImageView.onFocusSuccess();
        }
    }

    @Override
    public void onFocusStarted() {
        this.E = true;
        focusImageView.startFocus();
    }

    @Override
    public void onFocusStarted(int i, int i2) {
        this.E = true;
        focusImageView.startFocus();
    }

    @Override
    public void onPreviewChanged(final byte[] bArr, final Camera camera) {
        if (this.N && !this.O && !this.E) {
            this.O = true;
            try {
                final ByteArrayOutputStream stream = BitmapUtil.yuvImage(bArr, camera);
                Observable.create(new Observable.OnSubscribe<Bitmap>() {
                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = optionsBitmap(stream.toByteArray());
                            stream.close();
                        } catch (OutOfMemoryError e) {
                            subscriber.onError(e);
                        }catch (Exception e) {
                            subscriber.onError(e);
                        }
                        subscriber.onNext(bitmap);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new rx.Observer<Bitmap>() {
                            @Override
                            public void onNext(Bitmap bitmap) {
                                resultBitmap(bitmap);
                                isCanTake = true;
                            }

                            @Override
                            public void onCompleted() {
                                isCanTake = true;
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(getActivity(),"哎呀，相机不给力呀，调整一下再试试呗！",Toast.LENGTH_SHORT).show();
                                isCanTake = true;
                            }
                        });
            } catch (Exception e) {
            } catch (OutOfMemoryError e) {
            }
        }
    }

    protected Bitmap optionsBitmap(byte[]... bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        byte[] bArr2 = bArr[0];
        if (bArr2 == null) {
            return null;
        }
        int orient = cameraView != null ? getCameraDisplayOrientation() : 0;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bArr2, 0, bArr2.length, options);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Config.RGB_565;
        options.inSampleSize = BitmapUtil.inSampleSize(options, -1, ScreenUtils.getScreenWidth(getActivity()) * ScreenUtils.getScreenHeight(getActivity()));
        try {
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr2, 0, bArr2.length, options);
            try {
                if (orient == 0) {
                    return decodeByteArray;
                }
                Matrix matrix = new Matrix();
                matrix.setRotate((float) orient);
                decodeByteArray = Bitmap.createBitmap(decodeByteArray, 0, 0, decodeByteArray.getWidth(), decodeByteArray.getHeight(), matrix, false);
                return decodeByteArray;
            } catch (OutOfMemoryError e) {
                return decodeByteArray;
            } catch (Exception e2) {
                return decodeByteArray;
            }
        } catch (OutOfMemoryError e3) {
            return null;
        } catch (Exception e4) {
            return null;
        }
    }

    protected void resultBitmap(Bitmap bitmap) {
        isCanTake = true;
        if (bitmap == null) {
            Toast.makeText(getActivity(), "哎呀，相机不给力呀，调整一下再试试呗！", Toast.LENGTH_SHORT).show();
            cameraView.resetCamera();
            return;
        }
        goToPage(bitmap, null, 1);
    }


    private void goToPage(Bitmap bitmap, String str, int i) {
        if (cameraLight != null) {
            cameraLight.setBackgroundResource(R.mipmap.camera_search_light_off);
        }
        ParamHelper.acquireParamsReceiver(CameraPublishFragment.class.getName()).put("pic_bitmap", bitmap);
        int infoOrientation = cameraView == null ? 0 : cameraView.getInfoOrientation();
        Bundle bundle = new Bundle();
        bundle.putInt("evenType", evenType);
        bundle.putInt("flag", flag);
        bundle.putString("pic_url", str);
        bundle.putInt("pic_orient", pic_orient);
        bundle.putInt("pic_info_orient", infoOrientation);
        bundle.putInt("pic_res", i);
        Fragment f = CameraPublishFragment.newInstance(bundle);
        ((CameraActivity) getActivity()).goToPage(f);
        relCameraView();
    }


    @Override
    public void OnCameraTake() {
        if (isCanTake && cameraView != null) {
            this.N = true;
            this.O = false;
            isCanTake = false;
        }
    }

    @Override
    public void onTakePictureStarted() {
    }

    @Override
    public void OnCameraCancel() {
        getActivity().finish();
    }

    /**
     * 从图库选择图片，跳转到图库界面
     */
    @Override
    public void OnCameraAlbum() {
        Intent intent = new Intent(getContext(), ImageSelectActivity.class);
        getActivity().startActivityForResult(intent, 4);
    }

    public int getCameraDisplayOrientation() {
        return cameraView.getCameraDisplayOrientation();
    }

    public void onDestroyView() {
        super.onDestroyView();
        relCameraView();
    }

    private void relCameraView() {
        if (cameraView != null) {
            cameraView.release();
            cameraView = null;
        }
    }
}
