package com.asking91.app.util;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by wxiao on 2016/10/27.
 * 倒计时工具
 */

public class RxCountDown {

    public static Observable<Integer> countdown(int time) {
        if (time < 0) time = 0;

        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long increaseTime) {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 1);

    }

    public interface CountDownListener {
        void onComplete();
    }


    public static Observable<Integer> countUp(int time) {
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long increaseTime) {
                        return countTime + increaseTime.intValue();
                    }
                });

    }

    /**
     * 倒计时
     *
     * @param time              时间
     * @param countDownListener 完成后的事件
     */
    public static void countDown(final int time, final CountDownListener countDownListener) {
//        countdown(time)
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
                        RxCountDown.countdown(time).doOnSubscribe(new Action0() {
                                    @Override
                                    public void call() {
//                    appendLog("开始计时");
                                    }
                                })
                                .subscribe(new Subscriber<Integer>() {
                                    @Override
                                    public void onCompleted() {
                                        countDownListener.onComplete();
//            appendLog("计时完成");
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Integer integer) {
//            appendLog("当前计时：" + integer);
                                    }
                                });
//                    }
//                });
    }
}