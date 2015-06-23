package cn.edu.zstu.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.zstu.R;
import cn.edu.zstu.DB.ConnectionService;
import cn.edu.zstu.Listeners.GridViewListener;
import cn.edu.zstu.dialogbuilder.DIYDialog.callbackListener;
import cn.edu.zstu.tools.JsonTools;
import cn.edu.zstu.tools.MyAppliction;
import cn.edu.zstu.ui.PostItem;
import cn.edu.zstu.ui.Product_List_Fragment;
import cn.edu.zstu.ui.User_Profile_Fragment;
import android.R.integer;
import android.R.interpolator;
import android.R.layout;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<String> groupList;
	private List<List<String>> childList;
	private String requestUri;
	private gridViewCallBack callBack;
	private Handler handler;

	class Holder {
		TextView textView;
		GridView gridView;
		ImageView imageView;
	}

	private Holder holder;

	public void setListener(gridViewCallBack callBack) {
		this.callBack = callBack;
	}

	public MyExpandableListAdapter(Context context, List<String> groupList,
			List<List<String>> childList, String requestUri, Handler filterHandler) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.groupList = groupList;
		this.childList = childList;
		this.requestUri = requestUri;
		this.handler = filterHandler;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 为什么返回的childrencount为1才不会出现重复数据？？？
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		// return childList.get(groupPosition).size();
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			holder = new Holder();
			// convertView = LayoutInflater.from(context).inflate(, null);
			convertView = LayoutInflater.from(context).inflate(R.layout.product_list_groupview,
					null);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.textView = (TextView) convertView.findViewById(R.id.product_list_groupview_textview);
		holder.imageView = (ImageView) convertView
				.findViewById(R.id.product_list_groupview_imageview);
		holder.textView.setText(groupList.get(groupPosition));
		if (isExpanded) {
			holder.imageView.setImageResource(R.drawable.groupview_expandable_true);
		} else {
			holder.imageView.setImageResource(R.drawable.groupview_expandable_false);
		}
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(R.layout.product_list_childview,
					null);
			holder.gridView = (GridView) convertView
					.findViewById(R.id.product_list_childview_gridview);
			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}

		SimpleAdapter adapter = new SimpleAdapter(context, getData(childList.get(groupPosition)),
				R.layout.product_list_childview_item, new String[] { "item" },
				new int[] { R.id.product_list_childview_textview });
		holder.gridView.setAdapter(adapter);
		holder.gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		setGridViewListener(holder.gridView, groupPosition);
		return convertView;
	}

	/**
	 * 为子项目注册单击事件 实现filter功能 信息分类功能
	 * 
	 * @param gridView
	 * @param groupPosition
	 */
	private void setGridViewListener(GridView gridView, final int groupPosition) {
		// TODO Auto-generated method stub
		gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				TextView textView = null;
				StringBuffer category = new StringBuffer();
				category.append(groupPosition);
				if (position < 10) {
					category.append('0');
				}
				category.append(position);
				if (view instanceof TextView && MyAppliction.catOrFil == 1) {
					textView = (TextView) view;
					final String requestString = String.valueOf(JsonTools.getJsonObject("filter",
							category.toString()));
					/**
					 * 不能在主线程访问网络数据 避免错误
					 */
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							ConnectionService connection = new ConnectionService(requestString,
									requestUri);
							String responseString = connection.connect();
							// System.out.println("filter"+responseString);
							Message message = handler.obtainMessage();
							message.obj = responseString;
							handler.sendMessage(message);

							// Message message = Message.obtain();
							// Message message = new Message();
							// message.obj = responseString;
							// filterHandler.sendMessage(message);

							// System.out.println("filterresult:" +
							// responseString);
							// 回调函数的使用 好好理解 还是跳不出子线程的!!!
							// callBack.callbackListener(responseString);
						}
					}).start();

				} else if (view instanceof TextView && MyAppliction.catOrFil == 0) {
					if (category.toString().length() > 0) {
						Intent intent = new Intent(context, PostItem.class);
						intent.putExtra("Category", category.toString());
						context.startActivity(intent);
						// startActivityForResult(intent, 100);
					}
				}
			}
		});
	}

	public List<HashMap<String, Object>> getData(List<String> listString) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < listString.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("item", listString.get(i));
			list.add(map);
		}
		return list;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * gridview的回调接口
	 */
	public interface gridViewCallBack {
		public abstract void callbackListener(String responseString);
	}

}
