package com.asking91.app.ui.aq;

import com.asking91.app.api.Networks;
import com.asking91.app.mvpframe.rx.RxSchedulers;

import okhttp3.ResponseBody;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 */
public class AqModel implements AqContract.Model {

    @Override
    public Observable<ResponseBody> chagePassword(@Query("oldPass") String oldPass, @Query("pass") String pass, @Query("pass1") String pass1) {
        return Networks.getInstance().getUserApi().chagePassword(oldPass,pass,pass1)
                .compose(RxSchedulers.<ResponseBody>io_main());
    }
}
