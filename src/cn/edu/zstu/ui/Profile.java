package cn.edu.zstu.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.zstu.R;
import cn.edu.zstu.DB.ConnectionService;
import cn.edu.zstu.tools.JsonTools;
import cn.edu.zstu.tools.MyAppliction;

public class Profile extends Activity implements OnClickListener {

	private String userid;// 记录某条信息所对应的的userid
	private String postid;
	private Button buyAble;
	MyAppliction application;
	private TextView userName, gender, institute, phone, postdate, state;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		application = (MyAppliction) this.getApplication();
		Bundle bundle = getIntent().getExtras();
		userid = bundle.getString("userid");
		postid = bundle.getString("postid");
		// (TextView)this.findViewById(R.id.userName_2).setText();
		buyAble = (Button) this.findViewById(R.id.favorite_ok);
		userName = (TextView) this.findViewById(R.id.userName_2);
		gender = (TextView) this.findViewById(R.id.gender_2);
		institute = (TextView) this.findViewById(R.id.institute_2);
		phone = (TextView) this.findViewById(R.id.phone_2);
		postdate = (TextView) this.findViewById(R.id.postDate_2);
		state = (TextView) this.findViewById(R.id.state_2);
		if (userid.equals(application.getUserId())) {
			buyAble.setClickable(false);
			buyAble.setBackgroundColor(getResources().getColor(R.color.grey_style));
		} else {
			buyAble.setClickable(true);
			buyAble.setOnClickListener(this);

		}
		String[] strings = new String[2];
		strings[0] = "detail";
		strings[1] = String.valueOf(JsonTools.getJsonObject(userid, postid, "detail"));
		new LoadData().execute(strings);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// Toast.makeText(this, "购买成功", Toast.LENGTH_LONG).show();
		String[] strings = new String[2];
		strings[0] = "buy";
		// strings[1] = String.valueOf(JsonTools.getJsonObject(userid, postid,
		// "buy"));
		strings[1] = String.valueOf(JsonTools.getJsonObject(userid, postid,
				application.getUserId(), "buy"));
		new LoadData().execute(strings);
	}

	class LoadData extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// String requeString =
			// String.valueOf(JsonTools.getJsonObject(userid, postid,
			// "detail"));
			String requeString = params[1];
			String url_path = MyAppliction.url_path + "/servlet/UserAction";
			String result = "";
			result = new ConnectionService(requeString, url_path).connect();
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				JSONObject object = new JSONObject(result);
				if (object.getInt("responseCode") == 200) {
					if (object.getString("type").equals("detail")) {
						userName.setText(object.getString("username"));
						gender.setText(object.getString("gender"));
						institute.setText(object.getString("institute"));
						phone.setText(object.getString("phone"));
						postdate.setText(object.getString("posttime"));
						if (object.getInt("state") == 0) {
							state.setText("否");
						} else {
							state.setText("是");
							buyAble.setClickable(false);
							buyAble.setBackgroundColor(getResources().getColor(R.color.grey_style));
						}
					} else if (object.getString("type").equals("buy")) {
						Toast.makeText(Profile.this, "购买成功", Toast.LENGTH_LONG).show();
						state.setText("是");
						buyAble.setClickable(false);
						buyAble.setBackgroundColor(getResources().getColor(R.color.grey_style));

					}
				} else if (object.getInt("responseCode") == 500) {
					Toast.makeText(Profile.this, "已售卖", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
