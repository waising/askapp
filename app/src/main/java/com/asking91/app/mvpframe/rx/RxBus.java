package com.asking91.app.mvpframe.rx;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 用RxJava实现的EventBus
 *
 * @author baixiaokang
 */
public class RxBus {
    private static RxBus instance;

    private final Subject<Object, Object> mBusSubject = new SerializedSubject<>(PublishSubject.create());

    public static synchronized RxBus $() {
        if (null == instance) {
            instance = new RxBus();
        }
        return instance;
    }

    private RxBus() {
    }

    public <T> Subscription register(final Class<T> eventClass, Action1<T> onNext) {
        return mBusSubject.filter(new Func1<Object, Boolean>() {

            @Override
            public Boolean call(Object event) {
                return event.getClass().equals(eventClass);
            }
        }).map(new Func1<Object, T>() {
            @Override
            public T call(Object o) {
                return (T)o;
            }
        }).subscribe(onNext);
    }

    public void post(Object event) {
        mBusSubject.onNext(event);
    }

}
