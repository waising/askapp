package com.asking91.app.util;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

import static com.qiniu.android.common.FixedZone.zone1;

/**
 * Created by wxwang on 2017/6/5.
 */

public class QiniuUtil {

    public static UploadManager getUploadManager() {
        if(uploadManager==null)
            initQiNiuUpload();
        return uploadManager;
    }

    private static UploadManager uploadManager;

    public static void initQiNiuUpload() {
        if (uploadManager == null) {
            Configuration config = new Configuration.Builder()
                    .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                    .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                    .connectTimeout(10) // 链接超时。默认10秒
                    .responseTimeout(60) // 服务器响应超时。默认60秒
//                .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
//                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                    .zone(FixedZone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                    .build();
            //————http上传,指定zone的具体区域——
            //Zone.zone0:华东
            //Zone.zone1:华北
            //Zone.zone2:华南
            //Zone.zoneNa0:北美
            // 重用uploadManager。一般地，只需要创建一个uploadManager对象
            uploadManager = new UploadManager(config);
        }
    }
}
