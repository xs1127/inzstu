package cn.edu.zstu.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.zstu.R;

public class User_Profile_List {

	private List<Map<String, Object>> list;
	private int[] dra = {
			R.drawable.user_profile_mypost,R.drawable.user_profile_changeinstitute,R.drawable.user_profile_settings
	};
	private String[] from = {
		"picture","up","down"	
	};
	public User_Profile_List() {
		// TODO Auto-generated constructor stub
	}
	
	public  List<Map<String, Object>> getList(){
		list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(from[0], dra[0]);
		map.put(from[1], "我发布的帖子");
		map.put(from[2], "已发布的帖子，管理帖子");
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put(from[0], dra[1]);
		map1.put(from[1], "购买记录");
		map1.put(from[2], "查看购买记录详细信息...");
		
		Map<String , Object> map2 = new HashMap<String, Object>();
		map2.put(from[0], dra[2]);
		map2.put(from[1], "设置");
		map2.put(from[2], "完善个人信息，找回密码...");
		
		list.add(map);
		list.add(map1);
		list.add(map2);
		return list;
	}
}
