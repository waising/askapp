package com.asking91.app.ui.widget.camera.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.asking91.app.R;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;

/**
 * 拍照界面
 */
public class CameraActivity extends FragmentActivity {
	private int evenType;
    /**
     * 0:题目 1：头像
     */
    private int flag;

	private CameraMultiFragment cameraMultiFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_main);
		evenType = this.getIntent().getIntExtra("evenType",0);
        flag = this.getIntent().getIntExtra("flag",0);
		FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
		cameraMultiFragment = CameraMultiFragment.newInstance(evenType,flag);

		tr.add(R.id.main, cameraMultiFragment).commitAllowingStateLoss();
	}

	/**
	 * 去界面
	 * @param fragment
	 */
	public void goToPage(Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction trx = fragmentManager.beginTransaction();
		trx.setTransition(FragmentTransaction.TRANSIT_NONE);
		Fragment fragmentById = fragmentManager.findFragmentById(R.id.main);
		if (fragmentById != null) {
			trx.remove(fragmentById);
		}
		trx.add(R.id.main, fragment).commitAllowingStateLoss();
	}


	/**
	 * 从图库选择图片后
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1001 && data != null) {//从图库选择图片后
			String imgPath = data.getStringExtra("imgPath");
			int orient = BitmapUtil.getInfoOrientation(imgPath);
			Bundle bundle = new Bundle();
			bundle.putString("pic_url", imgPath);
			bundle.putInt("pic_orient", 2);
			bundle.putInt("pic_info_orient", orient);
			bundle.putInt("pic_index", 1);
			bundle.putInt("pic_res", 1);
			bundle.putInt("evenType", evenType);
			bundle.putInt("flag", flag);
			Fragment f = CameraPublishFragment.newInstance(bundle);
			goToPage(f);
		}
	}

	//flag : 0 扫描题目  1：头像，跳转到拍照界面
	public static void openCameraActivity(Activity activity, int evenType,int flag) {
		Intent intent=new Intent(activity,CameraActivity.class);
		intent.putExtra("evenType",evenType);
		intent.putExtra("flag",flag);
		activity.startActivity(intent);
		activity.overridePendingTransition(0,0);
	}

	@Override
	protected void onPause() {
		overridePendingTransition(0,0);
		super.onPause();
	}

}
