package cn.edu.zstu.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import cn.edu.zstu.R;
import cn.edu.zstu.DB.ConnectionService;
import cn.edu.zstu.adapter.MyPurchase_ListView_Adapter;
import cn.edu.zstu.tools.JsonTools;
import cn.edu.zstu.tools.MyAppliction;
import cn.edu.zstu.tools.MyPostBeans;
import cn.edu.zstu.tools.MyPostBeansMessage;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyPurchase extends Activity {

	private ListView listview;
	private ImageView image;
	private String uri, userid;
	private MyAppliction application;
	private ProgressDialog progressDialog;
	private ArrayList<MyPostBeans> list;
	private MyPurchase_ListView_Adapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypurchase);
		application = (MyAppliction) getApplication();
		uri = MyAppliction.serverPath + application.getUserId() + "/pho.jpg";
		listview = (ListView) this.findViewById(R.id.mypurchase_listview);
		View view = LayoutInflater.from(this).inflate(R.layout.mypost_listview_header, null);
		image = (ImageView) view.findViewById(R.id.mypost_listview_header_image);
		ImageLoader.getInstance().displayImage(uri, image);
		listview.addHeaderView(view);
		userid = getIntent().getExtras().getString("userid");
		new LoadData().execute();
	}

	class LoadData extends AsyncTask<Void, Void, MyPostBeansMessage> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(MyPurchase.this);
			progressDialog.setMessage("玩命为你加载...");
			progressDialog.show();
		}

		@Override
		protected MyPostBeansMessage doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String requestString = String.valueOf(JsonTools.getJsonObject("purchase", userid));
			String requestUri = MyAppliction.url_path + "/servlet/GetdataAction";
			ConnectionService service = new ConnectionService(requestString, requestUri);
			String responseString = service.connect();
			Gson gson = new Gson();
			MyPostBeansMessage message = gson.fromJson(responseString, MyPostBeansMessage.class);
			return message;
		}

		@Override
		protected void onPostExecute(MyPostBeansMessage result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();
			if(result.responseCode == 200 && result.list!=null){
				list = result.list;
				adapter = new MyPurchase_ListView_Adapter(MyPurchase.this, list);
				listview.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		}

	}
	
	public void changecover(View v) {
		
	}
}
