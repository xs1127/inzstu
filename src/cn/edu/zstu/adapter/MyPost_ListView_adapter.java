package cn.edu.zstu.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.zstu.R;
import cn.edu.zstu.tools.MyPostBeans;
import cn.edu.zstu.ui.MessageShow;

public class MyPost_ListView_adapter extends BaseAdapter {

	private String userId;
	private ArrayList<MyPostBeans> list;
	private LayoutInflater inflater;
	private Context context;

	public MyPost_ListView_adapter(Context context, String userId, ArrayList<MyPostBeans> list) {
		// TODO Auto-generated constructor stub
		this.userId = userId;
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.mypost_listview_info, null);
			holder.date = (TextView) convertView.findViewById(R.id.mypost_date_r);
			holder.message = (TextView) convertView.findViewById(R.id.mypost_message_r);
			holder.pics = (TextView) convertView.findViewById(R.id.mypost_pics_r);
			holder.state = (TextView) convertView.findViewById(R.id.mypost_state_r);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final MyPostBeans beans = (MyPostBeans) getItem(position);
		holder.date.setText(beans.date);
		holder.message.setText(beans.message);
		holder.pics.setTextColor(Color.BLUE);
		holder.pics.setText("点击查看详细图片");
		if (beans.state == 0) {
			holder.state.setText("未售卖");
		} else {
			holder.state.setText("已售卖");
		}
		holder.pics.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "客官稍等...", Toast.LENGTH_LONG).show();
			}
		});
		holder.message.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(context, beans.message, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context, MessageShow.class);
				intent.putExtra("message", beans.message);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	class Holder {
		TextView date;
		TextView message;
		TextView pics;
		TextView state;
	}
}
