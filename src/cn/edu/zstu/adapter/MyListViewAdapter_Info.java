package cn.edu.zstu.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.zstu.R;
import cn.edu.zstu.customview.NoScrollGridView;
import cn.edu.zstu.tools.Beans;
import cn.edu.zstu.tools.MyAppliction;
import cn.edu.zstu.ui.ImageBrowse;
import cn.edu.zstu.ui.Profile;

public class MyListViewAdapter_Info extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<Beans> list;
	private Context context;
	private String severPath;
	private MyAppliction appliction;

	public MyListViewAdapter_Info(Context context, ArrayList<Beans> list, String severPath) {
		// TODO Auto-generated constructor stub
		this.list = list;
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.severPath = severPath;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
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
			convertView = inflater.inflate(R.layout.listview_info, null);
			holder.user_pho = (ImageView) convertView.findViewById(R.id.user_pho);
			holder.user_name = (TextView) convertView.findViewById(R.id.user_name);
			holder.user_message = (TextView) convertView.findViewById(R.id.user_message);
			holder.user_post = (NoScrollGridView) convertView.findViewById(R.id.user_post);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final Beans beans = (Beans) getItem(position);

		ImageLoader.getInstance().displayImage(severPath + beans.user_pho, holder.user_pho);
		holder.user_name.setText(beans.user_name);
		holder.user_name.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(context, "321", Toast.LENGTH_LONG).show();
				Toast.makeText(context, "userid:" + beans.user_id + ",postid:" + beans.post_id,
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context, Profile.class);
				intent.putExtra("userid", beans.user_id);
				intent.putExtra("postid", beans.post_id);
				context.startActivity(intent);
			}
		});
		holder.user_message.setText(beans.user_message);
		if (beans.user_post != null && beans.user_post.length > 0) {
			holder.user_post.setVisibility(View.VISIBLE);
			holder.user_post.setAdapter(new MyGridViewAdapter_Info(context, beans.user_post,
					severPath));
			holder.user_post.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					imageBrowser(position, beans.user_post);
				}
			});
		} else {
			holder.user_post.setVisibility(View.GONE);
		}
		return convertView;
	}

	private void imageBrowser(int position, String[] urls) {
		Intent intent = new Intent(context, ImageBrowse.class);
		intent.putExtra(ImageBrowse.URLS, urls);
		intent.putExtra(ImageBrowse.INDEX, position);
		// intent.setClass(context, cls);
		context.startActivity(intent);
	}

	class Holder {
		ImageView user_pho;
		TextView user_name;
		TextView user_message;
		NoScrollGridView user_post;
	}

}
