package com.asking91.app.ui.widget.camera.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.asking91.app.R;
import com.asking91.app.api.Networks;
import com.asking91.app.global.Constants;
import com.asking91.app.util.FileUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.hanvon.utils.BitmapUtil.calculateInSampleSize;

/**
 * Created by jswang on 2017/2/16.
 */

@SuppressWarnings("deprecation")
public class BitmapUtil {
    public static int getMaxBitmapSize(int i, int i2) {
        int max = Math.max(i, i2);
        return Math.max(max, max < 1080 ? 1024 : 2048);
    }

    public static String getLoaclImagePath(String image) {
        File file1 = new File(Environment.getExternalStorageDirectory() + "/" + Constants.APP_CACHE_CACHE_PATH);
        if (file1.exists() && !file1.isDirectory()) {
            file1.delete();
            file1.mkdirs();
        }

        File file = new File(Environment.getExternalStorageDirectory() + "/" + Constants.APP_CACHE_IMG_PATH);
        if (!file.exists()) {
            file.mkdirs();
        } else if (!file.isDirectory()) {
            FileUtils.deleteDir(file);
            file.mkdirs();
        }
        return file.getAbsolutePath() + "/" + image;
    }

    public static File saveImage(Bitmap bitmap) {
        BufferedOutputStream bos = null;
        try {
            if (bitmap != null) {
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                        .format(new Date());
                String path = getLoaclImagePath(timeStamp + ".png");
                File file = new File(path);
                bos = new BufferedOutputStream(
                        new FileOutputStream(file.getAbsolutePath()));
                bitmap.compress(CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
                return file;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static ByteArrayOutputStream yuvImage(byte[] bArr, Camera camera) {
        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        YuvImage yuvImage = new YuvImage(bArr, 17, previewSize.width, previewSize.height, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 60, byteArrayOutputStream);
        return byteArrayOutputStream;
    }

    public static int inSampleSize(Options options, int i, int i2) {
        int b = b(options, i, i2);
        if (b > 8) {
            return ((b + 7) / 8) * 8;
        }
        int i3 = 1;
        while (i3 < b) {
            i3 <<= 1;
        }
        return i3;
    }

    private static int b(Options options, int i, int i2) {
        double d = (double) options.outWidth;
        double d2 = (double) options.outHeight;
        int ceil = i2 == -1 ? 1 : (int) Math.ceil(Math.sqrt((d * d2) / ((double) i2)));
        int min = i == -1 ? 128 : (int) Math.min(Math.floor(d / ((double) i)), Math.floor(d2 / ((double) i)));
        return min < ceil ? ceil : (i2 == -1 && i == -1) ? 1 : i != -1 ? min : ceil;
    }

    public static Bitmap getBmpFromUriSafely(Context context, Uri uri, int i, int i2) {
        Bitmap bitmap = null;
        int maxBitmapSize = getMaxBitmapSize(i, i2);
        try {
            bitmap = getBmpFromUriByLimit(context, uri, maxBitmapSize, maxBitmapSize);
        } catch (OutOfMemoryError e) {
            maxBitmapSize /= 2;
            if (i > maxBitmapSize) {
                i = maxBitmapSize;
            }
            if (i2 > maxBitmapSize) {
                i2 = maxBitmapSize;
            }
            try {
                bitmap = getBmpFromUriByLimit(context, uri, i, i2);
            } catch (FileNotFoundException e2) {
            } catch (OutOfMemoryError e3) {
            }
        } catch (Exception e4) {
        }
        return bitmap;
    }

    public static Bitmap getBmpFromUriByLimit(Context instance, Uri uri, int i, int i2) throws FileNotFoundException, OutOfMemoryError {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Config.RGB_565;
        InputStream openInputStream = instance.getContentResolver().openInputStream(uri);
        BitmapFactory.decodeStream(openInputStream, null, options);
        int ceil = i == -1 ? 1 : (int) Math.ceil((double) (((float) options.outWidth) / ((float) i)));
        int ceil2 = i2 == -1 ? 1 : (int) Math.ceil((double) (((float) options.outHeight) / ((float) i2)));
        if (ceil2 >= 1 && ceil >= 1) {
            if (ceil2 <= ceil) {
                ceil2 = ceil;
            }
            options.inSampleSize = ceil2;
        }
        options.inJustDecodeBounds = false;
        InputStream openInputStream2 = instance.getContentResolver().openInputStream(uri);
        Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream2, null, options);
        try {
            openInputStream.close();
            openInputStream2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decodeStream;
    }

    public static Bitmap getBmpFromFileSafely(Context instance, String str, int i, int i2) {
        Uri fromFile = Uri.fromFile(new File(str));
        int maxBitmapSize = getMaxBitmapSize(i, i2);
        try {
            return getBmpFromUriByLimit(instance, fromFile, maxBitmapSize, maxBitmapSize);
        } catch (OutOfMemoryError e) {
            maxBitmapSize /= 2;
            if (i > maxBitmapSize) {
                i = maxBitmapSize;
            }
            if (i2 > maxBitmapSize) {
                i2 = maxBitmapSize;
            }
            try {
                return getBmpFromUriByLimit(instance, fromFile, i, i2);
            } catch (FileNotFoundException e2) {
                return null;
            } catch (OutOfMemoryError e3) {
                return null;
            }
        } catch (Exception e4) {
            return null;
        }
    }

    public static ImageLoader imageLoader = ImageLoader.getInstance();
    public final static DisplayImageOptions itemOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.no_pic)
            .showImageForEmptyUri(R.mipmap.no_pic)
            .showImageOnFail(R.mipmap.no_pic)
            .resetViewBeforeLoading(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .considerExifParams(true)
            .cacheInMemory(true)
            .cacheOnDisk(true).build();

    public static void displayImage(String url, ImageView item_icon, boolean isNet) {
        String path = url;
        if (!isNet && !TextUtils.isEmpty(path) && !(path.contains("http") || path.contains("https"))) {
            path = ImageDownloader.Scheme.FILE.wrap(url);
        }
        imageLoader.displayImage(path, item_icon, itemOptions);
    }

    public static void displayUserImage(Context context,String url,ImageView askSimpleDraweeView) {
        Map<String, String> headers = new HashMap();
        headers.put("Authorization", Networks.getToken());
        DisplayImageOptions itemUserOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_user_img)
                .showImageForEmptyUri(R.mipmap.default_user_img)
                .showImageOnFail(R.mipmap.default_user_img)
                .extraForDownloader(headers)
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .cacheInMemory(true)
                .cacheOnDisk(true).displayer(
                        new RoundedBitmapDisplayer(context.getResources().getDimensionPixelSize(R.dimen.space_102))).build();
        imageLoader.displayImage(url,askSimpleDraweeView, itemUserOptions);
    }

    public static String getGreyPath() {
       return Environment.getExternalStorageDirectory() + "/" + Constants.APP_CACHE_IMG_GRAY_PATH;
    }

    /**
     * 保存方法
     */
    public static String saveGreyBitmap(String path, Bitmap bitmap) {
        FileOutputStream out = null;
        String greyPath = null;
        try {
            String dirPath = getGreyPath();
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            greyPath = dirPath + new File(path).getName().replace(".jpg", "_gray.jpg");
            Bitmap greyBitmap = bitmap2Gray(bitmap);
            File f = new File(greyPath);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            out = new FileOutputStream(f);
            greyBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (OutOfMemoryError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return greyPath;
    }

    public static Bitmap bitmap2Gray(Bitmap bmSrc) {
        Bitmap bmpGray = null;
        try {
            int width, height;
            height = bmSrc.getHeight();
            width = bmSrc.getWidth();
            bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            Canvas c = new Canvas(bmpGray);
            Paint paint = new Paint();
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
            paint.setColorFilter(f);
            c.drawBitmap(bmSrc, 0, 0, paint);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmpGray;
    }

    public static int getInfoOrientation(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static void createImageThumbnail(Context context,
                                           String largeImagePath, String thumbfilePath, int square_size,
                                           int quality) throws IOException {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        try{
            BitmapFactory.decodeFile(largeImagePath, opts);
            // 原始图片bitmap
            //Bitmap cur_bitmap = getBitmapByPath(largeImagePath, opts);
            opts.inSampleSize = calculateInSampleSize(opts, square_size, square_size);
        } catch (Exception e) {
            opts.inSampleSize = 10;
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            opts.inSampleSize = 10;
            e.printStackTrace();
        }
        // Decode bitmap with inSampleSize set
        opts.inJustDecodeBounds = false;

        Bitmap cur_bitmap = getBitmapByPath(largeImagePath, opts);

        if (cur_bitmap == null)
            return;

        // 原始图片的高宽
        int[] cur_img_size = new int[] { cur_bitmap.getWidth(),
                cur_bitmap.getHeight() };
        // 计算原始图片缩放后的宽高
        int[] new_img_size = scaleImageSize(cur_img_size, square_size);
        // 生成缩放后的bitmap
        Bitmap thb_bitmap = zoomBitmap(cur_bitmap, new_img_size[0],
                new_img_size[1],largeImagePath);
        // 生成缩放后的图片文件
        saveImageToSD(null, thumbfilePath, thb_bitmap, quality);
    }

    /**
     * 让Gallery上能马上看到该图片
     */
    private static void scanPhoto(Context ctx, String imgFileName) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imgFileName);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }

    /**
     * 写图片文件到SD卡
     *
     * @throws IOException
     */
    public static void saveImageToSD(Context ctx, String filePath,
                                     Bitmap bitmap, int quality) throws IOException {
        if (bitmap != null) {
            File file = new File(filePath.substring(0,
                    filePath.lastIndexOf(File.separator)));
            if (!file.exists()) {
                file.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(filePath));
            bitmap.compress(CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            if (ctx != null) {
                scanPhoto(ctx, filePath);
            }
        }
    }

    /**
     * 获取bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath) {
        return getBitmapByPath(filePath, null);
    }

    public static Bitmap getBitmapByPath(String filePath,
                                         BitmapFactory.Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 计算缩放图片的宽高
     *
     * @param img_size
     * @param square_size
     * @return
     */
    public static int[] scaleImageSize(int[] img_size, int square_size) {
        if (img_size[0] <= square_size && img_size[1] <= square_size)
            return img_size;
        double ratio = square_size
                / (double) Math.max(img_size[0], img_size[1]);
        return new int[] { (int) (img_size[0] * ratio),
                (int) (img_size[1] * ratio) };
    }

    /**
     * 放大缩小图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h,String largeImagePath) {
        Bitmap newbmp = null;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidht = ((float) w / width);
            float scaleHeight = ((float) h / height);
            matrix.postScale(scaleWidht, scaleHeight);
            newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                    true);
            newbmp = getRotatedBitmap(newbmp,largeImagePath);
        }
        return newbmp;
    }


    public static Bitmap getRotatedBitmap(Bitmap bitmap, String strImagePath) {
        int degree = getExifDegree(strImagePath);
        if ((degree == 90 || degree == 270)) {
            return getRotatedBitmap(bitmap, degree);
        }
        return bitmap;
    }

    public static int getExifDegree(String filepath) {
        int degree = 0;
        ExifInterface exif;
        try {
            exif = new ExifInterface(filepath);
            if (exif != null) {
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
                if (orientation != -1) {
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            degree = 90;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            degree = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            degree = 270;
                            break;
                    }
                }
            }
        } catch (Exception e) {
            return 0;
        }
        return degree;
    }

    public static Bitmap getRotatedBitmap(Bitmap bitmap, int degrees) {
        try {
            if (degrees != 0 && bitmap != null) {
                Matrix m = new Matrix();
                m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
                Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != b2) {
                    bitmap.recycle();
                    bitmap = b2;
                }
            }
        } catch (Exception e) {
        }catch (OutOfMemoryError ex) {
        }
        return bitmap;
    }
}
