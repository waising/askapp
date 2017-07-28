package com.asking91.app.ui.widget.camera.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.support.v4.util.LongSparseArray;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageService {
	public static final String Id = "_id";
    private static final ImageService mInstance = new ImageService();
    private final List<String> mAll = new ArrayList<String>();
    private int mCurrentDir = 0;
    private final List<String> mDirs = new ArrayList<String>();
    private Handler mHandler = new Handler();
    private final LongSparseArray<String> mImageIds = new LongSparseArray<String>();
    private final Map<String, String> mImageThumbnails = new HashMap<String, String>();
    private final Map<String, List<String>> mImages = new HashMap<String, List<String>>();
    private List<String> mPreviewImages;
    private boolean mPreviewMode = false;

    public interface LoadImageListener {
        void onFinish();
    }

    public static ImageService getInstance() {
        return mInstance;
    }

    private ImageService() {
    }

    public void load(final Context context, final LoadImageListener listener) {
        new Thread(new Runnable() {
            public void run() {
                doLoad(context);
                mHandler.post(new Runnable() {
                    public void run() {
                        listener.onFinish();
                    }
                });
            }
        }).start();
    }

    private void doLoad(Context context) {
        clear();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Media.EXTERNAL_CONTENT_URI, new String[]{"_data",  Id}, null, null, "date_added DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndex("_data"));
                if (!(path == null || path.isEmpty())) {
                    this.mImageIds.put(cursor.getLong(cursor.getColumnIndex(Id)), path);
                    addImage(path);
                }
            }
            cursor.close();
        }
        Cursor cursorThumb = contentResolver.query(Thumbnails.EXTERNAL_CONTENT_URI, new String[]{"_data", "image_id"}, null, null, null);
        if (cursorThumb != null) {
            while (cursorThumb.moveToNext()) {
                String thumbPath = cursorThumb.getString(cursorThumb.getColumnIndex("_data"));
                long imageID = cursorThumb.getLong(cursorThumb.getColumnIndex("image_id"));
                if (this.mImageIds.indexOfKey(imageID) >= 0) {
                    this.mImageThumbnails.put((String) this.mImageIds.get(imageID), thumbPath);
                }
            }
            cursorThumb.close();
        }
    }

    public String getImageThumbnail(String imagePath) {
        if (this.mImageThumbnails.containsKey(imagePath)) {
            return (String) this.mImageThumbnails.get(imagePath);
        }
        return "";
    }

    private void addImage(String imagePath) {
        File parentFile = new File(imagePath).getParentFile();
        if (parentFile != null) {
            String parentPath = parentFile.getAbsolutePath();
            if (!this.mImages.containsKey(parentPath)) {
                this.mImages.put(parentPath, new ArrayList<String>());
                this.mDirs.add(parentPath);
            }
            ((List<String>) this.mImages.get(parentPath)).add(imagePath);
            this.mAll.add(imagePath);
        }
    }

    public List<String> getDirImages(int position) {
        if (position == 0) {
            return this.mAll;
        }
        return (List<String>) this.mImages.get(this.mDirs.get(position));
    }

    public void setCurrentDir(int position) {
        this.mCurrentDir = position;
    }

    public int getCurrentDir() {
        return this.mCurrentDir;
    }

    public List<String> getCurrentDirImages() {
        if (this.mPreviewMode) {
            return this.mPreviewImages;
        }
        return getDirImages(this.mCurrentDir);
    }

    public int getDirCount() {
        return this.mDirs.size();
    }

    public boolean isEmpty() {
        return this.mImages.isEmpty();
    }

    private void clear() {
        this.mImages.clear();
        this.mAll.clear();
        this.mDirs.clear();
        this.mCurrentDir = 0;
        this.mDirs.add("ALL");
    }
}