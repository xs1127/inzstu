package cn.edu.zstu.ui;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.zstu.MainActivity;
import cn.edu.zstu.PullRefreshListViewActivity;
import cn.edu.zstu.R;
import cn.edu.zstu.DB.ConnectionService;
import cn.edu.zstu.tools.JsonTools;
import cn.edu.zstu.tools.MyAppliction;
import cn.edu.zstu.tools.User_Profile_List;
import cn.edu.zstu.ui.User_Profile_Login_Dialog.CallBackListener;

public class User_Profile_Fragment extends Fragment implements OnItemClickListener,
		View.OnClickListener {

	private View view;
	private TextView user_profile_textview_left;
	private Button user_profile_login_right;
	private ImageButton user_profile_post, user_profile_collection, user_profile_subscribe;
	private ListView user_profile_listview;
	private List<Map<String, Object>> list;
	private SimpleAdapter user_profile_listview_adapter;
	private final static int user_profile_post_constant = 100;
	private ProgressDialog progressDialog;
	private String userName, userId;
	private MyAppliction appliction;

	public User_Profile_Fragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// LayoutInflater inflater = getActivity().getLayoutInflater();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.user_profile, container, false);
		user_profile_listview = (ListView) view.findViewById(R.id.user_profile_listview);
		user_profile_textview_left = (TextView) view.findViewById(R.id.user_profile_textview_left);
		user_profile_login_right = (Button) view.findViewById(R.id.user_profile_login_right);
		user_profile_post = (ImageButton) view.findViewById(R.id.user_profile_post);
		user_profile_collection = (ImageButton) view.findViewById(R.id.user_profile_collection);
		user_profile_subscribe = (ImageButton) view.findViewById(R.id.user_profile_subscribe);
		user_profile_login_right.setOnClickListener(this);
		user_profile_post.setOnClickListener(this);
		user_profile_collection.setOnClickListener(this);
		user_profile_subscribe.setOnClickListener(this);
		list = new User_Profile_List().getList();
		user_profile_listview_adapter = new SimpleAdapter(getActivity(), list,
				R.layout.user_profile_listview, new String[] { "picture", "up", "down" },
				new int[] { R.id.user_profile_listview_imageview,
						R.id.user_profile_listview_textview_up,
						R.id.user_profile_listview_textview_down });
		user_profile_listview.setAdapter(user_profile_listview_adapter);
		user_profile_listview.setOnItemClickListener(this);
		appliction = (MyAppliction) getActivity().getApplication();

		if (!appliction.getUserId().equals("null") && !appliction.getUserName().equals("null")) {
			user_profile_textview_left.setText("欢迎回来:" + appliction.getUserName());
			user_profile_login_right.setText("注销");
		}

		return view;
	}

	private void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == user_profile_post_constant && resultCode == 1) {
			Toast.makeText(getActivity(), "fanhui successful!", Toast.LENGTH_LONG).show();
			MainActivity.main_viewpager.setCurrentItem(2);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		// Toast.makeText(getActivity(), "123", Toast.LENGTH_LONG).show();
		switch (position) {
		case 0:
			/**
			 * 提供对发布帖子的操作：查看、修改、删除
			 */
			if (!appliction.getUserId().equals("null")) {
				String userId = appliction.getUserId();
				Intent intent = new Intent(getActivity(), MyPost.class);
				intent.putExtra("userId", userId);
				startActivity(intent);
			} else {
				Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_LONG).show();
			}
			break;

		case 1:
			if (!appliction.getUserId().equals("null")) {
				String userId = appliction.getUserId();
				Intent intent = new Intent(getActivity(), MyPurchase.class);
				intent.putExtra("userid", userId);
				startActivity(intent);
			} else {
				Toast.makeText(getActivity(), "请先登录!", Toast.LENGTH_LONG).show();
			}
			break;

		case 2:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_profile_login_right:
			if (user_profile_login_right.getText().equals("登录")) {
				User_Profile_Login_Dialog newFragment = new User_Profile_Login_Dialog(
						getActivity(), new CallBackListener() {

							@Override
							public void onLoginPositiveClick(String username, String passwd) {
								// TODO Auto-generated method stub
								String requestString = String.valueOf(JsonTools.getJsonObject(
										username, passwd, "login"));
								new LoginTask().execute(requestString);
							}

							@Override
							public void onLoginNegativeClick(DialogFragment dialog) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
				newFragment.show(getFragmentManager(), "User_Profile_Login_Dialog");
			} else {
				user_profile_textview_left.setText("登录可使用更多功能!");
				user_profile_login_right.setText("登录");
				appliction.setUserName("null");
				appliction.setUserId("null");
			}

			// newFragment
			break;

		case R.id.user_profile_post:
			if (!appliction.getUserId().equals("null")) {
				/**
				 * 设标记flag 判断是发布新帖还是想实现过滤功能 发布新帖前 选择分类信息
				 */
				Toast.makeText(getActivity(), "选择分类信息", Toast.LENGTH_LONG).show();
				MainActivity.main_viewpager.setCurrentItem(1);
				appliction.catOrFil = 0;

			} else {
				Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.user_profile_collection:
			Toast.makeText(getActivity(), "收藏", Toast.LENGTH_LONG).show();
			Intent intent2 = new Intent(getActivity(), PullRefreshListViewActivity.class);
			startActivity(intent2);
			break;
		case R.id.user_profile_subscribe:
			Toast.makeText(getActivity(), "订阅", Toast.LENGTH_LONG).show();
			break;

		}
	}

	class LoginTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("玩命为你加载...");
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url_path = appliction.url_path + "/servlet/UserAction";
			String result = "";
			result = new ConnectionService(params[0], url_path).connect();
			System.out.println("--from UserAction--" + result + "--------");
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();
			try {
				JSONObject object = new JSONObject(result);
				if ((object.getInt("responseCode")) == 200) {
					userName = object.getString("userName");
					userId = object.getString("userId");
					SharedPreferences.Editor editor = appliction.autoLogin.edit();
					editor.putString("userName", userName);
					editor.putString("userId", userId);
					editor.commit();
					user_profile_textview_left.setText("欢迎回来:" + userName);
					user_profile_login_right.setText("注销");
					// appliction = (MyAppliction)
					// getActivity().getApplication();
					appliction.setUserName(userName);
					appliction.setUserId(userId);
				} else if ((object.getInt("responseCode")) == 500) {
					Toast.makeText(getActivity(), "用户名或密码输入错误,请重新输入!", Toast.LENGTH_LONG).show();
				} else if ((object.getInt("responseCode")) == 400) {
					Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_LONG).show();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
