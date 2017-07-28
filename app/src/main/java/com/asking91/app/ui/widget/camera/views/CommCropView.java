package com.asking91.app.ui.widget.camera.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.VelocityTracker;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Scroller;

import com.asking91.app.ui.widget.camera.utils.APPUtil;

import java.util.LinkedList;
import java.util.List;


public class CommCropView extends ImageView {
    private float A;
    private float B;
    private float C;
    private int D = -1;
    private int E = -1;
    private float F = 0.0f;
    private TouchType G = TouchType.NONE;
    private int[] H = new int[]{0, 0, 0, 0};
    private PointF I = new PointF();
    private TouchType J = TouchType.NONE;
    private PointF K = new PointF();
    private float L;
    private boolean M = true;
    private CropMode N = CropMode.CROP;
    private Bitmap O;
    private List<RectF> P = new LinkedList();
    private List<Matrix> Q = new LinkedList();
    private List<PointF> R = new LinkedList();
    private Animation S;
    private Transformation T = new Transformation();
    private boolean U = false;
    private int V = -1;
    private boolean W = true;
    ReferenceLineState a = new ReferenceLineState(this);
    private boolean aa = false;
    private int ab;
    private float ac;
    private boolean ad;
    private boolean ae;
    private int af;
    private int ag;
    private Runnable ah = new Runnable() {
        public void run() {
            if (v == null) {
                float height = u.height();
                float width = u.width();
                if (height != 0.0f && width != 0.0f) {
                    if (V != 1) {
                        v = new ScaleAnimation(1.0f, s.width() / width, 1.0f, s.height() / height, u.centerX(), u.centerY());
                        w = s;
                    } else {
                        v = new ScaleAnimation(1.0f, r.width() / width, 1.0f, r.height() / height, u.centerX(), u.centerY());
                        w = r;
                    }
                    v.setDuration(350);
                    v.initialize(0, 0, 0, 0);
                    postInvalidate();
                    return;
                }
                return;
            }
            postDelayed(ah, 10);
        }
    };
    private Matrix b;
    private RectF c = new RectF();
    public CropBoxChangedListener cropBoxChangedListener;
    private RectF d = new RectF();
    private float e = 0.0f;
    private Scroller f;
    private VelocityTracker g;
    private float h;
    private float i;
    private float j;
    private float k;
    private float l;
    private float m;
    private boolean n;
    private boolean o = false;
    private RectF p = new RectF(0.0f, 0.36f, 1.0f, 0.64f);
    private RectF q = new RectF(0.3f, 0.0f, 0.7f, 1.0f);
    private RectF r = new RectF();
    private RectF s = new RectF();
    private Paint t;
    private RectF u;
    private Animation v;
    private RectF w;
    private float x;
    private float y;
    private float z;

    public interface CropBoxChangedListener {
        void cropBoxChanged();
    }

    public enum CropMode {
        NONE,
        CROP,
        VERTEX
    }

    private class ReferenceLineState {
        boolean a;
        final CommCropView b;
        private AlphaAnimation c;
        private Transformation d;
        private float e;
        private float f;
        private int g;
        private float h;
        private float i;

        private ReferenceLineState(CommCropView commCropView) {
            this.b = commCropView;
            this.a = false;
            this.c = null;
            this.d = new Transformation();
            this.e = 0.0f;
            this.f = 0.0f;
            this.g = 0;
            this.h = 1.0f;
            this.i = 1.0f;
        }

        private boolean a() {
            return this.c != null;
        }

        private float b() {
            float f = 0.0f;
            if (this.c == null) {
                return 0.0f;
            }
            this.c.getTransformation(this.b.getDrawingTime(), this.d);
            if (this.c.hasEnded()) {
                this.c = null;
                f = 1.0f;
            } else {
                float drawingTime = (float) (this.b.getDrawingTime() - this.c.getStartTime());
                if (drawingTime >= 0.0f && drawingTime <= ((float) this.c.getDuration())) {
                    f = drawingTime / ((float) this.c.getDuration());
                } else if (this.a) {
                }
            }
            if (this.a) {
                this.i = f;
                this.h = 1.0f - f;
            } else {
                this.i = 1.0f - f;
                this.h = f;
            }
            this.b.postInvalidate();
            return this.d.getAlpha();
        }

        public void clear() {
            if (this.a) {
                this.a = false;
                this.c = new AlphaAnimation(this.f, 0.0f);
                this.c.setDuration(300);
                this.c.initialize(0, 0, 0, 0);
                this.b.postInvalidate();
            }
        }

        public int getLineColor() {
            float f = this.f;
            if (a()) {
                f = b();
            }
            return (((int) f) << 24) | this.g;
        }

        public float getMaskAlphaRatio() {
            return this.h;
        }

        public float getRefLineRatio() {
            return this.i;
        }

        public boolean needDraw() {
            return this.a || this.c != null;
        }

        public void setLineColor(int i) {
            this.e = ((float) (i >>> 24)) * 0.1f;
            this.f = ((float) (i >>> 24)) * 0.93f;
            this.g = ViewCompat.MEASURED_SIZE_MASK & i;
        }

        public void show() {
            if (!this.a) {
                this.a = true;
                this.c = new AlphaAnimation(this.e, this.f);
                this.c.setDuration(350);
                this.c.initialize(0, 0, 0, 0);
                this.b.postInvalidate();
            }
        }
    }

    enum TouchType {
        NONE,
        BOX,
        EDGE_LEFT,
        EDGE_RIGHT,
        EDGE_TOP,
        EDGE_BOTTOM,
        VERTEX_LEFT_TOP,
        VERTEX_RIGHT_TOP,
        VERTEX_LEFT_BOTTOM,
        VERTEX_RIGHT_BOTTOM,
        BACKGROUND,
        SCALE_AND_ROTATE_BACKGROUND
    }

    public CommCropView(Context context) {
        super(context);
        init(null);
    }

    public CommCropView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public CommCropView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private float a(float f, float f2, float f3, float f4) {
        return (float) Math.toDegrees(Math.atan2((double) (f4 - f2), (double) (f3 - f)));
    }

