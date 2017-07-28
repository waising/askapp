package com.asking91.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.util.ArrayMap;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.asking91.app.Asking91;
import com.asking91.app.R;
import com.asking91.app.common.BaseActivity;
import com.asking91.app.entity.IntentCarrier;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.HttpCodeConstant;
import com.asking91.app.global.UserConstant;
import com.asking91.app.mvpframe.CountingRequestBody;
import com.asking91.app.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Vector;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by wxiao on 2016/10/12.
 */

public class CommonUtil {

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获取电话号码
     */
    public static String getNativePhoneNumber(Context context) {
        return ((TelephonyManager) context .getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
    }

    /**
     * 获取上传文件body
     * @param context
     * @param bitmap            需要上传的bitmap图片
     * @param filePathAndName   bitmap缓存的路径和文件名
     * @return
     * 上传参数默认file
     */
    public static MultipartBody.Part getMultipartBodyPart(Context context, Bitmap bitmap, String filePathAndName){
        return getMultipartBodyPart(context, bitmap, filePathAndName, null);
    }

    /**
     * 获取上传文件body
     * {@link #getMultipartBodyPart(Context, Bitmap, String)}
     * @param fileParameter     上传文件对应参数
     * @return
     *
     */
    public static MultipartBody.Part getMultipartBodyPart(Context context, Bitmap bitmap, String filePathAndName, String fileParameter){
//        try {
//            //保存切图图片到本地
//            ImageUtils.saveToFile(mContext, com.devtf.belial.camera.util.FileUtils.getInstance(mContext).getCacheDir(mContext) + "/croppedcache.jpg",
//                    false, bitmap);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        File f = new File(filePathAndName);
        JLog.i("CommonUtil", "f.path="+f.getAbsolutePath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        //监听上传进度
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestFile, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {
                JLog.i("CommonUtil", "上传进度:" + contentLength + ":" + bytesWritten);
            }
        });
        if(fileParameter==null){
            return MultipartBody.Part.createFormData("file", f.getName(),countingRequestBody);
        }else {
            return MultipartBody.Part.createFormData(fileParameter, f.getName(), countingRequestBody);
        }
    }
    /**
     *
     * 函数名称: parseDataToMap
     * 函数描述: 将json字符串转换为map
     * @return
     */
    public static Map<String, Object> parseDataToMap(String msg){
        return new Gson().fromJson(msg, new TypeToken<Map<String, Object>>() {}.getType());
    }

    /**
     *
     * 函数名称: parseDataToMap
     * 函数描述: 将json字符串转换为map
     * @return
     */
    public static Map<String, Object> parseDataToMap(Object msg){
        Gson gson = new Gson();
        String gs = gson.toJson(msg);
        return gson.fromJson(gs, new TypeToken<Map<String, Object>>() {}.getType());
    }

