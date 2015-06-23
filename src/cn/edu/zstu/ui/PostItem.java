package cn.edu.zstu.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cn.edu.zstu.MainActivity;
import cn.edu.zstu.R;
import cn.edu.zstu.dialogbuilder.DIYDialog;
import cn.edu.zstu.tools.DateTools;
import cn.edu.zstu.tools.MyAppliction;
import cn.edu.zstu.tools.Upload;

public class PostItem extends Activity implements OnClickListener, DialogInterface.OnClickListener {

	private Button sendButton;
	private ImageView firstImageView, secondImageView, thirdImageView, fourthImageView,
			fifthImageView, sixthImageView;
	private ArrayList<String> picpath = new ArrayList<String>();
	// private ArrayList<String> paras = new ArrayList<String>();
	private String post = "";
	private EditText introduce;
	private ProgressDialog progressDialog;
	private DIYDialog dialog = null;
	private DIYDialog alertDialog = null;
	private ArrayList<String> params = null;
	private ViewPager viewPager;
	private final int SUCCESS = 1;
	private AlertDialog.Builder builder;
	private MyAppliction appliction;
	private static int index;
	private static int which;
	private String Category;

	public PostItem() {
		// TODO Auto-generated constructor stub
	}

	public PostItem(ViewPager viewPager) {
		this.viewPager = viewPager;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postitem);
		Bundle bundle = getIntent().getExtras();
		Category = (String) bundle.get("Category");
		index = 0;
		which = 0;
		introduce = (EditText) this.findViewById(R.id.postitem_introduce);
		firstImageView = (ImageView) this.findViewById(R.id.postitem_first_image);
		secondImageView = (ImageView) this.findViewById(R.id.postitem_second_image);
		thirdImageView = (ImageView) this.findViewById(R.id.postitem_third_image);
		fourthImageView = (ImageView) this.findViewById(R.id.postitem_fourth_image);
		fifthImageView = (ImageView) this.findViewById(R.id.postitem_fifth_image);
		sixthImageView = (ImageView) this.findViewById(R.id.postitem_sixth_image);
		firstImageView.setOnClickListener(this);
		secondImageView.setOnClickListener(this);
		thirdImageView.setOnClickListener(this);
		fourthImageView.setOnClickListener(this);
		fifthImageView.setOnClickListener(this);
		sixthImageView.setOnClickListener(this);
		sendButton = (Button) this.findViewById(R.id.postitem_sendbutton);
		sendButton.setOnClickListener(this);

		builder = new AlertDialog.Builder(PostItem.this);
		builder.setItems(R.array.camOrPic, this);

		progressDialog = new ProgressDialog(PostItem.this);
		progressDialog.setMessage("正在拼命为你加载....");
		params = new ArrayList<String>();
		params.add("微校园");
		params.add("确定退出编辑吗？");
		params.add("取消");
		params.add("确定");
		dialog = new DIYDialog(PostItem.this, params, new DIYDialog.callbackListener() {

			@Override
			public void yes() {
				// TODO Auto-generated method stub
				MyAppliction.catOrFil = 1;
				finish();
			}

			@Override
			public void cancel() {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// Intent intent = new Intent(Intent.ACTION_PICK, null);
		// intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
		// "image/*");
		if (v.getId() == R.id.postitem_sendbutton) {
			/**
			 * 发送状态 开启新线程处理请求 上传数据到服务器
			 */
			picpath.add(0, introduce.getText().toString());
			picpath.add(1, Category);
			if (picpath.size() == 2 && picpath.get(0).equals("")) {
				ArrayList<String> list = new ArrayList<String>();
				list.add("微校园");
				list.add("内容为空！");
				list.add("取消");
				list.add("确定");
				alertDialog = new DIYDialog(PostItem.this, list, new DIYDialog.callbackListener() {

					@Override
					public void yes() {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}

					@Override
					public void cancel() {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				alertDialog.show();
			} else {
				new uploadTask().execute(picpath);
			}
		} else {
			builder.show();
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

		switch (which) {
		case 0:
			PostItem.which = 0;// 拍照
			Intent fromCam = new Intent("android.media.action.IMAGE_CAPTURE");
			startActivityForResult(fromCam, index);
			break;

		case 1:
			PostItem.which = 1;
			Intent fromPic = new Intent(Intent.ACTION_PICK, null);
			fromPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(fromPic, index);
			break;

		}
		index++;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			Toast.makeText(getApplication(), "请选择图片", Toast.LENGTH_LONG).show();
			return;
		}

		Bitmap bitmap = null;
		if (which == 1) {
			ContentResolver contentResolver = getContentResolver();
			try {
				Uri uri = data.getData();
				bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
				String[] proj = { android.provider.MediaStore.Images.Media.DATA };
				android.database.Cursor cursor = managedQuery(uri, proj, null, null, null);
				if (cursor != null) {
					int column_index = cursor
							.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					picpath.add(cursor.getString(column_index));
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (which == 0) {
			Bundle extras = data.getExtras();
			bitmap = (Bitmap) extras.get("data");
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
				picpath.add(tempPic);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		switch (requestCode) {
		case 0:
			firstImageView.setImageBitmap(bitmap);
			secondImageView.setVisibility(ImageView.VISIBLE);
			break;
		case 1:
			secondImageView.setImageBitmap(bitmap);
			thirdImageView.setVisibility(ImageView.VISIBLE);
			break;
		case 2:
			thirdImageView.setImageBitmap(bitmap);
			fourthImageView.setVisibility(ImageView.VISIBLE);
			break;
		case 3:
			fourthImageView.setImageBitmap(bitmap);
			fifthImageView.setVisibility(ImageView.VISIBLE);
			break;
		case 4:
			fifthImageView.setImageBitmap(bitmap);
			sixthImageView.setVisibility(ImageView.VISIBLE);
			break;
		case 5:
			sixthImageView.setImageBitmap(bitmap);
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialog.show();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		dialog.show();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			dialog.dismiss();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	class uploadTask extends AsyncTask<ArrayList<String>, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected String doInBackground(ArrayList<String>... params) {
			// TODO Auto-generated method stub
			appliction = (MyAppliction) getApplication();
			Upload upload = new Upload(params[0], appliction.url_path, appliction.getUserId());
			return upload.uploadTask();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			MyAppliction.catOrFil = 1;
			progressDialog.dismiss();
			picpath.clear();
			System.out.println("上传结束！");
			System.out.println("--result--" + result);
			// setResult(SUCCESS);
			PostItem.this.finish();
			MainActivity.main_viewpager.setCurrentItem(2);
		}

	}

}
