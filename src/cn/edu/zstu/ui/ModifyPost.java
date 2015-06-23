package cn.edu.zstu.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Element;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.edu.zstu.R;
import cn.edu.zstu.DB.ConnectionService;
import cn.edu.zstu.tools.JsonTools;
import cn.edu.zstu.tools.MyAppliction;

public class ModifyPost extends Activity implements OnClickListener {
	private EditText message;
	private Button save;
	private String newMessage, oldMessage;
	private int postid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifypost);
		message = (EditText) this.findViewById(R.id.modifypost_edit);
		save = (Button) this.findViewById(R.id.modifypost_save);
		oldMessage = getIntent().getExtras().getString("message");
		message.setText(oldMessage);
		postid = getIntent().getExtras().getInt("postid");
		save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		newMessage = message.getText().toString();
		if (newMessage.equals(oldMessage)) {
			Toast.makeText(ModifyPost.this, "未作任何修改", Toast.LENGTH_LONG).show();
			return;
		} else {
			new Modify().execute();
		}
	}

	class Modify extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String requestString = String.valueOf(JsonTools.getJsonObject("modify", postid,
					newMessage));
			String url_path = MyAppliction.url_path + "/servlet/UserAction";
			ConnectionService service = new ConnectionService(requestString, url_path);
			String responseString = service.connect();
			return responseString;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				JSONObject object = new JSONObject(result);
				if (object.getInt("responseCode") == 200) {
					Toast.makeText(ModifyPost.this, "修改成功", Toast.LENGTH_LONG).show();
					Intent intent = getIntent();
					setResult(200, intent);
					ModifyPost.this.finish();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
