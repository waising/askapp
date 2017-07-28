package com.asking91.app.ui.widget.camera.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.asking91.app.R;
import com.asking91.app.ui.widget.camera.adapter.ImageGridAdapter;
import com.asking91.app.ui.widget.camera.utils.ImageService;
import com.asking91.app.ui.widget.camera.utils.ImageService.LoadImageListener;

/**
 * 图库
 */
public class ImageSelectActivity extends FragmentActivity {
    private ImageGridAdapter mGridAdapter;
    private RecyclerView mGridView;

    @Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_image_select2);
		onInit();
	}

    protected void onInit() {
        this.mGridView = (RecyclerView) findViewById(R.id.gridView);
        this.mGridAdapter = new ImageGridAdapter(this);
        GridLayoutManager mgr=new GridLayoutManager(this,4);
        mGridView.setLayoutManager(mgr);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        loadImages();
        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadImages() {
        ImageService.getInstance().load(this, new LoadImageListener() {
            public void onFinish() {
                if (ImageService.getInstance().isEmpty()) {
                    Toast.makeText(ImageSelectActivity.this, "没找到任何图片", Toast.LENGTH_SHORT).show();
                }
                mGridView.setAdapter(mGridAdapter);
            }
        });
    }

    public void onResume() {
        super.onResume();
        if (!(this.mGridView == null || this.mGridAdapter == null)) {
            this.mGridAdapter.notifyDataSetChanged();
        }
    }
}