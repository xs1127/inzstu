package cn.edu.zstu.adapter;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.edu.zstu.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyGridViewAdapter_Info extends BaseAdapter {

	private LayoutInflater inflater;
	private String[] urls;
	private String severPath;
  	
	public  MyGridViewAdapter_Info(Context context,String[] urls,String severPath) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.urls = urls;
		this.severPath = severPath;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return urls == null ? 0 : urls.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return urls[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.gridview_item,parent, false);
			holder.imageView = (ImageView)convertView.findViewById(R.id.album_image);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		String url = (String) getItem(position);
		ImageLoader.getInstance().displayImage(severPath+url, holder.imageView);
		return convertView;
	}

	class Holder{
		ImageView imageView;
	}
}