    /**
     *
     * 函数名称: parseDataToMap
     * 函数描述: 将json字符串转换为map
     * @return
     */
    public static ArrayMap<String, Object> parseDataToMap(ResponseBody body){
        Gson gson = new Gson();
        ArrayMap<String, Object> map = new ArrayMap<>();
        try {
            map =  gson.fromJson(body.string(), new TypeToken<ArrayMap<String, Object>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * String obj 转 list
     * @param obj
     * @param <T>
     * @return
     */
    public static<T> T parseDataToList(Object obj, TypeToken<T> token){
        Gson gson = new Gson();
        T list = gson.fromJson(gson.toJson(obj),token.getType());
        return list;
    }
    /**
     * String obj 转 list
     * @param
     * @param <T>
     * @return
     */
    public static<T> T parseDataToList(ResponseBody body, TypeToken<T> token){
        Gson gson = new Gson();
        T list = null;
        try {
            list = gson.fromJson(body.string(),token.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static<T> T data2Clazz(String json, Class<T> clazz){
        return new Gson().fromJson(json,clazz);
    }

    public static<T> T data2Clazz(Object obj, Class<T> clazz){
        Gson gson = new Gson();
        return new Gson().fromJson(gson.toJson(obj),clazz);
    }

    public static<T> T data2Clazz(ResponseBody body, Class<T> clazz){
        Gson gson = new Gson();
        try {
            return gson.fromJson(body.string(),clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void Toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static void openActivity(Context context, Class<? extends BaseActivity> toActivity){
        openActivity(context,toActivity,null);
    }

    public static void openActivity(Context context, Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(context, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.enter_left_in,R.anim.enter_left_out);
    }

    public static void openAuthActivity(Context context, Class<? extends BaseActivity> toActivity, Bundle parameter) {

        //判断登陆状态
        if (UserConstant.getInstance(context).isTokenLogin()) {
            openActivity(context,toActivity,parameter);
        }else{
            //跳转登陆
            IntentCarrier intentCarrier = new IntentCarrier(toActivity.getName(),parameter);
            if(parameter==null){
                parameter = new Bundle();
            }
            parameter.putBoolean(Constants.activity.toOtherActivity,true);
            parameter.putParcelable(Constants.activity.interceptorInvoker,intentCarrier);

            openActivity(context,LoginActivity.class,parameter);
        }
    }

    public static void openAuthActivity(Context context, Class<? extends BaseActivity> toActivity) {
        openAuthActivity(context,toActivity,null);
    }


    public static void openActivity(Context context,String backActivityName, Bundle parameter) {
        Intent intent = null;
        try {
            intent = new Intent(context, Class.forName(backActivityName));
            if (parameter != null) {
                intent.putExtras(parameter);
            }
            context.startActivity(intent);
            ((Activity)context).overridePendingTransition(R.anim.enter_left_in,R.anim.enter_left_out);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转 非activity页面使用
     * @param toActivity
     * @param bundle
     */
    public static void openActivity(Class<? extends BaseActivity> toActivity ,Bundle bundle){
        Intent intent=new Intent(Asking91.getmInstance().getApplicationContext(),toActivity);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        Asking91.getmInstance().startActivity(intent);
    }
    public static void openActivity(Class<? extends BaseActivity> toActivity){
        openActivity(toActivity,null);
    }

    public static void openResultActivity(Context context,Class<?> toActivity, int requestCode, Bundle parameter) {
        Intent intent = new Intent(context, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        ((Activity)context).startActivityForResult(intent,requestCode);
        ((Activity)context).overridePendingTransition(R.anim.enter_left_in,R.anim.enter_left_out);
    }

    public static void openBackResultActivity(Context context,Class<?> toActivity, Bundle parameter) {
        Intent intent = new Intent(context, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        ((Activity)context).setResult(Activity.RESULT_OK, intent);
        ((Activity)context).finish();
    }

    public static void openResultActivity(Context context,Class<?> toActivity, int requestCode) {
        openResultActivity(context,toActivity,requestCode,null);
    }

    public static void openBackResultActivity(Context context,Class<?> toActivity) {
        openBackResultActivity(context,toActivity,null);
    }


    /**
     * 切割字符串
     *
     * @param fgf
     *            分隔符
     * @param content
     *            内容
     * @return
     */
    public static String[] splitString(String fgf, String content) {
        String[] result = null;
        if (content.indexOf(fgf) == -1) {
            result = new String[1];
            result[0] = content;

        } else {
            Vector<String> vector = new Vector<String>();
            int nowposition = 0;
            int nextpos;
            String temp;
            nextpos = content.indexOf(fgf, nowposition);
            temp = read(fgf, content, nowposition, nextpos);
            while (temp != "") {
                vector.add(temp);
                nowposition = nextpos + fgf.length();
                nextpos = content.indexOf(fgf, nowposition);
                if (nextpos < 0 && nowposition < content.length())// 如果现在为止不是最后，当时又找不到下一个分隔符的位置，说明当前这个是最后一个，直接加入，退出
                {
                    temp = read(fgf, content, nowposition, content.length());
                    vector.add(temp);
                    break;
                }
                temp = read(fgf, content, nowposition, nextpos);
            }
            if (content.substring(content.length() - fgf.length(),
                    content.length()).equals(fgf))// 如果最后一个是fgf，添加最后一个数组纬度，内容为“”
                result = new String[vector.size() + 1];
            else
                result = new String[vector.size()];
            for (int i = 0; i < vector.size(); i++) {
                result[i] = vector.elementAt(i).toString();
            }
            if (content.substring(content.length() - fgf.length(),
                    content.length()).equals(fgf))
                result[result.length - 1] = "";
        }
        return result;
    }

    public static String read(String FGF, String content, int nowposition,
                              int nextpos) {
        if (nextpos < 0) {
            return "";
        } else {
            String result = content.substring(nowposition, nextpos);
            return result;
        }
    }

    public static RequestEntity getRequestEntity(Throwable throwable){
        return getRequestEntity(throwable,null);
    }

    public static RequestEntity getRequestEntity(Throwable throwable,String tag){
        RequestEntity requestEntity = new RequestEntity();
        try {
            requestEntity.setTag(tag==null?"":tag);
            if(throwable.getMessage()!=null && throwable.getMessage().indexOf("No address associated with hostname")!=-1){
                requestEntity.setDetailMessage(throwable.getMessage());
                requestEntity.setCode(((HttpException)throwable).code());
                requestEntity.setMessage( ((HttpException)throwable).message());
            }else if(throwable instanceof HttpException){
                if(((HttpException) throwable).code()==404)
                    requestEntity.setCode(HttpCodeConstant.CONNECT_404);
                else if(((HttpException) throwable).code()==502)
                    requestEntity.setCode(HttpCodeConstant.CONNECT_404);
                else if(((HttpException) throwable).code()==401)
                    requestEntity.setCode(HttpCodeConstant.CONNECT_401);
            }
            else if(throwable instanceof java.net.ConnectException){
                requestEntity.setCode(HttpCodeConstant.CONNECT_FAIL);
            }
            else if(throwable instanceof SocketTimeoutException){
                requestEntity.setCode(HttpCodeConstant.TIME_OUT);
            }else{
                requestEntity.setCode(HttpCodeConstant.APP_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return requestEntity;
    }


    /**
     * 以最省内存的方式读取本地资源的图片
     * @param context  上下文
     * @param resId 资源Id
     * @return 返回bitmap
     */
    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        //压缩编码
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        //下面两个过时了，但没影响
        opt.inPurgeable = true;
        opt.inInputShareable = true;

        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }


    /**
     * 以最省内存的方式读取本地资源的图片
     * @param context  上下文
     * @return 返回bitmap
     */
    public static Bitmap readBitMap(Context context, String imgPath){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        //压缩编码
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        //下面两个过时了，但没影响
        opt.inPurgeable = true;
        opt.inInputShareable = true;

        InputStream is = null;
        try {
            is = context.openFileInput(imgPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is,null,opt);
    }

    /**
     * 图片转灰度
     *
     * @param bmSrc
     * @return
     */
    public static Bitmap bitmap2Gray(Bitmap bmSrc)
    {
        int width, height;
        height = bmSrc.getHeight();
        width = bmSrc.getWidth();
        Bitmap bmpGray = null;
        bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0, 0, paint);
        return bmpGray;
    }

    public static Bitmap decodeBitmapWithOrientationMax(String pathName, int width, int height) {
        return decodeBitmapWithSize(pathName, width, height, true);
    }

    private static Bitmap decodeBitmapWithSize(String pathName, int width, int height,
                                               boolean useBigger) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inInputShareable = true;
        options.inPurgeable = true;
        BitmapFactory.decodeFile(pathName, options);

        int decodeWidth = width, decodeHeight = height;
        final int degrees = getImageDegrees(pathName);
        if (degrees == 90 || degrees == 270) {
            decodeWidth = height;
            decodeHeight = width;
        }

        if (useBigger) {
            options.inSampleSize = (int) Math.min(((float) options.outWidth / decodeWidth),
                    ((float) options.outHeight / decodeHeight));
        } else {
            options.inSampleSize = (int) Math.max(((float) options.outWidth / decodeWidth),
                    ((float) options.outHeight / decodeHeight));
        }

        options.inJustDecodeBounds = false;
        Bitmap sourceBm = BitmapFactory.decodeFile(pathName, options);
        return imageWithFixedRotation(sourceBm, degrees);
    }

    public static int getImageDegrees(String pathName) {
        int degrees = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(pathName);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degrees = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degrees = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degrees = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degrees;
    }
    public static Bitmap imageWithFixedRotation(Bitmap bm, int degrees) {
        if (bm == null || bm.isRecycled())
            return null;

        if (degrees == 0)
            return bm;

        final Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        Bitmap result = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        if (result != bm)
            bm.recycle();
        return result;

    }

    /** 保存方法 */
    public static void saveBitmap(String path, Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(),Constants.APP_CACHE_IMG_GRAY_PATH);
        if(!file.exists()){
            file.mkdirs();//创建文件夹
        }else if(!file.isDirectory() && file.canWrite()){
            file.delete();
            file.mkdirs();
        }
        File f = new File(Environment.getExternalStorageDirectory(), path);
        if (f.exists()) {
            f.delete();
        }else{
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public static void deleteCacheFile(){
        if(new File(Constants.APP_CACHE_PATH+"cache").isFile())
            FileUtils.deleteFile(Constants.APP_CACHE_PATH+"cache");
    }

    /**
     * 汉王云解析替换图片
     * @param str
     * @return
     */
    public static String getImgReplaceStr(String str){
        str = str.replaceAll("\\n","").replaceAll("\\\\begin","").replaceAll("\\\\end","");
        str = str.replaceAll("\\{","").replaceAll("\\}","").replaceAll("\\\\cdot","");
        str = str.replaceAll("\\\\dot","").replaceAll("\\\\text","").replaceAll("\\\\sqrt","");
        str = str.replaceAll("^circ","").replaceAll("\\\\omega","").replaceAll("\\\\Delta","");
        str = str.replaceAll("\\\\frac","").replaceAll("\\\\infty","").replaceAll("_","");
        str = str.replaceAll("\\\\overline","").replaceAll("\\\\sin","sin").replaceAll("\\\\cos","cos");
        str = str.replaceAll("\\\\tan","tan").replaceAll("\\\\circ","").replaceAll("\\\\angle","");
        str = str.replaceAll("^","");
        str = str.replaceAll("\\n","").replaceAll("\\\\stackrel","").replaceAll("\\\\equiv","");
        str = str.replaceAll("%","").replaceAll("|","").replaceAll("\\\\Lambda","");
        str = str.replaceAll("\\\\div","").replaceAll("一","").replaceAll("\\\\","").replaceAll("\\[","");
        str = str.replaceAll("\\]","").replaceAll("\\(","").replaceAll("\\)","").replaceAll("rightarrow","");

        return str;
    }

    public static String getReplaceStr(String str){
        str = str.replaceAll("\\\\documentstyle\\[12pt\\]\\{article\\} \\n \\\\begin\\{document\\} \\n \\\\begin\\{displaymath\\} \\n ", "");
        str = str.replaceAll("\\\\end\\{displaymath\\} \\n \\\\end\\{document\\}", "");
        return str;
    }

    public static String decode1(String str) {
        str = str.replaceAll("\\\\u([0-9a-zA-Z]{4})", "");
        return str;
    }

    //是公式
    public static boolean isMath(String str){
        if(!TextUtils.isEmpty(str))
            return str.matches(".*[a-zA-z$\\\\%].*");
        return false;
    }

    public static RequestBody getRequestBody(String value){
        if(TextUtils.isEmpty(value)){
            value = "";
        }
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    /**
     * 判断WIFI网络是否开启
     *
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        WifiManager wm = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (wm != null && wm.isWifiEnabled()) {
            //   Log.i(TAG, "Wifi网络已经开启");
            return true;
        }
        //  Log.i(TAG, "Wifi网络还未开启");
        return false;
    }

    /**
     * 判断移动网络是否开启
     *
     * @param context
     * @return
     */
    public static boolean isMobile(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }
    /**
     * 判断移动网络和WIFI是否开启
     */
    public static boolean isNetWorkEnabled(Context context) {
        if (isMobile(context) || isWifiEnabled(context)) {
            // Log.i(TAG, "网络已经开启"+isNetEnabled(this)+"    ,    "+isWifiEnabled(this));
            return true;
        } else {
            //     Log.i(TAG, "网络还未开启"+isNetEnabled(this)+"    ,    "+isWifiEnabled(this));
            return false;
        }
    }


}
