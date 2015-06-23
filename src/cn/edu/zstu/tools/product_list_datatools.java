package cn.edu.zstu.tools;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.service.product_lis_dataservice;

public class product_list_datatools implements product_lis_dataservice {

	private List<String> groupList;
	private List<List<String>> childList;
	public product_list_datatools() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getGroupData() {
		// TODO Auto-generated method stub
		groupList = new ArrayList<String>();
		groupList.add("书籍");
		groupList.add("二手车");
		groupList.add("手机/平板");
		groupList.add("电脑");
		groupList.add("生活用品");
		return groupList;
	}

	@Override
	public List<List<String>> getChildData() {
		// TODO Auto-generated method stub
		childList = new ArrayList<List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("理学院");
		list.add("服装学院");
		list.add("机械与自动控制学院");
		list.add("生命科学学院");
		list.add("艺术与设计学院");
		list.add("外国语学院");
		list.add("马克思主义学院");
		list.add("科学与艺术学院");
		list.add("材料与纺织学院");
		list.add("信息学院");
		list.add("建筑工程学院");
		list.add("经济管理学院");
		list.add("法政学院");
		list.add("文化传播学院");
		list.add("启新学院");
		list.add("继续教育学院");
		childList.add(list);
		List<String> list2 = new ArrayList<String>();
		list2.add("自行车");
		list2.add("电动车");
		childList.add(list2);
		List<String> list3 = new ArrayList<String>();
		list3.add("苹果");
		list3.add("三星");
		list3.add("其他");
		childList.add(list3);
		List<String> list4 = new ArrayList<String>();
		list4.add("苹果");
		list4.add("微软");
		list4.add("联想");
		list4.add("惠普");
		list4.add("三星");
		list4.add("其他");
		childList.add(list4);
		List<String> list5 = new ArrayList<String>();
		list5.add("插线板");
		list5.add("台灯");
		list5.add("......");
		childList.add(list5);
		return childList;
	}

}
