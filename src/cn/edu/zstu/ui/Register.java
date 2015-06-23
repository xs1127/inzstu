package cn.edu.zstu.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import cn.edu.zstu.R;
import cn.edu.zstu.dialogbuilder.DIYDialog;

public class Register extends Activity {

	private DIYDialog dialog = null;
	private ArrayList<String> params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		params = new ArrayList<String>();
		params.add("注册");
		params.add("确定退出注册吗？");
		params.add("取消");
		params.add("确定");
		dialog = new DIYDialog(Register.this, params, new DIYDialog.callbackListener() {

			@Override
			public void yes() {
				// TODO Auto-generated method stub
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialog.show();
		}
		return super.onKeyDown(keyCode, event);
	}

	// @Override
	// public void onBackPressed() {
	// // TODO Auto-generated method stub
	// super.onBackPressed();
	// dialog.show();
	// }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dialog.dismiss();
	}
}
