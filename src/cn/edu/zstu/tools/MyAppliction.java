package cn.edu.zstu.tools;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyAppliction extends Application {

	public static String url_path = "http://10.10.50.68:8080/ZstuServer";
	public static String serverPath = "http://10.10.50.68:8080/ZstuServer/uploadfile/";
//	public static String url_path = "http://10.10.50.68:8080/ZstuServer";
//	public static String serverPath = "http://10.10.50.68:8080/ZstuServer/uploadfile/";
	private String userId;
	private String userName;
	public static int catOrFil ;
	public SharedPreferences autoLogin;
	private int currentIndex;
	public MyMessage message;
	public ArrayList<Beans> messageList;

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public SharedPreferences getAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(SharedPreferences autoLogin) {
		this.autoLogin = autoLogin;
	}

	public String getUrl_path() {
		return url_path;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		userName = "null";
		userId = "null";	
		currentIndex = 0;
		/**
		 * catOrFil：
		 * 0：代表用户想要发布新帖
		 * 1：代表用户想要实现过滤功能
		 */
		catOrFil = 1;
		messageList = new ArrayList<Beans>();
		autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
		DisplayImageOptions defaultOptions = new DisplayImageOptions
				.Builder()
				.showImageForEmptyUri(cn.edu.zstu.R.drawable.default_pic) 
				.showImageOnFail(cn.edu.zstu.R.drawable.default_pic) 
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				.discCacheSize(50 * 1024 * 1024)//
				.discCacheFileCount(100)//缓存一百张图片
				.writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);
	}
}
