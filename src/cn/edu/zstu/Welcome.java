package cn.edu.zstu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Welcome extends Activity {

	private TextView up, down;
	private ImageView image;
	private String[] params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		params = this.getResources().getStringArray(R.array.welcome);
		up = (TextView) this.findViewById(R.id.welcome_up);
		down = (TextView) this.findViewById(R.id.welcome_down);
		up.setText(params[0]);
		down.setText(params[1]);
		welcome();
	}

	private void welcome() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				Intent intent = new Intent(Welcome.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}).start();
	}
}
