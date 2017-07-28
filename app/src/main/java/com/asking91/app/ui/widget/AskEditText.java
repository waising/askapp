package com.asking91.app.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class AskEditText extends EditText{
	private AskEditText mthis;

	public AskEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mthis = this;
		// TODO Auto-generated constructor stub
	}
	public AskEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mthis = this;
		// TODO Auto-generated constructor stub
	}
	
//	private PointF c

	public AskEditText(Context context) {
		super(context);
		mthis = this;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if(e.getAction()==MotionEvent.ACTION_DOWN){
			
			//֪ͨ���ؼ���Ҫ����
			getParent().requestDisallowInterceptTouchEvent(true);
		}else if(e.getAction()==MotionEvent.ACTION_MOVE){
			
			//֪ͨ���ؼ���Ҫ����
			getParent().requestDisallowInterceptTouchEvent(true);
		}else if(e.getAction()==MotionEvent.ACTION_UP){
			
//			getParent().requestDisallowInterceptTouchEvent(true);
		}
		return super.onTouchEvent(e);
	}

}
