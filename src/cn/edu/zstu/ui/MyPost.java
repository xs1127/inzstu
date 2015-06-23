package cn.edu.zstu.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Telephony.Sms.Conversations;
import android.text.StaticLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import cn.edu.zstu.R;
import cn.edu.zstu.DB.ConnectionService;
import cn.edu.zstu.adapter.MyPost_ListView_adapter;
import cn.edu.zstu.dialogbuilder.DIYDialog;
import cn.edu.zstu.tools.DateTools;
import cn.edu.zstu.tools.JsonTools;
import cn.edu.zstu.tools.MyAppliction;
import cn.edu.zstu.tools.MyPostBeans;
import cn.edu.zstu.tools.MyPostBeansMessage;
import cn.edu.zstu.tools.Upload;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyPost extends Activity implements OnItemLongClickListener, OnClickListener {

	private ListView listview;
	private MyPost_ListView_adapter adapter;
	private String userId;
	private String requestString, picpath;
	public ArrayList<MyPostBeans> list;
	public MyPostBeans beans;
	private int postid;
	private View view;
	private ImageView image;
	private String uri;
	private MyAppliction application;
	private DIYDialog change;
	private AlertDialog.Builder builder;
	private AlertDialog.Builder cover;
	private static int flag;
	private static int which;
	private int Code;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypost);
		application = (MyAppliction) this.getApplication();
		uri = MyAppliction.serverPath + application.getUserId() + "/pho.jpg";
		listview = (ListView) this.findViewById(R.id.mypost_listview);
		view = LayoutInflater.from(MyPost.this).inflate(R.layout.mypost_listview_header, null);
		image = (ImageView) view.findViewById(R.id.mypost_listview_header_image);
		ImageLoader.getInstance().displayImage(uri, image);
		listview.addHeaderView(view);
		userId = getIntent().getExtras().getString("userId");
		new LoadData().execute(userId);
		listview.setOnItemLongClickListener(this);
		builder = new AlertDialog.Builder(MyPost.this);
		String[] items = { "删除", "修改", "查看详细信息" };
		builder.setItems(items, this);

		cover = new AlertDialog.Builder(MyPost.this);
		String[] params = { "拍照", "相册" };
		cover.setItems(params, this);

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		beans = (MyPostBeans) parent.getItemAtPosition(position);
		builder.show();
		flag = 0;
		postid = beans.postid;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 怎么根据dialog区分判断是哪个alertdailog.builder对象呢？ 1:设全局变量 有没有更好的设计模式呢???
	 * 
	 * @see
	 * android.content.DialogInterface.OnClickListener#onClick(android.content
	 * .DialogInterface, int)
	 */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		if (flag == 0) {

			switch (which) {
			case 0:
				String[] strings = new String[2];
				strings[0] = "delete";
				strings[1] = String.valueOf(JsonTools.getJsonObject("delete", postid));
				new Manage().execute(strings);
				break;

			case 1:
				Intent intent = new Intent(MyPost.this, ModifyPost.class);
				intent.putExtra("message", beans.message);
				intent.putExtra("postid", beans.postid);
				Code = 3;
				startActivityForResult(intent, Code);
				break;
			case 2:
				Toast.makeText(MyPost.this, "没有更多消息了", Toast.LENGTH_LONG).show();
				break;
			}
		} else if (flag == 1) {
			switch (which) {
			case 0:
				Code = 1;// camera
				Intent fromCam = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(fromCam, Code);
				break;

			case 1:
				Code = 2;// 图库
				Intent fromPic = new Intent(Intent.ACTION_PICK, null);
				fromPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(fromPic, Code);
				break;
			}
			change.dismiss();
		}

	}

	/*
	 * (non-Javadoc) onactivity没有被调用的原因：
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// if (resultCode != RESULT_OK) {
		// // Toast.makeText(MyPost.this, "请选择照片", Toast.LENGTH_LONG).show();
		// return;
		// }

		Bitmap bitmap = null;
		switch (requestCode) {
		case 1:
			// Toast.makeText(MyPost.this, "back", Toast.LENGTH_LONG).show();
			Bundle extras = data.getExtras();
			bitmap = (Bitmap) extras.get("data");
			image.setImageBitmap(bitmap);
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bs);
			byte[] byteArray = bs.toByteArray();
			String tempPic = getExternalFilesDir("cache") + "/" + DateTools.getFileName();
			File tempFile = new File(tempPic);
			FileOutputStream fs;
			try {
				fs = new FileOutputStream(tempFile);
				fs.write(byteArray);
				fs.close();
				picpath = (tempPic);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new ChangeAvator().execute(picpath);
			break;

		case 2:
			ContentResolver contentResolver = getContentResolver();
			try {
				Uri uri = data.getData();
				bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
				image.setImageBitmap(bitmap);
				String[] proj = { android.provider.MediaStore.Images.Media.DATA };
				android.database.Cursor cursor = managedQuery(uri, proj, null, null, null);
				if (cursor != null) {
					int column_index = cursor
							.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					picpath = (cursor.getString(column_index));
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new ChangeAvator().execute(picpath);
			break;
		case 3:
			new LoadData().execute(userId);
			Toast.makeText(MyPost.this, "刷新", Toast.LENGTH_LONG).show();
			break;
		}
	}

	class ChangeAvator extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Upload upload = new Upload(params[0], MyAppliction.url_path, userId);
			return upload.changeAvator();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				JSONObject object = new JSONObject(result);
				if (object.getInt("responseCode") == 200) {
					Toast.makeText(MyPost.this, "头像修改成功", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	class LoadData extends AsyncTask<String, Void, ArrayList<MyPostBeans>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(MyPost.this);
			progressDialog.setMessage("玩命为你加载...");
			progressDialog.show();
		}

		@Override
		protected ArrayList<MyPostBeans> doInBackground(String... params) {
			// TODO Auto-generated method stub
			requestString = String.valueOf(JsonTools.getJsonObject("manage", params[0]));
			String requestUri = MyAppliction.url_path + "/servlet/GetdataAction";
			ConnectionService service = new ConnectionService(requestString, requestUri);
			String responseString = service.connect();
			Gson gson = new Gson();
			MyPostBeansMessage message = gson.fromJson(responseString, MyPostBeansMessage.class);
			if (message.responseCode == 200) {
				list = message.list;
			} else if (message.responseCode == 400) {
				// Toast.makeText(MyPost.this, "网络连接错误",
				// Toast.LENGTH_LONG).show();
			}
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<MyPostBeans> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (!(result == null)) {
				adapter = new MyPost_ListView_adapter(MyPost.this, userId, result);
				listview.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		}

	}

	class Manage extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = null;
			if (params[0].equals("delete")) {
				String requestUri = MyAppliction.url_path + "/servlet/UserAction";
				ConnectionService service = new ConnectionService(params[1], requestUri);
				result = service.connect();
			} else if (params[0].equals("modify")) {

			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			// super.onPostExecute(result);
			try {
				JSONObject object = new JSONObject(result);
				if (object.getInt("responseCode") == 200
						&& object.getString("type").equals("delete")) {
					Toast.makeText(MyPost.this, "删除成功", Toast.LENGTH_LONG).show();
					new LoadData().execute(userId);
					// adapter.notifyDataSetChanged();
				} else {

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void changecover(View v) {
		ArrayList<String> params = new ArrayList<String>();
		params.add("微校园");
		params.add("更改头像?");
		params.add("取消");
		params.add("确定");
		change = new DIYDialog(MyPost.this, params, new DIYDialog.callbackListener() {
			@Override
			public void yes() {
				// TODO Auto-generated method stub
				cover.show();
				flag = 1;
			}

			@Override
			public void cancel() {
				// TODO Auto-generated method stub
				change.dismiss();
			}
		});
		change.show();
	}

}
