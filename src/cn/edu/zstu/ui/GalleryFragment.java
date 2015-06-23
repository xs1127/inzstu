package cn.edu.zstu.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.edu.zstu.R;

import com.nostra13.universalimageloader.core.ImageLoader;

public class GalleryFragment extends Fragment {

	private String url;
	private ImageView imageView;
	private String severPath;
	public GalleryFragment(String url,String severPath) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.severPath = severPath;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.galleryfragment, null);
		imageView = (ImageView)view.findViewById(R.id.galleryfragment_imageview);
		ImageLoader.getInstance().displayImage(severPath+url, imageView);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
}
