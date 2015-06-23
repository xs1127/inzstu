package cn.edu.zstu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author sjtu
 * 自定义pageradapter适配器 完成页面的装载
 * 必须重载以下方法   instantiateItem destroyItem getCount isViewFromObject 
 *
 */
public class MyPagerAdapter extends PagerAdapter {

	private List<View> listview = new ArrayList<View>();
	private List<String> listtitle = new ArrayList<String>();
	public MyPagerAdapter(List<View> listview,List<String> listtitle) {
		// TODO Auto-generated constructor stub
		this.listview = listview;
		this.listtitle = listtitle;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		((ViewPager)container).addView(listview.get(position));
		return listview.get(position);
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return listtitle.get(position);
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		//super.destroyItem(container, position, object);
		((ViewPager)container).removeView(listview.get(position));
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listview.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

}
