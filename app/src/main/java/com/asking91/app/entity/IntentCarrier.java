package com.asking91.app.entity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 登录器载体
 * 
 * @author bzl
 * 
 */
public class IntentCarrier implements Parcelable {
	public String getmTargetAction() {
		return mTargetAction;
	}

	private String mTargetAction;

	public Bundle getMbundle() {
		return mbundle;
	}

	private Bundle mbundle;

	public IntentCarrier(String target, Bundle bundle) {
		mTargetAction = target;
		mbundle = bundle;
	}

	public IntentCarrier(Parcel parcel) {
		// 按变量定义的顺序读取
		mTargetAction = parcel.readString();
		mbundle = parcel.readParcelable(Bundle.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		// 按变量定义的顺序写入
		parcel.writeString(mTargetAction);
		parcel.writeParcelable(mbundle, flags);
	}

	public static final Parcelable.Creator<IntentCarrier> CREATOR = new Parcelable.Creator<IntentCarrier>() {

		@Override
		public IntentCarrier createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new IntentCarrier(source);
		}

		@Override
		public IntentCarrier[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return new IntentCarrier[arg0];
		}
	};

}
