package com.asking91.app.interceptor;

import android.util.Log;

import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.widget.camera.comm.AppManager;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.ToastUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wxwang on 2016/12/1.
 */
public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        RequestBody requestBody = request.body();

        Response response = chain.proceed(request);

        //权限不足 && !UserConstant.getInstance(Asking91.getmInstance().getApplicationContext()).isTokenLogin()
        if(response.code()==401 && -1 == request.url().encodedPath().indexOf("applogin")){
            ToastUtil.showMessage("你还没有登录，请登录后在操作!");
            CommonUtil.openActivity(LoginActivity.class);
        }

        return response;
    }

}
