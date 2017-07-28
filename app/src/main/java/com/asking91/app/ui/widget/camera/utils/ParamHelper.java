package com.asking91.app.ui.widget.camera.utils;

import java.util.HashMap;

public class ParamHelper {
    private static ParamHelper a = new ParamHelper();
    private HashMap<String, HashMap<String, Object>> b = new HashMap<String, HashMap<String, Object>>();
    private HashMap<String, Object> c = new HashMap<String, Object> ();

    private HashMap<String, Object> a(String str) {
        if (str == null) {
            throw new IllegalArgumentException("cls == null");
        }
        HashMap<String, Object> hashMap = (HashMap<String, Object>) this.b.get(str);
        if (hashMap != null) {
            return hashMap;
        }
        hashMap = new HashMap<String, Object>();
        this.b.put(str, hashMap);
        return hashMap;
    }

    private HashMap<String, Object> a(String str, boolean z) {
        if (str == null) {
            throw new IllegalArgumentException("cls == null");
        }
        HashMap<String, Object> hashMap = z ? (HashMap<String, Object>) this.b.remove(str) : (HashMap<String, Object>) this.b.get(str);
        if (hashMap != null) {
            return hashMap;
        }
        this.c.clear();
        return this.c;
    }

    private void a() {
        this.b.clear();
    }

    public static HashMap<String, Object> acceptParams(String str) {
        return a.a(str, true);
    }

    public static HashMap<String, Object> acceptParams(String str, boolean z) {
        return a.a(str, z);
    }

    public static HashMap<String, Object> acquireParamsReceiver(String str) {
        return a.a(str);
    }

    public static void clear() {
        a.a();
    }
}