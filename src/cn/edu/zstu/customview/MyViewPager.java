package cn.edu.zstu.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

	public MyViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyViewPager(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	@Override
	public boolean onInterceptHoverEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onInterceptHoverEvent(event);
	}

}
