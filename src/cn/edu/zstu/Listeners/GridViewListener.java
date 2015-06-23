package cn.edu.zstu.Listeners;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class GridViewListener implements OnItemClickListener {

	private Context context;
	private GridView gridView;
	private SimpleAdapter adapter;
	public GridViewListener(GridView gridView,Context context) {
		// TODO Auto-generated constructor stub
		this.gridView = gridView;
		this.context = context;
		adapter = (SimpleAdapter)gridView.getAdapter();
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		TextView text = null;
		if(view instanceof TextView){
			text = (TextView)view;
		}
		Toast.makeText(context, parent.getCount()+":|:"+text.getText(), Toast.LENGTH_LONG).show();

	}

}
