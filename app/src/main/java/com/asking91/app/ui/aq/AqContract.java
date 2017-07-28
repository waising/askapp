
package com.asking91.app.ui.aq;

import com.asking91.app.mvpframe.BaseModel;
import com.asking91.app.mvpframe.BasePresenter;
import com.asking91.app.mvpframe.BaseView;

import okhttp3.ResponseBody;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 */

public interface AqContract {

    interface Model extends BaseModel {
        Observable<ResponseBody> chagePassword(@Query("oldPass") String oldPass,
                                               @Query("pass") String pass,
                                               @Query("pass1") String pass1);
    }

    interface View extends BaseView {
        void onSuccess(ResponseBody body);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public abstract void chagePassword(String oldPass, String pass, String pass1);
    }



}
