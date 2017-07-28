package com.asking91.app.ui.widget.camera.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;
import java.util.LinkedList;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

public class h {
    private static h a = null;
    private Context b;
    private SensorManager c;
    private Sensor d;
    private OrientationEventListener e;
    private int f = 1;
    private List<a> g = new LinkedList();
    private List<c> h = new LinkedList();
    private b i = new b(this) {
        boolean a = false;
        boolean b = false;
        private long e = 0;
        private float f = 0.0f;
        private float g;
        private float h;
        private float i;

        public float[] a() {
            return new float[]{this.g, this.h, this.i};
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] fArr = sensorEvent.values;
            float f = fArr[0] - this.g;
            float f2 = fArr[1] - this.h;
            float f3 = fArr[2] - this.i;
            this.g = fArr[0];
            this.h = fArr[1];
            this.i = fArr[2];
            if (!this.a) {
                this.a = true;
            } else if (this.dddd.f == 1) {
                double sqrt = Math.sqrt((double) (((f * f) + (f2 * f2)) + (f3 * f3)));
                if (sqrt >= 0.5d) {
                    this.f = (float) (sqrt + ((double) this.f));
                    if (((double) this.f) > 1.2d) {
                        this.b = true;
                        this.e = System.currentTimeMillis();
                        this.dddd.c();
                    }
                } else if (this.b && System.currentTimeMillis() - this.e > 200) {
                    this.dddd.b();
                    this.b = false;
                    this.f = 0.0f;
                }
            } else if (this.dddd.f != 2) {
            } else {
                if ((((double) f) > 0.5d || ((double) f2) > 0.5d || (((double) f3) > 0.5d && this.b)) && System.currentTimeMillis() - this.e > 200) {
                    this.dddd.b();
                    this.f = 0.0f;
                    this.b = true;
                    this.e = System.currentTimeMillis();
                    this.dddd.c();
                }
            }
        }
    };
    private int j = -1;
    private int k = 1;
    private int l = 1;
    private int m = 1;
    private final int n = 3;

    public interface c {
        void a(int i);
    }

    public interface a {
        void onDeviceMoved();

        void onDeviceMoving();
    }

    private abstract class b implements SensorEventListener {
        h dddd;

        private b(h hVar) {
            this.dddd = hVar;
        }

        public abstract float[] a();
    }

    private h(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        this.b = context.getApplicationContext();
        this.c = (SensorManager) this.b.getSystemService(SENSOR_SERVICE);
        List sensorList = this.c.getSensorList(1);
        if (sensorList.size() > 0) {
            this.d = (Sensor) sensorList.get(0);
        }
        this.e = new OrientationEventListener(this.b, 3) {
            public void onOrientationChanged(int i) {
                int i2 = i - 90;
                if (i2 < 0) {
                    i2 += 360;
                }
                a((float) i2);
            }
        };
    }

    public static h a(Context context) {
        if (a != null) {
            return a;
        }
        a = new h(context);
        return a;
    }

    private void a(float f) {
        if (f < 0.0f) {
            f += 360.0f;
        }
        int i = 0;
        if (f <= 45.0f || f > 135.0f) {
            if (f > 135.0f && f <= 225.0f) {
                this.k++;
                if (this.k >= 3) {
                    i = 2;
                } else {
                    return;
                }
            } else if (f <= 45.0f || f >= 315.0f) {
                this.l++;
                if (this.l >= 3) {
                    i = 3;
                } else {
                    return;
                }
            } else {
                this.m++;
                if (this.m >= 3) {
                    i = 1;
                } else {
                    return;
                }
            }
        } else if (this.j > 0) {
            i = this.j;
        }
        if (i != this.j) {
            this.k = 1;
            this.l = 1;
            this.m = 1;
            this.j = i;
            b(i);
        }
    }

    private void b() {
        for (a onDeviceMoved : this.g) {
            onDeviceMoved.onDeviceMoved();
        }
    }

    private void b(int i) {
        for (c a : this.h) {
            a.a(i);
        }
    }

    private void c() {
        for (a onDeviceMoving : this.g) {
            onDeviceMoving.onDeviceMoving();
        }
    }

    private void d() {
        this.c.registerListener(this.i, this.d, 3);
    }

    private void e() {
        this.c.unregisterListener(this.i);
    }

    private void f() {
        this.e.enable();
    }

    private void g() {
        this.e.disable();
    }

    public void a(int i) {
        this.f = i;
    }

    public void a(a aVar) {
        if (aVar != null && !this.g.contains(aVar)) {
            if (this.g.size() == 0) {
                d();
            }
            this.g.add(aVar);
        }
    }

    public void a(c cVar) {
        if (cVar != null && !this.h.contains(cVar)) {
            if (this.h.size() == 0) {
                f();
            }
            if (this.j != -1) {
                cVar.a(this.j);
            }
            this.h.add(cVar);
        }
    }

    public float[] a() {
        return this.i.a();
    }

    public void b(a aVar) {
        this.g.remove(aVar);
        if (this.g.size() == 0) {
            e();
        }
    }

    public void b(c cVar) {
        this.h.remove(cVar);
        if (this.h.size() == 0) {
            g();
        }
    }
}