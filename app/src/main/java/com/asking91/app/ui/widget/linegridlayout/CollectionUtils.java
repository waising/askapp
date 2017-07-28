package com.asking91.app.ui.widget.linegridlayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jswang on 2017/3/6.
 */

public final class CollectionUtils {
    public static <T> List<T> intersect(List<T> list, List<T> list2) {
        List<T> arrayList = new ArrayList(Arrays.asList(new Object[list.size()]));
        Collections.copy(arrayList, list);
        arrayList.retainAll(list2);
        return arrayList;
    }

    public static <T> List<T> asList(T... tArr) {
        if (tArr == null) {
            return null;
        }
        return new ArrayList<T>(Arrays.asList(tArr));
    }

    public static <T> List<T> union(List<T> list, List<T> list2) {
        List<T> arrayList = new ArrayList(Arrays.asList(new Object[list.size()]));
        Collections.copy(arrayList, list);
        arrayList.removeAll(list2);
        arrayList.addAll(list2);
        return arrayList;
    }

    public static <T> List<T> diff(List<T> list, List<T> list2) {
        List<T> union = union(list, list2);
        union.removeAll(intersect(list, list2));
        return union;
    }

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean inArray(T t, List<T> list) {
        if (t == null || isEmpty(list)) {
            return false;
        }
        return list.contains(t);
    }

    public static <T> void addAll(List<T> list, T... tArr) {
        list.addAll(Arrays.asList(tArr));
    }

    public static <T> int size(List<T> list) {
        return list == null ? 0 : list.size();
    }
}
