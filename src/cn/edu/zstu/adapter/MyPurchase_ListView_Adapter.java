package cn.edu.zstu.adapter;

import java.util.ArrayList;

import cn.edu.zstu.R;
import cn.edu.zstu.tools.MyPostBeans;
import cn.edu.zstu.ui.MessageShow;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyPurchase_ListView_Adapter extends BaseAdapter {

	private Context context;
	private ArrayList<MyPostBeans> list;

	public MyPurchase_ListView_Adapter(Context context, ArrayList<MyPostBeans> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.mypurchase_listview_info,
					null);
			holder.username = (TextView) convertView.findViewById(R.id.mypurchase_username_r);
			holder.phone = (TextView) convertView.findViewById(R.id.mypurchase_phone_r);
			holder.date = (TextView) convertView.findViewById(R.id.mypurchase_date_r);
			holder.message = (TextView) convertView.findViewById(R.id.mypurchase_message_r);
			holder.pics = (TextView) convertView.findViewById(R.id.mypurchase_pics_r);
			holder.category = (TextView) convertView.findViewById(R.id.mypurchase_category_r);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final MyPostBeans beans = (MyPostBeans) getItem(position);
		holder.username.setText(beans.username);
		holder.phone.setText(beans.phone);
		holder.date.setText(beans.date);
		holder.message.setText(beans.message);
		holder.pics.setTextColor(Color.BLUE);
		holder.pics.setText("点击查看详细图片");
		holder.category.setText(beans.category);
		holder.message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(context, beans.message,
				// Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context, MessageShow.class);
				intent.putExtra("message", beans.message);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	class Holder {
		TextView username;
		TextView phone;
		TextView date;
		TextView category;
		TextView message;
		TextView pics;
	}
}
