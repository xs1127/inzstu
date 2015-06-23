package cn.edu.zstu.ui;

import java.util.List;

import com.google.gson.Gson;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import cn.edu.zstu.MainActivity;
import cn.edu.zstu.R;
import cn.edu.zstu.adapter.MyExpandableListAdapter;
import cn.edu.zstu.adapter.MyExpandableListAdapter.gridViewCallBack;
import cn.edu.zstu.tools.MyAppliction;
import cn.edu.zstu.tools.MyMessage;
import cn.edu.zstu.tools.product_list_datatools;

/**
 * @author sjtu
 *
 */
public class Product_List_Fragment extends Fragment implements gridViewCallBack {

	private View view;
	private List<String> groupList;
	private List<List<String>> childList;
	private ExpandableListView expandableListView;
	private MyExpandableListAdapter myExpandableListAdapter;
	private MyAppliction appliction;
	private String requestUri;
	public Handler filterHandler = new Handler() {
		public void handleMessage(Message msg) {
			// Toast.makeText(getActivity(), "123", Toast.LENGTH_LONG).show();
			Gson gson = new Gson();
			MyMessage message = gson.fromJson((String) msg.obj, MyMessage.class);
			if (message.responseCode == 200 && message.hasMore) {
				System.out.println(appliction.messageList.size());
				for (int i = 0; i < message.list.size(); i++) {
					appliction.messageList.add(i, message.list.get(i));
				}
				System.out.println(appliction.messageList.size());
			} else {
				Toast.makeText(getActivity(), "没有更多了", Toast.LENGTH_LONG).show();
			}
			MainActivity.main_viewpager.setCurrentItem(2);
		};
	};

	public Product_List_Fragment() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		appliction = (MyAppliction) getActivity().getApplication();
		requestUri = appliction.url_path + "/servlet/GetdataAction";
		view = inflater.inflate(R.layout.product_list, container, false);
		expandableListView = (ExpandableListView) view
				.findViewById(R.id.product_list_expandableListview);
		groupList = new product_list_datatools().getGroupData();
		childList = new product_list_datatools().getChildData();

		myExpandableListAdapter = new MyExpandableListAdapter(getActivity(), groupList, childList,
				requestUri, filterHandler);
		myExpandableListAdapter
				.setListener((MyExpandableListAdapter.gridViewCallBack) Product_List_Fragment.this);
		expandableListView.setAdapter(myExpandableListAdapter);
		expandableListView.expandGroup(0, true);
		expandableListView.expandGroup(1, true);
		return view;
	}

	/**
	 * 加载ExpandableListView布局 完成初始化
	 * 
	 */
	private void init() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc) 值得思考
	 * 
	 * @see
	 * cn.edu.zstu.adapter.MyExpandableListAdapter.gridViewCallBack#callbackListener
	 * (java.lang.String)
	 */
	@Override
	public void callbackListener(String responseString) {
		// TODO Auto-generated method stub
		// System.out.println("响应成功:" + responseString);
		// 由一个子线程调用 不是ui线程 所以不能调用toast函数 也不能使用mainactivity
		// Toast.makeText(getActivity(), "响应成功", Toast.LENGTH_LONG).show();
		// Looper.prepare();
		// MainActivity.main_viewpager.setCurrentItem(2);
	}

	/**
	 * 
	 * 好好学习下内部类
	 * 
	 * @author sjtu
	 *
	 */
	// class FilterHandler extends Handler {
	// @Override
	// public void handleMessage(Message msg) {
	// // TODO Auto-generated method stub
	// Toast.makeText(getActivity(), "finally", Toast.LENGTH_LONG).show();
	// MainActivity.main_viewpager.setCurrentItem(2);
	// }
	// }
}
