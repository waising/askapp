package com.asking91.app.ui.juniorhigh;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.greenrobot.event.EventBus;

/**
 * 初升高下载paf的service
 * Created by haibin
 * on 2016/10/19.
 */
@SuppressWarnings("all")
public class JuniorToHighPdfService extends Service {


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://下载完成
                    EventBus.getDefault().post(new JuniorToHighPdfEvent(2));
//                    if (downLoadSuccess != null) {
//                        downLoadSuccess.success();
//                    }
                    break;
                case 1://下载中
                    int rate = msg.arg1;//下载进度
                    if (rate < 100) {//显示下载进度
                        EventBus.getDefault().post(new JuniorToHighPdfEvent(1, rate, rate + "%"));
                    } else {//下载完毕
                        EventBus.getDefault().post(new JuniorToHighPdfEvent(2));
                   //     if (downLoadSuccess != null) {
//                            downLoadSuccess.success();
//                        }
                        }
                        break;
                    }

            }
        }

        ;

        /**
         * 开启服务
         *
         * @param context
         * @param downloadUrl
         * @param isForcedupdate
         */
        public static void startService(Context context, String downloadUrl, String savePath) {
            Intent intent = new Intent(context, JuniorToHighPdfService.class);
            intent.putExtra("url", downloadUrl);
            intent.putExtra("savePath", savePath);
            context.startService(intent);
        }

        @Override
        public void onCreate() {
            super.onCreate();
        }

        /**
         * 开启服务
         *
         * @param intent
         * @param flags
         * @param startId
         * @return
         */
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            final String url = intent.getStringExtra("url");
            String savePath = intent.getStringExtra("savePath");
            File file = new File(JuniorToHighDownLoadManager.getSavePath());
            if (!file.exists()) {//文件夹不存在，则创建文件夹Ask/Download文件夹
                file.mkdirs();
            }
            final File pdfFile = new File(savePath);//要保存的文件路径
            new Thread() {
                @Override
                public void run() {
                    try {
                        downloadPdfFile(url, pdfFile);//开始下载文件
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            return super.onStartCommand(intent, flags, startId);
        }

        /**
         * 下载更新文件,并写入到指定文件夹中
         *
         * @param downloadUrl
         * @param saveFile
         * @return
         * @throws Exception
         */
        private long downloadPdfFile(String downloadUrl, File saveFile) throws Exception {
            int downloadCount = 0;
            int currentSize = 0;
            long totalSize = 0;
            int updateTotalSize = 0;
            HttpURLConnection httpConnection = null;
            InputStream is = null;//文件读取流
            FileOutputStream fos = null;
            try {
                URL url = new URL(downloadUrl);
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setConnectTimeout(10000);
                httpConnection.setReadTimeout(20000);
                updateTotalSize = httpConnection.getContentLength();
                if (httpConnection.getResponseCode() == 404) {
                    throw new Exception("fail!");
                }
                is = httpConnection.getInputStream();//文件读取流
                fos = new FileOutputStream(saveFile, false);//写入到固定文件夹
                DESHelper.encrypt(is, fos);
//            byte buffer[] = new byte[2048];//文件的数组
//            int readSize = 0;//每次读取的字节数
//            while ((readSize = is.read(buffer)) > 0) {//未读完
//                fos.write(buffer, 0, readSize);//写入文件
//                totalSize += readSize;
//                if ((downloadCount == 0)
//                        || (int) (totalSize * 100 / updateTotalSize) - 4 > downloadCount) {//下载进度显示
//                    downloadCount += 4;
//                    Message msg = mHandler.obtainMessage();
//                    msg.what = 1;
//                    msg.arg1 = downloadCount;//下载进度
//                    mHandler.sendMessage(msg);
//                }
//            }
                mHandler.sendEmptyMessage(0);//下载完成

                //   isDownload = false;

            } finally {
                if (httpConnection != null) {
                    httpConnection.disconnect();
                }
                is.close();
                fos.close();
                stopSelf();
            }
            return totalSize;
        }


        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }


        private DownLoadSuccess downLoadSuccess;

        public void setDownLoadSuccess(DownLoadSuccess downLoadSuccess) {
            this.downLoadSuccess = downLoadSuccess;
        }

        interface DownLoadSuccess {
            void success();
        }


    }
