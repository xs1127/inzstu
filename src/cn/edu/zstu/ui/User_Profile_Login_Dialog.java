package cn.edu.zstu.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.zstu.R;
import cn.edu.zstu.customview.EditText_Pho_Del;

public class User_Profile_Login_Dialog extends DialogFragment {

	private View view;
	private TextView register;
	private EditText_Pho_Del username;
	private EditText_Pho_Del passwd;
	private CallBackListener mListener = null;
	private Context context;

	public User_Profile_Login_Dialog(Context context, CallBackListener mListener) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mListener = mListener;
	}

	public interface CallBackListener {
		public void onLoginPositiveClick(String username, String passwd);

		public void onLoginNegativeClick(DialogFragment dialog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.DialogFragment#onAttach(android.app.Activity)
	 * onattach方法仅对activity有效？？？
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		// Toast.makeText(activity, "--onattach-->", Toast.LENGTH_LONG).show();
		super.onAttach(activity);
		// try {
		// // mListener = (CallBackListener) activity;
		//
		//
		// } catch (ClassCastException e) {
		// Toast.makeText(getActivity(), "classcastexception",
		// Toast.LENGTH_LONG).show();
		// e.printStackTrace();
		// }
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Toast.makeText(getActivity(), "--oncreatedialog-->",
		// Toast.LENGTH_LONG)
		// .show();
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.user_profile_login, null);
		username = (EditText_Pho_Del) view.findViewById(R.id.login_username);
		passwd = (EditText_Pho_Del) view.findViewById(R.id.login_passwd);
		register = (TextView) view.findViewById(R.id.user_profile_register);
		register.setOnClickListener(new View.OnClickListener() {
			/**
			 * 跳转到注册界面
			 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(getActivity(), "register",
				// Toast.LENGTH_LONG).show();
				Intent intent = new Intent(getActivity(), Register.class);
				startActivity(intent);
			}
		});
		builder.setView(view).setPositiveButton("登录", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// CallBackListener mListener = (CallBackListener) getActivity()
				// .getFragmentManager().findFragmentById(
				// R.id.user_profile_fragment);
				mListener.onLoginPositiveClick(username.getText().toString(), passwd.getText()
						.toString());

			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				mListener.onLoginNegativeClick(User_Profile_Login_Dialog.this);
			}
		});

		return builder.create();
	}

	public void getData(String username, String passwd) {
		mListener.onLoginPositiveClick(username, passwd);
	}

}