    private float a(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return 0.0f;
        }
        if (this.c.width() == 0.0f || this.c.height() == 0.0f) {
            return 1.0f;
        }
        if (((float) i) == this.c.width() && ((float) i2) == this.c.height()) {
            return 1.0f;
        }
        float width = ((float) i) != this.c.width() ? ((float) i) / this.c.width() : 0.0f;
        float height = ((float) i2) != this.c.height() ? ((float) i2) / this.c.height() : 0.0f;
        if (this.ad) {
            if (width <= height) {
                width = height;
            }
        } else if (height == 0.0f && width == 0.0f) {
            width = 1.0f;
        } else if (width == 0.0f) {
            width = height;
        } else if (height != 0.0f && width >= height) {
            width = height;
        }
        return 1.0f / width;
    }

    private float a(int i, int i2, float f, boolean z) {
        return this.W ? i2 == 1 ? !z ? f / 2.0f : f * 2.0f : f : (this.ag == 2 || this.ag == 3) ? i2 == 1 ? !z ? f / 2.0f : f * 2.0f : f : i == 1 ? !z ? f / 2.0f : f * 2.0f : f;
    }

    private Bitmap a(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) i, ((float) bitmap.getWidth()) / 2.0f, ((float) bitmap.getHeight()) / 2.0f);
        try {
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    private PointF a() {
        return this.R.size() <= 0 ? new PointF() : (PointF) this.R.remove(0);
    }

    private PointF a(float f, float f2) {
        float width = this.c.width() / 2.0f;
        float height = this.c.height() / 2.0f;
        PointF a = a();
        a.x = width - f;
        a.y = height - f2;
        return a;
    }

    private void a(Bitmap bitmap) {
        if (bitmap != null) {
            setShowCrop(true);
            if (this.c.width() != 0.0f && this.c.height() != 0.0f) {
                this.e = a(bitmap.getWidth(), bitmap.getHeight());
                RectF rectF = new RectF();
                rectF.set(0.0f, 0.0f, (float) Math.floor((double) (((float) bitmap.getWidth()) * this.e)), (float) Math.floor((double) (((float) bitmap.getHeight()) * this.e)));
                Matrix c = c();
                c.reset();
                c.setScale(this.e, this.e);
                float height = (this.c.height() - (this.e * ((float) bitmap.getHeight()))) / 2.0f;
                float width = (this.c.width() - (this.e * ((float) bitmap.getWidth()))) / 2.0f;
                if (!this.ad) {
                    height = 0.0f;
                }
                c.postTranslate(width, height);
                super.setImageMatrix(c);
                b(rectF);
                a(c);
            }
        }
    }

    private void a(Canvas canvas) {
        Canvas canvas2;
        int i = 63;
        this.t.setStyle(Style.STROKE);
        this.t.setColor(this.E);
        this.t.setStrokeWidth(2.5f);
        if (!this.ad) {
            this.u.bottom = Math.min(this.ac, this.u.bottom);
        }
        RectF b = b();
        b.set(this.u);
        if (this.v != null) {
            postInvalidate();
            if (this.v.hasEnded()) {
                this.v = null;
                this.u.set(this.w);
                b.set(this.w);
            } else {
                this.v.getTransformation(getDrawingTime(), this.T);
                this.T.getMatrix().mapRect(b);
            }
        }
        canvas.drawRect(b.left, b.top, b.right, b.bottom, this.t);
        if (this.a.needDraw()) {
            this.t.setStrokeWidth(0.0f);
            this.t.setColor(this.a.getLineColor());
            float height = b.height() / 3.0f;
            float width = b.width() / 3.0f;
            float refLineRatio = this.a.getRefLineRatio();
            canvas2 = canvas;
            canvas2.drawLine(b.left, (height * 1.0f) + b.top, (b.width() * refLineRatio) + b.left, (height * 1.0f) + b.top, this.t);
            canvas2 = canvas;
            canvas2.drawLine(b.right, (height * 2.0f) + b.top, b.right - (b.width() * refLineRatio), (height * 2.0f) + b.top, this.t);
            canvas2 = canvas;
            canvas2.drawLine((width * 1.0f) + b.left, b.top, (width * 1.0f) + b.left, (b.height() * refLineRatio) + b.top, this.t);
            canvas2 = canvas;
            canvas2.drawLine((width * 2.0f) + b.left, b.bottom, (width * 2.0f) + b.left, b.bottom - (b.height() * refLineRatio), this.t);
            int maskAlphaRatio = (int) (((float) 193) * this.a.getMaskAlphaRatio());
            if (maskAlphaRatio < 63) {
                maskAlphaRatio = 63;
            }
            i = maskAlphaRatio << 24;
        } else if (this.v == null) {
            i = -1056964608;
        }
        this.t.setColor(i);
        this.t.setStyle(Style.FILL);
        this.t.setStrokeWidth(0.0f);
        canvas2 = canvas;
        canvas2.drawRect(-this.x, -this.x, b.left - 1.0f, this.x + this.c.height(), this.t);
        canvas2 = canvas;
        canvas2.drawRect(b.right + 1.0f, -this.x, this.x + this.c.width(), this.x + this.c.height(), this.t);
        canvas.drawRect(b.left - 1.0f, -this.x, b.right + 1.0f, b.top - 1.0f, this.t);
        canvas2 = canvas;
        canvas2.drawRect(b.left - 1.0f, b.bottom + 1.0f, b.right + 1.0f, this.x + this.c.height(), this.t);
        this.t.setStrokeWidth(this.y);
        this.t.setColor(this.E);
        canvas2 = canvas;
        canvas2.drawLine(b.left - (this.y / 2.0f), b.top, (this.y / 2.0f) + (b.left + ((float) this.D)), b.top, this.t);
        canvas2 = canvas;
        canvas2.drawLine(b.left, b.top, b.left, (this.y / 2.0f) + (b.top + ((float) this.D)), this.t);
        canvas2 = canvas;
        canvas2.drawLine((this.y / 2.0f) + b.right, b.top, (b.right - ((float) this.D)) - (this.y / 2.0f), b.top, this.t);
        canvas2 = canvas;
        canvas2.drawLine(b.right, b.top, b.right, (this.y / 2.0f) + (b.top + ((float) this.D)), this.t);
        canvas2 = canvas;
        canvas2.drawLine(b.left - (this.y / 2.0f), b.bottom, (this.y / 2.0f) + (b.left + ((float) this.D)), b.bottom, this.t);
        canvas.drawLine(b.left, b.bottom, b.left, (b.bottom - ((float) this.D)) - (this.y / 2.0f), this.t);
        canvas2 = canvas;
        canvas2.drawLine((this.y / 2.0f) + b.right, b.bottom, (b.right - ((float) this.D)) - (this.y / 2.0f), b.bottom, this.t);
        canvas.drawLine(b.right, b.bottom, b.right, (b.bottom - ((float) this.D)) - (this.y / 2.0f), this.t);
        a(b);
    }

    private void a(Matrix matrix) {
        if (this.Q.size() < 10 && !this.Q.contains(matrix)) {
            this.Q.add(matrix);
        }
    }

    private void a(PointF pointF) {
        if (this.R.size() < 10 && !this.R.contains(pointF)) {
            this.R.add(pointF);
        }
    }

    private void a(RectF rectF) {
        if (this.P.size() < 10 && !this.P.contains(rectF)) {
            this.P.add(rectF);
        }
    }

    private void a(RectF rectF, RectF rectF2) {
        if ((this.ag == 2 || this.ag == 3) && !this.W) {
            if (rectF.left <= rectF2.left) {
                this.H[0] = 1;
            } else {
                this.H[0] = 0;
            }
            if (rectF.right >= rectF2.right) {
                this.H[1] = 1;
            } else {
                this.H[1] = 0;
            }
            if (rectF.top <= rectF2.top) {
                this.H[2] = 1;
            } else {
                this.H[2] = 0;
            }
            if (rectF.bottom >= rectF2.bottom) {
                this.H[3] = 1;
                return;
            } else {
                this.H[3] = 0;
                return;
            }
        }
        if (rectF.left <= rectF2.left) {
            this.H[2] = 1;
        } else {
            this.H[2] = 0;
        }
        if (rectF.right >= rectF2.right) {
            this.H[3] = 1;
        } else {
            this.H[3] = 0;
        }
        if (rectF.top <= rectF2.top) {
            this.H[0] = 1;
        } else {
            this.H[0] = 0;
        }
        if (rectF.bottom >= rectF2.bottom) {
            this.H[1] = 1;
        } else {
            this.H[1] = 0;
        }
    }

    private void init(AttributeSet attributeSet) {
        this.b = new Matrix();
        this.E = getResources().getColor(com.asking91.app.R.color.button_bg_normal_1);
        setScaleType(ScaleType.MATRIX);
        this.f = new Scroller(getContext(), new DecelerateInterpolator());
        this.h = 1.0f;
        d();
    }

    private void a(Animation animation, Matrix matrix, Matrix matrix2) {
        if (animation != null) {
            if (animation instanceof AnimationSet) {
                RectF rectF = new RectF(this.u);
                matrix2.mapRect(rectF);
                PointF a = a((rectF.left + rectF.right) / 2.0f, (rectF.top + rectF.bottom) / 2.0f);
                rectF.offset(a.x, a.y);
                float f = (rectF.left + rectF.right) / 2.0f;
                float f2 = (rectF.top + rectF.bottom) / 2.0f;
                if (rectF.width() < this.F) {
                    rectF.set((float) Math.floor((double) (f - (this.F / 2.0f))), rectF.top, (float) Math.round(f + (this.F / 2.0f)), rectF.bottom);
                }
                if (rectF.height() < this.F) {
                    rectF.set(rectF.left, (float) Math.floor((double) (f2 - (this.F / 2.0f))), rectF.right, (float) Math.round(f2 + (this.F / 2.0f)));
                }
                this.u.set(rectF);
                a(rectF);
            } else if (animation instanceof TranslateAnimation) {
                matrix2.mapRect(this.u);
            }
        }
    }

    private void a(TouchType touchType, PointF pointF, MotionEvent motionEvent, int i) {
        postInvalidate();
        if (touchType == TouchType.SCALE_AND_ROTATE_BACKGROUND) {
            if (!a(motionEvent)) {
                touchType = TouchType.BACKGROUND;
            } else {
                return;
            }
        }
        if (this.N == CropMode.VERTEX) {
            switch (touchType) {
                case EDGE_LEFT:
                case EDGE_RIGHT:
                case EDGE_TOP:
                case EDGE_BOTTOM:
                    touchType = TouchType.BACKGROUND;
                    break;
            }
        }
        float x = motionEvent.getX(i) - pointF.x;
        float y = motionEvent.getY(i) - pointF.y;
        Matrix c = c();
        RectF b = b();
        switch (touchType) {
            case EDGE_LEFT:
                b.set(x + this.u.left, this.u.top, this.u.right, this.u.bottom);
                if (b.width() < this.F) {
                    b.left = this.u.right - this.F;
                    break;
                }
                break;
            case EDGE_RIGHT:
                b.set(this.u.left, this.u.top, x + this.u.right, this.u.bottom);
                if (b.width() < this.F) {
                    b.right = this.u.left + this.F;
                    break;
                }
                break;
            case EDGE_TOP:
                b.set(this.u.left, y + this.u.top, this.u.right, this.u.bottom);
                if (b.height() < this.F) {
                    b.top = this.u.bottom - this.F;
                    break;
                }
                break;
            case EDGE_BOTTOM:
                b.set(this.u.left, this.u.top, this.u.right, y + this.u.bottom);
                if (b.height() < this.F) {
                    b.bottom = this.u.top + this.F;
                    break;
                }
                break;
            case VERTEX_LEFT_BOTTOM:
                if (this.N == CropMode.VERTEX) {
                    b.set(this.u.left + x, this.u.top, this.u.right, ((this.u.width() - x) / this.L) + this.u.top);
                } else {
                    b.set(x + this.u.left, this.u.top, this.u.right, y + this.u.bottom);
                }
                if (b.width() < this.F) {
                    b.left = this.u.right - this.F;
                }
                if (b.height() < this.F) {
                    b.bottom = this.u.top + this.F;
                }
                if (!a(getImageMatrix(), b)) {
                    b.set(this.u);
                    break;
                }
                break;
            case VERTEX_RIGHT_BOTTOM:
                if (this.N == CropMode.VERTEX) {
                    b.set(this.u.left, this.u.top, this.u.right + x, ((x + this.u.width()) / this.L) + this.u.top);
                } else {
                    b.set(this.u.left, this.u.top, x + this.u.right, y + this.u.bottom);
                }
                if (b.width() < this.F) {
                    b.right = this.u.left + this.F;
                }
                if (b.height() < this.F) {
                    b.bottom = this.u.top + this.F;
                }
                if (!a(getImageMatrix(), b)) {
                    b.set(this.u);
                    break;
                }
                break;
            case VERTEX_LEFT_TOP:
                if (this.N == CropMode.VERTEX) {
                    b.set(this.u.left + x, this.u.bottom - ((this.u.width() - x) / this.L), this.u.right, this.u.bottom);
                } else {
                    b.set(x + this.u.left, y + this.u.top, this.u.right, this.u.bottom);
                }
                if (b.width() < this.F) {
                    b.left = this.u.right - this.F;
                }
                if (b.height() < this.F) {
                    b.top = this.u.bottom - this.F;
                }
                if (!a(getImageMatrix(), b)) {
                    b.set(this.u);
                    break;
                }
                break;
            case VERTEX_RIGHT_TOP:
                if (this.N == CropMode.VERTEX) {
                    b.set(this.u.left, this.u.bottom - ((this.u.width() + x) / this.L), x + this.u.right, this.u.bottom);
                } else {
                    b.set(this.u.left, y + this.u.top, x + this.u.right, this.u.bottom);
                }
                if (b.width() < this.F) {
                    b.right = this.u.left + this.F;
                }
                if (b.height() < this.F) {
                    b.top = this.u.bottom - this.F;
                }
                if (!a(getImageMatrix(), b)) {
                    b.set(this.u);
                    break;
                }
                break;
            case BOX:
                RectF b2 = b();
                b2.set(this.d);
                getImageMatrix().mapRect(b2);
                b.set(this.u);
                b.offset(x, y);
                if (!this.ad) {
                    if (this.c.bottom > b2.bottom) {
                        this.ac = Math.min(this.c.bottom - ((float) this.ab), b.bottom);
                    } else {
                        this.ac = Math.min(b2.bottom - ((float) this.ab), b.bottom);
                    }
                    if (b.bottom > this.ac) {
                        b.offset(0.0f, -y);
                    }
                }
                if (b.left < b2.left) {
                    b.offset(b2.left - b.left, 0.0f);
                }
                if (b.top < b2.top) {
                    b.offset(0.0f, b2.top - b.top);
                }
                if (b.right > b2.right) {
                    b.offset(b2.right - b.right, 0.0f);
                }
                if (b.bottom > b2.bottom) {
                    b.offset(0.0f, b2.bottom - b.bottom);
                }
                a(b2);
                break;
            case BACKGROUND:
                return;
            default:
                a(c);
                a(b);
                return;
        }
        if (this.N != CropMode.VERTEX || Math.abs((b.width() / b.height()) - this.L) <= 0.05f) {
            pointF.set(motionEvent.getX(i), motionEvent.getY(i));
            c(b);
            a(c);
            a(b);
            if (this.cropBoxChangedListener != null) {
                this.cropBoxChangedListener.cropBoxChanged();
                this.cropBoxChangedListener = null;
                return;
            }
            return;
        }
        a(c);
        a(b);
    }

    private boolean a(Matrix matrix, RectF rectF) {
        boolean z = true;
        if (this.d.width() == 0.0f) {
            return false;
        }
        Matrix c = c();
        c.set(matrix);
        float e = e(c);
        Matrix c2 = c();
        c2.setRotate(-e);
        c.postConcat(c2);
        RectF b = b();
        b.set(this.d);
        c.mapRect(b);
        RectF b2 = b();
        b2.set(rectF);
        c2.mapRect(b2);
        if (!b.contains(b2) && (b2.width() > b.width() || b2.height() > b.height())) {
            z = false;
        }
        a(c);
        a(c2);
        a(b);
        a(b2);
        return z;
    }

    private boolean a(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() < 2) {
            return false;
        }
        float d = d(motionEvent);
        if (d < 10.0f) {
            return false;
        }
        float f = d / this.h;
        float c = c(motionEvent) - this.i;
        Matrix c2 = c();
        PointF e = e();
        c2.set(this.b);
        if (!this.o) {
            float dpToPx = (float) APPUtil.dpToPx(getContext(), 30.0f);
            if (((double) Math.abs((f / this.l) - 1.0f)) > 0.02d || Math.abs(d - this.k) > dpToPx) {
                this.o = true;
                this.n = true;
            } else if (Math.abs(c - this.m) > 1.0f || Math.abs(this.j - c(motionEvent)) > 8.0f) {
                this.o = true;
                this.n = false;
            }
            this.h = d(motionEvent);
            this.i = c(motionEvent);
        } else if (!this.n) {
            float dpToPx2 = (float) APPUtil.dpToPx(getContext(), 25.0f);
            if (Math.abs(c - this.m) > 1.0f || (((double) Math.abs((f / this.l) - 1.0f)) < 0.02d && Math.abs(d - this.k) < dpToPx2)) {
                c2.postRotate(c, e.x, e.y);
                this.h = d(motionEvent);
            } else {
                this.n = true;
                this.j = c(motionEvent);
                this.b.set(getImageMatrix());
                c2.set(this.b);
                c2.postScale(f, f, e.x, e.y);
            }
        } else if (((double) Math.abs((f / this.l) - 1.0f)) > 0.015d || (Math.abs(c - this.m) < 1.0f && Math.abs(this.j - c(motionEvent)) < 12.0f)) {
            c2.postScale(f, f, e.x, e.y);
            this.i = c(motionEvent);
        } else {
            this.n = false;
            this.k = d(motionEvent);
            this.b.set(getImageMatrix());
            c2.set(this.b);
            c2.postRotate(c, e.x, e.y);
        }
        this.m = c;
        this.l = f;
        if (this.o) {
            d(c2);
            setImageMatrix(c2);
        }
        a(c2);
        return true;
    }

    private RectF b() {
        return this.P.size() <= 0 ? new RectF() : (RectF) this.P.remove(0);
    }

    private TouchType b(float f, float f2) {
        float f3 = 1.0f;
        if (!isShowCrop()) {
            return TouchType.BACKGROUND;
        }
        RectF b = b();
        this.z = a(this.H[0], this.H[2], this.z, true);
        b.set(this.u.left + ((float) this.D), this.u.top - this.z, this.u.right - ((float) this.D), this.u.top + this.z);
        this.z = a(this.H[0], this.H[2], this.z, false);
        if (b.contains(f, f2)) {
            return TouchType.EDGE_TOP;
        }
        this.z = a(this.H[1], this.H[3], this.z, true);
        b.set(this.u.left + ((float) this.D), this.u.bottom - this.z, this.u.right - ((float) this.D), this.u.bottom + this.z);
        this.z = a(this.H[1], this.H[3], this.z, false);
        if (b.contains(f, f2)) {
            return TouchType.EDGE_BOTTOM;
        }
        this.z = a(this.H[2], this.H[0], this.z, true);
        b.set(this.u.left - this.z, this.u.top + ((float) this.D), this.u.left + this.z, this.u.bottom - ((float) this.D));
        this.z = a(this.H[2], this.H[0], this.z, false);
        if (b.contains(f, f2)) {
            return TouchType.EDGE_LEFT;
        }
        this.z = a(this.H[3], this.H[1], this.z, true);
        b.set(this.u.right - this.z, this.u.top + ((float) this.D), this.u.right + this.z, this.u.bottom - ((float) this.D));
        this.z = a(this.H[3], this.H[1], this.z, false);
        if (b.contains(f, f2)) {
            return TouchType.EDGE_RIGHT;
        }
        b.set(this.u);
        float f4 = this.C;
        float f5 = this.C;
        if (this.u.width() <= this.F + ((float) APPUtil.dpToPx(getContext(), 15.0f))) {
            f4 = 1.0f;
        }
        if (this.u.height() > ((float) APPUtil.dpToPx(getContext(), 15.0f)) + this.F) {
            f3 = f5;
        }
        b.inset(f4, f3);
        if (b.contains(f, f2)) {
            return TouchType.BOX;
        }
        b.set(this.u);
        b.inset(-this.C, -this.C);
        if (!b.contains(f, f2)) {
            return TouchType.BACKGROUND;
        }
        b.set(this.u.left - this.C, this.u.top - this.C, this.u.left + this.A, this.u.top + this.C);
        if (b.contains(f, f2)) {
            return TouchType.VERTEX_LEFT_TOP;
        }
        b.set(this.u.left - this.C, this.u.top - this.C, this.u.left + this.C, this.u.top + this.A);
        if (b.contains(f, f2)) {
            return TouchType.VERTEX_LEFT_TOP;
        }
        b.set(this.u.left - this.C, this.u.bottom - this.A, this.u.left + this.C, this.u.bottom + this.C);
        if (b.contains(f, f2)) {
            return TouchType.VERTEX_LEFT_BOTTOM;
        }
        b.set(this.u.left - this.C, this.u.bottom - this.C, this.u.left + this.A, this.u.bottom + this.C);
        if (b.contains(f, f2)) {
            return TouchType.VERTEX_LEFT_BOTTOM;
        }
        b.set(this.u.right - this.A, this.u.top - this.C, this.u.right + this.C, this.u.top + this.C);
        if (b.contains(f, f2)) {
            return TouchType.VERTEX_RIGHT_TOP;
        }
        b.set(this.u.right - this.C, this.u.top + this.C, this.u.right + this.C, this.u.top + this.A);
        if (b.contains(f, f2)) {
            return TouchType.VERTEX_RIGHT_TOP;
        }
        b.set(this.u.right - this.C, this.u.bottom - this.A, this.u.right + this.C, this.u.bottom + this.C);
        if (b.contains(f, f2)) {
            return TouchType.VERTEX_RIGHT_BOTTOM;
        }
        b.set(this.u.right - this.A, this.u.bottom - this.C, this.u.right + this.C, this.u.bottom + this.C);
        if (b.contains(f, f2)) {
            return TouchType.VERTEX_RIGHT_BOTTOM;
        }
        a(b);
        return TouchType.BACKGROUND;
    }

    private void b(Matrix matrix) {
        if (this.d.width() != 0.0f) {
            float e = e(matrix);
            Matrix c = c();
            Matrix c2 = c();
            c.setRotate(-e);
            c.invert(c2);
            matrix.postConcat(c);
            RectF b = b();
            b.set(this.d);
            matrix.mapRect(b);
            RectF b2 = b();
            b2.set(this.u);
            c.mapRect(b2);
            if (!b.contains(b2)) {
                if (b2.width() > b.width() || b2.height() > b.height()) {
                    float f = (b2.left + b2.right) / 2.0f;
                    float f2 = (b2.top + b2.bottom) / 2.0f;
                    e = b2.width() / b.width();
                    if (e <= 1.0f) {
                        e = 1.0f;
                    }
                    float height = b2.height() / b.height();
                    if (height > e) {
                        e = height;
                    }
                    PointF e2 = e();
                    height = this.u.width() / e;
                    float height2 = this.u.height() / e;
                    if (height < this.F) {
                        height = this.F;
                    }
                    if (height2 < this.F) {
                        height2 = this.F;
                    }
                    if (height2 != this.F || height != this.F) {
                        switch (this.G) {
                            case EDGE_LEFT:
                                this.u.set(this.u.right - height, this.u.top, this.u.right, this.u.bottom);
                                break;
                            case EDGE_RIGHT:
                                this.u.set(this.u.left, this.u.top, height + this.u.left, this.u.bottom);
                                break;
                            case EDGE_TOP:
                                this.u.set(this.u.left, this.u.bottom - height2, this.u.right, this.u.bottom);
                                break;
                            case EDGE_BOTTOM:
                                this.u.set(this.u.left, this.u.top, this.u.right, height2 + this.u.top);
                                break;
                            case VERTEX_LEFT_BOTTOM:
                                this.u.set(this.u.right - height, this.u.top, this.u.right, height2 + this.u.top);
                                break;
                            case VERTEX_RIGHT_BOTTOM:
                                this.u.set(this.u.left, this.u.top, height + this.u.left, height2 + this.u.top);
                                break;
                            case VERTEX_LEFT_TOP:
                                this.u.set(this.u.right - height, this.u.bottom - height2, this.u.right, this.u.bottom);
                                break;
                            case VERTEX_RIGHT_TOP:
                                this.u.set(this.u.left, this.u.bottom - height2, height + this.u.left, this.u.bottom);
                                break;
                            default:
                                this.u.set(e2.x - (height / 2.0f), e2.y - (height2 / 2.0f), (height / 2.0f) + e2.x, (height2 / 2.0f) + e2.y);
                                break;
                        }
                    }
                    this.u.set(e2.x - (height / 2.0f), e2.y - (height2 / 2.0f), (height / 2.0f) + e2.x, (height2 / 2.0f) + e2.y);
                    matrix.postScale(e, e, f, f2);
                    c.mapRect(b2, this.u);
                    b.set(this.d);
                    matrix.mapRect(b);
                    if (b2.top < b.top) {
                        matrix.postTranslate(0.0f, b2.top - b.top);
                    }
                    if (b2.bottom > b.bottom) {
                        matrix.postTranslate(0.0f, b2.bottom - b.bottom);
                    }
                    if (b2.left < b.left) {
                        matrix.postTranslate(b2.left - b.left, 0.0f);
                    }
                    if (b2.right > b.right) {
                        matrix.postTranslate(b2.right - b.right, 0.0f);
                    }
                } else {
                    if (b2.top < b.top) {
                        matrix.postTranslate(0.0f, b2.top - b.top);
                    }
                    if (b2.bottom > b.bottom) {
                        matrix.postTranslate(0.0f, b2.bottom - b.bottom);
                    }
                    if (b2.left < b.left) {
                        matrix.postTranslate(b2.left - b.left, 0.0f);
                    }
                    if (b2.right > b.right) {
                        matrix.postTranslate(b2.right - b.right, 0.0f);
                    }
                }
            }
            matrix.postConcat(c2);
            a(c);
            a(c2);
            a(b);
            a(b2);
        }
    }

    private void b(RectF rectF) {
        if (this.c.width() > 0.0f && this.c.height() > 0.0f) {
            float min;
            int i;
            int i2;
            float min2;
            RectF rectF2 = new RectF();
            if (rectF.width() > this.c.width() || rectF.height() > this.c.height()) {
                rectF2.set(this.c);
            } else {
                rectF2.set(rectF);
            }
            int width = this.c.width() > rectF2.width() ? (int) (((this.c.width() - rectF2.width()) / 2.0f) + 0.5f) : 0;
            int height = this.c.height() > rectF2.height() ? (int) (((this.c.height() - rectF2.height()) / 2.0f) + 0.5f) : 0;
            RectF b = b();
            if (this.ad) {
                min = Math.min(rectF2.width(), rectF2.height());
                i = 0;
            } else {
                i = APPUtil.dpToPx(getContext(), 55.0f);
                min = 0.0f;
            }
            if (min == 0.0f || !this.ae) {
                b.set(rectF2.width() * this.p.left, (rectF2.height() * this.p.top) - ((float) i), rectF2.width() * this.p.right, (rectF2.height() * this.p.bottom) - ((float) i));
            } else {
                b.set(this.p.left * min, (this.p.top * min) - ((float) i), this.p.right * min, (min * this.p.bottom) - ((float) i));
            }
            b.offset((float) width, (float) height);
            this.r.set(b);
            c(b);
            if (this.ad) {
                i2 = 0;
                min2 = Math.min(rectF2.width(), rectF2.height());
            } else {
                i2 = APPUtil.dpToPx(getContext(), 130.0f);
                min2 = 0.0f;
            }
            if (min2 == 0.0f || !this.ae) {
                b.set(rectF2.width() * this.q.left, rectF2.height() * this.q.top, rectF2.width() * this.q.right, (rectF2.height() * this.q.bottom) - ((float) i2));
            } else {
                b.set(this.q.left * min2, this.q.top * min2, this.q.right * min2, (min2 * this.q.bottom) - ((float) i2));
            }
            b.offset((float) width, (float) height);
            this.s.set(b);
            a(b);
        }
    }

    private void b(MotionEvent motionEvent) {
        postInvalidate();
        if (this.G != TouchType.NONE) {
            a(this.G, this.I, motionEvent, 0);
            if (this.J != TouchType.NONE) {
                a(this.J, this.K, motionEvent, 1);
            }
        }
    }

    private float c(Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        float f = fArr[0];
        float f2 = fArr[3];
        return (float) Math.sqrt((double) ((f2 * f2) + (f * f)));
    }

    private float c(MotionEvent motionEvent) {
        return a(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
    }

    private Matrix c() {
        if (this.Q.size() <= 0) {
            return new Matrix();
        }
        Matrix matrix = (Matrix) this.Q.remove(0);
        matrix.reset();
        return matrix;
    }

    private void c(RectF rectF) {
        RectF b = b();
        b.set(this.d);
        getImageMatrix().mapRect(b);
        if (!this.ad) {
            this.ac = Math.min(this.c.bottom - ((float) this.ab), b.bottom);
            b.bottom = this.ac;
        }
        a(rectF, b);
        if (rectF.left < b.left) {
            rectF.left = b.left;
        }
        if (rectF.right > b.right) {
            rectF.right = b.right;
        }
        if (rectF.top < b.top) {
            rectF.top = b.top;
            if (!this.ad) {
                return;
            }
        }
        if (rectF.bottom == b.bottom) {
            rectF.top = rectF.bottom - rectF.height();
        }
        if (rectF.bottom > b.bottom) {
            rectF.bottom = b.bottom;
            if (!this.ad) {
                if (rectF.height() < this.F) {
                    rectF.top = rectF.bottom - this.F;
                    return;
                }
                return;
            }
        }
        a(b);
        this.u.set(rectF);
    }

    private float d(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    private void d() {
        this.x = (float) APPUtil.dpToPx(getContext(), 400.0f);
        this.B = (float) APPUtil.dpToPx(getContext(), 0.5f);
        this.D = APPUtil.dpToPx(getContext(), 14.0f);
        this.A = ((float) this.D) * 1.5f;
        this.y = (float) APPUtil.dpToPx(getContext(), 4.0f);
        this.z = this.y * 4.0f;
        this.C = this.y * 8.0f;
        this.F = ((((float) this.D) + (this.y / 2.0f)) * 2.0f) + this.B;
        this.u = new RectF();
        this.t = new Paint();
        this.t.setAntiAlias(true);
    }

    private void d(Matrix matrix) {
        float c = c(matrix);
        if (c > 5.0f) {
            c = ((1.0f - (1.0f / (c - 4.0f))) + 5.0f) / c;
            PointF e = e();
            matrix.postScale(c, c, e.x, e.y);
            a(e);
        }
    }

    private float e(Matrix matrix) {
        if (matrix == null) {
            return 0.0f;
        }
        float[] fArr = new float[]{0.0f, 0.0f, 100.0f, 0.0f};
        matrix.mapPoints(fArr);
        return a(fArr[0], fArr[1], fArr[2], fArr[3]);
    }

    private PointF e() {
        PointF pointF = new PointF();
        pointF.set((this.u.left + this.u.right) / 2.0f, (this.u.top + this.u.bottom) / 2.0f);
        return pointF;
    }

    public RectF getCropRect() {
        RectF rectF = new RectF(this.u);
        rectF.offset((float) getPaddingLeft(), (float) getPaddingTop());
        return rectF;
    }

    public RectF getInitCropRect() {
        return this.p;
    }

    public Bitmap getResultBitmap() {
        this.U = true;
        if (this.O == null) {
            return null;
        }
        if (!this.M) {
            return this.O;
        }
        float c = c(getImageMatrix());
        try {
            Matrix c2;
            RectF rectF = new RectF(this.u);
            Matrix matrix = new Matrix(getImageMatrix());
            float f = 1.0f / c;
            PointF e;
            if (this.V == 2) {
                e = e();
                c2 = c();
                c2.setRotate(-90.0f, e.x, e.y);
            } else if (this.V == 3) {
                e = e();
                c2 = c();
                c2.setRotate(90.0f, e.x, e.y);
            } else {
                c2 = null;
            }
            if (c2 != null) {
                c2.mapRect(rectF);
                matrix.postConcat(c2);
            }
            matrix.postScale(f, f);
            rectF.set(rectF.left * f, rectF.top * f, rectF.right * f, f * rectF.bottom);
            Bitmap createBitmap = Bitmap.createBitmap((int) rectF.width(), (int) rectF.height(), Config.RGB_565);
            matrix.postTranslate(-rectF.left, -rectF.top);
            Canvas canvas = new Canvas(createBitmap);
            canvas.concat(matrix);
            canvas.drawBitmap(this.O, 0.0f, 0.0f, null);
            return createBitmap;
        } catch (OutOfMemoryError e2) {
            return null;
        } catch (Exception e3) {
            return null;
        }
    }

    public boolean isInAnimation() {
        return (!this.f.computeScrollOffset() && this.S == null && this.v == null) ? false : true;
    }

    public boolean isShowCrop() {
        return this.M;
    }

    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
        }
        if (this.f.computeScrollOffset()) {
            Matrix c = c();
            c.set(this.b);
            c.postTranslate((float) this.f.getCurrX(), (float) this.f.getCurrY());
            setImageMatrix(c);
            a(c);
            invalidate();
        } else if (this.S != null) {
            boolean transformation = this.S.getTransformation(getDrawingTime(), this.T);
            Matrix c2 = c();
            Matrix matrix = this.T.getMatrix();
            c2.set(this.b);
            c2.postConcat(matrix);
            if (transformation) {
                setImageMatrix(c2);
                if (this.M) {
                    canvas.save();
                    if ((this.S instanceof AnimationSet) || (this.S instanceof TranslateAnimation)) {
                        canvas.concat(matrix);
                    }
                    canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
                    a(canvas);
                    canvas.restore();
                } else {
                    return;
                }
            }
            a(this.S, c2, matrix);
            this.S = null;
            setImageMatrix(c2);
            a(c2);
            invalidate();
            if (transformation) {
                return;
            }
        }
        if (this.M) {
            canvas.save();
            canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
            a(canvas);
            canvas.restore();
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            this.c.set(0.0f, 0.0f, (float) (getWidth() - (getPaddingLeft() + getPaddingRight())), (float) (getHeight() - (getPaddingTop() + getPaddingBottom())));
            if (getDrawable() == null) {
                this.d.set(this.c);
                b(this.c);
            } else if (this.O != null) {
                a(this.O);
            }
        }
    }

    @SuppressLint({"NewApi", "ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        if (isInAnimation() && motionEvent.getPointerCount() > 2) {
            return false;
        }
        motionEvent.offsetLocation((float) getPaddingLeft(), (float) getPaddingTop());
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (this.g == null) {
            this.g = VelocityTracker.obtain();
        }
        this.g.addMovement(motionEvent);
        int actionIndex = motionEvent.getActionIndex();
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.f.forceFinished(true);
                this.I.set(x, y);
                this.b.set(getImageMatrix());
                this.G = b(x, y);
                this.L = this.u.width() / this.u.height();
                return true;
            case 1:
                this.G = TouchType.NONE;
                this.J = TouchType.NONE;
                this.g.recycle();
                this.g = null;
                this.a.clear();
                return true;
            case 2:
                this.a.show();
                if (this.G == TouchType.BACKGROUND) {
                    return true;
                }
                this.U = true;
                b(motionEvent);
                return true;
            case 5:
                if (this.G == TouchType.NONE || this.G == TouchType.BACKGROUND) {
                    return true;
                }
                x = motionEvent.getX(1);
                y = motionEvent.getY(1);
                this.K.set(x, y);
                this.J = b(x, y);
                switch (this.G) {
                    case EDGE_LEFT:
                    case EDGE_RIGHT:
                    case EDGE_TOP:
                    case EDGE_BOTTOM:
                    case VERTEX_LEFT_BOTTOM:
                    case VERTEX_RIGHT_BOTTOM:
                    case VERTEX_LEFT_TOP:
                    case VERTEX_RIGHT_TOP:
                    case BOX:
                        switch (this.J) {
                            case EDGE_LEFT:
                            case EDGE_RIGHT:
                            case EDGE_TOP:
                            case EDGE_BOTTOM:
                            case VERTEX_LEFT_BOTTOM:
                            case VERTEX_RIGHT_BOTTOM:
                            case VERTEX_LEFT_TOP:
                            case VERTEX_RIGHT_TOP:
                                return true;
                            default:
                                this.J = TouchType.NONE;
                                return true;
                        }
                    default:
                        this.J = TouchType.NONE;
                        return true;
                }
            case 6:
                if (actionIndex == 0 || this.G == TouchType.SCALE_AND_ROTATE_BACKGROUND) {
                    this.G = TouchType.NONE;
                }
                this.J = TouchType.NONE;
                return true;
            default:
                return true;
        }
    }

    public void resetCrop() {
        b(this.c);
    }

    public void rotate(int i) {
        rotate(i, true);
    }

    public void rotate(int i, boolean z) {
        if (this.S == null && this.c.width() != 0.0f) {
            if (this.V == -1) {
                this.U = true;
            } else if (!this.U) {
                return;
            }
            this.b.set(getImageMatrix());
            PointF e = e();
            Animation rotateAnimation = new RotateAnimation(0.0f, (float) i, e.x, e.y);
            if (i % 90 == 0 && z) {
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
                rotateAnimation.setDuration(300);
                animationSet.addAnimation(rotateAnimation);
                Matrix c = c();
                c.set(getImageMatrix());
                c.postRotate((float) i, e.x, e.y);
                RectF b = b();
                c.mapRect(b, this.d);
                PointF a = a((b.left + b.right) / 2.0f, (b.top + b.bottom) / 2.0f);
                Animation translateAnimation = new TranslateAnimation(0.0f, a.x, 0.0f, a.y);
                translateAnimation.setDuration(150);
                translateAnimation.setStartOffset(rotateAnimation.getDuration());
                animationSet.addAnimation(translateAnimation);
                float width = this.c.width() / b.width();
                if (this.c.height() / b.height() < width) {
                    width = this.c.height() / b.height();
                }
                rotateAnimation = new ScaleAnimation(1.0f, width, 1.0f, width, e.x, e.y);
                rotateAnimation.setStartOffset(300);
                rotateAnimation.setDuration(200);
                animationSet.addAnimation(rotateAnimation);
                animationSet.initialize(0, 0, 0, 0);
                this.S = animationSet;
            } else {
                rotateAnimation.initialize(0, 0, 0, 0);
                rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                rotateAnimation.setDuration(300);
                this.S = rotateAnimation;
            }
            postInvalidate();
        }
    }

    public void rotateByRightAngle() {
        rotate(-90, true);
    }

    public void setBottomHeight(int i) {
        this.ab = i;
    }

    public Bitmap setCameraDisplayOrientation(int i, int i2, Bitmap bitmap) {
        int i3 = 0;
        switch (i2) {
            case 1:
                i3 = Surface.ROTATION_90;
                break;
            case 2:
                i3 = Surface.ROTATION_180;
                break;
            case 3:
                i3 = Surface.ROTATION_270;
                break;
        }
        return a(bitmap, ((i - i3) + 360) % 360);
    }

    public void setCameraInfoRotation(int i, int i2) {
        this.af = i;
        this.ag = i2;
    }

    public void setCropBoxChangedListener(CropBoxChangedListener cropBoxChangedListener) {
        this.cropBoxChangedListener = cropBoxChangedListener;
    }

    public void setCropMode(CropMode cropMode) {
        this.N = cropMode;
        if (this.N == CropMode.NONE) {
            setShowCrop(false);
        } else if (this.N == CropMode.VERTEX) {
            this.V = 1;
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        if (!this.ad && VERSION.SDK_INT >= 23 && Build.MODEL.equals("Nexus 5X")) {
            bitmap = a(bitmap, 180);
        }
        super.setImageBitmap(bitmap);
        this.O = bitmap;
        this.d.set(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight());
        a(bitmap);
        if (this.aa) {
            this.aa = false;
            if (this.V != 1) {
                this.u.set(this.s);
            } else {
                this.u.set(this.r);
            }
        }
    }

    public void setImageMatrix(Matrix matrix) {
        if (this.S == null) {
            b(matrix);
        }
        super.setImageMatrix(matrix);
    }

    public void setImageResource(int i) {
        Drawable drawable = getResources().getDrawable(i);
        if (drawable instanceof BitmapDrawable) {
            setImageBitmap(((BitmapDrawable) drawable).getBitmap());
        } else {
            setImageDrawable(drawable);
        }
    }

    public void setInitCropRect(RectF rectF) {
        if (this.O != null) {
            throw new RuntimeException("setInitCropRect should be called before set bitmap");
        }
        this.p.set(rectF);
    }

    public void setIsCropAvatar(boolean z) {
        this.ae = z;
    }

    public void setIsFromAlbum(boolean z) {
        this.ad = z;
    }

    public void setOrientation(int i) {
        boolean z = false;
        this.ag = i;
        if (i != this.V) {
            if (this.O == null) {
                this.W = false;
                this.V = i;
                this.aa = true;
            } else if (this.W) {
                this.W = false;
                this.V = i;
                if (this.V == 1) {
                    this.u.set(this.r);
                } else {
                    this.u.set(this.s);
                }
                postInvalidate();
            } else {
                if (this.V == 1) {
                    if (i == 2 || i == 3) {
                        z = true;
                    }
                } else if (this.V == 2) {
                    if (i == 1) {
                        z = true;
                    } else {
                        this.U = true;
                    }
                } else if (this.V == 3) {
                    if (i == 1) {
                        z = true;
                    } else {
                        this.U = true;
                    }
                }
                removeCallbacks(this.ah);
                if (z) {
                    this.V = i;
                    postInvalidate();
                    post(this.ah);
                }
            }
        }
    }

    public void setShowCrop(boolean z) {
        this.M = z;
        postInvalidate();
    }
}