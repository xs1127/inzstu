package cn.edu.zstu;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import cn.edu.zstu.adapter.MyFragmentPagerAdapter;
import cn.edu.zstu.tools.MyAppliction;
import cn.edu.zstu.ui.Campus_Fragment;
import cn.edu.zstu.ui.Product_Info_Fragment;
import cn.edu.zstu.ui.Product_List_Fragment;
import cn.edu.zstu.ui.User_Profile_Fragment;

/**
 * 加载滑动页面有3页
 * 
 * @author sjtu
 *
 */
public class MainActivity extends FragmentActivity {

	private boolean exit = false;
	public static ViewPager main_viewpager;
	private PagerTitleStrip main_title;
	private MyAppliction appliction;
	// private List<View> listview;
	// private List<String> listtitle;
	// private MyPagerAdapter main_viewpager_adapter;
	private List<Fragment> listfragment;
	private List<String> listtitle;
	private MyFragmentPagerAdapter main_viewpager_adapter;
	private FragmentManager manager;

	/*
	 * (non-Javadoc) main_title颜色的格式要求是0xAARRGGBB 其中AA代表的是透明度
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		appliction = (MyAppliction) getApplication();
		if (appliction.autoLogin.getString("userId", null) != null
				&& appliction.autoLogin.getString("userName", null) != null) {
			Toast.makeText(MainActivity.this, appliction.autoLogin.getString("userName", "null"),
					Toast.LENGTH_LONG).show();
			appliction.setUserId(appliction.autoLogin.getString("userId", "null"));
			appliction.setUserName(appliction.autoLogin.getString("userName", "null"));
		}
		main_viewpager = (ViewPager) this.findViewById(R.id.main_viewpager);
		main_title = (PagerTitleStrip) this.findViewById(R.id.main_pagertitle);
		main_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38);
		main_title.setTextColor(0xEE6495ED);
		init();
		manager = getSupportFragmentManager();
		main_viewpager_adapter = new MyFragmentPagerAdapter(manager, listfragment, listtitle);
		main_viewpager.setAdapter(main_viewpager_adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 完成对listview listtitle内容的填充 3个fragment分别为 user_profile_fragment
	 * product_list_fragment campus_Fragment user_profile_fragment:主要用用于常规显示
	 * product_list_fragment：实现filter功能 campus_Fragment：展示发布的内容
	 * 
	 */
	public void init() {
		listfragment = new ArrayList<Fragment>();
		listtitle = new ArrayList<String>();
		Fragment user_profile_fragment = new User_Profile_Fragment();
		Fragment product_list_fragment = new Product_List_Fragment();
		// Fragment product_info_Fragment = new product_info_fragment();
		Fragment product_info_Fragment = new Product_Info_Fragment();
		Fragment campus_Fragment = new Campus_Fragment();
		listfragment.add(user_profile_fragment);
		listfragment.add(product_list_fragment);
		listfragment.add(product_info_Fragment);
		// listfragment.add(campus_Fragment);
		listtitle.add("个人中心");
		listtitle.add("分类信息");
		listtitle.add("微校园");
	}

	/*
	 * (non-Javadoc) 完成连续双击后退键 完成退出功能 防治用户无意碰到后退键
	 * 
	 * @see android.support.v4.app.FragmentActivity#onKeyDown(int,
	 * android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Timer task = null;
			if (exit == false) {
				exit = true;
				Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				task = new Timer();
				task.schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						exit = false;
					}
				}, 1500);
			} else {
				finish();
				System.exit(0);
			}
		}
		return false;
	}
}
