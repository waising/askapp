package com.asking91.app.ui.widget.camera.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

import com.asking91.app.R;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.camera.utils.ScreenUtils;
import com.asking91.app.ui.widget.camera.utils.h;
import com.asking91.app.ui.widget.camera.utils.h.a;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("deprecation")
public class CommCameraView extends SurfaceView implements ErrorCallback, PreviewCallback, Callback, a {

    private SurfaceHolder b;
    private Camera c;
    private OnCameraActionCallback d;
    private int e = 100;
    private boolean f = false;
    private CameraState g = CameraState.STATE_UNUSEABLE;
    private Object h = new Object();
    private boolean i = false;
    private int j = 0;
    private int k = 0;
    private int l = 1;
    private boolean m = false;
    private h n = null;
    private Size o;
    private Handler p = new Handler();
    private int q;
    private int r;
    private int s = 1;
    private Rect t;

    private Runnable u = new Runnable() {
        public void run() {
            synchronized (h) {
                if (g == CameraState.STATE_FOCUSING || g == CameraState.STATE_WAIT_TAKE_PICTURE) {
                    CameraState b = g;
                    try {
                        c.cancelAutoFocus();
                    } catch (Exception e) {
                    }
                    a(CameraState.STATE_IDLE);
                    b(b);
                }
            }
        }
    };
    private Runnable v = new Runnable() {
        public void run() {
            if (!i) {
                startAutoFocus();
                if (s == 1) {
                    p.postDelayed(this, 3000);
                }
            }
        }
    };
    private PictureCallback w;
    private PictureCallback x= new PictureCallback() {
        public void onPictureTaken(byte[] bArr, Camera camera) {
            synchronized (h) {
                if (c != null) {
                    c.stopPreview();
                }
                if (w != null) {
                    w.onPictureTaken(bArr, camera);
                }
                a(CameraState.STATE_IDLE);
            }
        }
    };
    private AutoFocusCallback y= new AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            synchronized (h) {
                if (g == CameraState.STATE_UNUSEABLE) {
                } else {
                    i = success;
                    p.removeCallbacks(u);
                    d.onFocusEnd(i);
                    if (g == CameraState.STATE_WAIT_TAKE_PICTURE) {
                        CommCameraView.j(CommCameraView.this);
                        if (i) {
                            a();
                        } else {
                            a(CameraState.STATE_IDLE);
                            if (j < 3) {
                                a(w, false);
                            } else {
                                a();
                            }
                        }
                    } else {
                        a(CameraState.STATE_IDLE);
                    }
                }
            }
        }
    };

    public CommCameraView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    public CommCameraView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet);
    }

    static  int j(CommCameraView commCameraView) {
        int i = commCameraView.j + 1;
        commCameraView.j = i;
        return i;
    }


    private Size a(List<Size> list, int i, int i2) {
        double d = ((double) i) / ((double) i2);
        if (list == null) {
            return null;
        }
        Size size = null;
        double d2 = Double.MAX_VALUE;
        for (Size size2 : list) {
            Size size3;
            double d3;
            if (Math.abs((((double) size2.width) / ((double) size2.height)) - d) <= 0.05d && size2.width <= i) {
                if (((double) (i - size2.width)) < d2) {
                    size3 = size2;
                    d3 = (double) (i - size2.width);
                } else {
                    double d4 = d2;
                    size3 = size;
                    d3 = d4;
                }
                size = size3;
                d2 = d3;
            }
        }
        if (size != null) {
            return size;
        }
        d2 = Double.MAX_VALUE;
        for (Size size22 : list) {
            if (((double) Math.abs(size22.width - i)) < d2) {
                size = size22;
                d2 = (double) Math.abs(size22.width - i);
            }
        }
        return size;
    }


    private Size a(List<Size> list, Size size, int i) {
        if (list == null) {
            return null;
        }
        int min = Math.min(i, BitmapUtil.getMaxBitmapSize(ScreenUtils.getScreenWidth(getContext()), ScreenUtils.getScreenHeight(getContext())));
        double d = ((double) size.width) / ((double) size.height);
        Size size2 = null;
        double d2 = Double.MAX_VALUE;
        for (Size size3 : list) {
            Size size4;
            double abs;
            if (Math.abs((((double) size3.width) / ((double) size3.height)) - d) <= 0.05d) {
                if (((double) Math.abs(size3.width - min)) < d2) {
                    size4 = size3;
                    abs = (double) Math.abs(size3.width - min);
                } else {
                    double d3 = d2;
                    size4 = size2;
                    abs = d3;
                }
                size2 = size4;
                d2 = abs;
            }
        }
        if (size2 != null) {
            return size2;
        }
        d2 = Double.MAX_VALUE;
        for (Size size32 : list) {
            if (((double) Math.abs(size32.width - min)) < d2) {
                size2 = size32;
                d2 = (double) Math.abs(size32.width - min);
            }
        }
        return size2;
    }


    @SuppressWarnings("unused")
    private void a() {
        ShutterCallback shutterCallback = null;
        synchronized (this.h) {
            a(CameraState.STATE_TAKING_PICTURE);
            this.d.onTakePictureStarted();
            if (false) {
                shutterCallback = new ShutterCallback() {
                    public void onShutter() {
                    }
                };
            }
            try {
                this.c.takePicture(shutterCallback, null, this.x);
            } catch (Exception e) {
                a(CameraState.STATE_IDLE);
                Toast.makeText(getContext(), "相机拍照异常，请重新尝试一下", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void a(int i, int i2, CameraState cameraState) {
        if (this.c != null) {
            synchronized (this.h) {
                if (this.g != CameraState.STATE_IDLE) {
                    return;
                }
                if (VERSION.SDK_INT < 14 || i <= 0 || i2 <= 0) {
                    this.d.onFocusStarted();
                } else {
                    int i3 = 0;
                    Parameters parameters = null;
                    try {
                        parameters = this.c.getParameters();
                        i3 = parameters.getMaxNumFocusAreas();
                    } catch (Exception e) {
                    }
                    if (i3 <= 0 || parameters == null) {
                        this.d.onFocusStarted();
                    } else {
                        float width = ((((float) i) / ((float) getWidth())) * 2000.0f) - 1000.0f;
                        float height = ((((float) i2) / ((float) getHeight())) * 2000.0f) - 1000.0f;
                        List<Area> arrayList = new ArrayList<Area>();
                        arrayList.add(new Area(new Rect(((int) width) - this.e, ((int) height) - this.e, ((int) width) + this.e, ((int) height) + this.e), 1000));
                        this.t = new Rect(((int) width) - this.e, ((int) height) - this.e, ((int) width) + this.e, ((int) height) + this.e);
                        this.d.onFocusStarted(i, i2);
                        try {
                            parameters.setFocusAreas(arrayList);
                            this.c.setParameters(parameters);
                            this.c.startPreview();
                        } catch (Exception e2) {
                        }
                    }
                }
                if (cameraState == CameraState.STATE_UNSPECIFIED) {
                    a(CameraState.STATE_FOCUSING);
                } else {
                    a(cameraState);
                }
                try {
                    this.c.autoFocus(this.y);
                    if (this.s == 1) {
                        this.p.postDelayed(this.u, 10000);
                    }
                } catch (Exception e3) {
                    a(CameraState.STATE_IDLE);
                }
            }
        }
    }


    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CommCameraView);
        this.k = obtainStyledAttributes.getInt(R.styleable.CommCameraView_orientation, 0);
        this.l = obtainStyledAttributes.getInt(R.styleable.CommCameraView_imgScale, 1);
        this.m = obtainStyledAttributes.getBoolean(R.styleable.CommCameraView_needPreviewData, false);
        obtainStyledAttributes.recycle();
        this.e = getResources().getDimensionPixelSize(R.dimen.space_30);
        this.b = getHolder();
        this.b.addCallback(this);
        this.b.setType(3);
        this.n = com.asking91.app.ui.widget.camera.utils.h.a(getContext());
    }

    private void a(PictureCallback pictureCallback, boolean z) {
        if (this.c != null && pictureCallback != null) {
            synchronized (this.h) {
                if (this.g == CameraState.STATE_IDLE || this.g == CameraState.STATE_FOCUSING) {
                    this.w = pictureCallback;
                    if (z) {
                        this.j = 0;
                    }
                    if (this.g == CameraState.STATE_IDLE) {
                        if (this.i) {
                            a();
                        } else {
                            b(CameraState.STATE_WAIT_TAKE_PICTURE);
                        }
                    } else {
                        a(CameraState.STATE_WAIT_TAKE_PICTURE);
                    }
                    return;
                }
            }
        }
    }

    private void a(CameraState cameraState) {
        this.g = cameraState;
    }

    private boolean a(boolean z) {
        boolean z2 = true;
        this.f = z;
        try {
            Parameters parameters = this.c.getParameters();
            if (z) {
                parameters.setFlashMode("torch");
            } else {
                parameters.setFlashMode("off");
            }
            this.c.setParameters(parameters);
        } catch (Exception e) {
            if (this.f) {
                z2 = false;
            }
            this.f = z2;
            z2 = false;
        }
        return z2;
    }

    private void b(CameraState cameraState) {
        a(-1, -1, cameraState);
    }

    public int getCameraDisplayOrientation() {
        return this.k;
    }

    public int getFocusCountFromTakePicture() {
        return this.j;
    }

    public int getInfoOrientation() {
        return this.r;
    }

    public boolean isTorchOpen() {
        return this.f;
    }

    public void onDeviceMoved() {
        synchronized (this.h) {
            if (this.g == CameraState.STATE_UNUSEABLE || this.g == CameraState.STATE_WAIT_TAKE_PICTURE) {
            } else {
                if (this.s == 1) {
                    startAutoFocus();
                }
            }
        }
    }

    public void onDeviceMoving() {
        if (this.d != null) {
            this.d.onCameraMoving();
        }
    }

    public void onError(int i, Camera camera) {
    }

    public void onPreviewFrame(byte[] bArr, Camera camera) {
        if (this.d != null) {
            this.d.onPreviewChanged(bArr, camera);
        }
        this.c.addCallbackBuffer(bArr);
    }

    public void release() {
        boolean z = false;
        this.n.b(this);
        if (this.c != null) {
            synchronized (this.h) {
                if (this.f) {
                    if (!this.f) {
                        z = true;
                    }
                    a(z);
                }
                try {
                    this.c.stopPreview();
                    this.c.setPreviewCallback(null);
                    this.c.release();
                } catch (Exception e) {
                }
                this.p.removeCallbacksAndMessages(null);
                this.c = null;
                this.i = false;
                a(CameraState.STATE_UNUSEABLE);
            }
        }
    }

    public void resetCamera() {
        synchronized (this.h) {
            a(CameraState.STATE_IDLE);
            if (this.c != null) {
                try {
                    this.c.setPreviewDisplay(this.b);
                } catch (IOException e) {
                }
                this.c.setPreviewCallbackWithBuffer(this);
                try {
                    this.c.startPreview();
                } catch (Exception e2) {
                }
            }
        }
    }

    public void setAttrNeedPreviewData(boolean z) {
        this.m = z;
        if (this.c != null) {
            if (z) {
                this.c.setPreviewCallbackWithBuffer(this);
                this.c.addCallbackBuffer(new byte[((this.o.width * this.o.height) * ImageFormat.getBitsPerPixel(17))]);
                return;
            }
            this.c.setPreviewCallbackWithBuffer(null);
            this.c.addCallbackBuffer(null);
        }
    }

    public void setCameraCallback(OnCameraActionCallback onCameraActionCallback) {
        this.d = onCameraActionCallback;
    }

    public void setCameraDisplayOrientation(int rotation, int cameraId, Camera camera) {
        int degrees = 0;
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);
        switch (rotation) {
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        degrees = cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT ? (360 - ((degrees + cameraInfo.orientation) % 360)) % 360 : ((cameraInfo.orientation - degrees) + 360) % 360;
        this.r = cameraInfo.orientation;
        camera.setDisplayOrientation(degrees);
    }

    public void setCameraRotation(int i) {
        this.q = i;
    }

    public void setCameraType(int i) {
        if (i == 1 || i == 2) {
            this.s = i;
            if (this.n != null) {
                this.n.a(i);
            }
        }
        i = 1;
        this.s = i;
        if (this.n != null) {
            this.n.a(i);
        }
    }

    public void startAutoFocus() {
        a(-1, -1, CameraState.STATE_UNSPECIFIED);
    }

    public void startAutoFocus(int i, int i2) {
        a(i, i2, CameraState.STATE_UNSPECIFIED);
    }

    public void stopCamera() {
        synchronized (this.h) {
            a(CameraState.STATE_IDLE);
            if (this.c != null) {
                try {
                    this.c.stopPreview();
                } catch (Exception e) {
                }
            }
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        float f = 1.0f;
        if (this.c != null) {
            Parameters parameters;
            Parameters parameters2 = null;
            try {
                parameters = this.c.getParameters();
            } catch (Exception e) {
                parameters = parameters2;
            }
            if (parameters != null) {
                if (this.k != 90) {
                    int i4 = i3;
                    i3 = i2;
                    i2 = i4;
                }
                List<Size> sizes = parameters.getSupportedPreviewSizes();
                this.o = a(sizes, i3, i2);
                Size a = a(parameters.getSupportedPictureSizes(), this.o, this.l * i3);
                float f2 = a != null ? ((float) a.width) / ((float) a.height) : 1.0f;
                if (this.o != null) {
                    f = ((float) this.o.width) / ((float) this.o.height);
                }
                if (((double) Math.abs(f2 - f)) > 0.1d && a != null) {
                    this.o = a(sizes, a.width, a.height);
                }
                if (this.o != null) {
                    parameters.setPreviewSize(this.o.width, this.o.height);
                }
                if (a != null) {
                    parameters.setPictureSize(a.width, a.height);
                }
                parameters.setFocusMode("auto");
                parameters.setWhiteBalance("auto");
                try {
                    this.c.setParameters(parameters);
                    if (this.m) {
                        this.c.setPreviewCallbackWithBuffer(this);
                        this.c.addCallbackBuffer(new byte[((this.o.width * this.o.height) * ImageFormat.getBitsPerPixel(17))]);
                    }
                } catch (OutOfMemoryError e2) {
                } catch (Exception e3) {
                }
            }
            try {
                this.c.startPreview();
            } catch (Exception e4) {
            }
            if (this.p != null) {
                this.p.post(this.v);
            }
        }
    }


    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        release();
        int i = 0;
        while (true) {
            try {
                if (i < Camera.getNumberOfCameras()) {
                    CameraInfo cameraInfo = new CameraInfo();
                    Camera.getCameraInfo(i, cameraInfo);
                    if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                        try {
                            this.c = Camera.open(i);
                            setCameraDisplayOrientation(this.q, i, this.c);
                        } catch (RuntimeException e) {
                            if (this.c == null) {
                                Toast.makeText(getContext(), "请去‘设置’-‘权限管理’中打开相机权限", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e2) {
                            if (this.c == null) {
                                Toast.makeText(getContext(), "相机无法打开，重启阿思可在线试试看", Toast.LENGTH_SHORT).show();
                            }
                        }
                        try {
                            this.c.setPreviewDisplay(surfaceHolder);
                            this.c.setErrorCallback(this);
                            if (this.k == 0) {
                            }
                        } catch (Exception e3) {
                            Toast.makeText(getContext(), "请去‘设置’-‘权限管理’中打开相机权限", Toast.LENGTH_SHORT).show();
                            this.c.release();
                            this.c = null;
                        }
                        this.n.a(this);
                        return;
                    }
                    i++;
                } else if (this.c != null) {
                    this.c.setPreviewDisplay(surfaceHolder);
                    this.c.setErrorCallback(this);
                    if (this.k == 0) {
                    }
                    if (this.c != null) {
                        this.n.a(this);
                        return;
                    } else if (this.d != null) {
                        this.d.onCameraOpenFailed();
                        return;
                    } else {
                        return;
                    }
                } else if (this.d != null) {
                    this.d.onCameraOpenFailed();
                    return;
                } else {
                    return;
                }
            } catch (Exception e4) {
            }
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        release();
    }

    public void takePicture(PictureCallback pictureCallback) {
        a(pictureCallback, true);
    }

    public void toggleTorchOpen() {
        synchronized (this.h) {
            if (this.g != CameraState.STATE_IDLE) {
            } else {
                a(!(this.f));
            }
        }
    }

    private enum CameraState {
        STATE_UNUSEABLE,
        STATE_UNSPECIFIED,
        STATE_IDLE,
        STATE_FOCUSING,
        STATE_WAIT_TAKE_PICTURE,
        STATE_TAKING_PICTURE;
    }

    public interface OnCameraActionCallback {
        void onCameraMoving();

        void onCameraOpenFailed();

        void onFocusEnd(boolean z);

        void onFocusStarted();

        void onFocusStarted(int i, int i2);

        void onPreviewChanged(byte[] bArr, Camera camera);

        void onTakePictureStarted();
    }
}